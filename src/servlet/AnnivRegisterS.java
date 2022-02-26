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
import model.entity.TitleBean;
import model.entity.UserBean;
import model.model.AnnivDate;
import model.model.AnniversaryLogic;
import model.model.TitleLogic;

/**
 * Servlet implementation class AnnivRegisterS
 */
@WebServlet("/anniv_register")
public class AnnivRegisterS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnnivRegisterS() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/anniv_register.jsp");
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
		// int annivId = Integer.parseInt(request.getParameter("annivId"));
		String userId = user.getUserId();

		// 作品が何も登録されていなくて、登録しようとした場合、
		// 作品が何も登録されていない旨を返す
		String titleIdS = request.getParameter("titleId");
		int titleId = 0;
		if(titleIdS == null || titleIdS.length() == 0) {

			// 作品が何も登録されていなかったら、
			// リクエストコープにもろもろ格納して、入力画面へ送り返す
			request.setAttribute("msgAnnivErr", "まずは作品登録から行ってください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/anniv_register.jsp");
			dispatcher.forward(request, response);

		} else {

			titleId = Integer.parseInt(titleIdS);

		}

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

		String annivCnt = null;
		String annivCntText = request.getParameter("annivCnt");
		// 自由入力（annivCntText）の場合は、値を取得
		// 値が「なし」なら、nullをセット
		if(annivCntText.equals("annivCntText")) {

			annivCnt = request.getParameter("annivCntText");

		} else if(annivCntText.equals("なし")) {

			annivCnt = null;

		} else {

			annivCnt = annivCntText;

		}

		String annivClass = null;
		String annivClassText = request.getParameter("annivClass");
		// 自由入力（annivClassText）の場合は、値を取得
		// 値が「なし」なら、nullをセットｓ
		if(annivClassText.equals("annivClassText")) {

			annivClass = request.getParameter("annivClassText");

		} else if(annivClassText.equals("なし")) {

			annivClass = null;

		} else {

			annivClass = annivClassText;

		}

		String annivClr = request.getParameter("annivClr");

		// 記念日のインスタンスを作成
		AnniversaryBean annivInfo = new AnniversaryBean();
		// annivInfo.setAnnivId(annivId);
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/anniv_register.jsp");
			dispatcher.forward(request, response);

		} else {

			// 月日がおかしくなかったら、
			// 更新処理を行う
			AnniversaryLogic aL = new AnniversaryLogic();
			boolean rtnAnnivRegister = aL.annivRegister(annivInfo);

			// 更新結果の判定
			if(rtnAnnivRegister) {

				// 記念日の更新が成功したら、
				// リクエストスコープにメッセージを乗っけて、
				// リザルト画面に送る
				request.setAttribute("msgResult", "記念日・誕生日情報を登録しました。");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
						rd.forward(request, response);

			} else {

				// 作品登録が失敗したら、
				// リクエストスコープにメッセージを乗っけて送り返す
				request.setAttribute("msgTitle", "記念日・誕生日情報の登録ができませんでした。");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/anniv_register.jsp");
				rd.forward(request, response);

			}

		}

	}

}
