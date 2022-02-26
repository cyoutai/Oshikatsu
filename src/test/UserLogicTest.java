package test;

import model.entity.UserBean;
import model.model.UserLogic;

public class UserLogicTest {

	public static void main(String[] args) {

		// 重複ユーザーIDのチェック
		// userIdCheckLgicTest();

		// ユーザ登録
		// userRegisterLogicTest();

		// ユーザー認証
		// userLoginLogicTest();

		// ユーザー情報の更新
		// userUpdateLogicTest();

		// 全ユーザーの削除
		deleteUserAll();

	}

	// 重複ユーザーIDのチェック
	public static void userIdCheckLgicTest() {

		UserLogic uL = new UserLogic();

		boolean rtnBool1 = uL.userIdCheckLogic("testuer");
		boolean rtnBool2 = uL.userIdCheckLogic("testuer2");

		System.out.println("■重複ユーザーのチェック");
		System.out.println("ユーザーIDが存在する（true）：" + rtnBool1);
		System.out.println("ユーザーIDが存在しない（false）：" + rtnBool2);
		System.out.println();

	}

	// ユーザーIDの登録
	public static void userRegisterLogicTest() {

		UserBean user1 = new UserBean("testuser2", "testuser2");
		UserBean user2 = new UserBean("testuer", "testuser3");

		boolean rtnBool1 = false;
		boolean rtnBool2 = false;

		UserLogic uL = new UserLogic();

		rtnBool1 = uL.userRegisterLogic(user1);
		rtnBool2 = uL.userRegisterLogic(user2);

		System.out.println("■ユーザー登録");
		System.out.println("ユーザー登録できた：" + rtnBool1);
		System.out.println("ユーザー登録失敗した：" + rtnBool2);
		System.out.println();

	}

	// ログイン認証
	public static void userLoginLogicTest() {

		UserBean user1 = new UserBean("testuser2", "testuser2");
		UserBean user2 = new UserBean("testuer", "testuser3");

		UserLogic uL = new UserLogic();

		UserBean rtnUser1 = uL.userLoginLogic(user1);
		UserBean rtnUser2 = uL.userLoginLogic(user2);

		System.out.println("■ログイン認証");

		System.out.println("rtnUser1（正しい）：");
		if(rtnUser1 != null) {

			System.out.println("＜ログイン成功＞");
			System.out.print(rtnUser1.getUserId() + ", ");
			System.out.print(rtnUser1.getPass() + ", ");
			System.out.print(rtnUser1.getCountDwn() + ", ");
			System.out.print(rtnUser1.getEventGet());
			System.out.println();

		} else {

			System.out.println("＜ログイン失敗＞");

		}

		System.out.println("rtnUser2（パスワード違い）：");
		if(rtnUser2 != null) {

			System.out.println("＜ログイン成功＞");
			System.out.print(rtnUser2.getUserId() + ", ");
			System.out.print(rtnUser2.getPass() + ", ");
			System.out.print(rtnUser2.getCountDwn() + ", ");
			System.out.print(rtnUser2.getEventGet() + ", ");

		} else {

			System.out.println("＜ログイン失敗＞");

		}

	}

	// ユーザー情報の更新
	public static void userUpdateLogicTest() {

		// ユーザー情報の生成
		UserBean user1 = new UserBean();

		user1.setUserId("testuser3");
		user1.setPass("tes3");
		user1.setCountDwn(10);
		user1.setEventGet(1);

		UserBean user2 = new UserBean();

		user2.setUserId("testuser4");
		user2.setPass("tes4");
		user2.setCountDwn(10);
		user2.setEventGet(1);

		UserLogic uL = new UserLogic();

		boolean rtnBool1 = uL.userUpdateLogic(user1);
		boolean rtnBool2 = uL.userUpdateLogic(user2);

		System.out.println("■ユーザー情報の更新");
		System.out.println("正しい更新情報：" + rtnBool1);
		System.out.println("正しくない更新情報：" + rtnBool2);

	}

	// 全ユーザー削除（トランザクション処理していない）
	public static void userDeleteLogicTest() {

		UserLogic uL = new UserLogic();

		boolean rtnBool = uL.userDeleteLogic("testuser3");

		System.out.println("■ユーザーの削除");
		System.out.println("存在するユーザーの削除：" + rtnBool);
	}

	public static void deleteUserAll() {

		UserLogic uL = new UserLogic();

		boolean rtnBool = uL.deleteUserAll("testuer5");

		System.out.println("■ユーザーの削除");
		System.out.println("存在するユーザーの削除：" + rtnBool);

	}

}
