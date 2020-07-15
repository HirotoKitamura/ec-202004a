package com.example.ecommerce_a.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.repository.ItemRepository;

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
	 * @param name                        検索ワード(一覧表示時は空文字が入る)
	 * @param order(表示順。デフォルトはasc=価格の安い順)
	 * @return 条件に合致する商品のリスト
	 */
	public List<Item> showItemList(String name, String order) {
		return itemRepository.findByFuzzyName(name, order);
	}
	
	/**
	 * 検索処理を行い、表示する商品情報を３列になるよう加工する．
	 * 
	 * @param name 検索ワード（一覧表示時: 空文字）
	 * @param order 表示順（デフォルト: 価格安い順）
	 * @return 条件に合致する商品リスト
	 */
	public List<List<Item>> show3colItemList(String name,String order){
		List<Item> list = showItemList(name, order);
		List<Item> listIn3items = new ArrayList<>(); 
		List<List<Item>> listInlist = new ArrayList<>();
		
		for(int i=0;i<list.size();i++) {
			listIn3items.add(list.get(i));
			
			if(i%3 == 2) {//item3個目
				listInlist.add(listIn3items);
				listIn3items = new ArrayList<Item>();
			}		
		}
		
		if(list.size()%3 !=2) {
			listInlist.add(listIn3items);
		}
		
		return listInlist;
	}
	
	public int getItemHitSize(String name) {
		return itemRepository.itemHitSizeByFuzzyName(name);
	}

	/**
	 * オートコンプリート用にJavaScriptの配列の中身を文字列で作ります.
	 * 
	 * @param itemList 商品一覧
	 * @return オートコンプリート用JavaScriptの配列の文字列
	 */
	public StringBuilder getItemListForAutocomplete(List<Item> itemList) {
		StringBuilder itemListForAutocomplete = new StringBuilder();
		for (int i = 0; i < itemList.size(); i++) {
			if (i != 0) {
				itemListForAutocomplete.append(",");
			}
			Item item = itemList.get(i);
			itemListForAutocomplete.append("\"");
			itemListForAutocomplete.append(item.getName());
			itemListForAutocomplete.append("\"");
		}
		return itemListForAutocomplete;
	}
}
