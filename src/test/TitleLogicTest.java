package test;

import java.util.ArrayList;
import java.util.List;

import model.entity.TitleBean;
import model.entity.TitleSite;
import model.entity.TitleTwitter;
import model.model.TitleLogic;

public class TitleLogicTest {

	public static void main(String[] args) {

		// 作品の重複チェック
		// TitleNameCheckLogic();

		// 作品登録（トランザクション処理ver.）
		// titleRegisterAllLogicTest();

		// 作品IDから作品情報を取得する
		// getTitleInfoLogic();

		// 作品一覧（表示非表示関係なく、ユーザーIDの全部）を取得
		// selectTitleAll();

		// 「表示」設定の作品一覧を返す
		// これも、一覧やフォームのセレクトだけに使用するので、
		// サイト情報、Twitter情報は取得しない
		// selectTitleOnLogic();

		// 作品の表示・非表示の更新
		// titleOnOffUpdateLogicTest();

		// 作品情報の更新（トランザクション処理ver）
		// titleUpdateAllLogic();

		// 作品情報の削除
		// titleDeleteAll();

	}

	// 作品の重複チェック
	// 作品名が存在する（true）
    // 作品名が存在しない（false）
	public static void TitleNameCheckLogic() {

		TitleLogic tL = new TitleLogic();

		// 重複あり
		boolean rtn1 = tL.TitleNameCheckLogic("千銃士", "testuer");

		// 重複なし（ユーザーID違い）
		boolean rtn2 = tL.TitleNameCheckLogic("千銃士", "testuser2");

		System.out.println("■作品の重複チェック");
		System.out.println("重複ありの結果：" + rtn1);
		System.out.println("重複なしの結果：" + rtn2);

	}

	// 作品登録（トランザクション処理ver.）
	public static void titleRegisterAllLogicTest() {

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

		TitleLogic tL = new TitleLogic();

		boolean rtn = tL.titleRegisterAllLogic(title);

		System.out.println("■作品登録");
		System.out.println("登録の結果：" + rtn);

	}

	// 作品IDから作品情報を取得する
	public static void getTitleInfoLogic() {

		TitleLogic tL = new TitleLogic();

		TitleBean title = tL.getTitleInfo(1);

		System.out.println("■作品情報の取得");
		System.out.println("作品ID：" + title.getTitleId());
		System.out.println("ユーザーID" + title.getUserId());
		System.out.println("作品名：" + title.getTitleName());
		System.out.println("作品名（かな）：" + title.getTitleKn());
		for (TitleSite tS : title.getSite()) {
			System.out.println("サイト：" + tS.getSiteUrl());
		}
		for (TitleTwitter tT : title.getTwitter()) {
			System.out.println("サイト：" + tT.getTwitterId());
		}
		System.out.println("表示・非表示：" + title.gettOnOff());
	}

	// 作品一覧（表示非表示関係なく、ユーザーIDの全部）を取得
	// これは、表示非表示設定する用の全件取得なので、
	// サイト情報、Twitter情報は取得しない
	public static void selectTitleAll() {

		TitleLogic tL = new TitleLogic();

		List<TitleBean> titleList = tL.selectTitleAll("testuer");

		for (TitleBean title : titleList) {

			System.out.println("■作品情報の取得");
			System.out.println("作品ID：" + title.getTitleId());
			System.out.println("ユーザーID" + title.getUserId());
			System.out.println("作品名：" + title.getTitleName());
			System.out.println("作品名（かな）：" + title.getTitleKn());
//			for (TitleSite tS : title.getSite()) {
//				System.out.println("サイト：" + tS.getSiteUrl());
//			}
//			for (TitleTwitter tT : title.getTwitter()) {
//				System.out.println("サイト：" + tT.getTwitterId());
//			}
			System.out.println("表示・非表示：" + title.gettOnOff());

		}
	}

