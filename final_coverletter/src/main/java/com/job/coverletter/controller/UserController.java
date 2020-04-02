package com.job.coverletter.controller;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.google.gson.JsonObject;
import com.job.coverletter.all.Pagination;
import com.job.coverletter.model.coverletter.biz.CoverLetterBiz;
import com.job.coverletter.model.coverletter.dto.CoverLetterDto;
import com.job.coverletter.model.jobcalendar.biz.JobCalendarBiz;
import com.job.coverletter.model.jobcalendar.dto.JobCalendarDto;
import com.job.coverletter.model.joinUser.biz.JoinUserBiz;
import com.job.coverletter.model.joinUser.dto.JoinUserDto;
import com.job.coverletter.model.school.dto.SchoolDto;
import com.job.coverletter.model.total.biz.TotalBiz;
import com.job.coverletter.model.total.dto.TotalDto;
import com.job.coverletter.model.skill.biz.SkillBiz;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class UserController {
   // 로그인, 회원가입, 마이페이지, 이력작성, 캘린더, 관심공고, 비번 
   private Logger logger = LoggerFactory.getLogger(UserController.class);
   
   @Autowired
   private JoinUserBiz joinUserBiz;
   
   @Autowired
   private TotalBiz totalBiz;
   
   @Autowired
   private JobCalendarBiz jobCalendarBiz;

	String cvcategory = "";
	String joinemail = "USER@GMAIL.COM";

	@Autowired
	private CoverLetterBiz coverletterBiz;

	@Autowired
	private SkillBiz skillBiz;

	// 로그인 기능 완성되면 로그인 세션에 있는 아이디로 바꿔야됨
	String login = "cv@email.net";
   
//   //마이페이지
//   @RequestMapping(value="/USER_userMain.do", method=RequestMethod.GET)
//   public String userMain() {
//      logger.info("userMain go");
//      
//      
//      return "USER/userMain";
//   }
   

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
         
   //PFwrite page go 
   @RequestMapping(value = "/USER_userPFwrite.do", method = RequestMethod.GET)
   public String PFwrite() {
	   logger.info("PRwrite go");
	   
	   return "USER/userPFwrite";
   }
   
	// 마이페이지
	@RequestMapping(value = "/USER_userMain.do", method = RequestMethod.GET)
	public String userMain(Model model) {
		logger.info("userMain go");
		
		CoverLetterDto cvdto = new CoverLetterDto();
		cvdto.setCvcategory("자소서");
		cvdto.setJoinemail(joinemail);
		int cvlist = coverletterBiz.boardCVListCount(cvdto);
		System.out.println("cvlist : "+cvlist);
		CoverLetterDto pfdto = new CoverLetterDto();
		pfdto.setCvcategory("포폴");
		pfdto.setJoinemail(joinemail);
		int pflist = coverletterBiz.boardPFListCount(pfdto);
		System.out.println("pflist : "+pflist);
		JobCalendarDto jbdto = new JobCalendarDto();
		jbdto.setJoinemail(joinemail);
		int jblist = jobCalendarBiz.boardJobListCount(jbdto);
		System.out.println("jblist : "+jblist);
		
		model.addAttribute("cvlist",cvlist);
		model.addAttribute("pflist",pflist);
		model.addAttribute("jblist",jblist);

		// IT역량 차트
		JSONArray itSkill = skillBiz.selectItSkill();
		model.addAttribute("itSkill", itSkill);

		// 스펙 차트
		JSONArray mySkill = skillBiz.selectMySkill();
		model.addAttribute("mySkill", mySkill);

		return "USER/userMain";
	}

//	@RequestMapping(value = "/USER_userDetail.do", method = RequestMethod.GET)
//	public String userDetail() {
//		logger.info("userDetail go");
//
//		return "USER/userDetail";
//	}
   
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
  
 //sns로그인
   @RequestMapping (value = "/USER_snslogin.do", method = RequestMethod.POST)
   public String snslogin(HttpSession session, JoinUserDto dto) {
	   logger.info("sns로그인");
	   logger.info("=========dto: "+ dto.getJoinemail());

	   JoinUserDto onelogin = joinUserBiz.selectOne(dto.getJoinemail());
	   logger.info("******onelogin: "+onelogin);
	   
	   JoinUserDto snslogin = joinUserBiz.login(dto);
	   logger.info("login:"+snslogin);
	   
	   if(onelogin != null ) {
		   session.setAttribute("snslogin", snslogin);
		   return "MAIN/main";
		   
	   }else  {
		   int snsjoin = joinUserBiz.insertUser(dto);
		   if(snsjoin > 0) {
			   session.setAttribute("snslogin", snslogin);
			   return "MAIN/main";
		   }else {
			   return "MAIN/login";
		   }
	   }
   }
   
   
   
   //아이디비밀번호찾기(비밀번호 수정)
   @RequestMapping(value = "/USER_changepw.do", method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
   public String findidpw(Model model, JoinUserDto dto) {
	   logger.info("아이디 비밀번호 찾기"+ dto);
	   int res = joinUserBiz.updateJoinuser(dto);
	   
	   if(res >0) {
		   logger.info("비밀번호 변경 성공");
	         return "MAIN/login";
	   }else {
		   logger.info("비밀번호 변경 실패");
		   model.addAttribute("joinuserDto", dto);
		   return "MAIN/login";
	   }
	   
	   
   }
   
   //로그아웃
   @RequestMapping(value = "/USER_logout.do", method = RequestMethod.GET)
   public String logout(HttpSession session) {
      logger.info("logout");
      
      session.invalidate();
      
      return "MAIN/main";
   }
   
   
   @RequestMapping(value = "/USER_emailcheckpopup.do", method = RequestMethod.GET)
   public String emailpopup() {
      logger.info("회원가입 이메일 인증 팝업!");
      return "MAIN/emailChk";
   }
   
//   @RequestMapping(value = "/USER_emailcheckpopup_login.do", method = RequestMethod.GET)
//   public String emailpopup_login() {
//      logger.info("아이디찾기 이메일 인증 팝업!");
//      return "MAIN/emailChk_login";
//   }
   
   
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
     
   // 메핑에 USER 넣어주세요
   @RequestMapping(value = "/Address.do")
   public String address() {
	   
	   return "USER/userDetail_Address";
   }
   
	/*--------------------------------- 형권, 박하 : 게시판 ----------------------------------------------------------------------------------------------------*/

	// 글목록(페이징기능) , 이력서(자기소개서) 게시판
	@RequestMapping(value = "/USER_userCVList.do")
	public String boardListCV(@ModelAttribute("CoverLetterDto") CoverLetterDto dto,
			@RequestParam(defaultValue = "1") int curPage, HttpServletRequest request, Model model) {
		cvcategory = "자소서";

		dto.setCvcategory(cvcategory);
		dto.setJoinemail(joinemail);

		// 총 게시글 수
		int listCnt = coverletterBiz.boardCVListCount(dto);

		// 페이징 (시작글번호, 표시될 게시글) : 연산해서 쿼리문에 사용
		Pagination pagination = new Pagination(listCnt, curPage);
		dto.setStartIndex(pagination.getStartIndex());
		dto.setCntPerPage(pagination.getPageSize() * curPage);

		List<CoverLetterDto> list = coverletterBiz.boardCVList(dto);

		model.addAttribute("boardList", list);
		model.addAttribute("listCnt", listCnt);
		model.addAttribute("pagination", pagination);

		return "USER/userCVdown";
	}

	// 글목록(페이징기능) , 포트폴리오 게시판
	@RequestMapping(value = "/USER_userPFList.do")
	public String boardListPF(@ModelAttribute("CoverLetterDto") CoverLetterDto dto,
			@RequestParam(defaultValue = "1") int curPage, HttpServletRequest request, Model model) {
		cvcategory = "포폴";

		dto.setCvcategory(cvcategory);
		dto.setJoinemail(joinemail);

		// 총 게시글 수
		int listCnt = coverletterBiz.boardPFListCount(dto);

		// 페이징 (시작글번호, 표시될 게시글) : 연산해서 쿼리문에 사용
		Pagination pagination = new Pagination(listCnt, curPage);
		dto.setStartIndex(pagination.getStartIndex());
		dto.setCntPerPage(pagination.getPageSize() * curPage);
		List<CoverLetterDto> list = coverletterBiz.boardPFList(dto);

		model.addAttribute("boardList", list);
		model.addAttribute("listCnt", listCnt);
		model.addAttribute("pagination", pagination);

		return "USER/userPFdown";
	}

	@RequestMapping(value = "/USER_userCVdelete.do", method = RequestMethod.POST)
	public String boardCVDelete(@RequestParam(name = "chk") String[] seq) {

		coverletterBiz.CVdelete(seq);

		return "redirect:/USER_userCVList.do";
	}

	@RequestMapping(value = "/USER_userPFdelete.do", method = RequestMethod.POST)
	public String boardPFDelete(@RequestParam(name = "chk") String[] seq) {

		coverletterBiz.PFdelete(seq);

		return "redirect:/USER_userPFList.do";
	}

	@RequestMapping(value = "/USER_userJobList.do")
	public String BoardJobList(@ModelAttribute("JobCalendarDto") JobCalendarDto dto,
			@RequestParam(defaultValue = "1") int curPage, HttpServletRequest request, Model model) {
		joinemail = "USER@GMAIL.COM";
		dto.setJoinemail(joinemail);
		int listCnt = jobCalendarBiz.boardJobListCount(dto);

		// 페이징 (시작글번호, 표시될 게시글) : 연산해서 쿼리문에 사용
		Pagination pagination = new Pagination(listCnt, curPage);
		dto.setStartIndex(pagination.getStartIndex());
		dto.setCntPerPage(pagination.getPageSize() * curPage);
		List<JobCalendarDto> list = jobCalendarBiz.boardJobList(dto);

		model.addAttribute("boardList", list);
		model.addAttribute("listCnt", listCnt);
		model.addAttribute("pagination", pagination);

		return "USER/userJob";
	}
	
	// 이력서(자기소개서) 파일 다운로드
	@RequestMapping(value = "/CVdownload.do", method = RequestMethod.POST)
	@ResponseBody
	public byte[] CVfileDownload(HttpServletRequest request, HttpServletResponse response, String name) {
		// 연속적인 바이트들의 흐름 : byte[]
		byte[] down = null;
		String path;

		try {
			path = WebUtils.getRealPath(request.getSession().getServletContext(), "/storage");

			File file = new File(path + "/" + name);

			down = FileCopyUtils.copyToByteArray(file);
			String filename = new String(file.getName().getBytes(), "8859_1");

			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			response.setContentLength(down.length);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return down;
	}

	// 포트폴리오 파일 다운로드
	@RequestMapping(value = "/PFdownload.do", method = RequestMethod.POST)
	@ResponseBody
	public byte[] PFfileDownload(HttpServletRequest request, HttpServletResponse response, String name) {
		// 연속적인 바이트들의 흐름 : byte[]
		byte[] down = null;
		String path;

		try {
			path = WebUtils.getRealPath(request.getSession().getServletContext(), "/storage");

			File file = new File(path + "/" + name);

			down = FileCopyUtils.copyToByteArray(file);
			String filename = new String(file.getName().getBytes(), "8859_1");

			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			response.setContentLength(down.length);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	
		return down;
	}

	/*------------------------ 박하 : 취업센터 --------------------------*/
	// 자기소개서 작성 페이지
	@RequestMapping(value = "USER_userCVwriteForm.do")
	public String CVWriteForm() {
		return "USER/userCVwrite";
	}
	
	// 자기소개서 INSERT 
	@RequestMapping(value = "USER_userCVinsert.do", method = RequestMethod.POST)
	public String CVWriteInsert(HttpServletRequest request) {
		//Map<String, String> map = new HashMap<String, String>();
		String title = request.getParameter("title");
		String subtitle = request.getParameter("subtitle");
		String content = request.getParameter("content");
		
		List<String> list = new ArrayList<String>();
		list.add(title);
		list.add(subtitle);
		list.add(content);
		System.out.println(list);
		
	
		return "USER/userCVwrite";
	}
}
