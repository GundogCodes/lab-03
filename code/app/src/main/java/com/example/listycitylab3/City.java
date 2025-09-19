package com.example.listycitylab3;

import java.io.Serializable;

public class City implements Serializable {
    private String name;
    private String province;

    public City(String cityName, String provinceName) {
        this.name = cityName;
        this.province = provinceName;
    }
    public String getName() {
        return name;
    }
    public String getProvince() {
        return province;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setProvince(String newProvince){
        this.province = newProvince;
    }
}