package com.vhns.uploadarquivos.processarArquivo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocalizacaoArmazenamento {

    /**
     * Folder location for storing files
     */
    @Value("${value.from.file}")
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
