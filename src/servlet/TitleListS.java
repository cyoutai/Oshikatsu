package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.entity.TitleBean;
import model.entity.UserBean;
import model.model.TitleLogic;

/**
 * Servlet implementation class TitleListS
 */
@WebServlet("/title_list")
public class TitleListS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TitleListS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// home.jspからhrefでリンク張っているので、
		// Postに流す
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッションスコープからユーザー情報（めっちゃ使う）を取得
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		// 作品一覧を取得する＆リクエストスコープに格納
		TitleLogic tL = new TitleLogic();
		List<TitleBean> titleListAll = tL.selectTitleAll(user.getUserId());
		session.setAttribute("titleListAll", titleListAll);

		// フォワード
		// ホーム画面に行く
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/title_list.jsp");
		dispatcher.forward(request, response);

	}

}
