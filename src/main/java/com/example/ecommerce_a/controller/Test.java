package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.CreditCardRequest;
import com.example.ecommerce_a.service.CreditCardService;

/**
 * ヘッダの表示テスト用.
 * 
 * 確認出来たら消してもOKです
 * 
 * @author hiroto.kitamura
 *
 */
@Controller
@RequestMapping("test")
public class Test {

	@Autowired
	private HttpSession session;
	@Autowired
	MailSender sender;
	@Autowired
	private CreditCardService service;

	@RequestMapping("sendMail")
	public String index() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("sapec.tsukuba.ac.jp@gmail.com");
		msg.setTo("hiroto.kitamura@rakus.co.jp");
		msg.setSubject("テストメール");// タイトルの設定
		msg.setText("Spring Boot より本文送信"); // 本文の設定
		sender.send(msg);
		return "redirect:/toLogin";
	}

	@RequestMapping("creca")
	public String creca() {
		CreditCardRequest request = new CreditCardRequest("12", "3", "1000000", "1234123412341234", "2022", "12",
				"HINATA KINOSHITA", "123");
		System.out.println(service.isAuthenticated(request));
		return "redirect:/toLogin";
	}
}
