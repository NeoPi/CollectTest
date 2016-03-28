package cn.com.byg.collecttest.utils;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import cn.com.byg.collecttest.JavaBean.BooksBean;

/**
 * @author NeoPi.
 * @date 2015/9/1
 */
public class XmlPullParserUtils {

    public synchronized static List<BooksBean> parser(InputStream inputStream) {

        List<BooksBean> lists = null;
        BooksBean book = null;
        try {
            XmlPullParser parser = Xml.newPullParser(); // 得到pull解析器
            parser.setInput(inputStream, "UTF-8"); // 设置输入流的编码格式


            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        lists = new ArrayList<BooksBean>();

                        break;
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        if ("book".equals(name))
                            book = new BooksBean();
                        if (book != null) {
                            if ("id".equals(name)) {
                                book.setId(Integer.valueOf(parser.nextText()));
                            } else if ("author".equals(name)) {
                                book.setAuthor(parser.nextText());
                            } else if ("name".equals(name)) {
                                book.setName(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("book".equals(parser.getName()))
                            lists.add(book);
                        book = null;
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lists;
    }


    /**
     * 将参数类型转换成XML序列化数据用于保存
     *
     * @param books
     * @return
     * @throws Exception
     */

    public static String serialize(List<BooksBean> books) throws Exception {
//      XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//      XmlSerializer serializer = factory.newSerializer();

        XmlSerializer serializer = Xml.newSerializer(); //由android.util.Xml创建一个XmlSerializer实例
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);   //设置输出方向为writer
        serializer.startDocument("UTF-8", true);
        serializer.startTag("", "books");
        for (BooksBean book : books) {
            serializer.startTag("", "book");
            serializer.attribute("", "id", book.getId() + "");

            serializer.startTag("", "name");
            serializer.text(book.getName());
            serializer.endTag("", "name");

            serializer.startTag("", "author");
            serializer.text(book.getAuthor() + "");
            serializer.endTag("", "author");

            serializer.endTag("", "book");
        }
        serializer.endTag("", "books");
        serializer.endDocument();

        return writer.toString();
    }

}
