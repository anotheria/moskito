<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>
<section id="main">
    <jsp:include page="../../shared/jsp/Alerts.jsp"/>

    <div class="content">
        <ano:notPresent name="killSwitchConfiguration">
            <div class="box">
                <div class="box-content paddner">
                    Remote version doesn't support killswitch yet.
                </div>
            </div>
        </ano:notPresent>

        <ano:present name="killSwitchConfiguration">
            <div class="box">
                <div class="box-content paddner align-center-flex">
                    <div><input type="checkbox" class="js-switch-color" data-switcher="disableMetricCollection" <ano:equal name="killSwitchConfiguration" property="disableMetricCollection" value="true">checked</ano:equal> /></div>
                    <span class="tag-box">Disable metric collection</span>
                </div>
            </div>
            <div class="box">
                <div class="box-content paddner align-center-flex">
                    <div><input type="checkbox" class="js-switch-color" data-switcher="disableTracing" <ano:equal name="killSwitchConfiguration" property="disableTracing" value="true">checked</ano:equal> /></div>
                    <span class="tag-box">Disable tracing</span>
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