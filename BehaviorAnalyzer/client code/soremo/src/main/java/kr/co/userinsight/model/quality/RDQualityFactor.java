package kr.co.userinsight.model.quality;

import javax.persistence.*;

import kr.co.userinsight.model.entity.EntityA;

@Entity
@Table(name = "td_rd_qualityfactor")
public class RDQualityFactor extends EntityA{
	
	private static final long serialVersionUID = 1L;
	private Integer rdId;
	private Double rdVersion;
	private Integer rdWords;
	private Integer rdComplexity;
	private Integer rdContentsDiversity;
	private Integer rdTemplateCompliance;
	private Integer rdReuseCount;
	
	@Override
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quality_factor_id", unique=true, nullable=false)
	public Long getId(){
		return id;
	}
	
	@Column(name = "rd_version")
	public Double getRdVersion() {
		return rdVersion;
	}


	public void setRdVersion(Double rdVersion) {
		this.rdVersion = rdVersion;
	}


	@Column(name = "rd_id")
	public Integer getRdId() {
		return rdId;
	}



	public void setRdId(Integer rdId) {
		this.rdId = rdId;
	}



	@Column(name = "rd_words")
	public Integer getRdWords() {
		return rdWords;
	}

	public void setRdWords(Integer rdWords) {
		this.rdWords = rdWords;
	}

	@Column(name = "rd_complexity")
	public Integer getRdComplexity() {
		return rdComplexity;
	}

	public void setRdComplexity(Integer rdComplexity) {
		this.rdComplexity = rdComplexity;
	}

	@Column(name = "rd_contents_diversity")
	public Integer getRdContentsDiversity() {
		return rdContentsDiversity;
	}

	public void setRdContentsDiversity(Integer rdContentsDiversity) {
		this.rdContentsDiversity = rdContentsDiversity;
	}

	@Column(name = "rd_template_compliance")
	public Integer getRdTemplateCompliance() {
		return rdTemplateCompliance;
		}

	public void setRdTemplateCompliance(Integer rdTemplateCompliance) {
		this.rdTemplateCompliance = rdTemplateCompliance;
	}

	@Column(name = "rd_reuse_count")
	public Integer getRdReuseCount() {
		return rdReuseCount;
	}

	public void setRdReuseCount(Integer rdReuseCount) {
		this.rdReuseCount = rdReuseCount;
	}

	
	
	
	
}
