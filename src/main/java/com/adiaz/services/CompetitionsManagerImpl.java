package com.adiaz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adiaz.daos.CompetitionsDAO;
import com.adiaz.daos.MatchesDAO;
import com.adiaz.entities.Category;
import com.adiaz.entities.CompetitionsVO;
import com.adiaz.entities.MatchesVO;
import com.adiaz.entities.SportVO;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@Service ("competitionsManager")
public class CompetitionsManagerImpl implements CompetitionsManager {

	@Autowired CompetitionsDAO competitionsDAO;
	@Autowired MatchesDAO matchesDAO;
	
	@Override
	public void add(CompetitionsVO item) throws Exception {
		competitionsDAO.create(item);
	}

	@Override
	public boolean remove(CompetitionsVO competitionsVO) throws Exception {
		List<MatchesVO> queryMatchesByCompetition = matchesDAO.findByCompetition(competitionsVO.getId());
		for (MatchesVO matchesVO : queryMatchesByCompetition) {
			matchesDAO.remove(matchesVO);
		}
		return competitionsDAO.remove(competitionsVO);		
	}
	
	@Override
	public boolean update(CompetitionsVO item) throws Exception {
		return competitionsDAO.update(item);
	}
	
	@Override
	public List<CompetitionsVO> queryCompetitions() {
		return competitionsDAO.findCompetitions();
	}

	@Override
	public List<CompetitionsVO> queryCompetitionsBySport(SportVO sportVO) {
		return competitionsDAO.findCompetitionsBySport(sportVO);
	}
	
	public CompetitionsVO queryCompetitionsById(long id) {
		CompetitionsVO competition = null; 
		List<CompetitionsVO> competitions = competitionsDAO.findCompetitions();
		for (CompetitionsVO competitionsVO : competitions) {
			if (id==competitionsVO.getId()) {
				competition = competitionsVO;
			}
		}
		return competition;
	}

	@Override
	public void add(String name, Long sportId, Long categoryId) throws Exception {
		CompetitionsVO competitionsVO = new CompetitionsVO();
		competitionsVO.setName(name);
		Key<SportVO> keySport = Key.create(SportVO.class, sportId);
		competitionsVO.setSport(Ref.create(keySport));
		Key<Category> keyCategory = Key.create(Category.class, categoryId);
		competitionsVO.setCategory(Ref.create(keyCategory));
		competitionsDAO.create(competitionsVO);
	}

	@Override
	public List<CompetitionsVO> queryCompetitions(Long idSport, Long idCategory) {
		return competitionsDAO.findCompetitions(idSport, idCategory);
	}

	@Override
	public void removeAll() throws Exception {
		List<CompetitionsVO> competitions = competitionsDAO.findCompetitions();
		for (CompetitionsVO competition : competitions) {
			competitionsDAO.remove(competition);
		}
	}
}
