package kr.co.userinsight.service.reuse;

import java.util.List;

import kr.co.userinsight.model.reuse.ReuseComment;
import kr.co.userinsight.service.EntityService;

public interface ReuseService  extends EntityService{

	public List<ReuseComment> getReuseCommentList(String rdId, String rdVersion);
	public ReuseComment saveOrUpdateReuseComment(ReuseComment reuseComment);
	public void setReuseComment(String rdId, String rdVersion, String comment, String userId);
}
