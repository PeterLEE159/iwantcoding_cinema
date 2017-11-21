package com.client.vo;

import java.util.List;

public class Pagination {
	private int page;
	private String opt;
	private String keyword;
	private List<Integer> genre;
	private int scoops = 20;
	
	
	public int getBeginIndex() {
		return (page - 1) * scoops + 1;
	}
	public int getEndIndex() {
		return page * scoops;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public List<Integer> getGenre() {
		return genre;
	}
	public void setGenre(List<Integer> genre) {
		this.genre = genre;
	}
	@Override
	public String toString() {
		return "Pagination [page=" + page + ", opt=" + opt + ", keyword=" + keyword + ", genre=" + genre + "]";
	}
	
	
}
