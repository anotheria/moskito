<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
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
			
			<msk:iterate type="net.java.dev.moskito.webui.bean.StatDecoratorBean" id="decorator" name="decorators">
			tableColumnDefs = [
				{key:"name", label:"Name",sortable:true,resizeable:true},
				<msk:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind">
				{key:"<msk:write name="caption" property="caption"/>", label:"<msk:write name="caption" property="caption"/>"/*,formatter:YAHOO.widget.DataTable.formatNumber*/,sortable:true,resizeable:true},
				</msk:iterate>
			];
		
			tableDataSource = new YAHOO.util.DataSource('mskShowProducerYUI.json?pProducerId=<msk:write name="producer" property="id"/>&pForward=json');
			tableDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
			tableDataSource.responseSchema = {
				resultsList : "producer.stats",
				fields: [
				 	{key:"name"},
					<msk:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind">
					{key:"<msk:write name="caption" property="caption"/>"/*, parser:YAHOO.util.DataSource.parseNumber*/},
					</msk:iterate>
				]
			};
			table = new YAHOO.anoweb.widget.AnoDataTable("<msk:write name="producer" property="id"/>Box", tableColumnDefs, tableDataSource,
				{draggableColumns:true,sortedBy:{key:"name",dir:"desc"}}
			);
			scope.tables.push(table);

			//HOTFIX: AnoPanel fetches this content unsynchronizelly with show event notification. The next line is forced subscription  
			Moskito.subscribe(WEBUI.utils.Moskito.EVENT_ON_UPDATE, updateProducers, scope, true);

			container = Moskito.producerPanelsRegistry['<msk:write name="producer" property="id"/>'];
			container.subscribe("show", function(){
				Moskito.subscribe(WEBUI.utils.Moskito.EVENT_ON_UPDATE, updateProducers, scope, true);
			});
			container.subscribe("hide", function(){
				Moskito.unsubscribe(WEBUI.utils.Moskito.EVENT_ON_UPDATE, updateProducers, scope, true);
			});

			</msk:iterate>


		});
	</script>
<msk:iterate type="net.java.dev.moskito.webui.bean.StatDecoratorBean" id="decorator" name="decorators">
	<h4><msk:write name="decorator" property="name"/>: <msk:write name="producer" property="id"/></h4>
	<msk:present name="inspectableFlag">
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
	</msk:present> 
	<div id="<msk:write name="producer" property="id"/>Box" style="overflow:auto"></div>
</msk:iterate>
