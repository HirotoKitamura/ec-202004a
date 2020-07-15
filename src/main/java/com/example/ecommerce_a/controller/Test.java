package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.CreditCardRequest;
import com.example.ecommerce_a.repository.OrderRepository;
import com.example.ecommerce_a.service.CreditCardService;
import com.example.ecommerce_a.service.MailService;

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
	@Autowired
	private MailService mailservice;
	@Autowired
	private OrderRepository repository;

	@RequestMapping("sendMail")
	public String index() {
//		SimpleMailMessage msg = new SimpleMailMessage();
//		msg.setFrom("sapec.tsukuba.ac.jp@gmail.com");
//		msg.setTo("hiroto.kitamura@rakus.co.jp");
//		msg.setSubject("テストメール");// タイトルの設定
//		msg.setText("Spring Boot より本文送信"); // 本文の設定
//		sender.send(msg);
		mailservice.sendMail(repository.findByUserIdAndStatus(7, 0));
		return "redirect:/toLogin";
	}

	@RequestMapping("creca")
	public String creca() {
		CreditCardRequest request = new CreditCardRequest(null, null, null, "1234123412341234", "2022", "12",
				"HINATA KINOSHITA", "123");
		System.out.println(service.isAuthenticated(request));
		return "redirect:/toLogin";
	}
}
