var ChartController = function(interval, chartName, chartSettings, mainContainer) {
	// chart settings
	this.interval = interval;
	this.chartName = chartName;
	this.chartId = new Date().getTime();
	this.chartSettings = chartSettings;
	this.intervalId = 0;

	// initialization
	this.data = new google.visualization.DataTable();
	this.data.addColumn('datetime', 'Date');

	// creating columns
	for (i = 0; i < chartSettings.length; i++) {
		var producerSetting = chartSettings[i];

		this.data.addColumn('number', producerSetting[2]);
		this.data.addColumn('string', producerSetting[1]);
		this.data.addColumn('string', producerSetting[0]);
	}

	// needed for generate test data
	this.refreshCounter = 0;

	// init chart container
	var str =
			"<div class='table_layout' id='" + this.chartId + "'>" +
			"<div class='top'>" +
			"<div><!-- --></div>" +
			"</div>" +
			"<div class='in'>" +
			"<h2><a class='' href='#'>" + this.chartName + "</a></h2>" +
			"<a href='#' class='refresh'></a>" +
			"<a href='#' class='help'>delete</a>" +
			"<div class='clear'><!-- --></div>" +
			"<div class='table_itseft'>" +
			"<div class='top'><div class='left'><!-- --></div>" +
			"<div class='right'><!-- --></div></div>" +
			"<div class='in' style='padding:5px 10px;'>" +
			"<div id='a_" + this.chartId + "' style='width:600px; height:250px;'></div>" +
			"<div class='clear'><!-- --></div>" +
			"</div>" +
			"<div class='bot'>" +
			"<div class='left'><!-- --></div>" +
			"<div class='right'><!-- --></div>" +
			"</div>" +
			"</div>" +
			"</div>" +
			"<div class='bot'>" +
			"<div><!-- --></div>" +
			"</div>" +
			"</div>";

	// add chart container to main container
	$('.' + mainContainer).append(str);

	// add chart to chart container
	this.chart = new google.visualization.AnnotatedTimeLine(document.getElementById('a_' + this.chartId));

	// load initial data
	this.runRefresh();

	// initialize timer
	this.setTimer(this);

	// add to charts array
	charts.push(this);
};

ChartController.prototype.getUrl = function(action) {
	var url = action;
	url += 'producers=';
	for (i = 0; i < this.chartSettings.length; i++) {
		url += escape(this.chartSettings[i][0]) + '.';
		url += escape(this.chartSettings[i][1]) + '.';
		url += escape(this.chartSettings[i][2]) + ',';
	}
	url = url.substring(0, url.length - 1);
	return url;
};

//converting JSON to dataTable
ChartController.prototype.convertData = function() {

};

ChartController.prototype.runRefresh = function() {
	// create URL
	var url = this.getUrl('json/data.json?');

	// load JSON data	
	var jsonData;
	$.ajaxSetup({async:false});
	$.getJSON('json/data.json', function(data) {
		jsonData = data;
	});
	$.ajaxSetup({async:true});
	//JSONObject resultFromServer =  this.getChartData(url);

	//creating subData
	var rows = new Array();
	var row = [new Date()];
	var size = 1;
	for (i = 0; i < this.chartSettings.length; i++) {
		var producerSetting = this.chartSettings[i];
		for (j = 0; j < jsonData.data.length; j++) {
			var producerId = jsonData.data[j].producerId;
			var statName = jsonData.data[j].statName;
			var statValueName = jsonData.data[j].statValueName;
			var statValue = jsonData.data[j].statValue;

			if (producerId.toLowerCase() == producerSetting[0].toLowerCase() && statName.toLowerCase() == producerSetting[1].toLowerCase() && statValueName.toLowerCase() == producerSetting[2].toLowerCase() && !isNaN(statValue)) {
				row.push(statValue, producerSetting[0], producerSetting[1]);
				size += 3;
			}
		}
		rows.push(row);
	}
	if (size == this.chartSettings.length * 3 + 1) {
		this.data.addRows(rows);

		// update chart with new data
		this.chart.draw(this.data, {'displayAnnotations': false});
	}
};

