package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.entity.AnniversaryBean;
import model.entity.UserBean;
import model.model.AnnivDate;
import model.model.AnniversaryLogic;

/**
 * Servlet implementation class AnnivUpdateS
 */
@WebServlet("/anniv_update")
public class AnnivUpdateS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnnivUpdateS() {
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
		int annivId = Integer.parseInt(request.getParameter("annivId"));
		String userId = request.getParameter("userId");
		int titleId = Integer.parseInt(request.getParameter("titleId"));
		String annivName = request.getParameter("annivName");
		String annivYearS = request.getParameter("annivYear");
		int annivYear = 0;
		if(annivYearS != null) {

			try {

				annivYear = Integer.parseInt(annivYearS);

			} catch (NumberFormatException e) {

				annivYear = 0;

			}

		}
		int annivMonth = Integer.parseInt(request.getParameter("annivMonth"));
		int annivDay  = Integer.parseInt(request.getParameter("annivDay"));

		String annivCnt = request.getParameter("annivCnt");
		// 値がなかったら、nullをセット
		if(annivCnt == null || annivCnt.length() == 0) {

			annivCnt = null;

		}

		// 値がなかったら、nullをセット
		String annivClass = request.getParameter("annivClass");
		if(annivClass == null || annivClass.length() == 0) {

			annivClass = null;

		}

		String annivClr = request.getParameter("annivClr");

		// 記念日のインスタンスを作成
		AnniversaryBean annivInfo = new AnniversaryBean();
		annivInfo.setAnnivId(annivId);
		annivInfo.setUserId(userId);
		annivInfo.setTitleId(titleId);
		annivInfo.setAnnivName(annivName);
		annivInfo.setAnnivYear(annivYear);
		annivInfo.setAnnivMonth(annivMonth);
		annivInfo.setAnnivDay(annivDay);
		annivInfo.setAnnivCnt(annivCnt);
		annivInfo.setAnnivClass(annivClass);
		annivInfo.setAnnivClr(annivClr);

		// 記念日の月日が正しいか確認
		// 正しくなかったら、メッセージを送り返す
		AnnivDate aD = new AnnivDate();
		boolean rtnAnnivDateCheck = aD.annivDateCheck(annivMonth, annivDay);
		if(!rtnAnnivDateCheck) {

			// 月日がおかしかったら、
			// リクエストコープにもろもろ格納して、入力画面へ送り返す
			request.setAttribute("annivInfo", annivInfo);
			request.setAttribute("msgAnnivErr", "記念日・誕生日の月日の値が正しくありません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/anniv_form.jsp");
			dispatcher.forward(request, response);

		} else {

			// 月日がおかしくなかったら、
			// 更新処理を行う
			AnniversaryLogic aL = new AnniversaryLogic();
			boolean rtnAnnivUpdate = aL.annivUpdate(annivInfo);

			// 更新結果の判定
			if(rtnAnnivUpdate) {

				// 記念日の更新が成功したら、
				// リクエストスコープにメッセージを乗っけて、
				// リザルト画面に送る
				request.setAttribute("msgResult", "記念日・誕生日情報を更新しました。");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				rd.forward(request, response);

			} else {

				// 作品登録が失敗したら、
				// リクエストスコープにメッセージを乗っけて送り返す
				request.setAttribute("msgTitle", "記念日・誕生日情報の更新ができませんでした。");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/anniv_form.jsp");
				rd.forward(request, response);

			}
		}

	}

}
