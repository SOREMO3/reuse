package kr.co.userinsight.model.reuse;

import kr.co.userinsight.model.entity.EntityA;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "td_reuse_comment")
public class ReuseComment extends EntityA {

	private static final long serialVersionUID = 12L;
	private Integer reuseId;
	private Double reuseIdVersion;
	private String comment;
    private String userId;
   
    
    @Override
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reuse_comment_id", unique=true, nullable=false)
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	
    @Column(name = "reuse_id")
	public Integer getReuseId() {
		return reuseId;
	}
	public void setReuseId(Integer reuseId) {
		this.reuseId = reuseId;
	}
	
	@Column(name = "reuse_id_version")
    public Double getReuseIdVersion() {
		return reuseIdVersion;
	}


	public void setReuseIdVersion(Double reuseIdVersion) {
		this.reuseIdVersion = reuseIdVersion;
	}
	
	@Column(name = "comment")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	

}
