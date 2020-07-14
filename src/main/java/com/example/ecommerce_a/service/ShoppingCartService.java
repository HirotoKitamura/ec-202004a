package com.example.ecommerce_a.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderTopping;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.InsertItemInShoppingForm;
import com.example.ecommerce_a.repository.OrderItemRepository;
import com.example.ecommerce_a.repository.OrderRepository;
import com.example.ecommerce_a.repository.OrderToppingRepository;

/**
 * ショッピングカートの中身を操作するサービス.
 * 
 * @author hyoga.ito
 *
 */
@Service
@Transactional
public class ShoppingCartService {
	/** リポジトリの参照を注入 */
	@Autowired
	private OrderRepository orderRepository;

	/** リポジトリの参照を注入 */
	@Autowired
	private OrderItemRepository orderItemRepository;

	/** リポジトリの参照を注入 */
	@Autowired
	private OrderToppingRepository orderToppingRepository;

	/** セッションスコープ用変数 */
	@Autowired
	private HttpSession session;

	/**
	 * ユーザーIDとステータスから注文情報を取得する.
	 * 
	 * @param userId ユーザーID
	 * @return 注文情報
	 */
	public Order findByuserIdAndStatus0() {
		
		if (session.getAttribute("userId") != null) {
			Order order = orderRepository.findByUserIdAndStatus((int) session.getAttribute("userId"), 0);
			return order;
		}
		return null;
	}

	/**
	 * フォームの内容をデータベースに登録する.
	 * 
	 * @param form 入力されたフォーム
	 */
	public void insertItemIntoShoppingCart(InsertItemInShoppingForm form) {

		User user = (User) session.getAttribute("user");

		if (user == null && session.getAttribute("userId") == null) {

			session.setAttribute("userId", (orderRepository.findMinUserId() - 1));
		}else if(session.getAttribute("userId") == null){
		
			session.setAttribute("userId", user.getId());
		}

		int userId = Integer.parseInt("" + session.getAttribute("userId"));

		Order order = orderRepository.findByUserIdAndStatus(userId, 0);

		if (orderRepository.findByUserIdAndStatus(userId, 0) == null) {
			order = new Order();
			order.setUserId(userId);
			order.setStatus(0);
			order.setTotalPrice(0);// TODO 後で正しい値に修正
			order = orderRepository.insert(order);
			List<OrderItem> orderItems=new ArrayList<>();
			order.setOrderItemList(orderItems);

		}

		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(form.getItemId());
		orderItem.setOrderId(order.getId());
		orderItem.setQuantity(form.getQuantity());
		orderItem.setSize(form.getSize());
		orderItem = orderItemRepository.insert(orderItem);
		List<OrderTopping> orderToppings=new ArrayList<>();
		orderItem.setOrderToppingList(orderToppings);
		
		order.getOrderItemList().add(orderItem);
		//orderItems.add(orderItem);

		for (Integer toppingId : form.getToppingIds()) {
			OrderTopping orderTopping = new OrderTopping();
			orderTopping.setToppingId(toppingId);
			orderTopping.setOrderItemId(orderItem.getId());
			orderToppingRepository.insert(orderTopping);
			orderItem.getOrderToppingList().add(orderTopping);
			
		}

	}

	/**
	 * 注文商品IDから、注文商品と注文トッピングを削除する.
	 * 
	 * @param orderItemId 注文商品ID
	 */
	public void deleteItemFromShoppingCart(Integer orderItemId) {
		orderItemRepository.deleteById(orderItemId);
		orderToppingRepository.deleteByOrderItemId(orderItemId);

	}

}
