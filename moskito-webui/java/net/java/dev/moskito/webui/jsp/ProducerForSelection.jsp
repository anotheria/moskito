<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito Producer <ano:write name="producer" property="id"/> for Selection </title>
	<link rel="stylesheet" href="mskCSS"/>
</head>
<body>

    <script type="text/javascript" src="../js/wz_tooltip.js"></script>
    <script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="../js/function.js"></script>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script>
        function setandsubmit(valueName, statName){
            //alert('Value name is '+valueName+" in stat  "+statName);
            document.forms.CreateForm.pStatName.value = statName;
            document.forms.CreateForm.pValueName.value = valueName;
            document.forms.CreateForm.submit();
        }
    </script>

<jsp:include page="Menu.jsp" flush="false" />

<div class="main">
<ano:iterate type="net.java.dev.moskito.webui.bean.StatDecoratorBean" id="decorator" name="decorators">
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
            <form name="CreateForm" action="msk<ano:write name="target"/>Create" method="GET">
                <input type="hidden" name="pProducerId" value="<ano:write name="producer" property="id"/>"/>
                <input type="hidden" name="target" value="<ano:write name="target"/>"/>
                <input type="hidden" name="pStatName"/>
                <input type="hidden" name="pValueName"/>
            <div>
                <span>Select producer for new <ano:write name="target"/>.</span>
            </div>
            <br/>
            <div>
                <span>Name: <input type="text" value="new-<ano:write name="target"/>" name="pName"></span>
            </div>
            <br/>
            <div>
    			<span>Producer: <b><ano:write name="producer" property="id"/></b> for selection.</span>
			</div>
               <br/>
            <div><ano:define name="currentInterval" id="currentInterval" toScope="page" type="java.lang.String"/>
                <span>Interval:
                    <select name="pInterval">
                        <ano:iterate name="intervals" id="interval" type="net.java.dev.moskito.webui.bean.IntervalBean">
                            <option value="<ano:write name="interval" property="name"/>" <ano:equal name="interval" property="name" value="<%=currentInterval%>">selected="selected"</ano:equal>>
                                <ano:write name="interval" property="name"/>
                            </option>
                        </ano:iterate>
                    </select>
                </span>
            </div>
            <div><ano:define name="moskito.CurrentUnit" property="unitName" id="currentUnit" toScope="page" type="java.lang.String"/>
                <span>Unit:
                    <select name="pUnit">
                        <ano:iterate name="units" id="unit" type="net.java.dev.moskito.webui.bean.UnitBean">
                            <option value="<ano:write name="unit" property="unitName"/>" <ano:equal name="unit" property="unitName" value="<%=currentUnit%>">selected="selected"</ano:equal>>
                            <ano:write name="unit" property="unitName"/>
                            </option>
                        </ano:iterate>
                    </select>
                </span>
            </div>
            </form>
		</div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	<div class="clear"><!-- --></div>
	<div class="table_layout">
	<div class="top"><div><!-- --></div></div>
	<div class="in">
	<h2><ano:write name="producer" property="id" /></h2>
	<a target="_blank" class="help" href="mskShowExplanations#<ano:write name="decorator" property="name"/>">Help</a>&nbsp;	
		<div class="clear"><!-- --></div>
		<div class="table_itseft">
			<div class="top">
				<div class="left"><!-- --></div>
				<div class="right"><!-- --></div>
			</div>
			<div class="in">			
	
		<table cellpadding="0" cellspacing="0" class="fll" id="<ano:write name="decorator" property="name"/>_table">
		  <thead>
			<tr class="stat_header">			
				<th>Name</th>
			</tr>	
		</thead>
		<tbody>		
			<ano:iterate name="decorator" property="stats" id="stat" type="net.java.dev.moskito.webui.bean.StatBean" indexId="index">
			  <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
					<td>
						<ano:write name="stat" property="name"/>
					</td>				
				</tr>
			</ano:iterate>
		</tbody>			
	</table>
		
	<div class="table_right">	
		<table class="producer_filter_data_table" cellpadding="0" cellspacing="0">
		 <thead>
		  <tr>		    
			<ano:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind">				
			 <th>
                 <ano:write name="caption" property="caption" />
			 </th>
			</ano:iterate>			
		 </tr>		
	   </thead>
	  <tbody>
		  <ano:iterate name="decorator" property="stats" id="stat" type="net.java.dev.moskito.webui.bean.StatBean" indexId="index">
		 <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
				<ano:iterate name="stat" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean">
					<td>
					    <a href="#" onclick="setandsubmit('<ano:write name="value" property="name"/>', '<ano:write name="stat" property="name"/>'); return false">ADD</a>
					</td>
				</ano:iterate>
			</tr>	
		  </ano:iterate>			
	 </tbody>
	 </table>
    </div>
    <div class="clear"><!-- --></div>
	</div>
    <div class="bot">
	<div class="left"><!-- --></div>
	<div class="right"><!-- --></div>
	</div>
    </div>
	</div>

	<div class="bot"><div><!-- --></div></div>
	</div>
	</ano:iterate>
</div>
</body>
</html>

