<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="/css/styles.css" />
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js" ></script>
        <script type="text/javascript" src="/js/dashboardJs.js"></script>
		<title>Dashboard</title>
	</head>
	<body>
		<div class="container-fluid px-5">
			<div class="float-right" >
				<form action="/logout" method="POST">
					<input type="hidden" name="${ _csrf.parameterName }" value="${ _csrf.token }" />
					<input type="submit" value="Logout" class="btn btn-danger" />
				</form>
			</div>
			<h1 class="mt-5">Welcome, <c:out value="${ user.getFirst() }" /></h1>
			<h3 class="mt-5">Your Last 5 Graphs</h3>
			<hr>
			<table class="table table-striped table-bordered">
				<thead>
					<tr class="table-dark">
						<th>Title</th>
						<th>Type</th>
						<th>Last Updated</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ graphs }" var="g" >
						<tr>
							<td><a href="/graphs/${ g.getId() }" ><c:out value="${ g.getTitle() }" /></a><%-- <c:choose>
								<c:when test="${ g.getType() = 'Histogram' }" ><a href="/histo/${ g.getId() }" ><c:out value="${ g.getTitle() }" /></a></c:when>
								<c:otherwise><a href="/poly/${ g.getId() }" ><c:out value="${ g.getTitle() }" /></a></c:otherwise>
							</c:choose> --%></td>
							<td><c:out value="${ g.getType() }" /></td>
							<td><c:choose>
								<c:when test="${ g.getUpdatedAt() != null }" ><c:out value="${ g.getUpdatedAt() }" /></c:when>
								<c:otherwise><c:out value="${ g.getCreatedAt() }" /></c:otherwise>
							</c:choose></td>
							<td><a href="/graphs/delete/${ g.getId() }" >delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<h3 class="mt-3"><button id="create" class="btn btn-success mt-5">Create a New Graph</button></h3>
			<div id="media" class="mt-3">
				<div class="media border mt-3 p-1">
				  <img class="mr-3 img-fluid" src="/img/poly.png" alt="Generic placeholder image">
				  <div class="media-body mt-5 mx-5">
				    <h5 class="mt-0 mb-3">Polynomial Regression</h5>
				    <p>This graph performs regression analysis on your submitted data for the relationship between the independent variable x and the dependent variable y to the 3rd degree polynomial in x</p>
				  	<p class="text-primary">Use Polynomial Regression if you are looking for relationship between points (value pairs)</p>
				  	<button onclick="location.href='/poly'" class="btn btn-success">Start</button>
				  </div>
				</div>
				<div class="media border mt-3 p-1">
				  <div class="media-body mt-5 mx-5">
				    <h5 class="mt-0 mb-3">Histogram</h5>
				    <p>A histogram is an accurate graphical representation of the distribution of numerical data. It is an estimate of the probability distribution of a continuous and quantitative variable.</p>
					<p class="text-primary">Use a histogram if you would like to see the distribution of values and how much variation there is.</p>
				  	<button onclick="location.href='/histo'" class="btn btn-success">Start</button>
				  </div>
				  <img class="ml-3 img-fluid" src="/img/histo.png" alt="Generic placeholder image">
				</div>
				<div class="media border mt-3 p-1">
				  <img class="mr-3 img-fluid" src="/img/pie.png" alt="Generic placeholder image">
				  <div class="media-body mt-5 mx-5">
				    <h5 class="mt-0 mb-3">Pie</h5>
				    <p>A pie chart is a circular statistical graphic which is divided into slices to illustrate numerical proportion.</p>
				  	<p class="text-primary">Use a pie chart if you are interested in comparing data that is independent of time</p>
				  	<button onclick="location.href='/pie'" class="btn btn-success">Start</button>
				  </div>
				</div>
			</div>
		</div>
	</body>
</html>