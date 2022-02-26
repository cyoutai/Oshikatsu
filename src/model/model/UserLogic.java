package model.model;

import java.sql.SQLException;

import model.dao.AnniversaryDAO;
import model.dao.TransactionDAO;
import model.dao.EventDAO;
import model.dao.TitleDAO;
import model.dao.TitleSiteDAO;
import model.dao.TitleTwitterDAO;
import model.dao.UserDAO;
import model.entity.UserBean;

public class UserLogic {

	// ユーザーIDの重複チェック
	public boolean userIdCheckLogic(String userId) {

		UserDAO dao = new UserDAO();

		boolean rtnBool = false;

		try {

			rtnBool = dao.userIdCheck(userId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		// ユーザーIDが存在する（true）
	    // ユーザーIDが存在しない（false）
		return rtnBool;

	}

	// ユーザーIDの登録
	public boolean userRegisterLogic(UserBean user) {

		UserDAO dao = new UserDAO();

		int rtn = 0;

		try {

			rtn = dao.userRegister(user);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		// 0（失敗）と等しくなかった（!=）ら「True」（成功）、等しかったら「False」（失敗）
		return rtn != 0;

	}

	// ログイン認証
	public UserBean userLoginLogic(UserBean user) {

		UserDAO dao = new UserDAO();

		UserBean rtnUser = null;

		try {

			rtnUser = dao.userLogin(user);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return rtnUser;

	}

	// ユーザー情報の更新
	public boolean userUpdateLogic(UserBean user) {

		UserDAO dao = new UserDAO();

		int rtn = 0;

		try {

			rtn = dao.userUpdate(user);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		// 0と等しくなかった（!=）ら「True」、等しかったら「False」
		return rtn != 0;
	}

	// ユーザー削除による全データ削除（トランザクション処理ver）
	public boolean deleteUserAll(String userId) {

		boolean rtnBool = false;

		TransactionDAO dao = new TransactionDAO();

		try {

			rtnBool = dao.deleteUserAll(userId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		return rtnBool;

	}

	// ユーザー削除による全データ削除（トランザクション処理どうする？）
	public boolean userDeleteLogic(String userId) {

		UserDAO daoUser = new UserDAO();
		TitleDAO daoTitle = new TitleDAO();
		TitleSiteDAO daoTSite = new TitleSiteDAO();
		TitleTwitterDAO daoTTwitter = new TitleTwitterDAO();
		AnniversaryDAO daoAnniv = new AnniversaryDAO();
		EventDAO daoEvent = new EventDAO();

		int rtnUser = 0;
		int rtnTitle = 0;
		int rtnTSite = 0;
		int rtnTTwitter = 0;
		int rtnAnniv = 0;
		int rtnEvent = 0;

		try {

			rtnEvent = daoEvent.userEventDelete(userId);
			rtnAnniv = daoAnniv.userAnnivDelete(userId);

			rtnTTwitter = daoTTwitter.userTitleTwitterDelete(userId);
			rtnTSite = daoTSite.userTitleSiteDelete(userId);

			rtnTitle = daoTitle.userTitleDelete(userId);
			rtnUser = daoUser.userDelete(userId);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

			return false;

		}

		return true;

	}

}
