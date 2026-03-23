package com.vocabularyassitant.mapper;

import com.vocabularyassitant.entity.CustomizedVocabularyNotebook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CustomizedVocabularyNotebookMapper
{
    public Integer addCustomizedVocabularyNoteBook(CustomizedVocabularyNotebook customizedVocabularyNotebook);
    public Integer updateCustomizedVocabularyNoteBook(CustomizedVocabularyNotebook customizedVocabularyNotebook);   //need to check the validity of the request
    public Integer deleteCustomizedVocabularyNoteBook(@Param("nId") String nId);    //need to check the validity of the request as well
    public CustomizedVocabularyNotebook getCustomizedVocabularyNotebook(@Param("nId") String nId);  //get information of a specific notebook
}
