<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="Header.jsp" flush="false"/>

<section id="main">
    <%--
    Commented out for now. We may add this later as welcome message (to all layers).
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <strong>Welcome</strong> to moskito WebUI. <a href="">How it work?</a>
    </div>
    --%>

    <div class="page-content">
        <div class="row m-b-40">
            <div class="col-lg-4">
                <h1>Help</h1>
                <h4>Don't wait for your customers to tell you that your site is down!</h4>
                <p>MoSKito Monitoring for Java applications. Complete ecosystem for DevOps. Free & open source.</p>
                <p>
                    This page gives you some quick help and links.
                </p>
            </div>
            <div class="col-lg-4">
                <h3>Help links</h3>
                <ul class="point-list">
                    <li><a href="http://www.moskito.org/" target="_blank">MoSKito Homepage</a> is the best starting point.</li>
                    <li><a href="http://blog.anotheria.net/category/msk/" target="_blank">anotheria devblog</a> contains a lot of articles about MoSKito, in particular:</li>
                    <li><a href="http://blog.anotheria.net/msk/the-complete-moskito-integration-guide-step-1/" target="_blank">The complete integration guide</a>.</li>
                    <li>You could also check <a href="https://confluence.opensource.anotheria.net/display/MSK/Change+Log" target="_blank">the recent changes</a></li>
                    <li>or <a href="https://confluence.opensource.anotheria.net/display/MSK/MoSKito-Inspect+User+Guide" target="_blank">MoSKito Inspect Manual</a></li>
                    <li>and finally <a href="http://opensource.anotheria.net/" target="_blank">anotheria open source hub</a>.</li>
                    <li>MoSKito is free and opensource, <a href="https://github.com/anotheria/moskito" target="_blank">fork the project sources on github</a> and participate!</li>
                </ul>
            </div>
            <div class="col-lg-4">
                <h3>FeedBack</h3>
                <p>
                    Please send your comments, suggestions or any other kind of positive or negative feedback.
                </p>
                <div class="form-group">
                    <a ref="#contactUs" data-toggle="modal" data-target="#contactUs" class="btn btn-primary btn-lg">Send FeedBack now</a>
                </div>
                <p>
                    For questions or help please use MoSKito users mailing list: moskito-users@lists.anotheria.net
                </p>
                <div class="form-group form-btn-list">
                    <a href="http://lists.anotheria.net/cgi-bin/mailman/listinfo/moskito-users" class="btn btn-primary">Subscribe</a>
                    <a href="http://moskito-users.1088230.n5.nabble.com/" class="btn btn-default">Arcives on nabble</a>
                </div>
            </div>
        </div>
    </div>

    <div class="content">

        <ano:iterate name="decorators" type="net.anotheria.moskito.webui.shared.bean.DecoratorExplanationBean" id="decorator">
            <div class="box">
                <div class="box-title">
                    <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse${decorator.decoratorNameForCss}"><i class="fa fa-caret-right"></i></a>
                    <h3 class="pull-left">${decorator.name}</h3>
                </div>
                <div id="collapse${decorator.decoratorNameForCss}" class="box-content accordion-body collapse in">


                    <div class="table1fixed">
                        <table class="table table-striped tablesorter">
                            <thead>
                            <tr>
                                <th class="headcol">Abbreviation<i class="fa fa-caret-down"></i></th>
                                <th>Meaning <i class="fa fa-caret-down"></i></th>
                                <th>Explanation <i class="fa fa-caret-down"></i></th>
                            </tr>
                            </thead>
                            <tbody>
                            <ano:iterate name="decorator" property="captions" id="caption"
                                         type="net.anotheria.moskito.core.decorators.value.StatCaptionBean">
                                <tr >
                                    <td class="headcol">${caption.caption}</td>
                                    <td>${caption.shortExplanation}</td>
                                    <td>${caption.explanation}</td>
                                </tr>
                            </ano:iterate>
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </ano:iterate>


    </div>

        <jsp:include page="Footer.jsp"/>
</section>
</body>
</html>
