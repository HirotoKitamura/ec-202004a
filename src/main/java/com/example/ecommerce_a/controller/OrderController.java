package com.example.ecommerce_a.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.form.OrderForm;
import com.example.ecommerce_a.service.OrderService;

@Controller
@RequestMapping("order")
public class OrderController {
	@Autowired
	private ConfirmOrderController controller;
	
	@Autowired
	private OrderService service;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}

	@RequestMapping("")
	public String order(@Validated OrderForm form, BindingResult result, Model model) {
		//配達日と現在の日付が同じで、かつ配達時間が現在の時間の3時間後よりも前のときにエラー
		Date deliveryDate = form.getDeliveryDate();
		LocalDateTime inputDeliveryTime = LocalDateTime.now().withHour(form.getDeliveryTime());
		if (deliveryDate == null || inputDeliveryTime == null ||deliveryDate.equals(LocalDate.now()) && inputDeliveryTime.isBefore(LocalDateTime.now().plusHours(3))) {
			FieldError deliveryTimeError = new FieldError(result.getObjectName(),"deliveryTime", "3時間後以降の日時を指定してください");
			result.addError(deliveryTimeError);
		}
		if (result.hasErrors()) {
			//ここで他クラスのメソッドに引数を渡す方法は？
//			return "/confirmOrder";
			System.out.println("foobar");
//			return "forward:/confirmOrder";
			return controller.showOrderConfirm(model);
		}
		//orderを取得
		Order order = service.findByUserIdAndStatus((Integer)session.getAttribute("userId"), 0);
		//注文した日時をセット
		order.setOrderDate(Date.valueOf(LocalDate.now()));
		//配達日時をセット
		order.setDeliveryTime(java.sql.Timestamp.valueOf(inputDeliveryTime));
		
		//フォームからコピーできる値をorderオブジェクトにコピー
		BeanUtils.copyProperties(form, order);
		//決済手段によって、注文後のステータスを変更
		if (order.getPaymentMethod().equals(1)) {
			order.setStatus(1);
		} else {
			order.setStatus(2);
		}
		//合計金額をセットする。
		order.setTotalPrice(order.getCalcTotalPrice());
		service.order(order);
		return "order_finished.html";
	}
}
