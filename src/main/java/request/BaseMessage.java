package request;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 消息基类(普通用户->公众号)
 *
 * @auther ZhengTianle
 * @Date: 18-7-10
 */
public class BaseMessage {
    //开发者微信号
    private String ToUserName;
    //发送方帐号（一个OpenID）
    private String FromUserName;
    //消息创建时间 （整型）
    private long CreateTime;
    //消息类型(text/image/location/link/voice)
    private String MsgType;
    // 消息 id,64 位整型
    private long MsgId;

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public void setMsgId(long msgId) {
        MsgId = msgId;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public long getMsgId() {
        return MsgId;
    }

}
