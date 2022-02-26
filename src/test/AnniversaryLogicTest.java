package test;

import java.util.ArrayList;
import java.util.List;

import model.entity.AnniversaryBean;
import model.model.AnniversaryLogic;

public class AnniversaryLogicTest {

	public static void main(String[] args) {

		// 記念日・誕生日の登録
		// annivRegisterTest();

		// 記念日・誕生日の情報の取得
		// getAnnivTest();

		// 記念日・誕生日の全件取得
		// selectAnnivAllTest();

		// 選択した作品の記念日・誕生日の一覧を取得
		// selectAnnivTest();

		// 今日の記念日・誕生日の取得
		// selectAnnivToday();

		// 記念日・誕生日のカウントダウン一覧を取得
		// ユーザー情報のカウントダウン設定を確認して、
		// 年内なら通常版、年をまたぐならAcross版を呼ぶ
		// selectAnnivCntTest();

		// 記念日・誕生日の情報の更新
		annivUpdate();

		// 記念日・誕生日の情報の削除
		// annivDelete();

	}

	// 記念日・誕生日の登録
	public static void annivRegisterTest() {

		// テストデータの作成
		AnniversaryBean anniv = new AnniversaryBean();

		anniv.setUserId("testuer5");
		anniv.setTitleId(12);
		anniv.setAnnivName("同心庵九");
		anniv.setAnnivMonth(12);
		anniv.setAnnivDay(14);
		anniv.setAnnivClass("記念日");
		anniv.setAnnivClr("#ff99cc");

		AnniversaryLogic aL = new AnniversaryLogic();

		boolean rtn = aL.annivRegister(anniv);

		System.out.println("■記念日の登録");
		System.out.println("登録結果：" + rtn);

	}

	// 記念日・誕生日の情報の取得
	public static void getAnnivTest() {

		AnniversaryLogic aL = new AnniversaryLogic();

		AnniversaryBean anniv = aL.getAnniv(9);

		System.out.println("■記念日の取得");
		System.out.println("記念日ID：" + anniv.getAnnivId());
		System.out.println("ユーザー名：" + anniv.getUserId());
		System.out.println("作品ID：" + anniv.getTitleId());
		System.out.println("記念日の名称：" + anniv.getAnnivName());
		System.out.println("記念日の年：" + anniv.getAnnivYear());
		System.out.println("記念日の月：" + anniv.getAnnivMonth());
		System.out.println("記念日の日：" + anniv.getAnnivDay());
		System.out.println("周年カウント：" + anniv.getAnnivCnt());
		System.out.println("区分：" + anniv.getAnnivClass());
		System.out.println("名称の色：" + anniv.getAnnivClr());
		System.out.println("記念日のLocalDate型：" + anniv.getAnnivDateType());
		System.out.println("記念日までのカウントダウン日数：" + anniv.getAnnivCnt());
		System.out.println("カウントダウン開始日：" + anniv.getAnnivCountDwn());

	}


	// 記念日・誕生日の全件取得
	public static void selectAnnivAllTest() {

		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		AnniversaryLogic aL = new AnniversaryLogic();

		annivList = aL.selectAnnivAll("testuer");

		System.out.println("■記念日の取得（ユーザーの全件）");
		for (AnniversaryBean anniv : annivList) {

			System.out.println("記念日ID：" + anniv.getAnnivId());
			System.out.println("ユーザー名：" + anniv.getUserId());
			System.out.println("作品ID：" + anniv.getTitleId());
			System.out.println("記念日の名称：" + anniv.getAnnivName());
			System.out.println("記念日の年：" + anniv.getAnnivYear());
			System.out.println("記念日の月：" + anniv.getAnnivMonth());
			System.out.println("記念日の日：" + anniv.getAnnivDay());
			System.out.println("周年カウント：" + anniv.getAnnivCnt());
			System.out.println("区分：" + anniv.getAnnivClass());
			System.out.println("名称の色：" + anniv.getAnnivClr());
			System.out.println("記念日のLocalDate型：" + anniv.getAnnivDateType());
			System.out.println("記念日までのカウントダウン日数：" + anniv.getAnnivCnt());
			System.out.println("カウントダウン開始日：" + anniv.getAnnivCountDwn());
			System.out.println("-----------------");
		}
	}

