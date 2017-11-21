package com.client.vo;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("MOVIE_IMAGES")
public class MovieImage extends BaseVO{
	@Column("ID")
	private int id;
	@Column("IMAGE_URI")
	private String uri;
	@Column("MOVIE_ID")
	private int movieId;
	
	@Override
	public String toString() {
		return "MovieImage [id=" + id + ", uri=" + uri + ", movieId=" + movieId + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	@Override
	public BaseVO newInstance() {
		return new MovieImage();
	}
}
