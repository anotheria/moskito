<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"%>

<div class="main_menu">
	<div class="white_pl">
		<div class="top">
			<div class="left_bg"><!-- --></div>
				<a href="#" class="logo">
					<img alt="MoSKito WebUI" src="<msk:write name="mskPathToImages" scope="application"/>moskito_webui_logo.gif" width="131"	height="25"/>
				</a>
				<ul>
					<li><a href="<msk:write name="mskShowAllProducers"/>">All Producers</a></li>
					<li>
						<a href="<msk:write name="mskShowProducersByCategory"/>">Categories</a>
						<div class="sub_menu">
								<msk:present name="categories" scope="request">
								<ul>
									<jsp:include page="CategorySelection.jsp" flush="false" />	
								</ul>
								</msk:present>			
						</div>
						<div class="over_color"><div><!-- --></div></div>
					</li>
					<li><a href="<msk:write name="mskShowProducersBySubsystem"/>">Subsystems</a>
						<div class="sub_menu">
								<msk:present name="subsystems" scope="request">
								<ul>
									<jsp:include page="SubsystemSelection.jsp" flush="false" />
								</ul>
								</msk:present>													
						</div>
						<div class="over_color"><div><!-- --></div></div>
					</li>
					<li><a href="<msk:write name="mskShowUseCases"/>">Use Cases</a></li>
					<li class="active">
						<a href="<msk:write name="mskShowMonitoringSessions"/>">Monitoring Sessions</a>
					</li>
				</ul>
				<div class="right"><!-- --></div>
				<ul class="settings">
					<li><a href="#">Settings</a>
					<div class="sub_menu">
						<ul>
							<li><a href="<msk:write name="linkToCurrentPageAsXml"/>&amp;pForward=xml">XML</a></li>
							<li><a href="<msk:write name="linkToCurrentPageAsCsv"/>&amp;pForward=csv">CSV</a></li>
						</ul>
					</div>
					<div class="over_color"><div><!-- --></div></div>
					</li>
					<li><a href="#">Help</a></li>
				</ul>

		</div>
	</div>
</div>