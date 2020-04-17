<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/JS/jquery-3.4.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/JS/PAY/pay.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/CSS/PAY/pay.css"
	rel="stylesheet">
</head>
<body>
<!-- 	<input type="text" id="quan		tity" onkeydown="onlyNumber(this);"/> -->
	
	<h3>${joinname}님의 후원</h3>
	<input type="hidden" id="joinemail" value="${joinemail}"/>
	<label>후원금액: 										<!-- 숫자만 입력가능 -->
	<input type="text" id="quantity" style = "ime-mode:disabled"/>
	</label>
	
	<input type="button" id="paybtn" value="결제하기"/>
	
	<div id = "chkQuantity"></div>
	
</body>
</html>