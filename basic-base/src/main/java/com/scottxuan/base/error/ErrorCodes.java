package com.scottxuan.base.error;

/**
 * @author : scottxuan
 */
public interface ErrorCodes {
    /**
     * 服务器异常
     */
    IError SYS_ERROR_401 = new ErrorCode(401, "sys.error.401");
    IError SYS_ERROR_403 = new ErrorCode(403, "sys.error.403");
    IError SYS_ERROR_404 = new ErrorCode(404, "sys.error.404");
    IError SYS_ERROR_500 = new ErrorCode(500, "sys.error.500");

    /**
     * 操作异常
     */
    IError OPERATE_SUCCESS = new ErrorCode(200, "operate.success");
    IError OPERATE_FAILED = new ErrorCode(300, "operate.failed");
    IError OPERATE_TIMEOUT = new ErrorCode(408, "operate.timeout");
    IError OPERATE_ILLEGAL = new ErrorCode(478, "operate.illegal");
    IError ERROR_COMMON = new ErrorCode(601, "error.common");

    /**
     * 账户异常701-800
     */
    IError SMS_CODE_EXIST = new ErrorCode(701, "sms.code.exist");
    IError SMS_CODE_ERROR = new ErrorCode(702, "sms.code.error");
    IError SMS_CODE_SEND_FAILED = new ErrorCode(703, "sms.code.send.failed");

    /**
     * 参数异常10001-10100
     */
    IError ARGS_EXIST = new ErrorCode(10001, "args.exist");
    IError ARGS_NOT_EXIST = new ErrorCode(10002, "args.not.exist");
    IError ARGS_NOT_LEGAL = new ErrorCode(10003, "args.not.legal");
    IError ARGS_EMAIL_NOT_LEGAL = new ErrorCode(10004, "args.email.not.legal");
    IError ARGS_TELEPHONE_NOT_LEGAL = new ErrorCode(10005, "args.telephone.not.legal");
    IError ARGS_FORMAT_ERROR = new ErrorCode(10006, "args.format.error");

    /**
     * 数据异常10101-10200
     */
    IError DATA_EXIST = new ErrorCode(10101, "data.exist");
    IError DATA_NOT_EXIST = new ErrorCode(10102, "data.not.exist");

    /**
     * 文件异常10201-10300
     */
    IError FILE_TYPE_NOT_SUPPORT = new ErrorCode(10201, "file.type.not.support");
    IError FILE_UPLOAD_FAILED = new ErrorCode(10202, "file.upload.failed");
    IError FILE_EXCEED_MAX_SIZE = new ErrorCode(10203, "file.exceed.max.size");
    IError FILE_DOWNLOAD_FAILED = new ErrorCode(10204, "file.download.failed");

    /**
     * 账户异常10301-10400
     */
    IError ACCOUNT_LOCKED = new ErrorCode(10301, "account.locked");
    IError ACCOUNT_EXIST = new ErrorCode(10302, "account.exist");
    IError ACCOUNT_NOT_EXIST = new ErrorCode(10303, "account.not.exist");
    IError ACCOUNT_USERNAME_OR_PASSWORD_ERROR = new ErrorCode(10304, "account.username.or.password.error");
    IError ACCOUNT_PASSWORD_ERROR = new ErrorCode(10305, "account.password.error");
    IError ACCOUNT_OLD_PASSWORD_ERROR = new ErrorCode(10306, "account.old.password.error");
    IError ACCOUNT_TWO_PASSWORD_NOT_EQUAL = new ErrorCode(10307, "account.two.password.not.equal");
    IError ACCOUNT_PASSWORD_NOT_CHANGE = new ErrorCode(10308, "account.password.not.change");
}
