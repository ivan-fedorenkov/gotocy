insert into asset (id, version, key, asset_type) values
  (1, 0, 'property/1/34634941.jpg', 'IMAGE'),
  (2, 0, 'property/1/35066473.jpg', 'IMAGE'),
  (3, 0, 'property/1/36526520.jpg', 'IMAGE'),
  (4, 0, 'property/1/36526526.jpg', 'IMAGE');

insert into property (id, version, location, latitude, longitude, property_type, property_status, price, thumbnail_id)
  values (1, 0, 'LARNACA', '34.83584332950877', '33.60195279121399', 'DETACHED_HOUSE', 'LONG_TERM', 123456, 1);

insert into localized_property (id, version, locale, title, address, description, property_id)
  values (1, 0, 'en', 'Villa Regina', 'Farou Avenue, 7560 Perivolia, Cyprus', 'Villa Regina is a villa with an outdoor pool, set in Perivolia, 6 km from Mazotos Beach. You can fire up the barbecue for a tasty meal and enjoy the garden in fair weather. Free private parking is available on site. There is a seating area and a kitchen as well as a private bathroom. A flat-screen TV, as well as a CD player are featured. The nearest airport is Larnaca Airport, 5 km from the property. (http://www.villas.com/en-gb/cyprus/southern-cyprus/perivolia/villa-regina-perivolia.html)', 1);

insert into localized_property (id, version, locale, title, address, description, property_id)
  values (2, 0, 'ru', 'Villa Regina', 'Farou Avenue, 7560 Периволия, Кипр', 'Вилла Regina оснащена кондиционером и расположена в 1 км от центра города Периволии, в 400 метрах от пляжа Фарос и в 6 км от пляжа Мазотос. К услугам гостей бесплатный Wi-Fi и собственный бассейн. В числе прочих удобств виллы Regina гостиная с диванами, телевизором с плоским экраном, CD/DVD-плеером и консолью Wii с пультом дистанционного управления. На хорошо оборудованной кухне установлен обеденный стол. В ванной комнате гостям предоставляются фен и бесплатные туалетно-косметические принадлежности. Гости могут отдыхать в зоне для отдыха на открытом воздухе и пользоваться принадлежностями для барбекю. Расстояние до ближайшего супермаркета и ресторана составляет 1 км. В 8 км находится международный аэропорт Ларнаки. У туристов пользуются популярностью такие местные достопримечательности, как мечеть Хала-Султан-Текке (в 5,6 км) и византийский музей Святого Лазаря (в 5,9 км). В 10 метрах от виллы останавливаются автобусы. На территории обустроена бесплатная частная парковка. (http://www.villas.com/en-gb/cyprus/southern-cyprus/perivolia/villa-regina-perivolia.html)', 1);

insert into localized_property_specification (id, version, specification, localized_property_id) values
    (1, 0, 'BBQ facilities', 1),
    (2, 0, 'Outdoor pool', 1),
    (3, 0, 'Garden', 1),
    (4, 0, 'Air conditioning', 1),
    (5, 0, 'Барбекю', 2),
    (6, 0, 'Открытый плавательный бассейн', 2),
    (7, 0, 'Сад', 2),
    (8, 0, 'Кондиционер', 2);

insert into property_images (property_id, images_id) values
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4);