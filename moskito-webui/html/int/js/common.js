$(window).scroll(function(){
    if ( $(this).scrollTop() > 1){
        $('body').addClass("scroll");
    } else if($(this).scrollTop() <= 1 && $('body').hasClass("scroll")) {
        $('body').removeClass("scroll");
    }
});//scroll

$(function () {
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
            $('.box').each(
                function() {
                    resize_table($(this));
                }
            );
        }, 200);
    });

    $('.tooltip-bottom').tooltip({placement:'bottom'}).on('show', function (e) {e.stopPropagation();});
    $('.tooltip-top').tooltip({placement:'top'}).on('show', function (e) {e.stopPropagation();});
    $('.tooltip-left').tooltip({placement:'left'}).on('show', function (e) {e.stopPropagation();});
    $('.tooltip-right').tooltip({placement:'right'}).on('show', function (e) {e.stopPropagation();});
    $('.aside-collapse .sidebar-tooltip-right').tooltip({placement:'right', container: 'body'}).on('show', function (e) {e.stopPropagation();});

    $(".tablesorter").tablesorter();

    $(".select2").select2();

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

    $('.tree').treegrid({
        expanderExpandedClass: 'fa fa-minus',
        expanderCollapsedClass: 'fa fa-plus'
    }).treegrid('collapseAll');
});