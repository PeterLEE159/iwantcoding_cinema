$(function() {
	
	
	
	(function() {
		window['moment-range'].extendMoment(moment);
//		$('body').css({
//			'min-width': $(window).width()+'px',
//		});
		
		// 각국 언어 버튼 클릭
		$('.btn-flag').click(function() {
			
			var lang = $(this).attr('lang');
			var href = location.href;
			
			href = href.replace('?lang=en', '?');
			href = href.replace('?lang=ko', '?');
			href = href.replace('?lang=en', '?');
			href = href.replace('?lang=ko', '?');
			href = href.replace('?lang=', '?');
			href = href.replace('?lang=', '?');
			href = href.replace('&lang=ko', '');
			href = href.replace('&lang=en', '');
			href = href.replace('&lang=', '');
			href = href.replace('&lang=', '');
			
			href = href.replace('&login=deny', '');
			href = href.replace('&login=fail', '');
			href = href.replace('&login=retry', '');
			href = href.replace('&login=signup', '');
			href = href.replace('login=deny', '');
			href = href.replace('login=fail', '');
			href = href.replace('login=retry', '');
			href = href.replace('login=signup', '');
			
			
			href = href + (href.indexOf("?") > -1 ? "&" : "?") + "lang=" + lang;
			location.href = href;
		});
		
		// login 폼이 닫힐 때 모든 데이터를 지운다
		$('#div-login').on('hidden.bs.modal', function () {
			$this = $(this);
			$this.find(':input').val('');
			$this.find('h4.font-alert').text('');
		})
		// 인풋 파일업로드 버튼
		$('.btn-input-file').each(function(index, input) {
			var $this = $(this),
			id = $this.attr('id');
			
			if(!id) {
				id = 'btn-file-input-label-'+index;
				$this.attr('id', id);
			}
			
			$this.after("<label for='"+id+"' class='btn btn-default'><i class='fa fa-upload fa-fw'></i> "+$this.attr('content')+"</label>");
		});
		
		
		
		
		// 인풋박스 스타일
		$('.btn-input').each(function(index, input) {
			var $this = $(this),
			id = $this.attr('id');
			
			if(!id) {
				id = 'btn-input-label-'+index;
				$this.attr('id', id);
			}
			
			$this.after("<label for='"+id+"'>"+$this.attr('content')+"</label>");
		});
		
		// 인풋박스 스타일
		$('.btn-input-list').each(function(index, input) {
			var $this = $(this),
			id = $this.attr('id');
			
			if(!id) {
				id = 'btn-input-list-label-'+index;
				$this.attr('id', id);
			}
			$label = $("<label for='"+id+"'>"+$this.attr('content')+"</label>");
			$this.after($label);
			if($this.hasClass('hover'))
				$label.addClass('hover');
		});
		
		
		// 레이팅 바
		var $rts = $('.rating');
		if($rts.length > 0) {
			$.each($rts, function(index, $rating) {
				$rating = $($rating);
				hover = $rating.attr('onmouseover'),
				click = $rating.attr('onclick'),
				name = $rating.attr('name'),
				value = parseInt($rating.attr('value')),
				htmlContent = "",
				i = 0;
				$rating.attr({
					'onmouseover': '',
					'onclick': '',
					'value': ''
				});
				if(hover && click) { 	// 호버 레이팅바
					
					if(hover && click)
						for(;i<5;i++)
							htmlContent += "<span class='star star-hover"+(value >= 5-i ? " filled" : "")+"' onmouseover='innrStarHover(this, "+hover+")' onclick='innrStarClick(this, "+click+")'></span>";
					else if (hover)
						for(;i<5;i++)
							htmlContent += "<span class='star star-hover"+(value >= 5-i ? " filled" : "")+"' onmouseover='innrStarHover(this, "+hover+")'></span>";
					else if(click) 
						for(;i<5;i++)
							htmlContent += "<span class='star star-hover"+(value >= 5-i ? " filled" : "")+"' onclick='innrStarClick(this, "+click+")'></span>";

					htmlContent += "<input type='hidden' name='"+name+"' value='"+value+"'>";
					
				} else {				// 정적인 레이팅바
					for(;i<5;i++)
						htmlContent += "<span class='star"+(value >= 5-i ? " filled" : "")+"'></span>";
				}
				
				$rating.prepend(htmlContent);
			});
		}
		
		
		
		// 로딩바
		var $loadingDiv = $('#div-loading'),
		winHeight = document.body.clientHeight,
		winWidth = document.body.clientWidth;
		
		$loadingDiv.height(winHeight);
		$loadingDiv.width(winWidth);
		
		$loadingImg = $loadingDiv.find('img');
		$loadingImg.css({top: winHeight / 2 - 100, left: winWidth / 2 - 100});
		
		
		$('.btn-forward').click(function() {
			pageForwardAnim();
		});
	})();
	
});



