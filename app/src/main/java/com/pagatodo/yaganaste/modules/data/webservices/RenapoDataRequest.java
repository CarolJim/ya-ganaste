package com.pagatodo.yaganaste.modules.data.webservices;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//name=aurelio&
// fatherLastName=cortés&
// motherLastName=mendoza&
// sex=H&
// bornState=OC&
// bornDate=11/19/1944
public class RenapoDataRequest implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("fatherLastName")
    private String fatherLastName;

    @SerializedName("motherLastName")
    private String motherLastName;

    @SerializedName("sex")
    private String sex;

    @SerializedName("bornState")
    private String bornState;

    @SerializedName("bornDate")
    private String bornDate;

    public RenapoDataRequest(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherLastName() {
        return fatherLastName;
    }

    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }

    public String getMotherLastName() {
        return motherLastName;
    }

    public void setMotherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBornState() {
        return bornState;
    }

    public void setBornState(String bornState) {
        this.bornState = bornState;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }
}
