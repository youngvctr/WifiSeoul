<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Public Wifi Search Service</title>
    <link rel="shortcut icon" href="css/wifi-icon.png">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css' />">
</head>

<body>
<div class="class-padding">
    <section>
        <h1>북마크 그룹 목록</h1>
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
        <button class="button" onclick="location.href='bookmark-group-add.jsp'">북마크 그룹 추가</button>
    </section>
    <section>
        <div class="tbl-header">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>북마크 이름</th>
                    <th>우선순위</th>
                    <th>비고</th>
                </tr>
                </thead>
            </table>
        </div>
        <div class="tbl-content">
            <table>
                <tbody>
                <c:forEach var="bookmark" items="${bookmark}">
                    <tr>
                        <td>${bookmark.getId()}</td>
                        <td>${bookmark.getBookmark_name()}</td>
                        <td>${bookmark.getBookmark_order()}</td>
                        <td>
                            <form action="<%= request.getContextPath()%>/bookmark-group-delete" method="get" onsubmit="return checkDelete()">
                                <input type="hidden" name="delete" value="${bookmark.getId()}">
                                <input type="submit" value="Delete"></form>

                            <form action="<%= request.getContextPath()%>/bookmark-group-edit.jsp" method="get">
                                <input type="hidden" name="id" value="${bookmark.getId()}">
                                <input type="hidden" name="name" value="${bookmark.getBookmark_name()}">
                                <input type="hidden" name="order" value="${bookmark.getBookmark_order()}">
                                <input type="submit" value="Update"></form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty bookmark}">
                    <tr>
                        <td style="text-align: center">북마크 그룹 목록이 없습니다.</td>
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
