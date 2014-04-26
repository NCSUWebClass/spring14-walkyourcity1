<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"
    import = "java.util.List"
    import = "com.walkthiscity.beans.Location"
    import = "com.walkthiscity.beans.PlacesBean"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Walk This City</title>
<link href = "CSS/yelp.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<form action = "SearchServlet" method = "post">
		Enter Search Term: <input type = "text" name = "searchTerm"><br>
		Enter Address: <input type = "text" name = "address"><br>
		<input type = "submit" name = "submitButton" value = "Submit">		
	</form>
	
	<%
	if(request.getAttribute("locations") != null)
	{
		List<Location> locations = (List<Location>)request.getAttribute("locations");
	%>
	<div>
		<center><table>
			<tr>
				<th>Name</th>
				<th>Address</th>
				<th>Rating</th>
				<th>Phone Number</th>
			</tr>
			<%
			for(Location loc : locations)
			{
			%>
				<tr>
					<td><%=loc.getName()%></td>
					<td><%=loc.getAddress()%></td>
					<td><%=loc.getRating()%></td>
					<td><%=loc.getPhoneNumber()%></td>
				</tr>
			<%	
			}
			%>
		</table></center>
	</div>
	<%
	}
	%>
	<form action = "PlacesServlet" method = "post">
		Enter Search Term for Google Places: <input type = "text" name = "searchTerm"><br>
		Enter Latitude: <input type = "text" name = "lat"><br>
		Enter Longitude: <input type = "text" name = "lon"><br>
		<input type = "submit" name = "submitButton" value = "Submit">		
	</form>
	
	<%
	if(request.getAttribute("places") != null)
	{
		List<PlacesBean> placesBean = (List<PlacesBean>)request.getAttribute("places");
	%>
	<div>
		<center><table>
			<tr>
				<th>Name</th>
				<th>Address</th>
				<th>Rating</th>
				<th>Phone Number</th>
			</tr>
			<%
			for(PlacesBean place : placesBean)
			{
			%>
				<tr>
					<td><%=place.getName()%></td>
					<td><%=place.getAddress()%></td>
					<td><%=place.getRating()%></td>
					<td><%=place.getPhoneNumber()%></td>
				</tr>
			<%	
			}
			%>
		</table></center>
	</div>
	<%
	}
	%>
</body>
</html>