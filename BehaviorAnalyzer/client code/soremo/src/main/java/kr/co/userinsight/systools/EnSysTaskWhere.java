package kr.co.userinsight.systools;

public enum  EnSysTaskWhere {

    PLACE_01(1L, "BEFORE_SPRING_INIT_CONTEXT"),
    PLACE_10(2L, "AFTER_SPRING_INIT_CONTEXT"),
    PLACE_20(3L, "AFTER_START_UP");

    private final long id;
    private final String code;

    /**
     *
     */
    private EnSysTaskWhere(final long id, final String code) {
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
