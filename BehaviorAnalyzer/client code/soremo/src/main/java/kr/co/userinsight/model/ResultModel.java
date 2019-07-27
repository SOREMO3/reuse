package kr.co.userinsight.model;

public class ResultModel {
	private String title;
	private String prjName;
	private String author;
	private String rdID;
	private String rdType;
	private String contents;
	// private String contentsLimited;
	private String imageUrl;
	private String date;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getRdID() {
		return rdID;
	}

	public void setRdID(String rdID) {
		this.rdID = rdID;
	}

	public String getRdType() {
		return rdType;
	}

	public void setRdType(String rdType) {
		this.rdType = rdType;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		/*
		 * if(contents.length()>=250){ String tmpString1 = contents.substring(0,
		 * 120); String tmpString2 = contents.substring(contents.length() -
		 * 120); this.contents = tmpString1 + "..." + tmpString2; } else{
		 * this.contents = contents; }
		 */
		this.contents = contents;
	}

	// public String getContentsLimited() {
	// return contentsLimited;
	// }

	// public void setContentsLimited(String contentsLimited) {
	// /*
	// * if(contentsLimited.length()>=500){ // prevention of table broken due
	// * to web tag String tmpString1 = contentsLimited.substring(0, 245);
	// * String tmpString2 =
	// * contentsLimited.substring(contentsLimited.length() - 245);
	// * this.contentsLimited = tmpString1 + "..." + tmpString2; } else{
	// * this.contentsLimited = contentsLimited; }
	// */
	// this.contentsLimited = contentsLimited;
	// }

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
