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
 * Servlet implementation class UserDeleteS
 */
@WebServlet("/user_delete")
public class UserDeleteS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDeleteS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// POSTへ送る
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッションスコープからユーザー情報（めっちゃ使う）を取得
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		// ユーザーの削除処理の呼び出し
		UserLogic uL = new UserLogic();
		boolean rtnUserDelete = uL.deleteUserAll(user.getUserId());

		// 更新結果の判定
		if(rtnUserDelete) {

			// 記念日の更新が成功したら、
			// リクエストスコープにメッセージを乗っけて、
			// ログアウト画面に送る
			request.setAttribute("msgLogout", "ユーザーを削除しました。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/logout.jsp");
			dispatcher.forward(request, response);

		} else {

			// 作品登録が失敗したら、
			// リクエストスコープにメッセージを乗っけて送り返す
			request.setAttribute("msgResult", "ユーザーの削除ができませんでした。");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rd.forward(request, response);

		}

	}

}
