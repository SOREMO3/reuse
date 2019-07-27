package kr.co.userinsight.tools;

import kr.co.userinsight.model.entity.ISysRefData;

public enum Weight implements ISysRefData{
    KEYWORD(40L),
    REUSE_COUNT(30L),
    IMPORT_COUNT(10L),
    REUSE_DATE(10L),
    REUSE_ID (10L)
    ;

    private Long  value;

    private Weight(Long value){
        this.value= value;
    }

    public Long getValue (){
        return value;
    }

    @Override
    public String getCode() {
        return "";
    }

    @Override
    public String getDesc() {
        return "";
    }
}
