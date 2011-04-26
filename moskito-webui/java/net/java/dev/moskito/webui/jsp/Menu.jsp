<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>
<%@ page isELIgnored="false" %>

<div class="main_menu">
	<div class="white_pl">
		<div class="top">
			<div class="left_bg"><!-- --></div>
				<a href="mskShowAllProducers" class="logo"><img alt="MoSKito WebUI" src="<ano:write name="mskPathToImages" scope="application"/>moskito_webui_logo.gif" width="131" height="25"/></a>
				<ul>
				<%--
                    <li <ano:equal name="currentNaviItem" value="DASHBOARD">class="active"</ano:equal>>
                        <a href="mskDashBoard">Dashboard (Alpha)</a>
                    </li>
                --%>
					<ano:equal name="currentNaviItem" value="PRODUCERS">
                        <li class="active"><a href="mskShowAllProducers">Producers</a>
						<div class="sub_menu">
							<ul>
								<ano:present name="intervals" scope="request">
									<li>
										<span>Interval:</span>
										<select onchange="javascript:handleSelect(this)">									
								 			<jsp:include page="IntervalSelection.jsp" flush="false" />			
										</select>								
									</li>
								</ano:present
								><ano:present name="units" scope="request">
									<li class="separator">	
										<span>Unit:</span>
										<select onchange="javascript:handleSelect(this)">									
											<jsp:include page="UnitSelection.jsp" flush="false" />
										</select>
									</li>
								</ano:present
								><ano:present name="categories" scope="request">
									<li>								
										<span>Category:</span>
										<select onchange="javascript:handleSelect(this)">
											<jsp:include page="CategorySelection.jsp" flush="false" />
										</select>							
									</li>
							    </ano:present
							    ><ano:present name="subsystems" scope="request">
									<li>
										<span>Subsystem:</span>
										<select onchange="javascript:handleSelect(this)">
											<jsp:include page="SubsystemSelection.jsp" flush="false" />
										</select>								
									</li>
								</ano:present>
							    <div class="clear"><!-- --></div>
							</ul>
						</div>
						<div class="over_color"><div><!-- --></div></div>
						</li>
					</ano:equal
					><ano:notEqual name="currentNaviItem" value="PRODUCERS">
						<li><a href="mskShowAllProducers">Producers</a></li>	
					</ano:notEqual
					><%--ano:equal name="currentNaviItem" value="USECASES">
						<li class="active"><a href="mskShowUseCases">Use Cases</a></li>
					</ano:equal
					><ano:notEqual name="currentNaviItem" value="USECASES">
						<li><a href="mskShowUseCases">Use Cases</a></li>
					</ano:notEqual
					--%>
                    <li <ano:equal name="currentNaviItem" value="SESSIONS">class="active"</ano:equal>>
                        <a href="mskShowMonitoringSessions">Monitoring Sessions</a>
                    </li>
                    <li <ano:equal name="currentNaviItem" value="CHARTS">class="active"</ano:equal>>
                        <a href="mskShowCharts">Charts</a>
                    </li>
					<li <ano:equal name="currentNaviItem" value="THRESHOLDS">class="active"</ano:equal>>
                        <a href="mskThresholds"><img src="<ano:write name="mskPathToImages" scope="application"/>ind_<ano:write name="systemStatusColor"/>_small.png" alt="System status: <ano:write name="systemStatus"/>"/>&nbsp;&nbsp;Thresholds</a>
                    </li>
                    <li <ano:equal name="currentNaviItem" value="ACCUMULATORS">class="active"</ano:equal>>
                        <a href="mskAccumulators">Accumulators (beta)</a>
                    </li>
					
				</ul>
				<div class="right"></div>
				<jsp:include page="ExportMenu.jsp" flush="false" />
		</div>
	</div>
</div>