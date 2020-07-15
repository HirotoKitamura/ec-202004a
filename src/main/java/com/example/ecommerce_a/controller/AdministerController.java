package com.example.ecommerce_a.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce_a.form.InsertItemForm;
import com.example.ecommerce_a.form.InsertToppingForm;
import com.example.ecommerce_a.service.AdministerService;

/**
 * 管理者のコントローラークラス.
 * 
 * @author hiroto.kitamura
 *
 */
@Controller
@RequestMapping("administer")
public class AdministerController {
	@Autowired
	private AdministerService service;

	@ModelAttribute
	private InsertItemForm setUpItemForm() {
		return new InsertItemForm();
	}

	@ModelAttribute
	private InsertToppingForm setUpToppingForm() {
		return new InsertToppingForm();
	}

	/**
	 * 管理者のトップ画面を表示する.
	 * 
	 * @return 管理者トップ画面
	 */
	@RequestMapping("")
	public String index() {
		return "administer";
	}

	/**
	 * 商品登録画面を表示する.
	 * 
	 * @return 商品登録画面
	 */
	@RequestMapping("toRegisterItem")
	public String toRegisterItem() {
		return "register_item";
	}

	/**
	 * 商品を追加する.
	 * 
	 * @param form   商品のフォーム.
	 * @param result 入力値チェック結果
	 * @return 管理者トップ画面
	 * @throws IOException
	 */
	@RequestMapping("registerItem")
	public String registerItem(@Validated InsertItemForm form, BindingResult result) throws IOException {
		MultipartFile image = form.getImage();
		try {
			String fileName = image.getOriginalFilename();
			if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
				result.rejectValue("image", "", "拡張子は.jpgか.pngのみに対応しています");
			}
		} catch (Exception e) {
			result.rejectValue("image", "", "拡張子は.jpgか.pngのみに対応しています");
		}
		if (result.hasErrors()) {
			return "register_item";
		}
		service.insertItem(form);
		return "administer";
	}

	/**
	 * トッピング登録画面を表示する.
	 * 
	 * @return トッピング登録画面
	 */
	@RequestMapping("toRegisterTopping")
	public String toRegisterTopping() {
		return "register_topping";
	}

	/**
	 * トッピングを追加する.
	 * 
	 * @param form   入力フォーム
	 * @param result 入力値チェック結果
	 * @return 管理者トップ画面
	 */
	@RequestMapping("registerTopping")
	public String registerTopping(@Validated InsertToppingForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "register_item";
		}
		service.insertTopping(form);
		return "administer";
	}
}
