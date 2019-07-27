package kr.co.userinsight.model;

import java.util.List;
import java.util.ArrayList;

import lombok.Data;


@Data
public class TestingActivity {
	private String activityName;
	private ArrayList<TestingTask> taskList = new ArrayList<TestingTask>();
	
	public void setActivityName(String activityName){
		this.activityName = activityName;
	}
	
	public void setTaskList(List<TestingTask> taskList) {
		this.taskList = (ArrayList)taskList;
	}
	
	public String getActivityName() {
		return this.activityName;
	}

	public List<TestingTask> getTaskList(){
		return this.taskList;
	}
}
