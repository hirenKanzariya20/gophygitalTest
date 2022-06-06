package com.gophygital.daos;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.gophygital.models.User;

@Repository
public class UserDao extends BaseDAO<User>{
	
	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return "user";
	}
	
	@Override
	protected int getInsertSql(User bean) {
		final String sql = "INSERT INTO "+ getTableName() +"(username, password, name, mobile, language, role, active, created_date)"
				+ " VALUES(:username,:password,:name,:mobile,:language,:role,:active,:createdDate) ";
		SqlParameterSource parameterSource = getParameterSource(bean);
		int updated = getNamedParameterJdbcTemplate().update(sql,parameterSource);
		return updated;
	}
	

	public User findByUsername(String username) {
		final String sql = "Select * from " + getTableName() + " where username = ? ";
		System.out.println("sql--->"+sql);
		System.out.println("username--->"+username);
		return getJdbcTemplate().queryForObject(sql,BeanPropertyRowMapper.newInstance(User.class), new Object[] { username });
	}

	public List<User> getAllUser() {
		final String sql = "Select * from " + getTableName() + " where role ='User'";
		return getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(User.class));
	}

	public void updateUserActiveStatus(String username, String active) {
		String sql = "UPDATE user SET active=?  WHERE username = ?";		
		executeUpdateSql(sql,new Object[]{active, username});
	}
	
}