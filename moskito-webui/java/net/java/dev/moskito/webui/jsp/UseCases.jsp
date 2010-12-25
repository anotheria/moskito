<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito UseCases</title>
<link rel="stylesheet" href="mskCSS">
</head>
<body>
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>

<jsp:include page="Menu.jsp" flush="false"/>


<div class="main">	
	<div class="clear"><!-- --></div>
		
	<h3><span>Show Use Cases</span></h3>
	
	<div class="clear"><!-- --></div>
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
			<div>				
				<h3><span>AdHoc Use Cases</span></h3>					
				<h3><span>To record an addHoc use case add <code>mskCommand=recordUseCase&mskUseCaseName=USE-CASE-NAME</code> to any url on this server except this.</span></h3>
				<h3><span>Permanent Use Cases</span></h3>			
			</div>
		</div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	
	<div class="clear"><!-- --></div>
	<msk:present name="recordedAvailableFlag" scope="request">	
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
					<table cellpadding="0" cellspacing="0" width="100%">	
					<thead>
						<tr class="stat_header">
							<th>#</th>
							<th>Name</th>
							<th>Date</th>							
						</tr>
					</thead>
					<tbody>
						<msk:iterate name="recorded" type="net.java.dev.moskito.webui.bean.RecordedUseCaseListItemBean" id="useCase" indexId="index">
							<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
								<td><msk:write name="index"/></td>
								<td><a href="mskShowRecordedUseCase?pUseCaseName=<msk:write name="useCase" property="nameEncoded"/>">
									<msk:write name="useCase" property="name"/>	</a></td>
							    <td><msk:write name="useCase" property="date"/></td>
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
	</msk:present>	

	
	<msk:notPresent name="recordedAvailableFlag" scope="request">
		<i>No use cases recorded yet</i>
	</msk:notPresent>
	
</div>	
	
</body>
</html>