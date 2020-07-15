package com.example.ecommerce_a.form;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 注文情報を受けとるフォームクラス.
 * 
 * @author junpei.azuma
 *
 */
public class OrderForm {
	private Integer status;
	@NotBlank(message="名前を入力してください")
	private String destinationName;
	@NotBlank(message="メールアドレスを入力してください")
	@Email(message="メールアドレスの形式が不正です")
	private String destinationEmail;
	@NotBlank(message="郵便番号を入力してください")
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}$", message="郵便番号はxxx-xxxxの形で入力してください")
	private String destinationZipcode;
	@NotBlank(message="住所を入力してください")
	private String destinationAddress;
	@NotBlank(message="電話番号を入力してください")
	@Pattern(regexp="^[0-9]{2,4}-[0-9]{2,4}-[0-9]{4}$", message="- は必須です")
	private String destinationTel;
	private Date deliveryDate;
	private Integer deliveryTime;
	private Integer paymentMethod;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getDestinationEmail() {
		return destinationEmail;
	}
	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}
	public String getDestinationZipcode() {
		return destinationZipcode;
	}
	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}
	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	public String getDestinationTel() {
		return destinationTel;
	}
	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}
	public Integer getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Integer getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	@Override
	public String toString() {
		return "OrderForm [status=" + status + ", destinationName=" + destinationName + ", destinationEmail="
				+ destinationEmail + ", destinationZipcode=" + destinationZipcode + ", destinationTel=" + destinationTel
				+ ", deliveryTime=" + deliveryTime + ", paymentMethod=" + paymentMethod + "]";
	}
	
	
}
