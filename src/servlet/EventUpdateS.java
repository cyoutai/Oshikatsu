package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.entity.EventBean;
import model.entity.UserBean;
import model.model.EventLogic;

/**
 * Servlet implementation class EventUpdateS
 */
@WebServlet("/event_update")
public class EventUpdateS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventUpdateS() {
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

		// セッションスコープからユーザー情報（めっちゃ使う）を取得
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		// リクエストパラメーターの取得
		request.setCharacterEncoding("UTF-8");
		int eventId = Integer.parseInt(request.getParameter("eventId"));
		String userId = request.getParameter("userId");
		int titleId = Integer.parseInt(request.getParameter("titleId"));
		String eventName = request.getParameter("eventName");
		String eventStart = request.getParameter("eventStart");
		String eventEnd = request.getParameter("eventEnd");
		// イベント終了日が設定されていなかったら、
		// イベント開始日をセットして、同日にする
		if(eventEnd == null || eventEnd.length() == 0) {

			eventEnd = eventStart;

		}

		String eventLoc = request.getParameter("eventLoc");
		// 値がなかったら、nullをセット
		if(eventLoc == null || eventLoc.length() == 0) {

			eventLoc = null;

		}

		String eventUrl = request.getParameter("eventUrl");
		// 値がなかったら、nullをセット
		if(eventUrl == null || eventUrl.length() == 0) {

			eventUrl = null;

		}

		String eventShop = request.getParameter("eventShop");
		// 値がなかったら、nullをセット
		if(eventShop == null || eventShop.length() == 0) {

			eventShop = null;

		}


		// イベントのインスタンスを作成
		EventBean eventInfo = new EventBean();
		eventInfo.setEventId(eventId);
		eventInfo.setUserId(userId);
		eventInfo.setTitleId(titleId);
		eventInfo.setEventName(eventName);
		eventInfo.setEventStart(eventStart);
		eventInfo.setEventEnd(eventEnd);
		eventInfo.setEventLoc(eventLoc);
		eventInfo.setEventUrl(eventUrl);
		eventInfo.setEventShop(eventShop);


		// 更新処理を行う
		EventLogic eL = new EventLogic();
		boolean rtnEventUpdate = eL.eventUpdateLogic(eventInfo);

		// 更新結果の判定
		if(rtnEventUpdate) {

			// イベントの更新が成功したら、
			// リクエストスコープにメッセージを乗っけて、
			// リザルト画面に送る
			request.setAttribute("msgResult", "イベント情報を更新しました。");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rd.forward(request, response);

		} else {

			// 作品登録が失敗したら、
			// リクエストスコープにメッセージを乗っけて送り返す
			request.setAttribute("msgEventErr", "イベント情報の更新ができませんでした。");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/event_form.jsp");
			rd.forward(request, response);

		}

	}

}
