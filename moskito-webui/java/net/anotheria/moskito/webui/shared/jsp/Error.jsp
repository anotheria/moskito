<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>MoSKito Error</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="mskCSS"/>

    <!--    PART 1    -->
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js"></script>

    <script>
        $(function tggleErrorPageDetail() {
            var button = $('.error_page_details_button'),
                    detailsContent = $('.error_page_details_content');

            button.removeClass('display_none_property');
            detailsContent.addClass('display_none_property');

            button.on('click', function() {
                var $this = $(this);

                if ($this.text() === 'Details') {
                    $this.text('Hide details');
                    detailsContent.css('padding-bottom','30px');
                    console.log('miu')
                } else {
                    $this.text('Details');
                    detailsContent.css('padding-bottom','10px');
                }

                detailsContent.slideToggle();
            })
        })
    </script>
    <!--    /PART 1    -->

</head>

<!--    PART 2    -->
<body class="error_page">
<jsp:include page="Menu.jsp" flush="false"/>

<div class="error_page_box">
    <div class="error_page_status">
        MoSKito encountered an error:
    </div>
    <p class="error_page_data_hint">
           <span class="error_page_data">
                <ano:write name="maf.error" property="message"/>
            </span>
    </p>
    <div class="error_page_details_wrapper">
        <button class="error_page_details_button display_none_property">Details</button>
        <div class="error_page_details_content">
            <ano:write name="maf.error" property="stackTrace"/>
        </div>
    </div>
</div>
<!--    /PART 2    -->

</body>
</html>

