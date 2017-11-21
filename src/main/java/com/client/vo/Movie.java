package com.client.vo;

import java.util.Date;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("MOVIES")
public class Movie extends BaseVO{
	@Column("ID")
	private int id;
	@Column("DUBBED")
	private int dubbed;
	@Column("PUBLISH_DATE")
	private Date publishDate;
	@Column("OPEN_DATE")
	private Date openDate;
	@Column("CLOSE_DATE")
	private Date closeDate;
	@Column("AGE_LIMIT")
	private int ageLimit;
	
	
	
	@Override
	public String toString() {
		return "Movie [id=" + id + ", dubbed=" + dubbed + ", publishDate=" + publishDate + ", openDate=" + openDate
				+ ", closeDate=" + closeDate + ", ageLimit=" + ageLimit + "]";
	}

	public int getDubbed() {
		return dubbed;
	}

	public void setDubbed(int dubbed) {
		this.dubbed = dubbed;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	public int getAgeLimit() {
		return ageLimit;
	}
	public void setAgeLimit(int ageLimit) {
		this.ageLimit = ageLimit;
	}
	@Override
	public BaseVO newInstance() {
		return new Movie();
	}
}
