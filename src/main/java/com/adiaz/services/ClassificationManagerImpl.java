package com.adiaz.services;

import java.util.*;

import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.daos.MatchesDAO;
import com.adiaz.daos.SanctionsDAO;
import com.adiaz.entities.*;
import com.adiaz.utils.LocalSportsConstants;
import com.googlecode.objectify.Ref;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.ClassificationEntriesDAO;

@Service ("classificationManager")
public class ClassificationManagerImpl implements ClassificationManager {

	public static final Logger logger = Logger.getLogger(ClassificationManagerImpl.class);

	@Autowired ClassificationEntriesDAO classificationEntriesDAO;
	@Autowired MatchesDAO matchesDAO;
	@Autowired CompetitionsDAO competitionsDAO;
	@Autowired SanctionsDAO sanctionsDAO;

	@Override
	public void add(ClassificationEntry item) throws Exception {
		classificationEntriesDAO.create(item);

	}

	@Override
	public boolean remove(ClassificationEntry item) throws Exception {
		return classificationEntriesDAO.remove(item);
	}

	@Override
	public boolean update(ClassificationEntry item) throws Exception {
		return classificationEntriesDAO.update(item);
	}

	@Override
	public List<ClassificationEntry> queryClassificationByCompetition(Long idCompetition) {
		return classificationEntriesDAO.findByCompetition(idCompetition);
	}

	@Override
	public void add(List<ClassificationEntry> classificationList) throws Exception {
		for (ClassificationEntry classificationEntry : classificationList) {
			this.add(classificationEntry);
		}
	}

	@Override
	public void updateClassificationByCompetition(Long idCompetition) {
		List<ClassificationEntry> classificationList = classificationEntriesDAO.findByCompetition(idCompetition);
		Map<Long, ClassificationEntry> teamsMap = new HashMap<>();
		for (ClassificationEntry classificationEntry : classificationList) {
			classificationEntry.setPosition(0);
			classificationEntry.setPoints(0);
			classificationEntry.setMatchesPlayed(0);
			classificationEntry.setMatchesDrawn(0);
			classificationEntry.setMatchesLost(0);
			classificationEntry.setMatchesWon(0);
			classificationEntry.setGoalsFor(0);
			classificationEntry.setGoalsAgainst(0);
			classificationEntry.getRefs();
			Long idTeam = classificationEntry.getTeamEntity().getId();
			classificationEntry.setSanctions(calculateTeamSanctionsPoints(idCompetition, idTeam));
			teamsMap.put(idTeam, classificationEntry);
		}
		/*loop over the matches*/
		List<Match> matches = matchesDAO.findByCompetition(idCompetition);
		for (Match match : matches) {
			if (match.getState()== LocalSportsConstants.MATCH_STATE.PLAYED.getValue()
					&&  match.getTeamLocalRef()!=null
					&&  match.getTeamVisitorRef()!=null) {
				Long idTeamLocal = match.getTeamLocalRef().get().getId();
				Long idTeamVisitor = match.getTeamVisitorRef().get().getId();
				ClassificationEntry teamLocalEntry = teamsMap.get(idTeamLocal);
				ClassificationEntry teamVisitorEntry = teamsMap.get(idTeamVisitor);
				int matchesPlayedLocal = teamLocalEntry.getMatchesPlayed();
				int matchesPlayedVisitor = teamVisitorEntry.getMatchesPlayed();
				int matchesDrawnLocal = teamLocalEntry.getMatchesDrawn();
				int matchesDrawnVisitor = teamVisitorEntry.getMatchesDrawn();
				int matchesWonLocal = teamLocalEntry.getMatchesWon();
				int matchesWonVisitor = teamVisitorEntry.getMatchesWon();
				int matchesLostLocal = teamLocalEntry.getMatchesLost();
				int matchesLostVisitor = teamVisitorEntry.getMatchesLost();
				int pointsLocal = teamLocalEntry.getPoints();
				int pointsVisitor = teamVisitorEntry.getPoints();
				teamLocalEntry.setMatchesPlayed(++matchesPlayedLocal);
				teamVisitorEntry.setMatchesPlayed(++matchesPlayedVisitor);
				if (match.getScoreLocal()==match.getScoreVisitor()) {
					++matchesDrawnLocal;
					++matchesDrawnVisitor;
					pointsLocal += LocalSportsConstants.POINTS_DRAWN;
					pointsVisitor += LocalSportsConstants.POINTS_DRAWN;
				} else if (match.getScoreLocal()>match.getScoreVisitor()) {
					++matchesWonLocal;
					++matchesLostVisitor;
					pointsLocal += LocalSportsConstants.POINTS_WON;
					pointsVisitor += LocalSportsConstants.POINTS_LOST;
				} else {
					++matchesLostLocal;
					++matchesWonVisitor;
					pointsLocal += LocalSportsConstants.POINTS_LOST;
					pointsVisitor += LocalSportsConstants.POINTS_WON;
				}
				teamLocalEntry.setMatchesWon(matchesWonLocal);
				teamLocalEntry.setMatchesDrawn(matchesDrawnLocal);
				teamLocalEntry.setMatchesLost(matchesLostLocal);
				teamLocalEntry.setGoalsFor(teamLocalEntry.getGoalsFor() + match.getScoreLocal());
				teamLocalEntry.setGoalsAgainst(teamLocalEntry.getGoalsAgainst() + match.getScoreVisitor());

				teamVisitorEntry.setMatchesWon(matchesWonVisitor);
				teamVisitorEntry.setMatchesDrawn(matchesDrawnVisitor);
				teamVisitorEntry.setMatchesLost(matchesLostVisitor);
				teamVisitorEntry.setGoalsFor(teamVisitorEntry.getGoalsFor() + match.getScoreVisitor());
				teamVisitorEntry.setGoalsAgainst(teamVisitorEntry.getGoalsAgainst() + match.getScoreLocal());

				teamLocalEntry.setPoints(pointsLocal);
				teamVisitorEntry.setPoints(pointsVisitor);
			}
		}
		List<ClassificationEntry> values = new ArrayList<>(teamsMap.values());
		/* sort in descendent order by, points, goals difference, goals for, and team name. */
		Collections.sort(values, new Comparator<ClassificationEntry>() {
			@Override
			public int compare(ClassificationEntry entry1, ClassificationEntry entry2) {
				if (entry1.getPoints() != entry2.getPoints()) {
					return entry1.calculateRealPoints() - entry2.calculateRealPoints();
				} else {
					int dif1 = entry1.getGoalsFor() - entry1.getGoalsAgainst();
					int dif2 = entry2.getGoalsFor() - entry2.getGoalsAgainst();
					if (dif1 != dif2) {
						return dif1 - dif2;
					} else {
						if (entry1.getGoalsFor()!= entry2.getGoalsFor()) {
							return entry1.getGoalsFor() - entry2.getGoalsFor();
						} else {
							return entry1.getTeamEntity().getName().compareTo(entry2.getTeamEntity().getName());
						}
					}
				}
			}
		});
		Collections.reverse(values);
		for (int i = values.size() -1 ; i >= 0; i--) {
			values.get(i).setPosition(i+1);
		}
		classificationEntriesDAO.save(values);
	}

