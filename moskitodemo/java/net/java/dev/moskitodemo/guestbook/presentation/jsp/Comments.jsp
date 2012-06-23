<%@ page language="java" contentType="text/html;charset=utf-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><%@ page isELIgnored="false" %>
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
				<li class="logo"><a href="http://moskito.anotheria.net/"><img src="/moskitodemo/images/logo.png" alt=""/></a></li>
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
						time it offers us the possibility to get some feedback from you, dear user. Therefore we stronly
						encourage you to post a message.<br/>
						Thank you.<br/>
						The moskito team.</p>

					<div class="guestbook">
						<div class="top"></div>
						<div class="in">
							<div class="paper_bg">
								<form action="gbookCreateComment" id="CommentForm" name="CommentForm">
									<div class="left_f">
										<label for="name">First name:</label><input type="text" name="firstName" maxlength="100" style="name"/>
										<label for="sname">Last name:</label><input type="text" name="lastName" maxlength="100" style="sname"/>
										<label for="mail">Email:</label><input type="text" name="email" maxlength="100" style="mail"/>
									</div>
									<div class="right_f">
										<label for="mess">Message:</label>
										<textarea name="text" rows="" cols="" style="mess"></textarea>
									</div>
									<div class="clear"></div>
									<input type="checkbox" name="updateFlagChecked" style="notify"/><label for="notify" class="notify_label">
									I wish to be notified about new MoSKito versions.</label>

									<div class="clear"></div>
									<a href="#" onClick="document.CommentForm.submit(); return false"
									   class="gb_btn ml_75">Submit</a>

									<div class="clear"></div>
								<form>
								<div class="space"></div>

								<table cellpadding="0" cellspacing="0" border="0" width="100%">
									<thead>
									<tr class="lineCaptions">
										<ano:iterate name="headers" id="header"
													   type="net.java.dev.moskitodemo.guestbook.presentation.bean.CommentTableHeaderBean">
											<th><ano:write name="header" property="caption"/>&nbsp;
												<ano:iterate name="header" property="links"
															   type="net.java.dev.moskitodemo.guestbook.presentation.bean.SortLinkBean"
															   id="link">
													<ano:equal name="link" property="active" value="true">
														<b><ano:write name="link" property="caption"/></b>
													</ano:equal>
													<ano:equal name="link" property="active" value="false">
														<a href="gbookShowComments?<ano:write name="link" property="link"/>"><ano:write
																name="link" property="caption"/></a>
													</ano:equal>
												</ano:iterate>
											</th>
										</ano:iterate>
										<ano:equal name="authorized" value="true">
											<th>&nbsp;</th>
										</ano:equal>
									</tr>
									</thead>


										<%--<thead>--%>
										<%--<tr>--%>
										<%--<th><a href="#" class="up">Date</a></th>--%>
										<%--<th><a href="#" class="down">First name</a></th>--%>
										<%--<th><a href="#" class="">Last name</a></th>--%>
										<%--<th><a href="#" class="">Email</a></th>--%>
										<%--<th><a href="#" class="">Message</a></th>--%>
										<%--</tr>--%>
										<%--</thead>--%>
									<tbody>
									<ano:iterate name="comments" id="comment"
												   type="net.java.dev.moskitodemo.guestbook.presentation.bean.CommentTableItemBean"
												   indexId="i">
										<tr><%--<tr class="<%=i.intValue()%2==1	 ? "lineDark" : "lineLight"%>">--%>
											<td>
												<a href="gbookShowComment?pComment=<ano:write name="comment" property="id"/>"><ano:write
														name="comment" property="date"/></a></td>
											<td>
												<a href="gbookShowComment?pComment=<ano:write name="comment" property="id"/>"><ano:write
														name="comment" property="firstName"/></a></td>
											<td>
												<a href="gbookShowComment?pComment=<ano:write name="comment" property="id"/>"><ano:write
														name="comment" property="lastName"/></a></td>
											<td>
												<a href="gbookShowComment?pComment=<ano:write name="comment" property="id"/>"><ano:write
														name="comment" property="email"/></a></td>
											<td>
												<a href="gbookShowComment?pComment=<ano:write name="comment" property="id"/>"><ano:write
														name="comment" property="comment"/></a></td>
											<ano:equal name="authorized" value="true">
												<td>
													<a href="gbookDeleteComment?pComment=<ano:write name="comment" property="id"/>">Delete</a>
												</td>
											</ano:equal>
										</tr>
									</ano:iterate>

									<tr>
										<td colspan="5">
											
										</td>
									</tr>
									<tr>
										<td colspan="5">
                                            <ano:size id="sizePages" name="pagination" property="elements"/>
                                            <ano:greaterThan name="sizePages" value="1">
                                                <ul class="paginator">
                                                    <ano:equal name="pagination" property="hasPrevious" value="true">
                                                        <li class="prev"><a href="?page=${pagination.previousPageNumber}">prev</a></li>
                                                    </ano:equal>

                                                    <ano:iterate name="pagination" property="elements" id="element" indexId="pageNumberIndex">
                                                        <li <ano:equal name="element" property="caption" value="${pagination.currentPageNumber}">class="cur"</ano:equal>>
                                                            <ano:equal name="element" property="separator" value="false">
                                                                <a href="?page=${element.caption}">
                                                                    <ano:write name="element" property="caption"/>
                                                                </a>
                                                           </ano:equal>
                                                        </li>
                                                    </ano:iterate>

                                                    <ano:equal name="pagination" property="hasNext" value="true">
                                                        <li class="next"><a href="?page=${pagination.nextPageNumber}">next</a></li>
                                                    </ano:equal>
                                                </ul>
                                            </ano:greaterThan>
										</td>
									</tr>
									</tbody>
								</table>
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
	<%--<div class="footer">--%>
	<%--<div class="fll">--%>
	<%--<ul>--%>
	<%--<li><a href="#">Home</a>&nbsp;&nbsp;|&nbsp;&nbsp;</li>--%>
	<%--<li><a href="#">Demo</a>&nbsp;&nbsp;|&nbsp;&nbsp;</li>--%>
	<%--<li><a href="#">Docs</a>&nbsp;&nbsp;|&nbsp;&nbsp;</li>--%>
	<%--<li><a href="#">Download</a></li>--%>
	<%--</ul>--%>
	<%--<span>Copyright &copy; 2006-2010 MoSKito</span>--%>
	<%--</div>--%>
	<%--<div class="flr">--%>
	<%--<a href="#"><img src="images/powered.png" alt="Powered by Anotheria.net"/></a>--%>
	<%--</div>--%>

	<%--</div>--%>
<jsp:include page="Footer.jsp" flush="true"/>

</body>


<%--<body>--%>


<%--<br/><br/>--%>
<%--<a href="gbookNewComment">&raquo;&nbsp;post a comment!</a>--%>
<%--<jsp:include page="Footer.jsp" flush="true"/>--%>
<%--</body>--%>
</html>