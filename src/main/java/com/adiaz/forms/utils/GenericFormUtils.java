package com.adiaz.forms.utils;

/**
 * Created by toni on 18/07/2017.
 */
public interface GenericFormUtils <F, E> {
	public E formToEntity(F form);
	public void formToEntity(E entity, F form);
	public F entityToForm(E entity);
}
