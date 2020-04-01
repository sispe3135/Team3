package com.job.coverletter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.job.coverletter.model.joinUser.biz.JoinUserBiz;
import com.job.coverletter.model.joinUser.dto.JoinUserDto;
import com.job.coverletter.model.school.dto.SchoolDto;
import com.job.coverletter.model.total.biz.TotalBiz;
import com.job.coverletter.model.total.dto.TotalDto;

@Controller
public class UserController {
   // 로그인, 회원가입, 마이페이지, 이력작성, 캘린더, 관심공고, 비번 
   private Logger logger = LoggerFactory.getLogger(UserController.class);
   
   @Autowired
   private JoinUserBiz joinUserBiz;
   
   @Autowired
   private TotalBiz totalBiz;
   
   //마이페이지
   @RequestMapping(value="/USER_userMain.do", method=RequestMethod.GET)
   public String userMain() {
      logger.info("userMain go");
      
      
      return "USER/userMain";
   }
   

   @RequestMapping(value="/USER_userDetail.do", method=RequestMethod.GET)
   public String userDetail(Model model) {
      logger.info("userDetail go");
      model.addAttribute("totalDto", new TotalDto());
      
      return "USER/userDetail";
   }
   
   
   @RequestMapping(value = "/MAIN_main.do")
   public String main() {
      logger.info("main page");
      
      return "MAIN/main";
   }
   
   
   // join
   @RequestMapping(value = "/USER_join.do", method = RequestMethod.GET)
   public String join() {
      logger.info("joinpage go");

      return "MAIN/join";   
   }
   
   //vaild 설정
   @GetMapping
   public String joinuser(Model model) {
      
      model.addAttribute("joinuserDto",new JoinUserDto());
      
      return "MAIN/join";
   }
   
   //email중복체크
   
      @RequestMapping(value="/USER_emailcheck.do", method = RequestMethod.POST, produces = "application/text; charset=utf8")
      @ResponseBody
      public String checkemail(@ModelAttribute("joinemail") String joinemail) {  
         logger.info("이메일중복체크");   
         String res = joinUserBiz.checkemail(joinemail);
         
         
         if(res != "중복") {
            return res; 
            
         } else {
            return res;
         }
      }
   
   @RequestMapping(value = "/USER_joinRes.do", method = RequestMethod.POST)
   public String joinRes(Model model, @ModelAttribute("joinuserDto") @Valid JoinUserDto dto, BindingResult result) {
      logger.info("회원가입");
      
      if(result.hasErrors()) {
         
//         //유효성오류 찍어보기 
//      List<ObjectError> list = result.getAllErrors();
//         for(ObjectError error : list) {
//            System.out.println(error);
//         }
         return "MAIN/join";
      }
      
      System.out.println("================JoinUserDto : " + dto);
      
      
      
      int res = joinUserBiz.insertUser(dto);
      
      if(res > 0) {
         logger.info("회원가입 성공");
         return "MAIN/login";
         
      }else {
         logger.info("회원가입 실패");
         model.addAttribute("joinuserDto", dto);
         return "MAIN/join";
      }
      
   }
   
   
   
   
   
   
   // login
   @RequestMapping(value = "/USER_login.do")
   public String login() {
      logger.info("login page");
      
      return "MAIN/login";
   }
   
   @RequestMapping(value = "/USER_loginAjax.do", method = RequestMethod.POST)
   @ResponseBody
   public Map<String, Boolean> loginAjax(HttpSession session, @RequestBody JoinUserDto dto){
      
      logger.info("login ajax로 넘겨주는 controller : " + dto);
      
      JoinUserDto loginDto = joinUserBiz.login(dto);
      
      boolean check = false;
      
      if(loginDto != null) {
         session.setAttribute("login", loginDto);
         check = true;
      }
      
      Map<String, Boolean> map = new HashMap<String, Boolean>();
      map.put("check", check);
      
      return map;
      
   }
   
   
   @RequestMapping(value = "/USER_logout.do", method = RequestMethod.GET)
   public String logout(HttpSession session) {
      logger.info("logout");
      
      session.invalidate();
      
      return "MAIN/main";
   }
   
   
   @RequestMapping(value = "/USER_emailcheckpopup.do", method = RequestMethod.GET)
   public String emailpopup() {
      logger.info("이메일 인증 팝업!");
      return "MAIN/emailChk";
   }
   
   
   //이메일 전송 화면으로
   @RequestMapping(value="/USER_mailSend.do", method=RequestMethod.POST)
   public String mailSend(Model model, String EmailName) {
      logger.info("mailSend");
      model.addAttribute("EmailName",EmailName);
      return "MAIN/mailSend";
      }
   @RequestMapping(value = "/USER_detailRes.do", method = RequestMethod.POST)
   public String personal_insert(Model model, @ModelAttribute("totalDto") @Valid TotalDto dto, BindingResult result) {
	   if(result.hasErrors()) {
		   logger.info("유효성검사 실패");
		   logger.info(dto.getJoinname());
		   logger.info(dto.getCertificate());
		   logger.info(dto.getRegdate());
		   List<ObjectError> list = result.getAllErrors();
			for( ObjectError error : list ) {
				System.out.println(error);
			}
     
         return "USER/userDetail";
      }else {
    	  logger.info("유효성 검사 통과");
    	  logger.info(dto.getCertificate());
    	  logger.info(dto.getRegdate());
    	  int res = totalBiz.insert(dto);
    	  System.out.println(res);
    	  if(res>0) {
    		 
    		  return "redirect:index.jsp";
    	  }else {
    		  return "USER/userDetail";
    	  }
      }
   }
     
   
   @RequestMapping(value = "Address.do")
   public String address() {
	   
	   return "USER/userDetail_Address";
   }
   
   
    
}