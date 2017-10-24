package com.adiaz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by toni on 24/07/2017.
 */
@Entity
@Cache
@Data
public class Club  implements Serializable {
	@Id
	private Long id;

	private String name;

	private String contactPerson;

	private String contactEmail;

	private String contactAddress;

	private String contactPhone;

	@Load
	@JsonIgnore
	@Index
	private Ref<Town> townRef;

	@Ignore
	@JsonIgnore
	private Town townEntity;

	@OnLoad
	private void getRefs(){
		if (townRef!=null && townRef.isLoaded()){
			townEntity = townRef.get();
		}
	}
}
