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
        <h1>북마크 그룹 추가</h1>
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
        <form id="bookmark-group-add-form" action="/bookmark" method="post">
            <table class="s-table">
                <tr>
                    <td class="standard">북마크 이름</td>
                    <td><input type="text" name="bookmark_name" id="bookmark_name"></td>
                </tr>
                <tr>
                    <td class="standard">순서</td>
                    <td><input type="number" name="bookmark_order" id="bookmark_order" min=1></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;">
                        <button type="button" onclick="submitForm()">추가</button>
                    </td>
                </tr>
            </table>
        </form>
    </section>
</div>
</body>
<script>
    function submitForm() {
        const bookmarkName = document.getElementById("bookmark_name").value;
        const groupOrder = document.getElementById("bookmark_order").value;
        const xReq = new XMLHttpRequest();
        xReq.open("POST", "/bookmark", true);
        xReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xReq.onreadystatechange = function () {
            if (xReq.readyState === 4 && xReq.status === 200) {
                alert(xReq.responseText);
                location.replace("<%= request.getContextPath()%>/bookmark-group-list");
            } else if(xReq.readyState === 4 && xReq.status === 205){
                location.replace("<%= request.getContextPath()%>/bookmark-group-add.jsp");
                alert("북마크 이름이 중복되었습니다.");
            }
        };
        xReq.send("bookmark_name=" + encodeURIComponent(bookmarkName) + "&bookmark_order=" + encodeURIComponent(groupOrder));
    }
</script>
</html>