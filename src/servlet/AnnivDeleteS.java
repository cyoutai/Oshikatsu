package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.model.AnniversaryLogic;

/**
 * Servlet implementation class AnnivDeleteS
 */
@WebServlet("/anniv_delete")
public class AnnivDeleteS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnnivDeleteS() {
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
		int annivId = Integer.parseInt(request.getParameter("annivId"));

		// BOの呼び出し
		AnniversaryLogic aL = new AnniversaryLogic();
		boolean rtnAnnivDelet = aL.annivDelete(annivId);

		// 更新結果の判定
		if(rtnAnnivDelet) {

				// 記念日の更新が成功したら、
				// リクエストスコープにメッセージを乗っけて、
				// リザルト画面に送る
				request.setAttribute("msgResult", "記念日・誕生日情報を削除しました。");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				rd.forward(request, response);

			} else {

				// 作品登録が失敗したら、
				// リクエストスコープにメッセージを乗っけて送り返す
				request.setAttribute("msgAnnivErr", "記念日・誕生日情報の削除ができませんでした。");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/anniv_form.jsp");
				rd.forward(request, response);

			}

	}

}
