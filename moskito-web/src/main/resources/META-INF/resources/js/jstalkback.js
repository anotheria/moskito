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
        startTime = new Date().getTime();

    /**
     * Window load event handler.
     * Fired when page completely loaded - DOM, images, scripts, sub-frames etc.
     */
    window.addEventListener("load", function () {
        var time = new Date().getTime() - startTime;

        notifyFilter({
            windowLoadTime: time
        });
    }, false);

    /**
     * DOM load event handler.
     * Fired when DOM for the page is constructed.
     */
    document.addEventListener('DOMContentLoaded', function () {
        var time = new Date().getTime() - startTime;

        notifyFilter({
            domLoadTime: time
        });
    });

    /**
     * Notify Moskito JSTalkBackFilter with stats.
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