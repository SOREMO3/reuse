package kr.co.userinsight.model;

import java.util.List;

import lombok.Data;

@Data
public class SearchResultDataModel {
	// <-- RD data를 대신하는 임시_책_오브젝트의 데이터 목록 필드
	private List<String> name;
	private List<String> author;
	private List<String> ISBN;
	private List<String> keyword;
	private List<String> overview;
	private List<String> overviewSimple;
	private List<String> imageUrl;
	// RD data를 대신하는 임시_책_오브젝트의 데이터 목록 필드 -->


	public void setName(List<String> name) {
		this.name = name;
	}

	public List<String> getName() {
		return this.name;
	}

	public void setAuthor(List<String> author) {
		this.author = author;
	}

	public List<String> getAuthor() {
		return this.author;
	}

	public void setOverview(List<String> overview) {
		this.overview = overview;
	}

	public List<String> getOverview() {
		return this.overview;
	}

	public void setOverviewSimple(List<String> overviewSimple) {
		this.overviewSimple = overviewSimple;
	}

	public List<String> getOverviewSimple() {
		return this.overviewSimple;
	}

	public void setImageUrl(List<String> imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getImageUrl() {
		return this.imageUrl;
	}
}
