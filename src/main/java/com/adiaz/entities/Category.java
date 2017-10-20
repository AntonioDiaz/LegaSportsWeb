package com.adiaz.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import lombok.Data;

@Entity
@Cache
@Data
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	@Index
	private String name;
	
	@Index
	private Integer order;
}
