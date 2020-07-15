package com.example.ecommerce_a.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Topping;
import com.example.ecommerce_a.form.InsertItemForm;
import com.example.ecommerce_a.form.InsertToppingForm;
import com.example.ecommerce_a.repository.ItemRepository;
import com.example.ecommerce_a.repository.ToppingRepository;

/**
 * 管理者のサービスクラス.
 * 
 * @author hiroto.kitamura
 *
 */
@Service
public class AdministerService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * 商品を追加する.
	 * 
	 * @param form フォームに入力された商品情報.
	 * @throws IOException 例外
	 */
	public void insertItem(InsertItemForm form) throws IOException {
		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		MultipartFile image = form.getImage();
		String fileName = image.getOriginalFilename();
		String base64File = Base64.getEncoder().encodeToString(image.getBytes());
		if (fileName.endsWith(".jpg")) {
			base64File = "data:image/jpeg;base64," + base64File;
		} else if (fileName.endsWith(".png")) {
			base64File = "data:image/png;base64," + base64File;
		} else {
			throw new IOException();
		}
		item.setImagePath(base64File);
		itemRepository.insertItem(item);
	}

	/**
	 * 商品に削除フラグを立てる.
	 * 
	 * @param id 商品ID
	 */
	public void deleteItem(Integer id) {
		itemRepository.deleteItem(id);
	}

	/**
	 * トッピングを追加.
	 * 
	 * @param form フォームに入力されたトッピング情報
	 */
	public void insertTopping(InsertToppingForm form) {
		Topping topping = new Topping();
		BeanUtils.copyProperties(form, topping);
		toppingRepository.insertTopping(topping);
	}

	/**
	 * トッピングを削除.
	 * 
	 * @param id トッピングID
	 */
	public void deleteTopping(Integer id) {
		toppingRepository.deleteTopping(id);
	}
}
