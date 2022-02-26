package model.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.dao.EventDAO;
import model.entity.EventBean;

public class EventLogic {

	// イベント情報の登録
	public boolean eventRegisterLogic(EventBean event) {

		EventDAO dao = new EventDAO();

		// 戻り値の準備
		int cnt = 0;

		try {

			cnt = dao.eventRegister(event);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		// 0でなければtrue
		// 0ならfalse
		return cnt != 0;
	}

	// イベント情報の取得（修正用）
	public EventBean getEventLogic(int eventId) {

		// 戻り値の設定
		EventBean anniv = new EventBean();

		EventDAO dao = new EventDAO();

		try {

			anniv = dao.getEvent(eventId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return anniv;

	}

	// 期間を指定したイベント情報の取得（全件or作品毎をより分け）
	public List<EventBean> selectEventLogic(String sYYYYMMDD, String eYYYYMMDD, int titleId, String userId){

		// 戻り値の設定
		List<EventBean> eventList = new ArrayList<EventBean>();

		EventDAO dao = new EventDAO();

		try {

			if(titleId == 0) {

				// 作品IDが「0」（全件）の場合は、ユーザーIDの全イベント情報を取得
				eventList = dao.selectEventAll(sYYYYMMDD, eYYYYMMDD, userId);

			} else {

				// 作品IDの指定がある場合は、その作品のイベント情報を取得
				eventList = dao.selectEvent(sYYYYMMDD, eYYYYMMDD, titleId);

			}

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return eventList;

	}

	// ユーザー設定のイベント取得日数分のイベントを取得（ホーム画面、一覧初回表示）
	public List<EventBean> selectEventGetLogic(String userId){

		// 戻り値の設定
		List<EventBean> eventList = new ArrayList<EventBean>();

		// 現在の年月日を「YYYY-MM-DD」で返してくれるメソッド
		String tYYYYMMDD = this.getToday();

		EventDAO dao = new EventDAO();

		try {

			eventList = dao.selectEventGet(tYYYYMMDD, userId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return eventList;

	}

	// 今日のイベントの取得
	public List<EventBean> selectEventTodayLogic(String userId){

		// 戻り値の設定
		List<EventBean> eventList = new ArrayList<EventBean>();

		// 現在の年月日を「YYYY-MM-DD」で返してくれるメソッド
		String tYYYYMMDD = this.getToday();

		EventDAO dao = new EventDAO();

		try {

			eventList = dao.selectEventToday(tYYYYMMDD, userId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return eventList;

	}


	// イベント情報の更新
	public boolean eventUpdateLogic(EventBean event) {

		EventDAO dao = new EventDAO();

		// 戻り値の準備
		int cnt = 0;

		try {

			cnt = dao.eventUpdate(event);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		// 0でなければtrue
		// 0ならfalse
		return cnt != 0;

	}

	// イベント情報の削除
	public boolean eventDeleteLogic(int eventId) {

		EventDAO dao = new EventDAO();

		// 戻り値の準備
		int cnt = 0;

		try {

			cnt = dao.eventDelete(eventId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		// 0でなければtrue
		// 0ならfalse
		return cnt != 0;

	}



	// 現在の月日をLocalDate型で取得して、
	// 「YYYY-MM-DD」のフォーマットにするだけのメソッド
	public String getToday() {

		// 現在の年月日をLocalDate型で取得
		// 現在の月日を取得＋1日足す（現在月日だとリストに当日を含んでしまうため）
		LocalDate nowLD = LocalDate.now().plusDays(1);

		// LocalDate型を「YYYY-MM-DD」のフォーマットにする
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String tYYYYMMDD = nowLD.format(fmt);

		return tYYYYMMDD;

	}

	// 現在の月日をLocalDate型で取得して、
	// ユーザー設定のイベント取得日数分を足して、
	// 「YYYY-MM-DD」のフォーマットにするだけのメソッド
	// イベント一覧表示の終了期間設定で必要
	public String getAddEndDay(int eventGet) {

		// 現在の年月日をLocalDate型で取得
		// 現在の月日を取得＋1日足す（現在月日だとリストに当日を含んでしまうため）
		LocalDate nowLD = LocalDate.now().plusDays(eventGet);

		// LocalDate型を「YYYY-MM-DD」のフォーマットにする
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String eYYYYMMDD = nowLD.format(fmt);

		return eYYYYMMDD;

	}
}
