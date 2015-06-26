insert into property (id, version, location, latitude, longitude, property_type, property_status, price)
  values (1, 0, 'LARNACA', '34.7071301', '33.022617399999945', 'DETACHED_HOUSE', 'LONG_TERM', 123456);

insert into localized_property (id, version, locale, title, address, description, property_id)
  values (1, 0, 'en', 'A Larnaca Property #1', 'Some address', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras et dui vestibulum, bibendum purus sit amet, vulputate mauris. Ut adipiscing gravida tincidunt. Duis euismod placerat rhoncus. Phasellus mollis imperdiet placerat. Sed ac turpis nisl. Mauris at ante mauris. Aliquam posuere fermentum lorem, a aliquam mauris rutrum a. Curabitur sit amet pretium lectus, nec consequat orci.', 1);

insert into localized_property (id, version, locale, title, address, description, property_id)
  values (2, 0, 'ru', 'Объект в Ларнаке #1', 'Строка с адресом ...', 'Давно выяснено, что при оценке дизайна и композиции читаемый текст мешает сосредоточиться. Lorem Ipsum используют потому, что тот обеспечивает более или менее стандартное заполнение шаблона, а также реальное распределение букв и пробелов в абзацах, которое не получается при простой дубликации. Многие программы электронной вёрстки и редакторы HTML используют Lorem Ipsum в качестве текста по умолчанию, так что поиск по ключевым словам lorem ipsum сразу показывает, как много веб-страниц всё ещё дожидаются своего настоящего рождения. За прошедшие годы текст Lorem Ipsum получил много версий. Некоторые версии появились по ошибке, некоторые - намеренно (например, юмористические варианты).', 1);

insert into localized_property_specification (id, version, specification, localized_property_id)
  values (1, 0, 'Specification #1', 1),
    (2, 0, 'Specification #2', 1),
    (3, 0, 'Specification #3', 1),
    (4, 0, 'Specification #4', 1),
    (5, 0, 'Specification #5', 1),
    (6, 0, 'Спецификация #1', 2),
    (7, 0, 'Спецификация #2', 2),
    (8, 0, 'Спецификация #3', 2),
    (9, 0, 'Спецификация #4', 2),
    (10, 0, 'Спецификация #5', 2);