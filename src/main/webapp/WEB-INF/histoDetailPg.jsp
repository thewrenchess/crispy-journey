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
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js" ></script>
		<script type="text/javascript" src="/js/echarts.min.js"></script>
		<script type="text/javascript" src="/js/ecStat.js"></script>
        <script type="text/javascript" src="/js/histoJs.js"></script>
		<title><c:out value="${ currGraph.getTitle() }" /></title>
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
			<h1 class="mt-5">Histogram -- <c:out value="${ currGraph.getTitle() }" /></h1>
			<div class="row mt-5">
				<div class="col-2 _right_border _large _scroll">
					<form:form action="/histo/${ currGraph.getId() }" method="POST" modelAttribute="graph">
						<div class="form-group">
							<form:label path="title">Title</form:label>
							<form:input path="title" class="form-control" value="${ currGraph.getTitle() }" />
							<small class="form-text text-center text-danger"><form:errors path="title" /></small>
						</div>
						<div class="form-group">
							<form:label path="description">Description</form:label>
							<form:textarea path="description" class="form-control" placeholder="${ currGraph.getDescription() }"></form:textarea>
						</div>
						<input type="submit" value="Update" class="form-control btn btn-primary" />
					</form:form>
					<hr>
					<form:form action="/histo/addVal" method="POST" modelAttribute="value">
						<table class="table">
							<thead>
								<tr>
									<th class="text-center">Values</th>
									<th></th>
								</tr>
							</thead>
								<tbody>
									<c:forEach items="${ values }" var="v">
										<tr>
											<td class="text-center"><c:out value="${ v.getVal() }" /></td>
											<td><a href="/histo/delete/${ v.getId() }">x</a></td>
										</tr>
									</c:forEach>
									<tr>
										<td><form:input type="number" step="0.01" class="form-control text-center" path="val" /></td>
										<td></td>
									</tr>
								</tbody>
						</table>
						<form:hidden path="graph" value="${ currGraph.getId() }" />
						<input type="submit" value="Add Values" class="form-control btn btn-primary" />
					</form:form>
				</div>
				<div class="col-10">
					<div id="main" class="jumbotron _medium"></div>
				</div>		
			</div>
		</div>
	</body>
</html>