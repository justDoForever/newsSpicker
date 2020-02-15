package com.whch.newspicker.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whch.newspicker.entity.user;
import com.whch.newspicker.service.userLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")
public class userLoginController {

    @Autowired
    private userLoginService userloginservice;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/userRegister")
    public String userRegister(){
        return "pages/register";
    }
    @RequestMapping("/repassword")
    public String repassword(){ return "pages/repassword";}
   @RequestMapping("/userlist")
   public String userlist( Model model, @RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "5")Integer pageSize){
       //引入分页查询，使用PageHelper分页功能在查询之前传入当前页，然后多少记录
//       System.out.println(pageSize);
       PageHelper.startPage(pageNum, pageSize);
       //startPage后紧跟的这个查询就是分页查询
       List<user> users = userloginservice.userList();
//       System.out.println(users.size());
       //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
       PageInfo pageInfo = new PageInfo<user>(users, 5);

       model.addAttribute("pageInfo", pageInfo);
//System.out.println(pageInfo.getPageSize());
       //获得当前页
       model.addAttribute("pageNum", pageInfo.getPageNum());
       //获得一页显示的条数
       model.addAttribute("pageSize", pageInfo.getPageSize());
       //是否是第一页
       model.addAttribute("isFirstPage", pageInfo.isIsFirstPage());
       //获得总页数
       model.addAttribute("totalPages", pageInfo.getPages());
       //是否是最后一页
       model.addAttribute("isLastPage", pageInfo.isIsLastPage());

       return "pages/content/userM";
//       int pageSize = 15;  //每頁多少条数据
//       int pageLinkMax = 10; //页面上最多显示的页码数量
//       int curPage = request.getParameter("page") == null ? 1 : Integer
//               .valueOf(request.getParameter("page")); //当前页页码
//       int fromNum = (curPage - 1) * pageSize; //查询数据时从第几”条“开始，不是第几页
//
//       List<user> userlist=userloginservice.userList();
//       int count=userlist.size();
//
//       // 8 +3  11/4 = 2;
//       // 9 +3 12/4
//       int maxPage = (count + pageSize - 1) / pageSize; //通过总条数和每页显示条数计算 总页数
//       int beginPage = curPage - pageLinkMax/2; //计算页码开始数
//       if (beginPage<1){
//           beginPage = 1;
//       }
//
//       int endPage = curPage + pageLinkMax/2; //计算页码结束数
//       if (endPage>maxPage){
//           endPage = maxPage;
//       }
//
//       //把分页相关的数据传到jsp页面进行处理
//       request.setAttribute("curPage", curPage);
//       request.setAttribute("maxPage", maxPage);
//       request.setAttribute("beginPage", beginPage);
//       request.setAttribute("endPage", endPage);
//       List<user> userlistlimit=userloginservice.userlistlimit(fromNum,pageSize);
//       request.setAttribute("userlist",userlistlimit);

   }
//   改状态，启用禁用
   @RequestMapping("/changeState")
   public String changeState(String state,int id,HttpServletRequest request){
//       System.out.println(state+"***********************"+id);
       user User=new user();
       User.setState(state);
       User.setId(id);
       int flag=userloginservice.updateByPrimaryKeySelective(User);
       if(flag==1){
           return "redirect:userlist";
       }
       return null;
   }
    @RequestMapping("/userLogin")
    public String userLogin(@RequestParam("tel")String tel,@RequestParam("password")String password,HttpServletRequest request){
//       System.out.println(tel+password);
        if(tel == "" || password == ""){
            request.setAttribute("msg","账号密码不能为空");
            return "login";
        }

        user User=userloginservice.userLogin(tel, password);
        if(User != null && User.getState().equals("0")){

            request.getSession().setAttribute("session_user",User);
            return "pages/back";
        }
        else if(User !=null && User.getState().equals("1")){
            request.setAttribute("msg","账号已被禁用，请联系管理员");
        }
        else{
            request.setAttribute("msg","输入账号或密码有误!");
        }

        return "login"; }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping("/userLoginOut")
    public String userLoginOutController(HttpServletRequest request){
        request.getSession().removeAttribute("session_user");
        return "login";
    }
    @RequestMapping("/register")
    public String register( HttpServletRequest request,String name,String password,String phone_number,String email ){
        user userBean =new user();
        userBean.setName(name);
        userBean.setPassword(password);
        userBean.setTel(phone_number);
        userBean.setEmail(email);
        userBean.setState("0");//启用
        userBean.setAcess("0");//普通用户
//System.out.println(userloginservice.userLogin(phone_number,password));
        if( userloginservice.userLogin(phone_number,password)==null){
            int flag = userloginservice.register(userBean);
//        System.out.println(flag);
            if(flag==1)
            {
                request.setAttribute("msg","注册成功");
                return "login";
            }

        }
        request.setAttribute("msg","注册失败");
        return "login";
    }

    @RequestMapping("/userRepassword")
    public String userRepassword(String password,HttpServletRequest request){
        user User=(user)request.getSession().getAttribute("session_user");
        User.setPassword(password);
        int flag=userloginservice.updateByPrimaryKeySelective(User);
        if(flag==1)
        {
            request.setAttribute("msg","修改密码成功");
            return "pages/back";
        }
        request.setAttribute("msg","修改密码失败");
        return "pages/back";
    }
}
