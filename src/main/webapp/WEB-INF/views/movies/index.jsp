<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<html>
<head>
	<title>Home</title>
	<%@ include file="/WEB-INF/views/common/head.jsp" %>
</head>
<body>
	<c:set var="nav_active" value="movie" />
	<%@ include file="/WEB-INF/views/common/nav.jsp" %>
	<%@ include file="/WEB-INF/views/common/common_views.jsp" %>
	
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<h2><spring:message code="genre.select" /></h2>
			</div>
			<div class="col-md-5 col-md-offset-1">
				<h2><spring:message code="age.limit" /></h2>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6" id="div-genre">
				<input id="input-all-genre" type="checkbox" class="btn-input" content="<spring:message code="genre.all" />" ${empty param.genre ? 'checked' : ''}/>
				<c:forEach var="genre" items="${genres }">
					<input type="checkbox" boundform="form-search" class="btn-input genre" content="${genre.type }" name="genre" value="${genre.genreId }"
						<c:forEach var="selectedGenre" items="${paramValues.genre }">
							<c:if test="${ selectedGenre == genre.genreId}">
								checked
							</c:if>
						</c:forEach>
					/>
				</c:forEach>
			<c:forEach var="genreId" items="${paramValues.genre }">
				<input type="hidden" class="input-hidden-genre" value="${genreId }"/>
			</c:forEach>
			</div>
			<div class="col-md-5 col-md-offset-1">
				<input type="radio" class="btn-input genre" boundform="form-search" content="<spring:message code="age.all" />" name="ageLimit" value="0" ${empty param.ageLimit ? 'checked' : '' }/>
				<input type="radio" class="btn-input genre" boundform="form-search" content="<spring:message code="12yrs" />" name="ageLimit" value="12" ${param.ageLimit eq 12 ? 'checked' : ''}/>
				<input type="radio" class="btn-input genre" boundform="form-search" content="<spring:message code="15yrs" />" name="ageLimit" value="15" ${param.ageLimit eq 15 ? 'checked' : ''}/>
				<input type="radio" class="btn-input genre" boundform="form-search" content="<spring:message code="18yrs" />" name="ageLimit" value="18" ${param.ageLimit eq 18 ? 'checked' : ''}/>
			</div>
		</div>
		
		<form action="/movie/index" class="form-horizontal" method="get" id="form-search">
			<input type="hidden" value="${param.page == null || param.page == '' ? 1 : param.page }" name="page"/>
			<div class="row top-margin">
				<div class="col-md-2 form-group">
					<select name="opt" class="form-control remove-right-radius remove-right-border">
						<option value="name" ${param.opt eq 'name' ? 'selected' : '' }><spring:message code="name"/></option>
						<option value="publish_country" ${param.opt eq 'publish_country' ? 'selected' : '' }><spring:message code="movie.publishCountry" /></option>
					</select>
				</div>
				<div class="col-md-6 form-group">
					<input name="keyword" type="text" class="form-control remove-radius remove-right-border" value="${param.keyword }" placeholder="<spring:message code="movie.search"/>">
				</div>
				<div class="col-md-2 form-group">
					<button id="btn-search" class="btn btn-default btn-forward remove-left-radius" ><i class="fa fa-search fa-fw"></i> <spring:message code="search" /></button>
				</div>
				<div class="col-md-2 text-center">
					<button id="btn-v" type="button" style="padding: 9px;" class="btn btn-default remove-radius active"><i class="fa fa-th"></i></button>
					<button id="btn-h" type="button" style="padding: 9px;" class="btn btn-default remove-radius"><i class="fa fa-list-ul"></i></button>
				</div>
		  	</div>
		</form>
		
		<section class="padding-top">
			<c:choose>
				<c:when test="${not empty movies }">
					<ul class="ul-grid" id="ul-movies">
						<c:forEach var="movie" items="${movies }">
							<li>
								<div class="div-image">
									<img class="full-img" src="${movie.getFullMovieURI(movie.images[0].uri) }"/>
									<div class="div-score">
										<img src="/resources/images/ribbon.png" />
										<p><fmt:formatNumber value="${movie.ratingPoint }" pattern="0.0"/></p>
									</div>
								</div>
								<div class="div-movie-content">
									
									<div class="row">
										<div class="col-md-4"><p class="font-bold"><spring:message code="title" /></p></div>
										<div class="col-md-8"><p>${movie.cutoffString(movie.name, 8) }</p></div>
									</div>
									<div class="row">
										<div class="col-md-4"><p class="font-bold"><spring:message code="country" /></p></div>
										<div class="col-md-8"><p>${movie.publishCountry}</p></div>
									</div>
									<div class="row">
										<div class="col-md-4"><p class="font-bold"><spring:message code="genre" /></p></div>
										<div class="col-md-8">
											<p>
												<c:forEach var="genre" items="${movie.genres }" varStatus="status">
													${genre.type }
												</c:forEach>
											</p>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
										<c:choose>
											<c:when test="${movie.isExpired(movie.common.closeDate) }">
												<p class="font-bold font-alert"><spring:message code="movie.closed" /></p>
											</c:when>
											<c:otherwise>
												<p class="font-bold font-alert">${movie.dateFormat(movie.common.closeDate) } <spring:message code="movie.closed" /></p>
											</c:otherwise>
										</c:choose>
										</div>
									</div>
								</div>
								<div class="div-movie-hover" mid="${movie.common.id }">
									<div>
										<a class="btn btn-primary btn-booking btn-forward ${movie.isExpired(movie.common.closeDate) ? 'disabled' : ''}" style="margin-top: 60%"><i class="fa fa-ticket fa-fw"></i>  <spring:message code="movie.index.booking" /></a>
									</div>
									<div>
										<a href="/movie/detail?mid=${movie.common.id }&lang=${param.lang}" class="btn btn-detail btn-success btn-forward" style="margin-top: 20%"><i class="fa fa-info fa-fw"></i>  <spring:message code="movie.index.detail" /></a>
									</div>
									<div></div>
								</div>
							</li>
						</c:forEach>
					</ul>
				</c:when>
				<c:otherwise>
					<h3 class="page-header"><spring:message code="movie.index.nosearchresult" /></h3>						
				</c:otherwise>
			</c:choose>
		</section>
	</div>
	
