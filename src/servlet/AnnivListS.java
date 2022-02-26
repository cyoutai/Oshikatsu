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

import model.entity.AnniversaryBean;
import model.entity.TitleBean;
import model.entity.UserBean;
import model.model.AnniversaryLogic;
import model.model.TitleLogic;

/**
 * Servlet implementation class AnnivListS
 */
@WebServlet("/anniv_list")
public class AnnivListS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnnivListS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// doPostに送る
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

		// 戻り値の受け皿を作成
		List<AnniversaryBean> annivDispList = new ArrayList<AnniversaryBean>();
		List<TitleBean> titleOnList = new ArrayList<TitleBean>();

		// BOの読み込み
		AnniversaryLogic aL = new AnniversaryLogic();
		TitleLogic tL = new TitleLogic();

		// 作品IDが「0」なら、表示設定の全作品の記念日情報を取得
		// 作品IDが指定されていれば、指定作品の記念日情報を取得
		if(onTitleId == 0) {

			annivDispList = aL.selectAnnivAll(user.getUserId());

		} else {

			annivDispList = aL.selectAnniv(onTitleId);

		}

		// 表示設定している作品のリストを取得
		titleOnList = tL.selectTitleOnLogic(user.getUserId());

		// 表示設定の作品リストを表示
		for (TitleBean tB : titleOnList) {

			System.out.println(tB.getTitleName());

		}

		// リクエストスコープに取得した値を格納
		request.setAttribute("onTitleId", onTitleId);
		request.setAttribute("annivDispList", annivDispList);
		request.setAttribute("titleOnList", titleOnList);

		// フォワード
		// 整ったら、記念日一覧画面へ送る
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/anniv_list.jsp");
		rd.forward(request, response);

	}

}
