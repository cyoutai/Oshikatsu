package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.TitleBean;
import model.entity.TitleSite;
import model.entity.TitleTwitter;

public class TransactionDAO {

	// ユーザー削除の全データ削除
	public boolean deleteUserAll(String userId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化（配列にする）
		// 複数のSQLをaddBatch()でバッチ処理化し、
		// executeBatch()で複数SQLを実行するので、
		// 戻り値が各SQLの結果を格納した配列でくる。
		int cnt[];

		// SQL文の組み立て
		String sqlUser = "DELETE FROM user WHERE user_id = '" + userId + "' ;";
		String sqlTitle = "DELETE FROM title WHERE user_id = '" + userId + "' ;";
		String sqlTSite = "DELETE FROM title_site WHERE user_id = '" + userId + "' ;";
		String sqlTTwitter = "DELETE FROM title_twitter WHERE user_id = '" + userId + "' ;";
		String sqlAnniv = "DELETE FROM anniversary WHERE user_id = '" + userId + "' ;";
		String sqlEvent = "DELETE FROM event WHERE user_id = '" + userId + "' ;";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// DBにSQLを送るやつを作る
				Statement stmt = con.createStatement();){

			// オートコミットをオフにする
			con.setAutoCommit(false);

			// SQLをバッチ処理に追加していく
			stmt.addBatch(sqlTSite);
			stmt.addBatch(sqlTTwitter);
			stmt.addBatch(sqlAnniv);
			stmt.addBatch(sqlEvent);
			stmt.addBatch(sqlTitle);
			stmt.addBatch(sqlUser);

			// SQLバッチ処理の実行
			cnt = stmt.executeBatch();

			// SQL実行の戻り値をチェックし、
			// -3があれば、ロールバックする
			// 0以上の場合は、SQL文の実行によって更新された行数、
			// -2の場合は正常終了したものの更新された行数がわからない、
			// -3の場合は処理に失敗した事を示しています。
			for (int i = 0; i < cnt.length; i++) {

				if(cnt[i] == -3) {

					// 戻り値に-3があった場合は、ロールバック
					con.rollback();
					return false;

				}
				if(i == cnt.length - 1) {

					// 配列のお尻まで何事もなくチェックできたらコミット
					con.commit();

				}

			}

		}
		return true;

	}

	// 作品登録（作品DB、サイトDB、TwitterDBの連携3つ）をする
	public boolean titleRegisterAll(TitleBean title) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化（作品名登録用→バッチ処理化しない）
		int cntTitle = 0;
		// 更新件数の初期化（サイト登録のバッチ処理用）
		int cntSite[];
		// 更新件数の初期化（Twitter登録のバッチ処理用）
		int cntTwitter[];

		// 取得する作品ID
		int titleId = 0;

		// TitleBean（title）より各ユーザー情報の取り出し
		// ユーザーID
		String userId = title.getUserId();
		// 作品名
		String titleName = title.getTitleName();
		// 作品名（かな）
		String titleKn = title.getTitleKn();
		// 表示、非表示
		int tOnOff = title.gettOnOff();

		// SQL文の組み立て
		String sqlTitle = "INSERT INTO title (user_id, tetle, title_kn, t_on_OFF) VALUES('" +
							userId + "', '" + titleName + "', '" + titleKn + "', '" + tOnOff + "');";
		String sqlGetTitleId = "SELECT title_id FROM title WHERE user_id = '" + userId + "' AND tetle = '" + titleName + "';";
		String sqlSite = "INSERT INTO title_site (user_id,titele_id,site_url) VALUES(?, ?, ?);";
		String sqlTwitter = "INSERT INTO title_twitter (user_id,titele_id,twitter_id) VALUES(?, ?, ?);";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく（サイト登録用SQL）
				PreparedStatement pStmtS = con.prepareStatement(sqlSite);

				// SQLを先読みしておく（Twitter登録用SQL）
				PreparedStatement pStmtT = con.prepareStatement(sqlTwitter);

				// DBにSQLを送るやつを作る（タイトル登録用）
				Statement stmtT = con.createStatement();

				// DBにSQLを送るやつを作る（作品ID取得用）
				Statement stmtG = con.createStatement();){

			// オートコミットをオフにする
			con.setAutoCommit(false);

			// 作品の登録
			cntTitle = stmtT.executeUpdate(sqlTitle);

			if(cntTitle != 0) {

				// 作品IDの取得
				// SQLが実行されて、結果が「res」にドンって入る
				ResultSet res = stmtG.executeQuery(sqlGetTitleId);

				// どのみち1件しか取得しないので、if文より分け
				// カーソル読み込める＝値ある＝trueで、作品IDを取得
				if(res.next()) {

			    	// 作品IDを取得
			    	titleId = res.getInt("title_id");

			    } else {

			    	// カーソル読み込めない＝値ない＝取得失敗＝falseは、
			    	// ロールバックさせる
			    	con.rollback();
					return false;

			    }

			} else {

				// 作品登録ができなかったら、ロールバック
				con.rollback();
				return false;

			}

			// サイト情報のリストサイズが0（空）でなければ実行
			if(title.getSite().size() != 0) {

				for (TitleSite tS : title.getSite()) {

					String titleSiteUrl = tS.getSiteUrl();

					// 読み込んだSQLの「?」に値をセット
					// ユーザーID
					pStmtS.setString(1, userId);
					// 作品ID
					pStmtS.setInt(2, titleId);
					// 公式サイトURL
					pStmtS.setString(3, titleSiteUrl);

					// SQLのバッチ登録
					pStmtS.addBatch();

				}

				// SQLバッチ処理の実行（サイト登録）
				cntSite = pStmtS.executeBatch();

				// サイト登録のエラーチェック
				// 全部問題なかったとしても、コミットはまだしない
				// （Twitterのエラーチェックがまだなので）
				// SQL実行の戻り値をチェックし、
				// -3があれば、ロールバックする
				// 0以上の場合は、SQL文の実行によって更新された行数、
				// -2の場合は正常終了したものの更新された行数がわからない、
				// -3の場合は処理に失敗した事を示しています。
				for (int i = 0; i < cntSite.length; i++) {

					if(cntSite[i] == -3) {

					// 戻り値に-3があった場合は、ロールバック
					con.rollback();
					return false;

					}

				}
			}

			// サイト情報のリストサイズが0（空）でなければ実行
			if(title.getTwitter().size() != 0) {

				for (TitleTwitter tT : title.getTwitter()) {

					String twitterId = tT.getTwitterId();

					// 読み込んだSQLの「?」に値をセット
					// ユーザーID
					pStmtT.setString(1, userId);
					// 作品ID
					pStmtT.setInt(2, titleId);
					// TwitterID
					pStmtT.setString(3, twitterId);

					// SQLのバッチ登録
					pStmtT.addBatch();

				}

				// SQLバッチ処理の実行（Twitter登録）
				cntTwitter = pStmtT.executeBatch();

				// こっちはTwitter登録のエラーチェック
				// Twitter登録も問題なかったら、コミットする
				for (int i = 0; i < cntTwitter.length; i++) {

					if(cntTwitter[i] == -3) {

					// 戻り値に-3があった場合は、ロールバック
					con.rollback();
					return false;

					}
	//				if(i == cntTwitter.length - 1) {
	//
	//					// 配列のお尻まで何事もなくチェックできたらコミット
	//					con.commit();
	//
	//				}

				}
			}

			// 何事もなければコミット
			con.commit();

		}


		return true;

	}

	// 作品情報の更新
	public boolean titleUpdateAll(TitleBean title) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化（作品名更新用→バッチ処理化しない）
		int cntTitle = 0;
		// 更新件数の初期化（サイト更新のバッチ処理用）
		int cntUpSite[];
		// 更新件数の初期化（Twitter更新のバッチ処理用）
		int cntUpTwitter[];

		// 登録件数の初期化（サイト登録のバッチ処理用）
		int cntInSite[];
		// 登録件数の初期化（Twitter登録のバッチ処理用）
		int cntInTwitter[];

		// 削除件数の初期化（サイト削除のバッチ処理用）
		int cntDelSite[];
		// 削除件数の初期化（Twitter削除のバッチ処理用）
		int cntDelTwitter[];

		// 比較元のデータ取得用（サイト情報）
		List<TitleSite> siteList = new ArrayList<TitleSite>();
		// 比較元のデータ取得用（Twitter情報）
		List<TitleTwitter> TwitterList = new ArrayList<TitleTwitter>();

		// SQL文の組み立て（更新用）
		String sqlUpTitle = "UPDATE title SET user_id = ?, tetle = ?, title_kn = ?, t_on_OFF = ? WHERE title_id = ?";
		String sqlUpSite = "UPDATE title_site SET site_url = ? WHERE site_id = ?";
		String sqlUpTwitter = "UPDATE title_twitter SET twitter_id = ? WHERE t_twitter_id = ?";

		// SQL文の組み立て（元々のデータを取ってくる）
		String sqlGetSite = "SELECT * FROM title_site WHERE titele_id = ?";
		String sqlGetTwitter = "SELECT * FROM title_twitter WHERE titele_id = ?";

		// SQL文の組み立て（インサート用）
		String sqlInSite = "INSERT INTO title_site (user_id,titele_id,site_url) VALUES(?, ?, ?)";
		String sqlInTwitter = "INSERT INTO title_twitter (user_id,titele_id,twitter_id) VALUES(?, ?, ?)";

		// SQL文の組み立て（デリート用）
		String sqlDelSite = "DELETE FROM title_site WHERE site_id = ?";
		String sqlDelTwitter = "DELETE FROM title_twitter WHERE t_twitter_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく（作品更新用SQL）
				PreparedStatement pStmtTi = con.prepareStatement(sqlUpTitle);

				// SQLを先読みしておく（サイト更新用SQL）
				PreparedStatement pStmtS = con.prepareStatement(sqlUpSite);

				// SQLを先読みしておく（Twitter更新用SQL）
				PreparedStatement pStmtTw = con.prepareStatement(sqlUpTwitter);

				// SQLを先読みしておく（サイト情報取得用SQL）
				PreparedStatement pStmtGS = con.prepareStatement(sqlGetSite);

				// SQLを先読みしておく（Twitter情報取得用SQL）
				PreparedStatement pStmtGT = con.prepareStatement(sqlGetTwitter);

				// SQLを先読みしておく（サイト情報登録用SQL）
				PreparedStatement pStmtIS = con.prepareStatement(sqlInSite);

				// SQLを先読みしておく（サイト情報登録用SQL）
				PreparedStatement pStmtIT = con.prepareStatement(sqlInTwitter);

				// SQLを先読みしておく（サイト削除用SQL）
				PreparedStatement pStmtDS = con.prepareStatement(sqlDelSite);

				// SQLを先読みしておく（サイト削除用SQL）
				PreparedStatement pStmtDT = con.prepareStatement(sqlDelTwitter);){

			// オートコミットをオフにする
			con.setAutoCommit(false);

			// 作品情報の組み立て
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
			pStmtTi.setString(1, userId);
			// 作品名
			pStmtTi.setString(2, titleName);
			// 作品名（かな）
			pStmtTi.setString(3, titleKn);
			// 表示、非表示
			pStmtTi.setInt(4, tOnOff);
			// 作品ID
			pStmtTi.setInt(5, titleId);

			// 作品情報の登録
			// SQLが実行されて、更新件数が「cntTitle」に入る
			cntTitle = pStmtTi.executeUpdate();

			// 作品登録のエラーチェック
			// 登録ができていない（戻り値が0）なら、ロールバック
			if(cntTitle == 0) {

				// 作品登録ができなかったら、ロールバック
				con.rollback();
				return false;

			}

			// 比較元のデータを取得（サイト情報）
			// 読み込んだSQLの「?」に値をセット
			pStmtGS.setInt(1, titleId);

			// SQLが実行されて、結果が「res」にドンって入る
			ResultSet resS = pStmtGS.executeQuery();

			// 「ドン！」って入ったやつを、一つ一つ取り出して、リストに格納
			// 「siteList」に比較元のデータが入る
			while(resS.next()) {

				TitleSite titleSite = new TitleSite();

				// DBから値を取得
				titleSite.setSiteId(resS.getInt("site_id"));
				titleSite.setSiteUrl(resS.getString("site_url"));

				// 作品一覧に追加
				siteList.add(titleSite);

			}

			// SQLのバッチ登録数のカウント（サイト情報）
			// インサート用
			int cntSI = 0;
			// アップデート用
			int cntSU = 0;
			// デリート用
			int cntSD = 0;


			// 更新データのより分け（サイト情報）
			// インサートとアップデートのより分ける
			// 更新データのサイトIDが「0」なら、インサート
			// オブジェクトに値格納時、サイトIDがない時は、
			// デフォルトで「0」が格納される
			// それ以外は同じサイトIDなので、アップデート
			// デリートは、そもそも更新データにデータがないので、
			// 次に元データと比較して、消されたサイトIDを探す
			for (TitleSite tS : title.getSite()) {

				if(tS.getSiteId() == 0) {

					// サイトIDが「0」の場合は、インサート
					// インサートのSQLを組み立て

					// 公式サイトURL
					String titleSiteUrl = tS.getSiteUrl();

					// 読み込んだSQLの「?」に値をセット
					// ユーザーID
					pStmtIS.setString(1, userId);
					// 作品ID
					pStmtIS.setInt(2, titleId);
					// 公式サイトURL
					pStmtIS.setString(3, titleSiteUrl);

					// SQLのバッチ登録
					pStmtIS.addBatch();

					// インサートSQLのカウント
					cntSI++;

				} else {

					// サイトIDが「0」以外の場合は、アップデート
					// アップデートのSQLを組み立て

					// i番目に格納のTitleSiteより各情報の取り出し
					// サイトID
					int siteId = tS.getSiteId();
					// 公式サイトURL
					String titleSiteUrl = tS.getSiteUrl();

					// 読み込んだSQLの「?」に値をセット
					// 公式サイトURL
					pStmtS.setString(1, titleSiteUrl);
					// サイトID
					pStmtS.setInt(2, siteId);

					// SQLのバッチ登録
					pStmtS.addBatch();

					// アップデートSQLのカウント
					cntSU++;

				}

			}

			// デリートがあるかの確認（サイト情報）
			// 比較元→更新データで比較
			// 更新データに比較元がない場合は、デリート用のバッチ処理にぶち込む
			// 比較元にサイトIDがない場合の判定
			// → ループお尻までチェックしていたら無い（＝デリート）
			for (int i = 0; i < siteList.size(); i++) {

				for (int j = 0; j < title.getSite().size(); j++) {

					// 比較元と更新データに同じサイトIDがあれば、
					// 速攻でループ抜ける
					if(siteList.get(i).getSiteId() == title.getSite().get(j).getSiteId()) {

						break;

					}

					if(j == title.getSite().size() - 1) {

						// 更新データのサイトIDに比較元のサイトIDがない場合
						// デリートのSQLを組み立て
						pStmtDS.setInt(1, siteList.get(i).getSiteId());

						// SQLのバッチ登録
						pStmtDS.addBatch();

						// デリートSQLのカウント
						cntSD++;

					}
				}
			}


			// バッチ処理に登録があれば、SQLを実行（サイト情報）
			// インサート（サイト情報）
			if(cntSI > 0) {

				cntInSite = pStmtIS.executeBatch();

				// サイト登録のエラーチェック
				// 全部問題なかったとしても、コミットはまだしない
				// （Twitterのエラーチェックがまだなので）
				// SQL実行の戻り値をチェックし、
				// -3があれば、ロールバックする
				// 0以上の場合は、SQL文の実行によって更新された行数、
				// -2の場合は正常終了したものの更新された行数がわからない、
				// -3の場合は処理に失敗した事を示しています。
				for (int i = 0; i < cntInSite.length; i++) {

					if(cntInSite[i] == -3) {

					// 戻り値に-3があった場合は、ロールバック
					con.rollback();
					return false;

					}

				}

			}

			// アップデート（サイト情報）
			if(cntSU > 0) {

				cntUpSite = pStmtS.executeBatch();

				// エラーチェック（詳細は↑）
				for (int i = 0; i < cntUpSite.length; i++) {

					if(cntUpSite[i] == -3) {

					// 戻り値に-3があった場合は、ロールバック
					con.rollback();
					return false;

					}

				}

			}
			// デリート（サイト情報）
			if(cntSD > 0) {

				cntDelSite = pStmtDS.executeBatch();

				// エラーチェック（詳細は↑）
				for (int i = 0; i < cntDelSite.length; i++) {

					if(cntDelSite[i] == -3) {

					// 戻り値に-3があった場合は、ロールバック
					con.rollback();
					return false;

					}

				}

			}


			// ここからTwitter

			// 比較元のデータを取得（Twitter情報）
			// 読み込んだSQLの「?」に値をセット
			pStmtGT.setInt(1, titleId);

			// SQLが実行されて、結果が「res」にドンって入る
			ResultSet resT = pStmtGT.executeQuery();

			// 「ドン！」って入ったやつを、一つ一つ取り出して、リストに格納
			// 「siteList」に比較元のデータが入る
			while(resT.next()) {

				TitleTwitter titleTwitter = new TitleTwitter();

				// DBから値を取得
				titleTwitter.settTwitterId(resT.getInt("t_twitter_id"));
				titleTwitter.setTwitterId(resT.getString("titele_id"));

				// Twitter一覧に追加
				TwitterList.add(titleTwitter);

			}

			// SQLのバッチ登録数のカウント（Twitter情報）
			// インサート用
			int cntTwI = 0;
			// アップデート用
			int cntTwU = 0;
			// デリート用
			int cntTwD = 0;


			// 更新データのより分け（Twitter情報）
			// インサートとアップデートのより分ける
			// 更新データの管理用tTwitterIDが「0」なら、インサート
			// オブジェクトに値格納時、管理用tTwitterIDがない時は、
			// デフォルトで「0」が格納される
			// それ以外は同じサイトIDなので、アップデート
			// デリートは、そもそも更新データにデータがないので、
			// 次に元データと比較して、消されたサイトIDを探す
			for (TitleTwitter tT : title.getTwitter()) {

				if(tT.gettTwitterId() == 0) {

					// サイトIDが「0」の場合は、インサート
					// インサートのSQLを組み立て

					// TwitterID
					String twitterId = tT.getTwitterId();

					// 読み込んだSQLの「?」に値をセット
					// ユーザーID
					pStmtIT.setString(1, userId);
					// 作品ID
					pStmtIT.setInt(2, titleId);
					// TwitterID
					pStmtIT.setString(3, twitterId);

					// SQLのバッチ登録
					pStmtIT.addBatch();

					// インサートSQLのカウント
					cntTwI++;

				} else {

					// サイトIDが「0」以外の場合は、アップデート
					// アップデートのSQLを組み立て

					// i番目に格納のTitleSiteより各情報の取り出し
					// サイトID
					int tTwitterId = tT.gettTwitterId();
					// 公式サイトURL
					String twitterId = tT.getTwitterId();

					// 読み込んだSQLの「?」に値をセット
					// 公式サイトURL
					pStmtTw.setString(1, twitterId);
					// サイトID
					pStmtTw.setInt(2, tTwitterId);

					// SQLのバッチ登録
					pStmtTw.addBatch();

					// アップデートSQLのカウント
					cntTwU++;

				}

			}

			// デリートがあるかの確認（サイト情報）
			// 比較元→更新データで比較
			// 更新データに比較元がない場合は、デリート用のバッチ処理にぶち込む
			// 比較元にtTwitterIDがない場合の判定
			// → ループお尻までチェックしていたら無い（＝デリート）
			for (int k = 0; k < TwitterList.size(); k++) {

				for (int m = 0; m < title.getTwitter().size(); m++) {

					// 比較元と更新データに同じtTwitterIDがあれば、
					// 速攻でループ抜ける
					if(TwitterList.get(k).gettTwitterId() == title.getTwitter().get(m).gettTwitterId()) {

						break;

					}

					if(m == title.getTwitter().size() - 1) {

						// 更新データのサイトIDに比較元のサイトIDがない場合
						// デリートのSQLを組み立て
						pStmtDT.setInt(1, TwitterList.get(k).gettTwitterId());

						// SQLのバッチ登録
						pStmtDT.addBatch();

						// デリートSQLのカウント
						cntTwD++;

					}
				}
			}


			// バッチ処理に登録があれば、SQLを実行（Twitter情報）
			// インサート（Twitter情報）
			if(cntTwI > 0) {

				cntInTwitter = pStmtIT.executeBatch();

				// サイト登録のエラーチェック
				// 全部問題なかったとしても、コミットはまだしない
				// （Twitterのエラーチェックがまだなので）
				// SQL実行の戻り値をチェックし、
				// -3があれば、ロールバックする
				// 0以上の場合は、SQL文の実行によって更新された行数、
				// -2の場合は正常終了したものの更新された行数がわからない、
				// -3の場合は処理に失敗した事を示しています。
				for (int i = 0; i < cntInTwitter.length; i++) {

					if(cntInTwitter[i] == -3) {

					// 戻り値に-3があった場合は、ロールバック
					con.rollback();
					return false;

					}

				}

			}

			// アップデート（Twitter情報）
			if(cntTwU > 0) {

				cntUpTwitter = pStmtTw.executeBatch();

				// エラーチェック（詳細は↑）
				for (int i = 0; i < cntUpTwitter.length; i++) {

					if(cntUpTwitter[i] == -3) {

					// 戻り値に-3があった場合は、ロールバック
					con.rollback();
					return false;

					}

				}

			}
			// デリート（Twitter情報）
			if(cntTwD > 0) {

				cntDelTwitter = pStmtDT.executeBatch();

				// エラーチェック（詳細は↑）
				for (int i = 0; i < cntDelTwitter.length; i++) {

					if(cntDelTwitter[i] == -3) {

					// 戻り値に-3があった場合は、ロールバック
					con.rollback();
					return false;

					}

				}

			}

			// 何ごともなかったら、コミット
			con.commit();

		}

		return true;

	}


	// 作品の表示、非表示の更新（バッチ処理化でトランザクション処理できるやんver）
	public boolean titleOnOffUpdate(List<TitleBean> titleList) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化（配列にする）
		// 複数のSQLをaddBatch()でバッチ処理化し、
		// executeBatch()で複数SQLを実行するので、
		// 戻り値が各SQLの結果を格納した配列でくる。
		int cnt[];

		// SQL文の組み立て
		String sql = "UPDATE title SET t_on_OFF = ? WHERE title_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql);){

			// オートコミットをオフにする
			con.setAutoCommit(false);

			for (TitleBean title : titleList) {

				// 表示、非表示
				pStmt.setInt(1, title.gettOnOff());
				// 作品ID
				pStmt.setInt(2, title.getTitleId());

				// SQLのバッチ登録
				pStmt.addBatch();

			}

			// SQLバッチ処理の実行（サイト登録）
			cnt = pStmt.executeBatch();

			// SQL実行の戻り値をチェックし、
			// -3があれば、ロールバックする
			for (int i = 0; i < cnt.length; i++) {

				if(cnt[i] == -3) {

				// 戻り値に-3があった場合は、ロールバック
				con.rollback();
				return false;

				}
				if(i == cnt.length - 1) {

					// 配列のお尻まで何事もなくチェックできたらコミット
					con.commit();

				}
			}
		}

		return true;

	}


	// 作品情報の削除
	public boolean titleDeleteAll(int titleId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化（配列にする）
		// 複数のSQLをaddBatch()でバッチ処理化し、
		// executeBatch()で複数SQLを実行するので、
		// 戻り値が各SQLの結果を格納した配列でくる。
		int cnt[];

		// SQL文の組み立て
		String sqlTitle = "DELETE FROM title WHERE title_id = "+ titleId + " ;";
		String sqlSite = "DELETE FROM title_site WHERE titele_id = "+ titleId + " ;";
		String sqlTwitter = "DELETE FROM title_twitter WHERE titele_id = "+ titleId + " ;";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// DBにSQLを送るやつを作る
				Statement stmt = con.createStatement();){

			// オートコミットをオフにする
			con.setAutoCommit(false);

			// SQLをバッチ処理に追加していく
			stmt.addBatch(sqlSite);
			stmt.addBatch(sqlTwitter);
			stmt.addBatch(sqlTitle);

			// SQLバッチ処理の実行
			cnt = stmt.executeBatch();

			// SQL実行の戻り値をチェックし、
			// -3があれば、ロールバックする
			// 0以上の場合は、SQL文の実行によって更新された行数、
			// -2の場合は正常終了したものの更新された行数がわからない、
			// -3の場合は処理に失敗した事を示しています。
			for (int i = 0; i < cnt.length; i++) {

				if(cnt[i] == -3) {

					// 戻り値に-3があった場合は、ロールバック
					con.rollback();
					return false;

				}
				if(i == cnt.length - 1) {

					// 配列のお尻まで何事もなくチェックできたらコミット
					con.commit();

				}

			}

		}

		return true;

	}

}
