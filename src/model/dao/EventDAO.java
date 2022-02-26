package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.entity.EventBean;

public class EventDAO {

	// イベント情報の登録
	public int eventRegister(EventBean event) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "INSERT INTO event (user_id,titele_id,event_name,event_start,event_end,event_loc,event_url,event_shop) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// EventBean（event）より各ユーザー情報の取り出し
			// ユーザーID
			String userId = event.getUserId();
			// 作品ID
			int titleId = event.getTitleId();
			// イベント名
			String eventName = event.getEventName();
			// イベント開始日
			String eventStart = event.getEventStart();
			// イベント終了日
			String eventEnd = event.getEventEnd();
			// イベントの場所
			String eventLoc = event.getEventLoc();
			// イベントサイトURL
			String eventUrl = event.getEventUrl();
			// イベント通販サイトURL
			String eventShop = event.getEventShop();

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// 作品ID
			pStmt.setInt(2, titleId);
			// イベント名
			pStmt.setString(3, eventName);
			// イベント開始日
			//（フォーマットさえ合っていれば、DATE型カラムにはString型からデータを入れられる）
			pStmt.setString(4, eventStart);
			// イベント終了日
			pStmt.setString(5, eventEnd);
			// イベントの場所
			pStmt.setString(6, eventLoc);
			// イベントサイトURL
			pStmt.setString(7, eventUrl);
			// イベント通販サイトURL
			pStmt.setString(8, eventShop);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// イベント情報の取得（修正用）
	public EventBean getEvent(int eventId) throws ClassNotFoundException, SQLException {

		// 取得する記念日・誕生日の初期化
		EventBean event = null;

		// SQL文の組み立て
		String sql = "SELECT * FROM event WHERE event_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// 作品ID
			pStmt.setInt(1, eventId);

			// SQLが実行されて、結果が「res」にドンって入る
		    ResultSet res = pStmt.executeQuery();

		    while(res.next()) {

		    	event = new EventBean();

		    	// DBから取得する値
		    	event.setEventId(res.getInt("event_id"));
		    	event.setUserId(res.getString("user_id"));
		    	event.setTitleId(res.getInt("titele_id"));
		    	event.setEventName(res.getString("event_name"));
		    	event.setEventStart(res.getString("event_start"));
		    	event.setEventEnd(res.getString("event_end"));
		    	event.setEventLoc(res.getString("event_loc"));
		    	event.setEventUrl(res.getString("event_url"));
		    	event.setEventShop(res.getString("event_shop"));

		    	// 使用しない値にnull、O入れておく（DBから取得しない値）
		    	event.setEventStartDateType(null);
		    	event.setEventEndDateType(null);
		    	event.setEventEndCnt(0);

		    }

		}

