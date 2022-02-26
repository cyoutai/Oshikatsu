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

import model.entity.AnniversaryBean;
import model.entity.EventBean;
import model.entity.UserBean;
import model.model.AnniversaryLogic;
import model.model.EventLogic;

/**
 * Servlet implementation class HomeS
 */
@WebServlet("/home")
public class HomeS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 設定されたフォワード先にフォワード
//	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
//	    dispatcher.forward(request, response);
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッションスコープからユーザー情報（めっちゃ使う）を取得
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		// ここから、ホーム画面に表示させる情報を取得していく！
		// そして、セッションスコープに格納する

		// 今日の記念日を取得＆格納
		AnniversaryLogic aL = new AnniversaryLogic();
		List<AnniversaryBean> annivTodayList = aL.selectAnnivToday(user.getUserId());
		session.setAttribute("annivTodayList", annivTodayList);

		// 今日のイベントを取得＆格納
		EventLogic eL = new EventLogic();
		List<EventBean> eventTodayList = eL.selectEventTodayLogic(user.getUserId());
		session.setAttribute("eventTodayList", eventTodayList);

		// カウントダウン分の記念日を取得＆格納
		List<AnniversaryBean> annivList = aL.selectAnnivCnt(user.getUserId(), user.getCountDwn());
		session.setAttribute("annivList", annivList);

		// 〇ヶ月分のイベントを取得＆格納
		List<EventBean> eventList = eL.selectEventGetLogic(user.getUserId());
		session.setAttribute("eventList", eventList);

		// フォワード
		// ホーム画面に行く
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
		dispatcher.forward(request, response);

	}

}
