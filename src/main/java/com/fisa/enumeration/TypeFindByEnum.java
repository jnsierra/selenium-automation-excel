package com.fisa.enumeration;

public enum TypeFindByEnum {
    XPATH, ID, CLASSNAME, DEFAULT;

    public TypeFindByEnum getEnum(String data){
        if("XPATH".equalsIgnoreCase(data)){
            return XPATH;
        } else if ("ID".equalsIgnoreCase(data)) {
            return ID;
        } else if ("classname".equalsIgnoreCase(data))  {
            return CLASSNAME;
        }
        return DEFAULT;
    }
}
