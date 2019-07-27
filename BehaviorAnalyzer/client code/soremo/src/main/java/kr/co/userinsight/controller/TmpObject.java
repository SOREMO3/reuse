package kr.co.userinsight.controller;

// import java.util.List;
import java.util.ArrayList;

public class TmpObject {
	
	/*
	private List<String> title = new ArrayList<String>();
	private List<String> projectName = new ArrayList<String>();
	private List<String> author = new ArrayList<String>();
	private List<String> rdID = new ArrayList<String>();
	private List<String> rdType = new ArrayList<String>();
	private List<String> contents = new ArrayList<String>();
	private List<String> contentsLimited = new ArrayList<String>();
	private List<String> imageUrl = new ArrayList<String>();
	private List<String> date = new ArrayList<String>();
	*/
	
	// --------------------------------------
	// Added - Yonsei.
	// --------------------------------------
	private ArrayList<String> name = new ArrayList<String>();
	private ArrayList<String> overview = new ArrayList<String>();
	private ArrayList<String> overviewSimple = new ArrayList<String>();
	private ArrayList<String> sdFileInfo = new ArrayList<String>();
	private ArrayList<String> sdPackageInfo = new ArrayList<String>();
	private ArrayList<String> sdProjectInfo = new ArrayList<String>();
	private ArrayList<String> sdCodeInfo = new ArrayList<String>();
	private ArrayList<String> sdLimitedCodeInfo = new ArrayList<String>();
	
	public void addName(String name){
		this.name.add(name);
	}
	
	public void addSDFileInfo(String file) {
		this.sdFileInfo.add(file);
	}

	public void addSDPackageInfo(String sdPackageInfo) {
		this.sdPackageInfo.add(sdPackageInfo);
	}
	
	public void addSDProjectInfo(String sdProjectInfo) {
		this.sdProjectInfo.add(sdProjectInfo);
	}
	
	public void addSDCodeInfo(String sdCodeInfo) {
		this.sdCodeInfo.add(sdCodeInfo);
	}
	
	public void addSDLimitedCodeInfo(String sdLimitedCodeInfo) {
		this.sdLimitedCodeInfo.add(sdLimitedCodeInfo);
	}
	
	public void addOverview(String overview){
		this.overview.add(overview);
	}
	
	public void addOverviewSimple(String overviewSimple){
		this.overviewSimple.add(overviewSimple);
	}
	
	public ArrayList<String> getName(){
		return this.name;
	}
	
	public ArrayList<String> getSDFileInfo() {
		return this.sdFileInfo;
	}
	
	public ArrayList<String> getSDPackageInfo() {
		return this.sdPackageInfo;
	}
	
	public ArrayList<String> getSDProjectInfo() {
		return this.sdProjectInfo;
	}
	
	public ArrayList<String> getSDCodeInfo() {
		return this.sdCodeInfo;
	}
	
	public ArrayList<String> getSDLimitedCodeInfo() {
		return this.sdLimitedCodeInfo;
	}
	
	public ArrayList<String> getOverview(){
		return this.overview;
	}
	
	public ArrayList<String> getOverviewSimple(){
		return this.overviewSimple;
	}
	// --------------------------------------
	// Added - Yonsei. //
	// --------------------------------------

	/*
	public void addTitle(String title){
		
		this.title.add(title);
	}
	
	public void addProjectName(String projectName){
		this.projectName.add(projectName);
	}
	
	public void addAuthor(String author){
		this.author.add(author);
	}
	
	public void addRdID(String rdID){
		this.rdID.add(rdID);
	}
	
	public void addRdType(String rdType){
		this.rdType.add(rdType);
	}
	
	public void addContents(String contents){
		this.contents.add(contents);
	}
	
	public void addContentsLimited(String contentsLimited){
		this.contentsLimited.add(contentsLimited);
	}
	
	public void addImageUrl(String imageUrl){
		this.imageUrl.add(imageUrl);
	}
	
	public void addDate(String date){
		this.date.add(date);
	}
	
	public List<String> getTitle(){
		return this.title;
	}
	
	public List<String> getProjectName(){
		return this.projectName;
	}
	
	public List<String> getAuthor(){
		return this.author;
	}
	
	public List<String> getRdID(){
		return this.rdID;
	}
	
	public List<String> getRdType(){
		return this.rdType;
	}
	
	public List<String> getContents(){
		return this.contents;
	}
	
	public List<String> getContentsLimited(){
		return this.contentsLimited;
	}
	
	public List<String> getImageUrl(){
		return this.imageUrl;
	}
	
	public List<String> getDate(){
		return this.date;
	}
	*/
}
