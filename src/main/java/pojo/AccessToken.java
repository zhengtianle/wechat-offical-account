package pojo;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * 微信通用接口凭证
 *
 * @auther ZhengTianle
 * @Date: 18-7-15
 */
public class AccessToken {

    private String token;
    //凭证有效时间 单位为s
    private int expiresIn;

    /**
     * 记录获取到这个token的具体时间
     * 用于与当前时间比较  判断是否失效(2小时有效)
     */
    private Date startTime;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
