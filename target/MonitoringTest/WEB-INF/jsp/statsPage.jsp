<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Application Health</title>
	</head>
	<body>
		<h1>Application health</h1>
		
		<div>
			<table>
				<tr>
					<td>Status</td>
					<td>Used Memory</td>
					<td>Free Memory</td>
					<td>Total memory</td>
					<td>Used Disk Space</td>
					<td>Free Disk Space</td>
					<td>Total Disk Space</td>
					<td>1 Min CPU Load Average</td>
					<td>Load Average Per Core</td>
				</tr>
				<c:forEach items="${stats}" var="s" >
					<td>${s.status}</td>
					<td>${s.memUsed} MiB</td>
					<td>${s.memFree} MiB</td>
					<td>${s.mem} MiB</td>
					<td>${s.diskUsed} MiB</td>
					<td>${s.diskFree} MiB</td>
					<td>${s.diskSpace} MiB</td>
					<td>${s.loadAverage}</td>
					<td>${s.loadAvgPerCore}</td>
				</c:forEach>
			</table>
		</div>
	</body>
</html>