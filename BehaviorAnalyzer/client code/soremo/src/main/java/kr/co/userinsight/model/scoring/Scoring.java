package kr.co.userinsight.model.scoring;

import kr.co.userinsight.model.entity.EntityA;
import kr.co.userinsight.tools.Weight;

import javax.persistence.*;

/*@Entity
@Table(name = "td_scoring")*/
public class Scoring {

    private Long headerUsedCount;
    private Long textUsedCount;
    private Long sourceUsedCount;
    private Long imageUsedCount;

 /*   @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id", unique = true, nullable = false)*/
//    public Long getId() {
//        return id;
//    }

//    @Column(name = "header_count", nullable = true)
    public Long getHeaderUsedCount() {
        return headerUsedCount;
    }

    public void setHeaderUsedCount(Long headerUsedCount) {
        this.headerUsedCount = headerUsedCount;
    }

//    @Column(name = "text_count", nullable = true)
    public Long getTextUsedCount() {
        return textUsedCount;
    }

    public void setTextUsedCount(Long textUsedCount) {
        this.textUsedCount = textUsedCount;
    }

//    @Column(name = "source_count", nullable = true)
    public Long getSourceUsedCount() {
        return sourceUsedCount;
    }

    public void setSourceUsedCount(Long sourceUsedCount) {
        this.sourceUsedCount = sourceUsedCount;
    }


//    @Column(name = "image_count", nullable = true)
    public Long getImageUsedCount() {
        return imageUsedCount;
    }

    public void setImageUsedCount(Long imageUsedCount) {
        this.imageUsedCount = imageUsedCount;
    }


}
