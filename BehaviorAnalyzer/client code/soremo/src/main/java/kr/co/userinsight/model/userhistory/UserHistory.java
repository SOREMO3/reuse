package kr.co.userinsight.model.userhistory;

import kr.co.userinsight.model.entity.EntityA;
import kr.co.userinsight.model.keyword.UserHistoryKeyword;
import kr.co.userinsight.model.reuse.ReuseUserHistory;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "td_user_history")
public class UserHistory extends EntityA{

    private Long userId ;

    private List<ReuseUserHistory> reuseUserHistories;
    private List<UserHistoryKeyword> userHistoryKeywords;


    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "histr_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "user_id", nullable = true)
    public long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @OneToMany(mappedBy="userHistory", fetch = FetchType.LAZY)
    public List<ReuseUserHistory> getReuseUserHistories() {
        return reuseUserHistories;
    }

    public void setReuseUserHistories(List<ReuseUserHistory> reuseUserHistories) {
        this.reuseUserHistories = reuseUserHistories;
    }



    @OneToMany(mappedBy="userHistory", fetch = FetchType.LAZY)
    public List<UserHistoryKeyword> getUserHistoryKeywords() {
        return userHistoryKeywords;
    }

    public void setUserHistoryKeywords(List<UserHistoryKeyword> userHistoryKeywords) {
        this.userHistoryKeywords = userHistoryKeywords;
    }
}
