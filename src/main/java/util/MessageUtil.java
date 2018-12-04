package util;

import com.sun.org.apache.xerces.internal.xinclude.XIncludeTextReader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import response.Article;
import response.MusicMessage;
import response.NewsMessage;
import response.TextMessage;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 消息工具类
 *
 * @auther ZhengTianle
 * @Date: 18-7-11
 */
public class MessageUtil {
    //返回消息类型：文本
    public static final String RESPONSE_MESSAGE_TYPE_TEXT = "text";
    //返回消息类型：音乐
    public static final String RESPONSE_MESSAGE_TYPE_MUSIC = "music";
    //返回消息类型：图文
    public static final String RESPONSE_MESSAGE_TYPE_NEWS = "news";
    //请求消息类型：文本
    public static final String REQUEST_MESSAGE_TYPE_TEXT = "text";
    //请求消息类型：图片
    public static final String REQUEST_MESSAGE_TYPE_IMAGE = "image";
    //请求消息类型：链接
    public static final String REQUEST_MESSAGE_TYPE_LINK = "link";
    //请求消息类型：地理位置
    public static final String REQUEST_MESSAGE_TYPE_LOCATION = "location";
    //请求消息类型：音频
    public static final String REQUEST_MESSAGE_TYPE_VOICE = "voice";
    //请求消息类型：推送
    public static final String REQUEST_MESSAGE_TYPE_EVENT = "event";
    //请求消息类型：订阅
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    //请求消息类型：取消订阅
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    //请求消息类型：自定义菜单点击事件
    public static final String EVENT_TYPE_CLICK = "CLICK";
    //请求消息类型：自定义菜单跳转链接事件
    public static final String EVENT_TYPE_VIEW = "VIEW";

    /**
     * 解析微信发来的请求（XML）
     */
    public static Map<String,String> parseXml(HttpServletRequest request)
        throws Exception{

        //将解析结果放在HashMap中
        Map<String,String> map = new HashMap<String,String>();

        //读取输入流
        InputStream  inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);

        //得到根元素进而得到根元素的所有子节点
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();

        //遍历所有子节点
        for(Element e : elementList){
            map.put(e.getName(),e.getText());
        }

        inputStream.close();
        inputStream = null;

        return map;

    }

    /**
     * 文本消息对象->xml
     */
    public static String textMessageToXml(TextMessage textMessage){
        xstream.alias("xml",textMessage.getClass());//类别名

        return xstream.toXML(textMessage);
    }

    /**
     * 音乐对象->xml
     */
    public static String musicMessageToXml(MusicMessage musicMessage){
        xstream.alias("xml",musicMessage.getClass());//类别名

        return xstream.toXML(musicMessage);
    }

    /**
     * 图文消息对象->xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage){
        xstream.alias("xml",newsMessage.getClass());
        xstream.alias("item",new Article().getClass());

        return xstream.toXML(newsMessage);
    }

    /**
     * 扩展xstream，使支持CDATA块
     * CDATA里面的内容会被xml当做字符串处理
     */
    private static XStream xstream = new XStream(new XppDriver(){
        public HierarchicalStreamWriter createWriter(Writer out){
            return new PrettyPrintWriter(out){
                //对所有的xml节点的转换都增加CDATA标记
                boolean cdata = true;

                public void startNode(String name,Class clazz){
                    super.startNode(name,clazz);
                }

                public void writeText(QuickWriter writer,String text){
                    if(cdata){
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }else{
                        writer.write(text);
                    }
                }
            };
        }
    });

}//END
