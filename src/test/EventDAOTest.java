package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.EventDAO;
import model.entity.EventBean;

public class EventDAOTest {

	public static void main(String[] args) {

		// イベントの登録
		// eventRegisterTest();

		// イベント情報の取得
		// getEventTest();

		// イベントの全件取得
		// selectEventAllTest();

		// 選択した作品のイベント一覧を取得
		// selectEventTest();

		// 設定日分のイベント一覧を取得
		// selectEventGetTest();

		// 今日のイベントを取得
		// selectEventTodayTest();

		// イベント情報の更新
		//eventUpdateTest();

		// イベント情報の削除
		eventDeleteTest();

		// ユーザー削除によるイベント情報削除
		// userEventDeleteTest();

	}

	// イベント情報の登録
	public static void eventRegisterTest() {

		// テストデータ1
//		EventBean event = new EventBean();
//
//		event.setUserId("testuer");
//		event.setTitleId(1);
//		event.setEventName("『千銃士:Rhodoknight』×THEキャラSHOP");
//		event.setEventStart("2022-01-13");
//		event.setEventEnd("2022-01-24");
//		event.setEventLoc("池袋P'PARCO 3階");
//		event.setEventUrl("https://www.the-chara.com/blog/?p=38280");
//		event.setEventShop("https://www.the-chara.com/view/search?search_category=ct2896");

		// テストデータ2
		EventBean event2 = new EventBean();

		event2.setUserId("testuer");
		event2.setTitleId(2);
		event2.setEventName("文豪とアルケミスト　夏祭り");
		event2.setEventStart("2020-08-13");
		event2.setEventEnd("2020-08-31");
		event2.setEventLoc("アニメイト池袋");
		event2.setEventUrl("https://www.the-chara.com/blog/?p=38280");
		event2.setEventShop("https://www.the-chara.com/view/search?search_category=ct2896");

		// テストデータ2
		EventBean event3 = new EventBean();

		event3.setUserId("testuer2");
		event3.setTitleId(3);
		event3.setEventName("文豪とアルケミスト　POPUP");
		event3.setEventStart("2020-06-13");
		event3.setEventEnd("2020-06-30");
		event3.setEventLoc("紀伊国屋");
		event3.setEventUrl("https://www.the-chara.com/blog/?p=38280");
		event3.setEventShop("https://www.the-chara.com/view/search?search_category=ct2896");

		EventDAO dao = new EventDAO();

		// 戻り値の設定
//		int rtn = 0;
		int rtn2 = 0;
		int rtn3 = 0;

		try {

//			rtn = dao.eventRegister(event);
			rtn2 = dao.eventRegister(event2);
			rtn3 = dao.eventRegister(event3);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■イベントの登録");
		System.out.println("処理件数：" + (rtn2 + rtn3));

	}

	// イベント情報の取得
	public static void getEventTest() {

		// 戻り値の受け取り
		EventBean event = new EventBean();

		EventDAO dao = new EventDAO();

		try {

			event = dao.getEvent(1);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■イベント情報");
		System.out.println("イベントID：" + event.getEventId());
		System.out.println("ユーザーID：" + event.getUserId());
		System.out.println("作品ID：" + event.getTitleId());
		System.out.println("イベント名：" + event.getEventName());
		System.out.println("イベント開始日：" + event.getEventStart());
		System.out.println("イベント開始日：" + event.getEventStart().getClass().getSimpleName());
		System.out.println("イベント終了日：" + event.getEventEnd());
		System.out.println("イベントの場所：" + event.getEventLoc());
		System.out.println("イベントサイトURL：" + event.getEventUrl());
		System.out.println("イベント通販サイトURL：" + event.getEventShop());
		System.out.println("イベント開始日のLocalDate型：" + event.getEventStartDateType());
		// System.out.println("イベント開始日のLocalDate型：" + event.getEventStartDateType().getClass().getSimpleName());
		System.out.println("イベント終了日のLocalDate型：" + event.getEventEndDateType());
		System.out.println("イベントのカウント日数：" + event.getEventEndCnt());

	}

	// イベント情報の取得（作品全件）
	public static void selectEventAllTest() {

		// 取得する一覧Listを格納
		List<EventBean> eventList = new ArrayList<EventBean>();

		EventDAO dao = new EventDAO();

		try {

			eventList = dao.selectEventAll("2020-7-14", "2021-12-01", "testuer");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■イベントの一覧を取得（全件）");

		for (EventBean eB : eventList) {

			System.out.print(eB.getEventId() + ", ");
			System.out.print(eB.getUserId() + ", ");
			System.out.print(eB.getTitleId() + ", ");
			System.out.print(eB.getEventName() + ", ");
			System.out.print(eB.getEventStart()+ ", ");
			System.out.print(eB.getEventEnd() + ", ");
			System.out.print(eB.getEventLoc() + ", ");
			System.out.print(eB.getEventShop() + ", ");
			System.out.print(eB.getEventEndCnt() + ", ");
			System.out.print(eB.getEventStartDateType() + ", ");
			System.out.println(eB.getEventEndDateType());

		}

		System.out.println();

	}

	// 選択した作品のイベントの一覧を取得
	public static void selectEventTest() {

		// 取得する一覧Listを格納
		List<EventBean> eventList = new ArrayList<EventBean>();

		EventDAO dao = new EventDAO();

		try {

			eventList = dao.selectEvent("2021-10-14", "2021-12-15", 1);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■イベントの一覧を取得（作品別）");

		for (EventBean eB : eventList) {

			System.out.print(eB.getEventId() + ", ");
			System.out.print(eB.getUserId() + ", ");
			System.out.print(eB.getTitleId() + ", ");
			System.out.print(eB.getEventName() + ", ");
			System.out.print(eB.getEventStart()+ ", ");
			System.out.print(eB.getEventEnd() + ", ");
			System.out.print(eB.getEventLoc() + ", ");
			System.out.print(eB.getEventShop() + ", ");
			System.out.print(eB.getEventEndCnt() + ", ");
			System.out.print(eB.getEventStartDateType() + ", ");
			System.out.println(eB.getEventEndDateType());

		}

		System.out.println();

	}

	// ユーザー設定のイベント取得日数分のイベントを取得
	public static void selectEventGetTest() {

		// 取得する一覧Listを格納
		List<EventBean> eventList = new ArrayList<EventBean>();

		EventDAO dao = new EventDAO();

		try {

			eventList = dao.selectEventGet("2021-11-20", "testuer");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■設定日分のイベントの一覧を取得");

		for (EventBean eB : eventList) {

			System.out.print(eB.getEventId() + ", ");
			System.out.print(eB.getUserId() + ", ");
			System.out.print(eB.getTitleId() + ", ");
			System.out.print(eB.getEventName() + ", ");
			System.out.print(eB.getEventStart()+ ", ");
			System.out.print(eB.getEventEnd() + ", ");
			System.out.print(eB.getEventLoc() + ", ");
			System.out.print(eB.getEventShop() + ", ");
			System.out.print(eB.getEventEndCnt() + ", ");
			System.out.print(eB.getEventStartDateType() + ", ");
			System.out.println(eB.getEventEndDateType());

		}

		System.out.println();

	}

	// 今日のイベントの取得
	public static void selectEventTodayTest() {

		// 取得する一覧Listを格納
		List<EventBean> eventList = new ArrayList<EventBean>();

		EventDAO dao = new EventDAO();

		try {

			eventList = dao.selectEventToday("2022-01-14", "testuer");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■今日のイベントの一覧を取得");

		for (EventBean eB : eventList) {

			System.out.print(eB.getEventId() + ", ");
			System.out.print(eB.getUserId() + ", ");
			System.out.print(eB.getTitleId() + ", ");
			System.out.print(eB.getEventName() + ", ");
			System.out.print(eB.getEventStart()+ ", ");
			System.out.print(eB.getEventEnd() + ", ");
			System.out.print(eB.getEventLoc() + ", ");
			System.out.print(eB.getEventShop() + ", ");
			System.out.print(eB.getEventEndCnt() + ", ");
			System.out.print(eB.getEventStartDateType() + ", ");
			System.out.println(eB.getEventEndDateType());

		}

		System.out.println();

	}

	// イベント情報の更新
	public static void eventUpdateTest() {

		// テストデータ2
		EventBean event = new EventBean();

		event.setEventId(5);
		event.setUserId("testuer");
		event.setTitleId(2);
		event.setEventName("文豪とアルケミスト　審判の歯車");
		event.setEventStart("2020-08-13");
		event.setEventEnd("2020-08-31");
		event.setEventLoc("アニメイト池袋");
		event.setEventUrl("https://www.the-chara.com/blog/?p=38280");
		event.setEventShop("https://www.the-chara.com/view/search?search_category=ct2896");

		EventDAO dao = new EventDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.eventUpdate(event);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■イベントの登録");
		System.out.println("処理件数：" + rtn);

	}

	// イベント情報の削除
	public static void eventDeleteTest() {

		EventDAO dao = new EventDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.eventDelete(6);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■イベントの削除");
		System.out.println("処理件数：" + rtn);
		System.out.println();
	}

	// ユーザー削除によるイベント情報の削除
	public static void userEventDeleteTest() {

		EventDAO dao = new EventDAO();

		// 戻り値の設定
		int rtn = 0;

		try {

			rtn = dao.userEventDelete("USER3");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		System.out.println("■ユーザーの削除");
		System.out.println("処理件数：" + rtn);
		System.out.println();

	}

}
