package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.entity.AnniversaryBean;
import model.model.AnnivDate;

public class AnniversaryDAO {

	// 記念日・誕生日の登録
	public int annivRegister(AnniversaryBean anniv) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "INSERT INTO anniversary (user_id,titele_id,anniv_name,anniv_year,anniv_md,anniv_cnt,anniv_class,anniv_clr) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// AnniversaryBean（anniv）より各ユーザー情報の取り出し
			// ユーザーID
			String userId = anniv.getUserId();
			// 作品ID
			int titleId = anniv.getTitleId();
			// 記念日・誕生日の名称
			String annivName = anniv.getAnnivName();
			// 記念日・誕生日の年
			int annivYear = anniv.getAnnivYear();
			// 記念日・誕生日の月
			int annivMonth = anniv.getAnnivMonth();
			// 記念日・誕生日の日
			int annivDay = anniv.getAnnivDay();
			// 月日から、DB格納用の値に変換
			int annivMD = annivMonth * 100 + annivDay;
			// 周年カウントの名称
			String annivCnt = anniv.getAnnivCnt();
			// 記念日・誕生日の区分
			String annivClass = anniv.getAnnivClass();
			// 記念日・誕生日の表示カラー
			String annivClr = anniv.getAnnivClr();

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// 作品ID
			pStmt.setInt(2, titleId);
			// 記念日・誕生日の名称
			pStmt.setString(3, annivName);
			// 記念日・誕生日の年
			pStmt.setInt(4, annivYear);
			// 記念日・誕生日の月日
			pStmt.setInt(5, annivMD);
			// 周年カウントの名称
			pStmt.setString(6, annivCnt);
			// 記念日・誕生日の区分
			pStmt.setString(7, annivClass);
			// 記念日・誕生日の表示カラー
			pStmt.setString(8, annivClr);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// 記念日・誕生日の情報の取得
	public AnniversaryBean getAnniv(int anivId) throws ClassNotFoundException, SQLException {

		// 取得する記念日・誕生日の初期化
		AnniversaryBean anniv = null;

		// SQL文の組み立て
		String sql = "SELECT * FROM anniversary WHERE anniv_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// 作品ID
			pStmt.setInt(1, anivId);

			// SQLが実行されて、結果が「res」にドンって入る
		    ResultSet res = pStmt.executeQuery();

		    while(res.next()) {

		    	anniv = new AnniversaryBean();

		    	// DBから取得する値
		    	anniv.setAnnivId(res.getInt("anniv_id"));
		    	anniv.setUserId(res.getString("user_id"));
		    	anniv.setTitleId(res.getInt("titele_id"));
		    	anniv.setAnnivName(res.getString("anniv_name"));
		    	anniv.setAnnivYear(res.getInt("anniv_year"));
		    	anniv.setAnnivCnt(res.getString("anniv_cnt"));
		    	anniv.setAnnivClass(res.getString("anniv_class"));
		    	anniv.setAnnivClr(res.getString("anniv_clr"));

		    	// DBから値を算出するもの（月日を月と日に分ける）
		    	int annivMD = res.getInt("anniv_md") ;
		    	// 記念日・誕生日の月
		    	anniv.setAnnivMonth(annivMD / 100);
		    	// 記念日・誕生日の日
		    	anniv.setAnnivDay(annivMD % 100);

		    	// 使用しない値にnull、O入れておく（DBから取得しない値）
		    	anniv.setAnnivDateType(null);
		    	anniv.setAnnivDateCnt(0);

		    }

		}