ChartController.prototype.setTimer = function(obj) {
	this.intervalId = setInterval(function() {
		obj.runRefresh();
	}, this.interval
			);
};

ChartController.prototype.stopTimer = function() {
	clearInterval(this.intervalId);
};

ChartController.prototype.getChartId = function() {
	return this.chartId;
};

var charts = new Array();


$('.help').live('click', function() {
	for (i = 0; i < charts.length; i++) {
		var chartController = charts[i];
		if (chartController.getChartId() == $(this).parents().filter('.table_layout').attr('id')) {
			chartController.stopTimer();
			//$('#' + chartId).remove();
			$(this).parents().filter('.table_layout').remove();
		}
	}
});

$('.refresh').live('click', function() {
	for (i = 0; i < charts.length; i++) {
		var chartController = charts[i];
		if (chartController.getChartId() == $(this).parents().filter('.table_layout').attr('id')) {
			chartController.runRefresh();
		}
	}
});


var jsonData;
$(function() {
	//loading JSON

	$.ajaxSetup({async:false});
	$.getJSON('json/producers.json', function(data) {
		jsonData = data;
	});
	$.ajaxSetup({async:true});

	//loading interval
	var str = '';
	for (i = 0; i < jsonData.intervals.length; i++) {
		str += '<option>' + jsonData.intervals[i] + '</option>';
	}
	$('#interval').html(str);

	//loading producers
	str = '';
	var stats = '';
	for (i = 0; i < jsonData.producers.length; i++) {
		str += '<option>' + jsonData.producers[i].id + '</option>';
	}
	$('#producer_sel').html(str);
	/* sorting added! */
	var mylist = $('#producer_sel');
	var listitems = mylist.children('option').get();
	listitems.sort(function(a, b) {
		var compA = $(a).text().toUpperCase();
		var compB = $(b).text().toUpperCase();
		return (compA < compB) ? -1 : (compA > compB) ? 1 : 0;
	});
	$.each(listitems, function(idx, itm) {
		mylist.append(itm);
	});
	/* end of sorting! */

});

//check what producer selected and loading property stats and values
function loadStats() {
	var stats = '';
	var type = '';
	for (i = 0; i < jsonData.producers.length; i++) {
		if ($('#producer_sel').val() == jsonData.producers[i].id) {
			for (j = 0; j < jsonData.producers[i].statNames.length; j++) {
				stats += '<option>' + jsonData.producers[i].statNames[j] + '</option>';
			}
			$('#stats_sel').html(stats);
			type = jsonData.producers[i].type;
			for (j = 0; j < jsonData.types.length; j++) {
				var valStr = '';
				if (jsonData.types[j].name == type) {
					for (z = 0; z < jsonData.types[j].valueNames.length; z++) {
						valStr += '<option>' + jsonData.types[j].valueNames[z] + '</option>';
					}
					$('#value_sel').html(valStr);
					return;
				} else {
					$('#value_sel').html('');
				}
			}
		}
	}
}
;

$('#producer_sel').live('change', function() {
	loadStats();
});

function getDuration(x) {
	if (x == 'default') {
		return 1000 * 60; //1m
	}
	if (x.search('m') != -1) {
		return parseInt(x) * 1000 * 60; //x minutes
	} else {
		return parseInt(x) * 1000 * 60 * 60; //x hours
	}
}

//checking for stats not null
function checkStats() {
	if ($('.chart_overlay #stats_sel').val() == null || $('.chart_overlay #value_sel').val() == null) {
		$('.chart_overlay .add').hide();
	} else {
		$('.chart_overlay .add').show();
	}
};

//on change stats check if it not null
$('.chart_overlay #producer_sel').live('change', function() {
	checkStats();
});
