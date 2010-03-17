<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
	<title>Moskito Recorded UseCase: <msk:write name="recordedUseCase" property="name"/></title>
<link rel="stylesheet" href="mskCSS">
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>

<div class="main">	
	<div class="clear"><!-- --></div>
		
	<h3><span>Show Recorded Use Case: <msk:write name="recordedUseCase" property="name"/></span></h3>
	
	<div class="clear"><!-- --></div>
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
			<div>
				<msk:write name="recordedUseCase" property="date"/>&nbsp; (<msk:write name="recordedUseCase" property="created"/>)				
			</div>
		</div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	<div class="clear"><!-- --></div>	
	<div class="table_layout">
		<div class="top"><div><!-- --></div></div>
		<div class="in">		
			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">				
					<table cellpadding="4" cellspacing="0" width="100%">
						<thead>
							<tr class="stat_header">
								<th>Call</th>
								<th>Gross duration</th>
								<th>Net duration</th>
								<th width="1%">Aborted</th>
							</tr>
						</thead>	
						<tbody>				
							<msk:iterate name="recordedUseCase" property="elements" type="net.java.dev.moskito.webui.bean.UseCasePathElementBean" id="element" indexId="index">
								<tr>
									<msk:equal name="element" property="aborted" value="true"><tr class="stat_error"></msk:equal>
									<msk:notEqual name="element" property="aborted" value="true"><tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>"></msk:notEqual>
									<td><% for (int i=1; i<element.getLayer(); i++){ %><img src="../img/s.gif"/><%}%><msk:equal name="element" property="root" value="false"><img src="../img/l.gif"/></msk:equal><msk:write name="element" property="call"/></td>
									<td><msk:write name="element" property="duration"/></td>
									<td><msk:write name="element" property="timespent"/></td>
									<td><msk:equal name="element" property="aborted" value="true">X</msk:equal></td>
									</tr>
							</msk:iterate>
						</tbody>
					</table>
				<div class="clear"><!-- --></div>
						</div>
								<div class="bot">
									<div class="left"><!-- --></div>
									<div class="right"><!-- --></div>
								</div>
							</div>
				   </div>
	</div>		
</div>
</body>
</html>