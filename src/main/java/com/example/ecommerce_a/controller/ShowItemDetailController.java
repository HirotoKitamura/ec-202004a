package com.example.ecommerce_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.service.ShowItemDetailService;

/**
 * 商品の詳細情報を取得.
 * 
 * @author junpei.azuma
 *
 */
@Controller
@RequestMapping("/showItemDetail")
public class ShowItemDetailController {
	
	@Autowired
	private ShowItemDetailService service;
	
	/**
	 * 渡されたidに合致する商品を取得する.
	 * 
	 * @param itemID 商品id
	 * @param model リクエストスコープに値を渡すためのオブジェクト
	 * @return 商品詳細ページ
	 */
	public String showItemDetail(Integer itemID, Model model) {
		Item item = service.ShowItemDetail(itemID);
		model.addAttribute("item", item);
		return "item_detail";
		
	}
}
