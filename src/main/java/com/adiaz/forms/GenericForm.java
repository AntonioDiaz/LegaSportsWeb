package com.adiaz.forms;

/**
 * Created by toni on 29/09/2017.
 */
public interface GenericForm <E> {
	E formToEntity();
	E formToEntity(E entity);
}
