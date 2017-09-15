package com.adiaz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import java.util.Date;

/**
 * Created by toni on 14/09/2017.
 */
@Entity
public class Issue {

	@Id
	private Long id;

	@Index
	@Load
	@JsonIgnore
	private Ref<Town> townRef;

	@Ignore
	@JsonIgnore
	private Town town;

	@Index
	@Load
	@JsonIgnore
	private Ref<Competition> competitionRef;

	@Ignore
	@JsonIgnore
	private Competition competition;

	@Index
	@Load
	@JsonIgnore
	private Ref<Match> matchRef;

	@Ignore
	@JsonIgnore
	private Match match;

	boolean read;
	private String description;

	@Index
	private String clientId;

	@Index
	private Date dateSent;

	@OnLoad
	public void getRefs() {
		if (townRef != null && townRef.isLoaded()) {
			town = townRef.get();
		}
		if (competitionRef != null && competitionRef.isLoaded()) {
			competition = competitionRef.get();
		}
		if (matchRef != null && matchRef.isLoaded()) {
			match = matchRef.get();
		}
	}

	public Ref<Competition> getCompetitionRef() {
		return competitionRef;
	}

	public void setCompetitionRef(Ref<Competition> competitionRef) {
		this.competitionRef = competitionRef;
	}

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	public Ref<Match> getMatchRef() {
		return matchRef;
	}

	public void setMatchRef(Ref<Match> matchRef) {
		this.matchRef = matchRef;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Ref<Town> getTownRef() {
		return townRef;
	}

	public void setTownRef(Ref<Town> townRef) {
		this.townRef = townRef;
	}

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
}
