package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.entity.UserBean;
import model.model.UserLogic;

/**
 * Servlet implementation class ConfigUpdateS
 */
@WebServlet("/config_update")
public class ConfigUpdateS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfigUpdateS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 設定ページに遷移
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/config_form.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメーターの取得
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		String pass = request.getParameter("pass");
		int countDwn = Integer.parseInt(request.getParameter("countDwn"));
		int eventGet = Integer.parseInt(request.getParameter("eventGet"));

		// ユーザーのインスタンスを生成
		UserBean user = new UserBean();
		user.setUserId(userId);
		user.setPass(pass);
		user.setCountDwn(countDwn);
		user.setEventGet(eventGet);

		// ユーザー情報を更新
		UserLogic uL = new UserLogic();
		boolean rtnUserUpdate = uL.userUpdateLogic(user);

		// ユーザー登録が成功したか確認して、
		// 成功ならホーム画面に遷移、
		// 失敗ならメッセージを乗っけて送り返す
		if(rtnUserUpdate) {

			// 登録成功
			// セッションスコープにユーザー情報（これからめっちゃ使う）を保存
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			// フォワード
			// リザルト画面に送る
			request.setAttribute("msgResult", "ユーザー情報を更新しました。");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rd.forward(request, response);

		} else {

			// 登録失敗
			// セッションスコープにメッセージを乗っけて送り返す
			HttpSession sessiton = request.getSession();
			sessiton.setAttribute("msgUserErr", "ユーザー情報の更新に失敗しました。");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/config_form.jsp");
			rd.forward(request, response);

		}

	}

}
