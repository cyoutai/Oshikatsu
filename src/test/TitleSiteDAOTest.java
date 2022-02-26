package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.TitleSiteDAO;
import model.entity.TitleSite;

public class TitleSiteDAOTest {

	public static void main(String[] args) {

		// 公式サイト情報の登録
		// titleSiteRegisterTest();

		// 公式サイト情報を取得
		getTitleSiteTest();

		// 公式サイト情報を更新
		// titleSiteUpdateTest();

		// 作品情報削除による公式サイトの削除
		// titleSiteDeleteTest();

		// ユーザー削除による公式サイトの削除
		// userTitleSiteDeleteTest();

		// 公式サイト情報1件の削除
		titleSiteOneDeleteTest();

	}

	// 公式サイト情報の登録
	public static void titleSiteRegisterTest() {

		TitleSiteDAO dao = new TitleSiteDAO();

		// 戻り値の設定
		int rtn1 = 0;
		int rtn2 = 0;

		try {

			rtn1 = dao.titleSiteRegister(3, "http://www.test.com", "testuser2");
			rtn2 = dao.titleSiteRegister(4, "http://www.test.com", "testuser2");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■公式サイト登録");
		System.out.println("処理件数：" + (rtn1 + rtn2));
		System.out.println();

	}

	// 公式サイト情報を取得
	public static void getTitleSiteTest() {

		// 取得するサイト一覧の生成
		List<TitleSite> siteList = new ArrayList<TitleSite>();

		TitleSiteDAO dao = new TitleSiteDAO();

		try {

			siteList = dao.getTitleSite(2);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■公式サイト一覧を取得");

		for (TitleSite ts : siteList) {

			System.out.println(ts.getSiteId() + ", " + ts.getSiteUrl());

		}

		System.out.println();

	}

	// 公式サイトの更新
	public static void titleSiteUpdateTest() {

		// 公式サイト情報を生成
		TitleSite site = new TitleSite();

		site.setSiteId(6);
		site.setSiteUrl("http://www.testsite.co.jp");

		TitleSiteDAO dao = new TitleSiteDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.titleSiteUpdate(site);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■公式サイトの更新");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// 作品情報削除による公式サイト情報の削除
	public static void titleSiteDeleteTest() {

		TitleSiteDAO dao = new TitleSiteDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.titleSiteDelete(3);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■公式サイトの削除");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// ユーザー削除による公式サイト情報の削除
	public static void userTitleSiteDeleteTest() {

		TitleSiteDAO dao = new TitleSiteDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.userTitleSiteDelete("testuser2");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■ユーザーの削除");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// 公式サイト情報1件の削除
	public static void titleSiteOneDeleteTest() {

		TitleSiteDAO dao = new TitleSiteDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.titleSiteOneDelete(8);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■公式サイト1件の削除");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

}
