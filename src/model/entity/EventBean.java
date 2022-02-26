package model.entity;

import java.io.Serializable;
import java.util.Date;

public class EventBean implements Serializable {

	private int eventId;
	private String userId;
	private int titleId;
	private String eventName;
	private String eventStart;
	private String eventEnd;
	private String eventLoc;
	private String eventUrl;
	private String eventShop;
	private Date eventStartDateType;
	private Date eventEndDateType;
	private int eventEndCnt;
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {

		this.eventId = eventId;

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

	public String getEventName() {

		return eventName;

	}

	public void setEventName(String eventName) {

		this.eventName = eventName;

	}

	public String getEventStart() {

		return eventStart;

	}

	public void setEventStart(String eventStart) {

		this.eventStart = eventStart;

	}

	public String getEventEnd() {

		return eventEnd;

	}

	public void setEventEnd(String eventEnd) {

		this.eventEnd = eventEnd;

	}

	public String getEventLoc() {

		return eventLoc;

	}

	public void setEventLoc(String eventLoc) {

		this.eventLoc = eventLoc;

	}

	public String getEventUrl() {

		return eventUrl;

	}

	public void setEventUrl(String eventUrl) {

		this.eventUrl = eventUrl;

	}

	public String getEventShop() {

		return eventShop;

	}

	public void setEventShop(String eventShop) {

		this.eventShop = eventShop;

	}

	public Date getEventStartDateType() {

		return eventStartDateType;

	}

	public void setEventStartDateType(Date eventStartDateType) {

		this.eventStartDateType = eventStartDateType;

	}

	public Date getEventEndDateType() {

		return eventEndDateType;

	}

	public void setEventEndDateType(Date eventEndDateType) {

		this.eventEndDateType = eventEndDateType;

	}

	public int getEventEndCnt() {

		return eventEndCnt;

	}

	public void setEventEndCnt(int eventEndCnt) {

		this.eventEndCnt = eventEndCnt;

	}

}
