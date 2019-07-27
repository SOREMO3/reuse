package kr.co.userinsight.model.entity;

public interface EntityRef {

    Integer getSortIndex();

    EStatusRecord getStatusRecord();

    void setStatusRecord(EStatusRecord statusRecord);

    void fillSysBlock(String username);

}
