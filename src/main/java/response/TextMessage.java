package response;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文本消息
 *
 * @auther ZhengTianle
 * @Date: 18-7-11
 */
public class TextMessage extends BaseMessage{
    // 回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }
    public void setContent(String content) {
        Content = content;
    }
}
