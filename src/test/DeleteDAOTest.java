package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.TransactionDAO;
import model.entity.TitleBean;
import model.entity.TitleSite;
import model.entity.TitleTwitter;

public class DeleteDAOTest {

	public static void main(String[] args) {

		// 全ユーザーの削除
		// deleteUserAllTest();

		// 作品登録
		// titleRegisterAllTest();

		// 作品の表示・非表示の更新
		// titleOnOffUpdateTest();

		// 作品情報の更新
		// titleUpdateAllTest();

		// 作品情報の削除
		titleDeleteAllTest();

	}

	// ユーザー削除
	public static void deleteUserAllTest() {

		boolean rtnBool = false;

		System.out.println("DAOの生成");
		TransactionDAO dao = new TransactionDAO();

		try {

			System.out.println("DAOの実行");
			rtnBool = dao.deleteUserAll("testuer5");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("削除結果：" + rtnBool);

	}

	// 作品情報の登録
	public static void titleRegisterAllTest() {

		// テストデータの生成（サイト情報）
		List<TitleSite> tSites = new ArrayList<TitleSite>();

		TitleSite tSite1 = new TitleSite();
		tSite1.setSiteUrl("https://test.com");

		TitleSite tSite2 = new TitleSite();
		tSite2.setSiteUrl("https://test2.com");

		tSites.add(tSite1);
		tSites.add(tSite2);

		// テストデータの生成（Twitter情報）
		List<TitleTwitter> tTwitteres = new ArrayList<TitleTwitter>();

		TitleTwitter tTwitter1 = new TitleTwitter();
		tTwitter1.setTwitterId("@test");

		TitleTwitter tTwitter2 = new TitleTwitter();
		tTwitter2.setTwitterId("@test2");

		TitleTwitter tTwitter3 = new TitleTwitter();
		tTwitter3.setTwitterId("@test3");

		tTwitteres.add(tTwitter1);
		tTwitteres.add(tTwitter2);
		tTwitteres.add(tTwitter3);

		// テストデータの生成
		// 予めテストユーザー「testuer5」は登録しておく
		// ユーザーID、作品名、かな読み、サイトリスト、Twitterリスト
		TitleBean title = new TitleBean("testuer5", "地味俺", "じみおれ", tSites, tTwitteres);

		TransactionDAO dao = new TransactionDAO();

		boolean rtnBool = false;

		try {

			rtnBool = dao.titleRegisterAll(title);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■作品登録");
		System.out.println("登録結果：" + rtnBool);

	}

	// 作品の表示・非表示設定の更新
	public static void titleOnOffUpdateTest() {

		// テストデータの生成（作品リスト）
		List<TitleBean> titleList = new ArrayList<TitleBean>();

		// 作品1
		TitleBean title1 = new TitleBean();
		title1.setTitleId(1);
		title1.settOnOff(1);

		// 作品2
		TitleBean title2 = new TitleBean();
		title2.setTitleId(2);
		title2.settOnOff(0);

		// 作品2
		TitleBean title3 = new TitleBean();
		title3.setTitleId(4);
		title3.settOnOff(1);

		titleList.add(title1);
		titleList.add(title2);
		titleList.add(title3);

		// DAOの呼び出し
		TransactionDAO dao = new TransactionDAO();

		// 戻り値
		boolean rtnBool = false;

		try {

			rtnBool = dao.titleOnOffUpdate(titleList);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■作品の表示・非表示の更新");
		System.out.println("処理結果：" + rtnBool);

	}

	// 作品情報の更新
	public static void titleUpdateAllTest() {

		// テストデータの生成（サイト情報）
		List<TitleSite> tSites = new ArrayList<TitleSite>();

		// 更新テスト
		TitleSite tSite1 = new TitleSite();
		tSite1.setSiteId(16);
		tSite1.setSiteUrl("https://ttttest.com");

		// 18,19は削除テスト

		// 追加テスト
		TitleSite tSite2 = new TitleSite();
		tSite2.setSiteId(18);
		tSite2.setSiteUrl("https://test333333.com");

		// 追加テスト
		TitleSite tSite3 = new TitleSite();
		// tSite3.setSiteId(18);
		tSite3.setSiteUrl("https://test444444.com");

		tSites.add(tSite1);
		//tSites.add(tSite2);
		tSites.add(tSite3);

		// テストデータの生成（Twitter情報）
		List<TitleTwitter> tTwitteres = new ArrayList<TitleTwitter>();

		// 更新テスト
		TitleTwitter tTwitter1 = new TitleTwitter();
		tTwitter1.settTwitterId(13);
		tTwitter1.setTwitterId("@ttttest");

		// 面倒なのでこれも更新テスト
		TitleTwitter tTwitter2 = new TitleTwitter();
		tTwitter2.settTwitterId(14);
		tTwitter2.setTwitterId("@test55555");

		// 14、19は削除テスト

		// 追加テスト
		TitleTwitter tTwitter3 = new TitleTwitter();
		// tTwitter3.settTwitterId(19);
		tTwitter3.setTwitterId("@test444444");

		tTwitteres.add(tTwitter1);
		// tTwitteres.add(tTwitter2);
		tTwitteres.add(tTwitter3);

		// テストデータの生成
		// 予めテストユーザー「testuer5」は登録しておく
		// ユーザーID、作品名、かな読み、サイトリスト、Twitterリスト
		TitleBean title = new TitleBean("testuer5", "地味な俺らをめぐって学園で美少女たちが銃撃戦をしているんだが。", "じみおれ", tSites, tTwitteres);
		title.setTitleId(12);

		// DAOの呼び出し
		TransactionDAO dao = new TransactionDAO();

		boolean rtnBool = false;

		try {

			rtnBool = dao.titleUpdateAll(title);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■作品情報の更新");
		System.out.println("処理結果：" + rtnBool);

	}

	public static void titleDeleteAllTest(){

		// DAOの呼び出し
		TransactionDAO dao = new TransactionDAO();

		boolean rtnBool = false;

		try {

			rtnBool = dao.titleDeleteAll(15);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■作品情報の削除");
		System.out.println("処理結果：" + rtnBool);

	}
}
