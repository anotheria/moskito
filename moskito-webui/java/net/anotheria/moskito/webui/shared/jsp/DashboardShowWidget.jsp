<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>
<%@ page isELIgnored="false"%>

<div class="table_layout">
    <div class="top">
        <div><!-- --></div>
    </div>
    <div class="in">
        <h2>${widget.name}</h2>
        <a href="?deleteWidget=${widget.name}" class="help">Close</a>
        <a href="#" class="edit">Edit</a>

        <div class="clear"><!-- --></div>

        <div class="table_itseft">
            <div class="top">
                <div class="left"><!-- --></div>
                <div class="right"><!-- --></div>
            </div>
            <div class="in">
                <ano:iterate name="widget" property="producerGroups" id="group">
                    <ano:iF test="${group.selectedGroup}">
                        <h3>${group.groupName}</h3>
                        <div class="scroller_x">
                            <table width="100%" cellspacing="0" cellpadding="0">
                                <thead>
                                    <tr>
                                        <th>Producer</th>
                                        <ano:iterate name="group" property="captions" id="caption">
                                            <ano:equal name="caption" property="selectedCaption" value="true">
                                                <th>${caption.captionName}</th>
                                            </ano:equal>
                                        </ano:iterate>
                                    </tr>
                                </thead>
                                <tbody>
                                <ano:iterate name="group" property="producers" id="producer">
                                    <ano:equal name="producer" property="selectedProducer" value="true">
                                        <tr class="even">
                                            <td><a href="#">${producer.id}</a></td>
                                            <ano:iterate name="producer" property="values" id="value">
                                                <td>${value}</td>
                                            </ano:iterate>
                                        </tr>
                                    </ano:equal>
                                </ano:iterate>
                                </tbody>
                            </table>
                        </div>
                    </ano:iF>
                </ano:iterate>

                <div class="clear"><!-- --></div>
            </div>
            <div class="bot">
                <div class="left"><!-- --></div>
                <div class="right"><!-- --></div>
            </div>

        </div>
    </div>
    <div class="bot">
        <div class="left"><!-- --></div>
        <div class="right"><!-- --></div>
    </div>

    <!-- Editing widget(table) overlay-->
    <div class="edit_widget_overlay" style="display:none;">
    <form action="mskDashBoard" class="create_widget_form" method="post">
    <h2>Edit widget</h2>
    <label for="name">Widget name:</label>
    <input type="text" name="widgetName" value="${widget.name}" id="name"/>

    <div class="widget_type">
        <ano:equal name="widget" property="type" value="TABLE">
            <input type="radio" id="t_table" name="widget_type" checked="checked"/><label for="t_table">Table widget</label>
        </ano:equal>
        <ano:equal name="widget" property="type" value="CHART">
            <input type="radio" id="t_chart" name="widget_type"/><label for="t_chart">Chart widget</label>
        </ano:equal>
        <ano:equal name="widget" property="type" value="THRESHOLD">
            <input type="radio" id="t_threshold" name="widget_type"/><label for="t_threshold">Threshold widget</label>
        </ano:equal>
        <div class="clear"></div>
    </div>

    <div class="t_table">

        <div class="t_table_prod_group">
            <h3>Producer group</h3>
            <ul>
                <ano:iterate name="widget" property="producerGroups" id="group" indexId="indexId">
                    <li class="<ano:iF test="${group.selectedGroup}">checked</ano:iF><ano:equal name="indexId" value="0"> active</ano:equal>">
                        <div class="top_l"></div>
                        <div class="bot_l"></div>
                        <a href="#" class="uncheck">
                            <img src="<ano:write name="mskPathToImages" scope="application"/>close.png" alt="Uncheck" title="Uncheck"/>&nbsp;
                        </a>
                        <a href="#" class="${group.groupName}">${group.groupName}</a>
                    </li>
                </ano:iterate>
            </ul>
        </div>

        <div class="right_part">
            <div class="top_r"></div>
            <div class="bot_r"></div>
            <div class="t_table_val">
                <h3>Value</h3>
                <ano:iterate name="widget" property="producerGroups" id="group" indexId="indexId">
                    <ul class="<ano:write name="group" property="groupName"/>_val" <ano:notEqual name="indexId" value="0">style="display:none;"</ano:notEqual>>
                        <li><input type="checkbox" id="c_all" /><label>Select all</label></li>
                        <ano:iterate name="group" property="captions" id="caption">
                            <li>
								<input type="checkbox" <ano:iF test="${caption.selectedCaption}"> checked="checked"</ano:iF> name="${group.groupName}_${caption.captionName}"/>
								<label>${caption.captionName}</label>
							</li>
                        </ano:iterate>
                    </ul>
                </ano:iterate>
            </div>

            <div class="t_table_prod">
                <h3>Producer</h3>
                <ano:iterate name="widget" property="producerGroups" id="group" indexId="indexId">
					<ul class="${group.groupName}_prod" <ano:notEqual name="indexId" value="0">style="display:none;"</ano:notEqual>>
						<li><input type="checkbox" id="c_all"/><label>Select all</label></li>

						<ano:iterate name="group" property="producers" id="producer" >
							<li>
								<input type="checkbox" <ano:iF test="${producer.selectedProducer}">checked="checked"</ano:iF> name="${producer.id}"/>
								<label>${producer.id}</label>
							</li>
						</ano:iterate>
					</ul>
                </ano:iterate>
            </div>

            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>

    <div class="clear"></div>

    <div class="flr">
        <input type="submit" value="Create"/><span>&nbsp;&nbsp;or&nbsp;&nbsp;</span>
		<a onclick="closeLightbox(); return false;" href="#">Cancel</a>
    </div>
    </form>
    </div>
    <!-- Editing widget(table) overlay END-->
</div>