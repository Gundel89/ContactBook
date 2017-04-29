<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<script type="text/javascript">
	function validForm(form) {
		var valid = true;
		var namePattern = /^[а-яА-Я|a-zA-Z]{2,20}$/;
		var agePattern = /^\d{1,3}$/;
		var genderPattern = /^[м|ж]$/;
		var phonePattern = /^\+\d{2,2}\(\d{3,3}\)\d{7,7}$/;
		
		var firstName = form.firstName.value;
		var lastName = form.lastName.value;
		var age = form.age.value;
		var gender = form.gender.value;
		var phone = form.phone.value;
		
		if (!namePattern.test(firstName)) {
			valid = false;
			document.getElementById("invalidFirstName").innerHTML
			= "Имя должно состоять не менее чем из 5 и не более чем из 20 букв кирилицы, либо латиницы";
		}
		if (!namePattern.test(lastName)) {
			valid = false;
			document.getElementById("invalidLastName").innerHTML
			= "Фамилия должна состоять не менее чем из 5 и не более чем из 20 букв кирилицы, либо латиницы";
		}
		if (!agePattern.test(age)) {
			valid = false;
			document.getElementById("invalidAge").innerHTML
			= "Введите только число от 0 до 200";
		}
		if (!genderPattern.test(gender)) {
			valid = false;
			document.getElementById("invalidGender").innerHTML
			= "Введите либо 'м', либо 'ж'";
		}
		if (!phonePattern.test(phone)) {
			valid = false;
			document.getElementById("invalidPhone").innerHTML
			= "Некорректный формат номера: +хх(ххх)ххххххх";
		}
		return valid;
	}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<c:set var="isUserExists" value="${user!=null}" />

<title>Редактор пользователя</title>
</head>
<body>
	<form name="user_form" action="makeuser" method="POST" onsubmit="return validForm(user_form)">
		<input type="hidden" name="id" value="${user.id }" />
		<table>
			<caption>${isUserExists ? "Редактирование" : "Добавление нового пользователя"}</caption>
			<tr>
				<td align="right"><label for="lastName">Фамилия:</label></td>
				<td><input id="lastName" type="text" name="lastName" value="${user.lastName }" size="50" /></td>
				<td id="invalidLastName" style="color: red">${invalidLastName}</td>
			</tr>
			<tr>
				<td align="right"><label for="firstName">Имя:</label></td>
				<td><input id="firstName" type="text" name="firstName" value="${user.firstName }" size="50" /></td>
				<td id="invalidFirstName" style="color: red">${invalidFirstName}</td>
			</tr>
			<tr>
				<td align="right"><label for="age">Возраст:</label></td>
				<td><input id="age" type="text" name="age" value="${user.age }" size="50" /></td>
				<td id="invalidAge" style="color: red">${invalidAge}</td>
			</tr>
			<tr>
				<td align="right"><label for="gender">Пол:</label></td>
				<td>
					<select name="gender">
						<option value="м">М</option>
						<option value="ж">Ж</option>
					</select>
				</td>
				<td id="invalidGender" style="color: red">${invalidGender}</td>
			</tr>
			<tr>
				<td align="right"><label for="phone">Телефон:</label></td>
				<td><input id="phone" type="text" name="phone" placeholder="+38(123)4567890"
						value="${user.phone }" size="50" /></td>
				<td id="invalidPhone" style="color: red">${invalidPhone}</td>
			</tr>
		</table>
		<p align="center">
			<input type="submit" value="Сохранить" />
			<input type="reset" value="Очистить" />
		</p>
	</form>
	<p align="center" style="color: green">${success}</p>
	<p align="center" style="color: red">${error}</p>
	<a href="search">На главную</a>
	
</body>
</html>