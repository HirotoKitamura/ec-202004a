package com.example.ecommerce_a.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
import com.example.ecommerce_a.service.MailService;
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
	
	@Autowired
	private MailService mailService;
	
	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}

	/**
	 * 注文する.
	 * 
	 * @param form フォームに値を渡すためのフォームオブジェクト
	 * @param result エラー判定を行うためのオブジェクト
	 * @param model リクエストスコープに値を渡す
	 * @return エラーがなければ注文完了画面。エラーがあれば注文確認画面に戻る
	 */
	@RequestMapping("")
	public String order(@Validated OrderForm form, BindingResult result, Model model) {
		//日付を指定していない場合、エラーメッセージを生成する。
		if (form.getDeliveryDate() == null) {
			FieldError deliveryTimeError = new FieldError(result.getObjectName(),"deliveryTime", "日時を指定してください");
			result.addError(deliveryTimeError);
		}
		
		if (form.getDeliveryDate() != null) {
			LocalDateTime deliveryTime = form.getDeliveryDate().toLocalDate().atTime(form.getDeliveryTime(), 0);
			if (deliveryTime.isBefore(LocalDateTime.now().plusHours(3))) {
				FieldError deliveryTimeError = new FieldError(result.getObjectName(),"deliveryTime", "3時間後以降の日時を指定してください");
				result.addError(deliveryTimeError);
			} 
		}
		//エラーがあった場合、エラーがあったという情報をconfirmOrderコントローラに渡す
		if (result.hasErrors()) {
<<<<<<< HEAD
			System.out.println("foobar");
			return controller.showOrderConfirm(model, true);
=======

			//ここで他クラスのメソッドに引数を渡す方法は？
//			return "/confirmOrder";
//			return "forward:/confirmOrder";
			return controller.showOrderConfirm(model);
>>>>>>> f/admin
		}
		//orderを取得
		Order order = service.findByUserIdAndStatus((Integer)session.getAttribute("userId"), 0);
		
		//フォームからコピーできる値をorderオブジェクトにコピー
		BeanUtils.copyProperties(form, order);
		//電話番号がなぜかコピーできないのでひとまずセット
		//order.setDestinationTel(form.getDestinationTel());
		//決済手段によって、注文後のステータスを変更
		if (order.getPaymentMethod().equals(1)) {
			order.setStatus(1);
		} else {
			order.setStatus(2);
		}
		//合計金額をセットする。
		order.setTotalPrice(order.getCalcTotalPrice());
		//注文した日時をセット
		order.setOrderDate(Date.valueOf(LocalDate.now()));
		
		//配達日時をセット
		LocalDateTime deliveryTime = form.getDeliveryDate().toLocalDate().atTime(form.getDeliveryTime(), 0);
		order.setDeliveryTime(java.sql.Timestamp.valueOf(deliveryTime));
		service.order(order);
		mailService.sendMail(order);
		return "redirect:/order/showFinished";
	}
	
	/**
	 * ダブルサブミット対策として注文完了画面を表示.
	 * 
	 * @return 注文完了画面
	 */
	@RequestMapping("/showFinished")
	public String showFinished() {
		return "order_finished";
	}
}
