/**
 * Redefinitions of the Utils class for static environment.
 */

if (typeof(Utils) === 'undefined')
    throw 'Utils class must be defined. Include utils.js file before this one.';

Utils.getPath = function(path) {
    return '/parent/webapp/public' + path;
};