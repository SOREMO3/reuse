package kr.co.userinsight.model.reuse;

import kr.co.userinsight.model.entity.EntityA;
import kr.co.userinsight.model.keyword.Keyword;

import javax.persistence.*;

@Entity
@Table(name = "td_reuse_keyword")
public class ReuseKeyword extends EntityA{

    private ReuseHistory reuseHistory;
    private Keyword keyword;
   /* private Long headerUsedCount;
    private Long textUsedCount;
    private Long sourceUsedCount;
    private Long imageUsedCount;*/

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reuse_keyword_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reuse_id")
    public ReuseHistory getReuseHistory() {
        return reuseHistory;
    }

    public void setReuseHistory(ReuseHistory reuseHistory) {
        this.reuseHistory = reuseHistory;
    }


   /* @Column(name = "header_count", nullable = true)
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
    }*/

   /* @Column(name = "header_count", nullable = true)
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
    }*/

}
