package com.vocabularyassitant.controller;

import com.vocabularyassitant.result.Result;
import com.vocabularyassitant.service.CustomizedVocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/CustomizedVocabulary")
public class CustomizedVocabularyController
{
    @Autowired
    private CustomizedVocabularyService customizedVocabularyService;

    @PostMapping("addCustomizedVocabularyNotebook")
    public Result addCustomizedVocabularyNotebook(@RequestParam("uId") String uId, @RequestParam("nName") String nName, @RequestParam("nDescription") String nDescription)
    {
        return Result.success(customizedVocabularyService.addCustomizedVocabularyNoteBook(uId, nName, nDescription));
    }

    @PostMapping("updateCustomizedVocabularyNoteBook")
    public Result updateCustomizedVocabularyNoteBook(@RequestParam("uId") String uId, @RequestParam("nId") String nId, @RequestParam("nName") String nName,  @RequestParam("nDescription") String nDescription, Jwt jwt)
    {
        return Result.success(customizedVocabularyService.updateCustomizedVocabularyNoteBook(uId, nId, nName, nDescription, jwt));
    }

    @PostMapping("deleteCustomizedVocabularyNoteBook")
    public Result deleteCustomizedVocabularyNoteBook(@RequestParam("uId") String uId, @RequestParam("nId") String nId, Jwt jwt)
    {
        return Result.success(customizedVocabularyService.deleteCustomizedVocabularyNoteBook(uId, nId, jwt));
    }

    @GetMapping("getCustomizedVocabularyNotebook")
    public Result getCustomizedVocabularyNotebook(@RequestParam("uId") String uId, @RequestParam("nId") String nId, Jwt jwt)
    {
        return Result.success(customizedVocabularyService.getCustomizedVocabularyNotebook(uId, nId, jwt));
    }

    @GetMapping("getUsersAllCustomizedNotebook")
    public Result getUsersAllCustomizedNotebook(@RequestParam("uId") String uId, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset, Jwt jwt)
    {
        return customizedVocabularyService.getUsersAllCustomizedNotebook(uId, limit, offset, jwt);
    }

    @PostMapping("addCustomizedVocabularyRecord")
    public Result addCustomizedVocabularyRecord(@RequestParam("nId") String nId, @RequestParam("vContent") String vContent, @RequestParam("vZh_meaning") String vZh_meaning, Jwt jwt)
    {
        return Result.success(customizedVocabularyService.addCustomizedVocabularyRecord(nId, vContent, vZh_meaning, jwt));
    }

    @PostMapping("updateCustomizedVocabularyRecord")
    public Result updateCustomizedVocabularyRecord(@RequestParam("cId") String cId, @RequestParam("nId") String nId, @RequestParam("vContent") String vContent, @RequestParam("vZh_meaning") String vZh_meaning, Jwt jwt)
    {
        return Result.success(customizedVocabularyService.updateCustomizedVocabularyRecord(cId, nId, vContent, vZh_meaning, jwt));
    }

    @PostMapping("deleteCustomizedVocabularyRecord")
    public Result deleteCustomizedVocabularyRecord(@RequestParam("cId") String cId, @RequestParam("nId") String nId, Jwt jwt)
    {
        return Result.success(customizedVocabularyService.deleteCustomizedVocabularyRecord( cId, nId, jwt));
    }

    @GetMapping("getAllCustomizedRecord")
    public Result getAllCustomizedRecord(@RequestParam("nId") String nId, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset, Jwt jwt)
    {
        return customizedVocabularyService.getAllCustomizedRecord(nId, limit, offset, jwt);
    }

}
