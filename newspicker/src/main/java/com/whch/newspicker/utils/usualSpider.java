package com.whch.newspicker.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.deploy.net.HttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 *
 */
public class usualSpider {
    public static void main(String args[])  {


        try {
            String thisLine,url="https://news.sina.com.cn/c/xl/2019-03-31/doc-ihtxyzsm2022588.shtml";
            Document doc=Jsoup.connect(url).get();
            System.out.println();
//            Document doc=Jsoup.connect("http://news.163.com/19/0325/15/EB4GS21T0001875P.html").get();

            //            String str=getContent("http://www.sohu.com/a/304684570_267106?g=0?code=87b72773a2f002f0475c4ca5b0118fc8&spm=smpc.home.top-news1.1.1553911061794bGUJysu&_f=index_cpc_0");

//            System.out.println(doc.text());
            System.out.println(doc.title());
           // System.out.println(doc.getElementsByClass("post_cnum_tie js-tielink js-tiejoincount"));

            Element element=doc.getElementsByAttributeValue("name","keywords").first();
            System.out.println(element.attr("content"));
            if(!doc.hasClass("article")) {
                Elements e = doc.getElementById("article").getElementsByTag("p");
                int i = 0;
                String str = "";
                for (Element element1 : e) {
                    i++;
                    if (i > 3) str += element1.text();
                }
                System.out.println(str);
            }
            element=doc.getElementsByAttributeValue("property","article:published_time").first();
            System.out.println(element.attr("content").substring(0,10));
            element=doc.getElementsByAttributeValue("property","article:author").first();
            System.out.println(element.attr("content"));
            Date now=new Date();
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(sd.format(now));

            String id=url.substring(url.lastIndexOf("/"),url.lastIndexOf(".")).substring(6);
            String currentStr="gn:comos-"+id+":0";
            String commentRqStr="http://comment5.news.sina.com.cn/cmnt/count?format=json&newslist="+currentStr;
            System.out.println(Jsoup.connect(commentRqStr).ignoreContentType(true).execute().body());
            JSONObject json = JSONObject.fromObject(Jsoup.connect(commentRqStr).ignoreContentType(true).execute().body());
            System.out.println(json.getJSONObject("result").getJSONObject("count").getJSONObject(currentStr).get("total")
            );
// for (int j=0;j<jary.size();j++) {
//JSONObject obj = jary.getJSONObject(j);
//String s2=obj.getString("count");
//}

            System.out.println(json.get("total"));
             //http://news.ifeng.com/hotnews
            //http://comment.news.sohu.com/djpm/
            //"http://news.163.com/special/0001386F/rank_news.html"
//            String msg = obj.getString("responseMessage");
//            System.out.println(Jsoup.connect(commentRqStr).ignoreContentType(true).execute().body().indexOf("'total':"));
            //            FileWriter fw=new FileWriter("d://b.txt");
//
//             fw.write(str);fw.close();
//            FileReader fr=new FileReader("d://b.txt");
//            BufferedReader bfr=new BufferedReader(fr);
//            while((thisLine=bfr.readLine())!=null){
//               line+=thisLine;
//            }System.out.print(line.replaceAll(" ",""));
//            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 获得通用文本
     * @param url
     * @return
     * @throws IOException
     */
    public static String getContent(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        String htmlStr = doc.getElementsByTag("body").toString(); // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {System.err.println("Html2Text: " + e.getMessage()); }
        //剔除空格行
        textStr=textStr.replaceAll("&gt","");
        textStr =textStr.replaceAll("（）","");
        textStr=textStr.replaceAll("[ ]+", " ");
        textStr=textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        textStr=textStr.replaceAll("\n","");
        return textStr;// 返回文本字符串


    }
}