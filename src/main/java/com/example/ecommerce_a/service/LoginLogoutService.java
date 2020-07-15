package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.repository.OrderRepository;
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
	private OrderRepository orderRepository;

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

	/**
	 * ログイン前のカートの内容をログイン後にも継承する.
	 * 
	 * @param guestId ログイン前のID
	 * @param id      ログイン後のID
	 */
	public void inheritCart(Integer guestId, Integer id) {
		if (orderRepository.findByUserIdAndStatus(id, 0) == null) {
			System.out.println("おはよう");
			orderRepository.updateUserId(guestId, id);
		} else {
			orderRepository.updateOrderId(guestId, id);
			orderRepository.deleteOrder(guestId);
		}
	}
}
