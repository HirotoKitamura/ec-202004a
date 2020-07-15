package com.example.ecommerce_a.form;

import javax.validation.constraints.NotBlank;

/**
 * トッピングを追加するフォーム.
 * 
 * @author hiroto.kitamura
 *
 */
public class InsertToppingForm {
	/**
	 * 商品名.
	 */
	@NotBlank(message = "商品名を入力してください")
	private String name;
	/**
	 * Mサイズの値段.
	 */
	private Integer priceM;
	/**
	 * Lサイズの値段.
	 */
	private Integer priceL;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPriceM() {
		return priceM;
	}

	public void setPriceM(Integer priceM) {
		this.priceM = priceM == null ? 0 : priceM;
	}

	public Integer getPriceL() {
		return priceL;
	}

	public void setPriceL(Integer priceL) {
		this.priceL = priceL == null ? 0 : priceL;
	}

	@Override
	public String toString() {
		return "InsertToppingForm [name=" + name + ", priceM=" + priceM + ", priceL=" + priceL + "]";
	}

}
