package com.example.ecommerce_a.domain;

import java.util.List;

/**
 * 注文商品ドメイン.
 * 
 * @author hyoga.ito
 *
 */
public class OrderItem {
	/** 注文商品ID */
	private Integer id;
	/** 商品ID */
	private Integer itemId;
	/** 注文ID */
	private Integer orderId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private Character size;
	/** 商品 */
	private Item item;
	/** 注文トッピングリスト */
	private List<OrderTopping> orderToppingList;

	/**
	 * 小計を計算.
	 * 
	 * @return 小計
	 */
	public Integer getSubTotal() {
		int subTotal = 0;
		
		int priceM=0;
		int priceL=0;
		
		if(orderToppingList.size()!=0) {
			priceM=orderToppingList.get(0).getTopping().getPriceM();
			priceL=orderToppingList.get(0).getTopping().getPriceL();
		}
		
		
		if (size == 'M') {
			subTotal = (item.getPriceM()+(orderToppingList.size()*priceM)) * quantity;
			
		} else {
			subTotal = (item.getPriceL()+(orderToppingList.size()*priceL)) * quantity;
	
		}
		
		return subTotal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantitiy) {
		this.quantity = quantitiy;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}

	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantitiy=" + quantity
				+ ", size=" + size + ", item=" + item + ", orderToppingList=" + orderToppingList + "]";
	}

}
