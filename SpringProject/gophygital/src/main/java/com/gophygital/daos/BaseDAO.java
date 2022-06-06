package com.gophygital.daos;



import java.util.Date;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;




public abstract class BaseDAO<T> extends
		NamedParameterJdbcDaoSupport {

	protected Class<T> genericType;

	@SuppressWarnings("unchecked")
	public BaseDAO() {
		super();
		// Get the generic type class
		this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(
				getClass(), BaseDAO.class);
	}

	@Autowired
	public void setDS(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	protected abstract String getTableName();

	protected abstract int getInsertSql(T bean);
	
	
	public int insert(final T bean) { 
		return getInsertSql(bean); 
	}
	
	protected BeanPropertySqlParameterSource getParameterSource(T bean) {
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(
				bean);
		return parameterSource;
	}
	
	private int executeSQL(final T bean, final String sql) {

		SqlParameterSource parameterSource = getParameterSource(bean);
		int updated = getNamedParameterJdbcTemplate().update(sql,
				parameterSource);
		return updated;
	}
	
	public void executeUpdateSql(final String sql, Object[] parameters) {
		getJdbcTemplate().update(sql, parameters);
	}
}
