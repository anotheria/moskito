$(function() {

	//create dashboard lightbox
	$('.dashes .create').click(function() {
		//		if ($(this).val() == 'Create dashboard') {
		$('.dashes option').eq(1).attr('selected', 'selected');
		lightbox($('.create_dash_overlay').html(), 268);
		$('.lightbox .edit_dash_form input[type="text"]').val('Enter dashboard name');
		$('.lightbox .edit_dash_form input[type="text"]').focus().select();
		return false;
		//		}
	});

	//edit dashboard name lightbox
	$('.edit.dash').click(function() {
		if ($('.dashes').val() != '') {
			lightbox($('.edit_dash_overlay').html(), 268);
			$('.lightbox .edit_dash_form input[type="text"]').val($('.dashes').val());
			$('.lightbox .edit_dash_form input[type="text"]').focus().select();
		}
	});

	//create widget lightbox
	$('.create_wdgt').click(function() {
		lightbox($('.create_widget_overlay').html(), 650);
		$('.lightbox .create_widget_form .right_part').css('min-height', $('.lightbox .create_widget_form .t_table_prod_group').height() + 'px');
		$('.lightbox .create_widget_form .right_part input:visible').attr('checked', 'checked');
		$('.lightbox .create_widget_form input[type="text"]:first').val('Enter widget name');
		$('.lightbox .create_widget_form input[type="text"]:first').focus().select();
	});

	//active producer group (table)
	$('.lightbox .t_table_prod_group a:not(.uncheck)').live('click', function() {
		$('.lightbox .t_table_prod_group li').removeClass('active');
		$(this).parent('li').addClass('active');
	});

	//uncheck producer group (table)
	$('.lightbox .t_table_prod_group .uncheck').live('click', function() {
		$(this).parent('li').removeClass('checked');
		var classN = $(this).next().attr('class');
		$('.t_table_val').find('ul.' + classN + '_val').find('input').removeAttr('checked');
		$('.t_table_prod').find('ul.' + classN + '_prod').find('input').removeAttr('checked');
	});

	//chart widget selected
	$('.lightbox #t_chart, .lightbox #pie, .lightbox #bar').live('click', function() {
			$('.lightbox .t_table').html($('.create_widget_chart').html());	
		if ($(this).attr('id') == 'bar') {
			$('.lightbox #bar').attr('checked', 'checked');
		}
	});

	//table widget selected
	$('#t_table').live('click', function() {
		$('.lightbox .t_table').html($('.create_widget_overlay .t_table').html());
		$('.lightbox .create_widget_form .right_part').css('min-height', $('.lightbox .create_widget_form .t_table_prod_group').height() + 'px');
		$('.lightbox .create_widget_form .right_part input:visible').attr('checked', 'checked');
	});

	//chart widget active producer group
	$('.t_pie_bar .t_pie_bar_prod_group a, .t_pie_bar .t_pie_bar_val a').live('click', function() {
		$(this).parents('ul').find('li').removeClass('active');
		$(this).parent('li').addClass('active');
	});

	//uncheck button appear
	$('.right_part input:visible').live('change', function() {
		var checked1, checked2 = false;
		$(this).parents('.right_part').find('.t_table_val input:visible').each(function() {
			if ($(this).is(':checked')) {
				checked1 = true;
			}
		});
		$(this).parents('.right_part').find('.t_table_prod input:visible').each(function() {
			if ($(this).is(':checked')) {
				checked2 = true;
			}
		});
		if (checked1 && checked2) {
			$('.t_table_prod_group li.active').addClass('checked')
		} else {
			$('.t_table_prod_group li.active').removeClass('checked');
		}
	});

	//tabs change table
	$('.lightbox .t_table_prod_group a:not(.uncheck)').live('click', function() {
		var classN = $(this).attr('class');
		$('.t_table_val ul, .t_table_prod ul').hide();
		$('.t_table_val').find('ul.' + classN + '_val').show();
		$('.t_table_prod').find('ul.' + classN + '_prod').show();
		return false;
	});

	//tabs change chart
	$('.lightbox .t_pie_bar_prod_group a').live('click', function() {
		var classN = $(this).attr('class');
		$('.t_pie_bar_val ul, .t_pie_bar_prod ul').hide();
		$('.t_pie_bar_val').find('ul.' + classN + '_val').show().find('li').removeClass('active').parents('ul').find('li:first').addClass('active');
		$('.t_pie_bar_prod').find('input').removeAttr('checked').parents('td').find('ul.' + classN + '_prod').show().find('input').attr('checked', 'checked');
		return false;
	});

	//timeline chart appear
	$('#timeline').live('click', function() {
		$('.lightbox .t_table').html($('.create_widget_chart_timeline').html());
	});

	//threshold select
	$('#t_threshold').live('click', function() {
		$('.lightbox .t_table').html($('.create_widget_threshold').html());
	});

	//lightbox
	function lightbox(content, width) {
		var el = $('.lightbox');
		el.show();
		if (content != '') {
			el.find('.text_here').html(content);
		} else {
			el.hide();
			return false;
		}
		el.find('.box').css('width', 'auto');
		if (width > 0) {
			el.find('.box').css('width', width + 'px').css('min-width', width + 'px');
		} else {
			el.find('.box').width($('.lightbox .box_in').width());
		}
		var wid = el.find('.box').width();
		var box = el.find('.box');
		var hig = el.find('.box').height();
		box.css('left', '50%');
		box.css('margin-left', -wid / 2);
		box.css('top', '5%');
		//		box.css('margin-top', -hig / 2);
		box.css('position', 'fixed');
		return false;
	}

	//check all inputs
	function checkAll(all, inputs) {
		if (all.is(':checked')) {
			inputs.attr('checked', 'checked');
		} else {
			inputs.removeAttr('checked');
		}
	}

	//all checkboxes uncheck
	function checkAllUncheck(all, inputs) {
		var bool = true;
		inputs.each(function() {
			if (!$(this).is(':checked')) {
				bool = false;
			}
			if (!bool) {
				all.attr('checked', false);
			} else {
				all.attr('checked', true);
			}
		});
	}

	//all input click event
	$('#c_all').live('click', function(event) {
		event.stopPropagation();
		checkAll($(this), $(this).parents('ul').find('input'));
	});

	//all checkboxes uncheck event
	$('.right_part input:not(#c_all), .t_pie_bar_prod input:not(#c_all)').live('click', function(event) {
		event.stopPropagation();
		checkAllUncheck($(this).parents('ul').find('#c_all'), $(this).parents('ul').find('input:not(#c_all)'));
	});


});

