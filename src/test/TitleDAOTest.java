package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.TitleDAO;
import model.entity.TitleBean;

public class TitleDAOTest {

	public static void main(String[] args) {

		// 作品の重複をチェック
		titleNameCheckTest();

		// 作品情報の登録
		// titleRegisterTest();

		// 作品IDの検索
		getTitleIdTest();

		// 作品情報の取得
		getTitleInfoTest();

		// 作品一覧（表示、非表示問わず）を取得
		selectTitleAllTest();

		// 作品一覧（表示のみ）を取得
		selectTitleOnTest();

		// 作品情報の更新
		// titleUpdateTest();

		// 表示・非表示の更新
		// titleOnOffUpdateTest();

		// 作品情報の削除
		// titleDeleteTest();

		// ユーザー削除による作品情報の削除
		// userTitleDelete();


	}

	// 作品名の重複をチェック
	public static void titleNameCheckTest() {

		TitleDAO dao = new TitleDAO();

		boolean b1 = false;		// 重複あり（true）
		boolean b2 = true;			// 重複無し（false）
		boolean b3 = true;			// 重複無し（false）※ユーザーID違い

		try {

			// 重複の作品名がある
			b1 = dao.titleNameCheck("千銃士","testuer");
			// 重複の作品名がない
			b2 = dao.titleNameCheck("千銃士R", "testuer");
			// 重複の作品名だが、ユーザーIDが違う
			b3 = dao.titleNameCheck("あんさんぶるスターズ！！", "testuer");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■作品名の重複をチェック");
		System.out.println("重複ありは「true」、重複無しは「false」");
		System.out.println("存在する作品名を入力：" + b1);
		System.out.println("存在しない作品名を入力：" + b2);
		System.out.println("ユーザーID違いの存在する作品名を入力：" + b3);
		System.out.println();

	}


	// 作品情報の登録
	public static void titleRegisterTest() {

		// テストデータの作成
		TitleBean title = new TitleBean();

		title.setUserId("testuser2");
		title.setTitleName("あんさんぶるスターズ！！");
		title.setTitleKn("あんさんぶるすたーず");
		title.settOnOff(1);

		TitleDAO dao = new TitleDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.titleRegister(title);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■作品登録");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// 作品名から作品IDを返す
	public static void getTitleIdTest() {

		// 存在する作品
		String title1 = "千銃士";
		// 存在しない作品
		String title2 = "刀剣乱舞";
		// 存在しているが、ユーザーIDが違う
		String title3 = "あんさんぶるスターズ！！";

		TitleDAO dao = new TitleDAO();

		// 戻り値の設定
		int rtn1= 0;
		int rtn2= 0;
		int rtn3= 0;

		try {

			rtn1 = dao.getTitleId(title1, "testuer");
			rtn2 = dao.getTitleId(title2, "testuer");
			rtn3 = dao.getTitleId(title3, "testuer");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■作品IDサーチ");
		System.out.println(title1 + "の作品ID（存在する作品）：" + rtn1);
		System.out.println(title2 + "の作品ID（存在しない作品）：" + rtn2);
		System.out.println(title3 + "の作品ID（存在するが、ユーザーID違い）：" + rtn3);
		System.out.println();

	}

	// 作品IDから、作品情報（ユーザーID、作品名、かな、表示非表示）を取得
	public static void getTitleInfoTest() {

		TitleBean title = new TitleBean();

		TitleDAO dao = new TitleDAO();

		try {

			title = dao.getTitleInfo(1);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■作品情報の取得");
		System.out.println("作品ID："+ title.getTitleId());
		System.out.println("ユーザーID："+ title.getUserId());
		System.out.println("作品名："+ title.getTitleName());
		System.out.println("作品名（かな）："+ title.getTitleKn());
		System.out.println("表示・非表示："+ title.gettOnOff());

		// 取得しない値（null）
		System.out.println("公式サイト："+ title.getSite());
		System.out.println("Twitter："+ title.getTwitter());
		System.out.println();

	}

	// 作品一覧を返す
	public static void selectTitleAllTest() {

		List<TitleBean> titleList = new ArrayList<TitleBean>();

		TitleDAO dao = new TitleDAO();

		try {

			titleList = dao.selectTitleAll("testuer");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■作品一覧（表示・非表示問わず）を取得");

		for (TitleBean t : titleList) {

			System.out.println(t.getTitleId() + ", " + t.getUserId() + ", " + t.getTitleName() + "," + t.getTitleKn() + ", " + t.gettOnOff() + "," + t.getSite() + ", " + t.getTwitter());

		}

		System.out.println();

	}

	// 「表示」設定の作品一覧を返す
	public static void selectTitleOnTest() {

		List<TitleBean> titleList = new ArrayList<TitleBean>();

		TitleDAO dao = new TitleDAO();

		try {

			titleList = dao.selectTitleOn("testuer");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■作品一覧（表示のみ）を取得");

		for (TitleBean t : titleList) {

			System.out.println(t.getTitleId() + ", " + t.getUserId() + ", " + t.getTitleName() + "," + t.getTitleKn() + ", " + t.gettOnOff() + "," + t.getSite() + ", " + t.getTwitter());

		}

		System.out.println();

	}

	// 作品情報の更新
	public static void titleUpdateTest() {

		TitleBean title = new TitleBean();

		// 更新するデータをセット
		title.setTitleId(4);
		title.setUserId("testuser2");
		title.setTitleName("あんさんぶるスターズ！");
		title.setTitleKn("あんさんぶるすたーず");
		title.settOnOff(0);

		TitleDAO dao = new TitleDAO();

		// 戻り値の設定
		int rtn= 0;

		try {

			rtn = dao.titleUpdate(title);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■作品情報の更新");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// 表示、非表示の更新
	public static void titleOnOffUpdateTest() {

		TitleDAO dao = new TitleDAO();

		// 戻り値の設定
		int rtn= 0;

		try {

			rtn = dao.titleOnOffUpdate(4, 1);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■表示・非表示の更新");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// 作品情報の削除
	public static void titleDeleteTest() {

		TitleDAO dao = new TitleDAO();

		// 戻り値の設定
		int rtn= 0;

		try {

			rtn = dao.titleDelete(4);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■表示・非表示の更新");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// ユーザー削除による作品情報の削除
	public static void userTitleDelete() {

		TitleDAO dao = new TitleDAO();

		// 戻り値の設定
		int rtn= 0;

		try {

			rtn = dao.userTitleDelete("testuser2");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■表示・非表示の更新");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

}
