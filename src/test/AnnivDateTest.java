package test;

import java.time.LocalDate;

import model.model.AnnivDate;

public class AnnivDateTest {

	public static void main(String[] args) {

		// 月日のLocalDate型変換
		annivDateChangeTest();

		// 日付のカウント
		annivDateCntTest();

	}

	// 月日のLocalDate型変換
	public static void annivDateChangeTest() {

		AnnivDate annivDate = new AnnivDate();

		// 年をまたがない場合
		LocalDate mataganai = annivDate.annivDateChange(12, 26);

		// 年をまたぐ場合
		LocalDate matagu = annivDate.annivDateChange(1, 7);

		System.out.println("■月日のLocalDate型変換");
		System.out.println("年をまたがない：" + mataganai);
		System.out.println("年をまたぐ：" + matagu);
		System.out.println();

	}

	// 現在から記念日までの日数カウント
	public static void annivDateCntTest() {

		AnnivDate annivDate = new AnnivDate();

		// テスト用の日付（年をまたがない）
		LocalDate lD1 = LocalDate.of(2021, 12, 14);

		// テスト用の日付（年をまたぐ）
		LocalDate lD2 = LocalDate.of(2022, 1, 7);

		int cnt1 = annivDate.annivDateCnt(lD1);
		int cnt2 = annivDate.annivDateCnt(lD2);

		System.out.println("■月日のLocalDate型変換");
		System.out.println("年をまたがない：" + lD1 + "まであと" + cnt1 + "日");
		System.out.println("年をまたぐ："+ lD2 + "まであと" + cnt2 + "日");
		System.out.println();

	}

}
