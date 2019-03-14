<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>
<section id="main">
    <div class="content">
        <ano:notPresent name="killSwitchConfiguration">
            <div class="box">
                <div class="box-content paddner">
                    No connection to remote component.
                </div>
            </div>
        </ano:notPresent>

        <ano:present name="killSwitchConfiguration">
        <div class="box">
            <div class="box-content paddner">
                <dl class="dl-horizontal">
                    <dt>Disable metric collection</dt>
                    <dd><input type="checkbox" class="js-switch-color" data-switcher="disableMetricCollection" <ano:equal name="killSwitchConfiguration" property="disableMetricCollection" value="true">checked</ano:equal> /></dd>
                </dl>
            </div>
        </div>
        </ano:present>
    </div>

    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>

    <script>
        var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch-color'));
        elems.forEach(function(html) {
            var switchery = new Switchery(html, { color: '#64bd63', secondaryColor: '#ff6a00'});
            html.onchange = function() {
                $.ajax({url: "mskSwitchKillSetting?name=" + this.getAttribute('data-switcher') + "&value=" + this.checked,
                    dataType: "json",
                    success: function (data) {
                        console.log("Switched " + this.getAttribute('data-switcher') + ": " + this.checked);
                    }
                });
            };
        });


    </script>

</section>
</body>
</html>