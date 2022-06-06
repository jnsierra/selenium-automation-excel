package com.fisa.service;

public interface SaveInformation {

    Boolean guardaInformacion(String valor, String key);

    String getValue(String key);

}
