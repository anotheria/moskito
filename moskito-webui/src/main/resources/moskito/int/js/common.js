$(window).scroll(function(){
    if ( $(this).scrollTop() > 1){
        $('body').addClass("scroll");
    } else if($(this).scrollTop() <= 1 && $('body').hasClass("scroll")) {
        $('body').removeClass("scroll");
    }
});//scroll

$(function () {
    $(".select2").select2();

    if (Array.prototype.forEach) {
        var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));
        elems.forEach(function(html) {
            var switchery = new Switchery(html);
        });
    } else {
        var elems = document.querySelectorAll('.js-switch');
        for (var i = 0; i < elems.length; i++) {
            var switchery = new Switchery(elems[i]);
        }
    }

    if ($(window).width() < 800) {
        $('body').addClass('aside-collapse');
    }

    $('.caret-aside').click(function() {
        $('body').toggleClass('aside-collapse');

        $.post("mskSaveNavMenuState", {isNavMenuCollapsed : $('body').hasClass('aside-collapse')}, function(resp){
            if (resp.status == 'ERROR'){
                //console.log(resp);
            }
        });
        setTimeout(function() {
            if(chartEngineIniter && chartEngineIniter.d3charts && chartEngineIniter.d3charts.dispatch){
                chartEngineIniter.d3charts.dispatch.resizeLineCharts();
            }
        }, 200);
    });

    $('.tooltip-bottom').tooltip({placement:'bottom', container: 'body'}).on('show', function (e) {e.stopPropagation();});
    $('.tooltip-top').tooltip({placement:'top', container: 'body'}).on('show', function (e) {e.stopPropagation();});
    $('.tooltip-left').tooltip({placement:'left', container: 'body'}).on('show', function (e) {e.stopPropagation();});
    $('.tooltip-right').tooltip({placement:'right', container: 'body'}).on('show', function (e) {e.stopPropagation();});
    $(document).tooltip({selector: '.aside-collapse .sidebar-tooltip-right', placement:'right', container: 'body'}).on('show', function (e) {e.stopPropagation();});

    $('.popover-bottom').popover({placement:'bottom', container: 'body'}).click(function(){
        $('.popover-bottom').not(this).popover('hide');
        return false;
    });

    $.tablesorter.addParser({
        // set a unique id
        id: 'commaNumber',
        is: function(s) {
            return /^[0-9]?[0-9,\.]*$/.test(s);
        },
        format: function(s) {
//            return jQuery.tablesorter.formatFloat( s.replace(/,/g,'') );
            return s.replace(/\,/g,"");
        },
        // set type, either numeric or text
        type: 'numeric'
    });

    $(".tablesorter").tablesorter({
        sortInitialOrder: "desc",
        textExtraction: function (node) {
            if (($(node).index() === 0) && ($(node).text().toLowerCase().startsWith('total')))
                $(node).parent().addClass('total');
            return $(node).text();
        }
    }).bind('sortEnd', function () {
        $(this).append($(this).find('.total'));
    });

    $(".scrollbar").mCustomScrollbar();

    $(".hscrollbar").mCustomScrollbar({
        advanced:{
            updateOnContentResize: true
        },
        horizontalScroll:true
    });

    $('.hscrollbar .mCSB_scrollTools').css('position','fixed');
    $('.hscrollbar').mCustomScrollbar("update");

    $('ul.nav-sidebar > li').click(function () {
        $('ul.nav-sidebar > li').removeClass('active');
        $(this).addClass('active');
    });

    var max = 0

    $('.headcol').each(function() {
        var width = $(this).width(); if( max < width) { max = width}
    });

    $('.headcol').width(max);
    $('.table1fixed').css('margin-left', max + 39);


    var $treeTable = $("table.tree");
    if($treeTable.length > 0)
        $treeTable.treeTable();

    $('#feedbackForm').validator().on('submit', function (e) {
        if (e.isDefaultPrevented()) {
        } else {
            e.preventDefault()
            $.ajax({
                url: 'http://moskito-moskitorize.rhcloud.com/feedback',
                type: 'post',
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify($("#feedbackForm").serializeObject()),
                success: function(data) {
                }
            });
            $('#feedbackForm').hide();
            $('.submit-message').show();
            return false;
        }
    });
    
    $('#createNewThreshold .modal-resize .fa').on('click', function(){
        $(this).toggleClass('fa-expand');
        $(this).toggleClass('fa-compress');
        $('.modal-dialog').toggleClass('modal-full-screen');
    })
});
