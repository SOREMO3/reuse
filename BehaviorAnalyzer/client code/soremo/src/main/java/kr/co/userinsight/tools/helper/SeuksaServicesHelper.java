package kr.co.userinsight.tools.helper;

import kr.co.userinsight.service.EntityService;
import kr.co.userinsight.tools.spring.SpringUtils;
import kr.co.userinsight.tools.user.UserSessionManager;

public interface SeuksaServicesHelper {

    UserSessionManager SESSION_MNG = SpringUtils.getBean("sessionManager");
    EntityService ENTITY_SRV = SpringUtils.getBean(EntityService.class);

}
