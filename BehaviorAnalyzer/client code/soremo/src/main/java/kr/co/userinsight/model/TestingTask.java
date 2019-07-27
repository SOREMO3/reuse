package kr.co.userinsight.model;

import lombok.Data;

/**
 * 
 * @author yungu
 * @category 2세부: 테스트 계획
 */
@Data
public class TestingTask {
	public TestingTask(String taskName, String content) {
		this.taskName = taskName;
		this.content = content;
	}
	
	private String taskName;
	private String content;
}
