package model.entity;

import java.io.Serializable;

public class TitleSite implements Serializable {

	private int siteId;		// サイトID
	private String siteUrl;		// 公式サイトURL

	public int getSiteId() {

		return siteId;

	}

	public void setSiteId(int siteId) {

		this.siteId = siteId;

	}

	public String getSiteUrl() {

		return siteUrl;

	}

	public void setSiteUrl(String siteUrl) {

		this.siteUrl = siteUrl;

	}

}
