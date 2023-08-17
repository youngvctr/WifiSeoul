<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Public Wifi Search Service</title>
    <link rel="shortcut icon" href="css/wifi-icon.png">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css' />">
</head>

<body onload="loadBookmarks()">
<div class="class-padding">
    <section>
        <h1>북마크 수정</h1>
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
        <form id="bookmark-update-form" action="<%= request.getContextPath()%>/bookmark-update" method="get">
            <table class="s-table">
                <tr>
                    <td class="standard">북마크 이름</td>
                    <td>
                        <select name="bookmark_name">
                            <option name="option">북마크 그룹 이름 선택</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;">
                        <input type="hidden" name="bookmark_id" value="${param.id}">
                        <a onclick="location.href='<%= request.getContextPath()%>/bookmark';" style="cursor:pointer;">뒤로가기</a>
                        <input type="submit" value="Update">
                    </td>
                </tr>
            </table>
        </form>
    </section>
</div>
</body>

<script>
    function loadBookmarks() {
        const xReq = new XMLHttpRequest();
        xReq.onreadystatechange = function () {
            if (xReq.readyState === XMLHttpRequest.DONE) {
                if (xReq.status === 200) {
                    const bookmarkNames = JSON.parse(xReq.responseText);
                    const selectElement = document.querySelector("select");

                    bookmarkNames.forEach((bookmarkName) => {
                        const opt = document.createElement("option");
                        opt.textContent = bookmarkName;
                        opt.value = bookmarkName;
                        selectElement.appendChild(opt);
                    });
                }
            }
        };
        xReq.open("GET", "/bookmark-group", true);
        xReq.send();
    }
</script>
</html>