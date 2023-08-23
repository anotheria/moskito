var chartEngineIniterV2 = {
    init: function (params) {

        $('#' + params.container).empty();

        const d3ChartV2 = D3chartV2.getInstance();
        d3ChartV2("#" + params.container, params);

    }
};

var D3chartV2 = (function () {
    var instance;

    var gaugeChart = function () {
        var _gauges = {};
        function Gauge(placeholderSelector, configuration) {

            var randomScalingFactor = function () {
                return Math.round(Math.random() * 100);
            };

            var randomData = function () {
                return [
                    randomScalingFactor(),
                    randomScalingFactor(),
                    randomScalingFactor(),
                    randomScalingFactor()
                ];
            };

            var randomValue = function (data) {
                return Math.max.apply(null, data) * Math.random();
            };

            var data = randomData();
            var value = randomValue(data);

            var zoneColours = {
                "green": "#109618",
                "yellow": "#FFFF00",
                "orange": "#FF9900",
                "red": "#DC3912"
            };

            var config = {
                type: 'gauge',
                data: {
                    labels: ['Success', 'Warning', 'Warning', 'Error'],
                    datasets: [{
                        data: data,
                        value: value,
                        backgroundColor: [zoneColours.green, zoneColours.yellow, zoneColours.orange, zoneColours.red],
                        borderWidth: 2
                    }]
                },
                options: {
                    responsive: true,
                    title: {
                        display: true,
                        text: 'Gauge chart'
                    },
                    layout: {
                        padding: {
                            bottom: 30
                        }
                    },
                    needle: {
                        // Needle circle radius as the percentage of the chart area width
                        radiusPercentage: 2,
                        // Needle width as the percentage of the chart area width
                        widthPercentage: 3.2,
                        // Needle length as the percentage of the interval between inner radius (0%) and outer radius (100%) of the arc
                        lengthPercentage: 80,
                        // The color of the needle
                        color: 'rgba(0, 0, 240, 1)'
                    },
                    valueLabel: {
                        formatter: Math.round
                    }
                }
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

