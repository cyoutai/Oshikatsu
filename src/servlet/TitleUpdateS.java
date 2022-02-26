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
 * Servlet implementation class TitleUpdateS
 */
@WebServlet("/title_update")
public class TitleUpdateS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TitleUpdateS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 設定されたフォワード先にフォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/title_form.jsp");
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
		int titleId = 0;
		String userId = null;
		String titleName = null;
		String titleKn = null;
		List<TitleSite> siteList = new ArrayList<TitleSite>();
		List<TitleTwitter> twitterList = new ArrayList<TitleTwitter>();
		int tOnOff = 0;
		int siteId = 0;
		int tTwitterId = 0;

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

//				// 作品名の重複を確認
//				// 作品名が存在する（true）
//				// 作品名が存在しない（false）
//				boolean rtnTitleCheck = tL.TitleNameCheckLogic(value, user.getUserId());
//
//				// 値の重複確認
//				System.out.println("作品名の重複確認：" + rtnTitleCheck);
//
//				if(rtnTitleCheck) {
//
//					// 値の重複
//					System.out.println("値の重複あり");
//
//					// 作品名が重複する場合は、
//					// 作品名が重複している旨を伝える
//					// リクエストスコープにメッセージを乗っけて送り返す
//					request.setAttribute("msgTtileName", "作品名が、すでに登録されている作品名と重複したため、更新できませんでした。");
//					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/title_form.jsp");
//					rd.forward(request, response);
//					break;
//
//				} else {
//
//					// 値の重複
//					System.out.println("値の重複なし");
//
//					// 作品名が重複していなければ、値をセット
//					titleName = value;
//
//				}

				// 作品名をセット
				titleName = value;

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

			// 要素が「作品ID」ならやること
			if(name.equals("titleId")) {

				// 作品IDをセット
				titleId = Integer.parseInt(value);

			}

			// 要素が「ユーザーID」ならやること
			if(name.equals("userId")) {

				// ユーザーIDをセット
				userId = value;

			}

			// 要素が「表示非表示設定」ならやること
			if(name.equals("tOnOff")) {

				// 作品IDをセット
				tOnOff = Integer.parseInt(value);

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
					// まずは、普通に取り出した値をセット
					site.setSiteUrl(value);

					// 荒業
					// siteUrl〇〇の〇〇の数字部分を取り出し、
					// siteId〇〇として、リクエストスコープから値を取り出す
					String siteNo = name.substring(7);
					String siteIdS = request.getParameter("siteId" + siteNo);

					// siteId〇〇が存在しない場合は、nullが入るので、
					// nullだったら、siteIdは「0」のまま（初期値）
					// nullでなければ、取得した値をint型にして格納
					if(siteIdS != null) {

						siteId = Integer.parseInt(siteIdS);

					}

					// サイトIDを取り出したらセット
					site.setSiteId(siteId);

					// サイトリストに加える
					siteList.add(site);

				}

			}

			// 要素が追加の「サイト情報（url）」ならやること
			// 要素の先頭に「addSiteUrl」が含まれていたらやる
			if(name.startsWith("addSiteUrl")) {

				if(value.length() != 0) {

					// 空文字ではなかったら、
					// サイト情報（url）をセット
					TitleSite site = new TitleSite();
					// サイトIDはないので、普通に取り出した値をセット
					site.setSiteUrl(value);
					// サイトリストに加える
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
					// まずは、普通に取り出した値をセット
					twitter.setTwitterId(value);

					// 荒業
					// twitterId〇〇の〇〇の数字部分を取り出し、
					// tTwiterId〇〇として、リクエストスコープから値を取り出す
					String twitterNo = name.substring(9);
					String twitterNoS = request.getParameter("tTwitterId" + twitterNo);

					// tTwitterId〇〇が存在しない場合は、nullが入るので、
					// nullだったら、tTwitterIdは「0」のまま（初期値）
					// nullでなければ、取得した値をint型にして格納
					if(twitterNoS != null) {

						tTwitterId = Integer.parseInt(twitterNoS);

					}

					// tTwitterIDを取り出したらセット
					twitter.settTwitterId(tTwitterId);

					// Twitterリストに加える
					twitterList.add(twitter);

				}

			}

			// 要素が追加の「Twitter情報（TwitterId）」ならやること
			// 要素の先頭に「addTwitterId」が含まれていたらやる
			if(name.startsWith("addTwitterId")) {

				if(value.length() != 0) {

					// 空文字ではなかったら、
					// Twitter情報（TwitterId）をセット
					TitleTwitter twitter = new TitleTwitter();
					// tTwitterIDはないので、普通に取り出した値をセット
					twitter.setTwitterId(value);
					// サイトリストに加える
					twitterList.add(twitter);;

				}

			}

		}

		// リクエストパラメーターの要素を全回収したら、
		// 作品情報のインスタンスを生成
		// 各値をセット
		TitleBean title = new TitleBean();
		title.setTitleId(titleId);
		title.setUserId(userId);
		title.setTitleName(titleName);
		title.setTitleKn(titleKn);
		title.setSite(siteList);
		title.setTwitter(twitterList);
		title.settOnOff(tOnOff);


		// インスタンスに格納されているか確認
		System.out.println("■titleインスタンスに格納されているか確認");
		System.out.println("ユーザーID：" + title.getUserId());
		System.out.println("作品名：" + title.getTitleName());
		System.out.println("かなよみ：" + title.getTitleKn());
		for (TitleSite tS : siteList) {

			System.out.println("サイト情報" + tS.getSiteId() + "：" + tS.getSiteUrl());

		}
		for (TitleTwitter tT : twitterList) {

			System.out.println("Twitter情報" +  tT.gettTwitterId() + "：" + tT.getTwitterId());

		}

		// 作品情報を更新する
		boolean rtnTitleUpdate = tL.titleUpdateAllLogic(title);

		// サイト登録の結果
		System.out.println("サイト登録の結果：" + rtnTitleUpdate);

		if(rtnTitleUpdate) {

			// 作品登録が成功したら、
			// リクエストスコープにメッセージを乗っけて、
			// リザルト画面に送る
			request.setAttribute("msgResult", "作品情報を更新しました。");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			rd.forward(request, response);

		} else {

			// 作品登録が失敗したら、
			// リクエストスコープにメッセージを乗っけて送り返す
			request.setAttribute("msgTitle", "作品情報の更新ができませんでした。");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/title_register.jsp");
			rd.forward(request, response);

		}

	}


}
