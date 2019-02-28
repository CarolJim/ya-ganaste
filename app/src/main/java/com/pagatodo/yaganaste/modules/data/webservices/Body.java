package com.pagatodo.yaganaste.modules.data.webservices;

import com.google.gson.annotations.SerializedName;

public class Body
{
    @SerializedName("recordYear")
    private String recordYear;

    @SerializedName("issuingEntityKey")
    private String issuingEntityKey;

    @SerializedName("recordStateNumber")
    private String recordStateNumber;

    @SerializedName("fatherLastName")
    private String fatherLastName;

    @SerializedName("bornStateKey")
    private String bornStateKey;

    @SerializedName("tome")
    private String tome;

    @SerializedName("sex")
    private String sex;

    @SerializedName("book")
    private String book;

    @SerializedName("actNumber")
    private String actNumber;

    @SerializedName("errorCode")
    private String errorCode;

    @SerializedName("bornDate")
    private String bornDate;

    @SerializedName("probatoryDocument")
    private String probatoryDocument;

    @SerializedName("error")
    private String error;

    @SerializedName("recordMunicipalityKey")
    private String recordMunicipalityKey;

    @SerializedName("letterFolio")
    private String letterFolio;

    @SerializedName("foja")
    private String foja;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("motherLastName")
    private String motherLastName;

    @SerializedName("name")
    private String name;

    @SerializedName("crip")
    private String crip;

    @SerializedName("recordForeignNumber")
    private String recordForeignNumber;

    @SerializedName("curp")
    private String curp;

    @SerializedName("status")
    private String status;

    public String getRecordYear ()
    {
        return recordYear;
    }

    public void setRecordYear (String recordYear)
    {
        this.recordYear = recordYear;
    }

    public String getIssuingEntityKey ()
    {
        return issuingEntityKey;
    }

    public void setIssuingEntityKey (String issuingEntityKey)
    {
        this.issuingEntityKey = issuingEntityKey;
    }

    public String getRecordStateNumber ()
    {
        return recordStateNumber;
    }

    public void setRecordStateNumber (String recordStateNumber)
    {
        this.recordStateNumber = recordStateNumber;
    }

    public String getFatherLastName ()
    {
        return fatherLastName;
    }

    public void setFatherLastName (String fatherLastName)
    {
        this.fatherLastName = fatherLastName;
    }

    public String getBornStateKey ()
    {
        return bornStateKey;
    }

    public void setBornStateKey (String bornStateKey)
    {
        this.bornStateKey = bornStateKey;
    }

    public String getTome ()
    {
        return tome;
    }

    public void setTome (String tome)
    {
        this.tome = tome;
    }

    public String getSex ()
    {
        return sex;
    }

    public void setSex (String sex)
    {
        this.sex = sex;
    }

    public String getBook ()
    {
        return book;
    }

    public void setBook (String book)
    {
        this.book = book;
    }

    public String getActNumber ()
    {
        return actNumber;
    }

    public void setActNumber (String actNumber)
    {
        this.actNumber = actNumber;
    }

    public String getErrorCode ()
    {
        return errorCode;
    }

    public void setErrorCode (String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getBornDate ()
    {
        return bornDate;
    }

    public void setBornDate (String bornDate)
    {
        this.bornDate = bornDate;
    }

    public String getProbatoryDocument ()
    {
        return probatoryDocument;
    }

    public void setProbatoryDocument (String probatoryDocument)
    {
        this.probatoryDocument = probatoryDocument;
    }

    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }

    public String getRecordMunicipalityKey ()
    {
        return recordMunicipalityKey;
    }

    public void setRecordMunicipalityKey (String recordMunicipalityKey)
    {
        this.recordMunicipalityKey = recordMunicipalityKey;
    }

    public String getLetterFolio ()
    {
        return letterFolio;
    }

    public void setLetterFolio (String letterFolio)
    {
        this.letterFolio = letterFolio;
    }

    public String getFoja ()
    {
        return foja;
    }

    public void setFoja (String foja)
    {
        this.foja = foja;
    }

    public String getNationality ()
    {
        return nationality;
    }

    public void setNationality (String nationality)
    {
        this.nationality = nationality;
    }

    public String getMotherLastName ()
    {
        return motherLastName;
    }

    public void setMotherLastName (String motherLastName)
    {
        this.motherLastName = motherLastName;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCrip ()
    {
        return crip;
    }

    public void setCrip (String crip)
    {
        this.crip = crip;
    }

    public String getRecordForeignNumber ()
    {
        return recordForeignNumber;
    }

    public void setRecordForeignNumber (String recordForeignNumber)
    {
        this.recordForeignNumber = recordForeignNumber;
    }

    public String getCurp ()
    {
        return curp;
    }

    public void setCurp (String curp)
    {
        this.curp = curp;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "Body [recordYear = "+recordYear+", issuingEntityKey = "+issuingEntityKey+", recordStateNumber = "+recordStateNumber+", fatherLastName = "+fatherLastName+", bornStateKey = "+bornStateKey+", tome = "+tome+", sex = "+sex+", book = "+book+", actNumber = "+actNumber+", errorCode = "+errorCode+", bornDate = "+bornDate+", probatoryDocument = "+probatoryDocument+", error = "+error+", recordMunicipalityKey = "+recordMunicipalityKey+", letterFolio = "+letterFolio+", foja = "+foja+", nationality = "+nationality+", motherLastName = "+motherLastName+", name = "+name+", crip = "+crip+", recordForeignNumber = "+recordForeignNumber+", curp = "+curp+", status = "+status+"]";
    }
}
