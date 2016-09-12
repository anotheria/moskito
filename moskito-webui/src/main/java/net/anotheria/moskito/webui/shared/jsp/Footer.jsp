<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%><%@
        taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%><%@
        page isELIgnored="false" %>
<footer id="footer" class="navbar-default">
    <div class="row">
        <div class="col-md-4">
            <div class="footer-info">
                Developed by <a href="http://anotheria.org" target="_blank">Anotheria</a>
                <br>Email: <a href="mailto:support@moskito.org">support@moskito.org</a>
            </div>
        </div>
        <div class="col-md-4">
            <ano:equal name="config" property="trackUsage" value="true"><img src="//counter.moskito.org/counter/inspect/${applicationScope.moskito_version_string}/${pagename}" class="ipix"> </ano:equal>
        </div>
        <div class="col-md-4">
            <a href="https://itunes.apple.com/us/app/moskito-ui/id531387262?mt=8" class="iphone-banner"><img src="../moskito/int/img/iphone_banner.png" alt="Download MoSKito iOS App"></a>
        </div>
    </div>
</footer>
<!-- Modal -->
<div class="modal fade" id="contactUs" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Ask Question?</h4>
            </div>
            <div class="modal-body">
                <form id="feedbackForm" data-toggle="validator" method="post" role="form">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label for="typeRequest">Type of request</label>
                                <select class="form-control" name="typeRequest" id="typeRequest">
                                    <option value="Feedback">Feedback</option>
                                    <option value="Support">Support</option>
                                    <option value="Customization">Customization</option>
                                    <option value="Other">Other</option>
                                </select>
                                <div class="help-block with-errors"></div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group error">
                                <label for="fName">First Name<span class="required-item">*</span></label>
                                <input type="text" id="fName" name="fName" placeholder="First Name" class="form-control" data-error="Please, enter your First Name" required>
                                <div class="help-block with-errors"></div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label for="lName">Last Name<span class="required-item">*</span></label>
                                <input type="text" id="lName" name="lName" placeholder="Last Name" class="form-control" data-error="Please, enter your Last Name" required>
                                <div class="help-block with-errors"></div>
                            </div>
                        </div>
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label for="cEmail">Email<span class="required-item">*</span></label>
                                <input type="email" id="cEmail" name="cEmail" placeholder="Email address" class="form-control" data-error="Sorry, that email address is invalid" required>
                                <div class="help-block with-errors"></div>
                            </div>
                        </div>
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label for="cCompany">Company</label>
                                <input type="text" id="cCompany" name="cCompany" placeholder="Company name" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label for="Note">Note<span class="required-item">*</span></label>
                                <textarea id="Note" name="Note" placeholder="Write you question here" class="form-control" required></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="modal-actions">
                        <div class="pull-right">
                            <button type="button" class="btn btn-default btn-lg" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary btn-lg">Send</button>
                        </div>
                    </div>
                </form>

                <div class="submit-message" style="display:none;">
                    <p><strong>Thank you.</strong> Your message has been sent.</p>
                    <div class="modal-actions">
                        <button type="button" class="btn btn-default pull-right" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="Version" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">MoSKito Version</h4>
            </div>
            <div class="modal-body">
                <dl class="dl-horizontal">
                    <dt>App version:</dt>
                    <dd>${application_maven_version}</dd>
                    <dt>MoSKito version</dt>
                    <dd>v ${moskito_version_string}</dd>
                    <dt>Server:</dt>
                    <dd>${servername}</dd>
                    <dt>Connection:</dt>
                    <dd>${connection}</dd>
                </dl>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="About" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">What is MoSkito?</h4>
            </div>
            <div class="modal-body">
                <div class="modal-logo">
                    <img src="../moskito/int/img/logo_blue.png">
                    <span class="logo-text">MoSKito Inspect</span>
                    <span class="logo-version">v ${moskito_version_string}</span>
                </div>
                <h4>Don't wait for your customers to tell you that your site is down!</h4>
                <p>MoSKito Monitoring for Java applications. Complete ecosystem for DevOps. Free & open source.</p>
                <p>
                    MoSKito is an open source system for monitoring Java web applications.
                </p>
                <p>
                    <strong>With MoSKito, you may:</strong>
                </p>
                <ul>
                    <li>
                        collect, store and analyse any type of performance data (MoSKito-Essential),
                    </li>
                    <li>
                        create personalised storage for the collected data (MoSKito-Central),
                    </li>
                    </li>
                    <li>
                        effectively monitor multi-node applications (MoSKito-Control).
                    </li>
                </ul>
                <p>
                    In fact, MoSKito is a complete system kit for DevOps (as well as classical Devs and Ops) who care about performance of their web apps.
                </p>
                <p>
                    <strong>Enjoy!</strong>
                    <br/>
                    <strong><a href="http://moskito.org" target="_blank">Moskito Team</a></strong>
                </p>
                <div class="modal-actions">
                    <div class="pull-right">
                        <button class="btn btn-default btn-lg" data-dismiss="modal">Close</button>
                        <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#contactUs" data-dismiss="modal">Ask Question?</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="Contacts" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Contacts</h4>
            </div>
            <div class="modal-body">
                <dl class="dl-horizontal">
                    <dt>Email:</dt>
                    <dd><a href="mailto:support@moskito.org">support@moskito.org</a></dd>
                    <dt>Phone:</dt>
                    <dd><a href="skype:+49(0)4035716835">+49 (0) 40 357 168 35</a></dd>
                    <dt>Website:</dt>
                    <dd><a href="http://moskito.org" target="_blank">moskito.org</a></dd>
                </dl>
                <div class="modal-actions">
                    <div class="pull-right">
                        <button class="btn btn-default btn-lg" data-dismiss="modal">Close</button>
                        <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#contactUs" data-dismiss="modal">Ask Question?</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="../moskito/ext/bootstrap-3.1.1/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../moskito/ext/tablesorter/jquery.metadata.js" type="text/javascript"></script>
