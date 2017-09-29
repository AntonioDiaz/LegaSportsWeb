package com.adiaz.forms;

/**
 * Created by toni on 29/09/2017.
 */
public interface GenericForm <E> {
	E entity();
	E entity(E entity);
}
