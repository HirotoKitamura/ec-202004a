package com.example.ecommerce_a.form;

/**
 * ユーザ登録情報のフォーム．
 * 
 * @author yuiko.mitsui
 *
 */
public class RegistrationUserForm {

	/**
	 * ユーザの名前．
	 */
	private String name;
	/**
	 * メールアドレス．
	 */
	private String email;
	/**
	 * 郵便番号．
	 */
	private String zipcode;
	/**
	 * 住所．
	 */
	private String address;
	/**
	 * 電話番号．
	 */
	private String telephone;
	/**
	 * パスワード．
	 */
	private String password;
	/**
	 * 確認用パスワード．
	 */
	private String checkpassword;

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

	@Override
	public String toString() {
		return "RegistrationUserForm [name=" + name + ", email=" + email + ", zipcode=" + zipcode + ", address="
				+ address + ", telephone=" + telephone + ", password=" + password + ", checkpassword=" + checkpassword
				+ "]";
	}

}
