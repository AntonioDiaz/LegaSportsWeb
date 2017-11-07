package com.adiaz.forms;

import lombok.Data;
import org.aspectj.lang.annotation.DeclareAnnotation;

/**
 * Created by toni on 26/07/2017.
 */
@Data
public class GenerateCalendarForm {
	private Long idCompetition;
	private Long idCourt;
	private Integer numWeeks;
}
