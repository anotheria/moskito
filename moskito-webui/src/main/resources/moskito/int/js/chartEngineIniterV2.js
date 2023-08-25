var chartEngineIniterV2 = {
    init: function (params) {

        $('#' + params.container).empty();

        const JsChartInstance = JsChart.getInstance();
        JsChartInstance("#" + params.container, params);

    }
};

var JsChart = (function () {
    var instance;

    var gaugeChart = function () {
        var _gauges = {};
        function Gauge(placeholderSelector, configuration) {

            var zoneColours = {
                "green": "#109618",
                "yellow": "#FFFF00",
                "orange": "#FF9900",
                "red": "#DC3912"
            };

            // setup
            const data = {
                datasets: [{
                    label: configuration.caption,
                    data: [33,33,33],
                    backgroundColor: [
                        'rgba(255, 26, 104, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(0, 0, 0, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 26, 104, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)',
                        'rgba(0, 0, 0, 1)'
                    ],
                    borderWidth: 1,
                    rotation: 2/3 * 360,
                    circumference: 4/6 * 360,
                    needleValue: configuration.max !== 100 ? (configuration.current/configuration.max) * 100 : configuration.current,
                    cutout: '85%',
                }]
            };

            //plugins
            const gaugeNeedle = {
                id: 'gaugeNeedle',
                afterDatasetsDraw(chart, args, plugins) {
                    const { ctx, data } = chart;

                    ctx.save();
                    const needleValue = data.datasets[0].needleValue;
                    const xCenter = chart.getDatasetMeta(0).data[0].x;
                    const yCenter = chart.getDatasetMeta(0).data[0].y;
                    const outerRadius = chart.getDatasetMeta(0).data[0].outerRadius - 6;
                    const angle = Math.PI;

                    const dataTotal = data.datasets[0].data.reduce((a,b) => a+b, 0);
                    const needleValueAngle = (data.datasets[0].circumference / dataTotal) * needleValue + data.datasets[0].rotation;

                    ctx.translate(xCenter, yCenter);
                    ctx.rotate(needleValueAngle * Math.PI / 180);
                    //Needle
                    ctx.beginPath();
                    ctx.strokeStyle = 'darkgrey';
                    ctx.fillStyle = 'darkgrey';
                    ctx.moveTo(0 - 5, 0);
                    ctx.lineTo(0, - outerRadius);
                    ctx.lineTo(0 + 5, 0);
                    ctx.stroke();
                    ctx.fill();

                    //needle dot
                    ctx.beginPath();
                    ctx.arc(0, 0, 10, angle * 0, angle * 2, false);
                    ctx.fill();

                    ctx.restore();
                }
            }

            // config
            const config = {
                type: 'doughnut',
                data,
                options: {
                    responsive: true,
                    aspectRation: 1.8,
                    plugins: {
                        legend: {
                            display: false
                        },
                        tooltip: {
                            enabled: false
                        }
                    }
                },
                plugins: [gaugeNeedle]
            };

            const $canvas = $('<canvas></canvas>').attr('id', configuration.id);
            $(placeholderSelector).replaceWith($canvas);
            const ctx = $(placeholderSelector)[0].getContext('2d');
            this.chart = new Chart(ctx, config);

        }

        var _createGauge = function (containerId, config) {
            var gauge = new Gauge(containerId, config);

            _gauges[containerId] = {
                name: config.label,
                chart: gauge
            };

            //if (!_isMinMaxValid(config.min, config.max)) {
            //    return;
           //}

            //gauge.render();
            //gauge.redraw(config.current);
        };

        return (gaugeChart = function (container, params) {
            var d = params.data;

            console.log('params: ' + JSON.stringify(params));

            var config = {
                id: params.container,
                complete: d.complete,
                size: 160,
                label: d.name,
                caption: d.caption,
                min: undefined != d.min ? d.min : 0,
                max: undefined != d.max ? d.max : 100,
                minorTicks: 5,
                current: undefined != d.current ? d.current : 0,
                zones: d.zones || []
            };

            _createGauge(container, config);

        })(arguments[0], arguments[1]);
    }

    function createD3chart() {

        return function (id, params) {
            switch (params.type) {
                case "PieChart":
                    pieChart(id, params);
                    break;
                case "ColumnChart":
                    barChart(id, params);
                    break;
                case "LineChart":
                    lineChart(id, params);
                    break;
                case "GaugeChart":
                    gaugeChart(id, params);
                    break;
                case "HeatMapChart":
                    heatMapChart(id, params);
                    break;
                default:
                    lineChart(id, params);
            }
        }
    }

    return {
        getInstance: function () {
            if (instance === undefined) {
                instance = createD3chart();
            }
            return instance;
        }
    };
})();

