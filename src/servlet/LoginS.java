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
 * Servlet implementation class LoginS
 */
@WebServlet("/login")
public class LoginS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメーターの取得
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		String pass = request.getParameter("pass");

		// ユーザーのインスタンスを生成
		// これは認証用の仮のやつ
		UserBean userTentative = new UserBean(userId, pass);

		// ユーザー名の重複があるかチェック
		UserLogic uL = new UserLogic();
		boolean rtnIdCheck = uL.userIdCheckLogic(userId);

		// ユーザーIDが存在する（true）
	    // ユーザーIDが存在しない（false）
		if(rtnIdCheck) {

			// ユーザーIDが存在する場合
			// ユーザー認証処理を行い、メインページへ
			// 正しいユーザーのインスタンスを取得する
			// パスワード間違いなら、nullが入る
			UserBean user = uL.userLoginLogic(userTentative);

			// ユーザー認証が成功したか確認して、
			// 成功なら、Homeサーブレット経由でホーム画面に遷移、
			// 失敗なら、パスワード間違いなので、
			// メッセージを乗っけて送り返す
			if(user != null) {

				// 認証成功
				// セッションスコープにユーザー情報（これからめっちゃ使う）を保存
				HttpSession session = request.getSession();
				session.setAttribute("user", user);

				// フォワード
				// ホーム画面表示の準備で、Homeサーブレットを経由
				RequestDispatcher dispatcher = request.getRequestDispatcher("/home");
				dispatcher.forward(request, response);

			} else {

				// 認証失敗（パスワードが違う）
				// セッションスコープにメッセージを乗っけて送り返す
				HttpSession sessiton = request.getSession();
				sessiton.setAttribute("msg", "パスワードが間違っています。");
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);

			}

		} else {

			// ユーザーIDが存在しない場合
			// セッションスコープにメッセージを乗っけて送り返す
			HttpSession sessiton = request.getSession();
			sessiton.setAttribute("msg", "ユーザーIDが間違っています。");
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);

		}
	}

}
