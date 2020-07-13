package com.example.ecommerce_a.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.repository.ShowItemListService;

/**
 * 商品一覧表示と検索を行うコントローラクラス.
 * 
 * @author junpei.azuma
 *
 */
@Controller
@RequestMapping("showItemList")
public class ShowItemListController {
	
	@Autowired
	private ShowItemListService service;
	
	/**
	 * 商品一覧表示と検索を行う.
	 * 
	 * @param name 検索ワード
	 * @param order 表示順(デフォルトでは価格の安い順になるように設定)
	 * @param model リクエストスコープに値を格納するためのオブジェクト
	 * @return 商品一覧ページ
	 */
	@RequestMapping("")
	public String showItemList(String name, String order, Model model) {
		List<Item> itemList = service.showItemList(name, order);
		model.addAttribute("itemList", itemList);
		return "item_list_curry";
	}
}
