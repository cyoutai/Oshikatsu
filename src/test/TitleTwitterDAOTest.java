package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.TitleTwitterDAO;
import model.entity.TitleTwitter;

public class TitleTwitterDAOTest {

	public static void main(String[] args) {

		// Twitter情報の登録
		// titleTwitterRegisterTest();

		// Twitter情報の取得
		getTitleTwitterTest();

		// Twitter情報の更新
		// titleTwitterUpdateTest();

		// 作品情報削除によるTwitter情報の削除
		// titleTwitterDeleteTest();

		// ユーザーの削除によるTwitter情報の削除
		// userTitleTwitterDeleteTest();

		// Twitter情報1件削除
		titleTwitterOneDeleteTest();


	}

	// Twitter情報の登録
	public static void titleTwitterRegisterTest() {

			TitleTwitterDAO dao = new TitleTwitterDAO();

			// 戻り値の設定
			int rtn1 = 0;
			int rtn2 = 0;

			try {

				rtn1 = dao.titleTwitterRegister(3, "@testid", "testuser2");
				rtn2 = dao.titleTwitterRegister(4, "@testid2", "testuser2");

			} catch (ClassNotFoundException | SQLException e) {

				e.printStackTrace();

			}

			System.out.println("■Twitter登録");
			System.out.println("処理件数：" + (rtn1 + rtn2));
			System.out.println();

		}

	// Twitter情報を取得
	public static void getTitleTwitterTest() {

			// 取得するサイト一覧の生成
			List<TitleTwitter> twitterList = new ArrayList<TitleTwitter>();

			TitleTwitterDAO dao = new TitleTwitterDAO();

			try {

				twitterList = dao.getTitleTwitter(2);

			} catch (ClassNotFoundException | SQLException e) {

				e.printStackTrace();

			}

			System.out.println("■Twitter一覧を取得");

			for (TitleTwitter tt : twitterList) {

				System.out.println(tt.gettTwitterId() + ", " + tt.getTwitterId());

			}

			System.out.println();

		}

	// 公式サイトの更新
	public static void titleTwitterUpdateTest() {

		// 公式サイト情報を生成
		TitleTwitter twitter = new TitleTwitter();

		twitter.settTwitterId(6);
		twitter.setTwitterId("@testTwitterId");

		TitleTwitterDAO dao = new TitleTwitterDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.titleTwitterUpdate(twitter);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■Twitterの更新");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// 作品情報削除によるTwitter情報の削除
	public static void titleTwitterDeleteTest() {

		TitleTwitterDAO dao = new TitleTwitterDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.titleTwitterDelete(3);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■Twitterの削除");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// ユーザー削除によるTwitter情報の削除
	public static void userTitleTwitterDeleteTest() {

		TitleTwitterDAO dao = new TitleTwitterDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.userTitleTwitterDelete("testuser2");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■ユーザーの削除");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// Twitter情報1件の削除
	public static void titleTwitterOneDeleteTest() {

		TitleTwitterDAO dao = new TitleTwitterDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.titleTwitterOneDelete(8);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■Twitter1件の削除");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

}
