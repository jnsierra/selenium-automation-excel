package com.fisa.service.impl;

import com.fisa.service.SaveInformation;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SaveInformationImpl implements SaveInformation {

    private Map<String, String> datosAlmacenados;

    public SaveInformationImpl() {
        datosAlmacenados = new HashMap<>();
    }

    @Override
    public Boolean guardaInformacion(String valor, String key) {
        this.datosAlmacenados.put(key, valor);
        return Boolean.TRUE;
    }

    @Override
    public String getValue(String key) {
        return this.datosAlmacenados.get(key);
    }
}