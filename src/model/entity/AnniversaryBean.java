package model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class AnniversaryBean implements Serializable {

	private int annivId;
	private String userId;
	private int titleId;
	private String annivName;
	private int annivYear;
	private int annivMonth;
	private int annivDay;
	private String annivCnt;
	private String annivClass;
	private String annivClr;
	private LocalDate annivDateType;
	private int annivDateCnt;
	private int annivCountDwn;
	private Date annivDateTypeDisplay;


	public int getAnnivId() {

		return annivId;

	}

	public void setAnnivId(int annivId) {

		this.annivId = annivId;

	}

	public String getUserId() {

		return userId;

	}

	public void setUserId(String userId) {

		this.userId = userId;

	}

	public int getTitleId() {

		return titleId;

	}

	public void setTitleId(int titleId) {

		this.titleId = titleId;

	}

	public String getAnnivName() {

		return annivName;

	}

	public void setAnnivName(String annivName) {

		this.annivName = annivName;

	}

	public int getAnnivYear() {

		return annivYear;

	}

	public void setAnnivYear(int annivYear) {

		this.annivYear = annivYear;

	}

	public int getAnnivMonth() {

		return annivMonth;

	}

	public void setAnnivMonth(int annivMonth) {

		this.annivMonth = annivMonth;

	}

	public int getAnnivDay() {

		return annivDay;

	}

	public void setAnnivDay(int annivDay) {

		this.annivDay = annivDay;

	}

	public String getAnnivCnt() {

		return annivCnt;

	}

	public void setAnnivCnt(String annivCnt) {

		this.annivCnt = annivCnt;

	}

	public String getAnnivClass() {

		return annivClass;

	}

	public void setAnnivClass(String annivClass) {

		this.annivClass = annivClass;

	}

	public String getAnnivClr() {

		return annivClr;

	}

	public void setAnnivClr(String annivClr) {

		this.annivClr = annivClr;

	}

	public LocalDate getAnnivDateType() {

		return annivDateType;

	}

	public void setAnnivDateType(LocalDate annivDateType) {

		this.annivDateType = annivDateType;

	}

	public int getAnnivDateCnt() {

		return annivDateCnt;

	}

	public void setAnnivDateCnt(int annivDateCnt) {

		this.annivDateCnt = annivDateCnt;

	}

	public int getAnnivCountDwn() {

		return annivCountDwn;

	}

	public void setAnnivCountDwn(int annivCountDwn) {

		this.annivCountDwn = annivCountDwn;

	}

	public Date getAnnivDateTypeDisplay() {

		return annivDateTypeDisplay;

	}

	public void setAnnivDateTypeDisplay(Date annivDateTypeDisplay) {

		this.annivDateTypeDisplay = annivDateTypeDisplay;

	}


}
