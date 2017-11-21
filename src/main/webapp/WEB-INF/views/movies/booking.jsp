<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<html>
<head>
	<title><spring:message code="booking.title" /></title>
	<%@ include file="/WEB-INF/views/common/head.jsp" %>
	<style>
		.l-padding {
			left-padding: 4px !important;
			top-padding: 0px !important;
			bottom-padding: 0px !important;
			right-padding: 0px !important;
		}
		.padding {
			padding: 8px !important;
		}
		.ul-date li {
			padding-left: 4px;
		}
		.ul-list {
			max-height: 800px;
		}
		
		.side-margin {
			margin: 0px 72px;
		}
		#table-receipt {
			font-size: 0.9em;
		}
		.top-margin {
			margin-top: 12px !important;
		}
		.btn {
			border-radius: 0px !important;
		}
		.cbox {
			margin: 0px 2px 0px 24px;
		}
	</style>
</head>
<body>
	<c:set var="nav_active" value="booking" />
	<%@ include file="/WEB-INF/views/common/nav.jsp" %>
	<%@ include file="/WEB-INF/views/common/common_views.jsp" %>
	
	<div class="row side-margin top-margin">
		<div class="white-box padding col-md-9" id="div-movie">
			<div class="col-md-2 zero-padding">
				<h4 class="page-header text-center"><spring:message code="booking.movieList" /></h4>
				<ul class="ul-list ul-movies inner-scroll" style="overflow: auto;">
					<c:forEach items="${movies }" var="movie">
						<li><input class="btn-input-list hover" content="${movie.name }" type="radio" name="movie" value="${movie.common.id }" ${param.mid eq movie.common.id ? 'checked' : ''}/></li>
					</c:forEach>
				</ul>
			</div>
			
			
			<!-- 날짜선택 -->
			<div class="col-md-2 l-padding">
				<h4 class="page-header text-center"><spring:message code="booking.dateList" /></h4>
				
				<ul class="ul-list ul-date inner-scroll" style="overflow: auto;">
					
				</ul>
			</div>
			
			<!-- 시간선택 -->
			<div class="col-md-2 l-padding">
				<h4 class="page-header text-center"><spring:message code="booking.timeList" /></h4>
				
				<ul class="ul-list ul-time inner-scroll" style="overflow: auto;">
					<li><input class="btn-input-list hover" content="00:00" type="radio" name="time" value="00:00" data="0"/></li>
					<li><input class="btn-input-list hover" content="01:00" type="radio" name="time" value="01:00" data="1"/></li>
					<li><input class="btn-input-list hover" content="02:00" type="radio" name="time" value="02:00" data="2"/></li>
					<li><input class="btn-input-list hover" content="03:00" type="radio" name="time" value="03:00" data="3"/></li>
					<li><input class="btn-input-list hover" content="04:00" type="radio" name="time" value="04:00" data="4"/></li>
					<li><input class="btn-input-list hover" content="05:00" type="radio" name="time" value="05:00" data="5"/></li>
					<li><input class="btn-input-list hover" content="06:00" type="radio" name="time" value="06:00" data="6"/></li>
					<li><input class="btn-input-list hover" content="07:00" type="radio" name="time" value="07:00" data="7"/></li>
					<li><input class="btn-input-list hover" content="08:00" type="radio" name="time" value="08:00" data="8"/></li>
					<li><input class="btn-input-list hover" content="09:00" type="radio" name="time" value="09:00" data="9"/></li>
					<li><input class="btn-input-list hover" content="10:00" type="radio" name="time" value="10:00" data="10"/></li>
					<li><input class="btn-input-list hover" content="11:00" type="radio" name="time" value="11:00" data="11"/></li>
					<li><input class="btn-input-list hover" content="12:00" type="radio" name="time" value="12:00" data="12"/></li>
					<li><input class="btn-input-list hover" content="13:00" type="radio" name="time" value="13:00" data="13"/></li>
					<li><input class="btn-input-list hover" content="14:00" type="radio" name="time" value="14:00" data="14"/></li>
					<li><input class="btn-input-list hover" content="15:00" type="radio" name="time" value="15:00" data="15"/></li>
					<li><input class="btn-input-list hover" content="16:00" type="radio" name="time" value="16:00" data="16"/></li>
					<li><input class="btn-input-list hover" content="17:00" type="radio" name="time" value="17:00" data="17"/></li>
					<li><input class="btn-input-list hover" content="18:00" type="radio" name="time" value="18:00" data="18"/></li>
					<li><input class="btn-input-list hover" content="19:00" type="radio" name="time" value="19:00" data="19"/></li>
					<li><input class="btn-input-list hover" content="20:00" type="radio" name="time" value="20:00" data="20"/></li>
					<li><input class="btn-input-list hover" content="21:00" type="radio" name="time" value="21:00" data="21"/></li>
					<li><input class="btn-input-list hover" content="22:00" type="radio" name="time" value="22:00" data="22"/></li>
					<li><input class="btn-input-list hover" content="23:00" type="radio" name="time" value="23:00" data="23"/></li>
				</ul>
			</div>
			
			<!-- 좌석선택 -->
			<div class="col-md-6">
				<h4 class="page-header text-center"><spring:message code="booking.screenList" /></h4>
				<table id="table-timetable" class="table">
					<thead>
						<tr>
							<th><spring:message code="booking.screen" /></th>
							<th><spring:message code="seatCount" /></th>
							<th><spring:message code="booking.startTime" /></th>
							<th><spring:message code="booking.finishTime" /></th>
							<th><spring:message code="booking.onlinePurchase" /></th>
							<th><spring:message code="booking.offlinePurchase" /></th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
				
				<h4 class="page-header text-center"><spring:message code="booking.seatList" /></h4>
				<div class="row">
					<p class="h-side-margin text-center">
						<i class="box bg-white cbox"></i> <spring:message code="booking.availableSeat" />
						<i class="box bg-green cbox"></i> <spring:message code="booking.seatDisabled" />
						<i class="box bg-red cbox"></i> <spring:message code="booking.na" />
						<i class="box bg-blue cbox"></i> <spring:message code="booking.selectedSeat" />
					</p>
				</div>
				<div class="white-box row text-center top-margin v-side-padding h-side-margin">
					<spring:message code="booking.screen" />
				</div>
				<ul class="ul-seat zero-padding top-margin" style="overflow: auto">
					
				</ul>
				
			</div>
		</div>
		
		<!-- 영수증 -->
		<div class="white-box padding col-md-2 col-md-offset-1" id="div-receipt" style="position: relative;">
			<h4 class="text-center"><spring:message code="receipt" /></h4>
			<hr />
			<table class="table" id="table-receipt">
				<thead>
					<tr>
						<th><spring:message code="booking.movieTitle" /></th>
						<th><spring:message code="booking.screen" /></th>
						<th><spring:message code="booking.startTime" /></th>
						<th><spring:message code="booking.seat" /></th>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>
			<div style="position: absolute; bottom: 12px; width: 100%; height: auto; padding: 6px 18px;">
				<hr />
				<p style="font-size: 1.1em;"><strong><spring:message code="booking.totalPrice" /></strong><span id="receipt-total" style="float: right"></span></p>
				<div class="row" style="margin-top: 6px;">
					<div class="col-md-6"><a id="btn-pay" class="btn btn-primary btn-block btn-forward"><i class="fa fa-check fa-fw"></i><spring:message code="booking.pay" /></a></div>
					<div class="col-md-6"><a id="btn-ticket-clear" class="btn btn-default btn-block"><i class="fa fa-fire fa-fw"></i><spring:message code="booking.clear" /></a></div>
				</div>
			</div>
		</div>
	</div>
	<form id="form-ticket" method="post" action="/movie/select_coupon">
		
	</form>
