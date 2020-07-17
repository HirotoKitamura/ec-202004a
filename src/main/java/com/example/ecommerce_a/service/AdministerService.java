package com.example.ecommerce_a.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.Topping;
import com.example.ecommerce_a.form.InsertItemForm;
import com.example.ecommerce_a.form.InsertToppingForm;
import com.example.ecommerce_a.repository.ItemRepository;
import com.example.ecommerce_a.repository.OrderRepository;
import com.example.ecommerce_a.repository.ToppingRepository;

/**
 * 管理者のサービスクラス.
 * 
 * @author hiroto.kitamura
 *
 */
@Service
@Transactional
public class AdministerService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ToppingRepository toppingRepository;
	@Autowired
	private OrderRepository orderRepository;

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
	 * 検索処理を行い、表示する商品情報を３列になるよう加工する．
	 * 
	 * @param name   検索ワード（一覧表示時: 空文字）
	 * @param order  表示順（デフォルト: 価格安い順）
	 * @param status 販売状況
	 * @return 条件に合致する商品リスト
	 */
	public List<List<Item>> show3colItemList(String name, String order, Integer status) {
		List<Item> list = itemRepository.findByFuzzyNameAndStatus(name, order, status);
		List<Item> listIn3items = new ArrayList<>();
		List<List<Item>> listInlist = new ArrayList<>();
		System.out.println("商品数" + list.size());
		for (int i = 0; i < list.size(); i++) {
			if (i % 3 == 0) {
				listIn3items = new ArrayList<Item>();
				listIn3items.add(list.get(i));
				listInlist.add(listIn3items);
			} else {
				listIn3items.add(list.get(i));
			}

		}
		System.out.println("列数" + listInlist.size());
		return listInlist;
	}

	/**
	 * 商品の販売状況を変更する.
	 * 
	 * @param id     商品ID
	 * @param status 販売状況
	 */
	public void setStatus(Integer id, Integer status) {
		itemRepository.updateStatus(id, status);
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
	 * トッピングを全件検索.
	 * 
	 * @return トッピングのリストを販売状態別に集めたリスト.
	 */
	public List<List<Topping>> searchAllToppings() {
		List<List<Topping>> toppingListList = new ArrayList<>();
		toppingListList.add(toppingRepository.findOnSale());
		toppingListList.add(toppingRepository.findSuspended());
		return toppingListList;
	}

	/**
	 * 指定された状態の注文を全取得する.
	 * 
	 * @param status 状態
	 * @return 注文リスト
	 */
	public List<Order> searchByStatus(Integer status) {
		return orderRepository.findByStatus(status);
	}

	/**
	 * トッピングの削除フラグを変更する.
	 * 
	 * @param id      トッピングID
	 * @param deleted 削除フラグ
	 */
	public void setDeleted(Integer id, Boolean deleted) {
		toppingRepository.setDeleted(id, deleted);
	}

	/**
	 * 指定の注文IDのステータスを変更する.
	 * 
	 * @param orderId 注文ID
	 * @param status  変更後の状態
	 */
	public void editStatus(Integer orderId, Integer status) {
		orderRepository.updateStatus(orderId, status);
	}
}
