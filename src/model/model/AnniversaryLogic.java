package model.model;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

import model.dao.AnniversaryDAO;
import model.entity.AnniversaryBean;

public class AnniversaryLogic {

	// 記念日・誕生日の登録
	public boolean annivRegister(AnniversaryBean anniv) {

		AnniversaryDAO dao = new AnniversaryDAO();

		// 戻り値の準備
		int cnt = 0;

		try {

			cnt = dao.annivRegister(anniv);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		// 0でなければtrue
		// 0ならfalse
		return cnt != 0;

	}

	// 記念日・誕生日の情報の取得
	public AnniversaryBean getAnniv(int anivId) {

		// 戻り値の設定
		AnniversaryBean anniv = new AnniversaryBean();

		AnniversaryDAO dao = new AnniversaryDAO();

		try {

			anniv = dao.getAnniv(anivId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return anniv;

	}

	// 記念日・誕生日の全件取得
	public List<AnniversaryBean> selectAnnivAll(String userId) {

		// 戻り値の設定
		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		AnniversaryDAO dao = new AnniversaryDAO();

		try {

			annivList = dao.selectAnnivAll(userId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return annivList;

	}

	// 選択した作品の記念日・誕生日の一覧を取得
	public List<AnniversaryBean> selectAnniv(int titleId) {

		// 戻り値の設定
		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		AnniversaryDAO dao = new AnniversaryDAO();

		try {

			annivList = dao.selectAnniv(titleId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return annivList;

	}

	// 今日の記念日・誕生日の取得
	public List<AnniversaryBean> selectAnnivToday(String userId) {

		// 戻り値の設定
		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		// 今日の日付（MDD）を作成
		// 現在の月日を取得
		MonthDay nowMD = MonthDay.now();

		// 現在の日付から、月と日をint型で取得
		int m = nowMD.getMonthValue();
		int d = nowMD.getDayOfMonth();

		// int型で取得した月と日を3～4桁の数字verにする
		int tMD = m * 100 + d;

		AnniversaryDAO dao = new AnniversaryDAO();

		try {

			annivList = dao.selectAnnivToday(tMD, userId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return annivList;

	}

	// 記念日・誕生日のカウントダウン一覧を取得
	// ユーザー情報のカウントダウン設定を確認して、
	// 年内なら通常版、年をまたぐならAcross版を呼ぶ
	public List<AnniversaryBean> selectAnnivCnt(String userId, int countDwn) {

		// 戻り値の設定
		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		// 今日の日付（MDD）を作成
		// 現在の月日を取得＋1日足す（現在月日だとリストに当日を含んでしまうため）
		// 年をまたぐかの判定をするので、LocalDate型にする
		LocalDate nowLD = LocalDate.now().plusDays(1);

		// 現在の月日＋1日にカウントダウン設定の日数を足す
		LocalDate cntLD = nowLD.plusDays(countDwn);

		// 現在の月日とカウントダウン月日をint型に直す
		// 年またぎ判定に年も使うので、年のint型も取得しておく
		int tM = nowLD.getMonthValue();
		int tD = nowLD.getDayOfMonth();
		int cM = cntLD.getMonthValue();
		int cD = cntLD.getDayOfMonth();
		int tY = nowLD.getYear();
		int cY = cntLD.getYear();

		// 各日付さらに3～4桁の数字verに直す
		int tMD = tM * 100 + tD;
		int cMD = cM * 100 + cD;

		// 年をまたぐかの判定をして、DAOを呼び込む
		AnniversaryDAO dao = new AnniversaryDAO();

		try {

			if(tY < cY) {

				// 年をまたいでいたら、Across版
				annivList = dao.selectAnnivCntAcross(tMD, cMD, userId);

			} else {

				// 年をまたがない
				annivList = dao.selectAnnivCnt(tMD, cMD, userId);

			}

		} catch (ClassNotFoundException | SQLException | ParseException  e) {

			e.printStackTrace();

		}

		return annivList;

	}

	// 記念日・誕生日の情報の更新
	public boolean annivUpdate(AnniversaryBean anniv) {

		AnniversaryDAO dao = new AnniversaryDAO();

		// 戻り値の準備
		int cnt = 0;

		try {

			cnt = dao.annivUpdate(anniv);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		// 0でなければtrue
		// 0ならfalse
		return cnt != 0;

	}

	// 記念日・誕生日の情報の削除
	public boolean annivDelete(int annivId) {

		AnniversaryDAO dao = new AnniversaryDAO();

		// 戻り値の準備
		int cnt = 0;

		try {

			cnt = dao.annivDelete(annivId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		// 0でなければtrue
		// 0ならfalse
		return cnt != 0;

	}

}
