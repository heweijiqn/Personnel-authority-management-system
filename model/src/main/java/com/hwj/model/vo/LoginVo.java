/**
 * @author 何伟健
 * @version 1.0
 *@date 2023/6/12 17:47
 */

package com.hwj.model.vo;


/**
 * 登录对象
 */
public class LoginVo {

    /**
     * 手机号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
