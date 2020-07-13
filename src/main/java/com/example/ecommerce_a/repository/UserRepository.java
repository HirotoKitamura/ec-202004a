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
	 * メールアドレスとパスワードからユーザーを検索.
	 * 
	 * @param email    メールアドレス
	 * @param password パスワード
	 * @return 検索結果 ない場合はnull
	 */
	public User findByEmailAndPassWord(String email, String password) {
		String sql = "SELECT id, name, email, password, zipcode, address, telephone"
				+ " FROM users WHERE email = :email AND password = :password;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
		List<User> userList = template.query(sql, param, USER_ROWMAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
}
