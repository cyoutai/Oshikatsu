package model.entity;

import java.io.Serializable;
import java.util.List;

public class TitleBean implements Serializable {

	private int titleId;					// 作品ID
	private String userId;					// ユーザーID
	private String titleName;				// 作品名
	private String titleKn;					// 作品名（かな）
	private List<TitleSite> site;			// 公式サイトURL（サイトID、サイトURL）
	private List<TitleTwitter> twitter;		// TwittrID（管理用のtTwiterId、TwittrID）
	private int tOnOff;					// 表示（1）、非表示（0）


	public TitleBean() {

	}

	public TitleBean(String userId, String titleName, String titleKn, List<TitleSite> site,
			List<TitleTwitter> twitter) {

		this.titleId = 0;
		this.userId = userId;
		this.titleName = titleName;
		this.titleKn = titleKn;
		this.site = site;
		this.twitter = twitter;
		this.tOnOff = 1;				// 表示（1）、非表示（0）、デフォルトは「1」

	}

	public int getTitleId() {

		return titleId;

	}

	public void setTitleId(int titleId) {

		this.titleId = titleId;

	}

	public String getUserId() {

		return userId;

	}

	public void setUserId(String userId) {

		this.userId = userId;

	}

	public String getTitleName() {

		return titleName;

	}

	public void setTitleName(String titleName) {

		this.titleName = titleName;

	}

	public String getTitleKn() {

		return titleKn;

	}

	public void setTitleKn(String titleKn) {

		this.titleKn = titleKn;

	}

	public List<TitleSite> getSite() {

		return site;

	}

	public void setSite(List<TitleSite> site) {

		this.site = site;

	}

	public List<TitleTwitter> getTwitter() {

		return twitter;

	}
	public void setTwitter(List<TitleTwitter> twitter) {

		this.twitter = twitter;

	}

	public int gettOnOff() {

		return tOnOff;

	}

	public void settOnOff(int tOnOff) {

		this.tOnOff = tOnOff;

	}

}
