package kr.co.userinsight.model;

import java.util.ArrayList;
import java.util.LinkedList;

import kr.co.userinsight.model.reuse.ReuseComment;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 源��닚寃�
 *
 */
@Data

@ToString
public class RDStructureModel {

	public static final int name = 0;

	private String id;
	private String title;
	private String version;
	private String creator;
	private String createdDate;
	private String modifier;
	private String modifiedDate;
	private String application;
	private String contents;
	
	private int depth;
	private LinkedList<RDStructureModel> nestedRD;
	private String rdPointer;
	
	public static String RD_TEXT = "text";

	private RDStructureModel parent;
	private int level;

	private Integer rdWords;
	private Integer rdComplexity;
	private Integer rdContentsDiversity;
	private Integer rdTemplateCompliance;
	private Integer rdReuseCount;
	
	private ArrayList<ReuseComment> rdCommentList;
	private String rdComment;
	
	public ArrayList<ReuseComment> getRdCommentList(){return rdCommentList;}
	public void setRdCommentList(ArrayList<ReuseComment> rdCommentList){
		this.rdCommentList = rdCommentList; 
	}
	
	
	public void setRdComment(String rdComment) {
		this.rdComment = rdComment;
	}

	LinkedList<RDStructureModel> tmpRD = new LinkedList<RDStructureModel>();

	private Annotation tmpAnnotation;
	// ArrayList <RDStructure> tmpRD;

	public RDStructureModel() {
		tmpAnnotation = new Annotation();
		nestedRD = new LinkedList<RDStructureModel>();
	}

	
	public Integer getRdWords() {
		return rdWords;
	}


	public void setRdWords(Integer rdWords) {
		this.rdWords = rdWords;
	}


	public Integer getRdComplexity() {
		return rdComplexity;
	}


	public void setRdComplexity(Integer rdComplexity) {
		this.rdComplexity = rdComplexity;
	}


	public Integer getRdContentsDiversity() {
		return rdContentsDiversity;
	}


	public void setRdContentsDiversity(Integer rdContentsDiversity) {
		this.rdContentsDiversity = rdContentsDiversity;
	}


	public Integer getRdTemplateCompliance() {
		return rdTemplateCompliance;
	}


	public void setRdTemplateCompliance(Integer rdTemplateCompliance) {
		this.rdTemplateCompliance = rdTemplateCompliance;
	}


	public Integer getRdReuseCount() {
		return rdReuseCount;
	}


	public void setRdReuseCount(Integer rdReuseCount) {
		this.rdReuseCount = rdReuseCount;
	}


	/**
	 * @param rdlist
	 *            the rdlist to set
	 */
	public RDStructureModel(LinkedList<RDStructureModel> RDStructure, String id, String title, String version,
			String creator, String createdDate, String modifier, String modifiedDate, String application, int depth) {
		tmpRD = RDStructure;
		new RDStructureModel(id, title, version, creator, createdDate, modifier, modifiedDate, application, depth);
	}

	public RDStructureModel(String id, String title, String version, String creator, String createdDate,
			String modifier, String modifiedDate, String application, int depth) {
		this.id = id;
		this.title = title;
		this.version = version;
		this.creator = creator;
		this.createdDate = createdDate;
		this.modifier = modifier;
		this.modifiedDate = modifiedDate;
		this.application = application;
		this.depth = depth;

		nestedRD = new LinkedList<RDStructureModel>();
		tmpAnnotation = new Annotation();
		// rdlist = new ArrayList<RDStructureModel>();
	}

	public String getRdPointer() {
		return rdPointer;
	}

	public void setRdPointer(String rdPointer) {
		this.rdPointer = rdPointer;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public LinkedList<RDStructureModel> getNestedRD() {
		return nestedRD;
	}

	public void setNestedRD(LinkedList<RDStructureModel> nestedRD) {
		this.nestedRD = nestedRD;
	}

	public Annotation getAnnotation() {
		return this.tmpAnnotation;
	}

	public void setAnnotation(Annotation tmpAnnotation) {
		this.tmpAnnotation = tmpAnnotation;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setParent(RDStructureModel rdPointer) {
		this.parent = rdPointer;
	}

	public RDStructureModel getParent() {
		return this.parent;
	}

	// public ArrayList<RDStructureModel> getRdList() {
	// return this.rdlist;
	// }

	public void setDepth(String string) {
		this.depth = Integer.valueOf(string);
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	

}
