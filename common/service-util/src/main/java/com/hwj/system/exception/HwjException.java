/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/13 15:57
 */


package com.hwj.system.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HwjException extends RuntimeException{

    private Integer code;
    private String msg;
}
