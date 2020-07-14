package com.example.ecommerce_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.service.OrderHistoryService;

/**
 * 注文履歴のコントローラー.
 * 
 * @author hiroto.kitamura
 *
 */
@Controller
@RequestMapping("")
public class OrderHistoryController {
	@Autowired
	OrderHistoryService service;

	/**
	 * 注文履歴の一覧画面を表示する.
	 * 
	 * @return 注文履歴の一覧画面
	 */
	@RequestMapping("showOrderHistory")
	public String showOrderHistory(Model model) {
		System.out.println(service.searchOrdersOfLoginUser());
		for (Order order : service.searchOrdersOfLoginUser()) {
			System.out.println(order);
		}
		model.addAttribute("orderList", service.searchOrdersOfLoginUser());
		return "history_list";
	}

	/**
	 * 注文履歴の詳細画面を表示する.
	 * 
	 * @param id 注文ID
	 * @return 注文履歴の表示画面
	 */
	@RequestMapping("showHistoryDetail")
	public String showHistoryDetail(Model model, Integer id) {
		model.addAttribute("order", service.searchOrdersByOrderId(id));
		return "history_detail";
	}
}
