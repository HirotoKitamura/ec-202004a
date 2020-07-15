package com.example.ecommerce_a.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.form.InsertItemForm;
import com.example.ecommerce_a.form.InsertToppingForm;
import com.example.ecommerce_a.service.AdministerService;
import com.example.ecommerce_a.service.ShowItemListService;

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
	private AdministerService adminService;
	@Autowired
	private ShowItemListService itemService;

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
		adminService.insertItem(form);
		return "redirect:/administer";
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
			return "register_topping";
		}
		adminService.insertTopping(form);
		return "redirect:/administer";
	}

	/**
	 * 商品削除画面を表示する.
	 * 
	 * @return 商品削除画面
	 */
	@RequestMapping("toDeleteItem")
	public String toDeleteItem(String name, String order, Model model) {
		int itemhitSize = itemService.getItemHitSize(name);
		List<List<Item>> itemList = itemService.show3colItemList(name, order);

		if (itemhitSize == 0) {
			model.addAttribute("message", "該当する商品がありません");
			itemList = itemService.show3colItemList("", order);
		}
		// オートコンプリート用にJavaScriptの配列の中身を文字列で作ってスコープへ格納
		StringBuilder itemListForAutocomplete = itemService
				.getItemListForAutocomplete(itemService.showItemList(name, order));
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);

		model.addAttribute("itemList", itemList);
		return "delete_item";
	}

	@RequestMapping("deleteItem")
	public String deleteItem(Integer id) {
		adminService.deleteItem(id);
		return "redirect:/administer/toDeleteItem";
	}
}
