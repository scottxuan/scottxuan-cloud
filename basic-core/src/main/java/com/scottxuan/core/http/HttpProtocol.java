package com.scottxuan.core.http;


public enum HttpProtocol {

    HTTP("HTTP"),
    HTTPS("HTTPS"),
    ;

    private String value;

    HttpProtocol(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
