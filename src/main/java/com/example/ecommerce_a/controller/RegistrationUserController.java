package com.example.ecommerce_a.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.form.RegistrationUserForm;
import com.example.ecommerce_a.service.RegistrationUserService;

/**
 * ユーザ登録を行うコントローラ．
 * 
 * @author yuiko.mitsui
 *
 */
@Controller
@RequestMapping("/registration-user")
public class RegistrationUserController {

	@Autowired
	RegistrationUserService registrationuserService;

	@ModelAttribute
	public RegistrationUserForm setupRegistrationUserForm() {
		return new RegistrationUserForm();
	}

	@Autowired
	private HttpSession session;

	/**
	 * ユーザ登録画面を表示する．
	 * 
	 * @return ユーザ登録画面
	 */
	@RequestMapping("")
	public String index() {
		if (session.getAttribute("user") != null) {
			return "redirect:/showItemList";
		}
		return "register_user";
	}

	/**
	 * ユーザ登録を行う．
	 * 
	 * @return ユーザ登録画面(仮実装)
	 */
	@RequestMapping("/insert-user")
	public String insertUser(@Validated RegistrationUserForm form, BindingResult result) {

		if (!"".equals(result.getFieldValue("zipcode"))) {// 郵便番号notnull
			String zip = (String) result.getFieldValue("zipcode");
			if (isErrorZipcodeFormat(zip)) {// 形式判定エラー
				result.rejectValue("zipcode", "", "郵便番号はXXX-XXXXの形式で入力してください");
			}
		}

		if (!"".equals(result.getFieldValue("telephone"))) {// 電話notnull
			String tel = (String) result.getFieldValue("telephone");
			if (isErrorTelFormat(tel)) {// 形式判定エラー
				result.rejectValue("telephone", "", "電話番号はXXXX-XXXX-XXXXの形式で入力してください");
			}
		}

		if (!"".equals(result.getFieldValue("password"))) {// パスワードnotnull
			String pass = (String) result.getFieldValue("password");
			boolean flag = true;
			if (pass.length() < 8 || 16 < pass.length()) {// 8以上16以下判定＆エラー
				result.rejectValue("password", "", "パスワードは８文字以上１６文字以内で設定してください");
				flag = false;
			}

			if (isErrorPasswordFormat(pass) && flag) {// 形式チェック
				System.out.println("haittayo~");
				result.rejectValue("password", "", "英大文字、英小文字、数字すべてを使用してください");
			}
		}

		if (!"".equals(result.getFieldValue("checkpassword")))

		{// 確認パスワードnotnull
			if ("".equals(result.getFieldValue("password"))) {// パスワードnull
				result.rejectValue("checkpassword", "", "パスワードが空欄です");

			} else if (!form.getPassword().equals(form.getCheckpassword())) {// パスワード不一致
				result.rejectValue("checkpassword", "", "パスワードと確認用パスワードが不一致です");

			}
		}

		if (registrationuserService.isExistEmail(form.getEmail())) {// 既存ユーザ
			result.rejectValue("email", "", "そのメールアドレスはすでに使われています");
		}

		if (result.hasErrors()) {// 他バリデーション
			return index();
		}

		// insert
		registrationuserService.insertUser(form);

		return "redirect:/toLogin?from=reg";

	}

	/**
	 * 電話番号XXXX(2-4文字)-XXXX(2-4文字)-XXXX(4文字)形式との一致を確認し、エラーの発生を判定します．
	 * 
	 * @param matchval 電話番号
	 * @return エラー時:true/正常時:false
	 */
	public boolean isErrorTelFormat(String matchval) {
		Pattern p = Pattern.compile("^[0-9]{2,4}-[0-9]{2,4}-[0-9]{4}$");
		Matcher m = p.matcher(matchval);
		boolean b = m.matches();

		return !b;
	}

	/**
	 * 郵便番号XXX-XXXX形式の一致を確認し、エラーの発生を判定します．
	 * 
	 * @param matchval 郵便番号
	 * @return エラー時:true/正常時:false
	 */
	public boolean isErrorZipcodeFormat(String matchval) {
		Pattern p = Pattern.compile("^[0-9]{3}-[0-9]{4}$");
		Matcher m = p.matcher(matchval);
		boolean b = m.matches();

		return !b;
	}

	/**
	 * パスワードが英大文字、小文字、数字全てを使用しているか確認し、エラーの発生を判定します．
	 * 
	 * @param matchval パスワード
	 * @return
	 */
	public boolean isErrorPasswordFormat(String matchval) {
		Pattern p = Pattern.compile("^.*[a-z].*$");
		Matcher m = p.matcher(matchval);
		boolean b1 = m.matches();

		p = Pattern.compile("^.*[A-Z].*$");
		m = p.matcher(matchval);
		boolean b2 = m.matches();

		p = Pattern.compile("^.*[0-9].*$");
		m = p.matcher(matchval);
		boolean b3 = m.matches();

		boolean b = false;
		if (b1 && b2 && b3) {// true
			b = true;
		}

		return !b;
	}

}
