package com.vocabularyassitant.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CustomizeVocabulary
{
    private String cId;
    private String vContent;
    private String vZh_meaning;
    private String nId;
    private Date lDate;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Date getlDate() {
        return lDate;
    }

    public void setlDate(Date lDate) {
        this.lDate = lDate;
    }

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getvContent() {
        return vContent;
    }

    public void setvContent(String vContent) {
        this.vContent = vContent;
    }

    public String getvZh_meaning() {
        return vZh_meaning;
    }

    public void setvZh_meaning(String vZh_meaning) {
        this.vZh_meaning = vZh_meaning;
    }

    @Override
    public String toString() {
        return "CustomizeVocabulary{" +
                "cId='" + cId + '\'' +
                ", vContent='" + vContent + '\'' +
                ", vZh_meaning='" + vZh_meaning + '\'' +
                ", nId='" + nId + '\'' +
                ", lDate=" + lDate +
                '}';
    }
}
