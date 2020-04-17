package com.job.coverletter.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.job.coverletter.all.Pagination;
import com.job.coverletter.model.board.dto.BoardDto;
import com.job.coverletter.model.company.biz.CompanyBiz;
import com.job.coverletter.model.company.dto.CompanyDto;
import com.job.coverletter.model.joinUser.dto.JoinUserDto;
import com.job.coverletter.model.supportPay.biz.SupportPayBiz;
import com.job.coverletter.model.supportPay.dto.SupportPayDto;

@Controller
public class MainController {
	// 메인, 기업상세정보, 기업검색, 지도, 결제
	private Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private CompanyBiz companyBiz;

	@Autowired
	private SupportPayBiz supportpaybiz;

	@RequestMapping(value = "/MAIN_main.do", method = RequestMethod.GET)
	public String selectOne(Model model) {

		// 전체화면 20개
		List<CompanyDto> list_cnt20 = companyBiz.selectList_cnt20();
		model.addAttribute("list_cnt20", list_cnt20);

		// 웹 분야 4개
		List<CompanyDto> list_web = companyBiz.selectList_web();
		model.addAttribute("list_web", list_web);

		// 프론트 분야 4개
		List<CompanyDto> list_front = companyBiz.selectList_front();
		model.addAttribute("list_front", list_front);

		// 백 분야 4개
		List<CompanyDto> list_back = companyBiz.selectList_back();
		model.addAttribute("list_back", list_back);

		logger.info("Main go");
		return "MAIN/main";
	}

	/* 회사가 가지고있는 채용정보를 그룹넘버 불러 옴 */
	@RequestMapping(value = "/MAIN_mainDetail.do", method = RequestMethod.GET)
	public String selectOne(Model model, int companyseq) {

		CompanyDto selectOne = companyBiz.selectOne(companyseq);
		List<CompanyDto> selectAll_group = companyBiz.selectAll_group(selectOne.getGroupno());

		model.addAttribute("mainDetail", selectOne);
		model.addAttribute("selectAll_group", selectAll_group);

		return "MAIN/mainDetail";

	}

	@RequestMapping(value = "/MAIN_kakaomap.do", method = RequestMethod.GET)
	public String kakaomap(Model model, int companyseq) {

		CompanyDto kakaomap_selectOne = companyBiz.selectOne(companyseq);

		model.addAttribute("kakaomap_selectOne", kakaomap_selectOne);

		return "MAIN/kakaomap";

	}

	@RequestMapping(value = "/MAIM_mainAjax.do", method = RequestMethod.GET)
	public String mainAjax(Model model) {
		
		List<CompanyDto> mianAjax = new ArrayList<CompanyDto>();

		return "MAIN/main";

	}

	/*-------------------------후원하기-------------------------*/
	@RequestMapping(value = "/MAIN_pay.do")
	public String pay(Model model , HttpSession session) {
		JoinUserDto userDto = (JoinUserDto) session.getAttribute("login");
		String joinemail = userDto.getJoinemail();
		String joinname = userDto.getJoinname();
		
		model.addAttribute("joinname", joinname);

		return "PAY/pay";
	}

	@RequestMapping(value="MAIN_payReady.do")
	public String readyPay(Model model , HttpServletRequest request , int quantity , HttpSession session) throws IOException {
		JoinUserDto userDto = (JoinUserDto) session.getAttribute("login");
		String joinemail = userDto.getJoinemail();
		
		URL url = new URL("https://kapi.kakao.com/v1/payment/ready");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "KakaoAK " + "99105b4cd74b45dac2f9db4fd4183eb1");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		connection.setDoInput(true);
		connection.setDoOutput(true);

		String partner_order_id = "coverletter";
		String partner_user_id = joinemail;
		System.out.println(partner_user_id);

		// 아이템
		String Donation = "후원";

		// 수량
		// int quantity = Integer.parseInt(request.getParameter("quantity"));

		// 부가세
		int tax_free_amount = (int) (quantity * 0.1);

		// 가격
		int total_amount = (quantity - tax_free_amount);
		System.out.println("총 가격 : " + total_amount);

		Map<String, Object> payment = new HashMap<String, Object>();
		payment.put("cid", "TC0ONETIME");
		payment.put("partner_order_id", partner_order_id);
		payment.put("partner_user_id", partner_user_id);
		payment.put("item_name", Donation);
		payment.put("quantity", quantity);
		payment.put("total_amount", total_amount);
		payment.put("tax_free_amount", tax_free_amount);
		payment.put("approval_url", "http://localhost:8787/coverletter/approval.jsp");
		payment.put("cancel_url", "http://localhost:8787/coverletter/cancel.jsp");
		payment.put("fail_url", "http://localhost:8787/coverletter/fail.jsp");