<script src="../moskito/ext/tablesorter/jquery.tablesorter.js" type="text/javascript"></script>
<script src="../moskito/ext/select2-3.4.6/select2.js" type="text/javascript"></script>

<script src="../moskito/ext/switchery/switchery.min.js" type="text/javascript"></script>

<ano:equal name="currentSubNaviItem" property="id" value="more_config">
    <script type="text/javascript" src="../moskito/ext/google-code-prettify/prettify.js"></script>
    <script type="text/javascript" src="../moskito/ext/google-code-prettify/application.js"></script>
</ano:equal>

<ano:equal name="currentNaviItem" property="id" value="journeys">
    <script type="text/javascript" src="../moskito/ext/jquery-tree-table/jquery.treeTable.min.js"></script>
</ano:equal>

<ano:equal name="currentSubNaviItem" property="id" value="more_mbeans">
    <script type="text/javascript" src="../moskito/ext/jquery-tree-table/jquery.treeTable.min.js"></script>
</ano:equal>

<ano:equal name="currentNaviItem" property="id" value="tracers">
    <script type="text/javascript" src="../moskito/ext/jquery-tree-table/jquery.treeTable.min.js"></script>
</ano:equal>

<script src="../moskito/ext/custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="../moskito/ext/jquery-serialize-object/jquery.serialize-object.min.js" type="text/javascript"></script>
<script src="../moskito/ext/bootstrap-validator/validator.min.js" type="text/javascript"></script>
<script src="../moskito/int/js/common.js" type="text/javascript"></script>

<ano:equal name="config" property="showOnlineHelp" value="true">
<!-- Begin OLARK -->
<div id="olark-box-wrapper">

    <!-- Olark click-to-chat tab -->
    <div class="side-chat-container">
        <a id="side-chat" href="javascript:void(0);" onclick="changeClass()">
            <i class="fa fa-comment"></i> Helpdesk
        </a>
    </div>

    <!-- Empty container for Olark chat box  -->
    <div id="olark-box-container"></div>

