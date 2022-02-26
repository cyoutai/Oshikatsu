package model.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;

public class AnnivDate {

	public LocalDate annivDateChange(int annivMonth, int annivDay) {

		// 戻り値の変数定義（if文内で生成するので、初めに定義しておく）
		LocalDate annivYMD;

		// 受け取った月日から、MonthDay型を作成
		MonthDay annivMD = MonthDay.of(annivMonth, annivDay);

		// 現在の月日を取得
		MonthDay nowMD = MonthDay.now();

		// 現在の年を取得
		int nowY = LocalDate.now().getYear();

		// 受け取った月日が現在より前か（年をまたいでいるか）を判定
		// 現在より前（年をまたいでいる）なら、現在の年に＋1して、LacalDate型を作成
		// 現在・現在より後（年をまたいでいない）なら、現在の年でLacalDate型を作成
		// LacalDate型作成時、受け取った月日がうるうで、現在の年がうるう年でない場合、
		// 格納される日付は。2月28日になる。
		if(annivMD.isBefore(nowMD)) {

			// 年をまたいでいる（現在より前）
			annivYMD = annivMD.atYear(nowY + 1);

		} else {

			// 年をまたいでいない（現在、または現在より後）
			annivYMD = annivMD.atYear(nowY);

		}

		return annivYMD;

	}

	// 現在の年月日から受けっとった年月日までの日数をカウント
	public int annivDateCnt(LocalDate annivDateType) {

		LocalDate nowYMD = LocalDate.now();

		return (int) ChronoUnit.DAYS.between(nowYMD, annivDateType);

	}

	// 記念日のサーブレットで、有効な月日の組み合わせか
	// 確認するメソッド
	public boolean annivDateCheck(int annivMonth, int annivDay) {

		// 受け取った月日から、MonthDay型を作成
		// 作成でエラーになる＝月日の組み合わせが間違っている
		// ので、TryCatchで確認する
		try {

			MonthDay annivMD = MonthDay.of(annivMonth, annivDay);

		} catch (DateTimeException e) {

			return false;

		}

		return true;

	}

}
