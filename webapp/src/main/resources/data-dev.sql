insert into gtc_user (id, version, registration_date, username, password, name, email, phone, spoken_languages) values (1, 0, 0, 'user', '$2a$10$Ngiz5wy9SG223ZIDWDyuEOg7WsS6NDbRdxViMS9DM1poZDxwSpqge', 'Denis', 'support@gotocy.com', '+357 96 740485', 'Eng, Rus');
insert into gtc_user_role (id, version, role, gtc_user_id) values (1, 0, 'ROLE_USER', 1);
insert into gtc_user_role (id, version, role, gtc_user_id) values (2, 0, 'ROLE_MASTER', 1);

-- 5 [Sale] LAR 801/1

insert into asset (id, version, `asset_key`, asset_type) values (58, 0, 'property/5/DSC_3562.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (59, 0, 'property/5/DSC_3761.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (60, 0, 'property/5/DSC_3775.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (61, 0, 'property/5/DSC_3778.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (62, 0, 'property/5/DSC_3781.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (63, 0, 'property/5/DSC_3785.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (64, 0, 'property/5/DSC_3789.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (65, 0, 'property/5/DSC_3791.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (66, 0, 'property/5/DSC_3796.jpg', 'image');

insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, offer_type, price, covered_area, plot_size, distance_to_sea, bedrooms, representative_image_id, owner_id)
  values (5, 0, 'LAR 801/1', 'Oroklini, Larnaca', 'Oroklini, Larnaca', 'LARNACA', '34.972661', '33.645909', 'APARTMENT', 'SALE', 338000, 105, 177, 1500, 3, 66, 1);

insert into property_images (property_id, images_id) values (5, 58);
insert into property_images (property_id, images_id) values (5, 59);
insert into property_images (property_id, images_id) values (5, 60);
insert into property_images (property_id, images_id) values (5, 61);
insert into property_images (property_id, images_id) values (5, 62);
insert into property_images (property_id, images_id) values (5, 63);
insert into property_images (property_id, images_id) values (5, 64);
insert into property_images (property_id, images_id) values (5, 65);
insert into property_images (property_id, images_id) values (5, 66);


insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Communal swimming pool');
insert into property_localized_fields (property_id, localized_fields_id) values (5, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Showers, toilets, wardrobe');
insert into property_localized_fields (property_id, localized_fields_id) values (5, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Kitchen fully electrical appliances');
insert into property_localized_fields (property_id, localized_fields_id) values (5, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Landscaped garden (designer)');
insert into property_localized_fields (property_id, localized_fields_id) values (5, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Automatic entrance and exit');
insert into property_localized_fields (property_id, localized_fields_id) values (5, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Fully air-conditioning');
insert into property_localized_fields (property_id, localized_fields_id) values (5, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Elevators (Lift)');
insert into property_localized_fields (property_id, localized_fields_id) values (5, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Gypsum board ceilings, lighting');
insert into property_localized_fields (property_id, localized_fields_id) values (5, select last_insert_id());


-- 6 [Sale] LAR 802

insert into asset (id, version, `asset_key`, asset_type) values (67, 0, 'property/6/DSC_3529.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (68, 0, 'property/6/DSC_3534.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (69, 0, 'property/6/DSC_3539.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (70, 0, 'property/6/DSC_3545.jpg', 'image');

insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, offer_type, price, covered_area, plot_size, distance_to_sea, bedrooms, ready_to_move_in, representative_image_id, owner_id)
  values (6, 0, 'LAR 802', 'Athenon street, Larnaca', 'Athenon street, Larnaca', 'LARNACA', '34.914313', '33.637733', 'APARTMENT', 'SALE', 572000, 78, 82, 50, 2, true, 70, 1);

insert into property_images (property_id, images_id) values (6, 67);
insert into property_images (property_id, images_id) values (6, 68);
insert into property_images (property_id, images_id) values (6, 69);
insert into property_images (property_id, images_id) values (6, 70);

insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Fully furnishing, including TV');
insert into property_localized_fields (property_id, localized_fields_id) values (6, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Kitchen fully electrical appliances');
insert into property_localized_fields (property_id, localized_fields_id) values (6, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Automatic entrance and exit');
insert into property_localized_fields (property_id, localized_fields_id) values (6, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Fully air-conditioning');
insert into property_localized_fields (property_id, localized_fields_id) values (6, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Elevators (Lift)');
insert into property_localized_fields (property_id, localized_fields_id) values (6, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Gypsum board  ceilings, lighting');
insert into property_localized_fields (property_id, localized_fields_id) values (6, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Concierge service');
insert into property_localized_fields (property_id, localized_fields_id) values (6, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Store room');
insert into property_localized_fields (property_id, localized_fields_id) values (6, select last_insert_id());

-- 9 [House] Determinis

insert into asset (id, version, `asset_key`, asset_type) values (92, 0, 'property/9/IMG_0019.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (93, 0, 'property/9/IMG_0020.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (94, 0, 'property/9/IMG_0021.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (95, 0, 'property/9/IMG_0022.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (96, 0, 'property/9/IMG_0023.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (97, 0, 'property/9/IMG_0024.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (98, 0, 'property/9/IMG_0025.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (99, 0, 'property/9/IMG_0026.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (100, 0, 'property/9/IMG_0027.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (101, 0, 'property/9/IMG_0028.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (102, 0, 'property/9/IMG_0029.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (103, 0, 'property/9/IMG_0030.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (104, 0, 'property/9/IMG_0031.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (105, 0, 'property/9/IMG_0032.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (106, 0, 'property/9/IMG_0033.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (107, 0, 'property/9/IMG_0034.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (108, 0, 'property/9/IMG_0035.jpg', 'image');

insert into asset (id, version, `asset_key`, asset_type) values (109, 0, 'property/9/pano.xml', 'pano_xml');


insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, offer_type, price, bedrooms, covered_area, heating_system, furnishing, representative_image_id, pano_xml_id, owner_id)
  values (9, 0, 'Demetris Residence', '18 Agiou Amvrosiou street Krasas area 7100 Aradippou Larnaca Cyprus', '18 Agiou Amvrosiou st, Larnaca', 'LARNACA', '34.904490', '33.587985', 'HOUSE', 'LONG_TERM', 1000, 4, 0, true, 'FULL', 93, 109, 1);

insert into property_images (property_id, images_id) values (9, 92);
insert into property_images (property_id, images_id) values (9, 93);
insert into property_images (property_id, images_id) values (9, 94);
insert into property_images (property_id, images_id) values (9, 95);
insert into property_images (property_id, images_id) values (9, 96);
insert into property_images (property_id, images_id) values (9, 97);
insert into property_images (property_id, images_id) values (9, 98);
insert into property_images (property_id, images_id) values (9, 99);
insert into property_images (property_id, images_id) values (9, 100);
insert into property_images (property_id, images_id) values (9, 101);
insert into property_images (property_id, images_id) values (9, 102);
insert into property_images (property_id, images_id) values (9, 103);
insert into property_images (property_id, images_id) values (9, 104);
insert into property_images (property_id, images_id) values (9, 105);
insert into property_images (property_id, images_id) values (9, 106);
insert into property_images (property_id, images_id) values (9, 107);
insert into property_images (property_id, images_id) values (9, 108);

insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'A wood fireplace');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'A/C in all bed rooms and kitchen');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Ceiling fans with remote control');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Alarm system');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Garage gate with remote control');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Three olive trees');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'One lemon tree');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'One mandarin tree');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'One walnut tree');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Other plants');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());

insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Дровяной камин');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Кондиционеры во всех комнатах');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Потолочные вентиляторы');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Охранная система');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Гараж с дистанц. управлением');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Три оливковых дерева');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Лимонное дерево');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Мандариновое дерево');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Ореховое дерево');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Другие растения');
insert into property_localized_fields (property_id, localized_fields_id) values (9, select last_insert_id());

-- 10 (Short term) - a short term offer for property #9

insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, offer_type, price, bedrooms, guests, covered_area, air_conditioner, distance_to_sea, representative_image_id, pano_xml_id, owner_id)
  values (10, 0, 'Demetris Residence', '18 Agiou Amvrosiou street Krasas area 7100 Aradippou Larnaca Cyprus', '18 Agiou Amvrosiou st, Larnaca', 'LARNACA', '34.904490', '33.587985', 'HOUSE', 'SHORT_TERM', 100, 4, 8, 0, true, 4500, 93, 109, 1);

insert into property_images (property_id, images_id) values (10, 92);
insert into property_images (property_id, images_id) values (10, 93);
insert into property_images (property_id, images_id) values (10, 94);
insert into property_images (property_id, images_id) values (10, 95);
insert into property_images (property_id, images_id) values (10, 96);
insert into property_images (property_id, images_id) values (10, 97);
insert into property_images (property_id, images_id) values (10, 98);
insert into property_images (property_id, images_id) values (10, 99);
insert into property_images (property_id, images_id) values (10, 100);
insert into property_images (property_id, images_id) values (10, 101);
insert into property_images (property_id, images_id) values (10, 102);
insert into property_images (property_id, images_id) values (10, 103);
insert into property_images (property_id, images_id) values (10, 104);
insert into property_images (property_id, images_id) values (10, 105);
insert into property_images (property_id, images_id) values (10, 106);
insert into property_images (property_id, images_id) values (10, 107);
insert into property_images (property_id, images_id) values (10, 108);

insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'A wood fireplace');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'A/C in all bed rooms and kitchen');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Ceiling fans with remote control');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Alarm system');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Garage gate with remote control');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Three olive trees');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'One lemon tree');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'One mandarin tree');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'One walnut tree');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Other plants');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());

insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Дровяной камин');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Кондиционеры во всех комнатах');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Потолочные вентиляторы');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Охранная система');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Гараж с дистанц. управлением');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Три оливковых дерева');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Лимонное дерево');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Мандариновое дерево');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Ореховое дерево');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Другие растения');
insert into property_localized_fields (property_id, localized_fields_id) values (10, select last_insert_id());


