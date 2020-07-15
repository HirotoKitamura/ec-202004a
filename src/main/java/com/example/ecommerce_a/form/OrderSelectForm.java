package com.example.ecommerce_a.form;

/**
 * 一覧表示の並び順を保持するためのフォーム．
 * 
 * @author yuiko.mitsui
 *
 */
public class OrderSelectForm {

	/** 並び順のvalue */
	private String order;

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Order [order=" + order + "]";
	}

}
