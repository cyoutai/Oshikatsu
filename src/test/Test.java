package test;

import java.time.MonthDay;

import model.entity.TitleSite;

public class Test {

	public static void main(String[] args) {

		TitleSite ts = new TitleSite();

		System.out.println("SiteId:" + ts.getSiteId());
		System.out.println("SiteURL:" + ts.getSiteUrl());

		// 現在の月日を取得
		MonthDay nowMD = MonthDay.now();

		System.out.println(nowMD);

		int m = nowMD.getMonthValue();
		int d = nowMD.getDayOfMonth();

		int md = m * 100 + d;

		System.out.println(md);


	}

}
