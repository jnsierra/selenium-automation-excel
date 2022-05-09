package com.fisa.enumeration;

public enum TypeAdditionalWait {

    CLICKABLE, DEFAULT;

    public TypeAdditionalWait getEnum(String data){
        if("Clickable".equalsIgnoreCase(data)){
            return CLICKABLE;
        }
        return DEFAULT;
    }
}
