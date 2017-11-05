<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="Header.jsp" flush="false"/>

<section id="main">
    <%--
    Commented out for now. We may add this later as welcome message (to all layers).
    <div class="alert alert-success alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <strong>Welcome</strong> to moskito WebUI. <a href="">How it work?</a>
    </div>
    --%>
    <div class="content">

        <div class="page-content">
            <div class="row row-padding-10">
                <div class="col-md-6">
                    <div class="box box-feedback">
                        <h3>Ask Question?</h3>
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
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="box box-help-links">
                        <h3>Help Links</h3>
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
                </div>
            </div>
        </div>

        <!--
        <ano:iterate name="decorators" type="net.anotheria.moskito.webui.shared.bean.DecoratorExplanationBean" id="decorator">
            <div class="box">
                <div class="box-title">
                    <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse${decorator.decoratorNameForCss}"><i class="fa fa-caret-down"></i></a>
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
        -->


    </div>

        <jsp:include page="Footer.jsp"/>
</section>
</body>
</html>
