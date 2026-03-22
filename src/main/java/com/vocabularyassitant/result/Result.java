package com.vocabularyassitant.result;

import lombok.Data;

@Data
public class Result {
    private static final String SUCCESS_CODE = "200";
    private static final String FAILURE_CODE = "400";
    private static final String ERROR_CODE = "-1";

    private String code;
    private Object data;
    private String msg;
    private Integer total;          //部分接口需要知道查询结果有多少条

    public static Result success(){
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        return result;
    }

    public static Result success(Object data){
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        return result;
    }

    public static Result success(Object data, String msg)
    {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static Result success(Object data, Integer total)
    {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        result.setTotal(total);
        return result;
    }

    public static Result success(Object data, String msg, Integer total)
    {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        result.setMsg(msg);
        result.setTotal(total);
        return result;
    }

    public static Result fail(String message)
    {
        Result result = new Result();
        result.setCode(FAILURE_CODE);
        result.setMsg(message);
        return result;
    }

    public static Result error(String msg){
        Result result = new Result();
        result.setCode(ERROR_CODE);
        result.setMsg(msg);
        return result;
    }

    public static Result error(String code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}