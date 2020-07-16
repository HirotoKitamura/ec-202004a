package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.repository.UserRepository;

/**
 * ユーザー情報を操作するサービス
 * 
 * @author hyoga.ito
 *
 */
@Service
public class MyPageService {
	@Autowired
	UserRepository userRepository;
	
	public User serchUserByEmail(String email) {
		User user=userRepository.findByEmail(email);
		return user;
	}
	
	/**
	 * ユーザー情報を更新する.
	 * 
	 * @param user ユーザー情報
	 */
	public void updateUser(User user) {
		userRepository.updateUser(user);
	}
	
	/**
	 * 退会処理を行う.
	 * 
	 * @param userId ユーザーID
	 */
	public void withdrawalFromEcsite(Integer userId) {
		userRepository.deleteUser(userId);
	}
}
