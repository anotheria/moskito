<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%><%@ page isELIgnored ="false" 
%>

	
<style type="text/css">
.producerAccordion .yui-cms-accordion .yui-cms-item {
  margin-bottom: 40px;
  border: 1px solid #cccccc;
  background-color: white;
}
.producerAccordion .yui-cms-accordion .yui-cms-item h3 {
  margin: 0px;
}
.producerAccordion .yui-cms-accordion .yui-cms-item .accordionToggleItem {
  background: url(../yui/bubbling/img/accordion.gif) no-repeat 0px 0px;
  text-decoration: none;
  padding-left: 20px;
}
.producerAccordion .yui-cms-accordion .yui-cms-item.selected .accordionToggleItem {
  background: url(../yui/bubbling/img/accordion.gif) no-repeat 0px -100px;
}
.producerAccordion .yui-cms-accordion .yui-cms-item .bd {
    height: 0px;
    overflow: hidden;
    background-color: #fff;
    border-left: solid 1px #CCCCCC;
    border-right: solid 1px #CCCCCC;
}
.producerAccordion .yui-cms-accordion .yui-cms-item .bd .fixed {
    overflow: hidden;
}

.producerAccordion h3{
	background-color:#FFFFFF;
	background: url(../yui/bubbling/img/h3bg.jpg);
	background-position:center top;
	background-repeat:repeat-x;
	color:#636363;
	font-weight:normal;
	padding:8px;
}

.producerAccordion table{
	width: 100%;
}

.producerAccordion .yui-cms-accordion .yui-cms-item.hidden{
	position: absolute;
	top: -1000;
	left: -1000;
}

.inspect{
	margin-bottom: 20px;
}

.inspect .yui-cms-accordion .yui-cms-item .actions {
	position: relative;
	left: 2px;
	right: 0;
}

</style>


<div id="AllProducers">
	<div class="producerAccordion">
	<div class="yui-cms-accordion multiple">
	<msk:iterate type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" id="decorator" name="decorators">	
	<div id="<msk:write name="decorator" property="name"/>Panel" class="yui-cms-item yui-panel selected">
        <h3>
        	<a class="accordionToggleItem" title="click to expand" href="#">&nbsp;</a><msk:write name="decorator" property="name"/>&nbsp;&nbsp;<small><i>(<a target="_blank" href="mskShowExplanations#<msk:write name="decorator" property="name"/>">Explain</a>)</i></small>
        </h3>
        <div class="bd">
          <div class="fixed">
          	<div id="<msk:write name="decorator" property="name"/>Box" style="overflow:auto"></div>
          </div>
        </div>
    </div>
	</msk:iterate>
	</div>
	</div>
</div>


<script>
(function() {  

	Moskito.producerPanelsRegistry = Moskito.panelsRegistry || new Object();
	
	var Dom = YAHOO.util.Dom,
		Event = YAHOO.util.Event,
		layout = null;

	var cellFormater = function(elCell, oRecord, oColumn, oData) {
		switch (oColumn.key) {
		case 'producer':
			return producerLinkFormatter(elCell, oRecord, oColumn, oData);
		default:
			elCell.innerHTML = oData;
		}
	}		

	//ALT+SHIFT+W to close all showed panels
	var klForcedClose = new YAHOO.util.KeyListener(document, { alt:true, shift:true, keys:87 },YAHOO.anoweb.widget.AnoPanel.hideAll);
	klForcedClose.enable();
	
	//ALT+W to close only focused panel
	var klClose = new YAHOO.util.KeyListener(document, { alt:true, keys:87 }, YAHOO.anoweb.widget.AnoPanel.hideFocused);
	klClose.enable();

	

	var producerLinkFormatter = function(elCell, oRecord, oColumn, oData) {
	    elCell.innerHTML = '<a href="mskShowProducer?pProducerId=' + oData + '">' + oData + '</a>';		    				
	    YAHOO.util.Event.addListener(elCell, "click", function(event, panelsRegistry){
	    	Event.preventDefault(event);
			var producerPanel = Moskito.producerPanelsRegistry[oData];
			if(!producerPanel){
				producerPanel = new YAHOO.anoweb.widget.AnoPanel(YAHOO.util.Dom.generateId(), {
					show: false, 
					dataSrc:'mskShowProducerYUI?pProducerId=' + oData,
					autofillheight: 'body',
					x: window.pageXOffset + event.clientX,
					y: window.pageYOffset + event.clientY,
				});
				Moskito.producerPanelsRegistry[oData] = producerPanel;
				producerPanel.render('AllProducers');
			}
			producerPanel.show();
			producerPanel.focus();
		});
		 
	};

	var DS = YAHOO.util.DataSourceBase; 
	
	DS.Parser = {
		    string  : DS.parseString,
		    number  : DS.parseNumber,
		    date    : DS.parseDate,
		    long	: DS.parseNumber,
		    double	: DS.parseNumber,
		    int		: DS.parseNumber
		};
			
	
	var createTable = function(decorator){
		var tableDataSource,
			tableColumnDefs,
			table;

		tableDataSource = new YAHOO.util.LocalDataSource(decorator);
		tableDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		
		var fields = [];

		for(var j = 0; j < decorator.metaheader.length; j++ ){
			var meta = decorator.metaheader[j];
			fields.push({key:meta.id,label:meta.caption,sortable:true,resizeable:true,formatter:cellFormater, parser:meta.type});
		}
		
		tableDataSource.responseSchema = {
			resultsList: 'producers',
			fields: fields
		};

		tableColumnDefs = fields;
						
		table = new YAHOO.anoweb.widget.AnoDataTable(decorator.name + "Box", tableColumnDefs, tableDataSource,
			{draggableColumns:true,sortedBy:{key:"producer",dir:"desc"}}
		);
		table.subscribe("rowMouseoverEvent", table.onEventHighlightRow);
		table.subscribe("rowMouseoutEvent", table.onEventUnhighlightRow);
		return table;

		//YAHOO.plugin.Dispatcher.destroyer.subscribe (function(el, config){
//			this.table.destroy ();
//		});
	}
	var scope = {};
	Moskito.subscribe('onDataLoad',function(){
			this.tables = this.tables || [];

			for(var tableName in this.tables){
				this.tables[tableName].hidden = true;
			}
			
			var decorators = Moskito.getDecorators();
			for(var i = 0; i < decorators.length; i++){
				var decorator = decorators[i];
				if(!this.tables[decorator.name])
					this.tables[decorator.name] = createTable(decorator);
				else{
					var table = this.tables[decorator.name];
					table.getDataSource().liveData = decorator;
					table.refresh();						
					table.hidden = false;
				}
			}

			for(var tableName in this.tables){
				var table = this.tables[tableName];
				var tablePanel = Dom.get(tableName + "Panel");
				if(table.hidden){
					Dom.addClass(tablePanel,"hidden");
				}else{
					Dom.removeClass(tablePanel,"hidden");
				}
			}
	}, scope, true);
})();
</script>