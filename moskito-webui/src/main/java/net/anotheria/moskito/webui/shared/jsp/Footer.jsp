<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%><%@
        taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%><%@
        page isELIgnored="false" %>
<footer id="footer" class="navbar-default">
    <div class="row">
        <div class="col-md-3">
            <h4>Contacts</h4>
            <p>Developed by <a href="">Anotheria</a><br/>Email: <a href="mailto:support@moskito.org">support@moskito.org</a></p>
            <div class="form-group">
                <a ref="#contactUs" data-toggle="modal" data-target="#contactUs" class="btn btn-primary">Free Consultation</a>
            </div>
        </div>
        <div class="col-md-6">
            <div class="inform-line">
                <ul>
                    <li>Generated at ${timestampAsDate}</li>
                    <li>Timestamp: ${timestamp}</li>
                    <li>Interval updated at: ${currentIntervalUpdateTimestamp}</li>
                    <li>Interval age: ${currentIntervalUpdateAge}</li>
                    <li><a href="#Version" data-toggle="modal" data-target="#Version">More app info</a></li>
                </ul>
            </div>
        </div>
        <div class="col-md-3">
            <a href="https://itunes.apple.com/us/app/moskito-ui/id531387262?mt=8" class="iphone-banner"><img src="../moskito/int/img/iphone_banner.png" alt="Download MoSKito iOS App"></a>
        </div>
    </div>
    <ano:equal name="config" property="trackUsage" value="true"><img src="//counter.moskito.org/counter/inspect/${applicationScope.moskito_version_string}/${pagename}" class="ipix"> </ano:equal>
</footer>

<!-- Modal -->
<div class="modal fade" id="contactUs" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Free Consultation</h4>
            </div>
            <div class="modal-body">
                <form id="feedbackForm" data-toggle="validator" method="post" role="form">
                    <div class="row">
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
                                <textarea id="Note" name="Note" placeholder="Note" class="form-control" required></textarea>
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
                <h4 class="modal-title">App information</h4>
            </div>
            <div class="modal-body">
                <dl class="dl-horizontal">
                    <dt>App version:</dt>
                    <dd>${application_maven_version}</dd>
                    <dt>MoSKito version</dt>
                    <dd>${moskito_maven_version}</dd>
                    <dt>Server:</dt>
                    <dd>${servername}</dd>
                    <dt>Connection:</dt>
                    <dd>${connection}</dd>
                </dl>
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

<!-- begin olark code -->
<script data-cfasync="false" type='text/javascript'>/*<![CDATA[*/
window.olark || (function (c) {
    var f = window, d = document, l = f.location.protocol == "https:" ? "https:" : "http:", z = c.name, r = "load";
    var nt = function () {
        f[z] = function () {
            (a.s = a.s || []).push(arguments)
        };
        var a = f[z]._ = {
        }, q = c.methods.length;
        while (q--) {
            (function (n) {
                f[z][n] = function () {
                    f[z]("call", n, arguments)
                }
            })(c.methods[q])
        }
        a.l = c.loader;
        a.i = nt;
        a.p = {
            0: +new Date};
        a.P = function (u) {
            a.p[u] = new Date - a.p[0]
        };
        function s() {
            a.P(r);
            f[z](r)
        }

        f.addEventListener ? f.addEventListener(r, s, false) : f.attachEvent("on" + r, s);
        var ld = function () {
            function p(hd) {
                hd = "head";
                return["<", hd, "></", hd, "><", i, ' onl' + 'oad="var d=', g, ";d.getElementsByTagName('head')[0].", j, "(d.", h, "('script')).", k, "='", l, "//", a.l, "'", '"', "></", i, ">"].join("")
            }

            var i = "body", m = d[i];
            if (!m) {
                return setTimeout(ld, 100)
            }
            a.P(1);
            var j = "appendChild", h = "createElement", k = "src", n = d[h]("div"), v = n[j](d[h](z)), b = d[h]("iframe"), g = "document", e = "domain", o;
            n.style.display = "none";
            m.insertBefore(n, m.firstChild).id = z;
            b.frameBorder = "0";
            b.id = z + "-loader";
            if (/MSIE[ ]+6/.test(navigator.userAgent)) {
                b.src = "javascript:false"
            }
            b.allowTransparency = "true";
            v[j](b);
            try {
                b.contentWindow[g].open()
            } catch (w) {
                c[e] = d[e];
                o = "javascript:var d=" + g + ".open();d.domain='" + d.domain + "';";
                b[k] = o + "void(0);"
            }
            try {
                var t = b.contentWindow[g];
                t.write(p());
                t.close()
            } catch (x) {
                b[k] = o + 'd.write("' + p().replace(/"/g, String.fromCharCode(92) + '"') + '");d.close();'
            }
            a.P(2)
        };
        ld()
    };
    nt()
})({
    loader: "static.olark.com/jsclient/loader0.js", name: "olark", methods: ["configure", "extend", "declare", "identify"]});
/* custom configuration goes here (www.olark.com/documentation) */
olark.identify('7961-404-10-9387');
/*]]>*/</script>
<noscript><a href="https://www.olark.com/site/7961-404-10-9387/contact" title="Contact us" target="_blank">Questions?
    Feedback?</a> powered by <a href="http://www.olark.com?welcome" title="Olark live chat software">Olark live chat
    software</a></noscript>
<!-- end olark code -->
