package kr.co.userinsight.systools;

public enum  EnSysTaskType {
    JAVA(1L, "JAVA"),
    SQL_FILE(2L, "SQL_FILE"),
    SQL(3L, "SQL");

    private final long id;
    private final String code;

    /**
     *
     */
    private EnSysTaskType(final long id, final String code) {
        this.id = id;
        this.code = code;
    }

    /**
     * return id
     */

    public Long getId() {
        return id;
    }


    public String getCode() {
        return code;
    }

    /**
     * return desc
     */

    public String getDesc() {
        return getCode();
    }

    public String getDescEn() {
        return getDesc();
    }
}
