package com.example.ecommerce_a.domain;

/**
 * クレジットカードの認証APIから返ってくるレスポンスのドメイン.
 * 
 * @author hiroto.kitamura
 *
 */
public class CreditCardResponse {
	/**
	 * 認証結果ステータス.
	 * 
	 * 認証成功ならsuccess、失敗ならerror
	 */
	private String status;
	/**
	 * 認証結果メッセージ.
	 */
	private String message;
	/**
	 * 認証結果メッセージコード.
	 */
	private String error_code;

	public CreditCardResponse() {
		// TODO Auto-generated constructor stub
	}

	public CreditCardResponse(String status, String message, String error_code) {
		super();
		this.status = status;
		this.message = message;
		this.error_code = error_code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	@Override
	public String toString() {
		return "CreditCardResponse [status=" + status + ", message=" + message + ", error_code=" + error_code + "]";
	}

}
