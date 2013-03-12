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
                        <li class="active"><a href="mskShowAllProducers" class="producers"><i></i>Producers</a>
						<div class="sub_menu">
							<ul>
								<ano:present name="intervals" scope="request">
									<li class="separator">
										<span>Interval:</span>
										<select onchange="javascript:handleSelect(this)">									
								 			<jsp:include page="IntervalSelection.jsp" flush="false" />			
										</select>
                                        <span class="vline"></span>
									</li>
								</ano:present
								><ano:present name="units" scope="request">
									<li class="separator">	
										<span>Unit:</span>
										<select onchange="javascript:handleSelect(this)">									
											<jsp:include page="UnitSelection.jsp" flush="false" />
										</select>
                                        <span class="vline"></span>
									</li>
								</ano:present
								><ano:present name="categories" scope="request">
									<li class="separator">								
										<span>Category:</span>
										<select onchange="javascript:handleSelect(this)">
											<jsp:include page="CategorySelection.jsp" flush="false" />
										</select>
                                        <span class="vline"></span>
									</li>
							    </ano:present
							    ><ano:present name="subsystems" scope="request">
									<li class="separator">
										<span>Subsystem:</span>
										<select onchange="javascript:handleSelect(this)">
											<jsp:include page="SubsystemSelection.jsp" flush="false" />
										</select>
                                        <span class="vline"></span>
									</li>
								</ano:present>
								<%-- we use subsystems here, because they are present on producers page, we don't want the name filter on the producer detail page --%>
								<ano:present name="subsystems" scope="request">
									<li class="separator">
										<form action="" method="GET"><input name="pNameFilter" type="text" size="10" value="<ano:write name="nameFilter"/>"/>&nbsp;<input type="submit" value="Filter"/></form>
									</li>
								</ano:present>
							</ul>
						</div>
						<div class="over_color"><div><!-- --></div></div>
						</li>
					</ano:equal
					><ano:notEqual name="currentNaviItem" value="PRODUCERS">
						<li><a href="mskShowAllProducers" class="producers"><i></i>Producers</a></li>
					</ano:notEqual
					><%--ano:equal name="currentNaviItem" value="USECASES">
						<li class="active"><a href="mskShowUseCases">Use Cases</a></li>
					</ano:equal
					><ano:notEqual name="currentNaviItem" value="USECASES">
						<li><a href="mskShowUseCases">Use Cases</a></li>
					</ano:notEqual
					--%>
                    <li <ano:equal name="currentNaviItem" value="JOURNEYS">class="active"</ano:equal>>
                        <a href="mskShowJourneys"class="journeys"><i></i>Journeys</a>
                    </li>
					<li <ano:equal name="currentNaviItem" value="THRESHOLDS">class="active"</ano:equal>>
                        <a href="mskThresholds"class="thresholds"><i><img src="<ano:write name="mskPathToImages" scope="application"/>ind_<ano:write name="systemStatusColor"/>.png" alt="System status: <ano:write name="systemStatus"/>"/></i>Thresholds</a>
                    </li>
                    <li <ano:equal name="currentNaviItem" value="ACCUMULATORS">class="active"</ano:equal>>
                        <a href="mskAccumulators"class="accumulators"><i></i>Accumulators</a>
                    </li>
                    <li class="main_menu_threads <ano:equal name="currentNaviItem" value="THREADS">active</ano:equal>">
                        <a href="mskThreads" class="threads"><i></i>Threads</a>
						<div class="sub_menu">
							<ul>
								<li class="separator">
									<a href="mskThreadsList">List</a>
                                    <span class="vline"></span>
								</li>
								<li class="separator">
									<a href="mskThreadsDump">Dump</a>
                                    <span class="vline"></span>
								</li>
								<li>
									<a href="mskThreadsHistory">History</a>
								</li>
							</ul>
						</div>
						<div class="over_color"><div><!-- --></div></div>
                    </li>
                    <li class="main_menu_threads <ano:equal name="currentNaviItem" value="MORE">active</ano:equal>">
                        <a href="mskMore" class="more"><i></i>More</a>
                        <div class="sub_menu">
                            <ul>
                                <li class="separator">
                                    <a href="mskConfig">Config</a>
                                    <span class="vline"></span>
                                </li>
                                <li class="separator">
                                    <a href="mskMBeans">MBeans</a>
                                    <span class="vline"></span>
                                </li>
                                <li class="separator">
                                    <a href="mskLibs">Libs</a>
                                    <span class="vline"></span>
                                </li>
                                <li>
                                    <a href="#">Update</a>
                                </li>
                            </ul>
                        </div>
                        <div class="over_color"><div><!-- --></div></div>
                    </li>

                </ul>
				<div class="right"></div>
				<jsp:include page="ExportMenu.jsp" flush="false" />
                <jsp:include page="AutoreloadMenu.jsp" flush="false" />
		</div>
	</div>
</div>