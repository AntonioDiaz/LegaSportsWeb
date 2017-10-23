package com.adiaz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Entity
@Cache
@Data
public class Center implements Serializable {

	@Id
	private Long id;
	
	private String name;
	
	private String address;

	@Load
	@Index
	@JsonIgnore
	private Ref<Town> townRef;

	@Ignore
	@JsonIgnore
	private Town townEntity;

	@OnLoad
	public void getRefs(){
		if (townRef!=null && townRef.isLoaded()) {
			townEntity = townRef.get();
		}
	}
}
