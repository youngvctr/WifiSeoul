<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>

<head>
    <title>Public Wifi Search Service</title>
    <link rel="shortcut icon" href="css/wifi-icon.png">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css' />">
</head>

<body onload="loadBookmarkNames()">
<div class="class-padding">
    <section>
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
        <form action="<%= request.getContextPath()%>/bookmark-group" method="post">
            <select name="select">
                <option name="option">북마크 그룹 이름 선택</option>
            </select>
            <input type="hidden" name="id" value=${param.id}>
            <input type="hidden" name="main_nm" value=${param.main_nm}>
            <input type="hidden" name="select">
            <button class="class-button" type="submit">북마크 저장</button>
        </form>
    </section>

    <div class="tbl-content2">
        <table>
            <colgroup>
                <col width="40%"/>
                <col width="60%"/>
            </colgroup>
            <tbody>
            <c:forEach var="data" items="${detail}">
                <tr>
                    <th>거리(㎞)</th>
                    <td>${data.getStringDist()}</td>
                </tr>
                <tr>
                    <th>관리번호</th>
                    <td>${data.getMgr_no()}</td>
                </tr>
                <tr>
                    <th>자치구</th>
                    <td>${data.getWrdofc()}</td>
                </tr>
                <tr>
                    <th>Wifi명</th>
                    <td>${data.getMain_nm()}</td>
                </tr>
                <tr>
                    <th>도로명주소</th>
                    <td>${data.getAddress1()}</td>
                </tr>
                <tr>
                    <th>상세주소</th>
                    <td>${data.getAddress2()}</td>
                </tr>
                <tr>
                    <th>설치위치(층)</th>
                    <td>${data.getInstl_floor()}</td>
                </tr>
                <tr>
                    <th>설치유형</th>
                    <td>${data.getInstl_ty()}</td>
                </tr>
                <tr>
                    <th>설치기관</th>
                    <td>${data.getInstl_mby()}</td>
                </tr>
                <tr>
                    <th>서비스구분</th>
                    <td>${data.getSvc_se()}</td>
                </tr>
                <tr>
                    <th>망종류</th>
                    <td>${data.getCmcwr()}</td>
                </tr>
                <tr>
                    <th>설치년도</th>
                    <td>${data.getCnstc_year()}</td>
                </tr>
                <tr>
                    <th>실내외구분</th>
                    <td>${data.getIn_out()}</td>
                </tr>

                <tr>
                    <th>Wifi 접속환경</th>
                    <td>${data.getRemars3()}</td>
                </tr>
                <tr>
                    <th>위도</th>
                    <td>${data.getLat()}</td>
                </tr>
                <tr>
                    <th>경도</th>
                    <td>${data.getLng()}</td>
                </tr>
                <tr>
                    <th>작업일자</th>
                    <td>${data.getWork_dttm()}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
<script>
    function loadBookmarkNames() {
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    const bookmarkNames = JSON.parse(xhr.responseText);
                    const selectElement = document.querySelector("select");

                    bookmarkNames.forEach((bookmarkName) => {
                        const option = document.createElement("option");
                        option.textContent = bookmarkName;
                        option.value = bookmarkName;
                        selectElement.appendChild(option);
                    });
                }
            }
        };
        xhr.open("GET", "/bookmark-group", true);
        xhr.send();
    }
</script>
</html>