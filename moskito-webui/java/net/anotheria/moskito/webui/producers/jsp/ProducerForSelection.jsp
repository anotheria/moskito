<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito Producer <ano:write name="producer" property="producerId"/> for Selection </title>
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
            document.forms.CreateForm.statName.value = statName;
            document.forms.CreateForm.valueName.value = valueName;
            document.forms.CreateForm.submit();
        }
        function switchDirection(){
            if (document.forms.CreateForm.greenDir.value=='above')
                targetValue = 'below';
            else
                targetValue = 'above';

            document.forms.CreateForm.yellowDir.value = targetValue;
            document.forms.CreateForm.orangeDir.value = targetValue;
            document.forms.CreateForm.redDir.value = targetValue;
            document.forms.CreateForm.purpleDir.value = targetValue;
        }

        function switchgreenvalue(){
            document.forms.CreateForm.yellowValue.value=document.forms.CreateForm.greenValue.value;
        }
    </script>

<jsp:include page="../../shared/jsp/Menu.jsp" flush="false" />

<div class="main">
<ano:iterate type="net.anotheria.moskito.webui.shared.bean.StatDecoratorBean" id="decorator" name="decorators">
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
            <form name="CreateForm" action="msk<ano:write name="target"/>Create" method="get" class="form-inline">
                <input type="hidden" name="producerId" value="<ano:write name="producer" property="producerId"/>"/>
                <input type="hidden" name="target" value="<ano:write name="target"/>"/>
                <input type="hidden" name="statName"/>
                <input type="hidden" name="valueName"/>

            <h3>Select producer for new <ano:write name="target"/>.</h3>
            <div class="controls">
                <label>Name:</label> <input type="text" value="new-<ano:write name="target"/>" name="name"/>
            </div>
            <div class="controls">
                <ano:define name="currentInterval" id="currentInterval" toScope="page" type="java.lang.String"/>
                <label>Interval:</label>
                    <select name="interval">
                        <ano:iterate name="intervals" id="interval" type="net.anotheria.moskito.webui.shared.bean.IntervalBean">
                            <option value="<ano:write name="interval" property="name"/>" <ano:equal name="interval" property="name" value="<%=currentInterval%>">selected="selected"</ano:equal>>
                                <ano:write name="interval" property="name"/>
                            </option>
                        </ano:iterate>
                    </select>
            </div>
            <div class="controls">
                <ano:define name="moskito.CurrentUnit" property="unitName" id="currentUnit" toScope="page" type="java.lang.String"/>
                <label>Unit:</label>
                    <select name="unit">
                        <ano:iterate name="units" id="unit" type="net.anotheria.moskito.webui.shared.bean.UnitBean">
                            <option value="<ano:write name="unit" property="unitName"/>" <ano:equal name="unit" property="unitName" value="<%=currentUnit%>">selected="selected"</ano:equal>>
                            <ano:write name="unit" property="unitName"/>
                            </option>
                        </ano:iterate>
                    </select>
            </div>
            <div class="controls">
                <label>Producer:</label> <b><ano:write name="producer" property="producerId"/></b> for selection.
            </div>
            <ano:equal name="target" value="Threshold">
                <div class="clear"><!-- --></div>
                <div class="controls">
                    <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_green.png"/>&nbsp; if &nbsp;<select name="greenDir" onchange="switchDirection();"><option>below</option><option>above</option></select>&nbsp;<input type="text" name="greenValue" onchange="switchgreenvalue();"/> (Green)</span><br/><br/>
                    <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_yellow.png"/>&nbsp; if &nbsp;<select name="yellowDir"><option>below</option><option selected="selected">above</option></select>&nbsp;<input type="text" name="yellowValue"/> (Yellow)</span><br/><br/>
                    <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_orange.png"/>&nbsp; if &nbsp;<select name="orangeDir"><option>below</option><option selected="selected">above</option></select>&nbsp;<input type="text" name="orangeValue"/> (Orange)</span><br/><br/>
                    <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_red.png"/>&nbsp; if &nbsp;<select name="redDir"><option>below</option><option selected="selected">above</option></select>&nbsp;<input type="text" name="redValue"/> (Red)</span><br/><br/>
                    <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_purple.png"/>&nbsp; if &nbsp;<select name="purpleDir"><option>below</option><option selected="selected">above</option></select>&nbsp;<input type="text" name="purpleValue"/> (Purple)</span><br/><br/>
                </div>
            </ano:equal>
            </form>
		</div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	<div class="clear"><!-- --></div>
	<div class="table_layout">
	<div class="top"><div><!-- --></div></div>
	<div class="in">
	<h2><ano:write name="producer" property="producerId" /></h2>
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
			<ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
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
			<ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.webui.shared.bean.StatCaptionBean" id="caption" indexId="ind">
			 <th>
                 <ano:write name="caption" property="caption" />
			 </th>
			</ano:iterate>			
		 </tr>		
	   </thead>
	  <tbody>
		  <ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
		 <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
				<ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.webui.producers.api.StatValueAO">
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

