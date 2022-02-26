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
 * Servlet implementation class UserRegisterS
 */
@WebServlet("/user_register")
public class UserRegisterS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegisterS() {
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
		String userId = request.getParameter("userIdR");
		String pass = request.getParameter("passR");

		// ユーザーのインスタンスを生成
		UserBean user = new UserBean(userId, pass);

		// ユーザー名の重複があるかチェック
		UserLogic uL = new UserLogic();
		boolean rtnIdCheck = uL.userIdCheckLogic(userId);

		// ユーザーIDが存在する（true）
	    // ユーザーIDが存在しない（false）
		if(rtnIdCheck) {

			// ユーザーIDが存在する場合
			// リクエストスコープにメッセージを乗っけて送り返す
			request.setAttribute("msgUserErr", "同一のユーザーIDがすでに登録されています。");
			RequestDispatcher rd = request.getRequestDispatcher("/user_register.jsp");
			rd.forward(request, response);

		} else {

			// ユーザーIDが存在しない場合
			// ユーザー登録処理を行い、メインページへ
			boolean rtnRegist = uL.userRegisterLogic(user);

			// ユーザー登録が成功したか確認して、
			// 成功ならホーム画面に遷移、
			// 失敗ならメッセージを乗っけて送り返す
			if(rtnRegist) {

				// 登録成功
				// セッションスコープにユーザー情報（これからめっちゃ使う）を保存
				HttpSession session = request.getSession();
				session.setAttribute("user", user);

				// フォワード
				// Homeサーブレット経由で、ホーム画面に行く
				RequestDispatcher dispatcher = request.getRequestDispatcher("/home");
				dispatcher.forward(request, response);

			} else {

				// 登録失敗
				//リクエストスコープにメッセージを乗っけて送り返す
				request.setAttribute("msgUserErr", "ユーザー登録に失敗しました。");
				RequestDispatcher rd = request.getRequestDispatcher("/user_register.jsp");
				rd.forward(request, response);

			}

		}

	}

}
