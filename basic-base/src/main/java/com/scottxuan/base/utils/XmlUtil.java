package com.scottxuan.base.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author : pc
 * @date : 2020/9/7
 */
public class XmlUtil {
    /**
     * xml字符串转对象
     * @param xml
     * @param clazz
     * @return
     */
    public static <T> T xmlToBean(String xml, Class<T> clazz) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xml);
            xmlObject = unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return (T)xmlObject;
    }

    /**
     * 对象转xml字符串
     * @param bean
     * @param clazz
     * @return
     * @throws JAXBException
     */
    public static String beanToXml(Object bean,Class<?> clazz){
        String result = "";
        try{
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter writer = new StringWriter();
            marshaller.marshal(bean,writer);
            result = writer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
