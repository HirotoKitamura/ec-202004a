package com.example.ecommerce_a.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.repository.OrderRepository;

@Service
@Transactional
public class OrderHistoryService {
	@Autowired
	private HttpSession session;
	@Autowired
	private OrderRepository repository;

	/**
	 * ログイン中のユーザーの注文履歴を取得する.
	 * 
	 * @return 注文一覧
	 */
	public List<Order> searchOrdersOfLoginUser() {
		User user = (User) session.getAttribute("user");
		List<Order> orderList = repository.findOrdersByUser(user.getId());
		return orderList;
	}

	/**
	 * ユーザーIDから注文情報を取得する.
	 * 
	 * @return 注文情報
	 */
	public Order searchOrdersByOrderId(Integer orderId) {
		return repository.findByOrderId(orderId);
	}

	/**
	 * 指定した注文IDをキャンセルにする.
	 * 
	 * @param orderId
	 */
	public void cancel(Integer orderId) {
		repository.updateStatus(orderId, 9);
	}
}
