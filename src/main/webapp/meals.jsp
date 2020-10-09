<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <title>Meals</title>
</head>
<body class="w3-light-grey">
<h3><a href="index.html">Home</a></h3>
<hr>
<div class="w3-container w3-blue-grey w3-opacity w3-left-align">
    <h1>Список еды по дням</h1>
</div>

<table class="w3-table-all">
    <tr  class="w3-green">
        <th>Описание еды</th>
        <th>Каллорийность еды</th>
        <th>Дата и время</th>
    </tr>
    <%
        List<Meal> meals = (List<Meal>) request.getAttribute("mealsItem");

        if (meals != null && !meals.isEmpty()) {
            for (Meal s : meals) {

                out.println("<tr>" +
                        "<td>" + s.getDescription() + "</td>" +
                        "<td>" + s.getCalories() + "</td>" +
                        "<td>" + s.getDate() + "</td>" +
                "</tr>");

            }
        }
    %>
</table>

</body>
</html>