		// 作品情報を返す
		return anniv;

	}

	// 記念日・誕生日の全件取得
	public List<AnniversaryBean> selectAnnivAll(String userId) throws ClassNotFoundException, SQLException{

		// 取得する記念日・誕生日一覧の生成
		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		// SQL文の組み立て
		String sql = "SELECT anni.anniv_id, anni.user_id, anni.titele_id, anni.anniv_name, anni.anniv_year, anni.anniv_md, anni.anniv_cnt, anni.anniv_class, anni.anniv_clr FROM anniversary anni INNER JOIN title tit ON anni.titele_id = tit.title_id WHERE anni.user_id = ? AND tit.t_on_OFF = 1 ORDER BY anniv_md ASC";

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

				AnniversaryBean anniv = new AnniversaryBean();

		    	// DBから取得する値
		    	anniv.setAnnivId(res.getInt("anniv_id"));
		    	anniv.setUserId(res.getString("user_id"));
		    	anniv.setTitleId(res.getInt("titele_id"));
		    	anniv.setAnnivName(res.getString("anniv_name"));
		    	anniv.setAnnivYear(res.getInt("anniv_year"));
		    	anniv.setAnnivCnt(res.getString("anniv_cnt"));
		    	anniv.setAnnivClass(res.getString("anniv_class"));
		    	anniv.setAnnivClr(res.getString("anniv_clr"));

		    	// DBから値を算出するもの（月日を月と日に分ける）
		    	int annivMD = res.getInt("anniv_md") ;
		    	// 記念日・誕生日の月
		    	anniv.setAnnivMonth(annivMD / 100);
		    	// 記念日・誕生日の日
		    	anniv.setAnnivDay(annivMD % 100);

		    	// 使用しない値にnull、O入れておく（DBから取得しない値）
		    	anniv.setAnnivDateType(null);
		    	anniv.setAnnivDateCnt(0);

		    	annivList.add(anniv);

			}

		}

		return annivList;

	}

	// 選択した作品の記念日・誕生日の一覧を取得
	public List<AnniversaryBean> selectAnniv(int titleId) throws ClassNotFoundException, SQLException{

		// 取得する記念日・誕生日一覧の生成
		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		// SQL文の組み立て
		String sql = "SELECT anni.anniv_id, anni.user_id, anni.titele_id, anni.anniv_name, anni.anniv_year, anni.anniv_md, anni.anniv_cnt, anni.anniv_class, anni.anniv_clr FROM anniversary anni INNER JOIN title tit ON anni.titele_id = tit.title_id WHERE anni.titele_id = ? AND tit.t_on_OFF = 1 ORDER BY anniv_md ASC";

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

				AnniversaryBean anniv = new AnniversaryBean();

		    	// DBから取得する値
		    	anniv.setAnnivId(res.getInt("anniv_id"));
		    	anniv.setUserId(res.getString("user_id"));
		    	anniv.setTitleId(res.getInt("titele_id"));
		    	anniv.setAnnivName(res.getString("anniv_name"));
		    	anniv.setAnnivYear(res.getInt("anniv_year"));
		    	anniv.setAnnivCnt(res.getString("anniv_cnt"));
		    	anniv.setAnnivClass(res.getString("anniv_class"));
		    	anniv.setAnnivClr(res.getString("anniv_clr"));

		    	// DBから値を算出するもの（月日を月と日に分ける）
		    	int annivMD = res.getInt("anniv_md") ;
		    	// 記念日・誕生日の月
		    	anniv.setAnnivMonth(annivMD / 100);
		    	// 記念日・誕生日の日
		    	anniv.setAnnivDay(annivMD % 100);

		    	// 使用しない値にnull、O入れておく（DBから取得しない値）
		    	anniv.setAnnivDateType(null);
		    	anniv.setAnnivDateCnt(0);

		    	annivList.add(anniv);

			}

		}

		return annivList;

	}

	// 今日の記念日・誕生日の取得
	public List<AnniversaryBean> selectAnnivToday(int tMD, String userId) throws ClassNotFoundException, SQLException{

		// 取得する記念日・誕生日一覧の生成
		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		// SQL文の組み立て
		String sql = "SELECT anni.anniv_id, anni.user_id, anni.titele_id, anni.anniv_name, anni.anniv_year, anni.anniv_md, anni.anniv_cnt, anni.anniv_class, anni.anniv_clr FROM anniversary anni INNER JOIN title tit ON anni.titele_id = tit.title_id WHERE anni.user_id = ? AND anni.anniv_md = ? AND tit.t_on_OFF = 1 ORDER BY anniv_md ASC";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// 今日の月日
			pStmt.setInt(2, tMD);

			// SQLが実行されて、結果が「res」にドンって入る
			ResultSet res = pStmt.executeQuery();

			while(res.next()) {

				AnniversaryBean anniv = new AnniversaryBean();

		    	// DBから取得する値
		    	anniv.setAnnivId(res.getInt("anniv_id"));
		    	anniv.setUserId(res.getString("user_id"));
		    	anniv.setTitleId(res.getInt("titele_id"));
		    	anniv.setAnnivName(res.getString("anniv_name"));
		    	anniv.setAnnivYear(res.getInt("anniv_year"));
		    	anniv.setAnnivCnt(res.getString("anniv_cnt"));
		    	anniv.setAnnivClass(res.getString("anniv_class"));
		    	anniv.setAnnivClr(res.getString("anniv_clr"));

		    	// DBから値を算出するもの（月日を月と日に分ける）
		    	int annivMD = res.getInt("anniv_md") ;
		    	// 記念日・誕生日の月
		    	anniv.setAnnivMonth(annivMD / 100);
		    	// 記念日・誕生日の日
		    	anniv.setAnnivDay(annivMD % 100);

		    	// 使用しない値にnull、O入れておく（DBから取得しない値）
		    	anniv.setAnnivDateType(null);
		    	anniv.setAnnivDateCnt(0);

		    	annivList.add(anniv);

			}

		}

		return annivList;

	}

	// 記念日・誕生日のカウントダウン一覧を取得
	public List<AnniversaryBean> selectAnnivCnt(int tMD, int cMD, String userId) throws ClassNotFoundException, SQLException, ParseException{

		// 取得する記念日・誕生日一覧の生成
		List<AnniversaryBean> annivList = new ArrayList<AnniversaryBean>();

		// SQL文の組み立て
		String sql = "SELECT anni.anniv_id, anni.user_id, anni.titele_id, anni.anniv_name, anni.anniv_year, anni.anniv_md, anni.anniv_cnt, anni.anniv_class, anni.anniv_clr FROM anniversary anni INNER JOIN title tit ON anni.titele_id = tit.title_id WHERE anni.user_id = ? AND (anni.anniv_md BETWEEN ? AND ?) AND tit.t_on_OFF = 1 ORDER BY anni.anniv_md ASC";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// 今日の月日
			pStmt.setInt(2, tMD);
			// カウントダウンの範囲の月日
			pStmt.setInt(3, cMD);

			// SQLが実行されて、結果が「res」にドンって入る
			ResultSet res = pStmt.executeQuery();

			while(res.next()) {

				AnniversaryBean anniv = new AnniversaryBean();

		    	// DBから取得する値
		    	anniv.setAnnivId(res.getInt("anniv_id"));
		    	anniv.setUserId(res.getString("user_id"));
		    	anniv.setTitleId(res.getInt("titele_id"));
		    	anniv.setAnnivName(res.getString("anniv_name"));
		    	anniv.setAnnivYear(res.getInt("anniv_year"));
		    	anniv.setAnnivCnt(res.getString("anniv_cnt"));
		    	anniv.setAnnivClass(res.getString("anniv_class"));
		    	anniv.setAnnivClr(res.getString("anniv_clr"));

		    	// DBから値を算出するもの（月日を月と日に分ける）
		    	int annivMD = res.getInt("anniv_md") ;
		    	// 記念日・誕生日の月
		    	anniv.setAnnivMonth(annivMD / 100);
		    	// 記念日・誕生日の日
		    	anniv.setAnnivDay(annivMD % 100);

		    	// 算出する値（まずはnew）
		    	AnnivDate aD = new AnnivDate();

		    	// 取得した月日を今年の年にしてLocalDate型で格納
		    	anniv.setAnnivDateType(aD.annivDateChange(anniv.getAnnivMonth(), anniv.getAnnivDay()));

		    	// 現在から今年の記念日・誕生日までのカウント日数を格納
		    	anniv.setAnnivDateCnt(aD.annivDateCnt(anniv.getAnnivDateType()));

		    	// LocalDate型をDATE型に変換して格納
		    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    	Date date = simpleDateFormat.parse(anniv.getAnnivDateType().toString());
		    	anniv.setAnnivDateTypeDisplay(date);

		    	// すべて整ったら、リストに格納
		    	annivList.add(anniv);

			}

		}

		return annivList;

	}

	// 記念日・誕生日のカウントダウン一覧を取得（年をまたぐ場合）
	public List<AnniversaryBean> selectAnnivCntAcross(int tMD, int cMD, String userId) throws ClassNotFoundException, SQLException, ParseException{

		// 取得する記念日・誕生日一覧の生成（2回、取得メソッドを回すのを格納する）
		List<AnniversaryBean> annivListAdd = new ArrayList<AnniversaryBean>();

		// メソッドの戻り値を格納
		List<AnniversaryBean> annivListVessel = new ArrayList<AnniversaryBean>();

		// 今日から12月31日までを取得
		annivListVessel = this.selectAnnivCnt(tMD, 1231, userId);

		// 取得した結果をListに格納（1回目）
		for (AnniversaryBean aB : annivListVessel) {

			annivListAdd.add(aB);

		}

		// 1月1日からカウントダウン日までを取得
		annivListVessel = this.selectAnnivCnt(101, cMD, userId);

		// 取得した結果をListに続けて格納（2回目）
		for (AnniversaryBean aB : annivListVessel) {

			annivListAdd.add(aB);

		}

		// 年をまたいだ一覧のListを返す
		return annivListAdd;

	}

	// 記念日・誕生日の情報の更新
	public int annivUpdate(AnniversaryBean anniv) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "UPDATE anniversary SET user_id = ?, titele_id = ?, anniv_name = ?, anniv_year = ?, anniv_md = ?, anniv_cnt = ?, anniv_class = ?, anniv_clr = ? WHERE anniv_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// AnniversaryBean（anniv）より各記念日・誕生日の情報の取り出し
			// 記念日・誕生日ID
			int annivId = anniv.getAnnivId();
			// ユーザーID
			String userId = anniv.getUserId();
			// 作品ID
			int titleId = anniv.getTitleId();
			// 記念日・誕生日の名称
			String annivName = anniv.getAnnivName();
			// 記念日・誕生日の年
			int annivYear = anniv.getAnnivYear();
			// 記念日・誕生日の月
			int annivMonth = anniv.getAnnivMonth();
			// 記念日・誕生日の日
			int annivDay = anniv.getAnnivDay();
			// 月日から、DB格納用の値に変換
			int annivMD = annivMonth * 100 + annivDay;
			// 周年カウントの名称
			String annivCnt = anniv.getAnnivCnt();
			// 記念日・誕生日の区分
			String annivClass = anniv.getAnnivClass();
			// 記念日・誕生日の表示カラー
			String annivClr = anniv.getAnnivClr();

			// 読み込んだSQLの「?」に値をセット
			// ユーザーID
			pStmt.setString(1, userId);
			// 作品ID
			pStmt.setInt(2, titleId);
			// 記念日・誕生日の名称
			pStmt.setString(3, annivName);
			// 記念日・誕生日の年
			pStmt.setInt(4, annivYear);
			// 記念日・誕生日の月日
			pStmt.setInt(5, annivMD);
			// 周年カウントの名称
			pStmt.setString(6, annivCnt);
			// 記念日・誕生日の区分
			pStmt.setString(7, annivClass);
			// 記念日・誕生日の表示カラー
			pStmt.setString(8, annivClr);
			// 記念日・誕生日ID
			pStmt.setInt(9, annivId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// 記念日・誕生日の情報の削除
	public int annivDelete(int annivId) throws ClassNotFoundException, SQLException {

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "DELETE FROM anniversary WHERE anniv_id = ?";

		try(
				// コネクションマネージャーの呼び出し
				Connection con = ConnectionManager.getConnection();

				// SQLを先読みしておく
				PreparedStatement pStmt = con.prepareStatement(sql)){

			// 読み込んだSQLの「?」に値をセット
			// 記念日ID
			pStmt.setInt(1, annivId);

			// SQLが実行されて、更新件数が「cnt」に入る
			cnt = pStmt.executeUpdate();

		}

		// 更新件数を返す（処理の可否の判定は呼び出し元でする）
		return cnt;

	}

	// ユーザー削除による記念日・誕生日の情報の削除
	public int userAnnivDelete(String userId) throws  ClassNotFoundException, SQLException{

		// 更新件数の初期化
		int cnt = 0;

		// SQL文の組み立て
		String sql = "DELETE FROM anniversary WHERE user_id = ?";

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
