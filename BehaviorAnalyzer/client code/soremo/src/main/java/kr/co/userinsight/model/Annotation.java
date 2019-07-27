package kr.co.userinsight.model;

import java.util.LinkedList;


public class Annotation {
	
	String RelationTypes;
	String AIContents;
	String tmpProcess;
	private String numOfWords;
	private String numOfTables;
	private String numOfImages;
	
	public Annotation(){
		RelationTypes = null;
		AIContents = null;
		tmpProcess = null;
		numOfWords = null;
		numOfTables = null;
		numOfImages = null;
	}	
	
	
	public String getNumOfWords() {
		return numOfWords;
	}

	public void setNumOfWords(String numOfWords) {
		this.numOfWords = numOfWords;
	}
	
	public String getNumOfTables() {
		return numOfTables;
	}

	public void setNumOfTables(String numOfTables) {
		this.numOfTables = numOfTables;
	}
	
	public String getNumOfImages() {
		return numOfImages;
	}

	public void setNumOfImages(String numOfImages) {
		this.numOfImages = numOfImages;
	}

	public String getRelationTypes() {
		return RelationTypes;
	}
	public void setRelationTypes(String relationTypes) {
		RelationTypes = relationTypes;
	}
	public String getAIContents() {
		return AIContents;
	}
	public void setAIContents(String aIContents) {
		AIContents = aIContents;
	}
	public String getTmpProcess() {
		return tmpProcess;
	}
	public void setTmpProcess(String tmpProcess) {
		this.tmpProcess = tmpProcess;
	}




	class RelationType {
		
		private String Relation;
		private String From;
		private String To;
		public String getRelation() {
			return Relation;
		}
		public void setRelation(String relation) {
			Relation = relation;
		}
		public String getFrom() {
			return From;
		}
		public void setFrom(String from) {
			From = from;
		}
		public String getTo() {
			return To;
		}
		public void setTo(String to) {
			To = to;
		}
		
		
	}
	class AIItem {
		
		//private String Tag;
		
		private LinkedList<String> Tag = new LinkedList<String>();
		
		public LinkedList<String> getTag() {
			return Tag;
		}

		public void setTag(LinkedList<String> tag) {
			Tag = tag;
		}	
	}
	class Process {
		
	}
	
}