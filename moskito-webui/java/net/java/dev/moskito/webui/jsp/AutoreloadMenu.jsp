<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%><%@
 taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><div class="autoreload_wrapper">
    <div class="autoreload_toggle_button">
        <div class="autoreload_toggle_triangle"></div>
        Autoreload <ano:present name="autoreloadInterval"><ano:write name="autoreloadInterval"/></ano:present> <ano:notPresent name="autoreloadInterval">OFF</ano:notPresent>
    </div>
    <form name="autoreloadIntervalForm" action="" class="autoreload_settings">
        <div class="autoreload_current_interval">Current interval:<b class="autoreload_current_interval_data"><ano:present name="autoreloadInterval"><ano:write name="autoreloadInterval"/></ano:present> <ano:notPresent name="autoreloadInterval">none</ano:notPresent></b></div>
        <label>Set minutes reload interval:</label>
        <input class="autoreload_minutes_settings_input" type="text" placeholder="e.g.: 10">
        <button class="autoreload_set_button">Set</button>
        <ano:present name="autoreloadInterval"><button class="autoreload_reset_button">Off</button></ano:present>
    </form>
</div>