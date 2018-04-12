package com.belling.admin.controller.weixin.controller;

import com.belling.admin.controller.weixin.model.TextMessage;
import com.belling.admin.controller.weixin.util.CheckUtil;
import com.belling.admin.controller.weixin.util.MessageUtil;
import com.belling.base.controller.BaseController;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/weChat")
public class WeChatController extends BaseController {

    /**
     * 链接微信公众号
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getConnection",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void getConnection(HttpServletRequest request,HttpServletResponse response) throws Exception{

        boolean isGet = request.getMethod().toLowerCase().equals("get");

        if (isGet) {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            PrintWriter out = response.getWriter();
            if(CheckUtil.checkSignature(signature,timestamp,nonce)){
                out.print(echostr);
            }
        }else {
            getMesssage(request,response);
        }


    }

    public void getMesssage(HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Map<String,String> map = MessageUtil.xmlToMap(request);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            String message = null;
            if("text".equals(msgType)){
                TextMessage text = new TextMessage();
                text.setFromUserName(toUserName);
                text.setToUserName(fromUserName);
                text.setMsgType("text");
                text.setCreateTime(String.valueOf(new Date().getTime()));
                text.setContent("您发送的消息是：" + content);
                message = MessageUtil.textMessageToXml(text);
            }
            out.print(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
    }
}
