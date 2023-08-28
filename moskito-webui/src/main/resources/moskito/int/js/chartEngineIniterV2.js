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
            //plugins
            const gaugeNeedle = {
                id: 'gaugeNeedle',
                afterDatasetsDraw: function (chart, args, plugins) {
                    const { ctx, data } = chart;
                    ctx.save();

                    const dataset = data.datasets[0], metaData = chart.getDatasetMeta(0).data[0],
                        needleValue = dataset.needleValue,
                        xCenter = metaData.x,
                        yCenter = metaData.y,
                        outerRadius = metaData.outerRadius - 6,
                        dataTotal = dataset.max - dataset.min,
                        needleValueAngle = (dataset.circumference / dataTotal) * needleValue + dataset.rotation;

                    ctx.translate(xCenter, yCenter);
                    ctx.rotate(needleValueAngle * Math.PI / 180);
                    //Needle
                    ctx.beginPath();
                    ctx.strokeStyle = 'darkgrey';
                    ctx.fillStyle = 'darkgrey';
                    ctx.moveTo(-5, 0);
                    ctx.lineTo(0, -outerRadius);
                    ctx.lineTo(5, 0);
                    ctx.stroke();
                    ctx.fill();

                    //needle dot
                    ctx.beginPath();
                    ctx.arc(0, 0, 10, 0, Math.PI * 2, false);
                    ctx.fill();

                    ctx.restore();
                }
            }

            const gaugeLabels = {
                id: 'gaugeLabels',
                afterDatasetsDraw: (chart, args, plugins) => {
                    const { ctx } = chart;
                    ctx.save();

                    const metaData = chart.getDatasetMeta(0).data[0], dataset = data.datasets[0],
                        xCenter = metaData.x, yCenter = metaData.y,
                        outerRadius = metaData.outerRadius,
                        innerRadius = metaData.innerRadius,
                        widthSlice = (outerRadius - innerRadius) / 2;

                    ctx.translate(xCenter, yCenter);
                    ctx.font = '15px sans-serif';
                    ctx.fillStyle = 'black';
                    ctx.textAlign = 'center';
                    ctx.fillText(dataset.min, -(innerRadius + widthSlice), 90);
                    ctx.fillText(dataset.max, innerRadius +  widthSlice, 90);
                    ctx.fillText(dataset.needleValue, 0, 90);

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
                plugins: [gaugeNeedle, gaugeLabels]
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

