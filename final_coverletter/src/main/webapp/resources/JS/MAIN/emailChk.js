/*부모 veiw에게 값 전달*/
function EmailChk() {

   console.log(opener.window.document.getElementsByName("joinemail")[0]);
   opener.window.document.getElementsByName("joinemail")[0].value = document.getElementById("EmailID").value;
   window.close();
}
function EmailChk2(RandomNumber) {
   if (RandomNumber == document.getElementById("EmailCode").value) {
      alert("인증되었습니다.");
      $("#chk").show();
      $("#chk2").hide();
      $("#EmailCode").hide();
   } else if (RandomNumber != document.getElementById("EmailCode").value) {
      alert("인증번호를 확인하시오.");
   }
}


function validate() {
	var re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	var email = document.getElementById("emailCheck");
	if (email.value == "") {
		alert("이메일을 입력해 주세요");
		email.focus();
		return false;
	}
	if (!re2.test(email.value)) {
		alert("이메일 형식이 아닙니다.");
		return false;
	} else {
		$.ajax({
			url:"USER_emailcheck.do",
			type:"post",
			data:{
				joinemail:email.value
			},
			dataType:"text",
			success: function(res){
				if(res === "중복"){
					$("#errormessage").text("이미 가입된 이메일 입니다.");
					$("#errormessage").css("color", "#DF7401");
					$("#errormessage").css("font-size", "8pt");
					
					
					return false;
				} else if(res == "사용가능"){
					$("#errormessage").text("가입 가능한 이메일 입니다.");
					$("#errormessage").css("color", "#DF7401");
					$("#errormessage").css("font-size", "8pt");
					
					$("#feilde").hide();
					$("#feildeID").show();
					document.getElementById("EmailID").value = document
							.getElementById("emailCheck").value;
				}
			},
			error: function(){
				alert("통신 실패");
			}
		})
	}

}
