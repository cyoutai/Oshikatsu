package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.entity.UserBean;

public class UserDAO {

	// ユーザーIDの重複をチェック
	public boolean userIdCheck(String userId) throws ClassNotFoundException, SQLException {

		// ドン！って入った「res」からユーザー情報を格納するやつを初期化
		UserBean userInfo = null;

		// SQL文の組み立て
		String sql = "SELECT * FROM user WHERE user_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」にユーザーIDをセット
			pStmt.setString(1, userId);

			// SQLが実行されて、結果が「res」にドンって入る
			ResultSet res = pStmt.executeQuery();

		    while(res.next()) {

		    	userInfo = new UserBean();

		    	// 召喚した値のセット
		    	// ユーザーID
		    	userInfo.setUserId(res.getString("user_id"));
		    	// パスワード
		    	userInfo.setPass(res.getString("pass"));
		    	// カウントダウン開始日数
		    	userInfo.setCountDwn(res.getInt("countdwn"));
		    	// イベントの直近〇ヶ月分を表示
		    	userInfo.setEventGet(res.getInt("eventget"));

		    }

		    // if文で結果を返す(「userInfo」の中身がnullか否か)
		    // 「userInfo」に結果が入っている = ユーザーIDが存在する（true）
		    // 「userInfo」がnull = ユーザーIDが存在しない（false）
		    if(userInfo != null) {

		    	return true;

		    } else {

		    	return false;

		    }

		}

	}

	// ユーザー登録を行う
	public int userRegister(UserBean user) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "INSERT INTO user VALUES(?,?,?,?)";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// UserBean（user）より各ユーザー情報の取り出し
			// ユーザーID
			String userId = user.getUserId();
			// パスワード
			String pass = user.getPass();
			// カウントダウン開始日数
			int countDwn = user.getCountDwn();
			// イベントの直近〇ヶ月分を表示
			int eventGer = user.getEventGet();

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// パスワード
			pStmt.setString(2, pass);
			// カウントダウン開始日数
			pStmt.setInt(3, countDwn);
			// イベントの直近〇ヶ月分を表示
			pStmt.setInt(4, eventGer);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// ログイン認証
	public UserBean userLogin(UserBean user) throws ClassNotFoundException, SQLException {

		// 戻すユーザー情報を初期化
		// 合致するユーザー情報が取れなかった場合（パスワード誤りの場合）、nullを返す
		// 呼び出し元で、先にユーザーIDの重複チェックをしているので、
		// ここで値が取り出せなかったら、パスワード違いということ
		UserBean userInfo = null;

		// SQL文の組み立て
		String sql = "SELECT * FROM user WHERE user_id = ? AND pass = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// UserBean（user）より各ユーザー情報の取り出し
			// ユーザーID
			String userId = user.getUserId();
			// パスワード
			String pass = user.getPass();

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// パスワード
			pStmt.setString(2, pass);

			// SQLが実行されて、結果が「res」にドンって入る
		    ResultSet res = pStmt.executeQuery();

		    // ドン！と値が入ったやつの最初の行になんか入っていたら実行する
		    if(res.next()) {

		    	userInfo = new UserBean();

		    	// 召喚した値のセット
		    	// ユーザーID
		    	userInfo.setUserId(res.getString("user_id"));
		    	// パスワード
		    	userInfo.setPass(res.getString("pass"));
		    	// カウントダウン開始日数
		    	userInfo.setCountDwn(res.getInt("countdwn"));
		    	// イベントの直近〇ヶ月分を表示
		    	userInfo.setEventGet(res.getInt("eventget"));

		    }

		    // ユーザー情報を戻す
		    // ユーザー召喚できていなかったら、初期値のnullが返る（パスワード間違い）
			return userInfo;

		}

	}

	// ユーザー情報の更新
	public int userUpdate(UserBean user) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "UPDATE user SET pass = ?, countdwn = ?, eventget = ? WHERE user_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// UserBean（user）より各ユーザー情報の取り出し
			// ユーザーID
			String userId = user.getUserId();
			// パスワード
			String pass = user.getPass();
			// カウントダウン開始日数
			int countDwn = user.getCountDwn();
			// イベントの直近〇ヶ月分を表示
			int eventGet = user.getEventGet();

			// 読み込んだSQLの「?」に値をセット
			// パスワード
			pStmt.setString(1, pass);
			// カウントダウン開始日数
			pStmt.setInt(2, countDwn);
			// イベントの直近〇ヶ月分を表示
			pStmt.setInt(3, eventGet);
			// ユーザーID
			pStmt.setString(4, userId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// ユーザー削除
	public int userDelete(String userId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "DELETE FROM user WHERE user_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」にユーザーIDをセット
			pStmt.setString(1, userId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}
}
