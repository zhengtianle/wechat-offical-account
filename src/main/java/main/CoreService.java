package main;

import sun.plugin2.message.Message;
import util.MessageUtil;
import response.TextMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 核心服务类
 *
 * @auther ZhengTianle
 * @Date: 18-7-12
 */
public class CoreService {
    /**
     * 处理微信发来的请求
     */
    public static String processRequest(HttpServletRequest request){
        String responseMessage = null;
        try{
            //默认什么消息都不回复
            String responseContent = "";

            //xml请求解析
            Map<String,String> requestMap = MessageUtil.parseXml(request);

            String fromUserName = requestMap.get("FromUserName");
            String toUserName = requestMap.get("ToUserName");
            String msgType = requestMap.get("MsgType");

            switch (msgType){
                case MessageUtil.REQUEST_MESSAGE_TYPE_TEXT:responseContent = "";break;
                case MessageUtil.REQUEST_MESSAGE_TYPE_IMAGE:responseContent = "";break;
                case MessageUtil.REQUEST_MESSAGE_TYPE_LOCATION:responseContent = "";break;
                case MessageUtil.REQUEST_MESSAGE_TYPE_LINK:responseContent = "";break;
                case MessageUtil.REQUEST_MESSAGE_TYPE_VOICE:responseContent = "";break;
                case MessageUtil.REQUEST_MESSAGE_TYPE_EVENT:

                    String eventType = requestMap.get("Event");
                    if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
                        responseContent = "欢迎关注山东大学（威海）官方微信公众号";
                    }else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){

                        /*//Click则为key  View则为url
                        String eventKey = requestMap.get("EventKey");

                        switch (eventKey){
                            case "V1001_PERSONNEL":
                                //人事处
                                responseContent = "该功能升级维护中，尽情期待";
                                break;
                        }*/

                    }else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
                        // 取消订阅后用户再收不到公众号发送的消息,因此不需要回复消息
                    }

                    break;
            }//switch

            //组装要返回给微信端的文本消息对象,进而转成xml返回
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESPONSE_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            textMessage.setContent(responseContent);
            responseMessage = MessageUtil.textMessageToXml(textMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return responseMessage;
    }



}
