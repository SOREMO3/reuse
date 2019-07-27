package kr.co.userinsight.model.userhistory;

import kr.co.userinsight.model.entity.EntityA;

import javax.persistence.*;

@Entity
@Table(name = "td_user_activity")
public class UserActivity extends EntityA{

    private Long userId ;
    private String url ;


    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_activity_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "user_id", nullable = true)
    public long getUserId() {
        return userId;
    }

    @Column(name = "user_activity_url", nullable = true, length = 120)
    public String getUrl() {
        return url;
    }



    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