</body>

<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="/resources/js/main.js"></script>
<script>
	$(function() {
		var $ulMovie = $('.ul-movies'),					// 영화 리스트
		$ulDate = $('.ul-date'),						// 날짜 리스트
		$ulTime = $('.ul-time'),						// 시간 리스트
		$ulSeat = $('.ul-seat'),						// 좌석 리스트
		$tableTimetable = $('#table-timetable tbody'),	// 영화 시간표 테이블
		$tableReceipt = $('#table-receipt tbody'),		// 영수증 테이블
		$divMovie = $('#div-movie'),					// 영화 div
		$divReceipt = $('#div-receipt'), 				// 영수증 div
		$spanReceiptTotal = $('#receipt-total'),		// 총 가격 나타냄
		dateCount = 0,									// 날짜선택에 사용되는 변수
		mDate = moment(),								// 당일
		mMonth = '',									// 날짜선택에 사용되는 변수
		selectedTickets = [], 							// 선택된 좌석에 대한 정보 (단일)
		tarr = [], 										// 선택된 영화정보
		customerType = '${LOGIN_CUSTOMER.type.name}';	// 로그인한 유저 타입
		ticketPrice = parseInt('${LOGIN_CUSTOMER.type.price}');
		
		$('.ul-list').height(600);
		$ulSeat.height(400);
		
		
		
		
		// 사용자가 날짜스크롤을 내릴 걸 감지해 date정보 추가
		$ulDate.scroll(function() {
			if($ulDate.scrollTop() + $ulDate.innerHeight() >= $ulDate[0].scrollHeight) {
		        addDate(mDate);
		    }
		});
		
		
		// 사용자가 날짜 스크롤을 내릴 때 추가적인 date를 불러와 html에 부착
		addDate(mDate, true);
		function addDate(fromDate, isFirst) {
			if(fromDate == null)
				fromDate = moment();
			
			var toDate = moment(fromDate).add(50, 'days');
			var range = moment.range(fromDate, toDate);
			var array = Array.from(range.by('days'));
			
			
			$.each(array, function(index, day) {
				day = day.format('YYYY-MM-DD');
				split = day.split('-');
				newmonth = split[0] + '.' + split[1],
				newday = split[2],
				result = null;
				if(newmonth !== mMonth) {
					$ulDate.append($("<li class='font-alert'>"+newmonth+"</li>"));
					mMonth = newmonth;
				}
				
				var id = 'li-date-' + (dateCount++);
				$li = '';
				if(isFirst) {
					$li = $("<li><input type='radio' id='"+id+"' class='btn-input-list' name='date' value='"+day+"' checked /><label class='hover' for='"+id+"'>"+newday+"</label></li>");
					isFirst = false;
				}
				else
					$li = $("<li><input type='radio' id='"+id+"' class='btn-input-list' name='date' value='"+day+"'/><label class='hover' for='"+id+"'>"+newday+"</label></li>");
				$ulDate.append($li);
			});
			mDate = moment(toDate).add(1, 'days');
			$divReceipt.height($divMovie.height());
			$divReceipt.css({
				maxHeight: $divMovie.height()
			});
		}
		
		
		
		
		
		var mid = '${param.mid}' + '', 
		date = moment().format('YYYY-MM-DD'),
		time = moment().format('HH'),
		lang = '${param.lang}' + '';
		if(!lang) lang = 'ko';
		
		// ajax로 영화 시간표 요청
		function getSchedule() {
			
			if(mid && date && time) {
				data = {
					mid: mid, 
					date: moment(date +' '+time).format('YYYY-MM-DD HH:mm:SS'),
					lang: lang
				};
				
				$ulSeat.empty();
				$tableTimetable.empty();
				
				$.ajax({
					type: 'get',
					url: '/movie/schedule',
					data: data,
					dataType: 'json',
					success: function(data) {
						appendSchedule(data);
						
					}
				});
			}
		}
		getSchedule();
		
		
		// 타임테이블 html 부착
		function appendSchedule(screens) {
			
			tarr = [];
			$tableTimetable.empty();
			$ulSeat.empty();
			var screenHtml = '', timetableHtml = '';
			
			$.each(screens, function(sIndex, screen) {
				var seatIds = [];
				
				
				screenHtml = "<td>"+screen.name+"</td>";
				screenHtml += "<td>"+screen.seatCount+"</td>";
				
				$.each(screen.timetables, function(tIndex, timetable) {
					var startedAt = moment(timetable.startedAt).format('HH:mm');
					timetableHtml = "<td>"+startedAt+"</td>";
					timetableHtml += "<td>"+moment(timetable.endedAt).format('HH:mm')+"</td>";
					timetableHtml += "<td>"+(timetable.online == 1 ? '<spring:message code="booking.available" />' : '<spring:message code="booking.na" />')+"</td>";
					timetableHtml += "<td>"+(timetable.offline == 1 ? '<spring:message code="booking.available" />' : '<spring:message code="booking.na" />')+"</td>";
					$tr = $("<tr class='hover table-click' na='"+timetable.online+"' sname='"+screen.name+"' startedAt='"+startedAt+"' id='"+timetable.id+"'>"+screenHtml + timetableHtml +"</tr>");
					
					$tableTimetable.append($tr);
					
					$.each(timetable.tickets, function(index, ticket) {
						seatIds.push(ticket.seatId);
					});
					
					tarr.push({
						seats: screen.seats,
						sold: seatIds,
						tid: timetable.id
					});
					if(sIndex == 0 && tIndex == 0) {
						appendSeat(timetable.id);
						$tr.addClass('bg-blue');
					}
				});
				
			});
			if($tableTimetable.is(':empty')) {
				
				var warning = '<spring:message code="booking.notMovieAvailable" />';
				$tr = $("<tr><td colspan='6'>"+warning+"</td></tr>");
				
				$tableTimetable.append($tr);
			}
			
		}
		
		// 좌석 html 부착
		function appendSeat(tid) {
			$ulSeat.empty();
			var sid = 'list-seat-';
			var id = '';
			$.each(tarr, function(tIndex, timetable) {
				if(timetable.tid == tid) {
					var soldIds = timetable.sold;
					$.each(timetable.seats, function(sIndex, seat) {
						id = sid + sIndex;
						
						var html = '', status = seat.status, isSold = false;
						$.each(soldIds, function(ssIndex, soldId) {
							if(soldId === seat.id)
								isSold = true;
						});
						
						if(isSold) {
							$li = $("<li><input class='na-seat disabled' type='checkbox' id='"+id+"'><label for='"+id+"'>"+seat.name+"</label><li>");
						} else {
							if(status === 'Y')
								$li = $("<li><input class='normal-seat' sid='"+seat.id+"' sname='"+seat.name+"' type='checkbox' id='"+id+"' name='seat'><label class='hover' for='"+id+"'>"+seat.name+"</label><li>");
							else if(status === 'A')
								$li = $("<li><input class='advantage-seat' sid='"+seat.id+"' type='checkbox' id='"+id+"' sname='"+seat.name+"' name='seat'><label class='hover' for='"+id+"'>"+seat.name+"</label><li>");
							else
								$li = $("<li><input class='na-seat disabled' sid='"+seat.id+"' type='checkbox' id='"+id+"'><label for='"+id+"'>"+seat.name+"</label><li>");
						}
						
						
						$ulSeat.append($li);
						$.each(selectedTickets, function(index, ticket) {
							if(ticket.seatId == seat.id && timetable.tid == ticket.timetableId) {
								$li.find('input').attr('checked', 'checked');
							}
						});
					});
				}
			});
			$divReceipt.height($divMovie.height());
			$divReceipt.css({
				maxHeight: $divMovie.height()
			});
		}
		// 영화선택
		$(':input[name=movie]').change(function() {
			$this = $(this);
			mid = $this.val();
			getSchedule();
		});
		// 날짜선택
		$ulDate.on('click', ':input[name=date]', function() {
			date = $(this).val();
			getSchedule();
		});
		// 시간선택
		$(':input[name=time]').change(function() {
			time = $(this).val();
			getSchedule();
		});
		// 영화 시간표 선택
		$tableTimetable.on('click', '.table-click', function() {
			$this = $(this);
			$this.siblings().removeClass('bg-blue');
			$this.addClass('bg-blue');
			appendSeat($this.attr('id'));
		});
		// 자리선택
		$ulSeat.on('change', ':input[name=seat]', function() {
			var $this = $(this);
			var seatId = $this.attr('sid');
			if(!$this.is(':checked')) { 	// 티켓 제거
				selectedTickets = selectedTickets.filter(function(ticket) {
					if(ticket.seatId === seatId) {
						$tableReceipt.find("tr[id="+seatId+"]").remove();
						return false;
					}
					return true;
				});
			} else {						// 티켓 추가
				if(selectedTickets.length == 10) {
					fAlert('티켓', '티켓 구매제한은 10장입니다');
					return;
				}
				if($this.hasClass('advantage-seat') && customerType !== '장애인') {
					fAlert('좌석선택', '해당 좌석은 장애인 전용입니다');
					$this.prop('checked', false);
					return;
				}
				var seatName = $this.attr('sname'),
				selectedTimetable = {};
				
				selectedTimetable.seatId = seatId;
				selectedTimetable.seatName = seatName;
				
				$ulMovie.find('input').each(function() {
					$this = $(this);
					if($this.is(':checked')) {
						selectedTimetable.movieName = $this.attr('content');
						selectedTimetable.movieId = mid;
					}
				});
				$ulDate.find('input').each(function() {
					$this = $(this);
					if($this.is(':checked')) {
						selectedTimetable.startedDate = $this.val();
					}
				});
				$tableTimetable.find('tr').each(function() {
					$this = $(this);
					if($this.hasClass('bg-blue')) {
						selectedTimetable.screenName = $this.attr('sname');
						selectedTimetable.startedAt = $this.attr('startedAt');
						selectedTimetable.timetableId = $this.attr('id');
					}
				});
				var html = "<tr id='"+selectedTimetable.seatId+"'><td>"+cutstring(selectedTimetable.movieName, 7)+"</td><td>"+selectedTimetable.screenName+"</td><td>"+selectedTimetable.startedAt+"</td><td>"+selectedTimetable.seatName+"</td></tr>";
				$tableReceipt.append($(html));
				
				selectedTickets.push(selectedTimetable);
			}
			$spanReceiptTotal.text(ticketPrice + ' * ' + selectedTickets.length + ' = ' + (ticketPrice * selectedTickets.length));
		});
		
		$('#btn-pay').click(function() {
			var html = '';
			
			$form = $('#form-ticket');
			console.log(selectedTickets);
			$.each(selectedTickets, function(index, ticket) {
				html += "<input type='hidden' name='tickets["+index+"].seatId' value='"+ticket.seatId+"'/>";
				html += "<input type='hidden' name='tickets["+index+"].seatName' value='"+ticket.seatName+"'/>";
				html += "<input type='hidden' name='tickets["+index+"].movieId' value='"+ticket.movieId+"'/>";
				html += "<input type='hidden' name='tickets["+index+"].movieName' value='"+ticket.movieName+"'/>";
				html += "<input type='hidden' name='tickets["+index+"].screenName' value='"+ticket.screenName+"'/>";
				html += "<input type='hidden' name='tickets["+index+"].startedAt' value='"+(ticket.startedDate+' '+ticket.startedAt)+"'/>";
				html += "<input type='hidden' name='tickets["+index+"].timetableId' value='"+ticket.timetableId+"'/>";
			});
			
			$form.append(html);
			$form.submit();
		});
		
// 		// 계산하기
// 		$('#btn-pay').click(function() {
// 			if(selectedTickets.length == 0)
// 				fAlert('티켓선택', '선택된 티켓이 없습니다');
			
// 			$.ajax({
// 				type: 'post',
// 				url: '/movie/prior_pay',
// 				data: JSON.stringify(selectedTickets),
// 				contentType: 'json',
				
// 				success: function() {
// 					location.href = 'save.do?'
// 				}
// 			});
// 		});
		// 모든 티켓 취소
		$('#btn-ticket-clear').click(function() {
			selectedTickets = [];
			$('input[name=seat]').prop('checked', false);
			$spanReceiptTotal.text('');
			$tableReceipt.empty();
		});
		
		
		// time li 중 현시간을 선택한다
		$.each($(':input[name=time]'), function(index, each) {
			$this = $(this); 
			cTime = parseInt(moment().format('HH')) + '';
			if(cTime === $this.attr('data'))
				$this.attr('checked', 'checked');
			
		});
		
		
		(function() {
			$checkedMovie = $ulMovie.find('input:checked');
			if($checkedMovie.length > 0) {
				$inputMovies = $ulMovie.find('input');
				var ulHeight = 0;
				$.each($inputMovies, function(index, $inputMovie) {
					$inputMovie = $($inputMovie);
					if($inputMovie.is(':checked')) {
						$ulMovie.scrollTop(ulHeight);				
					}
					ulHeight += $inputMovie.height(); 
				});
			}
			
			$checkedTime = $ulTime.find('input:checked');
			$inputTimes = $ulTime.find('input');
			ulHeight = 0;
			$.each($inputTimes, function(index, $inputTime) {
				$inputTime = $($inputTime);
				if($inputTime.is(':checked')) {
					$ulTime.scrollTop(ulHeight);
				}
				ulHeight += $inputTime.height(); 
			});
		})();
	});
	
	
</script>
</html>