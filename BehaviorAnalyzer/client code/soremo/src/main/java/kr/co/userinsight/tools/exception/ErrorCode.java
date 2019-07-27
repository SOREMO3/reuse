package kr.co.userinsight.tools.exception;



public enum ErrorCode {

    ERR_NONE(0, "None"),
    ERR_SYS(1, "System"),
    ERR_SYS_TIMEOUT(2, "Timeout"),
    ERR_INCORRECT_PARAMETERS(3, "Incorrect parameters");

    private final long id;
    private final String code;

    private ErrorCode(final long id, final String code) {
        this.id = id;
        this.code = code;
    }


    /**
     * return code
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
        return "";
    }



    public String getDescEn() {
        return "";
    }

}
