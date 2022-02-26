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
 * Servlet implementation class EventRegisterS
 */
@WebServlet("/event_register")
public class EventRegisterS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventRegisterS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッションスコープからユーザー情報（めっちゃ使う）を取得
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		// 登録されている作品名を全件取得
		TitleLogic tL = new TitleLogic();
		List<TitleBean> titleListAll = tL.selectTitleAll(user.getUserId());
		// 月日エラー時に出戻りするので、
		// これはセッションスコープに入れて再利用する
		session.setAttribute("titleListAll", titleListAll);

		// フォワード
		// 入力フォームに送る
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/event_register.jsp");
		dispatcher.forward(request, response);

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
		String userId = user.getUserId();

		// 作品が何も登録されていなくて、登録しようとした場合、
		// 作品が何も登録されていない旨を返す
		String titleIdS = request.getParameter("titleId");
		int titleId = 0;
		if(titleIdS == null || titleIdS.length() == 0) {

			// 作品が何も登録されていなかったら、
			// リクエストコープにもろもろ格納して、入力画面へ送り返す
			request.setAttribute("msgEventErr", "まずは作品登録から行ってください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/event_register.jsp");
			dispatcher.forward(request, response);

		} else {

			titleId = Integer.parseInt(titleIdS);

		}


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
		boolean rtnEventRegister = eL.eventRegisterLogic(eventInfo);

		// 更新結果の判定
		if(rtnEventRegister) {

			// イベントの更新が成功したら、
			// リクエストスコープにメッセージを乗っけて、
			// リザルト画面に送る
			request.setAttribute("msgResult", "イベント情報を登録しました。");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rd.forward(request, response);

		} else {

			// 作品登録が失敗したら、
			// リクエストスコープにメッセージを乗っけて送り返す
			request.setAttribute("msgEventErr", "イベント情報の登録ができませんでした。");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/event_register.jsp");
			rd.forward(request, response);

		}

	}

}
