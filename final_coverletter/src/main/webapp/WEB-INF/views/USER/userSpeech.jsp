<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스피치 연습</title>
<!-- include JQeury/CSS/JS -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/JS/jquery-3.4.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/JS/USER/userSpeech.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/CSS/USER/userSpeech.css"
	rel="stylesheet">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/annyang/2.6.0/annyang.min.js"></script>
</head>
<body>
	<%@ include file="../ALL/header_login.jsp"%>


	<div class="container">
		<h1>스피치 연습</h1>
		<div id="original">
			<div class="main">
				<div class="side"></div>
				<div class="center">

					<div class="internal">
						<fieldset class="fieldset">
							<legend class="legend">대본</legend>
							<textarea id="korea" rows="15" cols="40"></textarea>
						</fieldset>
					</div>

					<div class="internal_bewteen"></div>

					<div class="internal">
						<fieldset class="fieldset">
							<legend class="legend">연습</legend>
							<textarea id="pract" rows="15" cols="40"></textarea>
						</fieldset>
					</div>

				</div>
				<div class="side"></div>
			</div>

			<div id="blank"></div>

			<div class="main">
				<div class="side"></div>
				<div class="center">

					<div class="internal">
						<input type="button" value="대본녹음" class="speech" id="speech" onclick="speech_to_text()" />
					</div>
					<div class="internal_bewteen"><button id="stop" onclick="stop()">Stop</button></div>
					<div class="internal">
						<input type="button" value="연습녹음" class="speech" onclick="pract_speech_to_text()"  />
					</div>

				</div>
				<div class="side"></div>
			</div>
		</div>

		<div id="cloneDiv"></div>

		<div id="custom_button2">
			<button class="button2" onclick="add_div();">
				<img alt=""
					src="${pageContext.request.contextPath}/resources/IMG/button.png">
			</button>
			<button class="button2" id="button2" onclick="remove_div(this.id);">
				<img alt=""
					src="${pageContext.request.contextPath}/resources/IMG/minus.png">
			</button>
		</div>
	</div>
	
	
	

</body>
</html>