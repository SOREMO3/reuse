package kr.co.userinsight.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.beans.Transient;
import java.util.Date;

@MappedSuperclass
public abstract class EntityHistoryManager extends AbstractEntity {


    private static final long serialVersionUID = -8669896493574539381L;

    private String desc ;



    @Column(name = "histr_desc", nullable = true, length = 120)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



}
