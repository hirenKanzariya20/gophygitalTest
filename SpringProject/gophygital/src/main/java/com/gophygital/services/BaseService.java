 package com.gophygital.services;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.gophygital.daos.BaseDAO;


@Transactional
public abstract class BaseService<T> {
	protected abstract BaseDAO<T> getDAO();
	
	
	public int insert(T bean) {
		return getDAO().insert(bean);
	}
	
	
}
