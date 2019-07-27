package kr.co.userinsight.model.preference;

import kr.co.userinsight.model.entity.EntityA;

import javax.persistence.*;

@Entity
@Table(name = "td_preference")
public class Preference extends EntityA{


    private Long userId ;
    private VisualizationType visualizationType;
   // private Long rd_id ;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preference_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Column(name = "visualization_type", nullable = true)
    @Enumerated(EnumType.STRING)
    public VisualizationType getVisualizationType() {
        return visualizationType;
    }

    public void setVisualizationType(VisualizationType visualizationType) {
        this.visualizationType = visualizationType;
    }

//    @Column(name = "rd_id", nullable = true)
//    public Long getRd_id() {
//        return rd_id;
//    }
//
//    public void setRd_id(Long rd_id) {
//        this.rd_id = rd_id;
//    }

}
