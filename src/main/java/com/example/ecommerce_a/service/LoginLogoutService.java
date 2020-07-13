package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
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

	/**
	 * メールアドレスとパスワードからユーザーを検索.
	 * 
	 * @param email    メールアドレス
	 * @param password パスワード
	 * @return 検索結果 ない場合はnull
	 */
	public User searchByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassWord(email, password);
	}
}
