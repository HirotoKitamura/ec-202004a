package com.example.ecommerce_a.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.User;
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
	 * 注文確認画面を表示する.
	 * 
	 * @param model     リクエストスコープに値を渡すためのオブジェクト
	 * @param hasErrors エラーの有無を判定する
	 * @return 注文確認画面
	 */
	@RequestMapping("")
	public String showOrderConfirm(Model model, boolean hasErrors, String error) {
		if (session.getAttribute("user") == null) {
			return "redirect:/toLogin?from=cart";
		}
		int updated = service.deleteSuspended();
		if (updated != 0) {
			model.addAttribute("alert", "販売終了や売り切れによりカートから削除された商品があります カートの中身をご確認ください");
		}
		if ("modified".equals(error)) {
			model.addAttribute("alert2", "注文確認画面の表示以降にカートが変更されました カートの中身をご確認ください");
		}
		Order order = service.findByuserIdAndStatus0();
		if (order == null || order.getOrderItemList() == null || order.getOrderItemList().size() == 0
				|| order.getOrderItemList().get(0).getId() == 0) {
			model.addAttribute("nullorder", "カートに何も入っていません");
		}
		session.setAttribute("order", order);
		model.addAttribute("order", order);

		model.addAttribute("today", java.sql.Date.valueOf(LocalDate.now()).toString());
		if (!hasErrors) {
			OrderForm orderForm = new OrderForm();
			User user = (User) session.getAttribute("user");
			orderForm.setDestinationName(user.getName());
			orderForm.setDestinationEmail(user.getEmail());
			orderForm.setDestinationZipcode(user.getZipcode());
			orderForm.setDestinationAddress(user.getAddress());
			orderForm.setDestinationTel(user.getTelephone());
			orderForm.setDeliveryDate(java.sql.Date.valueOf(LocalDate.now()));
			model.addAttribute("orderForm", orderForm);
		}
		return "order_confirm";
	}
}
