package kr.co.userinsight.service.impl;

import kr.co.userinsight.dao.BaseEntityDao;
import kr.co.userinsight.dao.EntityDao;
import kr.co.userinsight.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("entityService")
public class EntityServiceImpl extends BaseEntityServiceImpl implements EntityService {


    @Autowired
    private EntityDao dao;

    public EntityServiceImpl() {
        super();
    }

    @Override
    public BaseEntityDao getDao() {
        return dao;
    }
}
