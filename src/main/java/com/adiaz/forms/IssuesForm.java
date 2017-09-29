package com.adiaz.forms;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by toni on 14/09/2017.
 */
@Data
public class IssuesForm implements Serializable {

	private String clientId;
	private Long competitionId;
	private Long matchId;
	private Long townId;
	private String description;
	private Date dateSent;
}
