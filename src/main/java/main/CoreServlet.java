package main;

import main.CoreService;
import util.SignUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 核心请求处理类
 *
 * @auther ZhengTianle
 * @Date: 18-7-10
 */
public class CoreServlet extends HttpServlet {

    /**
     * 确认请求来自微信服务器
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //微信加密签名
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();

        /**
         * 通过检验 signature 对请求进行校验,
         * 若校验成功则原样返回 echostr,表示接入成功,
         * 否则接入失败
         */
        if(SignUtil.checkSignature(signature,timestamp,nonce)){
            out.println(echostr);
        }
        out.close();
        out = null;

    }

    /**
     * 处理微信服务器发来的消息
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //调用核心业务类接受消息、处理消息
        String responseMessage = CoreService.processRequest(request);

        //相应消息
        PrintWriter out = response.getWriter();
        out.print(responseMessage);
        out.close();

    }
}//END
