<%@ page import="hr.fer.zemris.java.hw13.servlets.SetColorServlet.ColorCombination" contentType="text/css; charset=UTF-8" pageEncoding="UTF-8" %>

<% 
	ColorCombination c = (ColorCombination) session.getAttribute("pickedBgCol");
	if (c == null){
		c = new ColorCombination("#EFEFEF", "white", "rgba(0, 0, 0, 0.87)");
	}
%>

html{
	background-color: <%= c.getBgColor() %>;
	color: <%= c.getFgColor() %>;
	font-family: Helvetica;
}
body{
	background-color: <%= c.getBgColor2() %>;
	width: 80%;
	margin: 20px auto;
	padding: 50px;
	box-shadow: 0 0 3px rgba(0, 0, 0, 0.87);
}
h2{
	font-size: 2em;
}
a{
	color: inherit;
}
#goHome{
	display: block;
	float: right;
	font-size: 1.5em;
	font-family: Arial;
	text-decoration: none;
	color: #0288D1;
	text-shadow: -1px -1px rgba(255, 255, 255, 0.4);
}
	#goHome:hover{
		color: #01579B;
		text-decoration: underline;
	}