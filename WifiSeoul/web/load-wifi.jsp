<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>

<html>
<head>
    <title>Public Wifi Search Service</title>
    <link rel="shortcut icon" href="css/wifi-icon.png">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css' />">
</head>

<body>
<div class="class-padding">
    <section>
        <h1 style="text-align:center;font-weight: bold">${loadWifi}</h1>
    </section>
    <section style="text-align: center;font-size: medium">
        <a onclick="location.href='<%= request.getContextPath()%>/';" style="cursor:pointer;text-decoration: underline">홈 으로 가기</a>
    </section>
</div>
</body>
</html>
