# newsSpicker
 # 网络新闻数据抓取与分析系统
 个人本科毕业设计
 # 需求分析

为了更好的满足用户对新闻热点快速获得的需求，需要通过对国内特定大型新闻网站对新闻数据进行抓取，新闻数据的抓取需要对新闻网页进行抽取，获取新闻的文本、发布时间、点击量和爬取时间等信息，以便后期处理。数据的分析需要对获取的新闻文本通过文本分析算法, 进行中文分词、关键字提取、文本摘要、人名识别、地名识别、以及生成词云图等。数据管理模块需要能排除一些垃圾信息，而可视化模块的设计需要更好地研究国内新闻热点和网络舆情动态变化状态。系统工作流程图如下图所示。
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/flows.png)


# 概要设计
该系统采用Spring Boot框架，按照需求分析得出的系统总体功能结构图如图下图所示。
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/function.png)

# 技术：
- J2EE的架构和MVC`模式
- Spring Boot
- HanLP汉语言处理包
- KUMO词云生成框架
- Maven进行项目管理与构建
- 使用Bootstrap进行设计，前端框架Echarts进行图表展示以及热点地点的突出显示。

# 界面
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/login.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/user image.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/data manage.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/picker.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/topk.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/ciyuntu.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/where.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/position.png)
![image](https://github.com/justDoForever/newsSpicker/edit/master/image/tuijian.png)


# 总结
该系统的分词性能有待进一步优化，尤其是在新闻文本比较大的情况下，可以明显感觉到系统延迟，可能降低用户的体验感，还有人名识别的精度不是很高，算法也需要改进，语料库也需要更新，对数据的抓取类型也可以进行扩展，可以不仅局限于文本，以上的不足是下一步研究的方向。
