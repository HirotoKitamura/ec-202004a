package com.example.ecommerce_a.form;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

/**
 * 商品を追加するフォーム.
 * 
 * @author hiroto.kitamura
 *
 */
public class InsertItemForm {
	/**
	 * 商品名.
	 */
	@NotBlank(message = "商品名を入力してください")
	private String name;
	/**
	 * 説明.
	 */
	@NotBlank(message = "説明を入力してください")
	private String description;
	/**
	 * Mサイズの値段.
	 */
	private Integer priceM;
	/**
	 * Lサイズの値段.
	 */
	private Integer priceL;
	/**
	 * 画像.
	 */
	private MultipartFile image;
	/**
	 * 販売状態.
	 * 
	 * 0:販売中 1:売り切れ 2:販売停止中
	 */
	private Integer status;

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
		this.priceM = priceM == null ? 0 : priceM;
	}

	public Integer getPriceL() {
		return priceL;
	}

	public void setPriceL(Integer priceL) {
		this.priceL = priceL == null ? 0 : priceL;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "InsertItemForm [name=" + name + ", description=" + description + ", priceM=" + priceM + ", priceL="
				+ priceL + ", image=" + image + ", status=" + status + "]";
	}

}
