<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
	<script>
		YAHOO.util.Event.onDOMReady(function(){

			var scope = {},
				tableColumnDefs,
				tableDataSource,
				table,
				container;

			scope.tables = [];
				
			var updateProducers = function(){				
				for(var i in this.tables)
					this.tables[i].refresh(Moskito.getQuery());
			} 
			
			<logic:iterate type="net.java.dev.moskito.webui.bean.StatDecoratorBean" id="decorator" name="decorators">
			tableColumnDefs = [
				{key:"name", label:"Name",sortable:true,resizeable:true},
				<logic:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind">
				{key:"<bean:write name="caption" property="caption"/>", label:"<bean:write name="caption" property="caption"/>"/*,formatter:YAHOO.widget.DataTable.formatNumber*/,sortable:true,resizeable:true},
				</logic:iterate>
			];
		
			tableDataSource = new YAHOO.util.DataSource('mskShowProducerYUI.json?pProducerId=<bean:write name="producer" property="id"/>&pForward=json');
			tableDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
			tableDataSource.responseSchema = {
				resultsList : "producer.stats",
				fields: [
				 	{key:"name"},
					<logic:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind">
					{key:"<bean:write name="caption" property="caption"/>"/*, parser:YAHOO.util.DataSource.parseNumber*/},
					</logic:iterate>
				]
			};
			table = new YAHOO.anoweb.widget.AnoDataTable("<bean:write name="producer" property="id"/>Box", tableColumnDefs, tableDataSource,
				{draggableColumns:true,sortedBy:{key:"name",dir:"desc"}}
			);
			scope.tables.push(table);

			//HOTFIX: AnoPanel fetches this content unsynchronizelly with show event notification. The next line is forced subscription  
			Moskito.subscribe(WEBUI.utils.Moskito.EVENT_ON_UPDATE, updateProducers, scope, true);

			container = Moskito.producerPanelsRegistry['<bean:write name="producer" property="id"/>'];
			container.subscribe("show", function(){
				Moskito.subscribe(WEBUI.utils.Moskito.EVENT_ON_UPDATE, updateProducers, scope, true);
			});
			container.subscribe("hide", function(){
				Moskito.unsubscribe(WEBUI.utils.Moskito.EVENT_ON_UPDATE, updateProducers, scope, true);
			});

			</logic:iterate>


		});
	</script>
<logic:iterate type="net.java.dev.moskito.webui.bean.StatDecoratorBean" id="decorator" name="decorators">
	<h4><bean:write name="decorator" property="name"/>: <bean:write name="producer" property="id"/></h4>
	<logic:present name="inspectableFlag">
		<div class="inspect">
			<div class="yui-cms-accordion fixIE">
			    <div class="yui-cms-item yui-panel">
			        <div class="actions">
			        	<a href="#" class="accordionToggleItem">&nbsp;</a>
			        </div>
			        <div class="hd">Inspect</div>
			        <div class="bd">
			          <div class="fixed">
			          	<jsp:include page="InspectProducerYUI.jsp" flush="false"/>
			          </div>
			        </div>
			    </div>
			</div>
		</div>
	</logic:present> 
	<div id="<bean:write name="producer" property="id"/>Box" style="overflow:auto"></div>
</logic:iterate>
