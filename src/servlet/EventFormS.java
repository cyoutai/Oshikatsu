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

import model.entity.EventBean;
import model.entity.TitleBean;
import model.entity.UserBean;
import model.model.EventLogic;
import model.model.TitleLogic;

/**
 * Servlet implementation class EventFormS
 */
@WebServlet("/event_form")
public class EventFormS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventFormS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// POSTに送る
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッションスコープからユーザー情報（めっちゃ使う）を取得
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		// リクエストパラメーターの取得
		request.setCharacterEncoding("UTF-8");
		int eventId = Integer.parseInt(request.getParameter("eventId"));
		int onTitleId = Integer.parseInt(request.getParameter("onTitleId"));
		String sYYYYMMDD = request.getParameter("sYYYYMMDD");
		String eYYYYMMDD = request.getParameter("eYYYYMMDD");

		// 作品一覧を取得する
		TitleLogic tL = new TitleLogic();
		List<TitleBean> titleListAll = tL.selectTitleAll(user.getUserId());

		// 記念日IDから、記念日情報を取得
		EventLogic aL = new EventLogic();
		EventBean eventInfo = aL.getEventLogic(eventId);

		// 取得した記念日情報の送り出し
		if(eventInfo != null) {

			// 記念日情報取得に成功
			// リクエストコープに格納して、詳細画面へ
			request.setAttribute("eventInfo", eventInfo);
			// 戻るときに使う選択中の作品IDも格納する
			// これはセッションスコープに入れて再利用する
			session.setAttribute("onTitleId", onTitleId);
			// 選択している期間もしまう
			session.setAttribute("sYYYYMMDD", sYYYYMMDD);
			session.setAttribute("eYYYYMMDD", eYYYYMMDD);
			// 作品選択に使う作品リストも格納する
			// 月日エラー時に出戻りするので、
			// これはセッションスコープに入れて再利用する
			session.setAttribute("titleListAll", titleListAll);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/event_form.jsp");
			dispatcher.forward(request, response);

		} else {

			// 作品情報の取得に失敗
			// リザルト画面に送る
			request.setAttribute("msgResult", "イベント情報の取得に失敗しました。");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rd.forward(request, response);

		}

	}

}
