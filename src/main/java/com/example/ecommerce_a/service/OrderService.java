package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.repository.OrderRepository;

/**
 * 注文処理を行うサービスクラス.
 * 
 * @author junpei.azuma
 *
 */
@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	/**
	 * 注文情報を取得する.
	 * 
	 * @param userId ログイン中のユーザーid
 	 * @param status 0
	 * @return
	 */
	public Order findByUserIdAndStatus(Integer userId, Integer status) {
		return repository.findByUserIdAndStatus(userId, status);
	}
	
	
	/**
	 * 注文処理を行う.
	 * 
	 * @param userId ログイン中のユーザーid
	 * @param status 注文前のオーダーを取得するための引数
	 */
	public void order(Order order) {
		repository.update(order);
	}
	
	
}
