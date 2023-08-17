<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>
<head>
    <title>Public Wifi Search Service</title>
    <link rel="shortcut icon" href="css/wifi-icon.png">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css' />">
</head>

<body>
<div class="class-padding">
    <section>
        <h1>위치 히스토리 목록</h1>
        <li>
            <form class="class-button" action="<%= request.getContextPath()%>/" method="get">
                <input class="class-button" type="submit" id="home" value="홈"></form>
            |
            <form action="<%= request.getContextPath()%>/logData" method="get">
                <input class="class-button" type="submit" id="logData" value="위치 히스토리 목록"></form>
            |
            <form action="<%= request.getContextPath()%>/update" method="get">
                <input class="class-button" type="submit" id="update" value="Open API WIFI 정보 가져오기">
            </form>
            |
            <form action="<%= request.getContextPath()%>/bookmark" method="get">
                <input class="class-button" type="submit" id="bookmark" value="북마크 보기">
            </form>
            |
            <form action="<%= request.getContextPath()%>/bookmark-group-list" method="get">
                <input class="class-button" type="submit" id="bookmarkGroup" value="북마크 그룹 관리">
            </form>
        </li>
    </section>

    <section>
        <div class="tbl-header">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>위도</th>
                    <th>경도</th>
                    <th>조회 일자</th>
                    <th>비고</th>
                </tr>
                </thead>
            </table>
        </div>
        <div class="tbl-content">
            <table>
                <tbody>
                <c:forEach var="logs" items="${logData}">
                    <tr>
                        <td>${logs.getId()}</td>
                        <td>${logs.getLat()}</td>
                        <td>${logs.getLng()}</td>
                        <td>${logs.getReq_datetime()}</td>
                        <td>
                            <form action="<%= request.getContextPath()%>/logData" method="get" onsubmit="return checkDelete()">
                                <input type="hidden" name="delete" value="${logs.getId()}">
                                <input type="submit" value="Delete"></form>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty logData}">
                    <tr>
                        <td style="text-align: center">위치 히스토리 목록이 없습니다.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </section>
</div>
</body>
<script>
    function checkDelete(){
        if(confirm("정말 삭제하시겠습니까?")){
            return true;
        }

        return false;
    }
</script>
</html>