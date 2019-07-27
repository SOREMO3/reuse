package kr.co.userinsight.model.reuse;

import kr.co.userinsight.model.entity.EntityA;
import kr.co.userinsight.model.userhistory.UserHistory;

import javax.persistence.*;

@Entity
@Table(name = "td_reuse_user_history")
public class ReuseUserHistory extends EntityA{

    private UserHistory userHistory;
    private ReuseHistory reuseHistory;

    private Long headerUsedCount;
    private Long textUsedCount;
    private Long sourceUsedCount;
    private Long imageUsedCount;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reuse_user_history_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "histr_id")
    public UserHistory getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(UserHistory userHistory) {
        this.userHistory = userHistory;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reuse_id")
    public ReuseHistory getReuseHistory() {
        return reuseHistory;
    }

    public void setReuseHistory(ReuseHistory reuseHistory) {
        this.reuseHistory = reuseHistory;
    }

    @Column(name = "header_count", nullable = true)
    public Long getHeaderUsedCount() {
        return headerUsedCount;
    }

    public void setHeaderUsedCount(Long headerUsedCount) {
        this.headerUsedCount = headerUsedCount;
    }

    @Column(name = "text_count", nullable = true)
    public Long getTextUsedCount() {
        return textUsedCount;
    }

    public void setTextUsedCount(Long textUsedCount) {
        this.textUsedCount = textUsedCount;
    }

    @Column(name = "source_count", nullable = true)
    public Long getSourceUsedCount() {
        return sourceUsedCount;
    }

    public void setSourceUsedCount(Long sourceUsedCount) {
        this.sourceUsedCount = sourceUsedCount;
    }


    @Column(name = "image_count", nullable = true)
    public Long getImageUsedCount() {
        return imageUsedCount;
    }

    public void setImageUsedCount(Long imageUsedCount) {
        this.imageUsedCount = imageUsedCount;
    }
}
