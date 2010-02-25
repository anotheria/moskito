<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"%>
<ul class="settings">
	<li><a href="#">Export</a>
		<div class="sub_menu">
			<ul>
				<li><a href="<msk:write name="linkToCurrentPageAsXml"/>&amp;pForward=xml">XML</a></li>
				<li><a href="<msk:write name="linkToCurrentPageAsCsv"/>&amp;pForward=csv">CSV</a></li>
			</ul>
		</div>
		<div class="over_color"><div><!-- --></div></div>
	</li>
	<li><a href="mskShowExplanations">Help</a></li>
</ul>