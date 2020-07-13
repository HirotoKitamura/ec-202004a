package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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

	private static final RowMapper<User> USER_ROWMAPPER = new BeanPropertyRowMapper<User>(User.class);

	/**
	 * 名前とパスワードからユーザーを検索.
	 * 
	 * @param name     ユーザー名
	 * @param password パスワード
	 * @return 検索結果 ない場合はnull
	 */
	public User findByEmailAndPassWord(String name, String password) {
		String sql = "SELECT id, name, email, password, zipcode, address, telephone"
				+ " FROM users WHERE name = :name AND password = :password;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name).addValue("password", password);
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
				.addValue("email", user.getEmail()).addValue("password", user.getPassword())
				.addValue("zipcode", user.getZipcode()).addValue("address", user.getAddress())
				.addValue("telephone", user.getTelephone());

		template.update(sql, param);
	}
}
