$(window).scroll(function(){
    if ( $(this).scrollTop() > 1){
        $('body').addClass("scroll");
    } else if($(this).scrollTop() <= 1 && $('body').hasClass("scroll")) {
        $('body').removeClass("scroll");
    }
});//scroll

function resize_table(el) {
    var in_t = el.find('.box-content');
    var tl = el.find('table:first');
    var tr = el.find('.table-right');
    tr.width(in_t.width()-tl.width());
    console.log(tr);
}

// resize table listner
$(window).bind('resize', function() {
    $('.box').each(
        function() {
            resize_table($(this));
        }
    );

    if ($(window).width() < 800) {
        $('body').addClass('aside-collapse');
    }
});

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

    $('.box').each(
        function() {
            resize_table($(this));
        }
    );

    $(".tablesorter").tablesorter();

    //hover table function
    $('table.table tr').hover(function() {
            var tr_num = $(this).parents().filter('table').find('tr').index($(this));
            $(this).parents().filter('.box').find('.table-right tr').eq(tr_num).addClass('hover_it');
            console.log('fff');
        },
        function() {
            var tr_num = $(this).parents().filter('table').find('tr').index($(this));
            $(this).parents().filter('.box').find('.table-right tr').eq(tr_num).removeClass('hover_it');
        });

    $('.table-right table tr').hover(function() {
            var tr_num = $(this).parents().filter('table').find('tr').index($(this));
            $(this).parents().filter('.box').find('table.table tr').eq(tr_num).addClass('hover_it');
        },
        function() {
            var tr_num = $(this).parents().filter('table').find('tr').index($(this));
            $(this).parents().filter('.box').find('table.table tr').eq(tr_num).removeClass('hover_it');
        });

    $(".select2").select2();

    $(".scrollbar").mCustomScrollbar();
});