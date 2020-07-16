package com.example.ecommerce_a.domain;

import java.util.List;

/**
 * 商品情報ドメイン.
 * 
 * @author hyoga.ito
 *
 */
public class Item {
	/** 商品ID */
	private Integer id;
	/** 商品名 */
	private String name;
	/** 商品説明 */
	private String description;
	/** Mサイズの値段 */
	private Integer priceM;
	/** Lサイズの値段 */
	private Integer priceL;
	/** 商品画像 */
	private String imagePath;
	/**
	 * 販売状況
	 * 
	 * 0:販売中 1:売り切れなど一時的に停止中 2:販売終了
	 */
	private Integer status;
	/** トッピングリスト */
	private List<Topping> toppingList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriceM() {
		return priceM;
	}

	public void setPriceM(Integer priceM) {
		this.priceM = priceM;
	}

	public Integer getPriceL() {
		return priceL;
	}

	public void setPriceL(Integer priceL) {
		this.priceL = priceL;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Topping> getToppingList() {
		return toppingList;
	}

	public void setToppingList(List<Topping> toppingList) {
		this.toppingList = toppingList;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", priceM=" + priceM + ", priceL="
				+ priceL + ", imagePath=" + imagePath + ", status=" + status + ", toppingList=" + toppingList + "]";
	}

}
