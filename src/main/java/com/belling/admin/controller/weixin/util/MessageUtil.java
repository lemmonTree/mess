package com.belling.admin.controller.weixin.util;

import com.belling.admin.controller.weixin.model.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.collections.map.HashedMap;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author admin on 2018/1/20.
 */
public class MessageUtil {
    /**
     * 将xml转成对象
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String,String> map = new HashedMap();
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);
        //获取xml的根元素
        Element root = doc.getRootElement();
        List<Element> list = root.elements();

        for(Element e : list){
            map.put(e.getName(),e.getText());
        }
        ins.close();
        return map;
    }

    /**
     * 将对象转xml
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage){
        XStream xStream = new XStream();
        xStream.alias("xml",textMessage.getClass());
        return xStream.toXML(textMessage);
    }
}
