package com.vocabularyassitant.mapper;

import com.vocabularyassitant.entity.CustomizedVocabulary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomizedVocabularyMapper
{
    public Integer addCustomizedVocabularyRecord(CustomizedVocabulary customizedVocabulary);
    public Integer updateCustomizedVocabularyRecord(CustomizedVocabulary customizedVocabulary);
    public Integer deleteCustomizedVocabularyRecord(@Param("cId") String cId, @Param("nId") String nId);
    public CustomizedVocabulary getHighPriorityRecordForReview(@Param("nId") String nId);

    public List<CustomizedVocabulary> getAllCustomizedRecord(@Param("nId") String nId, @Param("limit") Integer limit, @Param("offset") Integer offset);
    public Integer getAllCustomizedRecordCount(@Param("nId") String nId);
}
