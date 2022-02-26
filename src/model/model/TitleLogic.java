package model.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.TitleDAO;
import model.dao.TitleSiteDAO;
import model.dao.TitleTwitterDAO;
import model.dao.TransactionDAO;
import model.entity.TitleBean;
import model.entity.TitleSite;
import model.entity.TitleTwitter;

public class TitleLogic {

	// 作品名の重複チェック
	// 作品名が存在する（true）
    // 作品名が存在しない（false）
	public boolean TitleNameCheckLogic(String titleName, String userId) {

		boolean rtnBool = false;

		TitleDAO dao = new TitleDAO();

		try {

			rtnBool = dao.titleNameCheck(titleName, userId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return rtnBool;

	}

	// 作品登録（トランザクション処理ver.）
	public boolean titleRegisterAllLogic(TitleBean title) {

		boolean rtnBool = false;

		TransactionDAO dao = new TransactionDAO();

		try {

			rtnBool = dao.titleRegisterAll(title);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return rtnBool;

	}

	// 作品IDから作品情報を取得する
	public TitleBean getTitleInfo(int titleId) {

		// 戻り値格納用
		TitleBean title = new TitleBean();
		List<TitleSite> siteList = new ArrayList<TitleSite>();
		List<TitleTwitter> TwitterList = new ArrayList<TitleTwitter>();

		// DAOの作成
		TitleDAO daoTitle = new TitleDAO();
		TitleSiteDAO daoSite = new TitleSiteDAO();
		TitleTwitterDAO daoTwitter = new TitleTwitterDAO();

		try {

			title = daoTitle.getTitleInfo(titleId);
			siteList = daoSite.getTitleSite(titleId);
			TwitterList = daoTwitter.getTitleTwitter(titleId);

			// titleオブジェクトにサイト情報とTwitter情報を格納する
			title.setSite(siteList);
			title.setTwitter(TwitterList);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return title;

	}

	// 作品一覧（表示非表示関係なく、ユーザーIDの全部）を取得
	// これは、表示非表示設定する用の全件取得なので、
	// サイト情報、Twitter情報は取得しない
	public List<TitleBean> selectTitleAll(String userId){

		List<TitleBean> titleList = new ArrayList<TitleBean>();

		TitleDAO dao = new TitleDAO();

		try {

			titleList = dao.selectTitleAll(userId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return titleList;

	}

	// 「表示」設定の作品一覧を返す
	// これも、一覧やフォームのセレクトだけに使用するので、
	// サイト情報、Twitter情報は取得しない
	public List<TitleBean> selectTitleOnLogic(String userId){

		List<TitleBean> titleList = new ArrayList<TitleBean>();

		TitleDAO dao = new TitleDAO();

		try {

			titleList = dao.selectTitleOn(userId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return titleList;

	}


	// 作品の表示・非表示の更新
	public boolean titleOnOffUpdateLogic(List<TitleBean> titleList) {

		boolean rtnBool = false;

		TransactionDAO dao = new TransactionDAO();

		try {

			rtnBool = dao.titleOnOffUpdate(titleList);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return rtnBool;

	}

	// 作品情報の更新（トランザクション処理ver）
	public boolean titleUpdateAllLogic(TitleBean title) {

		boolean rtnBool = false;

		TransactionDAO dao = new TransactionDAO();

		try {

			rtnBool = dao.titleUpdateAll(title);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return rtnBool;

	}

	// 作品情報の削除
	public boolean titleDeleteAll(int titleId) {

		boolean rtnBool = false;

		TransactionDAO dao = new TransactionDAO();

		try {

			rtnBool = dao.titleDeleteAll(titleId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return rtnBool;

	}

	// これ、作品登録ボツ（トランザクション処理なし）
	public boolean titleRegisterLogic(TitleBean title) {

		boolean rtnBool = false;

		int cntTitleRegister = 0;
		int titleId = 0;
		int cntSiteRegister = 0;
		int cntTwitterRegister = 0;

		TitleDAO daoTitle = new TitleDAO();
		TitleSiteDAO daoTSite = new TitleSiteDAO();
		TitleTwitterDAO daoTTwitter = new TitleTwitterDAO();

		// 作品名の取得
		String titleName = title.getTitleName();
		String userId = title.getUserId();
		List<TitleSite> titleSite = title.getSite();

		try {

			cntTitleRegister = daoTitle.titleRegister(title);

			titleId = daoTitle.getTitleId(titleName, userId);

			if(title.getSite() != null) {

				for (TitleSite tS : title.getSite()) {

					cntSiteRegister = daoTSite.titleSiteRegister(titleId, tS.getSiteUrl(), userId);

					if(cntSiteRegister == 0) {

						return false;

					}

				}
			}

			if(title.getTwitter() != null) {


			}

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
			return false;

		}

		return true;
	}
}
