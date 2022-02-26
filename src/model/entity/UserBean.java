package model.entity;

import java.io.Serializable;

public class UserBean implements Serializable {

	private String userId;		// ユーザーID
	private String pass;		// パスワード
	private int countDwn;		// カウントダウン開始日数
	private int eventGet;		// イベントの直近〇ヶ月分を表示


	public UserBean() {

	}

	public UserBean(String userId, String pass) {

		this.userId = userId;
		this.pass = pass;
		this.countDwn = 30;		// デフォルトは30日
		this.eventGet = 60;		// デフォルトは2ヶ月(60日)

	}

	public String getUserId() {

		return userId;

	}

	public void setUserId(String userId) {

		this.userId = userId;

	}

	public String getPass() {

		return pass;

	}

	public void setPass(String pass) {

		this.pass = pass;

	}

	public int getCountDwn() {

		return countDwn;

	}

	public void setCountDwn(int countDwn) {

		this.countDwn = countDwn;

	}

	public int getEventGet() {

		return eventGet;

	}

	public void setEventGet(int eventGet) {

		this.eventGet = eventGet;

	}

}
