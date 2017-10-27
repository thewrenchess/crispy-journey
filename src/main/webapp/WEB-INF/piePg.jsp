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
		<title>Pie Chart</title>
	</head>
	<body>
		<div class="container-fluid px-5">
			<div class="float-right row" >
				<button class="col btn btn-info" onclick="location.href='/'">Dashboard</button>
				<form class="col" action="/logout" method="POST">
					<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
					<input type="submit" value="Logout" class="btn btn-danger" />
				</form>
			</div>
			<h1 class="mt-5">Pie Chart</h1>
			<div class="row mt-5">
				<div class="col-2 _right_border">
					<form:form action="/pie" method="POST" modelAttribute="graph">
						<div class="form-group">
							<form:label path="title">Title</form:label>
							<form:input path="title" class="form-control" />
							<small class="form-text text-center text-danger"><form:errors path="title" /></small>
						</div>
						<div class="form-group">
							<form:label path="description">Description</form:label>
							<form:textarea path="description" class="form-control" ></form:textarea>
						</div>
						<form:hidden path="type" value="Pie" />
						<input type="submit" value="Submit" class="form-control btn btn-primary" />
					</form:form>
					<hr>
					<form>
						<table class="table">
							<thead>
								<tr>
									<th class="text-center">Name</th>
									<th class="text-center">Value</th>
									<th></th>
								</tr>
							</thead>
								<tbody>
									<tr>
										<td><input type="text" class="form-control text-center" name="xVal" /></td>
										<td><input type="number" step="0.01" class="form-control text-center" name="yVal" /></td>
										<td></td>
									</tr>
								</tbody>
						</table>
						<input type="submit" value="Add Values" class="form-control btn btn-primary disabled" />
					</form>
				</div>
				<div class="col-10">
					<div id="main" class="jumbotron _medium"></div>
				</div>		
			</div>
		</div>
	</body>
</html>