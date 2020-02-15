package com.whch.newspicker.controller;

import com.whch.newspicker.entity.fenci;
import com.whch.newspicker.service.newsService;
import com.whch.newspicker.utils.analyzeUtils;
import com.whch.newspicker.utils.sinaSpider;
import com.whch.newspicker.utils.usualSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/data")
public class dataCatchController {
    @Autowired
    private newsService newsservice;
    @RequestMapping("/catch")
    public String Catch(){
        return "/pages/content/catch";
    }
    @RequestMapping("/catchn")
    public String catchn(String depth,String select1,HttpServletRequest request){
        String url="";
        if(select1.equals("新浪")) url="http://news.sina.com.cn/";
        int depth1=Integer.parseInt(depth);
        sinaSpider.catchN(newsservice,url,depth1);
        request.setAttribute("msg","新闻爬取完成！");
        return "/pages/content/catch";
    }
    @RequestMapping("/usual_a")
    public  String usual_a(String url, HttpServletRequest request){
        try {
            String str=usualSpider.getContent(url);
            request.setAttribute("str",str);
            return "/pages/content/text_analyze";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    @RequestMapping(value = "/text_analyze")
    public String text_analyze(String str,HttpServletRequest request){
        try {
            File f=new File(ResourceUtils.getURL("classpath:").getPath());
            if(!f.exists())f=new File("");
//            System.out.println(f.getAbsolutePath()+"-------------------------------------------");
            fenci fen=new fenci();
            fen= analyzeUtils.getResult(str,f.getAbsolutePath());
//            System.out.println(fen.getFenzi()+"************************************");
            request.setAttribute("f",fen);
            return "/pages/content/analyze";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
