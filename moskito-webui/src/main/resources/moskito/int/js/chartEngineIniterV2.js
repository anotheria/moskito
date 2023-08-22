var chartEngineIniterV2 = {
    init: function (params) {
        const $container = $('#' + params.container);
        $container.empty();
        const ctx = $('<canvas></canvas>').appendTo($container)[0].getContext('2d');
        //var d3Chart = D3chart.getInstance();
        //d3Chart("#" + params.container, params);
        window.myGauge = new Chart(ctx, config);
    }
};

var D3chart = (function () {
    var instance;

    var gaugeChart = function () {
        var _gauges = {};
        function Gauge(placeholderSelector, configuration) {

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

            dispatch.on("refreshGauge", function (params) {
                var containerId = params.containerId,
                    min = params.min,
                    max = params.max,
                    current = params.current;

                if (!_isMinMaxValid(min, max)) {
                    return;
                }

                _gauges[containerId].chart.redrawValues(min, max, current);
            });
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

var config = {
    type: 'gauge',
    data: {
        labels: ['Success', 'Warning', 'Warning', 'Error'],
        datasets: [{
            data: data,
            value: value,
            backgroundColor: ['green', 'yellow', 'orange', 'red'],
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

