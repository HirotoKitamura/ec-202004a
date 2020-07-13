package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.repository.ItemRepository;
import com.example.ecommerce_a.repository.ToppingRepository;

/**
 * 商品詳細を表示するサービスクラス.
 * 
 * @author junpei.azuma
 *
 */
@Service
public class ShowItemDetailService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ToppingRepository toppingRepository;
	
	
	/**
	 * 取得した商品にトッピングリストをセットする.
	 * 
	 * @param itemID 取得する商品のid
	 * @return 表示する商品情報
	 */
	public Item showItemDetail(Integer itemId) {
		Item item = itemRepository.load(itemId);
		item.setToppingList(toppingRepository.findAll());
		return item;
	}
}
