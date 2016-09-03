<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="WEB-INF/pages/header.jsp" %>
<%!
/**
 * Generates a random color. Returns it in HEX color format (RRGGBB).
 * @return The random color in HEX format.
 */
String randomColor(){
	java.util.Random random = new java.util.Random();
	String color = Integer.toString(random.nextInt(255*255*255), 16);
	
	while(color.length() < 6){
		color = "0" + color;
	}
	return color;
}
%>

<div style="color: #<%= randomColor() %>; margin: 10% auto; font-size: 1.3em; font-family: Georgia; width: 250px; text-align: center;">
	Programmer joke:<br>
	<pre style="margin: 40px 30px 30px;">!false</pre>
	<div style="text-align: right;">
		It's funny<br>
		because it's true.
	</div>
</div>

</body>
</html>
