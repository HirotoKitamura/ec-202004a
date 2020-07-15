package com.example.ecommerce_a.form;

import java.util.List;

/**
 * 注文する商品情報を入力するフォーム.
 * 
 * @author hyoga.ito
 *
 */
public class InsertItemInShoppingForm {
	/**	商品ID(hidden) */
	private Integer itemId;
	/**	数量 */
	private Integer quantity;
	/**　サイズ */
	private Character size;
	/** トッピングId一覧（セレクトボックス） */
	private List<Integer> toppingIds;
	/** 合計金額 */
	private Integer totalPrice;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public List<Integer> getToppingIds() {
		return toppingIds;
	}

	public void setToppingIds(List<Integer> toppingIds) {
		this.toppingIds = toppingIds;
	
	}
	
	

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "InsertItemInShoppingForm [itemId=" + itemId + ", quantity=" + quantity + ", size=" + size
				+ ", toppingIds=" + toppingIds + "]";
	}

}
