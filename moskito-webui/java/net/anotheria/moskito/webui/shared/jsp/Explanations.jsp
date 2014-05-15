<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/InspectHeader.jsp" flush="false"/>

<section id="main">
    <%--
    Commented out for now. We may add this later as welcome message (to all layers).
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <strong>Welcome</strong> to moskito WebUI. <a href="">How it work?</a>
    </div>
    --%>
    <div class="content">


        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse1"><i class="fa fa-caret-right"></i></a>
                <h3 class="pull-left">
                    Help
                </h3>
            </div>
            <div id="collapse1" class="box-content accordion-body collapse in">
                <div class="paddner">
                    This page gives you some quick help and links.
                    <ul>
                        <li><a target="_blank" href="http://www.moskito.org">MoSKito Homepage</a> is the best starting point.</li>
                        <li><a target="_blank" href="http://blog.anotheria.net/category/msk/">anotheria devblog</a> contains a lot of articles about MoSKito, in particular:</li>
                        <li><a target="_blank" href="http://blog.anotheria.net/msk/the-complete-moskito-integration-guide-step-1/">The complete integration guide</a>.</li>
                        <li>You could also check <a target="_blank" href="https://confluence.opensource.anotheria.net/display/MSK/Change+Log">the recent changes</a></li>
                        <li>or <a target="_blank" href="https://confluence.opensource.anotheria.net/display/MSK/MoSKito-WebUI+User+Guide">MoSKito Inspect Manual</a></li>
                        <li>and finally <a target="_blank" hred="http://opensource.anotheria.net/">anotheria open source hub</a>.</li>
                        <li>MoSKito is free and opensource, <a target="_blank" href="https://github.com/anotheria/moskito">fork the project sources on github</a> and participate!</li>
                    </ul>
                    This page explains what the abbreviations used on the producers overview page mean. However, it doesn't explain how to interpret
                    the data. Since the data is strongly use-case dependent, its mostly up to you to give it a correct interpretations, but
                    for some use-cases <a href="http://moskito.anotheria.net/documentation.html">moskito's documentation and HOWTOs</a> are quite useful.
                </div>
            </div>
        </div>


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
                                         type="net.anotheria.moskito.webui.shared.bean.StatCaptionBean">
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

        <jsp:include page="../../shared/jsp/InspectFooter.jsp"/>
</section>
</body>
</html>
