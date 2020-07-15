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
	@NotBlank(message = "価格を入力してください")
	private Integer priceM;
	/**
	 * Lサイズの値段.
	 */
	@NotBlank(message = "価格を入力してください")
	private Integer priceL;
	/**
	 * 画像.
	 */
	private MultipartFile image;
	/**
	 * 削除フラグ.
	 */
	private boolean deleted;

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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "InsertItemForm [name=" + name + ", description=" + description + ", priceM=" + priceM + ", priceL="
				+ priceL + ", image=" + image + ", deleted=" + deleted + "]";
	}

}
