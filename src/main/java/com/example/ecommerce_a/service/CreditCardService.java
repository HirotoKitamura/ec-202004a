package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.ecommerce_a.domain.CreditCardRequest;
import com.example.ecommerce_a.domain.CreditCardResponse;

/**
 * クレジットカードの認証に関するサービス.
 * 
 * @author hiroto.kitamura
 *
 */
@Service
public class CreditCardService {
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * APIがあるサーバーのURL.
	 * 
	 * ※IPアドレスは自分のVMのアドレスにすること
	 */
	public static final String URL = "http://192.168.30.126:8080/sample-credit-card-web-api/credit-card/payment";

	/**
	 * APIにクレジットカードの認証を問い合わせる.
	 * 
	 * @param request カード情報
	 * @return 認証の可否
	 */
	public Boolean isAuthenticated(CreditCardRequest request) {
		CreditCardResponse response = restTemplate.postForObject(URL, request, CreditCardResponse.class);
		System.out.println(response);
		return "success".equals(response.getStatus());
	}

}