		// イベント情報を返す
		return event;

	}

	// 期間を指定したイベント情報の取得（作品全件）
	public List<EventBean> selectEventAll(String sYYYYMMDD, String eYYYYMMDD, String userId) throws  ClassNotFoundException, SQLException{

		// 取得するイベント一覧の生成
		List<EventBean> eventList = new ArrayList<EventBean>();

		// SQL文の組み立て
		// String sql = "SELECT eve.event_id, eve.user_id, eve.titele_id, eve.event_name, eve.event_start, eve.event_end, eve.event_loc, eve.event_url, eve.event_shop FROM event eve INNER JOIN title tit ON eve.titele_id = tit.title_id WHERE eve.user_id = ? AND (eve.event_start BETWEEN ? AND ?) AND tit.t_on_OFF = 1 ORDER BY event_start ASC";
		String sql = "SELECT eve.event_id, eve.user_id, eve.titele_id, eve.event_name, eve.event_start, eve.event_end, eve.event_loc, eve.event_url, eve.event_shop FROM event eve INNER JOIN title tit ON eve.titele_id = tit.title_id WHERE eve.user_id = ? AND (? <= eve.event_end AND eve.event_start <= ?) AND tit.t_on_OFF = 1 ORDER BY event_end ASC";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			pStmt.setString(2, sYYYYMMDD);
			pStmt.setString(3, eYYYYMMDD);

			// SQLが実行されて、結果が「res」にドンって入る
			ResultSet res = pStmt.executeQuery();

			while(res.next()) {

				EventBean event = new EventBean();

				// DBから取得する値
				event.setEventId(res.getInt("event_id"));
				event.setUserId(res.getString("user_id"));
				event.setTitleId(res.getInt("titele_id"));
				event.setEventName(res.getString("event_name"));
				event.setEventStart(res.getString("event_start"));
				event.setEventEnd(res.getString("event_end"));
				event.setEventLoc(res.getString("event_loc"));
				event.setEventUrl(res.getString("event_url"));
				event.setEventShop(res.getString("event_shop"));

				// イベント開始日、終了日のDATE型
				// getDate(カラム名)で、DATE型のカラムより、DATE型としてDBより値を取得できる
				// getString(カラム名)で、DATE型のカラムより、String型としてDBより値を取得できる
				event.setEventStartDateType(res.getDate("event_start"));
				event.setEventEndDateType(res.getDate("event_end"));

				// 使用しない値にO入れておく（DBから取得しない値）
		    	event.setEventEndCnt(0);

				// すべて整ったら、リストに格納
				eventList.add(event);

			}

			return eventList;

		}
	}

	// 期間を指定した選択した作品のイベントの一覧を取得
	public List<EventBean> selectEvent(String sYYYYMMDD, String eYYYYMMDD, int titleId) throws ClassNotFoundException, SQLException{

		// 取得するイベント一覧の生成
		List<EventBean> eventList = new ArrayList<EventBean>();

		// SQL文の組み立て
		// String sql = "SELECT eve.event_id, eve.user_id, eve.titele_id, eve.event_name, eve.event_start, eve.event_end, eve.event_loc, eve.event_url, eve.event_shop FROM event eve INNER JOIN title tit ON eve.titele_id = tit.title_id WHERE eve.titele_id = ? AND (eve.event_start BETWEEN ? AND ?) AND tit.t_on_OFF = 1 ORDER BY event_start ASC";
		String sql = "SELECT eve.event_id, eve.user_id, eve.titele_id, eve.event_name, eve.event_start, eve.event_end, eve.event_loc, eve.event_url, eve.event_shop FROM event eve INNER JOIN title tit ON eve.titele_id = tit.title_id WHERE eve.titele_id = ? AND (? <= eve.event_end AND eve.event_start <= ?) AND tit.t_on_OFF = 1 ORDER BY event_start ASC";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// 作品ID
			pStmt.setInt(1, titleId);
			pStmt.setString(2, sYYYYMMDD);
			pStmt.setString(3, eYYYYMMDD);

			// SQLが実行されて、結果が「res」にドンって入る
			ResultSet res = pStmt.executeQuery();

			while(res.next()) {

				EventBean event = new EventBean();

				// DBから取得する値
				event.setEventId(res.getInt("event_id"));
				event.setUserId(res.getString("user_id"));
				event.setTitleId(res.getInt("titele_id"));
				event.setEventName(res.getString("event_name"));
				event.setEventStart(res.getString("event_start"));
				event.setEventEnd(res.getString("event_end"));
				event.setEventLoc(res.getString("event_loc"));
				event.setEventUrl(res.getString("event_url"));
				event.setEventShop(res.getString("event_shop"));

				// イベント開始日、終了日のDATE型
				// getDate(カラム名)で、DATE型のカラムより、DATE型としてDBより値を取得できる
				// getString(カラム名)で、DATE型のカラムより、String型としてDBより値を取得できる
				event.setEventStartDateType(res.getDate("event_start"));
				event.setEventEndDateType(res.getDate("event_end"));

				// 使用しない値にO入れておく（DBから取得しない値）
		    	event.setEventEndCnt(0);

				// すべて整ったら、リストに格納
				eventList.add(event);

			}

		}

		return eventList;

	}

	// ユーザー設定のイベント取得日数分のイベントを取得（ホーム画面、一覧初回表示）
	public List<EventBean> selectEventGet(String tYYYYMMDD, String userId) throws ClassNotFoundException, SQLException {

		// 取得するイベント一覧の生成
		List<EventBean> eventList = new ArrayList<EventBean>();

		// SQL文の組み立て
		String sql = "SELECT eve.event_id, eve.user_id, eve.titele_id, eve.event_name, eve.event_start, eve.event_end, eve.event_loc, eve.event_url, eve.event_shop FROM (event eve INNER JOIN title tit ON eve.titele_id = tit.title_id) INNER JOIN user usr ON eve.user_id = usr.user_id WHERE eve.user_id = ? AND (eve.event_start BETWEEN ? AND DATE_ADD(?, INTERVAL usr.eventget DAY)) AND tit.t_on_OFF = 1 ORDER BY event_start ASC;";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// 作品ID
			pStmt.setString(1, userId);
			pStmt.setString(2, tYYYYMMDD);
			pStmt.setString(3, tYYYYMMDD);

			// SQLが実行されて、結果が「res」にドンって入る
			ResultSet res = pStmt.executeQuery();

			while(res.next()) {

				EventBean event = new EventBean();

				// DBから取得する値
				event.setEventId(res.getInt("event_id"));
				event.setUserId(res.getString("user_id"));
				event.setTitleId(res.getInt("titele_id"));
				event.setEventName(res.getString("event_name"));
				event.setEventStart(res.getString("event_start"));
				event.setEventEnd(res.getString("event_end"));
				event.setEventLoc(res.getString("event_loc"));
				event.setEventUrl(res.getString("event_url"));
				event.setEventShop(res.getString("event_shop"));

				// イベント開始日、終了日のDATE型
				// getDate(カラム名)で、DATE型のカラムより、DATE型としてDBより値を取得できる
				// getString(カラム名)で、DATE型のカラムより、String型としてDBより値を取得できる
				event.setEventStartDateType(res.getDate("event_start"));
				event.setEventEndDateType(res.getDate("event_end"));

				// 使用しない値にO入れておく（DBから取得しない値）
		    	event.setEventEndCnt(0);

				// すべて整ったら、リストに格納
				eventList.add(event);

			}

		}

		return eventList;

	}

	// 今日のイベントの取得
	public List<EventBean> selectEventToday(String tYYYYMMDD, String userId) throws ClassNotFoundException, SQLException{

		// 取得するイベント一覧の生成
		List<EventBean> eventList = new ArrayList<EventBean>();

		// SQL文の組み立て
		// String sql = "SELECT eve.event_id, eve.user_id, eve.titele_id, eve.event_name, eve.event_start, eve.event_end, eve.event_loc, eve.event_url, eve.event_shop FROM event eve INNER JOIN title tit ON eve.titele_id = tit.title_id WHERE eve.user_id = ? AND (? <= eve.event_end AND eve.event_start <= ?) AND tit.t_on_OFF = 1 ORDER BY event_start ASC";
		String sql = "SELECT eve.event_id, eve.user_id, eve.titele_id, eve.event_name, eve.event_start, eve.event_end, eve.event_loc, eve.event_url, eve.event_shop FROM event eve INNER JOIN title tit ON eve.titele_id = tit.title_id WHERE eve.user_id = ? AND (? <= eve.event_end AND eve.event_start <= ?) AND tit.t_on_OFF = 1 ORDER BY event_end ASC";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// 今日の日付（終了日との比較）
			pStmt.setString(2, tYYYYMMDD);
			// 今日の日付（開始日との比較）
			pStmt.setString(3, tYYYYMMDD);

			// SQLが実行されて、結果が「res」にドンって入る
			ResultSet res = pStmt.executeQuery();

			while(res.next()) {

				EventBean event = new EventBean();

				// DBから取得する値
				event.setEventId(res.getInt("event_id"));
				event.setUserId(res.getString("user_id"));
				event.setTitleId(res.getInt("titele_id"));
				event.setEventName(res.getString("event_name"));
				event.setEventStart(res.getString("event_start"));
				event.setEventEnd(res.getString("event_end"));
				event.setEventLoc(res.getString("event_loc"));
				event.setEventUrl(res.getString("event_url"));
				event.setEventShop(res.getString("event_shop"));

				// イベント開始日、終了日のDATE型
				// getDate(カラム名)で、DATE型のカラムより、DATE型としてDBより値を取得できる
				// getString(カラム名)で、DATE型のカラムより、String型としてDBより値を取得できる
				event.setEventStartDateType(res.getDate("event_start"));
				// イベント終了日のDATE型はまた使うので、変数に入れておく
				Date eventEnd = res.getDate("event_end");
				event.setEventEndDateType(eventEnd);

				// 今日から終了日までのカウント計算
				// 今日をString型からLocalDate型に変換
				LocalDate nowLD = LocalDate.parse(tYYYYMMDD, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				// 終了日をDATE型からLocalDate型に変換
				// これは、DATE型がjava.sql.Dateだった場合
				// LocalDate endLD = eventEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				// これは、DATE型がjava.time.だった場合
				// LocalDate endLD = LocalDate.ofInstant(eventEnd.toInstant(), ZoneId.systemDefault());
				// これが、DATE型がjava.util.Dateだった場合
				// java.sql.Timestampを経由してjava.sqlに変換して、そこからLocalDate型に変換する
				LocalDateTime localDateTime = new Timestamp(eventEnd.getTime()).toLocalDateTime();
				LocalDate endLD = localDateTime.toLocalDate();
				// 今日から終了日までをカウント
				int countDWN = (int) ChronoUnit.DAYS.between(nowLD, endLD);

				// 今日から終了日までを格納
				event.setEventEndCnt(countDWN);

				// すべて整ったら、リストに格納
				eventList.add(event);

			}
		}

		return eventList;
	}

	// イベント情報の更新
	public int eventUpdate(EventBean event) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "UPDATE event SET user_id = ?, titele_id = ?, event_name = ?, event_start = ?, event_end = ?, event_loc = ?, event_url = ?, event_shop = ? WHERE event_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// EventBean（even）より各イベントの情報の取り出し
			// イベントID
			int eventId = event.getEventId();
			// ユーザーID
			String userId = event.getUserId();
			// 作品ID
			int titleId = event.getTitleId();
			// イベント名
			String eventName = event.getEventName();
			// イベント開始日
			String eventStart = event.getEventStart();
			// イベント終了日
			String eventEnd = event.getEventEnd();
			// イベントの場所
			String eventLoc = event.getEventLoc();
			// イベントサイトURL
			String eventUrl = event.getEventUrl();
			// イベント通販サイトURL
			String eventShop = event.getEventShop();

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// 作品ID
			pStmt.setInt(2, titleId);
			// イベント名
			pStmt.setString(3, eventName);
			// イベント開始日
			pStmt.setString(4, eventStart);
			// イベント終了日
			pStmt.setString(5, eventEnd);
			// イベントの場所
			pStmt.setString(6, eventLoc);
			// イベントサイト
			pStmt.setString(7, eventUrl);
			// イベント通販サイトURL
			pStmt.setString(8, eventShop);
			// イベントID
			pStmt.setInt(9, eventId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// イベント情報の削除
	public int eventDelete(int eventId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "DELETE FROM event WHERE event_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// 記念日ID
			pStmt.setInt(1, eventId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// ユーザー削除によるイベント情報の削除
	public int userEventDelete(String userId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "DELETE FROM event WHERE user_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
				return cnt;
	}

}
