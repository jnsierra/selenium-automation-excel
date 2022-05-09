package com.fisa.enumeration;

public enum TypeFindByEnum {
    XPATH, ID, DEFAULT;

    public TypeFindByEnum getEnum(String data){
        if("XPATH".equalsIgnoreCase(data)){
            return XPATH;
        } else if ("ID".equalsIgnoreCase(data)) {
            return ID;
        }
        return DEFAULT;
    }
}
