package request;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文本消息
 *
 * @auther ZhengTianle
 * @Date: 18-7-10
 */
public class TextMessage extends BaseMessage{
    //消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
