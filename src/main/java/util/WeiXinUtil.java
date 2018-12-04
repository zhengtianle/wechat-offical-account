package util;


import com.sun.deploy.net.HttpRequest;
import com.sun.xml.internal.bind.v2.TODO;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.AccessToken;
import pojo.Menu;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @auther ZhengTianle
 * @Date: 18-7-15
 */
public class WeiXinUtil {

    private static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);

    /**
     * 获取access_token接口地址
     * GET
     * 2000次/天
     */
    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 菜单创建接口地址
     * POST
     * 100次/天
     */
    public static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";


    /**
     * 发起https请求并获取结果
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式GET/POST
     * @param outputStr 提交的数据
     * @return JSONObject
     */
    public static JSONObject httpRequest(String requestUrl,
                                         String requestMethod,
                                         String outputStr){

        JSONObject jsonObject = null;
        StringBuffer stringBuffer = new StringBuffer();
        try{
            //创建SSLContext对象并使用我们自定义的信任管理器初始化
            TrustManager[] trustManagers = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");

            sslContext.init(null,trustManagers,new SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
            httpsURLConnection.setSSLSocketFactory(ssf);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setUseCaches(false);
            //设置请求方式(GET/POST)
            httpsURLConnection.setRequestMethod(requestMethod);

            if("GET".equalsIgnoreCase(requestMethod)){
                httpsURLConnection.connect();
            }

            //当有数据需要提交时
            if(null != outputStr){
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            //将返回的输入流转换成字符串
            InputStream inputStream = httpsURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while((str = bufferedReader.readLine()) != null){
                stringBuffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            httpsURLConnection.disconnect();

            jsonObject = JSONObject.fromObject(stringBuffer.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }


    /**
     * 创建菜单，由MenuManager逻辑调用
     * @param menu
     * @param accessToken 有效期限(2小时)内的access_token
     * @return 0表示创建成功 其他值表示失败
     */
    public static int createMenu(Menu menu, String accessToken){
        int result = 0;
        String url = MENU_CREATE_URL.replace("ACCESS_TOKEN",accessToken);
        String jsonMenu = JSONObject.fromObject(menu).toString();
        //调用接口创建菜单
        JSONObject jsonObject = httpRequest(url,"POST",jsonMenu);

        if(null != jsonObject){
            if(0 != (result = jsonObject.getInt("errcode"))){
                log.error("创建菜单失败 errcode:{} errmsg:{}",
                        result,jsonObject.getString("errmsg"));
            }
        }

        return result;

    }

}
