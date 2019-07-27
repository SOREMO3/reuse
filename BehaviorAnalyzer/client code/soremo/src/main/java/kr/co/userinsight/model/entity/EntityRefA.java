package kr.co.userinsight.model.entity;

import kr.co.userinsight.tools.exception.EntityNotValidParameterException;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class EntityRefA extends EntityA implements MEntityRefA, EntityRef{
    /** */
    private static final long serialVersionUID = -8839423010654857579L;

    public static final long DEFAULT_ID = 1L;
    public final static String DESCLOCALE = "descLocale";

    protected String code;
    protected String desc;
    protected String descEn;
    protected String descFr;
    protected Integer sortIndex;


    /**
     *
     * @param clazz
     * @param code
     * @return
     * @throws Exception
     */
    public static EntityRefA newInstance(final Class<? extends EntityRefA> clazz) {
        try {
            EntityRefA entity = clazz.newInstance();
            entity.setStatusRecord(EStatusRecord.ACTIV);
            //entity.fillSysBlock(SecurityHelper.ADMIN_LOGIN);
            return entity;
        } catch (Exception e) {
            throw new IllegalStateException("Error while instantiating EntityRefA [" + clazz.getCanonicalName() + "]", e);
        }
    }

    /**
     * @return the code
     */
    @Transient

    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     *
     * @return
     */
    @Transient

    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(final String desc) {
        this.desc = desc;
    }

    /**
     *
     */
    @Transient
    public String getDescEn() {
        return descEn;
    }

    /**
     * @param desc the descEn to set
     */
    public void setDescEn(final String descEn) {
        this.descEn = descEn;
    }

    @Transient
    public String getDescFr() {
        return descFr;
    }

    /**
     * @param desc the descFr to set
     */
    public void setDescFr(final String descFr) {
        this.descFr = descFr;
    }

    /**
     * @return SortIndex
     */
    @Column(name = "sort_index", nullable = true)
    public Integer getSortIndex() {
        return sortIndex;
    }

    /**
     * @param sortIndex
     */
    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }



    @Override
    public void checkForCreation() {
        super.checkForCreation();

        //
        if (StringUtils.isEmpty(getCode())) {
            throw new EntityNotValidParameterException(" invalid code!");
        }

    }

    @Override
    public void checkForUpdate() {
        super.checkForUpdate();

        if (StringUtils.isEmpty(getCode())) {
            throw new EntityNotValidParameterException("mandantory  code !");
        }

    }

    @Override
    public void checkForDeletion() {
        super.checkForDeletion();
    }

}
