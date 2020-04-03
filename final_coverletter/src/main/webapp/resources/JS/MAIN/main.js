

//Javascript
var count = 0;
//스크롤 바닥 감지
window.onscroll = function(e) {
    //추가되는 임시 콘텐츠
    //window height + window scrollY 값이 document height보다 클 경우,
    if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
    	//실행할 로직 (콘텐츠 추가)
        count++;
        
       // var seq = document.getElementById("companyname").innerText;
      //  console.log(seq);
        var addContent = '<div class="col-md-12"><div class="col-md-3</div>+</div>';
  
        //article에 추가되는 콘텐츠를 append
        $('article').append(addContent);
        
        
      
    }
};

//Javascript
var lastScrollTop = 0;
var delta = 5;
var fixBoxHeight = fixBox.offsetHeight;
var didScroll;
//스크롤 이벤트 
window.onscroll = function(e) {
    didScroll = true;
};

//0.25초마다 스크롤 여부 체크하여 스크롤 중이면 hasScrolled() 호출
setInterval(function(){
    if(didScroll){
        hasScrolled();
        didScroll = false;
    }
}, 25000);

function hasScrolled(){
    var nowScrollTop = window.scrollY;
    if(Math.abs(lastScrollTop - nowScrollTop) <= delta){
        return;
    }
    if(nowScrollTop > lastScrollTop && nowScrollTop > fixBoxHeight){
        //Scroll down
        console.log('scroll down');
    }else{
        if(nowScrollTop + window.innerHeight < document.body.offsetHeight){
            //Scroll up
            console.log('scroll up');
        }
    }
    lastScrollTop = nowScrollTop;
}



 