	// 「表示」設定の作品一覧を返す
	// これも、一覧やフォームのセレクトだけに使用するので、
	// サイト情報、Twitter情報は取得しない
	public static void selectTitleOnLogic() {

		TitleLogic tL = new TitleLogic();

		List<TitleBean> titleList = tL.selectTitleOnLogic("testuer");

		for (TitleBean title : titleList) {

			System.out.println("■作品情報の取得");
			System.out.println("作品ID：" + title.getTitleId());
			System.out.println("ユーザーID" + title.getUserId());
			System.out.println("作品名：" + title.getTitleName());
			System.out.println("作品名（かな）：" + title.getTitleKn());
//			for (TitleSite tS : title.getSite()) {
//				System.out.println("サイト：" + tS.getSiteUrl());
//			}
//			for (TitleTwitter tT : title.getTwitter()) {
//				System.out.println("サイト：" + tT.getTwitterId());
//			}
			System.out.println("表示・非表示：" + title.gettOnOff());
		}

	}

	// 作品の表示・非表示の更新
	public static void titleOnOffUpdateLogicTest() {

		List<TitleBean> titleList = new ArrayList<TitleBean>();

		// テストデータ1
		TitleBean title1 = new TitleBean();

		// 更新するデータをセット
		title1.setTitleId(12);
		title1.setUserId("testuer5");
		title1.setTitleName("地味な俺らをめぐって学園で美少女たちが銃撃戦をしているんだが。");
		title1.setTitleKn("じみなおれらをめぐっていかりゃく");
		title1.settOnOff(1);

		// テストデータ1
		TitleBean title2 = new TitleBean();

		// 更新するデータをセット
		title2.setTitleId(16);
		title2.setUserId("testuer5");
		title2.setTitleName("地味俺");
		title2.setTitleKn("じみおれ");
		title2.settOnOff(0);

		// リストにテストデータを格納
		titleList.add(title1);
		titleList.add(title2);

		TitleLogic tL = new TitleLogic();

		boolean rtn = tL.titleOnOffUpdateLogic(titleList);

		System.out.println("■作品登録");
		System.out.println("登録の結果：" + rtn);

	}

	// 作品情報の更新（トランザクション処理ver）
	public static void titleUpdateAllLogic() {

		// テストデータの作成
		// テストデータの生成（サイト情報）
		List<TitleSite> tSites = new ArrayList<TitleSite>();

		// 更新テスト
		TitleSite tSite1 = new TitleSite();
		tSite1.setSiteId(32);
		tSite1.setSiteUrl("https://benoble.com");

		// 31は削除テスト

		// 追加テスト
		TitleSite tSite2 = new TitleSite();
		tSite2.setSiteId(18);
		tSite2.setSiteUrl("https://test333333.com");

		// 追加テスト
		TitleSite tSite3 = new TitleSite();
		// tSite3.setSiteId(18);
		tSite3.setSiteUrl("https://be-noble2.com");

		tSites.add(tSite1);
		//tSites.add(tSite2);
		tSites.add(tSite3);

		// テストデータの生成（Twitter情報）
		List<TitleTwitter> tTwitteres = new ArrayList<TitleTwitter>();

		// 更新テスト
		TitleTwitter tTwitter1 = new TitleTwitter();
		tTwitter1.settTwitterId(31);
		tTwitter1.setTwitterId("@doushinan");

		// 面倒なのでこれも更新テスト
		TitleTwitter tTwitter2 = new TitleTwitter();
		tTwitter2.settTwitterId(14);
		tTwitter2.setTwitterId("@test55555");

		// 32、33は削除テスト

		// 追加テスト
		TitleTwitter tTwitter3 = new TitleTwitter();
		// tTwitter3.settTwitterId(19);
		tTwitter3.setTwitterId("@doushinan9");

		tTwitteres.add(tTwitter1);
		// tTwitteres.add(tTwitter2);
		tTwitteres.add(tTwitter3);

		// テストデータの生成
		// 予めテストユーザー「testuer5」は登録しておく
		// ユーザーID、作品名、かな読み、サイトリスト、Twitterリスト
		TitleBean title = new TitleBean("testuer5", "地味俺。", "じみおれ", tSites, tTwitteres);
		title.setTitleId(16);

		TitleLogic tL = new TitleLogic();

		boolean rtn = tL.titleUpdateAllLogic(title);

		System.out.println("■作品登録");
		System.out.println("登録の結果：" + rtn);

	}

	// 作品情報の削除
	public static void titleDeleteAll() {

		TitleLogic tL = new TitleLogic();

		boolean rtn = tL.titleDeleteAll(16);

		System.out.println("■作品登録");
		System.out.println("登録の結果：" + rtn);

	}

}
