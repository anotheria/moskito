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

            const zoneColours = {
                "green": "rgba(16, 150, 24, .9)",
                "yellow": "rgba(255, 255, 0, .9)",
                "orange": "rgba(255, 153, 0, .9)",
                "red": "rgba(220, 57, 18, .9)"
            };

            const dataFrom = (anyData) => {
                const data = {data:[]};
                data.backgroundColor = ['rgba(0, 0, 0, 0.1)'];

                const min = anyData.min ? anyData.min : 0;
                const max = anyData.max ? anyData.max : 100;
                if (!anyData.zones || anyData.zones.lenght === 0) {
                    data.data.push(max);
                } else {
                    const interval = max - min;
                    anyData.zones.forEach((zone, index) => {

                        if(index===0 && zone.from!==0) data.data.push(interval * zone.from);
                        if(index===0 && zone.from===0) {
                            data.data.push(interval * zone.to);
                            data.backgroundColor = [];
                        } else {
                            const dataPoint = interval * (zone.to - zone.from);
                            data.data.push(dataPoint);
                        }
                        data.backgroundColor.push(zoneColours[zone.color]);
                    })
                }

                data.min = min;
                data.max = max;
                data.needleValue = anyData.current;
                return data;
            };

            // setup
            const data = {
                datasets: [{
                    label: 'Weekly Sales',
                    rotation: 2/3 * 360,
                    circumference: 4/6 * 360,
                    cutout: '85%',
                    ...dataFrom(configuration)
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

                    //const dataTotal = data.datasets[0].data.reduce((a,b) => a+b, 0);
                    const dataTotal = data.datasets[0].max - data.datasets[0].min;
                    const needleValueAngle = (data.datasets[0].circumference / dataTotal) * needleValue + data.datasets[0].rotation;

                    ctx.translate(xCenter, yCenter);
                    ctx.rotate(needleValueAngle * Math.PI / 180);
                    //Needle
                    ctx.beginPath();
                    ctx.strokeStyle = 'darkgrey';
                    ctx.fillStyle = 'darkgrey';
                    ctx.moveTo(-5, 0);
                    ctx.lineTo(0, -outerRadius);
                    ctx.lineTo(+5, 0);
                    ctx.stroke();
                    ctx.fill();

                    //needle dot
                    ctx.beginPath();
                    ctx.arc(0, 0, 10, 0, angle * 2, false);
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

