package kr.co.userinsight.model.entity;

import java.util.List;

public interface StatusRecordListAware {
    List<EStatusRecord> getStatusRecordList();
    void setStatusRecordList(List<EStatusRecord> statusRecordList);
}
