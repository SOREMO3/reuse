package kr.co.userinsight.model.keyword;

import kr.co.userinsight.model.entity.EntityA;

import javax.persistence.*;

@Entity
@Table(name = "td_keyword")
public class Keyword extends EntityA{

    private String desc ;
    private String descEn;
//    private String rd_id;

    /*private List<ReuseKeyword> reuseKeyword;*/

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "keyword_desc", nullable = true)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Column(name = "keyword_descEn", nullable = true)
    public String getDescEn() {
        return descEn;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

  /*  @OneToMany(mappedBy="keyword", fetch = FetchType.LAZY)
    public List<ReuseKeyword> getReuseKeyword() {
        return reuseKeyword;
    }

    public void setReuseKeyword(List<ReuseKeyword> reuseKeyword) {
        this.reuseKeyword = reuseKeyword;
    }*/

//    @Column(name = "keyword_rd_id",nullable = true , length = 120)
//    public String getRd_id() {
//        return rd_id;
//    }
//
//    public void setRd_id(String rd_id) {
//        this.rd_id = rd_id;
//    }
}
