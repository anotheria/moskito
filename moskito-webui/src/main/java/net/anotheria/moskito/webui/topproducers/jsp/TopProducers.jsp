<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
    <jsp:include page="../../shared/jsp/Alerts.jsp"/>

    <div class="content">

        <ano:iterate name="categories" type="net.anotheria.moskito.webui.topproducers.api.CategoryTopProducersAO" id="category" indexId="catindex">
        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse-${category.category}"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    ${category.category}
                </h3>
            </div>
            <div id="collapse-${category.category}" class="box-content accordion-body collapse in">
                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Producer <i class="fa fa-caret-down"></i></th>
                        <th>Category <i class="fa fa-caret-down"></i></th>
                        <th>Subsystem <i class="fa fa-caret-down"></i></th>
                        <th>Score <i class="fa fa-caret-down"></i></th>
                        <th>Average <i class="fa fa-caret-down"></i></th>
                        <th>Top <i class="fa fa-caret-down"></i></th>
                        <th>Last <i class="fa fa-caret-down"></i></th>
                        <th>Intervals <i class="fa fa-caret-down"></i></th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="category" property="producers" type="net.anotheria.moskito.webui.topproducers.api.TopProducerAO" id="producer" indexId="index">
                    <tr>
                        <td>${index + 1}</td>
                        <td>${producer.producerId}</td>
                        <td>${producer.producerCategory}</td>
                        <td>${producer.producerSubsystem}</td>
                        <td>${producer.cumulatedScore}</td>
                        <td>${producer.averageScore}</td>
                        <td>${producer.topScore}</td>
                        <td>${producer.lastScore}</td>
                        <td>${producer.scoreCount}</td>
                    </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
        </div>
        </ano:iterate>

    </div>

    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>

</section>

</body>
</html>