		String string = new String();
		for (Entry<String, Object> elem : payment.entrySet()) {
			string += (elem.getKey() + "=" + elem.getValue() + "&");
		}

		connection.getOutputStream().write(string.getBytes());

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		JSONParser parser = new JSONParser();
		System.out.println(parser + "parser");

		try {

			JSONObject tmp = (JSONObject) parser.parse(in);
			System.out.println(tmp.get("tid"));

			String tid = (String) tmp.get("tid");
			session.setAttribute("tid", tid);

			model.addAttribute("kakao", tmp);
		} catch (Exception e) {
			System.out.println(e + "jalsdfjlsadj");
			e.printStackTrace();
		}
		return "PAY/payment";
	}

	@RequestMapping(value = "MAIN_payDetail.do")
	public String payDetail(Model model, String tid, HttpServletRequest request) throws IOException {

		String cid = "TC0ONETIME";
		URL url = new URL("https://kapi.kakao.com/v1/payment/order");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "KakaoAK " + "99105b4cd74b45dac2f9db4fd4183eb1");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		connection.setDoInput(true);
		connection.setDoOutput(true);

		Map<String, Object> payment = new HashMap<String, Object>();
		payment.put("cid", cid);
		payment.put("tid", tid);

		String string = new String();
		for (Entry<String, Object> elem : payment.entrySet()) {
			string += (elem.getKey() + "=" + elem.getValue() + "&");

		}

		connection.getOutputStream().write(string.getBytes());
		System.out.println(string + " : string");

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		System.out.println("in : " + in);

		JSONParser parser = new JSONParser();
		System.out.println(parser + " : parser");
		SupportPayDto dto = new SupportPayDto();

		try {

			JSONObject tmp = (JSONObject) parser.parse(in);
			System.out.println(tmp);

			String tmp_tid = (String) tmp.get("tid");
			String partner_order_id = (String) tmp.get("partner_order_id");
			String partner_user_id = (String) tmp.get("partner_user_id");
			String payment_method_type = (String) tmp.get("payment_method_type");
			JSONObject json_amount = (JSONObject) tmp.get("amount");

			// String amount = (String)String.valueOf(json_amount);

			String amount_total = (String) String.valueOf(json_amount.get("total"));
			String amount_tax_free = (String) String.valueOf(json_amount.get("tax_free"));
			String item_name = (String) tmp.get("item_name");
			String quantity = (String) String.valueOf(tmp.get("quantity"));

			// 2020-03-21 21:32:23
			DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String stringCreatedAt = ((String) tmp.get("created_at")).replace("T", " ");

			Date created_at = sdFormat.parse(stringCreatedAt);

			dto.setTid(tmp_tid);
			dto.setCid(cid);
			dto.setPartner_order_id(partner_order_id);
			dto.setjoinemail(partner_user_id);
			dto.setPayment_method_type(payment_method_type);
			// dto.setAmount(amount);
			dto.setAmount_total(amount_total);
			dto.setAmount_tax_free(amount_tax_free);
			dto.setItem_name(item_name);
			dto.setQuantity(quantity);
			dto.setCreated_at(created_at);

		} catch (Exception e) {
			System.out.println(e + "jalsdfjlsadj");
			e.printStackTrace();
		}
		supportpaybiz.payInsert(dto); // db에 값 저장
		model.addAttribute("dto", supportpaybiz.payList(dto));
		return "redirect:/PAY_payList.do";
	}

	@RequestMapping(value = "/PAY_payList.do")
	public String payList(@ModelAttribute("SupportPayDto") SupportPayDto dto, @RequestParam(defaultValue = "1") int curPage,
			HttpServletRequest request, Model model , HttpSession session) {
		JoinUserDto userDto = (JoinUserDto) session.getAttribute("login");
		String joinemail = userDto.getJoinemail();
		// 총 게시글 수
		int listCnt = supportpaybiz.payListCount(dto);
		dto.setjoinemail(joinemail);

		// 페이징 (시작글번호, 표시될 게시글) : 연산해서 쿼리문에 사용
		Pagination pagination = new Pagination(listCnt, curPage);
		dto.setStartIndex(pagination.getStartIndex());
		dto.setCntPerPage(pagination.getPageSize() * curPage);

		List<SupportPayDto> list = supportpaybiz.payList(dto);

		model.addAttribute("boardList", list);
		model.addAttribute("listCnt", listCnt);
		model.addAttribute("pagination", pagination);
		return "PAY/payList";
	}

}
