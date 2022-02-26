package test;

import java.util.ArrayList;
import java.util.List;

import model.entity.EventBean;
import model.model.EventLogic;

public class EventLogicTest {

	public static void main(String[] args) {

		// イベント情報の登録
		// eventRegisterLogicTest();

		// イベント情報の取得（修正用）
		// getEventLogicTest();

		// 期間を指定したイベント情報の取得（全件or作品毎をより分け）
		// selectEventLogicTest();

		// ユーザー設定のイベント取得日数分のイベントを取得（ホーム画面、一覧初回表示）
		// selectEventGetLogicTest();

		// 今日のイベントの取得
		selectEventTodayLogicTest();

		// イベント情報の更新
		// eventUpdateLogicTest();

		// イベント情報の削除
		// eventDeleteLogicTest();

	}

	// イベント情報の登録
	public static void eventRegisterLogicTest() {

		// テストデータの作成
		EventBean event = new EventBean();

		event.setUserId("testuer");
		event.setTitleId(1);
		event.setEventName("千銃士:Rhodoknight×ヴィレッジヴァンガード");
		event.setEventStart("2022-01-25");
		event.setEventEnd("2022-03-13");
		event.setEventLoc("ヴィレッジヴァンガード 渋谷本店");
		event.setEventUrl("https://www.village-v.co.jp/news/item/12138");

		EventLogic eL = new EventLogic();

		boolean rtn = eL.eventRegisterLogic(event);

		System.out.println("■イベント情報の登録");
		System.out.println("登録結果：" + rtn);

	}

	// イベント情報の取得（修正用）
	public static void getEventLogicTest() {

		// 戻り値の作成
		EventBean event = new EventBean();

		EventLogic eL = new EventLogic();

		event = eL.getEventLogic(2);

		System.out.println("■イベント情報の取得");
		System.out.println("イベントID：" + event.getEventId());
		System.out.println("ユーザーID：" + event.getUserId());
		System.out.println("作品ID：" + event.getTitleId());
		System.out.println("イベント名：" + event.getEventName());
		System.out.println("イベント開始日：" + event.getEventStart());
		System.out.println("イベント終了日：" + event.getEventEnd());
		System.out.println("イベントの場所：" + event.getEventLoc());
		System.out.println("イベントサイトURL：" + event.getEventUrl());
		System.out.println("イベント通販サイトURL：" + event.getEventShop());
		System.out.println("終了日カウントダウン：" + event.getEventEndCnt());
		System.out.println("イベント開始日DATE型：" + event.getEventStartDateType());
		System.out.println("イベント終了日DATE型：" + event.getEventEndDateType());

	}

	// 期間を指定したイベント情報の取得（全件or作品毎をより分け）
	public static void selectEventLogicTest() {

		// 戻り値の設定
		List<EventBean> eventList1 = new ArrayList<EventBean>();
		List<EventBean> eventList2 = new ArrayList<EventBean>();

		EventLogic eL = new EventLogic();

		// 全件取得の場合
		eventList1 = eL.selectEventLogic("2020-01-01", "2022-03-02", 0, "testuer");

		// 作品指定の場合
		eventList2 = eL.selectEventLogic("2020-01-01", "2022-03-02", 1, "testuer");

		System.out.println("■期間指定のイベント情報の取得");
		System.out.println("＜全作品＞");
		for (EventBean event : eventList1) {

			System.out.println("イベントID：" + event.getEventId());
			System.out.println("ユーザーID：" + event.getUserId());
			System.out.println("作品ID：" + event.getTitleId());
			System.out.println("イベント名：" + event.getEventName());
			System.out.println("イベント開始日：" + event.getEventStart());
			System.out.println("イベント終了日：" + event.getEventEnd());
			System.out.println("イベントの場所：" + event.getEventLoc());
			System.out.println("イベントサイトURL：" + event.getEventUrl());
			System.out.println("イベント通販サイトURL：" + event.getEventShop());
			System.out.println("終了日カウントダウン：" + event.getEventEndCnt());
			System.out.println("イベント開始日DATE型：" + event.getEventStartDateType());
			System.out.println("イベント終了日DATE型：" + event.getEventEndDateType());
			System.out.println("-------------");

		}
		System.out.println("＜作品指定＞");
		for (EventBean event : eventList2) {

			System.out.println("イベントID：" + event.getEventId());
			System.out.println("ユーザーID：" + event.getUserId());
			System.out.println("作品ID：" + event.getTitleId());
			System.out.println("イベント名：" + event.getEventName());
			System.out.println("イベント開始日：" + event.getEventStart());
			System.out.println("イベント終了日：" + event.getEventEnd());
			System.out.println("イベントの場所：" + event.getEventLoc());
			System.out.println("イベントサイトURL：" + event.getEventUrl());
			System.out.println("イベント通販サイトURL：" + event.getEventShop());
			System.out.println("終了日カウントダウン：" + event.getEventEndCnt());
			System.out.println("イベント開始日DATE型：" + event.getEventStartDateType());
			System.out.println("イベント終了日DATE型：" + event.getEventEndDateType());
			System.out.println("-------------");

		}
	}