-- 14 Trisveis / Alex Residence #105

insert into asset (id, version, `asset_key`, asset_type) values (167, 0, 'property/14/DSC_0298.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (168, 0, 'property/14/DSC_0321.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (169, 0, 'property/14/DSC_0307.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (170, 0, 'property/14/DSC_0316.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (171, 0, 'property/14/DSC_0299.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (172, 0, 'property/14/DSC_0300.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (173, 0, 'property/14/DSC_0297.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (174, 0, 'property/14/DSC_0304.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (175, 0, 'property/14/DSC_0301.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (176, 0, 'property/14/DSC_0319.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (177, 0, 'property/14/DSC_0318.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (178, 0, 'property/14/DSC_0296.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (179, 0, 'property/14/DSC_0302.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (180, 0, 'property/14/DSC_0303.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (181, 0, 'property/14/DSC_0306.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (182, 0, 'property/14/DSC_0305.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (183, 0, 'property/14/DSC_0320.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (184, 0, 'property/14/DSC_0322.jpg', 'image');

insert into property (id, version, title, location, latitude, longitude, address, short_address, property_type, offer_type, price, covered_area, plot_size, bedrooms, guests, distance_to_sea, air_conditioner, ready_to_move_in, heating_system, furnishing, representative_image_id, pano_xml_id, owner_id)
  values (14, 0, 'Alex Residence #105', 'LARNACA', '34.920028', '33.631734', '4 Agiou Spyridonos street, 6015, Larnaca, Cyprus', '4 Agiou Spyridonos st, Larnaca', 'APARTMENT', 'SHORT_TERM', 90, 0, 0, 2, 4, 500, TRUE, FALSE, FALSE, NULL, 169, NULL, 1);

insert into localized_field (version, field_type, field_key, language, text_value) values (0, 'text', 'description', 'en', 'A brand new, never occupied, two bedroom duplex with a shared swimming pool on the roof. This beautiful flat consists two double bedrooms, fully equipped kitchen, fully furnished. Located just a few minutes from Phinikoudes (Palm trees) beach and town center. The modern design of the building gives the flat a unique/special vibe.The guests can enjoy their vacations in this quiet spot of the town center while they can enjoy the great beach, restaurants, and shops within walking distance from the duplex.A great get away for couples, families or group of friends.
Guests will be required to pay a security deposit of 100 euros upon arrival. This security deposit is to cover any costs due to any damage(s) by yourself or any member of your party. If no deductions are required, a full refund will be made upon departure from the property. Should the security deposit not be sufficient to cover any damage(s) or service charges incurred by yourself and/or your party, you will be responsible for the extra payment immediately and upon request by the owners representative.');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, text_value) values (0, 'text', 'description', 'ru', 'Совершенно новые двуспальные апартаменты с общим бассейном на крыше с потрясающим видом. Апартаменты с полным набором мебели и полностью оборудованной кухней. Находится в 10 минутах от центра города и набережной Финикудес (Пальмовая аллея). Современные технологии, использованные при строительстве, позволяют наслаждаться абсолютной тишиной жизни практически в центре города. Отличное место для проживания парой, семьей или компании друзей.
Гости при въезде должны внести 100 евро на депозит на случай непредвиденных инцидентов, связанных с порчей имущества в апартаментах. Депозит возвращается в полном объеме в день выезда (сдачи апартаментов владельцу). Если Вы или Ваши гости случайно разбили или повредили оборудование, то из внесенного депозита вычитается необходимая сумма для покрытия убытков владельца. Если ущерб превышает размер депозита, то Вам необходимо будет покрыть разницу.');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());

insert into property_images (property_id, images_id) values (14, 167);
insert into property_images (property_id, images_id) values (14, 168);
insert into property_images (property_id, images_id) values (14, 169);
insert into property_images (property_id, images_id) values (14, 170);
insert into property_images (property_id, images_id) values (14, 171);
insert into property_images (property_id, images_id) values (14, 172);
insert into property_images (property_id, images_id) values (14, 173);
insert into property_images (property_id, images_id) values (14, 174);
insert into property_images (property_id, images_id) values (14, 175);
insert into property_images (property_id, images_id) values (14, 176);
insert into property_images (property_id, images_id) values (14, 177);
insert into property_images (property_id, images_id) values (14, 178);
insert into property_images (property_id, images_id) values (14, 179);
insert into property_images (property_id, images_id) values (14, 180);
insert into property_images (property_id, images_id) values (14, 181);
insert into property_images (property_id, images_id) values (14, 182);
insert into property_images (property_id, images_id) values (14, 183);
insert into property_images (property_id, images_id) values (14, 184);

insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Cooker');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Fridge');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Freezer');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Microwave');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Toaster');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Kettle');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Washing machine');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Iron');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Local Guides');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'TV');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Satellite TV');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Wi-Fi available');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Hair dryer');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Ironing board');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Air conditioning');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Linen provided');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Towels provided');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Housekeeping');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Shared outdoor pool');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Mountain Views');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Solarium or roof terrace');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Balcony or terrace');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Sea view');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());

insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Плита');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Холодильник');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Морозильная камера');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Микроволновка');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Тостер');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Чайник');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Стиральная машина');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Утюг');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Телевизор');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Спутниковое ТВ');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Wi-Fi');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Фен');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Гладильная доска');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Кондиционер');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Постельное белье');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Полотенца');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Уборка дома');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Общий бассейн');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Вид на горы');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Место для загара');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Терраса');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Вид на море');
insert into property_localized_fields (property_id, localized_fields_id) values (14, select last_insert_id());



-- Denis House

insert into asset (id, version, `asset_key`, asset_type) values (297, 0, 'property/20/IMG_0526.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (298, 0, 'property/20/IMG_0523.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (299, 0, 'property/20/IMG_0524.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (300, 0, 'property/20/IMG_0525.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (301, 0, 'property/20/IMG_0522.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (302, 0, 'property/20/IMG_0527.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (303, 0, 'property/20/IMG_0528.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (304, 0, 'property/20/IMG_0529.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (305, 0, 'property/20/IMG_0530.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (306, 0, 'property/20/IMG_0531.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (307, 0, 'property/20/IMG_0532.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (308, 0, 'property/20/IMG_0534.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (309, 0, 'property/20/pano.xml', 'pano_xml');

insert into property (id, version, title, location, latitude, longitude, address, short_address, property_type, offer_type, price, covered_area, plot_size, bedrooms, guests, distance_to_sea, air_conditioner, ready_to_move_in, heating_system, furnishing, representative_image_id, pano_xml_id, owner_id)
  values (20, 0, 'Dimitraki Koumantari Residence', 'LARNACA', '34.940275', '33.590204', 'Demetri Koumandari Str. No.1, 7103, Aradippou, Larnaca', 'Demetri Koumandari Str.1, Larnaca', 'HOUSE', 'LONG_TERM', 850, 0, 0, 4, 10, 4700, TRUE, TRUE, TRUE, 'FULL', 300, 309, 1);


insert into property_images (property_id, images_id) values (20, 297);
insert into property_images (property_id, images_id) values (20, 298);
insert into property_images (property_id, images_id) values (20, 299);
insert into property_images (property_id, images_id) values (20, 300);
insert into property_images (property_id, images_id) values (20, 301);
insert into property_images (property_id, images_id) values (20, 302);
insert into property_images (property_id, images_id) values (20, 303);
insert into property_images (property_id, images_id) values (20, 304);
insert into property_images (property_id, images_id) values (20, 305);
insert into property_images (property_id, images_id) values (20, 306);
insert into property_images (property_id, images_id) values (20, 307);
insert into property_images (property_id, images_id) values (20, 308);

insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Cooker');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Fridge');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Freezer');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Microwave');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Kettle');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Washing machine');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Iron');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'TV');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Wi-Fi available');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Ironing board');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Air conditioning');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Ceiling Fans');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Mountain view');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Balcony');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Parking');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Private garden');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'en', 'Outdoor dining');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());

insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Плита');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Холодильник');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Морозильная камера');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Микроволновка');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Чайник');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Посудомоечная машина');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Утюг');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Телевизор');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Wi-Fi');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Гладильная доска');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Кондиционер');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Потолочные вентиляторы');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Вид на горы');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Балкон');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Стоянка');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Сад');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'feature', 'ru', 'Беседка');
insert into property_localized_fields (property_id, localized_fields_id) values (20, select last_insert_id());


-- Offers status update

update property set offer_status = 'RENTED' where id = 20;
update property set offer_status = 'SOLD' where id = 5;


-- Complexes

insert into asset (id, version, `asset_key`, asset_type) values (471, 0, 'property/25/DSC_3535.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (472, 0, 'property/25/DSCN1326.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (473, 0, 'property/25/DSCN1351.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (474, 0, 'property/25/DSCN1357.jpg', 'image');
insert into asset (id, version, `asset_key`, asset_type) values (475, 0, 'property/25/DSCN1359.jpg', 'image');

insert into asset (id, version, `asset_key`, asset_type) values (476, 0, 'property/25/DSCN1359.jpg', 'pdf_file');
insert into asset (id, version, `asset_key`, asset_type) values (477, 0, 'property/25/DSCN1359.jpg', 'pdf_file');

insert into complex (id, version, title, location, address, coordinates, representative_image_id, contacts_email)
  values (1, 0, 'Mesoyios Park Residences', 'LARNACA', 'Larnaca Dhekelia', '[{"lat":34.983005, "lng":33.729343},{"lat":34.983919, "lng":33.729756},{"lat":34.983845, "lng":33.730158},{"lat":34.983651, "lng":33.730561},{"lat":34.983137, "lng":33.730979},{"lat":34.982838, "lng":33.729616}]', 471, 'support@gotocy.com');

insert into complex_images (complex_id, images_id) values (1, 471);
insert into complex_images (complex_id, images_id) values (1, 472);
insert into complex_images (complex_id, images_id) values (1, 473);
insert into complex_images (complex_id, images_id) values (1, 474);
insert into complex_images (complex_id, images_id) values (1, 475);

insert into complex_pdf_files (complex_id, pdf_files_id) values (1, 476);
insert into complex_pdf_files (complex_id, pdf_files_id) values (1, 477);

insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'specification', 'en', 'Multiple modern architectural designs to choose from');
insert into complex_localized_fields (complex_id, localized_fields_id) values (1, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'specification', 'en', 'A selection of three and four bedroom , three bathroom houses');
insert into complex_localized_fields (complex_id, localized_fields_id) values (1, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'specification', 'en', 'Provision for fireplace');
insert into complex_localized_fields (complex_id, localized_fields_id) values (1, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'specification', 'en', 'Allocated space for W/M and dryer on first floor');
insert into complex_localized_fields (complex_id, localized_fields_id) values (1, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'specification', 'en', 'Optional private pools');
insert into complex_localized_fields (complex_id, localized_fields_id) values (1, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, string_value) values (0, 'string', 'specification', 'en', 'Covered garage');
insert into complex_localized_fields (complex_id, localized_fields_id) values (1, select last_insert_id());
insert into localized_field (version, field_type, field_key, language, text_value) values (0, 'text', 'description', 'en', 'Not more than 5 minutes drive from Larnaca town centre, Mesoyios Park Residences is located in an established residential area, 200m from the beach. The project is composed of 23 residential plots, an area which has been identified for high growth potential and future development gains.
The position provides privacy and accessibility to town and country with independent modern-designed villas comprising three bedrooms, laundry, gardens and private optional pool. These Residences come with the option of five designs, internal layouts and external finish. Great luxury options are featured comprising open plan living areas which can be modified to suit the purchases needs, kitchen/dining area, guest toilet, pantry and storage area, master bedroom with en-suite and main bathroom. Set amidst easy-care gardens with paved flooring bordering theoptional pool, covered garage, and verandas.
Mesoyios Residences uses a unique combination of glass, stone andtimber to appeal to the new home buyer.');
insert into complex_localized_fields (complex_id, localized_fields_id) values (1, select last_insert_id());

update property set complex_id = 1 where id in (9, 20);

insert into developer (id, version, developer_name) values (1, 0, 'Giovani Developers');

insert into localized_field (version, field_type, field_key, language, text_value) values (0, 'text', 'description', 'en', 'Giovani Developers history has been built upon the philosophies of its founder, Christakis Giovanis, who successfully developed a construction company into the biggest Cypriot property development company in the south-east of Cyprus. Giovani Developers build a wide range of luxury properties, mainly on the South East Coast of Cyprus in the areas of Avgorou, Ayia Napa, Ayia Thekla, Derynia, Frenaros, Oroklini, Pyla, Paralimni, Protaras, Pyla, Sotira, and Vrysoulles.
With a commitment to building excellence and quality of construction, the company was established in 1986 and, since then, its played a major role in the development of property in Cyprus. Nowadays, the company is widely recognized as one of the leaders in the Cyprus Property sector, due not only to its volume of building, but also due to the high standards of Quality and Customer Service that it provides to the market. Its operation techniques have been also recognized several times by quality associations and committees. As a result, Giovani Developers is the only company that complies fully with the International Standards of NQA ISO; something that has established its presence in the Cyprus Property market as a most reliable company.
Giovani Developers has a reputable portfolio, with 2000 individual clients to date. Since the company has established various procedures to ease everything from the purchase process up to the delivery process, clients have found that the stress from buying a property has been largely eliminated. Giovani Developers delivers approximately 300 properties on average, based on the sales record and on the companys development strategy for 2003 up to 2010.
Initially, the companys primary business was to serve the British Sovereign bases in Dekhelia back in the early 80s. Having been in the developing industry for more that 20 years, Giovani Developers holds a Grade A Construction license and it is classified among the elite of Cypriot contractors. Over the years, while the Group has significantly expanded, it still exclusively holds this contract. Giovani Developers has establish strong relationships with its clients, and due to this trust, Giovani Developers has a 14.4% total market share in the South East Coast. The nearest rival follows with 7.8%.
The Group will continue to provide quality services, and will take pride in delivering to clients the chance to experience the lifestyle of their dreams on the Island of Cyprus.');
insert into developer_localized_fields (developer_id, localized_fields_id) values (1, select last_insert_id());

update property set developer_id = 1 where id in (5, 9, 20);
update complex set developer_id = 1 where id = 1;

