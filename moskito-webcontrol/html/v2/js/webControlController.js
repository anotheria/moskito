$(function() {

	//need to be deleted!!!
	// $('.scroll thead a').click(function(event) {
	//  	event.stopPropagation();
	// 	console.log('event stopped!!!');
	// });

	//resizes table for scroll
	function resizeTable() {
		$('.scroll').width($('.header').width() - $('.views_td').width() - 20);
	}



	//makes tables width same
	function sameTables() {
		var ttop = $('.main_table table:first th');
		var ttotal = $('.main_table table:last th');
		for (i = 0; i < ttop.length; i++) {
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

//	sameTables();

	//resize scroll on window resize
	$(window).bind('resize', function() {
		resizeTable();
//		sameTables();
	});

	//ajax operations with views
	function ajaxCall(action, params) {
				var spinner = $('.spinner');
				spinner.show();
//				var response = $(this).parents('tr').find('.response');
//				var error = $(this).parents('tr').find('.error');
				$.ajax({url:action+'?'+params,
				// $.ajax({url:'http://google.com'+'/'+action,
					// data:param+"="+value,
					timeout:10000,
					success: function() {
						spinner.hide();
						// showResponse(response);
					},
					error: function(xhr, ajaxOptions, thrownError) {
						spinner.hide();
						// response.hide();
						alert('Error!');

					}});
				// response.hide();
				// error.hide();
				spinner.hide();
	}

	//add view input
	function addView(btn) {
		btn.hide();
		btn.parents('li').find('.ok, .delete').show();
		btn.parents('li').find('a:not(.ok, .delete, .edit)').hide();
		btn.parents('li').find('input').val(btn.parents('li').find('a:not(.ok, .delete, .edit, .rename)').html()).show();
		btn.parents('li').find('input').focus().select();
	}
	
	//rename view input
	function renameView(btn) {
		btn.hide();
		btn.parents('li').find('.rename, .delete').show();
		btn.parents('li').find('a:not(.rename, .delete)').hide();
		btn.parents('li').find('input').val(btn.parents('li').find('a:not(.ok, .delete, .edit, .rename)').html()).show();
		btn.parents('li').find('input').focus().select();		
	}
	
	//edit view btn
	$('.views .edit').live('click', function() {
		renameView($(this));
		return false;
	});

	//save view input
	function saveView(btn) {
		var li = btn.parents('li');
		li.find('.ok, .delete, .edit, .rename').removeAttr('style');
		li.find('input').hide();
		li.find('a:not(.ok, .delete, .edit, .rename)').html(li.find('input[type="text"]').val()).show();
		li.find('input[type="hidden"]').val(li.find('a:not(.ok, .delete, .edit, .rename)').html());
		li.find('a:not(.ok, .delete, .edit, .rename)').attr('href', 'viewConfig2?currentViewConfiguration='+li.find('a:not(.ok, .delete, .edit, .rename)').html());
	}

	//edit view btn
	$('.views .ok').live('click', function() {
		saveView($(this));
		ajaxCall('viewAdd', 'viewName='+$(this).parents('li').find('input[type="text"]').val());
		return false;
	});

	//rename ok button
	$('.views .rename').live('click', function() {
		ajaxCall('viewRename', 'oldViewName='+$(this).parents('li').find('a:not(.ok, .delete, .edit, .rename)').html()+'&'+"newViewName="+$(this).parents('li').find('input').val());
		saveView($(this));

		return false;
	});

	//save on enter keypress
	$('.views input').live('keypress', function(event) {
		if (event.keyCode == '13') {
			if ($('.views .ok').is(':visible')) {
				saveView($(this));
				ajaxCall('viewAdd', 'viewName='+$(this).parents('li').find('input[type="text"]').val());
			} else {
				ajaxCall('viewRename', 'oldViewName='+$(this).parents('li').find('a:not(.ok, .delete, .edit, .rename)').html()+'&'+"newViewName="+$(this).parents('li').find('input').val());
				saveView($(this));
			}
		}
	});

	//delete view btn
	$('.views .delete').live('click', function() {
		ajaxCall('viewDelete', 'viewName='+$(this).parents('li').find('input[type="text"]').val());
		$(this).parents('li').remove();

		return false;
	});

	//add view btn
	$('.views_td .button.add').click(function() {
		var str = '<li><input type="text" name="views"/><a href="#">Change view name</a><a href="#" class="delete"></a><a class="edit" href="#"></a><a class="ok" href="#"></a><a href="#" class="rename"></a></li>';
		$('.views').append(str);
		addView($('.views li:last').find('.edit'));
		return false;
	});

	//drag'n'drop init
	var viewsList = '';
	$(".scroll tbody").sortable({cancel: '.scroll tbody input, .scroll tbody select, .scroll tbody option, .scroll tbody a', containment: '.scroll tbody' });
	$('.views').sortable({cancel: '.views .show, .views .hide', containment: '.views', revert: true, helper: 'original', stop: function(event, ui) {
		viewsList = '';
		$('.views li').each(function() {
			viewsList = viewsList + $(this).find('a:not(.delete, .edit, .ok)').html()+', '	
		});
		viewsList = viewsList.substr(0, viewsList.length-2);

		ajaxCall('RenewViewList', viewsList);
	}});
	$(".widget").disableSelection();
	


	//overlay select category
	$('.category li').live('click', function() {
		$('.category li').removeClass('active');
		$(this).addClass('active');
		$('.text_here ul:not(.tabs)').hide();
		$('.text_here').find('.'+$(this).attr('id')).show();
	});

	function hideDefault(delBtn) {
		var cat = delBtn.parents('tr').find('.category').val();
		var tpath = delBtn.parents('tr').find('.path').val();
			$('.lightbox .'+cat+' .path').each(function() {
									if ($(this).val() == tpath) {
										$(this).parents('li:first').show();
									}
								});
		delBtn.parents('tr:first').remove();
	}

//	hideDefault();

	$('.scroll #settingsTable tr a').live('click', function() {
		hideDefault($(this));
		return false;
	});

	$('.scroll input[type="checkbox"]').live('change', function() {
		if ($(this).is(':checked')) {
			$(this).next().val('true');
		} else {
			$(this).next().val('false');
		}
	});

	resizeTable();

	//function sets border
	function setBorder(tr, color) {
		tr.find('td').css('border-bottom', '1px solid '+color);
		tr.find('td:first').css('border-left', '5px solid '+color).css('padding-left', '8px');
		tr.find('td:last').css('border-right', '5px solid '+color).css('padding-right', '8px');
		tr.prev().find('td').css('border-bottom', '1px solid '+color);
	}

	//function finds red and yellow cells
	function findIssue() {
		$('.inner .in table tr').each(function () {
			if ($(this).find('.yellow').length > 0) {
			setBorder($(this), '#FFD200');
			}
			if ($(this).find('.red').length > 0) {
				setBorder($(this), '#FF3131');
			}

		});
	}

	findIssue();

	//hide or show views
	$('div.hide a').live('click', function() {
		$('.views_td, .views').animate({
			width: '30px'
		}, 200);
		$(this).parent().removeClass('hide').addClass('show');
		$('.scroll').animate({
			width: '+=140px'
		}, 200);
	});
	//hide or show views
	$('div.show a').live('click', function() {
		$('.views_td, .views').animate({
			width: '170px'
		}, 200);
		$(this).parent().removeClass('show').addClass('hide');
		$('.scroll').animate({
			width: '-=140px'
		}, 200);

	});
	

});

//open lightbox
function lightbox() {
	var el = $('.lightbox');
	el.show();
	$('.lightbox ul.tabs li').removeClass('active');
	$('.lightbox ul.tabs li:first').addClass('active');
	$('.lightbox .service').show();

	$('.lightbox .box').css('width', 'auto');
	$('.lightbox .box').width($('.lightbox .box_in').width());
	var wid = el.find('.box').width();
	var box = el.find('.box');
	var hig = el.find('.box').height();
	box.css('left', '50%');
	box.css('margin-left', -wid / 2);
	//box.css('top', link.offset().top);
	box.css('top', '50%');
	box.css('margin-top', -hig / 2);
	box.css('position', 'fixed');
	$('.lightbox ul:not(.category ul)').height($('.lightbox .category').height());
	return false;
}


//close lightbox
function closeOverlay() {
	$('.lightbox').hide();
	$('.text_here ul').hide();
	$('.text_here ul.cache').show();
	$('.text_here ul.tabs').show();
	$('.text_here .tabs li').removeClass('active');
	$('.text_here .tabs li#cache').addClass('active');
	$('.text_here input[type="checkbox"]').removeAttr('checked');
	return false;

}
$('.black_bg, .close_box, #cancel_button').live('click', function() {
	closeOverlay();
});

function addRow() {
	$('.text_here input:checked').each(function() {
		var li = $(this).parent();
		var text = '<tr>'+
				'<td><input type="checkbox" checked="checked"/><input type="hidden" name="visible" value="true"/></td>'+
				'<td><span>'+li.find('.attributeName').val()+'</span><input type="hidden" class="attributeName" value="'+li.find('.attributeName').val()+'" name="attributeName"/></td>'+
				'<td><input type="text" name="fieldName" value="'+li.find('.fieldName').val()+'"></td>'+
				'<td><select name="javaType"><option>int</option><option>float</option><option>byte</option></select><input type="hidden" class="javaType" value="'+li.find('.javaType').val()+'" name="javaType"></td>'+
				'<td><select name="total"><option>sum</option><option>avg</option><option>min</option><option>max</option></select><input type="hidden" class="formula" value="'+li.find('.formula').val()+'" name="total"/></td>'+
				'<td><select name="guard"><option>TrafficLightsGuard</option></select><input type="hidden" class="guard" value="'+li.find('.guard').val()+'" name="guard"/><input type="hidden" class="category" value="'+li.parent('ul').attr('class')+'"/></td>'+
				'<td><span><input type="text" name="format" class="format" value="'+li.find('.format').val()+'"></span></td>'+
				'<td><a href="#"><img alt="" src="../v2/images/delete.gif"></a><input type="hidden" class="type" value="'+li.find('.type').val()+'" name="type"/><input type="hidden" class="path" value="'+li.find('.path').val()+'" name="path"/></td>'+
				'</tr>';
		li.hide();
		$('.scroll tbody').append(text);
		$('.scroll tbody').find('tr:last').find('select').each(function() {
			$(this).val($(this).next().val());
		});
	});
	closeOverlay();
}