package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.TitleTwitter;

public class TitleTwitterDAO {

	// Twitter情報の登録
	public int titleTwitterRegister(int titleId, String twitterId, String userId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "INSERT INTO title_twitter (user_id,titele_id,twitter_id) VALUES(?, ?, ?);";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){


			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// 作品ID
			pStmt.setInt(2, titleId);
			// TwitterID
			pStmt.setString(3, twitterId);


			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// Twitter情報を取得
	public List<TitleTwitter> getTitleTwitter(int titleId) throws ClassNotFoundException, SQLException{

		// 取得するサイト一覧の生成
		List<TitleTwitter> TwitterList = new ArrayList<TitleTwitter>();

		// SQL文の組み立て
		String sql = "SELECT * FROM title_twitter WHERE titele_id = ?";

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

			while(res.next()) {

				TitleTwitter titleTwitter = new TitleTwitter();

				// DBから値を取得
				titleTwitter.settTwitterId(res.getInt("t_twitter_id"));
				titleTwitter.setTwitterId(res.getString("twitter_id"));

				// 作品一覧に追加
				TwitterList.add(titleTwitter);

			}

			// 公式サイト一覧を返す
			return TwitterList;

		}

	}

	// Twitterの更新
	public int titleTwitterUpdate(TitleTwitter twitter) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "UPDATE title_twitter SET twitter_id = ? WHERE t_twitter_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// TitleSite（site）より各ユーザー情報の取り出し
			// サイトID
			int tTwitterId = twitter.gettTwitterId();
			// 公式サイトURL
			String twitterId = twitter.getTwitterId();

			// 読み込んだSQLの「?」に値をセット
			// 公式サイトURL
			pStmt.setString(1, twitterId);
			// サイトID
			pStmt.setInt(2, tTwitterId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// Twitter情報の1件削除
	public int titleTwitterOneDelete(int tTwitterId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "DELETE FROM title_twitter WHERE t_twitter_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// 管理上のTwitterID
			pStmt.setInt(1, tTwitterId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// 作品情報削除によるTwitter情報の削除
	public int titleTwitterDelete(int titleId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "DELETE FROM title_twitter WHERE titele_id = ?";

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

	// ユーザー削除によるTwitter情報の削除
	public int userTitleTwitterDelete(String userId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "DELETE FROM title_twitter WHERE user_id = ?";

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
