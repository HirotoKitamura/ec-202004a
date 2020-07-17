package com.example.ecommerce_a.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderTopping;

@Service
@Transactional
public class MailService {
	@Autowired
	MailSender sender;

	@Async
	public void sendMail(Order order) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("sapec.tsukuba.ac.jp@gmail.com");
		msg.setTo(order.getDestinationEmail());
		msg.setSubject("ご注文完了のお知らせ");// タイトルの設定
		StringBuilder mainMessage = new StringBuilder();
		mainMessage.append(order.getDestinationName() + "様\n\n");
		mainMessage.append("ラクラクカリーをご利用いただきありがとうございます。\n");
		mainMessage.append("ご注文が完了いたしましたので、注文内容をお送りいたします。\n\n");
		mainMessage.append("注文日:" + order.getOrderDate() + "\n");
		mainMessage.append("宛先住所:〒" + order.getDestinationZipcode() + "  ");
		mainMessage.append(order.getDestinationAddress() + "\n");
		mainMessage.append("宛先電話番号:" + order.getDestinationTel() + "\n");
		LocalDateTime dateTime = order.getDeliveryTime().toLocalDateTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M月d日h時mm分");
		mainMessage.append("配達予定時刻:" + dateTime.format(formatter) + "\n\n");
		mainMessage.append("ご注文内容\n\n");
		for (OrderItem orderItem : order.getOrderItemList()) {
			mainMessage.append("商品名:" + orderItem.getItem().getName() + "\n");
			mainMessage.append("サイズ:" + orderItem.getSize() + "\n");
			mainMessage.append("数量:" + orderItem.getQuantity() + "\n");
			mainMessage.append("トッピング:\n");
			if (orderItem.getOrderToppingList().size() == 0) {
				mainMessage.append("なし\n");
			}
			for (OrderTopping orderTopping : orderItem.getOrderToppingList()) {
				mainMessage.append(orderTopping.getTopping().getName() + "\n");
			}
			mainMessage.append("\n");
			mainMessage.append("小計:" + orderItem.getSubTotal() + "円\n\n");
		}
		mainMessage.append("税抜合計価格:" + order.getTotalPrice() + "円\n");
		mainMessage.append("消費税:" + order.getTax() + "円\n");
		mainMessage.append("税込合計価格:" + order.getCalcTotalPrice() + "円\n");
		mainMessage.append("支払方法:" + (order.getPaymentMethod() == 1 ? "代金引換" : "クレジットカード") + "\n");
		msg.setText(mainMessage.toString()); // 本文の設定
		System.out.println("mail送信完了");
		sender.send(msg);
	}
}
