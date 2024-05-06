package com.hq.myfirtsapi.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReaderProperties {

    private Properties properties;
    public ReaderProperties(String path){
        try{
            Properties properties = new Properties();
            properties.load(new FileInputStream(path));
            this.properties = properties;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getSECRET_KEY(){
        return properties.getProperty("SECRET_KEY");
    }

    public void setSECRET_KEY(String key){
        properties.setProperty("SECRET_KEY", key);
    }
}
