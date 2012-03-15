<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %>
<%@ page isELIgnored ="false" %>
<html>
<head>
	<title>Moskito Web Control - Login Page</title>
	<style>
		.r1, .r2,  .r3, .r4 { display: block; height: 1px; background: #E9F0F7; overflow: hidden; }
		.r1 {  margin: 0 5px; }
		.r2 {  margin: 0 3px; }
		.r3 {  margin: 0 2px; }
		.r4 {  margin: 0 1px; height: 2px; }		
		.login_form { background: none repeat scroll 0 0 #E9F0F7; padding:5px; margin-bottom: 0; line-height: 25px; }		
		.name, .error, .centered { font-family: Arial,Helvetica,sans-serif; font-size: 1em; }
		.name, .centered { color: #005FC1; }
		.error { color: #E46C6D; }
	</style>
</head>
<body>
<div class="centered">
	<span class="r1"></span><span class="r2"></span><span class="r3"></span><span class="r4"></span>
    <form action="" method="POST" class='login_form'>
        <fieldset style="border:1px #99CCCC solid;">
            <legend>Login</legend>
            <table>
            	<tr>
            		<td><label class='name'>User name: </label></td>
            		<td><input type="text" name="login" autocomplete='off' value="${username}"/></td>
            		<td>
            			<ano:notEmpty name="mwc_error_msg"><ano:equal name="mwc_error_msg" property="fieldName" value="login">
	            			<span class='error'>${mwc_error_msg.errorText}</span>
	            		</ano:equal></ano:notEmpty>
            		</td>
           		</tr>
            	<tr>
            		<td><label class='name'>Password: </label></td>
            		<td><input type="password" name="password" autocomplete='off' value="${password}"/></td>
            		<td>
            			<ano:notEmpty name="mwc_error_msg"><ano:equal name="mwc_error_msg" property="fieldName" value="password">
            				<span class='error'>${mwc_error_msg.errorText}</span>
            			</ano:equal></ano:notEmpty>
            		</td>
           		</tr>
            	<tr>
            		<td><input type="submit" value="Login" id="submit_button" /></td>
            		<td></td>
            		<td></td>
           		</tr>
            </table>
        </fieldset>
    </form>
    <span class="r4"></span><span class="r3"></span><span class="r2"></span><span class="r1"></span>
</div>
<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$(window).resize(function() {
		$('.centered').css( { position:'absolute' } ); 
		$('.centered').css(
			{ 
				left: ($(window).width()-$('.centered').outerWidth())/2, 
				top: ($(window).height()-$('.centered').outerHeight())/2
			}
		);
	});
	
	$(window).resize();
});
</script>
</body>
</html>