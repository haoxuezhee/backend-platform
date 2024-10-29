package com.hxt.project.common;

/**
 * ClassName: RespCode
 * Package: com.hxt.comment
 * Description:
 *
 * @Author hxt
 * @Create 2024/7/24 18:30
 * @Version 1.0
 */
public interface RespCode {

    int LOGIN_SUCCESS_CODE = 20000;
    int TOKEN_SUCCESS_CODE = 20010;
    int LOGIN_FAIL_CODE = 20001;
    int TOKEN_FAIL_CODE = 20011;

    int QUERY_FAIL_CODE = 20021;
    int QUERY_SUCCESS_CODE = 20020;

    int UPDATE_FAIL_CODE = 20031;

    int UPDATE_SUCCESS_CODE = 20030;

    int SAVE_FAIL_CODE = 20041;

    int SAVE_SUCCESS_CODE = 20040;

    int DELETE_FAIL_CODE = 20051;

    int DELETE_SUCCESS_CODE = 20050;

    int RESETPWD_FAIL_CODE = 20061;

    int RESETPWD_SUCCESS_CODE = 20060;

    int SYSTEM_ERROR=50000;

    int OUTBOUND_FAIL_CODE = 20071;

    int OUTBOUND_SUCCESS_CODE = 20070;


    int INBOUND_FAIL_CODE = 20081;

    int INBOUND_SUCCESS_CODE = 20080;

    int UPLOAD_FAIL_CODE = 20091;

    int UPLOAD_SUCCESS_CODE = 20090;

}