	// ユーザー設定のイベント取得日数分のイベントを取得（ホーム画面、一覧初回表示）
	public static void selectEventGetLogicTest() {

		// 戻り値の設定
		List<EventBean> eventList = new ArrayList<EventBean>();

		EventLogic eL = new EventLogic();

		eventList = eL.selectEventGetLogic("testuer");

		for (EventBean event : eventList) {

			System.out.println("イベントID：" + event.getEventId());
			System.out.println("ユーザーID：" + event.getUserId());
			System.out.println("作品ID：" + event.getTitleId());
			System.out.println("イベント名：" + event.getEventName());
			System.out.println("イベント開始日：" + event.getEventStart());
			System.out.println("イベント終了日：" + event.getEventEnd());
			System.out.println("イベントの場所：" + event.getEventLoc());
			System.out.println("イベントサイトURL：" + event.getEventUrl());
			System.out.println("イベント通販サイトURL：" + event.getEventShop());
			System.out.println("終了日カウントダウン：" + event.getEventEndCnt());
			System.out.println("イベント開始日DATE型：" + event.getEventStartDateType());
			System.out.println("イベント終了日DATE型：" + event.getEventEndDateType());
			System.out.println("-------------");

		}

	}

	// 今日のイベントの取得
	public static void selectEventTodayLogicTest(){

		// 戻り値の設定
		List<EventBean> eventList = new ArrayList<EventBean>();

		EventLogic eL = new EventLogic();

		eventList = eL.selectEventTodayLogic("testuer");

		for (EventBean event : eventList) {

			System.out.println("イベントID：" + event.getEventId());
			System.out.println("ユーザーID：" + event.getUserId());
			System.out.println("作品ID：" + event.getTitleId());
			System.out.println("イベント名：" + event.getEventName());
			System.out.println("イベント開始日：" + event.getEventStart());
			System.out.println("イベント終了日：" + event.getEventEnd());
			System.out.println("イベントの場所：" + event.getEventLoc());
			System.out.println("イベントサイトURL：" + event.getEventUrl());
			System.out.println("イベント通販サイトURL：" + event.getEventShop());
			System.out.println("終了日カウントダウン：" + event.getEventEndCnt());
			System.out.println("イベント開始日DATE型：" + event.getEventStartDateType());
			System.out.println("イベント終了日DATE型：" + event.getEventEndDateType());
			System.out.println("-------------");

		}

	}

	// イベント情報の更新
	public static void eventUpdateLogicTest() {

		// テストデータの作成
		EventBean event = new EventBean();

		event.setEventId(10);
		event.setUserId("testuer");
		event.setTitleId(1);
		event.setEventName("ヴィレッジヴァンガード");
		event.setEventStart("2022-01-14");
		event.setEventEnd("2022-03-10");
		event.setEventLoc("ヴィレッジヴァンガード 渋谷本店");
		event.setEventUrl("https://www.village-v.co.jp/news/item/12138");

		EventLogic eL = new EventLogic();

		boolean rtn = eL.eventUpdateLogic(event);

		System.out.println("■イベント情報の登録");
		System.out.println("登録結果：" + rtn);

	}

	// イベント情報の削除
	public static void eventDeleteLogicTest() {

		EventLogic eL = new EventLogic();

		boolean rtn = eL.eventDeleteLogic(9);

		System.out.println("■イベント情報の登録");
		System.out.println("登録結果：" + rtn);

	}
}
