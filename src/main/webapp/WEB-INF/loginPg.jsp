<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="/css/styles.css" />
		<title>Login</title>
	</head>
	<body>
		<div class="container-fluid px-5">
			<c:if test="${ logout != null }">
				<p class="text-center text-danger"><c:out value="${ logout }" /></p>
			</c:if>
			<h1 class="text-center mt-5">Welcome to QuikChart</h1>
			<div class="row">
				<div class="col jumbotron border m-3">
					<h3>Register</h3>
					<form:form action="/registration" method="POST" modelAttribute="user" >
						<div class="form-group">
							<form:label path="first" >First Name</form:label>
							<form:input type="text" path="first" class="form-control" />
							<small class="form-text text-center text-danger"><form:errors path="first" /></small>
						</div>
						<div class="form-group" >
							<form:label path="username" >Email</form:label>
							<form:input type="email" path="username" class="form-control" />
							<small class="form-text text-center text-danger"><form:errors path="username" /></small>
						</div>
						<div class="form-group" >
							<form:label path="password" >Password</form:label>
							<form:input type="password" path="password" class="form-control" />
							<small class="form-text text-center text-danger"><form:errors path="password" /></small>
						</div>
						<div class="form-group" >
							<form:label path="passwordConfirmation" >Confirm Password</form:label>
							<form:input type="password" path="passwordConfirmation" class="form-control" />
							<small class="form-text text-center text-danger"><form:errors path="passwordConfirmation" /></small>
							<small class="form-text text-center text-muted">Password must contain 8 characters with at least one lowercase, one uppercase, and one number</small>
						</div>
						<input type="submit" value="Register" class="form-control btn btn-info" />
					</form:form>
				</div>
				<div class="col jumbotron border m-3">
					<c:if test="${ error != null }">
						<p class="text-center text-danger"><c:out value="${ error }" /></p>
					</c:if>
					<h3>Login</h3>
					<form action="/login" method="POST" >
						<div class="form-group">
							<label for="email">Email</label>
							<input id="email" name="username" type="email" class="form-control" />
						</div>
						<div class="form-group">
							<label for="pw">Password</label>
							<input for="pw" name="password" type="password" class="form-control" />
						</div>
						<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
						<input type="submit" value="Login" class="form-control btn btn-success" />
					</form>
				</div>
			</div>
		</div>
	</body>
</html>