// 페이지 변경 애니메이션
function pageForwardAnim() {
	$div = $('div#div-loading');
	$('div#div-loading').css({'display': 'block', 'background': '#fff'})
		.find('img')
		.attr('src', '/resources/images/page_loading.gif');
}

function loadingAnim() {
	
	$('div#div-loading').css({'display': 'block', 'background': 'rgba(0, 0, 0, 0.3)'})
		.find('img')
		.attr('src', '/resources/images/ajax_loading.gif');
}
function finishLoadingAnim() {
	$div = $('div#div-loading');
	$('div#div-loading').css({'display': 'none'})
		.find('img')
		.attr('src', '');
}

function numberWithCommas(number) {
    var parts = number.toString().split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return parts.join(".");
}


function innrStarHover(span, onmouseover) {
	var $hoverStar = $(span),
	value = $hoverStar.index();
	onmouseover(5 - value);
	
}

function innrStarClick(span, onclick) {
	var $hoverStar = $(span),
	$parent = $hoverStar.parent(),
	$spanStar = $parent.find(':first-child'),
	$input = $parent.find('input'),
	currValue = parseInt($input.attr('value')),
	value = $hoverStar.index(),
	i = 0;

	for(i; i< 5 ; i++) {
		if(i >= value) $spanStar.addClass('filled');
		else $spanStar.removeClass('filled');
		$spanStar = $spanStar.next();
	}

	onclick(5 - value);
	$input.attr('value', 5 - value);
	
}



/*
 * 이미지 드랍다운
 */
var Images = (function() {
	var map = {};
	var instance;
	var init = function() {
		return {
			getFiles: function() {
				return map;
			},
			setFiles: function(files) {
				map = files;
			}	
		}
	};
	
	return {
		getInstance: function() {
			if(!instance)
				instance = init();
			return instance;
		}
	}
	
})();

var divDropzone = $("#dropzone");


divDropzone.on('dragenter', function(e) {
	e.stopPropagation();
	e.preventDefault();

	$(this).css('border', '2px solid #5272A0');
});

divDropzone.on('dragleave', function(e) {
	e.stopPropagation();
	e.preventDefault();
	$(this).css('border', '2px dotted #8296C2');
});

divDropzone.on('dragover', function(e) {
	e.stopPropagation();
	e.preventDefault();
});

divDropzone.on('drop', function(e) {
	e.stopPropagation();
	e.preventDefault();
	
	$(this).css('border', '2px dotted #8296C2');
	var files = e.originalEvent.dataTransfer.files;
	
	if (files.length < 1)
		return;

	showFile(files);
});


function cutstring(string, length) {
	if(string == null) 
		return '';
	if(string.length < length)
		return string;
	
	return string.substring(0, length-4)+' ...';
}


