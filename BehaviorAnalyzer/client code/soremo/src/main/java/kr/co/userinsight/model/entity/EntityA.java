package kr.co.userinsight.model.entity;

import kr.co.userinsight.tools.exception.EntityNotValidParameterException;
import kr.co.userinsight.tools.reflection.MyClassUtils;
import kr.co.userinsight.tools.spring.SpringUtils;
import kr.co.userinsight.tools.vo.DateUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@MappedSuperclass
public abstract class EntityA extends AbstractEntity implements MEntityA, CrudActionAware, IHasAssignedIdI, SysInfoAuditable , EntityStatusRecordAware{
    /** */
    private static final long serialVersionUID = -8669896493574539381L;

    public static final String NOT_AUTHENTICATED_USER = "Anomymous";

    public static final String ID_FIELD = "id";

    protected Long id = null;

    private EStatusRecord statusRecord;
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;

    private CrudAction crudAction;

    // It is used for example Employee which has sub-entities non auto-cascade
    // . Actor, Address, BankAccount, ActorDiploma, ...
    // . and the user can update only the sub-entities he needs
    private List<EntityA> subEntitiesToCascade = new ArrayList<EntityA>();
    private List<EntityA> subMappedEntitiesToCascade = new ArrayList<EntityA>();
    private List<List<EntityA>> subListEntitiesToCascade = new ArrayList<List<EntityA>>();

    /**
     * @return the subEntitiesToCascade
     */
    @Transient
    public List<EntityA> getSubEntitiesToCascadeAction() {
        return subEntitiesToCascade;
    }

    /**
     * @param subEntitiesToCascade the subEntitiesToCascade to set
     */
    public void setSubEntitiesToCascade(List<EntityA> subEntitiesToCascade) {
        this.subEntitiesToCascade = subEntitiesToCascade;
    }

    /**
     *
     * @param subEntityToCascade
     */
    public void addCascadeAtCreation(EntityA subEntityToCascade) {
        addSubEntityToCascade(subEntityToCascade, CrudAction.CREATE);
    }

    /**
     *
     * @param subEntityToCascade the subEntityToCascade to set
     * @param crudAction the crudAction to set
     */
    public void addSubEntityToCascade(EntityA subEntityToCascade, CrudAction crudAction) {
        //        if (!subEntitiesToCascade.contains(subEntityToCascade)) {
        subEntityToCascade.setCrudAction(crudAction);
        subEntitiesToCascade.add(subEntityToCascade);
        //        }
    }

    /**
     *
     * @return subMappedEntitiesToCascade
     */
    @Transient
    public List<EntityA> getSubMappedEntitiesToCascade() {
        return subMappedEntitiesToCascade;
    }

    /**
     *
     * @param subMappedEntitiesToCascade
     */
    public void setSubMappedEntitiesToCascade(List<EntityA> subMappedEntitiesToCascade) {
        this.subMappedEntitiesToCascade = subMappedEntitiesToCascade;
    }

    /**
     *
     * @param subMappedEntityToCascade
     * @param crudAction
     */
    public void addSubMappedEntityToCascade(EntityA subMappedEntityToCascade, CrudAction crudAction) {
        //        if (!subMappedEntitiesToCascade.contains(subMappedEntityToCascade)) {
        subMappedEntityToCascade.setCrudAction(crudAction);
        subMappedEntitiesToCascade.add(subMappedEntityToCascade);
        //        }
    }

    /**
     *
     * @return the subListEntitiesToCascade
     */
    @Transient
    public List<List<EntityA>> getSubListEntitiesToCascade() {
        return subListEntitiesToCascade;
    }

    /**
     *
     * @param subListEntitiesToCascade the subListEntitiesToCascade to set
     */
    public void setSubListEntitiesToCascade(List<List<EntityA>> subListEntitiesToCascade) {
        this.subListEntitiesToCascade = subListEntitiesToCascade;
    }

    /**
     *
     * @param subListEntityToCascade the subListEntityToCascade to set
     */
    public <T extends EntityA> void addSubListEntityToCascade(List<T> subListEntityToCascade) {
        subListEntitiesToCascade.add((List<EntityA>) subListEntityToCascade);
    }

    /**
     * @return the id
     */
    @Transient
    public abstract Long getId();


