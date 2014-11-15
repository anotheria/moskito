/**
 * Script for JSTalkBackFilter.
 *
 * Example of usage:
 *     <script type="text/javascript"
 *     src="/moskito/js/jstalkback.js?
 *     filter=/moskito/jstalkbackfilter&
 *     producerId=PRODUCER_ID&
 *     category=CATEGORY_NAME&
 *     subsystem=NAME_OF_THE_SUBSYSTEM"></script>
 *
 * 'filter' parameter is a path to the JSTalkBackFilter, mandatory.
 * 'producerId' parameter is an id of the producer, if was not specified - default value will be used.
 * 'category' parameter is category name of the producer, if was not specified - default value will be used.
 * 'subsystem' parameter is a subsystem of the producer, if was not specified - default value will be used.
 */
(function () {
    var settings = getSettings(),
        startTime = new Date().getTime(),
        timing = window.performance && window.performance.timing,
        domLoadEndTime = 0;

    // if performance.timing not supported
    if (!timing) {
        /**
         * DOM load event listener.
         * Fired when DOM for the page is constructed.
         */
        document.addEventListener('DOMContentLoaded', function () {
            domLoadEndTime = new Date().getTime();
        });
    }

    /**
     * Window load event listener.
     * Fired when page completely loaded - DOM, images, scripts, sub-frames etc.
     */
    window.addEventListener("load", function () {
        setTimeout(function () {
            var data = calculateTimeData();
            notifyFilter(data);
        }, 0);
    }, false);

    /**
     * Calculate DOM load time and window load time.
     * If performance.timing is supported then it will be used for the calculation.
     *
     * @returns {{domLoadTime: number, windowLoadTime: number}}
     */
    function calculateTimeData() {
        if (timing) {
            return {
                domLoadTime: timing.domContentLoadedEventEnd - timing.domContentLoadedEventStart,
                windowLoadTime: timing.loadEventEnd - timing.navigationStart
            };
        }

        return {
            domLoadTime: domLoadEndTime - startTime,
            windowLoadTime: new Date().getTime() - startTime
        };
    }

    /**
     * Notify Moskito JSTalkBackFilter about given stats.
     *
     * @param data - stats data
     */
    function notifyFilter(data) {
        var pathName = window.location.pathname,
            domLoadTime = data.domLoadTime || 0,
            windowLoadTime = data.windowLoadTime || 0;

        var src = settings.filter + "?";
        src += "url=" + encodeURIComponent(pathName);
        src += "&domLoadTime=" + domLoadTime;
        src += "&windowLoadTime=" + windowLoadTime;
        if (settings.producerId)
            src += "&producerId=" + settings.producerId;
        if (settings.category)
            src += "&category=" + settings.category;
        if (settings.subsystem)
            src += "&subsystem=" + settings.subsystem;

        new Image().src = src;
    }

    /**
     * Gets settings from jstalkback script query url.
     *
     * @returns map with query params
     */
    function getSettings() {
        var script = document.querySelector("script[src*='/jstalkback.js']");
        if (!script)
            throw new Error("Can't find jstalkback.js script.");

        var map = {},
            scriptUrl = script.src,
            query = scriptUrl.substr(scriptUrl.indexOf('?') + 1);

        query.replace(/([^&=]+)=?([^&]*)/g, function (match, key, value) {
            map[key] = value;
        });

        if (!map.filter)
            throw new Error("Please specify script settings.");

        return map;
    }
}());
