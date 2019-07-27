package kr.co.userinsight.model.preference;

import kr.co.userinsight.model.entity.ISysRefData;

public enum VisualizationType  implements ISysRefData {

    HEADER("header"),
    IMAGE("image"),
    TEXT("text"),
    CODE("source");

    private final String code;

    /**
     *
     */
    private VisualizationType(final String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return code;
    }
}