</div>

<!-- begin olark code -->
<script data-cfasync="false" type='text/javascript'>/*<![CDATA[*/window.olark||(function(c){var f=window,d=document,l=f.location.protocol=="https:"?"https:":"http:",z=c.name,r="load";var nt=function(){
    f[z]=function(){
        (a.s=a.s||[]).push(arguments)};var a=f[z]._={
    },q=c.methods.length;while(q--){(function(n){f[z][n]=function(){
        f[z]("call",n,arguments)}})(c.methods[q])}a.l=c.loader;a.i=nt;a.p={
        0:+new Date};a.P=function(u){
        a.p[u]=new Date-a.p[0]};function s(){
        a.P(r);f[z](r)}f.addEventListener?f.addEventListener(r,s,false):f.attachEvent("on"+r,s);var ld=function(){function p(hd){
        hd="head";return["<",hd,"></",hd,"><",i,' onl' + 'oad="var d=',g,";d.getElementsByTagName('head')[0].",j,"(d.",h,"('script')).",k,"='",l,"//",a.l,"'",'"',"></",i,">"].join("")}var i="body",m=d[i];if(!m){
        return setTimeout(ld,100)}a.P(1);var j="appendChild",h="createElement",k="src",n=d[h]("div"),v=n[j](d[h](z)),b=d[h]("iframe"),g="document",e="domain",o;n.style.display="none";m.insertBefore(n,m.firstChild).id=z;b.frameBorder="0";b.id=z+"-loader";if(/MSIE[ ]+6/.test(navigator.userAgent)){
        b.src="javascript:false"}b.allowTransparency="true";v[j](b);try{
        b.contentWindow[g].open()}catch(w){
        c[e]=d[e];o="javascript:var d="+g+".open();d.domain='"+d.domain+"';";b[k]=o+"void(0);"}try{
        var t=b.contentWindow[g];t.write(p());t.close()}catch(x){
        b[k]=o+'d.write("'+p().replace(/"/g,String.fromCharCode(92)+'"')+'");d.close();'}a.P(2)};ld()};nt()})({
    loader: "static.olark.com/jsclient/loader0.js",name:"olark",methods:["configure","extend","declare","identify"]});
/* custom configuration goes here (www.olark.com/documentation) */
olark.configure('box.inline', true);
olark('api.box.expand');
olark.identify('7961-404-10-9387');/*]]>*/</script><noscript><a href="https://www.olark.com/site/7961-404-10-9387/contact" title="Contact us" target="_blank">Questions? Feedback?</a> powered by <a href="http://www.olark.com?welcome" title="Olark live chat software">Olark live chat software</a></noscript>
<!-- end olark code -->

<script type='text/javascript'>
    var changeClass;

    (function (d, olk) {
        // These functions are helpers for the below
        function getOlarkWrapper() {
            return d.getElementById("olark-box-wrapper");
        }

        function setOlarkVisible(olark_wrapper) {
            olark_wrapper.className = "chatbox-open";
        }

        function setOlarkHidden(olark_wrapper) {
            olark_wrapper.className = "chatbox-closed";
        }

        function olarkIsVisible(olark_wrapper) {
            return olark_wrapper.className.match(/(?:^|\s)chatbox-open(?!\S)/);
        }

        // This is used by the link to show/hide the inline page element
        changeClass = function() {
            var olark_wrapper = getOlarkWrapper();
            // Get the HTML object containing the Olark chat box
            // If the chat box is already open, close id
            if (olarkIsVisible(olark_wrapper)) {
                setOlarkHidden(olark_wrapper);
            } else {
                setOlarkVisible(olark_wrapper);
            }
        }

        // This is used to cause the box to auto-open if a visitor is already in conversation
        olk('api.visitor.getDetails', function (details) {
            if (details.isConversing) {
                setOlarkVisible(getOlarkWrapper());
            }
        });
    }(document, olark));
</script>
<!-- END OLARK -->
</ano:equal>

<%------------------------------ Add/Delete elements to dashboard ------------------------------%>

<div class="modal fade" id="addElementToDashboard" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Add <span id="selectedElement">someElement</span> to dashboards</h4>
            </div>
            <form id="addElementToDashboardAction" action="someAction" method="GET">
                <div class="modal-body">
                    <input id="selectedElementName" type="hidden" class="form-control" name="pName" value="test">
                    <div id="dashboardsToSelect">
                    </div>
                </div>
                <div class="modal-footer text-center">
                    <button type="button" class="btn btn-success" onclick="submit();">Add</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade modal-danger" id="removeElementFromDashboard" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="removeElementFromDashboardTitle">test</h4>
            </div>

            <div class="modal-body">
                <form id="removeElementFromDashboardAction" action="someAction" method="GET">
                    <input type="hidden" class="form-control" name="pName" value="${requestScope.selectedDashboard}">
                    <input type="hidden" class="form-control" name="pElement" value="test" id="removeElement">
                    <div class="form-group text-right">
                        <button class="btn btn-danger" type="button" onclick="submit();">Yes</button>
                        <button class="btn btn-default" type="button" data-dismiss="modal">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%----------------------------------------------------------------------------------------------%>

<ano:present name="gauges">
    <script language="JavaScript">
        function removeGauge(gaugeForRemovalCaption, gaugeForRemovalName, dashboard){
            $("#removeElementFromDashboardTitle").html("Remove gauge \"" + gaugeForRemovalCaption + "\" from dashboard \""+dashboard+"\"?");
            $("#removeElementFromDashboardAction").attr("action", "mskDashboardRemoveGauge");
            $("#removeElement").attr("value", gaugeForRemovalName);
            $("#removeElementFromDashboard").modal('show');
        }

        function addGauge(gaugeCaption, gaugeName, dashboardsToAdd){

            $("#selectedElement").html("gauge \"" + gaugeCaption + "\"");
            $("#selectedElementName").attr("value", gaugeName);
            $("#addElementToDashboardAction").attr("action", "mskAddGaugeToDashboard");

            var dashboards = dashboardsToAdd.split(',');

            var textToAdd = "";
            for (var i = 0; i < dashboards.length; i++) {
                textToAdd +=
                        "<div class=\"checkbox\"> " +
                        "<label>" +
                        "<input type=\"checkbox\" checked name=\"pDashboards\" value=\""+dashboards[i]+"\">" + dashboards[i] +
                        "</label>" +
                        "</div>";

            }
            $("#dashboardsToSelect").html(textToAdd);

            if (dashboards.length == 1) {
                $("#addElementToDashboardAction").submit();
            } else {
                $("#addElementToDashboard").modal('show');
            }
        }

        function saveGaugesSvgAsPng(event, from, to) {

            event.preventDefault();
            event.stopPropagation();

//                 var xValBegin= 100, xStep = 200;
            var guageWidth = 144,
                    guageHeight = 144,
                    marginLeft = 50,
                    marginRight = 50,
                    marginTop = 50,
                    marginBottom = 50,
                    indent=20;

            var allSvgsCode = '<svg xmlns="http://www.w3.org/2000/svg" class="gauge1" width="800" height="244" style="background-color: #FFFFFF;">';


            var svgs = document.getElementsByClassName("gauge");

            to = to < svgs.length ? to : svgs.length - 1;
            var gaugeCount = to - from + 1;
            for(var i = from; i <= to;i++) {
                var svgOrigin =svgs[i];

                //copy svg chart
                var svg = svgOrigin.cloneNode(true);

                svg.setAttribute("x", marginLeft + (i-from)*(guageWidth+indent));
                svg.setAttribute("y", marginTop);
                svg.setAttribute("style", "background-color: #FFFFFF;");

                var css = '.axis path,' +
                        '.axis line {' +
                        'fill: none;' +
                        'stroke: #000;' +
                        'shape-rendering: crispEdges;' +
                        '}' +
                        '.legend, .tick {' +
                        'font: 12px sans-serif;' +
                        '}' +
                        'text {' +
                        'font: 12px sans-serif;' +
                        '}' +
                        '.line {' +
                        'fill: none;' +
                        'stroke: steelblue;' +
                        'stroke-width: 1.5px;' +
                        '}' +
                        '.line.hover {' +
                        'fill: none;' +
                        'stroke: steelblue;' +
                        'stroke-width: 3.0px;' +
                        '}' +

                        '.grid .tick {' +
                        'stroke: lightgrey;' +
                        'opacity: 0.7;' +
                        '}' +
                        '.grid path {' +
                        'stroke-width: 0;' +
                        '}';

                var style = document.createElement('style');
                style.type = 'text/css';
                if (style.styleSheet){
                    style.styleSheet.cssText = css;
                } else {
                    style.appendChild(document.createTextNode(css));
                }

                svg.appendChild(style);

                allSvgsCode+= new XMLSerializer().serializeToString(svg);
            }
            allSvgsCode+='</svg>';

            var svgData = allSvgsCode;
            var canvas = document.createElement("canvas");
            canvas.width  = marginLeft + marginRight + gaugeCount * guageWidth + (gaugeCount-1) * indent;
            canvas.height = guageHeight + marginBottom + marginTop;
            var ctx = canvas.getContext("2d");
            ctx.fillStyle="white";
            ctx.fill();
            var img = document.createElement("img");

            var img = document.createElement("img");
            var encoded_svg = btoa(svgData.replace(/[\u00A0-\u2666]/g, function(c) {
                return '&#' + c.charCodeAt(0) + ';';
            }));
            img.setAttribute("src", "data:image/svg+xml;base64," + encoded_svg);

            img.onload = function () {
                ctx.drawImage(img, 0, 0);
                var canvasdata = canvas.toDataURL("image/png");
                var a = document.createElement("a");
                var file_name = getGaugeFileName();

                a.download = file_name + ".png";
                a.href = canvasdata;
                document.body.appendChild(a);
                a.click();

            };
        }

        function getGaugeFileName() {
            var t = new Date($.now());
            var current_date = t.getFullYear()+'-'+ t.getMonth()+'-'+ t.getDate()+'__'+t.getHours()+'-'+ t.getMinutes();
            return "Guages_"+current_date;
        }
    </script>
</ano:present>

<ano:present name="thresholds">
    <script language="JavaScript">
        function removeTresholds(thresholdName, dashboard) {
            $("#removeElementFromDashboardTitle").html("Remove threshold \"" + thresholdName + "\" from dashboard \""+dashboard+"\"?");
            $("#removeElementFromDashboardAction").attr("action", "mskDashboardRemoveThreshold");
            $("#removeElement").attr("value", thresholdName);
            $("#removeElementFromDashboard").modal('show');
        }

        function addTresholds(thresholdName, dashboardsToAdd) {
            $("#selectedElement").html("threshold \"" + thresholdName + "\"");
            $("#selectedElementName").attr("value", thresholdName);
            $("#addElementToDashboardAction").attr("action", "mskAddThresholdToDashboard");

            var dashboards = dashboardsToAdd.split(',');

            var textToAdd = "";
            for (var i = 0; i < dashboards.length; i++) {
                textToAdd +=
                        "<div class=\"checkbox\"> " +
                        "<label>" +
                        "<input type=\"checkbox\" checked name=\"pDashboards\" value=\""+dashboards[i]+"\">" + dashboards[i] +
                        "</label>" +
                        "</div>";

            }
            $("#dashboardsToSelect").html(textToAdd);

            if (dashboards.length == 1) {
                $("#addElementToDashboardAction").submit();
            } else {
                $("#addElementToDashboard").modal('show');
            }
        }
    </script>
</ano:present>