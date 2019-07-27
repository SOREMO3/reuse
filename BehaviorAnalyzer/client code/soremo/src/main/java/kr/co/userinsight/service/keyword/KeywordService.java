package kr.co.userinsight.service.keyword;

import kr.co.userinsight.model.keyword.Keyword;
import kr.co.userinsight.service.EntityService;

public interface KeywordService extends EntityService {

    Keyword saveOrUpdateKeyword(Keyword keyword);
    Keyword isExistingKeyword(String basedKeyword);
}
