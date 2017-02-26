function countGauges() {
        return $('.gauge').length;
    }
function saveSvgAsPng(event, index) {
    event.preventDefault();
    event.stopPropagation();

    var margin = 30;

    var svgOrigin = document.getElementsByTagName("svg")[index];
    var svg = svgOrigin.cloneNode(true);


    svg.setAttribute("style", "background-color: #FFFFFF;");
    svg.setAttribute("x",margin);
    svg.setAttribute("y",margin);

    var css = '.axis path,'+
        '.axis line {'+
        'fill: none;'+
        'stroke: #000;'+
        'shape-rendering: crispEdges;'+
        '}'+
        '.legend, .tick {'+
        'font: 12px sans-serif;'+
        '}'+

        '.line {'+
        'fill: none;'+
        'stroke: steelblue;'+
        'stroke-width: 1.5px;'+
        '}'+

        '.line.hover {'+
        'fill: none;'+
        'stroke: steelblue;'+
        'stroke-width: 3.0px;'+
        '}'+

        '.grid .tick {'+
        'stroke: lightgrey;'+
        'opacity: 0.7;'+
        '}'+
        '.grid path {'+
        'stroke-width: 0;'+
        '}';

    var style = document.createElement('style');
    style.type = 'text/css';
    if (style.styleSheet){
        style.styleSheet.cssText = css;
    } else {
        style.appendChild(document.createTextNode(css));
    }

    svg.appendChild(style);

    var svgData = new XMLSerializer().serializeToString(svg);

    var originWidth = parseInt($(svg).attr("width"))+margin
    var originHeight = parseInt($(svg).attr("height"))+margin;
    svgData ='<svg xmlns="http://www.w3.org/2000/svg"  style="background-color: #FFFFFF;" width="'+ originWidth+'" height="'+ originHeight+'" >' + svgData + '</svg>';

    var canvas = document.createElement("canvas");
    canvas.width  = originWidth + 2*margin;
    canvas.height = originHeight + 2*margin;
    var ctx = canvas.getContext("2d");
    ctx.fillStyle="white";
    ctx.fill();

    var img = document.createElement("img");
    var encoded_svg = btoa(svgData.replace(/[\u00A0-\u2666]/g, function(c) {
        return '&#' + c.charCodeAt(0) + ';';
    }));
    img.setAttribute("src", "data:image/svg+xml;base64," + encoded_svg);

    img.onload = function () {
        ctx.drawImage(img, 0, 0);
        var canvasdata = canvas.toDataURL("image/png");
        var a = document.createElement("a");
        var file_name = getChartFileName(index);

        a.download = file_name + ".png";
        a.href = canvasdata;
        document.body.appendChild(a);
        a.click();

    };
}
function getChartFileName(index) {
    var t = new Date($.now());
    var current_date = t.getFullYear()+'-'+ t.getMonth()+'-'+ t.getDate()+'__'+t.getHours()+'-'+ t.getMinutes();
    var chart_header = document.getElementsByClassName("chart-header")[index];
    return $.trim(chart_header.innerText).split(' ').join('_')+'_'+current_date;
}