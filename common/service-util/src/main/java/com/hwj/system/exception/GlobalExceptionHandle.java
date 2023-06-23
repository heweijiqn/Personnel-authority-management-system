/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/13 15:46
 */


package com.hwj.system.exception;


import com.hwj.common.result.Result;
import com.hwj.common.result.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        System.out.println("全局异常处理......");
        e.printStackTrace();
        return Result.fail().message("执行了全局异常处理");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        System.out.println("算数异常处理......");
        e.printStackTrace();
        return Result.fail().message("执行了特定异常处理");
    }

    @ExceptionHandler(HwjException.class)
    @ResponseBody
    public Result error(HwjException e) {
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }

    /**
     * spring security异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        return Result.fail().code( ResultCodeEnum.PERMISSION.getCode()).message("权限不足");
    }

}
