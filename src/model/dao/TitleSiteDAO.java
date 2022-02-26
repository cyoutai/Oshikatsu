

package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.TitleSite;

public class TitleSiteDAO {

	// 公式サイト情報の登録
	public int titleSiteRegister(int titleId, String titleSiteUrl, String userId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "INSERT INTO title_site (user_id,titele_id,site_url) VALUES(?, ?, ?);";

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
			// 公式サイトURL
			pStmt.setString(3, titleSiteUrl);


			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// 公式サイト情報を取得
	public List<TitleSite> getTitleSite(int titleId) throws ClassNotFoundException, SQLException{

		// 取得するサイト一覧の生成
		List<TitleSite> siteList = new ArrayList<TitleSite>();

		// SQL文の組み立て
		String sql = "SELECT * FROM title_site WHERE titele_id = ?";

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

				TitleSite titleSite = new TitleSite();

				// DBから値を取得
				titleSite.setSiteId(res.getInt("site_id"));
				titleSite.setSiteUrl(res.getString("site_url"));

				// 作品一覧に追加
				siteList.add(titleSite);

			}

			// 公式サイト一覧を返す
			return siteList;

		}
	}

	// 公式サイトの更新
	public int titleSiteUpdate(TitleSite site) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "UPDATE title_site SET site_url = ? WHERE site_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// TitleSite（site）より各ユーザー情報の取り出し
			// サイトID
			int siteId = site.getSiteId();
			// 公式サイトURL
			String siteUrl = site.getSiteUrl();

			// 読み込んだSQLの「?」に値をセット
			// 公式サイトURL
			pStmt.setString(1, siteUrl);
			// サイトID
			pStmt.setInt(2, siteId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// 公式サイト1件の削除
	public int titleSiteOneDelete(int siteId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "DELETE FROM title_site WHERE site_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// サイトID
			pStmt.setInt(1, siteId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();
		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// 作品情報削除による公式サイトの削除
	public int titleSiteDelete(int titleId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "DELETE FROM title_site WHERE titele_id = ?";

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

	// ユーザー削除による公式サイト情報の削除
	public int userTitleSiteDelete(String userId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "DELETE FROM title_site WHERE user_id = ?";

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
