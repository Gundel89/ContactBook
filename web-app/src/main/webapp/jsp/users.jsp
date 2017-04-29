<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Users</title>
</head>
<body>
	<form action="search" method="GET">
		<input type="text" name="search_req" placeholder="Поиск" size="50" />
		<select name="search_param">
			<option value="id">по порядковому номеру</option>
			<option value="last_name">по фамилии</option>
			<option value="first_name">по имени</option>
			<option value="age">по возрасту</option>
			<option value="phone">по телефону</option>
		</select>
		<input type="submit" value="Найти" />
	</form>
	<form action="search" method="GET">
		<p>Поиск по полу:</p>
		<select name="search_req">
			<option value="м">Мужской</option>
			<option value="ж">Женский</option>
		</select>
		<input type="hidden" name="search_param" value="gender" />
		<input type="submit" value="Найти" />
	</form>
	<c:choose>
		<c:when test="${not empty users}">
			<table border="1" align="center">
				<tr>
					<th>Номер</th>
					<th>Фамилия</th>
					<th>Имя</th>
					<th>Возраст</th>
					<th>Пол</th>
					<th>Телефон</th>
				</tr>
				<c:forEach items="${users}" var="user">
					<tr>
						<td><c:out value="${user.id}" /></td>
						<td><c:out value="${user.lastName}" /></td>
						<td><c:out value="${user.firstName}" /></td>
						<td><c:out value="${user.age}" /></td>
						<td><c:out value="${user.gender}" /></td>
						<td><c:out value="${user.phone}" /></td>
						<td><a href="/edit?id=${user.id}">Редактировать</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<h2 align="center">Ничего не найдено</h2>
		</c:otherwise>
	</c:choose>
	<a href="/jsp/userdetails.jsp">Добавить</a>
</body>
</html>