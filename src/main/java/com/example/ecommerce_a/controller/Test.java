package com.example.ecommerce_a.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ヘッダの表示テスト用.
 * 
 * 確認出来たら消してもOKです
 * 
 * @author hiroto.kitamura
 *
 */
@Controller
@RequestMapping("")
public class Test {
	@RequestMapping("")
	public String index() {
		return "item_detail";
	}
}
