package com.vocabularyassitant.entity.EnglishVocabulary;

import lombok.Data;

import java.util.Date;

@Data
public class EnglishVocabularyLearningRecords
{
    private String vId;
    private String uId;
    private Date lDate;

    public Date getlDate() {
        return lDate;
    }

    public void setlDate(Date lDate) {
        this.lDate = lDate;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    @Override
    public String toString() {
        return "EnglishVocabularyLearningRecords{" +
                "lDate=" + lDate +
                ", vId='" + vId + '\'' +
                ", uId='" + uId + '\'' +
                '}';
    }
}
