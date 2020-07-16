package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.User;

/**
 * ユーザーのリポジトリクラス.
 * 
 * @author hiroto.kitamura
 *
 */
@Repository
public class UserRepository {
	@Autowired
	NamedParameterJdbcTemplate template;
	
	@Autowired
	PasswordEncoder encoder;

	private static final RowMapper<User> USER_ROWMAPPER = new BeanPropertyRowMapper<User>(User.class);

	/**
	 * メールアドレスからユーザーを検索.
	 * 
	 * @param email メールアドレス
	 * @return 検索結果 ない場合はnull
	 */
	public User findByEmail(String email) {
		String sql = "SELECT id, name, email, password, zipcode, address, telephone"
				+ " FROM users WHERE email = :email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROWMAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	/**
	 * ユーザ情報を登録する．
	 * 
	 * @param user ユーザ情報
	 */
	public void insertUser(User user) {
		String sql = "INSERT INTO users(name,email,password,zipcode,address,telephone)"
				+ " VALUES (:name,:email,:password,:zipcode,:address,:telephone);";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", user.getName())
				.addValue("email", user.getEmail()).addValue("password", encoder.encode(user.getPassword()))
				.addValue("zipcode", user.getZipcode()).addValue("address", user.getAddress())
				.addValue("telephone", user.getTelephone());

		template.update(sql, param);
	}
	
	/**
	 * ユーザー情報を更新する.
	 * 
	 * @param user　ユーザー情報
	 */
	public void updateUser(User user) {
		
		String sql="update users set name=:name,email=:email,password=:password,"
				+ "zipcode=:zipcode,address=:address,telephone=:telephone where id=:id";
		
		SqlParameterSource param = new MapSqlParameterSource("id",user.getId()).addValue("name", user.getName())
				.addValue("email", user.getEmail()).addValue("password", encoder.encode(user.getPassword()))
				.addValue("zipcode", user.getZipcode()).addValue("address", user.getAddress())
				.addValue("telephone", user.getTelephone());
		
		template.update(sql, param);
		
	}
	
	/**
	 * ユーザー情報を削除する.
	 *  
	 * @param userId ユーザーID
	 */
	public void deleteUser(Integer userId) {
		String sql="delete from users where id=:userId";
		SqlParameterSource param =new MapSqlParameterSource("userId",userId);
		template.update(sql, param);
	}
}
