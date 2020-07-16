package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.form.OrderForm;
import com.example.ecommerce_a.service.ShoppingCartService;

/**
 * 注文確認画面を表示するコントローラクラス.
 * 
 * @author junpei.azuma
 *
 */
@Controller
@RequestMapping("/confirmOrder")
public class ConfirmOrderController {

	@Autowired
	private ShoppingCartService service;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String showOrderConfirm(Model model) {
		if (session.getAttribute("user") == null) {
			return "redirect:/toLogin?from=cart";
		}
		int updated = service.deleteSuspended();
		if (updated != 0) {
			model.addAttribute("alert", "販売終了や売り切れによりカートから削除された商品があります カートの中身をご確認ください");
		}
		Order order = service.findByuserIdAndStatus0();
		if (order == null || order.getOrderItemList() == null || order.getOrderItemList().size() == 0
				|| order.getOrderItemList().get(0).getId() == 0) {
			model.addAttribute("nullorder", "カートに何も入っていません");
		}
		model.addAttribute("order", order);
		return "order_confirm";
	}
}
