package kr.co.userinsight.model;

public class SimilarityModel {
	private String word;
	private int similarity;

	public SimilarityModel(String word, int sim) {
		this.word = word;
		this.similarity = sim;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getSimilarity() {
		return similarity;
	}

	public void setSimilarity(int similarity) {
		this.similarity = similarity;
	}

}
