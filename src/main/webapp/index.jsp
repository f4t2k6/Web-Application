<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Survey</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css">
</head>
<body>
<div class="container">
    <img src="images/murachlogo.png" alt="Logo" class="logo">
    <h1>Survey</h1>

    <form action="SurveyServlet" method="post">

        <fieldset>
            <legend>Your information</legend>

            <label for="fullName">Full name:</label>
            <input type="text" id="fullName" name="fullName" required><br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br>

            <label>Date of birth:</label>

            <%-- Tháng: lặp qua chuỗi tên tháng, varStatus.count chạy từ 1 đến 12 --%>
            <select name="birthMonth">
                <c:forEach var="month"
                           items="January,February,March,April,May,June,July,August,September,October,November,December"
                           varStatus="s">
                    <option value="${s.count}">${month}</option>
                </c:forEach>
            </select>

            <%-- Ngày: lặp từ 1 đến 31 --%>
            <select name="birthDay">
                <c:forEach var="d" begin="1" end="31">
                    <option value="${d}">${d}</option>
                </c:forEach>
            </select>

            <input type="number" name="birthYear" placeholder="Year" min="1900" max="2026" style="width:80px;">
        </fieldset>

        <fieldset>
            <legend>How did you hear about us?</legend>
            <div class="radio-group">
                <%-- Phần tử đầu tiên (s.first) được checked mặc định --%>
                <c:forEach var="opt"
                           items="Search engine,Word of mouth,Social media,Other"
                           varStatus="s">
                    <label>
                        <input type="radio" name="hearAbout" value="${opt}" ${s.first ? 'checked' : ''}>${opt}
                    </label>
                </c:forEach>
            </div>
        </fieldset>

        <fieldset>
            <legend>Would you like to receive announcements about new CDs and special offers?</legend>
            <div class="radio-group">
                <c:forEach var="opt" items="Yes,No" varStatus="s">
                    <label>
                        <input type="radio" name="receiveAnnouncements" value="${opt}" ${s.first ? 'checked' : ''}>${opt}
                    </label>
                </c:forEach>
            </div>
        </fieldset>

        <div class="submit-row">
            <input type="submit" value="Submit">
        </div>

    </form>
</div>
</body>
</html>