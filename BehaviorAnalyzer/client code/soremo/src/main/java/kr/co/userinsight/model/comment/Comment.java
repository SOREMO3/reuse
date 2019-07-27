package kr.co.userinsight.model.comment;

import kr.co.userinsight.model.entity.EntityA;

import javax.persistence.*;

@Entity
@Table(name = "td_comment")
public class Comment  extends EntityA{

    private String desc ;
    private String descEn;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }


    @Column(name = "comment_desc", nullable = true)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Column(name = "comment_descEn", nullable = true)
    public String getDescEn() {
        return descEn;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }


}