</body>
<%@ include file="/WEB-INF/views/common/scripts.jsp" %>

<script src="/resources/js/main.js"></script>
<script>
	$(function() {
		$inputPage = $('input[name=page]'),
		$inputAllGenre = $('#input-all-genre'),
		$movieWindow = $(window),
		$movieDocument = $(document),
		$ulMovies = $('#ul-movies'),
		$btnV = $('#btn-v'),
		$btnH = $('#btn-h'),
		isOnProcess = false,
		keyword = '${param.keyword}',
		opt = '${param.opt}',
		ageLimit = '${param.ageLimit}' !== '' ? parseInt('${param.ageLimit}') : 0,
		genres = [],
		fLang = '${param.lang}';
		
		
		
		
		$.each($('.input-hidden-genre'), function(index, $inputGenre) {
			$inputGenre = $($inputGenre);
			genres.push($inputGenre.val());
		});
		
		
		
		
		// 영화 수평으로 만들기
		$btnV.click(function() {
			if(!$ulMovies.hasClass('ul-grid')) {
				
				$ulMovies.removeClass('ul-h-movie')
					.addClass('ul-grid');
				
				$btnV.addClass('active');
				$btnH.removeClass('active');
			}
		});
		
		$btnH.click(function() {
			
			if(!$ulMovies.hasClass('ul-h-movie')) {
				
				$ulMovies.removeClass('ul-grid')
					.addClass('ul-h-movie');
				
				$btnH.addClass('active');
				$btnV.removeClass('active');
			}
		});
		
		// 예약버튼
		$('.btn-booking').click(function() {
			var mid = $(this).parent().parent().attr('mid');
			location.href = '/movie/booking?mid='+mid+"&lang=${param.lang}";
		});
		
		// 영화 상세정보
		$('.btn-detail').click(function() {
			var mid = $(this).parent().parent().attr('mid');
			location.href = '/movie/detail?id='+mid+"&lang=${param.lang}";
		});
		
		// 검색 버튼 클릭
		$('#btn-search').click(function(e) {
			
			var $form = $('#form-search');
			$inputPage.val(1);
			$("input[boundform='form-search']").each(function() {
				var $input = $(this);
				if($input.prop('checked')) {
					$hidden = $("<input type='hidden' />");
					$hidden.attr('name', $input.attr('name')).val($input.val());
					$form.append($hidden);
				}
			});
			$form.submit();
		});
		
		
		
		// 전체 장르 클릭 시
		$inputAllGenre.on('change', function() {
			if($inputAllGenre.prop('checked'))
				$('.genre').prop('checked', false);
		});
		
		
		// 각 장르 선택 시
		$('#div-genre').on('change', '.genre', function() {
			
			var $inputGenre = $('.genre'),
			size = $inputGenre.length;
			for(var i =0 ; i < size; i++) {
				
				if($inputGenre[i].checked) {
					$inputAllGenre.prop('checked', false);
					return;
				}
			}
			$inputAllGenre.prop('checked', true);
		});
		$('genre').change(function() {
			if($(this).checked) $inputAllGenre.checked = false;
		});
		
		
		$movieWindow.scroll(function() {
			var documentHeight = $movieDocument.height();
			var currHeight = $movieWindow.scrollTop();
			if(!isOnProcess && currHeight > documentHeight - 1000) {
				isOnProcess = true;
				readMovies();
			}
		});
		
		function readMovies() {
			var nextPage = parseInt($inputPage.val()) + 1;
			$inputPage.val(nextPage);
			
			data = {
				genre: genres,
				opt: opt,
				keyword: keyword,
				ageLimit: ageLimit == '' ? 0 : ageLimit,
				page: nextPage,
				lang: fLang
			};
			
			
			$.ajax({
				data: JSON.stringify(data),
				url: '/movie/ajaxmovie',
				method: 'post',
				contentType: 'application/json',
				dataType: 'json',
				success: function(movies) {
					var html = "",
					movieTitle = '<spring:message code="title" />',
					countryTitle = '<spring:message code="country" />',
					genreName = '<spring:message code="genre" />',
					bookingName = '<spring:message code="movie.index.booking" />',
					movieDetail = '<spring:message code="movie.index.detail" />',
					movieCloseDate = '<spring:message code="movie.closed" />';
					
					$.each(movies, function(index, movie) {
						html += "<li>";
						html += "<div class='div-image'>";
						html += "<img class='full-img' src='" + movieURI(movie.images[0].uri) + "'/>";
						html += "<div class='div-score'>";
						html += "<img src='/resources/images/ribbon.png' />";
						
						html += "<p>"+movie.ratingPoint.toFixed(1)+"</p>";
						html += "</div>";
						html += "</div>";
						html += "<div class='div-movie-content'>";
							
						html += "<div class='row'>";
						html += "<div class='col-md-4'><p class='font-bold'> " + movieTitle + "</p></div>";
						html += "<div class='col-md-8'><p>" + cutoff(movie.name, 8) + "</p></div>";
						html += "</div>";
						html += "<div class='row'>";
						html += "<div class='col-md-4'><p class='font-bold'>" + countryTitle + "</p></div>";
						html += "<div class='col-md-8'><p>" + movie.publishCountry + " </p></div>";
						html += "</div>";
						html += "<div class='row'>";
						html += "<div class='col-md-4'><p class='font-bold'>" + genreName + "</p></div>";
						html += "<div class='col-md-8'>";
						html += "<p>";
						$.each(movie.genres, function(index, eachGenre) {
							html += eachGenre.type;
						});
						html += "</p>";
						html += "</div>";
						html += "</div>";
						html += "	<div class='row'>";
						html += "		<div class='col-md-12'>";
						if(isExpired(movie.common.closeDate)) {
							html += "<p class='font-bold font-alert'>"+movieCloseDate+"</p>";
						} else {
							html += "<p class='font-bold font-alert'>"+(moment(movie.common.closeDate, 'x').format('YYYY.MM.DD'))+" "+movieCloseDate+"</p>";
						}
						html += "		</div>";
						html += "	</div>";
						html += "</div>";
						html += "<div class='div-movie-hover' mid='" +movie.common.id +"'>";
						html += "<div>";
						html += "<a class='btn btn-primary btn-booking btn-forward "+ (isExpired(movie.common.closeDate) ? 'disabled' : '' ) +"' style='margin-top: 60%'><i class='fa fa-ticket fa-fw'></i>  "+bookingName+"</a>";
						
						html += "	</div>";
						html += "<div>";
						html += "		<a href='/movie/detail?mid="+movie.common.id+"&lang="+fLang+" class='btn btn-detail btn-success btn-forward' style='margin-top: 20%'><i class='fa fa-info fa-fw'></i> "+movieDetail+"</a>";
						html += "	</div>";
						html += "	<div></div>";
						html += "</div>";
						html += "</li>";
					});
					
					
					
					$ulMovies.append(html);
					isOnProcess = false;
				}
			});
		}
		
	});
	
	
</script>
</html>