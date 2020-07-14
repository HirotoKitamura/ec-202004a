package com.example.ecommerce_a.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;

/**
 * 注文履歴のコントローラー.
 * 
 * @author hiroto.kitamura
 *
 */
@Controller
@RequestMapping("")
public class OrderHistoryController {
	/**
	 * 注文履歴の一覧画面を表示する.
	 * 
	 * @return 注文履歴の一覧画面
	 */
	@RequestMapping("showOrderHistory")
	public String showOrderHistory() {
		// ここに諸々の処理を書く
		return "history_list";
	}

	/**
	 * 注文履歴の詳細画面を表示する.
	 * 
	 * @param orderId 注文ID
	 * @return 注文履歴の詳細画面
	 */
	@RequestMapping("showHistoryDetail")
	public String showHistoryDetail(Integer orderId, Model model) {
		Order order = new Order();
//		order.setTotalPrice(700);
		model.addAttribute("order", order);
		return "history_detail";
	}
}
