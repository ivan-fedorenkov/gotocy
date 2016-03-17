/**
 * Utility class.
 */

var Utils = {};

/**
 * Creates and appends a script element to the document body.
 */
Utils.appendScript = function(src) {
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = src;
    script.async = false;
    $(document.body).append(script);
};

/**
 * Returns the path prepended by the 'root'.
 */
Utils.getPath = function(path) {
    return path;
};