	// 選択した作品の記念日・誕生日の一覧を取得
	public static void selectAnnivTest() {

		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		AnniversaryLogic aL = new AnniversaryLogic();

		annivList = aL.selectAnniv(1);

		System.out.println("■記念日の取得（選択した作品）");
		for (AnniversaryBean anniv : annivList) {

			System.out.println("記念日ID：" + anniv.getAnnivId());
			System.out.println("ユーザー名：" + anniv.getUserId());
			System.out.println("作品ID：" + anniv.getTitleId());
			System.out.println("記念日の名称：" + anniv.getAnnivName());
			System.out.println("記念日の年：" + anniv.getAnnivYear());
			System.out.println("記念日の月：" + anniv.getAnnivMonth());
			System.out.println("記念日の日：" + anniv.getAnnivDay());
			System.out.println("周年カウント：" + anniv.getAnnivCnt());
			System.out.println("区分：" + anniv.getAnnivClass());
			System.out.println("名称の色：" + anniv.getAnnivClr());
			System.out.println("記念日のLocalDate型：" + anniv.getAnnivDateType());
			System.out.println("記念日までのカウントダウン日数：" + anniv.getAnnivCnt());
			System.out.println("カウントダウン開始日：" + anniv.getAnnivCountDwn());
			System.out.println("-----------------");
		}

	}

	// 今日の記念日・誕生日の取得
	public static void selectAnnivToday() {

		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		AnniversaryLogic aL = new AnniversaryLogic();

		annivList = aL.selectAnnivToday("testuer5");

		System.out.println("■記念日の取得（今日）");
		for (AnniversaryBean anniv : annivList) {

			System.out.println("記念日ID：" + anniv.getAnnivId());
			System.out.println("ユーザー名：" + anniv.getUserId());
			System.out.println("作品ID：" + anniv.getTitleId());
			System.out.println("記念日の名称：" + anniv.getAnnivName());
			System.out.println("記念日の年：" + anniv.getAnnivYear());
			System.out.println("記念日の月：" + anniv.getAnnivMonth());
			System.out.println("記念日の日：" + anniv.getAnnivDay());
			System.out.println("周年カウント：" + anniv.getAnnivCnt());
			System.out.println("区分：" + anniv.getAnnivClass());
			System.out.println("名称の色：" + anniv.getAnnivClr());
			System.out.println("記念日のLocalDate型：" + anniv.getAnnivDateType());
			System.out.println("記念日までのカウントダウン日数：" + anniv.getAnnivCnt());
			System.out.println("カウントダウン開始日：" + anniv.getAnnivCountDwn());
			System.out.println("-----------------");
		}

	}

	// 記念日・誕生日のカウントダウン一覧を取得
	// ユーザー情報のカウントダウン設定を確認して、
	// 年内なら通常版、年をまたぐならAcross版を呼ぶ
	public static void selectAnnivCntTest() {

		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		AnniversaryLogic aL = new AnniversaryLogic();

		annivList = aL.selectAnnivCnt("testuer", 340);

		System.out.println("■記念日の取得（カウントダウン）");
		for (AnniversaryBean anniv : annivList) {

			System.out.println("記念日ID：" + anniv.getAnnivId());
			System.out.println("ユーザー名：" + anniv.getUserId());
			System.out.println("作品ID：" + anniv.getTitleId());
			System.out.println("記念日の名称：" + anniv.getAnnivName());
			System.out.println("記念日の年：" + anniv.getAnnivYear());
			System.out.println("記念日の月：" + anniv.getAnnivMonth());
			System.out.println("記念日の日：" + anniv.getAnnivDay());
			System.out.println("周年カウント：" + anniv.getAnnivCnt());
			System.out.println("区分：" + anniv.getAnnivClass());
			System.out.println("名称の色：" + anniv.getAnnivClr());
			System.out.println("記念日のLocalDate型：" + anniv.getAnnivDateType());
			System.out.println("記念日までのカウントダウン日数：" + anniv.getAnnivDateCnt());
			System.out.println("カウントダウン開始日：" + anniv.getAnnivCountDwn());
			System.out.println("-----------------");
		}

	}

	// 記念日・誕生日の情報の更新
	public static void annivUpdate() {

		// テストデータの作成
		AnniversaryBean anniv = new AnniversaryBean();

		anniv.setAnnivId(12);
		anniv.setUserId("testuer5");
		anniv.setTitleId(12);
		anniv.setAnnivName("葉月");
		anniv.setAnnivMonth(2);
		anniv.setAnnivDay(16);
		anniv.setAnnivClass(null);
		anniv.setAnnivClr("#ff99cc");

		// JSP表示用
		anniv.setAnnivCnt("周年");
		anniv.setAnnivYear(2020);

		AnniversaryLogic aL = new AnniversaryLogic();

		boolean rtn = aL.annivUpdate(anniv);

		System.out.println("■記念日の登録");
		System.out.println("登録結果：" + rtn);

	}

	// 記念日・誕生日の情報の削除
	public static void annivDelete() {

		AnniversaryLogic aL = new AnniversaryLogic();

		boolean rtn = aL.annivDelete(13);

		System.out.println("■記念日の登録");
		System.out.println("登録結果：" + rtn);

	}
}
