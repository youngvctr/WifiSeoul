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
    <h1>Wifi 정보 구하기 | 결과</h1>
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
        <form action="<%= request.getContextPath()%>/search" method="get">
            LAT: <input type="text" id="lat" name="lat" value="${param.lat}"/> ,
            LNG: <input type="text" id="lng" name="lng" value="${param.lng}"/>
            <button type="button" onclick="getYourLocation()">현 위치 조회</button>
            <input type="submit" value="내 주변 wifi 조회"></form>
    </section>

    <section>
        <div>
            <table>
                <colgroup>
                    <col width="5%"/>
                    <col width="5%"/>
                    <col width="5%"/>
                    <col width="6%"/>
                    <col width="*"/>
                    <col width="10%"/>
                    <col width="5%"/>
                    <col width="5%"/>
                    <col width="5%"/>
                    <col width="5%"/>
                    <col width="5%"/>
                    <col width="5%"/>
                    <col width="5%"/>
                    <col width="5%"/>
                    <col width="5%"/>
                    <col width="5%"/>
                    <col width="6%"/>
                </colgroup>

                <thead>
                <tr>
                    <th>거리(Km)</th>
                    <th>관리번호</th>
                    <th>자치구</th>
                    <th>Wifi<br>명</th>
                    <th>도로명<br>주소</th>
                    <th>상세주소</th>
                    <th>설치위치<br>(층)</th>
                    <th>설치유형</th>
                    <th>설치기관</th>
                    <th>서비스<br>구분</th>
                    <th>망종류</th>
                    <th>설치<br>년도</th>
                    <th>실내외<br>구분</th>
                    <th>Wifi<br>접속환경</th>
                    <th>위도</th>
                    <th>경도</th>
                    <th>작업일자</th>
                </tr>
                </thead>

                <tbody class="tbl-content">
                <c:forEach var="GetData" items="${getData}">
                    <tr>
                        <td>${GetData.getStringDist()}</td>
                        <td>${GetData.getMgr_no()}</td>
                        <td>${GetData.getWrdofc()}</td>
                        <td>
                            <form action="<%=request.getContextPath()%>/detail" method="get">
                                <input type="hidden" name="id" value="${GetData.getId()}">
                                <input type="hidden" name="main_nm" value="${GetData.getMain_nm()}">
                                <input type="submit" class="classic-words" id="detail" value="${GetData.getMain_nm()}">
                            </form>
                        </td>
                        <td>${GetData.getAddress1()}</td>
                        <td>${GetData.getAddress2()}</td>
                        <td>${GetData.getInstl_floor()}</td>
                        <td>${GetData.getInstl_ty()}</td>
                        <td>${GetData.getInstl_mby()}</td>
                        <td>${GetData.getSvc_se()}</td>
                        <td>${GetData.getCmcwr()}</td>
                        <td>${GetData.getCnstc_year()}</td>
                        <td>${GetData.getIn_out()}</td>
                        <td>${GetData.getRemars3()}</td>
                        <td>${GetData.getLat()}</td>
                        <td>${GetData.getLng()}</td>
                        <td>${GetData.getWork_dttm()}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>
</div>
</body>
<script>
    function getYourLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showYourLocation, showErrorMsg);
        } else {
            location.href = "error";
        }
    }

    function showYourLocation(position) {
        document.getElementById("lat").value = position.coords.latitude.toFixed(7);
        document.getElementById("lng").value = position.coords.longitude.toFixed(7);
    }

    function showErrorMsg(error) {
        switch (error.code) {
            case error.PERMISSION_DENIED:
                alert("Geolocation API 사용 요청을 허용해주십시오.");
                break;
            case error.POSITION_UNAVAILABLE:
                alert("위치 정보를 사용할 수 없습니다.");
                break;
            case error.TIMEOUT:
                alert("위치 정보를 가져오기 위한 요청이 허용 시간을 초과했습니다.");
                break;
            default:
                alert("알 수 없는 오류가 발생했습니다.");
                break;
        }
    }
</script>
</html>
