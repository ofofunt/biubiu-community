package com.example.demo.exception;

public enum CustomizedErrorCode implements ICustomizedExceptionCode {
    QUESTION_NOT_FOUND(2001, "问题不存在,要不换个试试?"),
    TARGET_PARAM__NOT_FOUND(2002, "未选中任何问题或者评论进行回复~"),
    NOT_LOGIN(2003, "当前操作需要登录~"),
    SYS_ERROR(2004, "服务器忙,请稍后再试~"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在~"),
    COMMENT_NOT_FOUND__WRONG(2006, "你回复的评论不存在了~"),
    QUESTION_NOT_FOUND__WRONG(2007, "你回复的问题不存在了~"),
    COMMENT_IS_EMPTY(2008, "输入内容不能为空~");
    private Integer code;
    private String message;

    CustomizedErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
