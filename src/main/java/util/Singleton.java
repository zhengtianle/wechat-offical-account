package util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.AccessToken;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * 单例模式获取token
 * 自动刷新
 *
 * @auther ZhengTianle
 * @Date: 18-7-16
 */
public class Singleton {

    private static Logger log = LoggerFactory.getLogger(Singleton.class);

    //map中包含一个accessToken和缓存的时间戳
    private Map<String,String> map = new HashMap<>();

    private static Singleton singleton = null;

    private Singleton(){}

    public static Singleton getInstance(){
        if(singleton == null){
            singleton = new Singleton();
        }
        return singleton;
    }


    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getAccessToken(String appid, String appsecret){
        String result = null;
        Singleton singleton = Singleton.getInstance();
        Map<String,String> map = singleton.getMap();
        //起始时间
        String time = map.get("time");
        String accessToken = map.get("access_token");

        Long currentDate = new Date().getTime();//单位为 毫秒
        //设置比过期时间少一些---> 7200s过期 设置超过7000s就刷新一次
        if(accessToken != null && time != null && currentDate-Long.parseLong(time) < 7000*1000){
            //从缓存中拿数据为返回结果赋值
            result = accessToken;
        }else{
            String requestUrl = WeiXinUtil.ACCESS_TOKEN_URL
                    .replace("APPID",appid)
                    .replace("APPSECRET",appsecret);
            JSONObject jsonObject = WeiXinUtil.httpRequest(requestUrl,"GET",null);

            //请求success
            if(null != jsonObject){
                try{
                    map.put("time",currentDate + "");
                    map.put("access_token",jsonObject.getString("access_token"));

                    result = jsonObject.getString("access_token");
                }catch (JSONException e){
                    accessToken = null;
                    //获取token失败
                    log.error("获取token失败 errcode:{} errmsg:{}",
                            jsonObject.getInt("errcode"),
                            jsonObject.getString("errmsg"));
                }
            }

        }

        return result;
    }


}
