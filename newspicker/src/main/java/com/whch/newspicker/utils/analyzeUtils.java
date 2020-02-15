package com.whch.newspicker.utils;


import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import com.whch.newspicker.entity.fenci;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class analyzeUtils {
    public static void main(String args[]){
        String fen="";
        List<Term> list =HanLP.segment("你好，习近平!欢迎使用HanLP汉语言处理包！");
        for(Term t :list){
            if(t.nature.startsWith("nr"))fen+=t.word;
        }
        HttpServletRequest request = null;


        System.out.print(request.getContextPath());
    }
   public static fenci getResult(String str,String url) throws IOException {
       fenci f=new fenci();
       String fen=f.getFenzi();
       List<Term> list =HanLP.segment(str);
       for(Term t :list){
           fen+=t;
       }
       f.setFenzi(fen);
       String keyw="";
       List<String> list1= HanLP.extractSummary(str,2);
       f.setExtracts(list1.get(0)+"\\n"+list1.get(1));
       List<String> list2=HanLP.extractKeyword(str,10);
       for(String s : list2){
           keyw+=s+" ";
       }
       f.setKeywords(keyw);
//       System.out.println(keyw+"*********************************");

       Segment segment = HanLP.newSegment().enablePlaceRecognize(true);
       List<Term> termList=segment.seg(str);
       String name="";
       List<String> list3=new ArrayList<String>();
       for(Term t:termList){
        if(t.nature.startsWith("nr")) {
            if( !name.contains(t.word))name+=t.word+" ";
        }
       else if(t.nature.startsWith("n")) list3.add(t.word);
       }
       f.setName(name);

       //建立词频分析器，设置词频，以及词语最短长度，此处的参数配置视情况而定即可
       FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
       frequencyAnalyzer.setWordFrequenciesToReturn(600);
       frequencyAnalyzer.setMinWordLength(2);

       //引入中文解析器
       frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
       //指定文本文件路径，生成词频集合
       final List<WordFrequency> wordFrequencyList = frequencyAnalyzer.load(list3);
       //设置图片分辨率
       Dimension dimension = new Dimension(1920,1080);
       //此处的设置采用内置常量即可，生成词云对象
       WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
       //设置边界及字体
       wordCloud.setPadding(5);
       java.awt.Font font = new java.awt.Font("STSong-Light", 2, 30);
       //设置词云显示的三种颜色，越靠前设置表示词频越高的词语的颜色
       wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
       wordCloud.setKumoFont(new KumoFont(font));
       //设置背景色
       wordCloud.setBackgroundColor(new Color(0,0,0));
       //设置背景图 片
       //wordCloud.setBackground(new PixelBoundryBackground("E:\\爬虫/google.jpg"));
       //设置背景图层为圆形
       wordCloud.setBackground(new CircleBackground(500));
       wordCloud.setFontScalar(new SqrtFontScalar(30, 60));//12 45
       //生成词云
       wordCloud.build(wordFrequencyList);


       wordCloud.writeToFile(url+"\\static\\img\\3.png");
//       System.out.println(url+"\\static\\img\\3.png");
       return f;
   }
}
