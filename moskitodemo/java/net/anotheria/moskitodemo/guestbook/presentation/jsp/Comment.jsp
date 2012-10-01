<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

	<title>Moskito Guestbook Overview</title>
	<jsp:include page="Pragmas.jsp"/>
	<link type="text/css" rel="stylesheet" rev="stylesheet" href="/moskitodemo/css/common.css"/>
	<link type="text/css" rel="stylesheet" rev="stylesheet" href="/moskitodemo/css/gb.css"/>
	<script type="text/javascript" src="/moskitodemo/js/jquery-1.4.min.js"></script>
	<script type="text/javascript" src="/moskitodemo/js/guestbookController.js"></script>
</head>

	<body>
<div class="main">
	<div class="header home">
		<div class="w_980">
			<ul class="main_menu">
				<li><a href="http://moskito.anotheria.net/">Home</a></li>
				<li class="active"><a href="http://moskito.anotheria.net/moskitodemo/">Demo</a></li>
				<li class="logo"><a href="http://moskito.anotheria.net/"><img src="/moskitodemo/images/logo.png"
																			  alt=""/></a></li>
				<li><a href="https://confluence.opensource.anotheria.net/display/MSK/Home">Docs</a></li>
				<li><a href="https://confluence.opensource.anotheria.net/display/MSK/01+How+To+Get">Download</a></li>
			</ul>

		</div>
	</div>

	<div class="w_980 pad">
		<div class="">
			<h2>Guestbook</h2>

			<div class="cont">
				<div class="top">
					<div><!-- --></div>
				</div>
				<div class="in">
					<h3>Welcome to Moskito's Guestbook</h3>

					<p>Moskito's guestbook is an example application for moskito-struts integration. At the same
						time it
						offers us the possibility to get some feedback from you, dear user. Therefore we stronly
						encourage you to post a message.<br/>
						Thank you.<br/>
						The moskito team.</p>

					<div class="guestbook">
						<div class="top"></div>
						<div class="in">
							<div class="paper_bg">

								<ul>
									<li>
										<div>Date:</div><span><ano:write name="comment" property="date"/>&nbsp;(Central European Time)</span>
									</li>
									<li>
										<div>Author:</div><span><ano:write name="comment" property="firstName"/>&nbsp;<ano:write name="comment" property="lastName"/></span>
									</li>
									<li>
										<div>Email:</div><span><ano:write name="comment" property="email"/></span>
									</li>
									<li>
										<div>Message:</div><span><ano:write name="comment" property="text"/></span>
									</li>
								</ul>



								<div class="clear"></div>
								<a href="gbookShowComments" class="gb_btn ml_63">Back</a>


							</div>
						</div>
						<div class="bot"></div>
					</div>

				</div>
				<div class="bot">
					<div><!-- --></div>
				</div>
			</div>
		</div>


		<div class="clear"><!-- --></div>
		<div class="empty"><!-- --></div>
	</div>
</div>
<jsp:include page="Footer.jsp" flush="true"/>

</body>
</html>