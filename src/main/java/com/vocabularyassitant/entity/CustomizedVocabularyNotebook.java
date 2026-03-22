package com.vocabularyassitant.entity;

import lombok.Data;

@Data
public class CustomizedVocabularyNotebook
{
    private String nId;
    private String uId;
    private String nName;
    private String nDescription;

    public String getnDescription() {
        return nDescription;
    }

    public void setnDescription(String nDescription) {
        this.nDescription = nDescription;
    }

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Override
    public String toString() {
        return "CustomizedVocabularyNotebook{" +
                "nDescription='" + nDescription + '\'' +
                ", nId='" + nId + '\'' +
                ", uId='" + uId + '\'' +
                ", nName='" + nName + '\'' +
                '}';
    }
}