    @Override
    public void setId(final Long id) {
        this.id = id;
    }



    @Transient
    public boolean hasValidId() {
        return getId() != null && getId() > 0;
    }


    @Column(name = "sta_rec_id", nullable = true)
    @Enumerated(EnumType.STRING)
    @Override
    public EStatusRecord getStatusRecord() {
        return statusRecord;
    }


    public void setStatusRecord(EStatusRecord statusRecord) {
        this.statusRecord = statusRecord;
    }

    /**
     *
     * @param isActive
     */
    public void setStatusRecord(Boolean isActive) {
        this.statusRecord = BooleanUtils.isNotFalse(isActive) ? EStatusRecord.ACTIV : EStatusRecord.INACT;
    }


    /**_______________________________________________________________________________________
     *
     * START BLOCK [LAST USER/DATE]
     * _______________________________________________________________________________________
     */
    /**
     * @return the createUser
     */
    @Column(name = "usr_cre", nullable = false, length = 30)
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser the createUser to set
     */
    public void setCreateUser(final String createUser) {
        this.createUser = createUser;
    }

    /**
     * @return the createDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_cre", nullable = false, updatable=false)
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the updateUser
     */
    @Column(name = "usr_upd", nullable = false, length = 30)
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser the updateUser to set
     */
    public void setUpdateUser(final String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * @return the updateDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_upd", nullable = false)
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(final Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * PrePersist/PreUpdate: these are JPA callbacks that won't work with Session API
     * http://technovergence-en.blogspot.com/2011/05/why-i-prefer-hibernate-session-api-to.html
     * http://stackoverflow.com/questions/4133287/preupdate-and-prepersist-in-hibernate-jpa-using-session
     */
    //@PrePersist
    public void onCreateSysBlock() {
        updateDate = createDate = new Date();
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            createUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        } else {
            createUser = NOT_AUTHENTICATED_USER;
        }
    }

    /**
     * PrePersist/PreUpdate: these are JPA callbacks that won't work with Session API
     * http://technovergence-en.blogspot.com/2011/05/why-i-prefer-hibernate-session-api-to.html
     * http://stackoverflow.com/questions/4133287/preupdate-and-prepersist-in-hibernate-jpa-using-session
     */
    //@PreUpdate
    public void onUpdateSysBlock() {
        updateDate = new Date();
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            updateUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        } else {
            updateUser = NOT_AUTHENTICATED_USER;
        }
    }


    /**
     *
     * @param username
     */
    @Transient
    public void fillSysBlock(final String username) {
        if (StringUtils.isEmpty(createUser)) {
            createUser = username;
            createDate = DateUtils.today();
            updateUser = createUser;
            updateDate = createDate;
        } else {
            updateUser = username;
            updateDate = DateUtils.today();;
        }
    }

    @Transient
    public void fillSysBlock() {
        String username = null;
        if (SpringUtils.isAuthenticated()) {
            username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        } else {
            username = NOT_AUTHENTICATED_USER;
        }
        fillSysBlock(username);
    }

    /**_______________________________________________________________________________________
     *
     * END BLOCK [LAST USER/DATE]
     * _______________________________________________________________________________________
     */

    @Override
    @Transient
    public CrudAction getCrudAction() {
        return crudAction;
    }

    @Override
    public void setCrudAction(CrudAction crudAction) {
        this.crudAction = crudAction;
    }

    /**
     *
     * @return an error message if error
     */
    public void checkForCreation() {
        if (getId() != null) {
            throw new EntityNotValidParameterException("entity not validate parameter! ");
        }
    }

    /**
     *
     * @return an error message if error
     */
    public void checkForUpdate() {
        if (getId() == null || getId() == 0) {
            throw new EntityNotValidParameterException("entity not validate parameter! ");
        }
    }

    /**
     *
     * @return an error message if error
     */
    public void checkForDeletion() {
        if (getId() == null || getId() == 0) {
            throw new EntityNotValidParameterException("entity not validate parameter! ");
        }
    }

    /**
     *
     */
    public void checkBeforeThrowIntoRecycledBin() {
        // NONE by default
    }

    /**
     *
     * @param fieldName
     * @return
     */
    public Object getValueFromField(String fieldName) {
        return MyClassUtils.getValue(this, fieldName);
    }
}

