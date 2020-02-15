package com.whch.newspicker.utils;

import com.whch.newspicker.entity.news;
import com.whch.newspicker.mapper.newsMapper;
import com.whch.newspicker.service.newsService;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 输入一个种子链接到addUrl()，将该链接即未访问过的链接加入到等待队列中，并判断该链接是否在控制深度的集合中，若无则加入该链接
 * 启动多线程，先用getUrl从allWaitUrl队列中获得一个未访问链接并进行解析parseUrl（）
 * parseUrl（）进行数据内容的提取，将网页中的关联网页链接提取出来，并将尚未爬取的关联网页链接通过addUrl（）放到allWaitUrl队列中
 * 解析过的链接加到allOverUrl集合中，重复执行直到等待队列中为链接时结束所有线程，程序结束
 */
public class sinaSpider {
    //等待爬取的URL
    private static List<String> allWaitUrl=new ArrayList<String>();
    //已经爬取的URL
    private  static Set<String> allOverUrl=new HashSet<>();
    //记录所有URL的深度，以便在addURL方法内判断
    private static Map<String,Integer> allUrlDepth=new HashMap<>();
    //爬去网页的深度
    private static int maxDepth=3;
    //声明object独享帮助线程的等待操作
    private static Object obj=new Object();
    //设置总线程数
    private static final int MAX_THERD=20;
    //设置空闲进程
    private static int count=0;
    //声明dao对象
    private  static  newsService newsservice;

    public static void setNewsservice(newsService newsservice) {
        sinaSpider.newsservice = newsservice;
    }

    public static void setMaxDepth(int maxDepth) {
        sinaSpider.maxDepth = maxDepth;
    }
    public static  void main(String args[]){
        String strUrl="http://news.sina.com.cn/";
        //爬取第一输入的url
        addUrl(strUrl,0);
        for(int i=0;i<MAX_THERD;i++){
            new sinaSpider().new MyThread().start();
        }
    }
    public static void catchN(newsService newsservic,String url,int maxDepth){
        setNewsservice(newsservic);
        setMaxDepth(maxDepth);
        //爬取第一输入的url
        addUrl(url,0);
        for(int i= 0 ;i<MAX_THERD;i++){
            new sinaSpider().new MyThread().start();
        }
    }
    /**
     * url加到等待队列中，并判断是否已经放过，若没有就放入allUrlDepth中
     *
     * @param href
     * @param depth
     */
    public static synchronized void addUrl(String href,Integer depth){
        //将URL放入等待队列
        allWaitUrl.add(href);
        //判断URL是否已经存在
        if(!allUrlDepth.containsKey(href)){
            allUrlDepth.put(href,depth+1);
        }

    }
    /**
     * 用多线程进行URL爬取
     */
    public class MyThread extends Thread{
        public void run(){
            //编写一个死循环，以便线程可以一直存在
            while(true){
                String url=getUrl();
//                System.out.println(url);
                if(url!=null){
                    //调取该方法爬取的数据
//                    System.out.println(allUrlDepth.get(url));
                    parseUrl(url,allUrlDepth.get(url));
                } else{
                    System.out.println("当前线程就绪，等待链接爬取："+this.getName());
                    //线程+1
                    count++;
//                    建立一个对象，帮助线程进入等待状态wait（）
                    synchronized(obj){
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
//线程-1
                    count--;
                }
            }
        }
    }

