package com.example.ecommerce_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.form.InsertItemInShoppingForm;
import com.example.ecommerce_a.service.ShoppingCartService;

/**
 * ショッピングカートを操作するコントローラ.
 * 
 * @author hyoga.ito
 *
 */
@Controller
@RequestMapping("cartList")
public class ShoppingCartController {
	/**　サービスの参照を注入 */
	@Autowired
	ShoppingCartService service;
	
	/** フォームをセットアップ　 */
	@ModelAttribute
	public InsertItemInShoppingForm setUpForm() {
		return new InsertItemInShoppingForm();
	}
	
	
	/**
	 * ショッピングカートを表示する.
	 * 
	 * @param model リクエストスコープ
	 * @return　ショッピングカート
	 */
	@RequestMapping("")
	public String index(Model model) {
		Order order=service.findByuserIdAndStatus0();
		if(order==null) {
			model.addAttribute("enptyCart", "カートは空です。");
		}else {
			model.addAttribute("order", order);
		}
		return "/cart_list";
	}
	
	/**
	 * ショッピングカートに商品を追加する.
	 * 
	 * @param form 商品情報、個数、サイズ、トッピング
	 * @return リダイレクト：ショッピングカート
	 */
	@RequestMapping("addShoppingCart")
	public String insertShoppingCart(InsertItemInShoppingForm form) {
		service.insertItemIntoShoppingCart(form);
		
		return "redirect:/cartList/";
	}
	
	/**
	 * ショッピングカートから商品を削除する.
	 * 
	 * @param orderItemId 注文商品ID
	 * @return　リダイレクト：ショッピングカート
	 */
	@RequestMapping("deleteItem")
	public String deleteItemFromShoppingCart(int orderItemId) {
		service.deleteItemFromShoppingCart(orderItemId);
		return "redirect:/cartList/";
	}
}
