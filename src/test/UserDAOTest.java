package test;

import java.sql.SQLException;

import model.dao.UserDAO;
import model.entity.UserBean;

public class UserDAOTest {

	public static void main(String[] args) {

		// ユーザーIDの重複をチェック
		userIdCheckTest();

		// ユーザー登録
		//userRegisterTest();

		// ログイン認証
		userLoginTest();

		// ユーザー情報の更新
		userUpdateTest();

		// ユーザー情報の削除
		userDeleteTest();

	}

	// ユーザーIDの重複をチェック
	public static void userIdCheckTest() {

		UserDAO cDao = new UserDAO();

		boolean b1 = false;		// 重複あり（true）
		boolean b2 = true;			// 重複無し（false）

		try {

			// 重複IDがある
			b1 = cDao.userIdCheck("testuer");
			// 重複IDがない
			b2 = cDao.userIdCheck("testuer2");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■ユーザーIDの重複をチェック");
		System.out.println("重複ありは「true」、重複無しは「false」");
		System.out.println("存在するユーザーIDを入力：" + b1);
		System.out.println("存在しないユーザーIDを入力：" + b2);
		System.out.println();

	}

	// ユーザー登録を行う
	public static void userRegisterTest() {

		// ユーザー情報の生成
		UserBean user = new UserBean("testuser3", "testuser3");

		UserDAO dao = new UserDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.userRegister(user);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■ユーザー登録");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// ログイン認証
	public static void userLoginTest() {

		// ユーザー情報の生成（正しいログイン）
		UserBean user1 = new UserBean();

		user1.setUserId("testuer");
		user1.setPass("test");

		// 戻り値受け取り用
		UserBean userRtn1 = new UserBean();

		// ユーザー情報の生成（パスワード違い）
		UserBean user2 = new UserBean();

		user2.setUserId("testuer");
		user2.setPass("testuser");

		// 戻り値受け取り用
		UserBean userRtn2 = new UserBean();

		UserDAO dao = new UserDAO();

		try {

			// 正しいログイン（ユーザー情報を取得）
			userRtn1 = dao.userLogin(user1);

			// パスワード違い（nullが戻る）
			userRtn2 = dao.userLogin(user2);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■ログイン認証");

		if(userRtn1 != null) {

			System.out.println("正しいログイン：" + userRtn1.getUserId() + ", " + userRtn1.getPass() + ", " + userRtn1.getCountDwn() + ", " + userRtn1.getEventGet());

		} else {

			System.out.println("正しいログインはnullでした（ログイン認証失敗）");

		}

		if(userRtn2 != null) {

			System.out.println("パスワード違い：" + userRtn2.getUserId() + ", " + userRtn2.getPass() + ", " + userRtn2.getCountDwn() + ", " +  userRtn2.getEventGet());

		} else {

			System.out.println("パスワード違いはnullでした（ログイン認証成功）");

		}

		System.out.println();


	}

	// ユーザー情報更新
	public static void userUpdateTest() {

		// ユーザー情報の生成
		UserBean user = new UserBean();

		user.setUserId("testuser3");
		user.setPass("tes3");
		user.setCountDwn(10);
		user.setEventGet(1);

		UserDAO dao = new UserDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.userUpdate(user);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■ユーザー情報の更新");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// ユーザーの削除
	public static void userDeleteTest() {

		UserDAO dao = new UserDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.userDelete("testuser3");

		} catch (ClassNotFoundException | SQLException e) {


			e.printStackTrace();

		}

		System.out.println("■ユーザー情報の削除");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

}
