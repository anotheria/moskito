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
					<li class="active"><a href="<msk:write name="mskShowAllProducers"/>">Producers</a>					
					<div class="sub_menu">
						<ul>
							<msk:present name="intervals" scope="request">
								<li>
									<span>Interval:</span>
									<select onchange="javascript:handleSelect(this)">									
							 			<jsp:include page="IntervalSelection.jsp" flush="false" />			
									</select>								
								</li>
							</msk:present>
							<msk:present name="units" scope="request">
								<li>	
									<span>Unit:</span>
									<select onchange="javascript:handleSelect(this)">									
										<jsp:include page="UnitSelection.jsp" flush="false" />
									</select>
								</li>
							</msk:present>							
							<msk:present name="categories" scope="request">
								<li>								
									<span>Category:</span>
									<select onchange="javascript:handleSelect(this)">
										<jsp:include page="CategorySelection.jsp" flush="false" />
									</select>							
								</li>
						    </msk:present>
						    	
							<msk:present name="subsystems" scope="request">
								<li class="separator">								
									<span>Subsystem:</span>
									<select onchange="javascript:handleSelect(this)">
										<jsp:include page="SubsystemSelection.jsp" flush="false" />
									</select>								
								</li>
							</msk:present>
						    <div class="clear"><!-- --></div>
						</ul>
					</div>
					<div class="over_color"><div><!-- --></div></div>
					</li>						
										
					<li><a href="<msk:write name="mskShowUseCases"/>">Use Cases</a></li>
					<li><a href="<msk:write name="mskShowMonitoringSessions"/>">Monitoring Sessions</a></li>
				</ul>
				<div class="right"></div>
				<jsp:include page="ExportMenu.jsp" flush="false" />
		</div>
	</div>
</div>