package kr.co.userinsight.model.reuse;

import kr.co.userinsight.model.comment.Comment;
import kr.co.userinsight.model.entity.EntityA;
import kr.co.userinsight.model.userhistory.UserHistory;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "td_reuse_history", indexes = {
        @Index(name = "idx_rd_id", columnList = "rd_id"),
})
public class ReuseHistory extends EntityA{

   // private Long userId ;
    private Long numberOfReuse;
    private String rd_id ;
    private Date reuseDate;


//    private List<ReuseComment> reuseComments;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reuse_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

//    @Column(name = "user_id", nullable = true)
//    public long getUserId() {
//        return userId;
//    }

    @Column(name = "reuse_count", nullable = true)
    public Long getNumberOfReuse() {
        return numberOfReuse;
    }

    @Column(name = "rd_id", nullable = true, length = 20)
    public String getRd_id() {
        return rd_id;
    }


//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }


    public void setRd_id(String rd_id) {
        this.rd_id = rd_id;
    }

    public void setNumberOfReuse(Long numberOfReuse) {
        this.numberOfReuse = numberOfReuse;
    }


    @Column(name = "reuse_date", nullable = true)
    public Date getReuseDate() {
        return reuseDate;
    }

    public void setReuseDate(Date reuseDate) {
        this.reuseDate = reuseDate;
    }
//
//    @OneToMany(mappedBy="reuseHistory", fetch = FetchType.LAZY)
//    public List<ReuseComment> getReuseComments() {
//        return reuseComments;
//    }
//
//    public void setReuseComments(List<ReuseComment> reuseComments) {
//        this.reuseComments = reuseComments;
//    }

}
