<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

	function fncGetList(currentPage, priceCondition) {
		//var minCharge = document.detailForm.minCharge.value;
		var minCharge = $("input[name='minCharge']").val();
		//var maxCharge = document.detailForm.maxCharge.value;
		var maxCharge = $("input[name='maxCharge']").val();
		
		if(parseInt(minCharge) > parseInt(maxCharge)) {
			alert("가격입력이 잘못되었습니다.");
			return;
		}
		
		//document.getElementById("priceCondition").value = priceCondition;
		$("#priceCondition").val(priceCondition);
		//document.getElementById("currentPage").value = currentPage;
		$("#currentPage").val(currentPage);
		//document.detailForm.submit();
		$("form").attr("method", "POST").attr("action", "/product/listProduct").submit();
	}
	
	<select name="searchCondition" class="ct_input_g" style="width:80px">
		<option value="0" ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
		<option value="1" ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
		<option value="2" ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
	</select>
	
	$(function(){
		$("td.ct_btn01:contains('검색')").bind("click", function(){
			fncGetList('0', '1');
		});
		
		$("td:contains('높은순')").bind("click", function(){
			fncGetList('1', '1');
		});
		
		$("td:contains('낮은순')").bind("click", function(){
			fncGetList('1', '2');
		});
		
		//==> userId LINK Event End User 에게 보일수 있도록 
		$( ".ct_list_pop td:nth-child(3)" ).css("color" , "red");
		$("h7").css("color" , "red");
		
		//==> 아래와 같이 정의한 이유는 ??
		$(".ct_list_pop:nth-child(4n+6)" ).css("background-color" , "whitesmoke");
		
		$(function(){
			$("#searchKeyword").autocomplete({
				source : ,// 리스트
				focus : function(event, ui){
					return false;
				},
				
			});
		});
		
	});
	
	function notLogin(){
		alert("로그인을 하셔야 합니다.");
		document.detailForm.action='/user/loginView.jsp';
		document.detailForm.submit();
		$("form").attr("method", "POST").attr("action", "/user/loginView.jsp").submit();
	}
	
	

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
						<c:if test="${param.menu == 'manage'}">
							상품 관리
						</c:if>
						<c:if test="${param.menu == 'search'}">
							상품 목록
						</c:if>					
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
				<option value="1" ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
				<option value="2" ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
			</select>
			<input 	type="text" id="searchKeyword" name="searchKeyword" value="${! empty search.searchKeyword ? search.searchKeyword : ""}"  
							class="ct_input_g" style="width:200px; height:20px" >
		</td>
	</tr>
	
	<tr>
		<td align="right">
			가격 
			<input type="text" name="minCharge" value="${! empty search.minCharge ? search.minCharge : ""}"
							class="ct_input_g" style="width:100px; height:20px" > 
							~
			<input type="text" name="maxCharge" value="${! empty search.maxCharge ? search.maxCharge : ""}"
							class="ct_input_g" style="width:100px; height:20px" >
		</td>
	</tr>
	<tr>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						검색
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table border="1">
	<tr>
		<td>
			가격높은순
		</td>
		<td>
			가격낮은순
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${i+1}"/>
		<tr class="ct_list_pop">
			<td align="center">${i}</td>
			<td></td>
				
				<td align="left">
				
					<c:if test="${!empty user}">
						<a href="/product/getProduct?prodNo=${product.prodNo}&menu=${param.menu}">${product.prodName}</a>
						
					</c:if>
					
					<c:if test="${empty user}">
						<a href="javascript:notLogin();">${product.prodName}</a>
					</c:if>
				
				</td>
			
			<td></td>
			<td align="left">${product.price}</td>
			<td></td>
			<td align="left">${product.manuDate}</td>
			<td></td>
			<td align="left">
			
				<jsp:include page="/product/transCodeProduct.jsp">
					<jsp:param value="${product.quantity}" name="quantity"/>
					<jsp:param value="${menu}" name="menu"/>
				</jsp:include>
				
			</td>	
		</tr>
		<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>	
	</c:forEach>
</table>

<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			<input type="hidden" id="menu" name="menu" value="${param.menu}"/>
			<input type="hidden" id="priceCondition" name="priceCondition" value=""/>
			
			<jsp:include page="../common/pageNavigator.jsp"/>
			
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>