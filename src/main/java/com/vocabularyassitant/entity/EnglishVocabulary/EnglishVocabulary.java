package com.vocabularyassitant.entity.EnglishVocabulary;

import lombok.Data;

@Data
public class EnglishVocabulary
{
    private String vId;
    private String vWord;
    private String vZh_meaning;

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    public String getvWord() {
        return vWord;
    }

    public void setvWord(String vWord) {
        this.vWord = vWord;
    }

    public String getvZh_meaning() {
        return vZh_meaning;
    }

    public void setvZh_meaning(String vZh_meaning) {
        this.vZh_meaning = vZh_meaning;
    }

    @Override
    public String toString() {
        return "EnglishVocabulary{" +
                "vId='" + vId + '\'' +
                ", vWord='" + vWord + '\'' +
                ", vZh_meaning='" + vZh_meaning + '\'' +
                '}';
    }
}
