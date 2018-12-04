package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.*;
import util.Singleton;
import util.WeiXinUtil;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * 菜单管理类
 *
 * @auther ZhengTianle
 * @Date: 18-7-16
 */
public class MenuManager {

    private static Logger log = LoggerFactory.getLogger(MenuManager.class);

    public static String APPID = "your appid";

    public static String APPSECRET = "your appsecret";

    public static String PERSONNEL_URL = "http://rsc.wh.sdu.edu.cn/";

    public static String CALENDAR_URL = "http://msg.weixiao.qq.com/t/ca83a12f9523585716bb1d272b95f816";

    public static String PHONE_URL = "http://msg.weixiao.qq.com/t/73191f942597df0d745dc781972e2e26";

    public static String WEATHER_URL = "http://astro.wh.sdu.edu.cn/weather/try_c.htm";

    public static String SHARE_URL = "http://mp.weixin.qq.com/mp/homepage?__biz=MzU3NDU2Mjg4MQ==&hid=2&sn=02186350e6f38b9692a8c8a865205980&scene=18#wechat_redirect";

    private static String STUDENT_URL = "https://apps.eol.cn/461/index.html";

    /**
     * 真*创建菜单
     */
    public static String createMenu(){
        String accessToken = Singleton.getInstance().getAccessToken(APPID,APPSECRET);
        String msg = "未获取到accessToken";

        if(null != accessToken){
            int result = WeiXinUtil.createMenu(getMenu(),accessToken);
            if(result == 0){
                log.info("菜单创建成功");
                msg = "菜单创建成功";
            }else{
                log.info("菜单创建失败，错误码："+result);
                msg = "菜单创建失败，错误码："+result;
            }
        }
        return msg;

    }

    /**
     * 组装菜单
     * @return
     */
    private static Menu getMenu(){

        ViewButton btn11 = new ViewButton();
        btn11.setName("校历");
        btn11.setUrl(CALENDAR_URL);

        ViewButton btn12 = new ViewButton();
        btn12.setName("即时天气");
        btn12.setUrl(WEATHER_URL);

        ViewButton btn13 = new ViewButton();
        btn13.setName("办公电话");
        btn13.setUrl(PHONE_URL);

        ViewButton btn14 = new ViewButton();
        btn14.setName("人事处");
        btn14.setUrl(PERSONNEL_URL);


        ComplexButton firstButton = new ComplexButton();
        firstButton.setName("常用");
        firstButton.setSub_button(new Button[]{btn11,btn12,btn13,btn14});

        ViewButton secondButton = new ViewButton();
        secondButton.setName("共建共享");
        secondButton.setUrl(SHARE_URL);

        ViewButton thirdButton = new ViewButton();
        thirdButton.setName("招生信息");
        thirdButton.setUrl(STUDENT_URL);

        Menu menu = new Menu();
        menu.setButton(new Button[]{firstButton,secondButton,thirdButton});

        System.out.println(menu.toString());

        return menu;
    }


}
