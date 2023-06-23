package com.hwj.system.annotation;


import com.hwj.system.enums.BusinessType;
import com.hwj.system.enums.OperatorType;

import java.lang.annotation.*;

/*
* 自定义日志注解
*
 */
@Target({ElementType.PARAMETER,ElementType.METHOD}) // 作用在参数和方法上
@Retention(RetentionPolicy.RUNTIME)  // 运行时有效
@Documented  // 生成文档
public @interface Log {
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveResponseData() default true;
}
