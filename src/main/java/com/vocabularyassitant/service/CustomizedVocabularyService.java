package com.vocabularyassitant.service;

import com.vocabularyassitant.entity.CustomizedVocabulary;
import com.vocabularyassitant.entity.CustomizedVocabularyNotebook;
import com.vocabularyassitant.mapper.CustomizedVocabularyMapper;
import com.vocabularyassitant.mapper.CustomizedVocabularyNotebookMapper;
import com.vocabularyassitant.result.Result;
import org.apache.ibatis.annotations.Param;
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

    public   Integer deleteCustomizedVocabularyNoteBook(String uId, String nId, Jwt jwt)
    {
        String tokenUid=jwt.getSubject();
        if(!tokenUid.equals(uId))
        {
            return null;
        }
        else
        {
            return customizedVocabularyNotebookMapper.deleteCustomizedVocabularyNoteBook(nId);
        }
    }

    public CustomizedVocabularyNotebook getCustomizedVocabularyNotebook(String uId, String nId, Jwt jwt)
    {
        String tokenUid=jwt.getSubject();
        if(!tokenUid.equals(uId))
        {
            return null;
        }
        else
        {
            return customizedVocabularyNotebookMapper.getCustomizedVocabularyNotebook(nId);
        }
    }

    public Result getUsersAllCustomizedNotebook(String uId, Integer limit, Integer offset, Jwt jwt)
    {
        String tokenUid=jwt.getSubject();
        if(!tokenUid.equals(uId))
        {
            return null;
        }
        else
        {
            return Result.success(customizedVocabularyNotebookMapper.getUsersAllCustomizedNotebook(uId,limit,offset), customizedVocabularyNotebookMapper.getUsersAllCustomizedNotebookCount(uId));
        }
    }

    public Integer addCustomizedVocabularyRecord(String nId, String vContent, String vZh_meaning, Jwt jwt)
    {
        String tokenUid=jwt.getSubject();
        CustomizedVocabularyNotebook notebook=customizedVocabularyNotebookMapper.getCustomizedVocabularyNotebook(nId);
        if(!tokenUid.equals(nId))
        {
            return null;
        }
        else
        {
            CustomizedVocabulary New_Record=new CustomizedVocabulary();
            New_Record.setnId(nId);
            New_Record.setvContent(vContent);
            New_Record.setvZh_meaning(vZh_meaning);
            return customizedVocabularyMapper.addCustomizedVocabularyRecord(New_Record);
        }
    }

    public Integer updateCustomizedVocabularyRecord(String cId, String nId, String vContent, String vZh_meaning, Jwt jwt)
    {
        String tokenUid=jwt.getSubject();
        CustomizedVocabularyNotebook notebook=customizedVocabularyNotebookMapper.getCustomizedVocabularyNotebook(nId);
        if(!tokenUid.equals(nId))
        {
            return null;
        }
        else
        {
            CustomizedVocabulary Updated_Record=new CustomizedVocabulary();
            Updated_Record.setnId(nId);
            Updated_Record.setvContent(vContent);
            Updated_Record.setvZh_meaning(vZh_meaning);
            Updated_Record.setcId(cId);
            return customizedVocabularyMapper.updateCustomizedVocabularyRecord(Updated_Record);
        }
    }

    public Integer deleteCustomizedVocabularyRecord(String cId, String nId, Jwt jwt)
    {
        String tokenUid=jwt.getSubject();
        CustomizedVocabularyNotebook notebook=customizedVocabularyNotebookMapper.getCustomizedVocabularyNotebook(nId);
        if(!tokenUid.equals(nId))
        {
            return null;
        }
        else
        {
            return customizedVocabularyMapper.deleteCustomizedVocabularyRecord(cId,nId);
        }
    }

    public Result getAllCustomizedRecord(String nId, Integer limit, Integer offset, Jwt jwt)
    {
        String tokenUid=jwt.getSubject();
        CustomizedVocabularyNotebook notebook=customizedVocabularyNotebookMapper.getCustomizedVocabularyNotebook(nId);
        if(!tokenUid.equals(nId))
        {
            return null;
        }
        else
        {
            return Result.success(customizedVocabularyMapper.getAllCustomizedRecord(nId, limit, offset), customizedVocabularyMapper.getAllCustomizedRecordCount(nId));
        }
    }
}
