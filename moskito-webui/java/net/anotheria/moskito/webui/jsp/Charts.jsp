<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>Moskito Charts <msk:write name="pageTitle" /></title>
	<link rel="stylesheet" href="mskCSS"/>
	<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
	<script type="text/javascript" src="../js/function.js"></script>
	<script type="text/javascript" src="http://www.google.com/jsapi"></script>
	<script type="text/javascript" src="../js/chartController.js"></script>
</head>
<body>
	<jsp:include page="Menu.jsp" flush="false" />
	<div class="main">
		<p>Here you can create on-the-fly charts which are directly stored in your browser.</p>
		<input type="submit" value="Create chart" class="create_chart_btn" onclick="lightbox($(this));"/>
		<div class="clear"><!-- --></div>
		<div id="charts_holder"></div>
		<div class="clear"><!-- --></div>
		<div class="generated">Generated at <msk:write name="timestampAsDate"/>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;timestamp: <msk:write name="timestamp"/></div>
	</div>
	<div class="lightbox" style="display:none;">
		<div class="black_bg"><!-- --></div>
		<div class="box">
			<div class="box_top"><div><!-- --></div><span><!-- --></span><a class="close_box"><!-- --></a><div class="clear"><!-- --></div></div>
			<div class="box_in">
				<div class="right">
					<div class="text_here">
						<div class="chart_overlay"><h2>Create chart</h2>
							<div class="fll"><label>Chart name:</label><input type="text" class="name_ch"/></div>
							<div class="fll"><label>Update interval:</label><select id="interval"></select></div>
							<div class="clear"></div><hr size="1"/><div class="fll"><label>Producer:</label><select id="producer_sel"></select></div>
							<div class="fll"><label>Stats:</label><select id="stats_sel"></select></div>
							<div class="fll"><label>Value:</label><select id="value_sel"></select></div>
							<div class="fll"><input type="submit" value="Add" class="add"/></div>
							<div class="clear"></div>
							<div class="table_layout"><div class="top"><div><!-- --></div></div>
								<div class="in"><div class="clear"><!-- --></div>
									<div class="table_itseft">
										<div class="top"><div class="left"><!-- --></div><div class="right"><!-- --></div></div>
										<div class="in">
											<table cellspacing="0" cellpadding="0" width="100%">
												<thead><tr class="stat_header">
													<th>Producer</th>
													<th>Stats</th>
													<th>Value</th>
													<th></th>
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
											<div class="clear"><!-- --></div>
										</div>
										<div class="bot"><div class="left"><!-- --></div><div class="right"><!-- --></div></div>
									</div>
								</div>
								<div class="bot"><div><!-- --></div></div>
								<input type="submit" value="Create chart" class="create_ch_btn" style="display:none;"/>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="box_bot"><div><!-- --></div><span><!-- --></span></div>
		</div>
	</div>
	<script type="text/javascript">
		function lightbox(link) {
			$('.lightbox').show();
			//var text = link.attr('title');
			var el = $('.lightbox');
			var wid = el.find('.box').width();
			var box = el.find('.box');
			//el.find('.box_in .text_here').html(text);
			box.css('left', '50%');
			box.css('margin-left', -wid / 2);
			box.css('top', link.offset().top);
			$('.name_ch').focus();
	
			// Fix for ie7
			$('.lightbox .box_top span, .lightbox .box_bot span').width($('.lightbox .box').width() - 60);
			$('.main .help').parents().filter('.table_layout').find('.in:last').css('height', '0px');
			loadStats();
			checkStats();
		};
	
		function odev(el) {
			var i = 0;
			el.find('tr').each(function() {
				$(this).removeClass();
				if (i % 2) {
					$(this).addClass('even');
				} else {
					$(this).addClass('edd');
				}
				i++;
			});
		}
	
		$('.chart_overlay .add').live('click', function() {
			var pr_s = $('.chart_overlay #producer_sel').val();
			var st_s = $('.chart_overlay #stats_sel').val();
			var val_s = $('.chart_overlay #value_sel').val();
			$('.chart_overlay table tbody').append("<tr><td>" + pr_s + "</td><td>" + st_s + "</td><td>" + val_s + "</td><td><input type='button' value='delete' class='delete_btn'/></td></tr>");
			odev($('.chart_overlay table'));
		});
	
		$('.chart_overlay table tbody .delete_btn').live('click', function() {
			$(this).parent().parent().remove();
			odev($('.chart_overlay table'));
		});
	
		/*---------creating object---------*/
		google.load('visualization', '1', {'packages':['annotatedtimeline']});	
		function getParameters() {
			var params = new Array();
			$('.chart_overlay .table_itseft tbody tr').each(function() {
				var temp = new Array();
				for (i=0; i <= 2; i++) {
					temp.push($(this).find('td')[i].innerHTML);
				}
				params.push(temp);
			});
			return params;
		};
	
		$('.create_ch_btn').click(function() {
			var interval = getDuration($('.chart_overlay #interval').val());
			//var interval = parseInt($('.chart_overlay #interval').val()) * 1000;
			var chartName = $('.chart_overlay .name_ch').val();
			if (chartName == '') {
				return;
			}
	        
			new ChartController(interval, chartName, getParameters(), 'main');		

			$('.chart_overlay table tbody tr').each(function() {
				$(this).remove();
			});
			$('.name_ch').val('');
			$('.create_ch_btn').hide();
			$('.lightbox .name_ch').val('');
			$('.lightbox .name_ch').removeClass('error');
			$('.chart_overlay table tbody').html('');
			$('.lightbox #interval option:first').attr('selected', 'selected');
			$('.lightbox #producer_sel option:first').attr('selected', 'selected');
			$('.lightbox').hide();
			$('.main .help').parents().filter('.table_layout').find('.in:last').css('height', '250px');
		});

		function getDuration(x) {
			if (x == 'default')
				return 1000*60; //1m
			
			if (x.search('m') != -1)
				return parseInt(x)*1000*60; //x minutes
				
			return parseInt(x)*1000*60*60; //x hours			
		}
	</script>
	<style type="text/css">
		.table_layout .in {overflow:hidden;}
		.lightbox .box {width:800px;}
		#stats_sel {max-width:150px;}
	</style>
</body>
</html>

