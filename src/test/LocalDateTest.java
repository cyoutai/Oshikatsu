package test;

import java.time.LocalDate;
import java.time.MonthDay;

public class LocalDateTest {

	public static void main(String[] args) {

		MonthDay urudoshi = MonthDay.of(2, 29);
		LocalDate sonzaisinai = urudoshi.atYear(2021);

		System.out.println(sonzaisinai);

		System.out.println("月日を求めるテスト");

		int sanketanosuuji = 111;
		int futaketanosuuji = 1214;

		int sanketaM = sanketanosuuji / 100;
		int sanketaD = sanketanosuuji % 100;

		int futaketaM = futaketanosuuji / 100;
		int futaketaD = futaketanosuuji % 100;

		System.out.println(sanketanosuuji + "を月日にすると、" + sanketaM + "月" + sanketaD + "日");
		System.out.println(futaketanosuuji + "を月日にすると、" + futaketaM + "月" + futaketaD + "日");

	}

}
