package com.adiaz.daos;

import com.googlecode.objectify.Key;

public interface GenericDAO <T>{
	Key<T> create(T item) throws Exception;
	boolean update(T item) throws Exception;
	boolean remove(T item) throws Exception;
}
