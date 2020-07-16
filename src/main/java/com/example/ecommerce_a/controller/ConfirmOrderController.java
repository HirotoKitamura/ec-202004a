package com.example.ecommerce_a.controller;

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
	 * @param model リクエストスコープに値を渡すためのオブジェクト
	 * @param hasErrors エラーの有無を判定する
	 * @return 注文確認画面
	 */
	@RequestMapping("")
	public String showOrderConfirm(Model model, boolean hasErrors) {
		if (session.getAttribute("user") == null) {
			return "redirect:/toLogin?from=cart";
		}
		Order order = service.findByuserIdAndStatus0();
		model.addAttribute("order", order);
		
		//お届け先情報を入力する際に、デフォルト値にログイン者情報をセットする。
		//バリデーションチェックでエラーがあった場合にif文の中身が実行されないようにした
		if (!hasErrors) {
			OrderForm orderForm = new OrderForm();
			User user = (User)session.getAttribute("user");
			orderForm.setDestinationName(user.getName());
			orderForm.setDestinationEmail(user.getEmail());
			orderForm.setDestinationZipcode(user.getZipcode());
			orderForm.setDestinationAddress(user.getAddress());
			orderForm.setDestinationTel(user.getTelephone());
			model.addAttribute("orderForm", orderForm);
			System.out.println(orderForm.getDestinationName());
		}
		return "order_confirm";
	}
}
