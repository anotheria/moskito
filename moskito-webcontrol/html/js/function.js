$(function() {
	
	if ($('.views li:first') && $('.views li:first').hasClass('active')) {
		$('.inner .top div').css('background', 'white');
		$('.inner .top div').css('border-top', '1px solid #8eceeb');
	}
        

        //resizes table for scroll
        function resizeTable() {
                $('.scroll').width($('.header').width() - $('.views_td').width()-20);
        };
        
        resizeTable();

        //makes tables width same
        function sameTables() {
                var ttop = $('.main_table table:first th');
                var ttotal = $('.main_table table:last th');

                for (i=0; i<ttop.length; i++) {
                        if (ttop.eq(i).width() >= ttotal.eq(i).width()) {
                                if (ttop.eq(i).width() != ttotal.eq(i).width()) {
	                                ttotal.eq(i).css('min-width', (ttop.eq(i).width()));
	                                ttotal.eq(i).find('a').css('width', (ttop.eq(i).find('a').width()));
                                }
                        } else {
                                ttop.eq(i).css('min-width', (ttotal.eq(i).width()));
                                ttop.eq(i).find('a').css('width', (ttotal.eq(i).find('a').width()));
                        }

                }
        }

        sameTables();

        //resize scroll on window resize
        $(window).bind('resize', function() {
                resizeTable();
                sameTables();
        });


});