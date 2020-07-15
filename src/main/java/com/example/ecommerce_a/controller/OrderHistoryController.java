package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	@Autowired
	HttpSession session;

	/**
	 * 注文履歴の一覧画面を表示する.
	 * 
	 * @return 注文履歴の一覧画面
	 */
	@RequestMapping("showOrderHistory")
	public String showOrderHistory(Model model) {
		if (session.getAttribute("user") == null) {
			return "redirect:/toLogin";
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
		if (session.getAttribute("user") == null) {
			return "redirect:/toLogin";
		}
		model.addAttribute("order", service.searchOrdersByOrderId(id));
		return "history_detail";
	}
	
	@RequestMapping("cancel")
	public String cancel(Integer orderId) {
		service.cancel(orderId);
		return "redirect:/showOrderHistory";
		
	}
}
