<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
<%@ page import="com.model2.mvc.service.domain.Purchase" %>

<%
	Purchase purchase = (Purchase)request.getAttribute("purchase");
%>
 --%>

<html>
<head>
<title>구매 결과창</title>
</head>

<body>

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td>${purchase.purchaseProd.prodNo}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td>${purchase.buyer.userId}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매수량</td>
		<td>${purchase.quantity}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
			<c:if test="${purchase.paymentOption == '0'}">
			현금구매
			</c:if>
			
			<c:if test="${purchase.paymentOption == '1'}">
			신용구매
			</c:if>
			
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td>${purchase.receiverName}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td>${purchase.receiverPhone}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td>${purchase.divyAddr}</td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td>${purchase.divyRequest}</td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td>${purchase.divyDate}</td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>