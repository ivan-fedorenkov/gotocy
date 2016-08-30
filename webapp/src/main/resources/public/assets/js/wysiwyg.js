/**
 * Wysiwyg editor class.
 */

var Wysiwyg = {};


/**
 * params:
 *  editor: editor selector
 *  inputByLanguage: language -> target selector
 *  currentLanguage: current language
 */
Wysiwyg.init = function(params) {
    Utils.appendScript(Utils.getPath('/assets/bootstrap3-wysiwyg/bootstrap3-wysihtml5.all.js'));

    Wysiwyg.editor = $(params.editor);
    Wysiwyg.currentLanguage = params.currentLanguage;

    var templates = {
        emphasis: function(context) {
            var locale = context.locale;
            return '<li><div class="btn-group">' +
                        '<a class="btn btn-primary" data-wysihtml5-command="bold" title="CTRL+B" tabindex="-1">' + locale.emphasis.bold + '</a>' +
                        '<a class="btn btn-primary" data-wysihtml5-command="italic" title="CTRL+I" tabindex="-1">' + locale.emphasis.italic + '</a>' +
                        '<a class="btn btn-primary" data-wysihtml5-command="underline" title="CTRL+U" tabindex="-1">' + locale.emphasis.underline + '</a>' +
                    '</div></li>'
        },

        lists: function(context) {
            var locale = context.locale;
            return '<li><div class="btn-group">' +
                        '<a class="btn btn-primary" data-wysihtml5-command="insertUnorderedList" title="' + locale.lists.unordered + '" tabindex="-1"><span class="fa fa-list-ul"></span></a>' +
                        '<a class="btn btn-primary" data-wysihtml5-command="insertOrderedList" title="' + locale.lists.ordered + '" tabindex="-1"><span class="fa fa-list-ol"></span></a>' +
                        '<a class="btn btn-primary" data-wysihtml5-command="Outdent" title="' + locale.lists.outdent + '" tabindex="-1"><span class="fa fa-outdent"></span></a>' +
                        '<a class="btn btn-primary" data-wysihtml5-command="Indent" title="' + locale.lists.indent + '" tabindex="-1"><span class="fa fa-indent"></span></a>' +
                    '</div></li>'
        },

        languages: function(context) {
            return '<li id="languages-commands"><div class="btn-group">' +
                        '<a data-target="' + params.inputByLanguage.en + '" data-language="en" class="btn btn-primary"><img alt="English" src="http://assets.gotocy.com/static/img/flags/gb.png"></a>' +
                        '<a data-target="' + params.inputByLanguage.ru + '" data-language="ru" class="btn btn-primary"><img alt="Russian" src="http://assets.gotocy.com/static/img/flags/ru.png"></a>' +
                        '<a data-target="' + params.inputByLanguage.el + '" data-language="el" class="btn btn-primary"><img alt="Greek" src="http://assets.gotocy.com/static/img/flags/gr.png"></a>' +
                    '</div></li>'
        }
    };

    Wysiwyg.editor.wysihtml5({
        toolbar: {"fa": true, "font-styles": false, "link":false, "image": false, "blockquote": false, "languages": true},
        customTemplates: templates
    });

    $('#languages-commands a').click(function() {
        var languageButton = Wysiwyg.getLanguageButton(Wysiwyg.currentLanguage);
        languageButton.removeClass('wysihtml5-command-active');
        Wysiwyg.getInputField(languageButton).val(Wysiwyg.editor.html());

        Wysiwyg.currentLanguage = $(this).data('language');
        $(this).addClass('wysihtml5-command-active');
        Wysiwyg.editor.html(Wysiwyg.getInputField($(this)).val());
    });

    Wysiwyg.editor.closest('form').submit(function() {
        var languageButton = Wysiwyg.getLanguageButton(Wysiwyg.currentLanguage);
        Wysiwyg.getInputField(languageButton).val(Wysiwyg.editor.html());
    });

    var languageButton = Wysiwyg.getLanguageButton(Wysiwyg.currentLanguage);
    languageButton.addClass('wysihtml5-command-active');
    Wysiwyg.editor.html(Wysiwyg.getInputField(languageButton).val());
};

/**
 * Returns language button element (jquery object).
 */
Wysiwyg.getLanguageButton = function(language) {
    return $('#languages-commands a[data-language="' + language + '"]');
};

/**
 * Return input field that specified language button refers to.
 */
Wysiwyg.getInputField = function(languageButton) {
    return $(languageButton.data('target'));
};
