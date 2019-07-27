package kr.co.userinsight.service.keyword.impl;

import kr.co.userinsight.model.keyword.Keyword;
import kr.co.userinsight.service.EntityService;
import kr.co.userinsight.service.impl.EntityServiceImpl;
import kr.co.userinsight.service.keyword.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KeywordServiceImpl extends EntityServiceImpl implements KeywordService{



    @Override
    public Keyword saveOrUpdateKeyword(Keyword keyword) {

      saveOrUpdate(keyword);

        return keyword;
    }

    @Override
    public Keyword isExistingKeyword(String basedKeyword) {
        return null;
    }
}
