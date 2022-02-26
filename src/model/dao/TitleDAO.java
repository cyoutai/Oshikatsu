package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.TitleBean;

public class TitleDAO {

	// 作品名の重複をチェック
	public boolean titleNameCheck(String titileName, String userId) throws ClassNotFoundException, SQLException {

		TitleBean titleInfo = null;

		// SQL文の組み立て
		String sql = "SELECT * FROM title WHERE user_id = ? AND tetle = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// 作品名
			pStmt.setString(2, titileName);

			// SQLが実行されて、結果が「res」にドンって入る
			ResultSet res = pStmt.executeQuery();

		    if(res.next()) {

		    	titleInfo = new TitleBean();

		    	// 召喚した値のセット
		    	// 作品ID
		    	titleInfo.setTitleId(res.getInt("title_id"));
		    	// ユーザーID
		    	titleInfo.setUserId(res.getString("user_id"));
		    	// 作品名
		    	titleInfo.setTitleName(res.getString("tetle"));
		    	// 作品名（よみがな）
		    	titleInfo.setTitleKn(res.getString("title_kn"));
		    	// 表示、非表示
		    	titleInfo.settOnOff(res.getInt("t_on_OFF"));

		    }

		    // if文で結果を返す(「titleInfo」の中身がnullか否か)
		    // 「titleInfo」に結果が入っている = 作品名が存在する（true）
		    // 「titleInfo」がnull = 作品名が存在しない（false）
		    if(titleInfo != null) {

		    	return true;

		    } else {

		    	return false;

		    }

		}

	}

	// 作品登録（ユーザーID、作品名、かな、表示非表示）
	public int titleRegister(TitleBean title) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "INSERT INTO title (user_id, tetle, title_kn, t_on_OFF) VALUES(?, ?, ?, ?)";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// TitleBean（title）より各ユーザー情報の取り出し
			// ユーザーID
			String userId = title.getUserId();
			// 作品名
			String titleName = title.getTitleName();
			// 作品名（かな）
			String titleKn = title.getTitleKn();
			// 表示、非表示
			int tOnOff = title.gettOnOff();

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// 作品名
			pStmt.setString(2, titleName);
			// 作品名（かな）
			pStmt.setString(3, titleKn);
			// 表示（1）、非表示（0）
			pStmt.setInt(4, tOnOff);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// 作品名から作品IDを返す
	public int getTitleId(String titleName, String userId) throws ClassNotFoundException, SQLException {

		// 取得する作品IDの初期化
		int titleId = 0;

		// SQL文の組み立て
		String sql = "SELECT title_id FROM title WHERE user_id = ? AND tetle = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// 作品名
			pStmt.setString(2, titleName);

			// SQLが実行されて、結果が「res」にドンって入る
		    ResultSet res = pStmt.executeQuery();

		    if(res.next()) {

		    	// 作品IDを取得
		    	titleId = res.getInt("title_id");

		    }

		}

		// 作品IDを返す（取得できなかった場合は0を返す）
		return titleId;

	}


	// 作品IDから、作品情報（ユーザーID、作品名、かな、表示非表示）を取得
	public TitleBean getTitleInfo(int titleId) throws ClassNotFoundException, SQLException {

		// 取得する作品IDの初期化
		TitleBean title = null;

		// SQL文の組み立て
		String sql = "SELECT * FROM title WHERE title_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// 作品ID
			pStmt.setInt(1, titleId);

			// SQLが実行されて、結果が「res」にドンって入る
		    ResultSet res = pStmt.executeQuery();

		    if(res.next()) {

		    	title = new TitleBean();

		    	title.setTitleId(res.getInt("title_id"));
		    	title.setUserId(res.getString("user_id"));
		    	title.setTitleName(res.getString("tetle"));
		    	title.setTitleKn(res.getString("title_kn"));
		    	title.settOnOff(res.getInt("t_on_OFF"));

		    }

		}

		// 作品情報を返す
		return title;

	}

	// 作品一覧を返す
	public List<TitleBean> selectTitleAll(String userId) throws ClassNotFoundException, SQLException{

		// 取得する作品一覧の生成
		List<TitleBean> titleList = new ArrayList<TitleBean>();

		// SQL文の組み立て
		String sql = "SELECT * FROM title WHERE user_id = ? ORDER BY title_kn ASC";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);

			// SQLが実行されて、結果が「res」にドンって入る
			ResultSet res = pStmt.executeQuery();

			while(res.next()) {

				TitleBean title = new TitleBean();

				// DBから取得できる値
				title.setTitleId(res.getInt("title_id"));
				title.setUserId(res.getString("user_id"));
				title.setTitleName(res.getString("tetle"));
				title.setTitleKn(res.getString("title_kn"));
				title.settOnOff(res.getInt("t_on_OFF"));

				// DBから取得しない値（使用しない公式サイト、Twitterはnullを入れておく）
				title.setSite(null);
				title.setTwitter(null);

				// 作品一覧に追加
				titleList.add(title);

			}

		}

		// 作品一覧を返す
		return titleList;

	}

	// 「表示」設定の作品一覧を返す
	public List<TitleBean> selectTitleOn(String userId) throws ClassNotFoundException, SQLException{

		// 取得する作品一覧の初期化
		List<TitleBean> titleList = new ArrayList<TitleBean>();

		// SQL文の組み立て
		String sql = "SELECT * FROM title WHERE user_id = ? AND t_on_OFF = 1 ORDER BY title_kn ASC";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);

			// SQLが実行されて、結果が「res」にドンって入る
			ResultSet res = pStmt.executeQuery();

			while(res.next()) {

				TitleBean title = new TitleBean();

				// DBから取得できる値
				title.setTitleId(res.getInt("title_id"));
				title.setUserId(res.getString("user_id"));
				title.setTitleName(res.getString("tetle"));
				title.setTitleKn(res.getString("title_kn"));
				title.settOnOff(res.getInt("t_on_OFF"));

				// DBから取得しない値（使用しない公式サイト、Twitterはnullを入れておく）
				title.setSite(null);
				title.setTwitter(null);

				// 作品一覧に追加
				titleList.add(title);

			}
		}

		// 作品一覧を返す
		return titleList;

	}

	// 作品情報の更新
	public int titleUpdate(TitleBean title) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "UPDATE title SET user_id = ?, tetle = ?, title_kn = ?, t_on_OFF = ? WHERE title_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// TitleBean（title）より各ユーザー情報の取り出し
			// 作品ID
			int titleId = title.getTitleId();
			// ユーザーID
			String userId = title.getUserId();
			// 作品名
			String titleName = title.getTitleName();
			// 作品名（かな）
			String titleKn = title.getTitleKn();
			// 表示、非表示
			int tOnOff = title.gettOnOff();

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// 作品名
			pStmt.setString(2, titleName);
			// 作品名（かな）
			pStmt.setString(3, titleKn);
			// 表示、非表示
			pStmt.setInt(4, tOnOff);
			// 作品ID
			pStmt.setInt(5, titleId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// 表示、非表示の更新
	public int titleOnOffUpdate(int titleId, int tOnOff) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "UPDATE title SET t_on_OFF = ? WHERE title_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 表示、非表示
			pStmt.setInt(1, tOnOff);
			// 作品ID
			pStmt.setInt(2, titleId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// 作品情報の削除
	public int titleDelete(int titleId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "DELETE FROM title WHERE title_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// 作品ID
			pStmt.setInt(1, titleId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// ユーザー削除による作品情報の削除
	public int userTitleDelete(String userId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "DELETE FROM title WHERE user_id = ?";

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
