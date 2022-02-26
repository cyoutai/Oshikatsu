package servlet;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class EventListS
 */
@WebServlet("/event_list")
public class EventListS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventListS() {
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
		int onTitleId = Integer.parseInt(request.getParameter("titleId"));
		String sYYYYMMDD = request.getParameter("sYYYYMMDD");
		String eYYYYMMDD = request.getParameter("eYYYYMMDD");

		// BOの読み込み
		EventLogic eL = new EventLogic();
		TitleLogic tL = new TitleLogic();

		// 選択期間がない場合は、開始日と終了日を設定する
		if(sYYYYMMDD.equals("なし") || sYYYYMMDD == null || sYYYYMMDD.length() == 0) {

			sYYYYMMDD = eL.getToday();

		}
		if(eYYYYMMDD.equals("なし") || eYYYYMMDD == null || eYYYYMMDD.length() == 0) {

			eYYYYMMDD = eL.getAddEndDay(user.getEventGet());
		}

		// 戻り値の受け皿を作成
		List<EventBean> eventDispList = new ArrayList<EventBean>();
		List<TitleBean> titleOnList = new ArrayList<TitleBean>();

		// 作品IDが「0」なら、表示設定の全作品のイベント情報を取得
		// 作品IDが指定されていれば、指定作品のイベント情報を取得
//		if(onTitleId == 0) {
//
//			eventDispList = eL.selectEventGetLogic(user.getUserId());
//
//		} else {

			// 選択期間がない場合は、開始日と終了日を設定した
			// ので、これだけで行けたわ
			eventDispList = eL.selectEventLogic(sYYYYMMDD, eYYYYMMDD, onTitleId, user.getUserId());

//		}

		// 表示設定している作品のリストを取得
		titleOnList = tL.selectTitleOnLogic(user.getUserId());

		// リクエストスコープに取得した値を格納
		request.setAttribute("onTitleId", onTitleId);
		request.setAttribute("eventDispList", eventDispList);
		request.setAttribute("titleOnList", titleOnList);
		request.setAttribute("sYYYYMMDD", sYYYYMMDD);
		request.setAttribute("eYYYYMMDD", eYYYYMMDD);

		// フォワード
		// 整ったら、記念日一覧画面へ送る
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/event_list.jsp");
		rd.forward(request, response);

	}

}