    /**
     * 解析网页
     * @param strUrl
     * @param depth
     */
    public  static void parseUrl(String strUrl, Integer depth) {
        //相判断当前url是否爬取过
        //判断深度是否符合要求
//        System.out.println( depth <= maxDepth);
        if(!allOverUrl.contains(strUrl) && depth <= maxDepth){
            System.out.println("当前执行的"+Thread.currentThread().getName()+"爬虫线程处理爬取"+strUrl);
            //用jsoup进行数据爬取
            try {
                Document doc= Jsoup.connect(strUrl).get();
//                System.out.println(!strUrl.equals("http://news.sina.com.cn/")+"******************************");
//                System.out.println(strUrl+"******************************");
                news n1=newsservice.hasUrl(strUrl);
                if(n1!=null){
                    news New=new news();
                    if(strUrl.contains(".shtml")){
                        String id=strUrl.substring(strUrl.lastIndexOf("/"),strUrl.lastIndexOf(".")).substring(6);
                        String currentStr="gn:comos-"+id+":0";
                        String commentRqStr="http://comment5.news.sina.com.cn/cmnt/count?format=json&newslist="+currentStr;
//                        System.out.println(Jsoup.connect(commentRqStr).ignoreContentType(true).execute().body());
                        JSONObject json = JSONObject.fromObject(Jsoup.connect(commentRqStr).ignoreContentType(true).execute().body());
//                    System.out.println(json.getJSONObject("result").getJSONObject("count").getJSONObject(currentStr).get("total"));
                        New.setClickrate(Integer.parseInt(json.getJSONObject("result").getJSONObject("count").getJSONObject(currentStr).get("total").toString()));
                    }
                    New.setUrl(strUrl);
                    newsservice.changeClicrate(New);
                }
                if(n1==null && !strUrl.equals("https://news.sina.com.cn/") && !strUrl.equals("https://news.sina.com.cn/china/") && strUrl.contains("https://news.sina.com.cn/") && (doc!=null)){
                    //通过doc提取有效的内容
                    news New=new news();
                    New.setTitle(doc.title());//标题
                    Element element=doc.getElementsByAttributeValue("name","keywords").first();
                    New.setKeywords(element.attr("content"));//关键字
                    if(!doc.hasClass("article")){
                        Elements e=doc.getElementById("article").getElementsByTag("p");
                        int i=0;
                        String str="";
                        for(Element element1:e){
                            i++;
                            if(i>3) str+=element1.text();
                        }
                        New.setText(str);//文本内容
                    }
                    element=doc.getElementsByAttributeValue("property","article:published_time").first();
                    New.setPubdate(element.attr("content").substring(0,10));//发布时间
                    element=doc.getElementsByAttributeValue("property","article:author").first();
                    New.setPubsource(element.attr("content"));//来源
                    Date now=new Date();
                    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
                    New.setGetdate(sd.format(now));//当前时间
                    if(strUrl.contains(".shtml")){
                        String id=strUrl.substring(strUrl.lastIndexOf("/"),strUrl.lastIndexOf(".")).substring(6);
                        String currentStr="gn:comos-"+id+":0";
                        String commentRqStr="http://comment5.news.sina.com.cn/cmnt/count?format=json&newslist="+currentStr;
//                        System.out.println(Jsoup.connect(commentRqStr).ignoreContentType(true).execute().body());
                        JSONObject json = JSONObject.fromObject(Jsoup.connect(commentRqStr).ignoreContentType(true).execute().body());
//                    System.out.println(json.getJSONObject("result").getJSONObject("count").getJSONObject(currentStr).get("total"));
                        New.setClickrate(Integer.parseInt(json.getJSONObject("result").getJSONObject("count").getJSONObject(currentStr).get("total").toString()));
                    }
                    New.setUrl(strUrl);
//                    System.out.println(New);
                    newsservice.insert(New);
                }
            //解析所欲超链接
                Elements aEs=doc.getElementsByTag("a");
                if(aEs!=null && aEs.size() >0){
                   for(Element a : aEs){
                       String href=a.attr("href");
                       //截取网址，并给出筛选条件
                       if((href.startsWith("http:")||href.startsWith("https:")) && href.contains("https://news.sina.com.cn/")){
                           //addUrl方法
                           addUrl(href,depth+1);
                       }
                   }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            //把当前的url放到已访问队列
            allOverUrl.add(strUrl);
            System.out.println(strUrl+"爬取完成，已经爬取的数量为："+allOverUrl.size()+"剩余爬取量为："+allWaitUrl.size());


        }
        //判断集合中是否还有其他链接需要爬取，如果有，则进行线程唤醒
        if((depth <= maxDepth) && allWaitUrl.size() > 0  ){
//            System.out.println("等待队列大小:"+allWaitUrl.size()+"爬取深度："+depth+"当前网页深度"+allUrlDepth.get(strUrl));

            synchronized(obj){
                obj.notify();
            }
        } else {
            System.out.println("*****&&&&&&&&&&****爬取结束...*******************************");
            System.exit(0);
        }

    }

    /**
     * 获取等待队列的下一url，并从等待队列中移除
     * @return
     */
    public static synchronized String getUrl() {
        if(allWaitUrl.size()>0){
            String nextUrl=allWaitUrl.get(0);
            allWaitUrl.remove(0);
            return nextUrl;
        }
        return null;
    }
}