//close lightbox
function closeLightbox() {
	$('.lightbox .name_ch').val('');
	$('.lightbox .name_ch').removeClass('error');
	$('.lightbox').hide();
	$('.lightbox .text_here').html('');

}

//chart functions
google.load('visualization', '1', {packages: ['columnchart']});
$(function() {
	function drawBarChart() {
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Producer');
		data.addColumn('number', 'val');
		data.addRows([
			['UserAgentFilter',74],
			['UserAgentFilter',0],
			['moskitoUI',2990],
			['moskitoUI',0],
			['RefererFilter',25],
			['RefererFilter',0],
			['RequestURIFilter',96],
			['RequestURIFilter',0],
			['DomainFilter',131],
			['DomainFilter',0]
		]);
		var options = {width: 450, height: 240, title: 'Filter - Time'};
		var chartInfo = {
			params: '',
			container: 'barChart',
			type: 'BarChart',
			data: data,
			options: options,
			maximized: false
		};
		addDragStopListener(chartInfo);
		drawChart(chartInfo);
	}

	function drawPieChart() {
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Producer');
		data.addColumn('number', 'val');
		data.addRows([
			['UserAgentFilter',74],
			['UserAgentFilter',0],
			['moskitoUI',2990],
			['moskitoUI',0],
			['RefererFilter',25],
			['RefererFilter',0],
			['RequestURIFilter',96],
			['RequestURIFilter',0],
			['DomainFilter',131],
			['DomainFilter',0]
		]);
		var options = {width: 450, height: 240, title: 'Filter - Time'};
		var chartInfo = {
			params: '',
			container: 'pieChart',
			type: 'PieChart',
			data: data,
			options: options,
			maximized: false
		};
		addDragStopListener(chartInfo);
		drawChart(chartInfo);
	}

	function drawChart(chartInfo) {
		document.getElementById(chartInfo.container + '_container').chartInfo = chartInfo;

		google.visualization.drawChart({
			"containerId": chartInfo.container,
			dataTable: chartInfo.data/*+chartInfo.params*/,
			"chartType": chartInfo.type,
			"options": chartInfo.options,
			"refreshInterval": 60
		});
	}

	function addDragStopListener(chartInfo) {
		$(".widgets_left").sortable({
			stop: function(event, ui) {
				drawChart(ui.item[0].chartInfo);
			}
		});
		$(".widgets_right").sortable({
			stop: function(event, ui) {
				drawChart(ui.item[0].chartInfo);
			}
		});
	}

	drawBarChart();
	drawPieChart();
});
