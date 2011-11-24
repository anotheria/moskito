// JavaScript Document

// function to resize tables
function resize_table(el) {
	var in_t = el.find('.in');
			var tl = el.find('table:first');
			var tr = el.find('.table_right');
			tr.width(in_t.width()-tl.width());
}

// resize table listner
$(window).bind('resize', function() {
	$('.table_itseft').each(
		function() {
			resize_table($(this));
		}
	);
});

$(function() {

	var timeOutId;

	// resize when loaded
	$('.table_itseft').each(
		function() {
			resize_table($(this));
		}
	);

	//setting submenu
	function submenu_shadow() {
	if ($('.sub_menu').is(':visible')) {
		$('.main').css('padding-top', '101px');
		//console.log($('.sub_menu').parent())
		$('.main_menu li.active .over_color').width($('.sub_menu').parent().width())
	} else {
		$('.main').css('padding-top', '71px');
	}
	};

	submenu_shadow();

	function submenu_shadow_hover(el) {
	if ($('.sub_menu').is(':visible')) {
		$('.main').css('padding-top', '101px');
		el.parent().find('.over_color').width(el.parent().width());
	} else {
		$('.main').css('padding-top', '71px');
	}
	};




	//setting first level navigation if active
	$('.top ul li.active:first').each(function() {
		var yes = $(this).find('.sub_menu').length;
		if (!yes) {
			$(this).css('height', $(this).height()-1);
			$(this).css('border-bottom','1px solid #98b9da');
			$(this).css('overflow','hidden');
		}
	});

	//opens bottom filter if row cols >= 10
	function filter_2_open(){
		$('.table_itseft').each(function(){
			if (($(this).find('table:first tr').length >= 10) && ($(this).css('display') == 'block')){
				$(this).parents().filter('.in').find('.filter_2').show();
			}
		})
	};

	//hides tables
	$('h2 a').live('click',function() {
		if ($(this).hasClass('hidden')){
			$(this).parent().parent().css('padding-bottom', '5px');
			$(this).parent().parent().find('.filter, .table_itseft, .help').show();
			$(this).removeClass('hidden');
			filter_2_open();
			return false;			
		} else {
			$(this).parent().parent().css('padding-bottom', '0');
			$(this).parent().parent().find('.filter, .table_itseft, .filter_2, .help').hide();
			$(this).addClass('hidden');
			return false;
		}
	});


	filter_2_open();
 $('.top ul li a').click(function() {
		if (!$(this).parent().hasClass('active') && $(this).parent().find('.sub_menu').length != 0) {
			clearTimeout(timeOutId);
			$('.top ul li.passive.active').removeClass('active');
			$('.top ul li.passive').removeClass('passive');
			$(this).parent().addClass('active passive');
			var yes = $(this).parent().find('.sub_menu').length;
			if (!yes) {
				$(this).css('height', $(this).height()-1);
				$(this).css('border-bottom','1px solid #98b9da');
				$(this).css('overflow','hidden');
			}
			//submenu_shadow();
			submenu_shadow_hover($(this));

		}
	});/*,
	function() {
			if ($(this).parent().hasClass('passive')) {
				timeOutId = setTimeout(function() {$('.top ul li.passive.active').removeClass('active');
				$('.top ul li.passive').removeClass('passive');
				$(this).css('height', 'auto');
				$(this).css('border-bottom','none');
				}, 500);
//				$(this).parent().removeClass('active');
//				$(this).parent().removeClass('passive');

			}
	}); */
	//show submenus
	/* $('.top ul li a').hover(function() {
		if (!$(this).parent().hasClass('active') && $(this).parent().find('.sub_menu').length != 0) {
			clearTimeout(timeOutId);
			$('.top ul li.passive.active').removeClass('active');
			$('.top ul li.passive').removeClass('passive');
			$(this).parent().addClass('active passive');
			var yes = $(this).parent().find('.sub_menu').length;
			if (!yes) {
				$(this).css('height', $(this).height()-1);
				$(this).css('border-bottom','1px solid #98b9da');
				$(this).css('overflow','hidden');
			}
			//submenu_shadow();
			submenu_shadow_hover($(this));

		}
	},
	function() {
			if ($(this).parent().hasClass('passive')) {
				timeOutId = setTimeout(function() {$('.top ul li.passive.active').removeClass('active');
				$('.top ul li.passive').removeClass('passive');
				$(this).css('height', 'auto');
				$(this).css('border-bottom','none');
				}, 500);
//				$(this).parent().removeClass('active');
//				$(this).parent().removeClass('passive');

			}
	}); */

	/*$('.top ul li .sub_menu').hover(function() {
		clearTimeout(timeOutId);
	}, function() {
		timeOutId = setTimeout(function() {$('.top ul li.passive.active').removeClass('active');
				$('.top ul li.passive').removeClass('passive');
				$(this).css('height', 'auto');
				$(this).css('border-bottom','none');
				//submenu_shadow();
				}, 500);
	});*/

	//hover table function
	$('table.fll tr').hover(function() {
		var tr_num = $(this).parents().filter('table').find('tr').index($(this));
		$(this).parents().filter('.in').find('.table_right tr').eq(tr_num).addClass('hover_it');
	},
	function() {
		var tr_num = $(this).parents().filter('table').find('tr').index($(this));
		$(this).parents().filter('.in').find('.table_right tr').eq(tr_num).removeClass('hover_it');		
	});


	$('.table_right table tr').hover(function() {
		var tr_num = $(this).parents().filter('table').find('tr').index($(this));
		$(this).parents().filter('.in').find('table.fll tr').eq(tr_num).addClass('hover_it');
	},
	function() {
		var tr_num = $(this).parents().filter('table').find('tr').index($(this));
		$(this).parents().filter('.in').find('table.fll tr').eq(tr_num).removeClass('hover_it');
	});

	//root table hover effect
	$('.its_root td').hover(function() {
			//$(this).parents().filter('tr:first').addClass('hover_it');

	},function() {
		//$(this).parent().removeClass('hover_it');
	});


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

	//lightbox function
	function lightbox() {
		var text = $(this).attr('title');
		var el = $('.lightbox');
		el.find('.box_in').html(text);
		var wid = el.find('.box').width();
		el.find('.box').css('left', '50%');
		el.find('.box').css('margin-left', -wid/2);
		el.fadeIn('fast');

	};

	
	//close lightbox
	$('.close_box').click(function() {
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

	$('.chart_overlay .delete_btn').live('click', function() {
		if ($('.chart_overlay table tbody tr').length == 0) {
			$('.create_ch_btn').hide();
		}
	});


//	$(".draggable .table_layout").draggable({containment:'.draggable', snap:'.draggable, .table_layout ',  cancel:'.table_layout a, .table_itseft', stack:'.table_layout'});
	$(".widgets_left").sortable({connectWith:['.widgets_right'], cancel:'.table_itseft'});
	$(".widgets_right").sortable({connectWith:['.widgets_left'], cancel:'.table_itseft'});


	
});

