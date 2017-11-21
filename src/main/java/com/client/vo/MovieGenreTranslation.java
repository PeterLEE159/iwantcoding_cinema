package com.client.vo;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;

@Table("GENRE_TRANSLATION")
public class MovieGenreTranslation extends BaseVO{
	@Column("ID")
	private int id;
	@Column("TYPE")
	private String type;
	@Column("LANG_ISO")
	private String langIso;
	@Column("LANG_DEFAULT")
	private int langDefault;
	@Column("GENRE_ID")
	private int genreId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getLangIso() {
		return langIso;
	}
	public void setLangIso(String langIso) {
		this.langIso = langIso;
	}
	public int getLangDefault() {
		return langDefault;
	}
	public void setLangDefault(int langDefault) {
		this.langDefault = langDefault;
	}
	public int getGenreId() {
		return genreId;
	}
	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}
	
	@Override
	public String toString() {
		return "MovieGenreTranslation [id=" + id + ", type=" + type + ", langIso=" + langIso + ", langDefault="
				+ langDefault + ", genreId=" + genreId + "]";
	}
	@Override
	public BaseVO newInstance() {
		return new MovieGenreTranslation();
	}
	
	
}
