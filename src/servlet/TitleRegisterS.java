package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.entity.TitleBean;
import model.entity.TitleSite;
import model.entity.TitleTwitter;
import model.entity.UserBean;
import model.model.TitleLogic;

/**
 * Servlet implementation class TitleRegisterS
 */
@WebServlet("/title_register")
public class TitleRegisterS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TitleRegisterS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 設定されたフォワード先にフォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/title_register.jsp");
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

		// リクエストパラメーターに格納された名前を取得する（getParameterNames()）
		// Enumerationは、Mapのキーや値を取得して繰り返し処理を行いたい場合に使う型
		// Iteratorの仲間で、古いタイプ（Iteratorはメソッド名が短く、削除できる）
		Enumeration paramNames = request.getParameterNames();

		// 作品情報をセットする器を作っておく
		String titleName = null;
		String titleKn = null;
		List<TitleSite> siteList = new ArrayList<TitleSite>();
		List<TitleTwitter> twitterList = new ArrayList<TitleTwitter>();

		// BOを先に呼び出しておく（while内でも使うので）
		TitleLogic tL = new TitleLogic();

		// hasMoreElements()で要素があるか判定して、
		// 要素がある場合は処理をする
		while (paramNames.hasMoreElements()){

			// 要素を取り出す
			String name = (String)paramNames.nextElement();

			String value = request.getParameter(name);

			// 要素が「作品名」ならやること
			if(name.equals("titleName")) {

				// 取り出した要素の確認
				System.out.println("作品名のはず");
				System.out.println("取り出した要素の値（name）：" + name);
				System.out.println("取り出した要素の値（value）：" + value);

				// 作品名の重複を確認
				// 作品名が存在する（true）
			    // 作品名が存在しない（false）
				boolean rtnTitleCheck = tL.TitleNameCheckLogic(value, user.getUserId());

				// 値の重複確認
				System.out.println("作品名の重複確認：" + rtnTitleCheck);

				if(rtnTitleCheck) {

					// 値の重複
					System.out.println("値の重複あり");

					// 作品名が重複する場合は、
					// 作品名が重複している旨を伝える
					// リクエストスコープにメッセージを乗っけて送り返す
					request.setAttribute("msgTitle", "すでに登録されている作品のため、登録できませんでした。");
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/title_register.jsp");
					rd.forward(request, response);
					break;

				} else {

					// 値の重複
					System.out.println("値の重複なし");

					// 作品名が重複していなければ、値をセット
					titleName = value;

				}

			}

			// 要素が「かな」ならやること
			if(name.equals("titleKn")) {

				// 取り出した要素の確認
				System.out.println("かなのはず");
				System.out.println("取り出した要素の値（name）：" + name);
				System.out.println("取り出した要素の値（value）：" + value);

				// かなをセット
				titleKn = value;

			}

			// 要素が「サイト情報（url）」ならやること
			// 要素の先頭に「siteUrl」が含まれていたらやる
			if(name.startsWith("siteUrl")) {

				// 取り出した要素の確認
				System.out.println("サイトURLのはず");
				System.out.println("取り出した要素の値（name）：" + name);
				System.out.println("取り出した要素の値（value）：" + value);

				if(value.length() != 0) {

					// 空文字ではなかったら、
					// サイト情報（url）をセット
					TitleSite site = new TitleSite();
					site.setSiteUrl(value);
					siteList.add(site);

				}

			}

			// 要素が「Twitter情報（TwitterId）」ならやること
			// 要素の先頭に「twitterId」が含まれていたらやる
			if(name.startsWith("twitterId")) {

				// 取り出した要素の確認
				System.out.println("サイトURLのはず");
				System.out.println("取り出した要素の値（name）：" + name);
				System.out.println("取り出した要素の値（value）：" + value);

				if(value.length() != 0) {

					// 空文字ではなかったら、
					// Twitter情報（TwitterId）をセット
					TitleTwitter twitter = new TitleTwitter();
					twitter.setTwitterId(value);
					twitterList.add(twitter);
				}

			}

		}

		// リクエストパラメーターの要素を全回収したら、
		// 作品情報のインスタンスを生成
		TitleBean title = new TitleBean(user.getUserId(), titleName, titleKn, siteList, twitterList);

		// インスタンスに格納されているか確認
		System.out.println("■titleインスタンスに格納されているか確認");
		System.out.println("ユーザーID：" + title.getUserId());
		System.out.println("作品名：" + title.getTitleName());
		System.out.println("かなよみ：" + title.getTitleKn());
		for (TitleSite tS : siteList) {

			System.out.println("サイト情報：" + tS.getSiteUrl());

		}
		for (TitleTwitter tT : twitterList) {

			System.out.println("Twitter情報：" + tT.getTwitterId());

		}

		// 作品を登録する
		boolean rtnTitleRegister = false;
		if(title.getTitleName() != null) {

			rtnTitleRegister = tL.titleRegisterAllLogic(title);

		}

		// サイト登録の結果
		System.out.println("サイト登録の結果：" + rtnTitleRegister);

		if(rtnTitleRegister) {

			// 作品登録が成功したら、
			// リクエストスコープにメッセージを乗っけて、
			// リザルト画面に送る
			request.setAttribute("msgResult", "作品を登録しました。");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rd.forward(request, response);

		} else {

			// 作品登録が失敗したら、
			// リクエストスコープにメッセージを乗っけて送り返す
			request.setAttribute("msgTitle", "作品の登録ができませんでした。");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/title_register.jsp");
			rd.forward(request, response);

		}

	}

}
