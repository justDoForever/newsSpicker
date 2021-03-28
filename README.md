
# newsSpicker
 # 网络新闻数据抓取与分析系统
 个人本科毕业设计
 # 需求分析

为了更好的满足用户对新闻热点快速获得的需求，需要通过对国内特定大型新闻网站对新闻数据进行抓取，新闻数据的抓取需要对新闻网页进行抽取，获取新闻的文本、发布时间、点击量和爬取时间等信息，以便后期处理。数据的分析需要对获取的新闻文本通过文本分析算法, 进行中文分词、关键字提取、文本摘要、人名识别、地名识别、以及生成词云图等。数据管理模块需要能排除一些垃圾信息，而可视化模块的设计需要更好地研究国内新闻热点和网络舆情动态变化状态。系统工作流程图如下图所示。
![flows](https://user-images.githubusercontent.com/52912000/112753234-562fa300-9009-11eb-84a2-65128d85eaa4.png)


# 概要设计
该系统采用Spring Boot框架，按照需求分析得出的系统总体功能结构图如图下图所示。
![function](https://user-images.githubusercontent.com/52912000/112753243-60ea3800-9009-11eb-8cd2-02e56c6bc705.png)

# 技术：

- J2EE的架构和MVC`模式
- Spring Boot
- HanLP汉语言处理包
- KUMO词云生成框架
- Maven进行项目管理与构建
- 使用Bootstrap进行设计，前端框架Echarts进行图表展示以及热点地点的突出显示。


# 界面







![login](https://user-images.githubusercontent.com/52912000/112753309-adce0e80-9009-11eb-9ff7-109d3f60d07e.png)
![userImage](https://user-images.githubusercontent.com/52912000/112753321-b7f00d00-9009-11eb-8c86-d36fd9463fe7.png)
![picker](https://user-images.githubusercontent.com/52912000/112753324-be7e8480-9009-11eb-82a6-f11e8777f28f.png)
![dataManage](https://user-images.githubusercontent.com/52912000/112753334-c4746580-9009-11eb-8538-7ca85f529ecf.png)
![where](https://user-images.githubusercontent.com/52912000/112753340-c8a08300-9009-11eb-8918-79da6137eae0.png)
![ciyuntu](https://user-images.githubusercontent.com/52912000/112753342-ca6a4680-9009-11eb-9ef7-6310959da9ef.png)
![position](https://user-images.githubusercontent.com/52912000/112753345-cf2efa80-9009-11eb-96bf-8066e285ab87.png)
![topk](https://user-images.githubusercontent.com/52912000/112753347-d0f8be00-9009-11eb-8c2f-8cc1c1b49395.png)
![picker](https://user-images.githubusercontent.com/52912000/112753353-d5bd7200-9009-11eb-826b-c296a7afedba.png)
![tuijian](https://user-images.githubusercontent.com/52912000/112753355-d81fcc00-9009-11eb-9973-953cc729b582.png)
![ci](https://user-images.githubusercontent.com/52912000/112753572-8e83b100-900a-11eb-8cab-a97e1e71151f.png)
![topci](https://user-images.githubusercontent.com/52912000/112753575-95122880-900a-11eb-822d-b36731f8ed25.png)

![image](https://github.com/justDoForever/newsSpicker/edit/master/image/login.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/userImage.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/dataManage.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/picker.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/topk.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/ciyuntu.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/where.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/position.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/tuijian.png)



# 总结
该系统的分词性能有待进一步优化，尤其是在新闻文本比较大的情况下，可以明显感觉到系统延迟，可能降低用户的体验感，还有人名识别的精度不是很高，算法也需要改进，语料库也需要更新，对数据的抓取类型也可以进行扩展，可以不仅局限于文本，以上的不足是下一步研究的方向。
