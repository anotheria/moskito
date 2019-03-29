<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.moskito.org/inspect/tags" prefix="mos" %>

<ano:iterate id="alertBean" name="alertBeans" type="net.anotheria.moskito.webui.shared.bean.AlertBean">
    <div class="alert ${alertBean.type.cssStyle}
                ${alertBean.animate ? '' : ' alert-noanimation'}
                ${alertBean.fullWidth ? ' alert-full-width' : ''}
                ${alertBean.roundBorders ? '' : ' alert-nobradius'}
                ${alertBean.dismissible ? ' alert-dismissible' : ''}" role="alert">
        <ano:iF test="${alertBean.dismissible}">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        </ano:iF>
            ${alertBean.message}
    </div>
</ano:iterate>
