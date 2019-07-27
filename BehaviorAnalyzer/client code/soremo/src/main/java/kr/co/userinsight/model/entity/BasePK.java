package kr.co.userinsight.model.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public abstract class BasePK implements Serializable {

    /** */
    private static final long serialVersionUID = -8975678855560770635L;

    public abstract boolean pkEquals(BasePK pk);
    public abstract int pkHashCode();

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof BasePK && pkEquals((BasePK) o);
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return pkHashCode();
    }
}