	@Override
	public void initClassification(Long idCompetition) {
		Competition competition = competitionsDAO.findById(idCompetition);
		/* remove previous entries for this competition. */
		List<ClassificationEntry> classificationEntries = classificationEntriesDAO.findByCompetition(idCompetition);
		classificationEntriesDAO.remove(classificationEntries);
		/* create new entries. */
		List<ClassificationEntry> classificationEntryList = new ArrayList<>();
		Ref<Competition> competitionRef = Ref.create(competition);
		for (Team team : competition.getTeamsDeref()) {
			ClassificationEntry classificationEntry = new ClassificationEntry();
			classificationEntry.setPosition(0);
			classificationEntry.setPoints(0);
			classificationEntry.setMatchesPlayed(0);
			classificationEntry.setMatchesDrawn(0);
			classificationEntry.setMatchesLost(0);
			classificationEntry.setMatchesWon(0);
			classificationEntry.setGoalsFor(0);
			classificationEntry.setGoalsAgainst(0);
			classificationEntry.setTeamRef(Ref.create(team));
			classificationEntry.setCompetitionRef(competitionRef);
			classificationEntryList.add(classificationEntry);
		}
		classificationEntriesDAO.save(classificationEntryList);
	}

	@Override
	public void removeAll() throws Exception {
		List<ClassificationEntry> list = classificationEntriesDAO.findAll();
		for (ClassificationEntry classificationEntry : list) {
			classificationEntriesDAO.remove(classificationEntry);
		}
	}

	private Integer calculateTeamSanctionsPoints(Long idCompetition, Long idTeam) {
		Integer totalPoints = 0;
		List<Sanction> sanctions = sanctionsDAO.findByCompetitionIdAndTeamId(idCompetition, idTeam);
		for (Sanction sanction : sanctions) {
			totalPoints += sanction.getPoints();
		}
		return totalPoints;
	}
}
