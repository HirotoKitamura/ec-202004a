package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_a.domain.Item;

/**
 * 商品一覧表示と検索を行うサービスクラス.
 * 
 * @author junpei.azuma
 *
 */
@Service
public class ShowItemListService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 商品一覧表示と検索処理を行うサービスクラス.
	 * 
	 * @param name 検索ワード(一覧表示時は空文字が入る)
	 * @param order(表示順。デフォルトはasc=価格の安い順)
	 * @return 条件に合致する商品のリスト
	 */
	public List<Item> showItemList(String name, String order) {
		return itemRepository.findByFuzzyName(name, order);
	}
}
