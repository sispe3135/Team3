var idx = 1;

/*---------- cv-container 복제 : add func ----------*/
function add() {
	// 마지막 cv-container를 복제
	var lastCVcontainer = $('.cv-container').last();
	var cloneElements = $(lastCVcontainer).clone(false);

	// 마지막 cv-container에 추가
	lastCVcontainer.after(cloneElements);

	// CLONE : 소제목 영역, 작성영역 clear
	cloneElements.find('input[type=text]').val('');
	cloneElements.find('textarea').val('');
	cloneElements.find('.cv-spell').text('');
	cloneElements.find('#cntArea-a').text('0');
	cloneElements.find('#cntArea-b').text('0');
	
	// path 
	cloneElements.find('input[id=title]').attr('name', 'targets[' + idx + '].title');
	console.log('되고잇나..' + idx);
	cloneElements.find('input[id=subtitle]').attr('name', 'targets[' + idx + '].subtitle');
	cloneElements.find('select[id=question]').attr('name', 'targets[' + idx + '].question');
	cloneElements.find('textarea[id=content]').attr('name', 'targets[' + idx + '].content');
	
	idx++;
}

/*---------- cv-container 삭제 : remove func ----------*/
function remove(btn) {
	// 누른 버튼의 상위 cv-container 찾아서 삭제
	var btn = btn;
	var thisElements = $(btn).parents('.cv-container');
	thisElements.remove();
}

/*---------- 글자수 세기 : contentCnt func ----------*/
function contentCnt(textarea) {
	var thisElement = textarea;
	var cntRes = $(thisElement).val();
	var parent = $(thisElement).parents('.cv-container');

	// 공백포함
	var cntAreaA = $(parent).find('#cntArea-a');
	$(cntAreaA).text(cntRes.length);

	// 공백제거
	var cntAreaB = $(parent).find('#cntArea-b');
	var cntResTrim = cntRes.replace(/ /g, '');
	$(cntAreaB).text(cntResTrim.length);

}

/*---------- 맞춤법 검사 : spellCheck func ----------*/
function spellCheck(btn) {
	// 보낼 데이터 (맞춤법 검사할 데이터)
	var thisElement = btn;
	var parent = $(thisElement).parents('.cv-container');
	var ta = $(parent).find('textarea');
	var sd = $(parent).find('.cv-spell');

	// node server로 보내기
	$.ajax({
		type : 'post',
		url : 'http://127.0.0.1:3003/spellCheck/',
		data : ta.val(),
		crossOrigin : true,
	    crossDomain : true,
		async : false,
		dataType: 'json',
		success : function(data) {
			console.log('통신성공');
			console.log('넘어오는 데이터 : ' + data);
			
			// data = json 
			$.each(data, function(idx, obj) {
				$.each(obj, function(key, value) {
					console.log('key = ' + key);
					console.log('val = ' + value);
					/*
					 * -- KEY --
					 * token : 오류
					 * suggestions : 교정어
					 * info : 맞춤법 검사 결과 설명
					 * 
					 * */
					if(key == 'token'){
						
					}
					
					if(key == 'suggestions'){
						// button 생성
						sd.append
							("<button class='btn spell-btn' type='button'>"
							+ value
							+ "</button>");
					}
					
				});
			 });
			
		},
		error : function(request, status, error) {
			console.log('통신실패');
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}

	});
};











