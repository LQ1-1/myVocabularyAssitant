package com.vocabularyassitant.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EnglishVocabularyNeworReviewCount
{
    private String uId;
    private LocalDate pDate;
    private Integer cNew;
    private Integer cReview;

    public Integer getcNew() {
        return cNew;
    }

    public void setcNew(Integer cNew) {
        this.cNew = cNew;
    }

    public Integer getcReview() {
        return cReview;
    }

    public void setcReview(Integer cReview) {
        this.cReview = cReview;
    }

    public LocalDate getpDate() {
        return pDate;
    }

    public void setpDate(LocalDate pDate) {
        this.pDate = pDate;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Override
    public String toString() {
        return "EnglishVocabularyNeworReviewCount{" +
                "cNew=" + cNew +
                ", uId='" + uId + '\'' +
                ", pDate=" + pDate +
                ", cReview=" + cReview +
                '}';
    }
}
