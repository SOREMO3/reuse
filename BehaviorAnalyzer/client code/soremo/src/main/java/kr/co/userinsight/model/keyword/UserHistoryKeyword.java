package kr.co.userinsight.model.keyword;

import kr.co.userinsight.model.entity.EntityA;
import kr.co.userinsight.model.userhistory.UserHistory;

import javax.persistence.*;

@Entity
@Table(name = "td_userhistory_keyword")
public class UserHistoryKeyword extends EntityA{

    private UserHistory userHistory;
    private Keyword keyword;

    private Long headerUsedCount;
    private Long textUsedCount;
    private Long sourceUsedCount;
    private Long imageUsedCount;


    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userhistory_keyword_id", unique = true, nullable = false)
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
    @JoinColumn(name = "keyword_id")
    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
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
