package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.repository.UserRepository;

/**
 * ログインログアウトのサービスクラス.
 * 
 * @author hiroto.kitamura
 *
 */
@Service
@Transactional
public class LoginLogoutService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	/**
	 * メールアドレスとパスワードからログイン判定をする.
	 * 
	 * @param email    メールアドレス
	 * @param password パスワード
	 * @return ログインユーザー 該当するユーザーがいない場合はnull
	 */
	public User login(String email, String password) {
		User user = userRepository.findByEmail(email);
		if (user != null && encoder.matches(password, user.getPassword())) {
			return user;
		} else {
			return null;
		}
	}
}
