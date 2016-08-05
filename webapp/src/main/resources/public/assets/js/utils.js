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
Utils.getPropertySearchPath = function(language, location, offerType, propertyType) {

    var languageKey = language === 'ru' ? 'ru' : 'default';

    var urlKey = [];
    if (location) {
        urlKey.push(location);
    }
    if (offerType) {
        urlKey.push(offerType);
    }
    if (propertyType) {
        urlKey.push(propertyType);
    }

    return (language === 'en' ? '/' : language === 'ru' ? '/ru/' : '/el/') +
        Utils.propertySearchPath[urlKey.join('_')][languageKey];
};

Utils.propertySearchPath = {
    "": {"default": "properties-in-cyprus", "ru": "nedvizhimost-na-kipre"},
    "AYIA_NAPA": {"default": "properties-in-ayia-napa", "ru": "nedvizhimost-v-ayia-nape"},
    "FAMAGUSTA": {"default": "properties-in-famagusta", "ru": "nedvizhimost-v-famaguste"},
    "LARNACA": {"default": "properties-in-larnaca", "ru": "nedvizhimost-v-larnake"},
    "LIMASSOL": {"default": "properties-in-limassol", "ru": "nedvizhimost-v-limassole"},
    "NICOSIA": {"default": "properties-in-nicosia", "ru": "nedvizhimost-v-nikosii"},
    "PAPHOS": {"default": "properties-in-paphos", "ru": "nedvizhimost-v-pafose"},
    "PROTARAS": {"default": "properties-in-protaras", "ru": "nedvizhimost-v-protarase"},
    "TROODOS": {"default": "properties-in-troodos", "ru": "nedvizhimost-v-troodose"},
    "LONG_TERM": {"default": "properties-long-term-rental-in-cyprus", "ru": "arenda-nedvizhimosti-na-kipre"},
    "AYIA_NAPA_LONG_TERM": {"default": "properties-long-term-rental-in-ayia-napa", "ru": "arenda-nedvizhimosti-v-ayia-nape"},
    "FAMAGUSTA_LONG_TERM": {"default": "properties-long-term-rental-in-famagusta", "ru": "arenda-nedvizhimosti-v-famaguste"},
    "LARNACA_LONG_TERM": {"default": "properties-long-term-rental-in-larnaca", "ru": "arenda-nedvizhimosti-v-larnake"},
    "LIMASSOL_LONG_TERM": {"default": "properties-long-term-rental-in-limassol", "ru": "arenda-nedvizhimosti-v-limassole"},
    "NICOSIA_LONG_TERM": {"default": "properties-long-term-rental-in-nicosia", "ru": "arenda-nedvizhimosti-v-nikosii"},
    "PAPHOS_LONG_TERM": {"default": "properties-long-term-rental-in-paphos", "ru": "arenda-nedvizhimosti-v-pafose"},
    "PROTARAS_LONG_TERM": {"default": "properties-long-term-rental-in-protaras", "ru": "arenda-nedvizhimosti-v-protarase"},
    "TROODOS_LONG_TERM": {"default": "properties-long-term-rental-in-troodos", "ru": "arenda-nedvizhimosti-v-troodose"},
    "SHORT_TERM": {"default": "properties-short-term-rental-in-cyprus", "ru": "otdih-na-kipre-nedvizhimost"},
    "AYIA_NAPA_SHORT_TERM": {"default": "properties-short-term-rental-in-ayia-napa", "ru": "otdih-na-kipre-v-ayia-nape-nedvizhimost"},
    "FAMAGUSTA_SHORT_TERM": {"default": "properties-short-term-rental-in-famagusta", "ru": "otdih-na-kipre-v-famaguste-nedvizhimost"},
    "LARNACA_SHORT_TERM": {"default": "properties-short-term-rental-in-larnaca", "ru": "otdih-na-kipre-v-larnake-nedvizhimost"},
    "LIMASSOL_SHORT_TERM": {"default": "properties-short-term-rental-in-limassol", "ru": "otdih-na-kipre-v-limassole-nedvizhimost"},
    "NICOSIA_SHORT_TERM": {"default": "properties-short-term-rental-in-nicosia", "ru": "otdih-na-kipre-v-nikosii-nedvizhimost"},
    "PAPHOS_SHORT_TERM": {"default": "properties-short-term-rental-in-paphos", "ru": "otdih-na-kipre-v-pafose-nedvizhimost"},
    "PROTARAS_SHORT_TERM": {"default": "properties-short-term-rental-in-protaras", "ru": "otdih-na-kipre-v-protarase-nedvizhimost"},
    "TROODOS_SHORT_TERM": {"default": "properties-short-term-rental-in-troodos", "ru": "otdih-na-kipre-v-troodose-nedvizhimost"},
    "SALE": {"default": "properties-for-sale-in-cyprus", "ru": "prodazha-nedvizhimosti-na-kipre"},
    "AYIA_NAPA_SALE": {"default": "properties-for-sale-in-ayia-napa", "ru": "prodazha-nedvizhimosti-v-ayia-nape"},
    "FAMAGUSTA_SALE": {"default": "properties-for-sale-in-famagusta", "ru": "prodazha-nedvizhimosti-v-famaguste"},
    "LARNACA_SALE": {"default": "properties-for-sale-in-larnaca", "ru": "prodazha-nedvizhimosti-v-larnake"},
    "LIMASSOL_SALE": {"default": "properties-for-sale-in-limassol", "ru": "prodazha-nedvizhimosti-v-limassole"},
    "NICOSIA_SALE": {"default": "properties-for-sale-in-nicosia", "ru": "prodazha-nedvizhimosti-v-nikosii"},
    "PAPHOS_SALE": {"default": "properties-for-sale-in-paphos", "ru": "prodazha-nedvizhimosti-v-pafose"},
    "PROTARAS_SALE": {"default": "properties-for-sale-in-protaras", "ru": "prodazha-nedvizhimosti-v-protarase"},
    "TROODOS_SALE": {"default": "properties-for-sale-in-troodos", "ru": "prodazha-nedvizhimosti-v-troodose"},
    "APARTMENT": {"default": "apartments-in-cyprus", "ru": "apartamenti-na-kipre"},
    "AYIA_NAPA_APARTMENT": {"default": "apartments-in-ayia-napa", "ru": "apartamenti-v-ayia-nape"},
    "FAMAGUSTA_APARTMENT": {"default": "apartments-in-famagusta", "ru": "apartamenti-v-famaguste"},
    "LARNACA_APARTMENT": {"default": "apartments-in-larnaca", "ru": "apartamenti-v-larnake"},
    "LIMASSOL_APARTMENT": {"default": "apartments-in-limassol", "ru": "apartamenti-v-limassole"},
    "NICOSIA_APARTMENT": {"default": "apartments-in-nicosia", "ru": "apartamenti-v-nikosii"},
    "PAPHOS_APARTMENT": {"default": "apartments-in-paphos", "ru": "apartamenti-v-pafose"},
    "PROTARAS_APARTMENT": {"default": "apartments-in-protaras", "ru": "apartamenti-v-protarase"},
    "TROODOS_APARTMENT": {"default": "apartments-in-troodos", "ru": "apartamenti-v-troodose"},
    "LONG_TERM_APARTMENT": {"default": "apartments-long-term-rental-in-cyprus", "ru": "arenda-apartamentov-na-kipre"},
    "AYIA_NAPA_LONG_TERM_APARTMENT": {"default": "apartments-long-term-rental-in-ayia-napa", "ru": "arenda-apartamentov-v-ayia-nape"},
    "FAMAGUSTA_LONG_TERM_APARTMENT": {"default": "apartments-long-term-rental-in-famagusta", "ru": "arenda-apartamentov-v-famaguste"},
    "LARNACA_LONG_TERM_APARTMENT": {"default": "apartments-long-term-rental-in-larnaca", "ru": "arenda-apartamentov-v-larnake"},
    "LIMASSOL_LONG_TERM_APARTMENT": {"default": "apartments-long-term-rental-in-limassol", "ru": "arenda-apartamentov-v-limassole"},
    "NICOSIA_LONG_TERM_APARTMENT": {"default": "apartments-long-term-rental-in-nicosia", "ru": "arenda-apartamentov-v-nikosii"},
    "PAPHOS_LONG_TERM_APARTMENT": {"default": "apartments-long-term-rental-in-paphos", "ru": "arenda-apartamentov-v-pafose"},
    "PROTARAS_LONG_TERM_APARTMENT": {"default": "apartments-long-term-rental-in-protaras", "ru": "arenda-apartamentov-v-protarase"},
    "TROODOS_LONG_TERM_APARTMENT": {"default": "apartments-long-term-rental-in-troodos", "ru": "arenda-apartamentov-v-troodose"},
    "SHORT_TERM_APARTMENT": {"default": "apartments-short-term-rental-in-cyprus", "ru": "otdih-na-kipre-apartamenti"},
    "AYIA_NAPA_SHORT_TERM_APARTMENT": {"default": "apartments-short-term-rental-in-ayia-napa", "ru": "otdih-na-kipre-v-ayia-nape-apartamenti"},
    "FAMAGUSTA_SHORT_TERM_APARTMENT": {"default": "apartments-short-term-rental-in-famagusta", "ru": "otdih-na-kipre-v-famaguste-apartamenti"},
    "LARNACA_SHORT_TERM_APARTMENT": {"default": "apartments-short-term-rental-in-larnaca", "ru": "otdih-na-kipre-v-larnake-apartamenti"},
    "LIMASSOL_SHORT_TERM_APARTMENT": {"default": "apartments-short-term-rental-in-limassol", "ru": "otdih-na-kipre-v-limassole-apartamenti"},
    "NICOSIA_SHORT_TERM_APARTMENT": {"default": "apartments-short-term-rental-in-nicosia", "ru": "otdih-na-kipre-v-nikosii-apartamenti"},
    "PAPHOS_SHORT_TERM_APARTMENT": {"default": "apartments-short-term-rental-in-paphos", "ru": "otdih-na-kipre-v-pafose-apartamenti"},
    "PROTARAS_SHORT_TERM_APARTMENT": {"default": "apartments-short-term-rental-in-protaras", "ru": "otdih-na-kipre-v-protarase-apartamenti"},
    "TROODOS_SHORT_TERM_APARTMENT": {"default": "apartments-short-term-rental-in-troodos", "ru": "otdih-na-kipre-v-troodose-apartamenti"},
    "SALE_APARTMENT": {"default": "apartments-for-sale-in-cyprus", "ru": "prodazha-apartamentov-na-kipre"},
    "AYIA_NAPA_SALE_APARTMENT": {"default": "apartments-for-sale-in-ayia-napa", "ru": "prodazha-apartamentov-v-ayia-nape"},
    "FAMAGUSTA_SALE_APARTMENT": {"default": "apartments-for-sale-in-famagusta", "ru": "prodazha-apartamentov-v-famaguste"},
    "LARNACA_SALE_APARTMENT": {"default": "apartments-for-sale-in-larnaca", "ru": "prodazha-apartamentov-v-larnake"},
    "LIMASSOL_SALE_APARTMENT": {"default": "apartments-for-sale-in-limassol", "ru": "prodazha-apartamentov-v-limassole"},
    "NICOSIA_SALE_APARTMENT": {"default": "apartments-for-sale-in-nicosia", "ru": "prodazha-apartamentov-v-nikosii"},
    "PAPHOS_SALE_APARTMENT": {"default": "apartments-for-sale-in-paphos", "ru": "prodazha-apartamentov-v-pafose"},
    "PROTARAS_SALE_APARTMENT": {"default": "apartments-for-sale-in-protaras", "ru": "prodazha-apartamentov-v-protarase"},
    "TROODOS_SALE_APARTMENT": {"default": "apartments-for-sale-in-troodos", "ru": "prodazha-apartamentov-v-troodose"},
    "HOUSE": {"default": "houses-in-cyprus", "ru": "doma-na-kipre"},
    "AYIA_NAPA_HOUSE": {"default": "houses-in-ayia-napa", "ru": "doma-v-ayia-nape"},
    "FAMAGUSTA_HOUSE": {"default": "houses-in-famagusta", "ru": "doma-v-famaguste"},
    "LARNACA_HOUSE": {"default": "houses-in-larnaca", "ru": "doma-v-larnake"},
    "LIMASSOL_HOUSE": {"default": "houses-in-limassol", "ru": "doma-v-limassole"},
    "NICOSIA_HOUSE": {"default": "houses-in-nicosia", "ru": "doma-v-nikosii"},
    "PAPHOS_HOUSE": {"default": "houses-in-paphos", "ru": "doma-v-pafose"},
    "PROTARAS_HOUSE": {"default": "houses-in-protaras", "ru": "doma-v-protarase"},
    "TROODOS_HOUSE": {"default": "houses-in-troodos", "ru": "doma-v-troodose"},
    "LONG_TERM_HOUSE": {"default": "houses-long-term-rental-in-cyprus", "ru": "arenda-domov-na-kipre"},
    "AYIA_NAPA_LONG_TERM_HOUSE": {"default": "houses-long-term-rental-in-ayia-napa", "ru": "arenda-domov-v-ayia-nape"},
    "FAMAGUSTA_LONG_TERM_HOUSE": {"default": "houses-long-term-rental-in-famagusta", "ru": "arenda-domov-v-famaguste"},
    "LARNACA_LONG_TERM_HOUSE": {"default": "houses-long-term-rental-in-larnaca", "ru": "arenda-domov-v-larnake"},
    "LIMASSOL_LONG_TERM_HOUSE": {"default": "houses-long-term-rental-in-limassol", "ru": "arenda-domov-v-limassole"},
    "NICOSIA_LONG_TERM_HOUSE": {"default": "houses-long-term-rental-in-nicosia", "ru": "arenda-domov-v-nikosii"},
    "PAPHOS_LONG_TERM_HOUSE": {"default": "houses-long-term-rental-in-paphos", "ru": "arenda-domov-v-pafose"},
    "PROTARAS_LONG_TERM_HOUSE": {"default": "houses-long-term-rental-in-protaras", "ru": "arenda-domov-v-protarase"},
    "TROODOS_LONG_TERM_HOUSE": {"default": "houses-long-term-rental-in-troodos", "ru": "arenda-domov-v-troodose"},
    "SHORT_TERM_HOUSE": {"default": "houses-short-term-rental-in-cyprus", "ru": "otdih-na-kipre-doma"},
    "AYIA_NAPA_SHORT_TERM_HOUSE": {"default": "houses-short-term-rental-in-ayia-napa", "ru": "otdih-na-kipre-v-ayia-nape-doma"},
    "FAMAGUSTA_SHORT_TERM_HOUSE": {"default": "houses-short-term-rental-in-famagusta", "ru": "otdih-na-kipre-v-famaguste-doma"},
    "LARNACA_SHORT_TERM_HOUSE": {"default": "houses-short-term-rental-in-larnaca", "ru": "otdih-na-kipre-v-larnake-doma"},
    "LIMASSOL_SHORT_TERM_HOUSE": {"default": "houses-short-term-rental-in-limassol", "ru": "otdih-na-kipre-v-limassole-doma"},
    "NICOSIA_SHORT_TERM_HOUSE": {"default": "houses-short-term-rental-in-nicosia", "ru": "otdih-na-kipre-v-nikosii-doma"},
    "PAPHOS_SHORT_TERM_HOUSE": {"default": "houses-short-term-rental-in-paphos", "ru": "otdih-na-kipre-v-pafose-doma"},
    "PROTARAS_SHORT_TERM_HOUSE": {"default": "houses-short-term-rental-in-protaras", "ru": "otdih-na-kipre-v-protarase-doma"},
    "TROODOS_SHORT_TERM_HOUSE": {"default": "houses-short-term-rental-in-troodos", "ru": "otdih-na-kipre-v-troodose-doma"},
    "SALE_HOUSE": {"default": "houses-for-sale-in-cyprus", "ru": "prodazha-domov-na-kipre"},
    "AYIA_NAPA_SALE_HOUSE": {"default": "houses-for-sale-in-ayia-napa", "ru": "prodazha-domov-v-ayia-nape"},
    "FAMAGUSTA_SALE_HOUSE": {"default": "houses-for-sale-in-famagusta", "ru": "prodazha-domov-v-famaguste"},
    "LARNACA_SALE_HOUSE": {"default": "houses-for-sale-in-larnaca", "ru": "prodazha-domov-v-larnake"},
    "LIMASSOL_SALE_HOUSE": {"default": "houses-for-sale-in-limassol", "ru": "prodazha-domov-v-limassole"},
    "NICOSIA_SALE_HOUSE": {"default": "houses-for-sale-in-nicosia", "ru": "prodazha-domov-v-nikosii"},
    "PAPHOS_SALE_HOUSE": {"default": "houses-for-sale-in-paphos", "ru": "prodazha-domov-v-pafose"},
    "PROTARAS_SALE_HOUSE": {"default": "houses-for-sale-in-protaras", "ru": "prodazha-domov-v-protarase"},
    "TROODOS_SALE_HOUSE": {"default": "houses-for-sale-in-troodos", "ru": "prodazha-domov-v-troodose"},
    "LAND": {"default": "land-in-cyprus", "ru": "zemlya-na-kipre"},
    "AYIA_NAPA_LAND": {"default": "land-in-ayia-napa", "ru": "zemlya-v-ayia-nape"},
    "FAMAGUSTA_LAND": {"default": "land-in-famagusta", "ru": "zemlya-v-famaguste"},
    "LARNACA_LAND": {"default": "land-in-larnaca", "ru": "zemlya-v-larnake"},
    "LIMASSOL_LAND": {"default": "land-in-limassol", "ru": "zemlya-v-limassole"},
    "NICOSIA_LAND": {"default": "land-in-nicosia", "ru": "zemlya-v-nikosii"},
    "PAPHOS_LAND": {"default": "land-in-paphos", "ru": "zemlya-v-pafose"},
    "PROTARAS_LAND": {"default": "land-in-protaras", "ru": "zemlya-v-protarase"},
    "TROODOS_LAND": {"default": "land-in-troodos", "ru": "zemlya-v-troodose"},
    "LONG_TERM_LAND": {"default": "land-long-term-rental-in-cyprus", "ru": "arenda-zemli-na-kipre"},
    "AYIA_NAPA_LONG_TERM_LAND": {"default": "land-long-term-rental-in-ayia-napa", "ru": "arenda-zemli-v-ayia-nape"},
    "FAMAGUSTA_LONG_TERM_LAND": {"default": "land-long-term-rental-in-famagusta", "ru": "arenda-zemli-v-famaguste"},
    "LARNACA_LONG_TERM_LAND": {"default": "land-long-term-rental-in-larnaca", "ru": "arenda-zemli-v-larnake"},
    "LIMASSOL_LONG_TERM_LAND": {"default": "land-long-term-rental-in-limassol", "ru": "arenda-zemli-v-limassole"},
    "NICOSIA_LONG_TERM_LAND": {"default": "land-long-term-rental-in-nicosia", "ru": "arenda-zemli-v-nikosii"},
    "PAPHOS_LONG_TERM_LAND": {"default": "land-long-term-rental-in-paphos", "ru": "arenda-zemli-v-pafose"},
    "PROTARAS_LONG_TERM_LAND": {"default": "land-long-term-rental-in-protaras", "ru": "arenda-zemli-v-protarase"},
    "TROODOS_LONG_TERM_LAND": {"default": "land-long-term-rental-in-troodos", "ru": "arenda-zemli-v-troodose"},
    "SHORT_TERM_LAND": {"default": "land-short-term-rental-in-cyprus", "ru": "otdih-na-kipre-zemlya"},
    "AYIA_NAPA_SHORT_TERM_LAND": {"default": "land-short-term-rental-in-ayia-napa", "ru": "otdih-na-kipre-v-ayia-nape-zemlya"},
    "FAMAGUSTA_SHORT_TERM_LAND": {"default": "land-short-term-rental-in-famagusta", "ru": "otdih-na-kipre-v-famaguste-zemlya"},
    "LARNACA_SHORT_TERM_LAND": {"default": "land-short-term-rental-in-larnaca", "ru": "otdih-na-kipre-v-larnake-zemlya"},
    "LIMASSOL_SHORT_TERM_LAND": {"default": "land-short-term-rental-in-limassol", "ru": "otdih-na-kipre-v-limassole-zemlya"},
    "NICOSIA_SHORT_TERM_LAND": {"default": "land-short-term-rental-in-nicosia", "ru": "otdih-na-kipre-v-nikosii-zemlya"},
    "PAPHOS_SHORT_TERM_LAND": {"default": "land-short-term-rental-in-paphos", "ru": "otdih-na-kipre-v-pafose-zemlya"},
    "PROTARAS_SHORT_TERM_LAND": {"default": "land-short-term-rental-in-protaras", "ru": "otdih-na-kipre-v-protarase-zemlya"},
    "TROODOS_SHORT_TERM_LAND": {"default": "land-short-term-rental-in-troodos", "ru": "otdih-na-kipre-v-troodose-zemlya"},
    "SALE_LAND": {"default": "land-for-sale-in-cyprus", "ru": "prodazha-zemli-na-kipre"},
    "AYIA_NAPA_SALE_LAND": {"default": "land-for-sale-in-ayia-napa", "ru": "prodazha-zemli-v-ayia-nape"},
    "FAMAGUSTA_SALE_LAND": {"default": "land-for-sale-in-famagusta", "ru": "prodazha-zemli-v-famaguste"},
    "LARNACA_SALE_LAND": {"default": "land-for-sale-in-larnaca", "ru": "prodazha-zemli-v-larnake"},
    "LIMASSOL_SALE_LAND": {"default": "land-for-sale-in-limassol", "ru": "prodazha-zemli-v-limassole"},
    "NICOSIA_SALE_LAND": {"default": "land-for-sale-in-nicosia", "ru": "prodazha-zemli-v-nikosii"},
    "PAPHOS_SALE_LAND": {"default": "land-for-sale-in-paphos", "ru": "prodazha-zemli-v-pafose"},
    "PROTARAS_SALE_LAND": {"default": "land-for-sale-in-protaras", "ru": "prodazha-zemli-v-protarase"},
    "TROODOS_SALE_LAND": {"default": "land-for-sale-in-troodos", "ru": "prodazha-zemli-v-troodose"}
};