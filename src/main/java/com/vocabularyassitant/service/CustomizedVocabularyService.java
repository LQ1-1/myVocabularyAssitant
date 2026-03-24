package com.vocabularyassitant.service;

import com.vocabularyassitant.entity.CustomizedVocabularyNotebook;
import com.vocabularyassitant.mapper.CustomizedVocabularyMapper;
import com.vocabularyassitant.mapper.CustomizedVocabularyNotebookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class CustomizedVocabularyService
{
    @Autowired
    private CustomizedVocabularyNotebookMapper customizedVocabularyNotebookMapper;
    @Autowired
    private CustomizedVocabularyMapper customizedVocabularyMapper;

    //add new Vocabulary Notebook
    public Integer addCustomizedVocabularyNoteBook(String uId, String nName, String nDescription)
    {
        CustomizedVocabularyNotebook Notebook=new CustomizedVocabularyNotebook();
        Notebook.setuId(uId);
        Notebook.setnName(nName);
        Notebook.setnDescription(nDescription);
        return customizedVocabularyNotebookMapper.addCustomizedVocabularyNoteBook(Notebook);
    }

    public Integer updateCustomizedVocabularyNoteBook(String nId ,String uId, String nName, String nDescription, Jwt jwt)
    {
        String tokenUid=jwt.getSubject();
        if(!tokenUid.equals(uId))
        {
            return null;
        }
        else
        {
            CustomizedVocabularyNotebook Notebook=new CustomizedVocabularyNotebook();
            Notebook.setuId(uId);
            Notebook.setuId(uId);
            Notebook.setnName(nName);
            Notebook.setnDescription(nDescription);
            return customizedVocabularyNotebookMapper.updateCustomizedVocabularyNoteBook(Notebook);
        }
    }
}
