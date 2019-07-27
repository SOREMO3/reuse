package kr.co.userinsight.model.entity;


public enum EStatusRecord implements ISysRefData {

    ACTIV("active"),
    NEW("new"),
    INPRO("inprogress"),
    INACT("inactive"),
    RECYC("recycled"),
    ARCHI("archived");

    private final String code;

    /**
     *
     */
    private EStatusRecord(final String code) {
        this.code = code;
    }

    /**
     * return code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * return desc
     */
    @Override
    public String getDesc() {
        return code;
    }

}