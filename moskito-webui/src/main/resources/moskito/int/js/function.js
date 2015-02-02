// JavaScript Document

function handleSelect(elm) {
	var baseUrl = getBaseUrl();
	var targetUrl = baseUrl + elm.value;
	window.location = targetUrl;
}

function getBaseUrl() {

	var url = location.href;  // entire url including querystring - also: window.location.href;
    var baseURL = url.substring(0, url.lastIndexOf('/')+1);
    return baseURL;
}

	//opens bottom filter if row cols >= 10
	function filter_2_open(){
		$('.table_itseft').each(function(){
			if (($(this).find('table:first tr').length >= 10) && ($(this).css('display') == 'block')){
				$(this).parents().filter('.in').find('.filter_2').show();
			}
		})
	};


	filter_2_open();

	//setting parameters of tree
	function setClosed() {
		$('.call_list img:visible').each(function() {
			if ($(this).hasClass('down')) {
				$(this).parent().find('ul:first').show();
			} else {
				$(this).parent().find('ul:first').hide();
			}
		});
	};

	//closing call tree
	$('.call_list img').click(function() {
		console.log($(this));
		if ($(this).hasClass('down')) {
			$(this).parent().find('ul').hide();
			$(this).parent().find('.up:first').show();
			$(this).hide();
			setClosed();
			set_errors();
		} else {
			$(this).parent().find('ul').show();
			$(this).parent().find('.down:first').show();
			$(this).hide();
			setClosed();
			set_errors();
		}

	});

	//test errors
	function set_errors() {
		$('.call_list .call_error').each(function() {
			if ($(this).is(':visible') && !$(this).parents(':hidden').length) {
				if ($(this).find('.call_error').length) {
					$(this).removeClass('call_error');
				}
			} else {
				$(this).parents('li:visible:first').addClass('call_error');
			}

		});
	};

	set_errors();

	//functions for charts
	$('.chart').click(function() {
	});

	$('.name_ch').blur(function() {
		if ($(this).val()=='') {
			$(this).addClass('error');
		} else {
			$(this).removeClass('error');
		}
	});

	$('.chart_overlay .add').click(function() {
		$('.create_ch_btn').show();
	});

	//close lightbox
$('.black_bg, .close_box').click(function() {
	$('.lightbox .name_ch').val('');
	$('.lightbox .name_ch').removeClass('error');
	$('.chart_overlay table tbody').html('');
	$('.lightbox #interval option:first').attr('selected', 'selected');
	$('.lightbox #producer_sel option:first').attr('selected', 'selected');
	$('.lightbox').hide();
	if ($('.lightbox #interval').length != 0) {
		$('.main .help').parents().filter('.table_layout').find('.in:last').css('height', '250px');
		$('.main .help').parents().filter('.table_layout').find('.in:last').show();
	}
});




