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

import model.entity.TitleBean;
import model.entity.UserBean;
import model.model.TitleLogic;

/**
 * Servlet implementation class TitleOnOffS
 */
@WebServlet("/title_on_off")
public class TitleOnOffS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TitleOnOffS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// home.jspからhrefでリンク張っているので、
		// Postに流す
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッションスコープからユーザー情報（めっちゃ使う）を取得
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		// 元の作品リスト（ユーザーIDの全部ver）も取得
		List<TitleBean> titleListAll = (List<TitleBean>) session.getAttribute("titleListAll");

		// リクエストパラメーターの取得
		request.setCharacterEncoding("UTF-8");
		String[] titleOnOffList = request.getParameterValues("TitleOnOff");

		// titleの値を格納するやつ（BOに渡す）
		List<TitleBean> titleList = new ArrayList<TitleBean>();

		// 更新する値の確認とセット
		// 表示させたやつ（ユーザーが登録している全作品）の作品IDが、
		// チェックボックスONのリストにあれば、ONをセット
		// リストになければ、OFFをセット
		for (int i = 0; i < titleListAll.size(); i++) {

			for (int j = 0; j < titleOnOffList.length; j++) {

				// 同じ作品IDがあったら、ON（1）をセット
				if(titleListAll.get(i).getTitleId() == Integer.parseInt(titleOnOffList[j])) {

					TitleBean title = new TitleBean();
					title.setTitleId(titleListAll.get(i).getTitleId());
					title.settOnOff(1);
					titleList.add(title);
					break;

				}

				// 最後まで検索して、見つからなかったらOFF（O）
				if(j == titleOnOffList.length - 1) {

					TitleBean title = new TitleBean();
					title.setTitleId(titleListAll.get(i).getTitleId());
					title.settOnOff(0);
					titleList.add(title);
					break;

				}

			}

		}

		// 表示非表示の更新
		TitleLogic tL = new TitleLogic();
		boolean rtnOnOffUpdate = tL.titleOnOffUpdateLogic(titleList);

		// 更新の成功、失敗を確認
		if(rtnOnOffUpdate) {

			// 更新成功
			request.setAttribute("msgResult", "作品の表示非表示設定を更新しました。");

		} else {

			// 更新失敗
			request.setAttribute("msgResult", "作品の表示非表示設定の更新に失敗しました。");

		}

		// リザルト画面にフォワード
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
		rd.forward(request, response);

	}

}
