package com.example.ecommerce_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.form.InsertItemInShoppingForm;
import com.example.ecommerce_a.service.ShoppingCartService;

@Controller
@RequestMapping("cartList")
public class ShoppingCartController {
	
	@Autowired
	ShoppingCartService service;
	
	@ModelAttribute
	public InsertItemInShoppingForm setUpForm() {
		return new InsertItemInShoppingForm();
	}
	

	
	@RequestMapping("")
	public String index(Model model) {
		Order order=service.findByuserIdAndStatus0();
		model.addAttribute("order", order);
		return "/cart_list";
	}
	
	@RequestMapping("addShoppingCart")
	public String insertShoppingCart(InsertItemInShoppingForm form) {
		System.out.println(form);
		service.insertItemIntoShoppingCart(form);
		
		return "redirect:/cartList/";
	}
	
	@RequestMapping("delete")
	public String deleteItemFromShoppingCart(int orderItemId) {
		service.deleteItemFromShoppingCart(orderItemId);
		return "redirect:/cartList/";
	}
}
