package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.entity.TitleBean;
import model.model.TitleLogic;

/**
 * Servlet implementation class TitleDetaileS
 */
@WebServlet("/title_detail")
public class TitleDetailS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TitleDetailS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// hrefでリンク張っているので、
		// Postに流す
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメーターの取得
		request.setCharacterEncoding("UTF-8");
		int titleId = Integer.parseInt(request.getParameter("titleId"));

		// 作品IDから、作品情報の取り出し
		TitleLogic tL = new TitleLogic();
		TitleBean titleInfo = tL.getTitleInfo(titleId);

		// 取得した作品情報の送り出し
		if(titleInfo != null) {

			// 作品情報取得に成功
			// セッションスコープに格納して、詳細画面へ
			HttpSession session = request.getSession();
			session.setAttribute("titleInfo", titleInfo);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/title_detail.jsp");
			dispatcher.forward(request, response);

		} else {

			// 作品情報の取得に失敗
			// リザルト画面に送る
			request.setAttribute("msgResult", "作品情報の取得に失敗しました。");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rd.forward(request, response);

		}

	}

}
