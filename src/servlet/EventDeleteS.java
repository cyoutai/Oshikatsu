package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.model.EventLogic;

/**
 * Servlet implementation class EventDeleteS
 */
@WebServlet("/event_delete")
public class EventDeleteS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventDeleteS() {
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
		int eventId = Integer.parseInt(request.getParameter("eventId"));

		// BOの呼び出し
		EventLogic eL = new EventLogic();
		boolean rtnEventDelet = eL.eventDeleteLogic(eventId);

		// 更新結果の判定
		if(rtnEventDelet) {

				// 記念日の更新が成功したら、
				// リクエストスコープにメッセージを乗っけて、
				// リザルト画面に送る
				request.setAttribute("msgResult", "イベント情報を削除しました。");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				rd.forward(request, response);

			} else {

				// 作品登録が失敗したら、
				// リクエストスコープにメッセージを乗っけて送り返す
				request.setAttribute("msgTitle", "イベント情報の削除ができませんでした。");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/anniv_form.jsp");
				rd.forward(request, response);

			}

	}

}
