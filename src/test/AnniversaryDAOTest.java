package test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import model.dao.AnniversaryDAO;
import model.entity.AnniversaryBean;

public class AnniversaryDAOTest {

	public static void main(String[] args) {

		// 記念日・誕生日の登録
		// annivRegisterTest();

		// 記念日・誕生日の取得
		// getAnnivTest();

		// 記念日・誕生日の一覧を取得（全件）
		// selectAnnivAllTest();

		// 選択した作品の記念日・誕生日の一覧を取得
		// selectAnnivTest();

		// 今日の記念日・誕生日の一覧を取得
		// selectAnnivTodayTest();

		// カウントダウンの記念日・誕生日の一覧を取得
		selectAnnivCntTest();

		// 年をまたいだカウントダウン
		// selectAnnivCntAcrossTest();

		// 記念日・誕生日の更新
		// annivUpdateTest();

		// 記念日・誕生日の削除
		// annivDeleteTest();

		// ユーザーの削除
		//userAnnivDeleteTest();

	}


	// 記念日・誕生日の登録
	public static void annivRegisterTest() {

		// テストデータ1
		AnniversaryBean anniv1 = new AnniversaryBean();

		anniv1.setUserId("testuer");
		anniv1.setTitleId(1);
		anniv1.setAnnivName("ファル");
		anniv1.setAnnivYear(0);
		anniv1.setAnnivMonth(3);
		anniv1.setAnnivDay(01);
		anniv1.setAnnivCnt(null);
		anniv1.setAnnivClass("記念日");
		anniv1.setAnnivClr("#cc66ff");

		// テストデータ2
		AnniversaryBean anniv2 = new AnniversaryBean();

		anniv2.setUserId("testuser2");
		anniv2.setTitleId(3);
		anniv2.setAnnivName("佐賀美陣");
		anniv2.setAnnivYear(0);
		anniv2.setAnnivMonth(12);
		anniv2.setAnnivDay(11);
		anniv2.setAnnivCnt(null);
		anniv2.setAnnivClass("誕生日");
		anniv2.setAnnivClr("#ff0000");

		AnniversaryDAO dao = new AnniversaryDAO();

		// 戻り値の設定
		int rtn1 = 0;
		int rtn2 = 0;

		try {

			rtn1 = dao.annivRegister(anniv1);
			rtn2 = dao.annivRegister(anniv2);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■記念日・誕生日の登録");
		System.out.println("処理件数：" + (rtn1 + rtn2));
		System.out.println();

	}

	// 記念日・誕生日の情報の取得
	public static void getAnnivTest() {

		// 戻り値の受け取り
		AnniversaryBean anniv = new AnniversaryBean();

		AnniversaryDAO dao = new AnniversaryDAO();

		try {

			anniv = dao.getAnniv(1);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■記念日・誕生日の情報");
		System.out.println("記念日・誕生日ID：" + anniv.getAnnivId());
		System.out.println("ユーザーID：" + anniv.getUserId());
		System.out.println("作品ID：" + anniv.getTitleId());
		System.out.println("記念日・誕生日の名称：" + anniv.getAnnivName());
		System.out.println("記念日・誕生日の年：" + anniv.getAnnivYear());
		System.out.println("記念日・誕生日の月：" + anniv.getAnnivMonth());
		System.out.println("記念日・誕生日の日：" + anniv.getAnnivDay());
		System.out.println("周年カウントの名称：" + anniv.getAnnivCnt());
		System.out.println("記念日・誕生日の区分：" + anniv.getAnnivClass());
		System.out.println("名称の色：" + anniv.getAnnivClr());
		System.out.println("今年のLocalDate型：" + anniv.getAnnivDateType());
		System.out.println("カウントダウン日数：" + anniv.getAnnivDateCnt());
		System.out.println();

	}

	// 記念日・誕生日の全件取得
	public static void selectAnnivAllTest() {

		// 取得する一覧Listを格納
		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		AnniversaryDAO dao = new AnniversaryDAO();

		try {

			annivList = dao.selectAnnivAll("testuer");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■記念日・誕生日の一覧を取得（全件）");

		for (AnniversaryBean aB : annivList) {

			System.out.println(aB.getAnnivId() + ", " + aB.getUserId() + ", " + aB.getTitleId() + ", " + aB.getAnnivName() + ", " + aB.getAnnivYear() + ", " + aB.getAnnivMonth() + ", " + aB.getAnnivDay() + ", " + aB.getAnnivCnt() + ", " + aB.getAnnivClass() + ", " + aB.getAnnivClr() + ", " + aB.getAnnivDateType() + ", " + aB.getAnnivDateCnt());

		}

		System.out.println();

	}

	// 選択した作品の記念日・誕生日の一覧を取得
	public static void selectAnnivTest() {

		// 取得する一覧Listを格納
		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		AnniversaryDAO dao = new AnniversaryDAO();

		try {

			annivList = dao.selectAnniv(1);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■記念日・誕生日の一覧を取得（作品選択）");

		for (AnniversaryBean aB : annivList) {

			System.out.println(aB.getAnnivId() + ", " + aB.getUserId() + ", " + aB.getTitleId() + ", " + aB.getAnnivName() + ", " + aB.getAnnivYear() + ", " + aB.getAnnivMonth() + ", " + aB.getAnnivDay() + ", " + aB.getAnnivCnt() + ", " + aB.getAnnivClass() + ", " + aB.getAnnivClr() + ", " + aB.getAnnivDateType() + ", " + aB.getAnnivDateCnt());

		}

		System.out.println();

	}

	// 今日の記念日・誕生日の取得
	public static void selectAnnivTodayTest() {

		// 取得する一覧Listを格納
		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		AnniversaryDAO dao = new AnniversaryDAO();

		try {

			annivList = dao.selectAnnivToday(1214, "testuer");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■今日の記念日・誕生日の一覧");

		for (AnniversaryBean aB : annivList) {

			System.out.println(aB.getAnnivId() + ", " + aB.getUserId() + ", " + aB.getTitleId() + ", " + aB.getAnnivName() + ", " + aB.getAnnivYear() + ", " + aB.getAnnivMonth() + ", " + aB.getAnnivDay() + ", " + aB.getAnnivCnt() + ", " + aB.getAnnivClass() + ", " + aB.getAnnivClr() + ", " + aB.getAnnivDateType() + ", " + aB.getAnnivDateCnt());

		}

		System.out.println();

	}

	// 記念日・誕生日のカウントダウン一覧を取得
	public static void selectAnnivCntTest() {

		// 取得する記念日・誕生日一覧の生成
		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		AnniversaryDAO dao = new AnniversaryDAO();

		try {

			// annivList = dao.selectAnnivCnt(1207, 1226, "testuer");
			annivList = dao.selectAnnivCnt(215, 307, "testuer");

		} catch (ClassNotFoundException | SQLException | ParseException e) {

			e.printStackTrace();

		}

		System.out.println("■カウントダウンの記念日・誕生日の一覧");

		for (AnniversaryBean aB : annivList) {

			System.out.println(aB.getAnnivId() + ", " + aB.getUserId() + ", " + aB.getTitleId() + ", " + aB.getAnnivName() + ", " + aB.getAnnivYear() + ", " + aB.getAnnivMonth() + ", " + aB.getAnnivDay() + ", " + aB.getAnnivCnt() + ", " + aB.getAnnivClass() + ", " + aB.getAnnivClr() + ", " + aB.getAnnivDateType() + ", " + aB.getAnnivDateCnt());

		}

		System.out.println();

	}

	// 記念日・誕生日のカウントダウン一覧を取得（年をまたぐ場合）
	public static void selectAnnivCntAcrossTest() {

		// 取得する記念日・誕生日一覧の生成
		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		AnniversaryDAO dao = new AnniversaryDAO();

		try {

			annivList = dao.selectAnnivCntAcross(1207, 125, "testuer");

		} catch (ClassNotFoundException | SQLException | ParseException e) {

			e.printStackTrace();

		}

		System.out.println("■年をまたいだ記念日・誕生日の一覧");

		for (AnniversaryBean aB : annivList) {

			System.out.println(aB.getAnnivId() + ", " + aB.getUserId() + ", " + aB.getTitleId() + ", " + aB.getAnnivName() + ", " + aB.getAnnivYear() + ", " + aB.getAnnivMonth() + ", " + aB.getAnnivDay() + ", " + aB.getAnnivCnt() + ", " + aB.getAnnivClass() + ", " + aB.getAnnivClr() + ", " + aB.getAnnivDateType() + ", " + aB.getAnnivDateCnt());

		}

		System.out.println();

	}

	// 記念日・誕生日の情報の更新
	public static void annivUpdateTest() {

		// テストデータ1
		AnniversaryBean anniv = new AnniversaryBean();

		anniv.setAnnivId(8);
		anniv.setUserId("testuer");
		anniv.setTitleId(1);
		anniv.setAnnivName("ファル");
		anniv.setAnnivYear(0);
		anniv.setAnnivMonth(5);
		anniv.setAnnivDay(23);
		anniv.setAnnivCnt(null);
		anniv.setAnnivClass("記念日");
		anniv.setAnnivClr("#cc66ff");

		AnniversaryDAO dao = new AnniversaryDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.annivUpdate(anniv);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■記念日・誕生日の更新");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// 記念日・誕生日の情報の削除
	public static void annivDeleteTest() {

		AnniversaryDAO dao = new AnniversaryDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.annivDelete(8);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■記念日・誕生日の削除");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

	// ユーザー削除による記念日・誕生日の情報の削除
	public static void userAnnivDeleteTest() {

		AnniversaryDAO dao = new AnniversaryDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.userAnnivDelete("testuser2");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■ユーザーの削除");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

}
