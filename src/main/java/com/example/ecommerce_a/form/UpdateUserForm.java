package com.example.ecommerce_a.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UpdateUserForm {
	/** ユーザの名前． */
	@NotBlank(message = "名前を入力してください")
	private String name;

	/** メールアドレス． */
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が不正です")
	private String email;

	/** 郵便番号． */
	@Pattern(regexp = "[0-9]{3}-[0-9]{4}" , message ="郵便番号はXXX-XXXXの形式で入力してください")
	private String zipcode;

	/** 住所． */
	@NotBlank(message = "住所を入力してください")
	private String address;

	/** 電話番号． */
	@Pattern(regexp = "[0-9]{2,4}-[0-9]{3,4}-[0-9]{4}" , message ="電話番号はXXXX-XXXX-XXXXの形式で入力してください")
	private String telephone;

	/** 変更後パスワード． */
	private String password;

	/** 変更後確認用パスワード． */
	private String checkpassword;
	
	/** 変更前パスワード． */
	private String oldPassword;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckpassword() {
		return checkpassword;
	}

	public void setCheckpassword(String checkpassword) {
		this.checkpassword = checkpassword;
	}
	
	

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@Override
	public String toString() {
		return "RegistrationUserForm [name=" + name + ", email=" + email + ", zipcode=" + zipcode + ", address="
				+ address + ", telephone=" + telephone + ", password=" + password + ", checkpassword=" + checkpassword
				+ "]";
	}
	
	
}
