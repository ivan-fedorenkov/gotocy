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

/**
 * Returns the properties search path.
 */
Utils.getPropertySearchPath = function(language, location, propertyStatus, propertyType) {
    var path = (language === 'en' ? '/' : language === 'ru' ? '/ru/' : '/el/');
    if (language === 'ru') {
        switch (propertyStatus) {
        case 'SALE':
            path += 'prodazha-';
            break;
        case 'SHORT_TERM':
            path += 'kratkosrochnaya-arenda-';
            break;
        case 'LONG_TERM':
            path += 'dolgosrochnaya-arenda-';
            break;
        }

        switch (propertyType) {
        case 'HOUSE':
            path += (propertyStatus ? 'kottedzhei-' : 'kottedzhi-');
            break;
        case 'APARTMENT':
            path += (propertyStatus ? 'apartamentov-' : 'apartamenti-');
            break;
        case 'LAND':
            path += (propertyStatus ? 'zemli-' : 'zemlya-');
            break;
        default:
            path += (propertyStatus ? 'nedvizhimosti-' : 'nedvizhimost-');
        }

        switch (location) {
        case 'AYIA_NAPA':
            path += 'v-ayia-nape';
            break;
        case 'FAMAGUSTA':
            path += 'v-famaguste';
            break;
        case 'LARNACA':
            path += 'v-larnake';
            break;
        case 'LIMASSOL':
            path += 'v-limassole';
            break;
        case 'NICOSIA':
            path += 'v-nikosii';
            break;
        case 'PAPHOS':
            path += 'v-pafose';
            break;
        case 'PROTARAS':
            path += 'v-protarase';
            break;
        case 'TROODOS':
            path += 'v-troodose';
            break;
        default:
            path += 'na-kipre';
        }
    } else {
        switch (propertyType) {
        case 'HOUSE':
            path += 'houses-';
            break;
        case 'APARTMENT':
            path += 'apartments-';
            break;
        case 'LAND':
            path += 'land-';
            break;
        default:
            path += 'properties-';
        }

        switch (propertyStatus) {
        case 'SALE':
            path += 'for-sale-';
            break;
        case 'SHORT_TERM':
            path += 'short-term-rental-';
            break;
        case 'LONG_TERM':
            path += 'long-term-rental-';
            break;
        }

        switch (location) {
        case 'AYIA_NAPA':
            path += 'in-ayia-napa';
            break;
        case 'FAMAGUSTA':
            path += 'in-famagusta';
            break;
        case 'LARNACA':
            path += 'in-larnaca';
            break;
        case 'LIMASSOL':
            path += 'in-limassol';
            break;
        case 'NICOSIA':
            path += 'in-nicosia';
            break;
        case 'PAPHOS':
            path += 'in-paphos';
            break;
        case 'PROTARAS':
            path += 'in-protaras';
            break;
        case 'TROODOS':
            path += 'in-troodos';
            break;
        default:
            path += 'in-cyprus';
        }
    }
    return path;
};