function showFile(files) {
	var i=0, size = files.length;
	var $ul = $('#ul-image');
	for(; i< size; i ++) {
		var fileReader = new FileReader();
		fileReader.onload = function(file) {
			var html = "";
			html += "<li>";
			html +=	"	<div>";
			html += "		<img src='"+file.target.result+"'>";
			html += "	</div>";
			html += "</ul>";
			$ul.append(html);
		}
		
		fileReader.readAsDataURL(files[i]);
	}
	
	var images = Images.getInstance();
	images.setFiles(files);
}


function movieURI(uri) {
	return "/resources/images/movie/" + uri;
}
function customerURI(uri) {
	return "/resources/images/customer/" + uri;
}

var fpastMin = 60;
var fpastHour = fpastMin * 60;
var fpastDay = fpastHour * 24;
var fpastMonth = fpastDay * 30;
var fpastYear = fpastMonth * 12;


function calcPastTime(date, lang) {
	var pastSec = (new Date().getTime() - date) / 1000;
	if(pastSec < fpastMin) return pastSec.toFixed() + (lang == 'ko' ? ' 초 전' : ' seconds ago');
	else if (pastSec < fpastHour)  return (pastSec / fpastMin).toFixed() + (lang == 'ko' ? ' 분 전' : ' seconds ago');
	else if (pastSec < fpastDay)  return (pastSec / fpastHour).toFixed()+ (lang == 'ko' ? ' 시간 전' : ' minutes ago');
	else if (pastSec < fpastMonth)  return (pastSec / fpastDay).toFixed()+ (lang == 'ko' ? ' 일 전' : ' days ago');
	else if (pastSec < fpastYear)  return (pastSec / fpastMonth).toFixed()+ (lang == 'ko' ? ' 개월 전' : ' months ago');
	else return (pastSec / fpastYear).toFixed()+ (lang == 'ko' ? ' 년 전' : ' years ago');
}

function insertRating($rts) {
	if($rts.length > 0) {
		$.each($rts, function(index, $rating) {
			$rating = $($rating);
			hover = $rating.attr('onmouseover'),
			click = $rating.attr('onclick'),
			name = $rating.attr('name'),
			value = parseInt($rating.attr('value')),
			htmlContent = "",
			i = 0;
			$rating.attr({
				'onmouseover': '',
				'onclick': '',
				'value': ''
			});
			if(hover && click) { 	// 호버 레이팅바
				
				if(hover && click)
					for(;i<5;i++)
						htmlContent += "<span class='star star-hover"+(value >= 5-i ? " filled" : "")+"' onmouseover='innrStarHover(this, "+hover+")' onclick='innrStarClick(this, "+click+")'></span>";
				else if (hover)
					for(;i<5;i++)
						htmlContent += "<span class='star star-hover"+(value >= 5-i ? " filled" : "")+"' onmouseover='innrStarHover(this, "+hover+")'></span>";
				else if(click) 
					for(;i<5;i++)
						htmlContent += "<span class='star star-hover"+(value >= 5-i ? " filled" : "")+"' onclick='innrStarClick(this, "+click+")'></span>";

				htmlContent += "<input type='hidden' name='"+name+"' value='"+value+"'>";
				
			} else {				// 정적인 레이팅바
				for(;i<5;i++)
					htmlContent += "<span class='star"+(value >= 5-i ? " filled" : "")+"'></span>";
			}
			
			$rating.prepend(htmlContent);
		});
	}
}

function cutoff(str, length) {
	if(str.length > length)
		str = str.substring(0, length - 2) + ' ..';
	if(str == null)
		str = '';
	return str;
}

function isExpired(date) {
	var now = new Date();
	
	return now.getTime() > date;
}

$('#a-ajax').on('click', function() {
	var images = Images.getInstance().getFiles(),
	data = new FormData(),
	size = images.length, i =0;
	
	for(; i< size; i++) 
		data.append('file', images[i]);
	
	var url = "/upload.do";
    $.ajax({
       url: url,
       method: 'post',
       data: data,
       dataType: 'json',
       processData: false,
       contentType: false,
       success: function(res) {
           
       }
    });
});