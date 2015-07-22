insert into owner (id, version, name, email, phone, spoken_languages)
  values (1, 0, 'Denis', 'support@gotocy.eu', '+357 96 740485', 'Eng, Rus');
insert into owner (id, version, name, email, phone, spoken_languages)
  values (2, 0, 'Demetris', '', '+357 99483998', 'Greek, Eng, Rus');
insert into owner (id, version, name, email, phone, spoken_languages)
  values (3, 0, 'Alexandros Trisveis', 'alextrisvei@hotmail.com', '+357 99627112', 'Greek, Eng');

-- 1 [Short term] http://www.housetrip.com/en/rentals/63167?destination_id=160317&destination_name=Greece&guests=2&origin=search&search_result_position=3&source_element=card&source_type=list

insert into asset (id, version, key, asset_type) values (1, 0, 'property/1/1.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (2, 0, 'property/1/2.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (3, 0, 'property/1/3.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (4, 0, 'property/1/4.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (5, 0, 'property/1/5.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (6, 0, 'property/1/6.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (7, 0, 'property/1/7.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (8, 0, 'property/1/8.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (9, 0, 'property/1/9.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (10, 0, 'property/1/10.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (11, 0, 'property/1/11.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (12, 0, 'property/1/12.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (13, 0, 'property/1/13.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (14, 0, 'property/1/14.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (15, 0, 'property/1/18.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (16, 0, 'property/1/19.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (17, 0, 'property/1/20.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (18, 0, 'property/1/21.jpg', 'IMAGE');

insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, property_status, price, bedrooms, guests, baths, air_conditioner, distance_to_sea, representative_image_id, owner_id)
  values (1, 0, 'Sofia', 'Chania, Crete, Nomós Chaníon', 'Chania, Crete, Nomós Chaníon', 'GREECE', '35.318198', '24.318744', 'APARTMENT', 'SHORT_TERM', 95, 1, 3, 1, true, 1200, 1, 1);

insert into localized_property (id, version, locale, description, property_id)
  values (1, 0, 'en', 'Kastellos village is situated in the picturesque village of Kastellos in Georgioupolis, Chania. The complex is ideal for those wishing to escape everyday routine and relax enjoying the Cretan countryside; it’s for those who want to contact the nature, the wild life and of course to meet the people and their habits.
"Sofia Apartment" independent and fully equipped. It is built according to the traditional architecture. On the outside, beautiful stone work frames the dark green doorways and windows, further accentuated by the pastel strawberry and lemon walls. Add to this lovely tiled roofs and backdrop of the blue sky and mountains, and then you begin to understand why we were so smitten.
Inside the restoration is if anything, even more remarkable, with beautifully tiled floors, ceilings supported by huge beams interlaced with traditional bamboo linings and pale ochre coloured walls set off by elegant concealed lighting.
"Sofia Apartment" has two floors and a private veranda with panoramic view of the mountain and the sea. Is has a fully equipped kitchen (with kitchen appliances and utensils), bathroom with bathtub, WC with shower and one or two bedrooms with king size beds. The house combines harmoniously the traditional style with modern decorative elements, creating a warm and cosy atmosphere. For your convenience, you can find A/C, fireplace and plasma TV and can accommodate 2 to 6 persons.

KASTELLOS VILLAGE is built in a privileged location. During your stay here you can make a tour around the area and enjoy nature.
It is worth visiting the famous Kourna lake, which is only 5 km from the village. You can also visit the area of Argiroupolis, known for the running and spring waters. You will have a meal under the plane trees and visit the small church of Saint Prokopios, which is built in a cave.
The popular beach of Georgioupolis (5 km from the complex) is a pole of attraction for the visitors of the area.
There are many restaurants and taverns in the village, where you can enjoy the famous recipes of the traditional Cretan cuisine.
Semi-mountainous village built on the feet of the White Mountains on an altitude of 252m. Its named derived from the site it was built (like a castle). Originally the village was situated lower on the valley in the present area of the settlement Agathes from where the inhabitants were forced to leave in 1800 due to the Ottomans. Close to the village a there was in 1835 a severe battle amongst Cretans and Ottomans that left 22 dead Ottomans and just 1 Cretan. It was the reason that the ottomans stopped raiding the area. The locals had an important contribution to the resistance against the Germans fascists during the 2sn World War.'
  , 1);

insert into localized_property (id, version, locale, description, property_id)
  values (101, 0, 'ru', '', 1);

insert into property_images (property_id, images_id) values (1, 1);
insert into property_images (property_id, images_id) values (1, 2);
insert into property_images (property_id, images_id) values (1, 3);
insert into property_images (property_id, images_id) values (1, 4);
insert into property_images (property_id, images_id) values (1, 5);
insert into property_images (property_id, images_id) values (1, 6);
insert into property_images (property_id, images_id) values (1, 7);
insert into property_images (property_id, images_id) values (1, 8);
insert into property_images (property_id, images_id) values (1, 9);
insert into property_images (property_id, images_id) values (1, 10);
insert into property_images (property_id, images_id) values (1, 11);
insert into property_images (property_id, images_id) values (1, 12);
insert into property_images (property_id, images_id) values (1, 13);
insert into property_images (property_id, images_id) values (1, 14);
insert into property_images (property_id, images_id) values (1, 15);
insert into property_images (property_id, images_id) values (1, 16);
insert into property_images (property_id, images_id) values (1, 17);
insert into property_images (property_id, images_id) values (1, 18);


--insert into localized_property_specification (id, version, specification, localized_property_id) values (1, 0, 'Bedroom (2 Guests - 1 double bed)', 1);
--insert into localized_property_specification (id, version, specification, localized_property_id) values (2, 0, 'Living Room (1 Guest - 1 sofa bed / futon)', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (3, 0, 'Air conditioning', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (4, 0, 'Children friendly, Smoking', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (5, 0, 'Board games, Internet (Wi-Fi), Radio, TV', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (6, 0, 'Balcony', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (7, 0, 'Barbecue', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (8, 0, 'Garden', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (9, 0, 'Parking / Garage', 1);

-- 2 [Short term] http://www.housetrip.com/en/rentals/61781?destination_id=160317&destination_name=Greece&guests=2&origin=search&search_result_position=31&source_element=card&source_type=list

insert into asset (id, version, key, asset_type) values (19, 0, 'property/2/1.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (20, 0, 'property/2/2.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (21, 0, 'property/2/3.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (22, 0, 'property/2/4.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (23, 0, 'property/2/5.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (24, 0, 'property/2/6.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (25, 0, 'property/2/7.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (26, 0, 'property/2/8.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (27, 0, 'property/2/9.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (28, 0, 'property/2/10.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (29, 0, 'property/2/12.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (30, 0, 'property/2/13.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (31, 0, 'property/2/14.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (32, 0, 'property/2/15.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (33, 0, 'property/2/16.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (34, 0, 'property/2/17.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (35, 0, 'property/2/19.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (36, 0, 'property/2/20.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (37, 0, 'property/2/21.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (38, 0, 'property/2/22.jpg', 'IMAGE');


insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, property_status, price, bedrooms, guests, baths, air_conditioner, distance_to_sea, representative_image_id, owner_id)
  values (2, 0, 'Anezina', 'Chania, Crete, Nomós Chaníon', 'Chania, Crete, Nomós Chaníon', 'GREECE', '35.31242', '24.310504', 'HOUSE', 'SHORT_TERM', 118, 2, 6, 2, true, 120, 19, 1);

insert into localized_property (id, version, locale, description, property_id)
  values (2, 0, 'en', 'Kastellos village is situated in the picturesque village of Kastellos in Georgioupolis, Chania. The complex is ideal for those wishing to escape everyday routine and relax enjoying the Cretan countryside; it’s for those who want to contact the nature, the wild life and of course to meet the people and their habits.
"Anezina House" independent and fully equipped. It is built according to the traditional architecture. On the outside, beautiful stone work frames the dark green doorways and windows, further accentuated by the pastel strawberry and lemon walls. Add to this lovely tiled roofs and backdrop of the blue sky and mountains, and then you begin to understand why we were so smitten.
Inside the restoration is if anything, even more remarkable, with beautifully tiled floors, ceilings supported by huge beams interlaced with traditional bamboo linings and pale ochre coloured walls set off by elegant concealed lighting.
"Anezina House" has two floors and a private veranda with panoramic view of the mountain and the sea. Is has a fully equipped kitchen (with kitchen appliances and utensils), bathroom with bathtub, WC with shower and one or two bedrooms with king size beds. The house combines harmoniously the traditional style with modern decorative elements, creating a warm and cosy atmosphere. For your convenience, you can find A/C, fireplace and plasma TV and can accommodate 2 to 6 persons.

KASTELLOS VILLAGE is built in a privileged location. During your stay here you can make a tour around the area and enjoy nature.
It is worth visiting the famous Kourna lake, which is only 5 km from the village. You can also visit the area of Argiroupolis, known for the running and spring waters. You will have a meal under the plane trees and visit the small church of Saint Prokopios, which is built in a cave.
The popular beach of Georgioupolis (5 km from the complex) is a pole of attraction for the visitors of the area.
There are many restaurants and taverns in the village, where you can enjoy the famous recipes of the traditional Cretan cuisine.
Semi-mountainous village built on the feet of the White Mountains on an altitude of 252m. Its named derived from the site it was built (like a castle). Originally the village was situated lower on the valley in the present area of the settlement Agathes from where the inhabitants were forced to leave in 1800 due to the Ottomans. Close to the village a there was in 1835 a severe battle amongst Cretans and Ottomans that left 22 dead Ottomans and just 1 Cretan. It was the reason that the ottomans stopped raiding the area. The locals had an important contribution to the resistance against the Germans fascists during the 2sn World War.'
  , 2);

insert into localized_property (id, version, locale, description, property_id)
  values (202, 0, 'ru', '', 2);

insert into property_images (property_id, images_id) values (2, 19);
insert into property_images (property_id, images_id) values (2, 20);
insert into property_images (property_id, images_id) values (2, 21);
insert into property_images (property_id, images_id) values (2, 22);
insert into property_images (property_id, images_id) values (2, 23);
insert into property_images (property_id, images_id) values (2, 24);
insert into property_images (property_id, images_id) values (2, 25);
insert into property_images (property_id, images_id) values (2, 26);
insert into property_images (property_id, images_id) values (2, 27);
insert into property_images (property_id, images_id) values (2, 28);
insert into property_images (property_id, images_id) values (2, 29);
insert into property_images (property_id, images_id) values (2, 30);
insert into property_images (property_id, images_id) values (2, 31);
insert into property_images (property_id, images_id) values (2, 32);
insert into property_images (property_id, images_id) values (2, 33);
insert into property_images (property_id, images_id) values (2, 34);
insert into property_images (property_id, images_id) values (2, 35);
insert into property_images (property_id, images_id) values (2, 36);
insert into property_images (property_id, images_id) values (2, 37);
insert into property_images (property_id, images_id) values (2, 38);


--insert into localized_property_specification (id, version, specification, localized_property_id) values (10, 0, 'Bedroom (2 Guests - 1 double bed)', 2);
--insert into localized_property_specification (id, version, specification, localized_property_id) values (11, 0, 'Bedroom (2 Guests - 1 double bed)', 2);
--insert into localized_property_specification (id, version, specification, localized_property_id) values (12, 0, 'Living Room (2 Guests - 2 sofa beds / futons)', 2);
--insert into localized_property_specification (id, version, specification, localized_property_id) values (14, 0, 'Board games, Internet (Wi-Fi), Radio, TV, Telephone', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (13, 0, 'Air conditioning', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (15, 0, 'Children friendly, Smoking', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (16, 0, 'Balcony', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (17, 0, 'Barbecue', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (18, 0, 'Garden', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (19, 0, 'Parking / Garage', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (20, 0, 'Swimming Pool', 2);

-- 3 [Short term] http://www.housetrip.com/en/rentals/206592?destination_id=160317&destination_name=Greece&guests=2&origin=search&search_result_position=28&source_element=card&source_type=list

insert into asset (id, version, key, asset_type) values (39, 0, 'property/3/1.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (40, 0, 'property/3/2.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (41, 0, 'property/3/3.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (42, 0, 'property/3/4.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (43, 0, 'property/3/5.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (44, 0, 'property/3/6.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (45, 0, 'property/3/7.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (46, 0, 'property/3/8.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (47, 0, 'property/3/9.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (48, 0, 'property/3/10.jpg', 'IMAGE');


insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, property_status, price, bedrooms, guests, baths, air_conditioner, distance_to_sea, representative_image_id, owner_id)
  values (3, 0, 'Green Paradise Luxury Villa', 'Rethymno, Crete, Nomós Rethýmnis', 'Rethymno, Crete, Nomós Rethýmnis', 'GREECE', '35.345041', '24.689299', 'HOUSE', 'SHORT_TERM', 141, 3, 7, 2, false, 150, 45, 1);

insert into localized_property (id, version, locale, description, property_id)
  values (3, 0, 'en', 'Built in a quiet hamlet of the village Margarites, the Tzannakiana villa ‘Green Paradise’ is a country retreat, stone-built with a distinctive blend of modern and traditional. In an area of natural beauty, surrounded by a garden of flowers, herbs and trees. This is a luxurious villa to rent for your perfect holiday.

The villa is on the outskirts of the hamlet and so has stunning views of the Mediterranean Sea, the surrounding lush countryside and mountains. Quiet and private but not isolated, just 0.6km from the centre of the picturesque village of Margarites where there are traditional tavernas, cafes and a super-market. The village of Margarites also has a renowned pottery where visitors have the opportunity to watch the ceramic pottery being made.

Green Paradise Villa is only 20 km from Rethymno, 72km from Herkalion airport and 95km from Chania airport. The villa is also in the vicinity of several places of historical interest such as Arkadi (16km), ancient Eleftherna (5km) and a little bit further afield the famous archaeological site of Knossos (72km). And conveniently, the villa is just 13km (approx: 10-15 minute drive) from the beautiful, sandy beaches of Panormo. In the large village of Panormo you will find more amenities.

This delightful two-storey villa is 160 sq.m, with beautifully tiled floors and high, wooden beam ceilings. It features a lovely well-kept garden and terrace with a 20 sq.m pool with sunbeds and umbrellas, barbecue facilities and parking area. It can accommodate 6 people in three elegantly furnishing bedrooms, of which two have double beds and the third has two single beds. Furthermore, it is possible for more persons to be accommodated. The very comfortable living room sofa easily converts into a bed and a cot/crib for a baby/infant is available that can be used in any of the rooms. In addition all rooms feature air conditioning, satellite T.V. and internet access.

On the ground floor there is a patio of 35 sq.m with steel table and chairs, ideal for dining in the garden. The comfortable sitting room is fully furnishing and has satellite T.V., CD player and internet access. The modern fully equipped kitchen has all essential appliances such as electric cooker/oven, fridge/freezer, dish-washer, microwave, kettle, coffee maker, toaster, juicer etc. and dining table and chairs. There is a large, beautifully designed 17 sq.m bathroom with jacuzzi/bathtub, double wash-hand basins and toilet. Off the bathroom, there is a separate storage room for laundry with a washing machine. Also on the ground floor is one of the double bedrooms.

On the first floor there is a 35 sq.m balcony with a comfortable, wicker suite and coffee table where you can sit back, relax and overlook the breath-taking views of the Mediterranean Sea and Cretan countryside. There is a shower-room with wash-hand basin and toilet. The other double bedroom is also on this floor along with the bedroom with the two single beds.

Throughout, the interior has been furnishing with stylish, modern wooden furniture and elegant fabrics, with some modern and traditional touches, all in perfect harmony. Plus, there is ample storage space in all of the rooms.

Due to the location of the village and the topography of the area, there are many beautiful hiking trails, such as E4 starting from the village of Margarites and going up to Eleftherna. Green Paradise Villa offers visitors (on request) organised excursions in the countryside (at weekends) enabling them to explore the archaeological site of ancient Eleftherna, the caves of Melidoni and Zoniana all located within walking distance.

Your stay, here at Green Paradise Luxury Villa, is sure to be heavenly!

Green Paradise Villa is approved by Greek Tourism Organisation with license number: 1041K91002955601'
  , 3);

insert into localized_property (id, version, locale, description, property_id)
  values (303, 0, 'ru', '', 3);

insert into property_images (property_id, images_id) values (3, 39);
insert into property_images (property_id, images_id) values (3, 40);
insert into property_images (property_id, images_id) values (3, 41);
insert into property_images (property_id, images_id) values (3, 42);
insert into property_images (property_id, images_id) values (3, 43);
insert into property_images (property_id, images_id) values (3, 44);
insert into property_images (property_id, images_id) values (3, 45);
insert into property_images (property_id, images_id) values (3, 46);
insert into property_images (property_id, images_id) values (3, 47);
insert into property_images (property_id, images_id) values (3, 48);


--insert into localized_property_specification (id, version, specification, localized_property_id) values (21, 0, 'Bedroom (2 Guests - 1 double bed)', 3);
--insert into localized_property_specification (id, version, specification, localized_property_id) values (22, 0, 'Bedroom (2 Guests - 1 double bed)', 3);
--insert into localized_property_specification (id, version, specification, localized_property_id) values (23, 0, 'Bedroom (2 Guests - 2 single beds)', 3);
--insert into localized_property_specification (id, version, specification, localized_property_id) values (24, 0, 'Living area (1 Guest - 1 sofa bed / futon)', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (25, 0, 'Balcony', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (26, 0, 'Barbecue', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (27, 0, 'Childrens Games', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (28, 0, 'Garden', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (29, 0, 'Parking / Garage', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (30, 0, 'Swimming Pool', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (31, 0, 'Terrace', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (32, 0, 'Air conditioning', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (33, 0, 'Safe', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (34, 0, 'Washing machine', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (35, 0, 'Children friendly, Pets', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (36, 0, 'Board games', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (37, 0, 'Internet (Wi-Fi)', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (38, 0, 'Internet (cable)', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (39, 0, 'TV', 3);

-- 4 [Short term] http://www.housetrip.com/en/rentals/296121?destination_id=160317&destination_name=Greece&guests=2&origin=search&search_result_position=17&source_element=card&source_type=list

-- insert into asset (id, version, key, asset_type) values (49, 0, 'property/4/1.jpg', 'IMAGE');
-- insert into asset (id, version, key, asset_type) values (50, 0, 'property/4/2.jpg', 'IMAGE');
-- insert into asset (id, version, key, asset_type) values (51, 0, 'property/4/3.jpg', 'IMAGE');
-- insert into asset (id, version, key, asset_type) values (52, 0, 'property/4/4.jpg', 'IMAGE');
-- insert into asset (id, version, key, asset_type) values (53, 0, 'property/4/5.jpg', 'IMAGE');
-- insert into asset (id, version, key, asset_type) values (54, 0, 'property/4/6.jpg', 'IMAGE');
-- insert into asset (id, version, key, asset_type) values (55, 0, 'property/4/7.jpg', 'IMAGE');
-- insert into asset (id, version, key, asset_type) values (56, 0, 'property/4/8.jpg', 'IMAGE');
-- insert into asset (id, version, key, asset_type) values (57, 0, 'property/4/9.jpg', 'IMAGE');
--
--
-- insert into property (id, version, location, latitude, longitude, property_type, property_status, price, bedrooms, guests, baths, air_conditioner, distance_to_sea, representative_image_id, owner_id)
--   values (4, 0, 'GREECE', '36.295279', '28.155108', 'HOUSE', 'SHORT_TERM', 182, 4, 8, 4, true, 250, 49, 1);
--
-- insert into localized_property (id, version, locale, title, address, description, property_id)
--   values (4, 0, 'en', 'Villa Chrysa', 'Rhodes town, Rhodes, Dodecanese', 'Its located in St. Luke Afandou on the eastern side of the island . It is about 20 km away from the city of Rhodes and from Rhodes Airport , 3 km from Afandou beach -with panoramic view towards this beach- it is also 2 km away from Afandou Golf Course , 6 km from the tourist resort Faliraki of Rhodes with the known beach , 6 km from the tourist resort of Kolymbia Rhodes and the famous Tsampika beach .
-- Also , its location is such that it is easily accessible to other tourist resorts/sightseeings of the island such as Lindos which is 30km away , Seven Springs - 7km, Butterflies - 15k , -and Filerimos is 20km away, the above are just a portion of the accessible options close and relatively close to its location, there are many more. It was built in 2010 and it will be the first year that will be used by visitors .The town in which is located, Afandou, has population of approximately 7000 inhabitants, it is easily accessible , and it takes just 15 minutes to get to the city because of the existence of the highway. Except from golf and the known beach with length over 7 thousand km , in the area you can find Super Market, Banks , Police Station, tavernas , cafes , bars and hotel complexes .
-- The house is located on a hill above the city Afandou with boundless view. It has a private driveway along with 4 other houses in the area , it was built on a plot of 13,000 sq.m. and it has ground floor. The total area of ​the 1st floor is ​150sq.m. On the ground floor there are kitchen , living room , dining room, storage room and WC, and on the first floor there are three bedrooms and two bathrooms . It also features a guest house made of stone, in the courtyard area. Its dimensions are 40sqm, and includes kitchen, bathroom, air-condition and other amenities. Overall the house, along with the guest house, has the possibility of hosting at least 8 people. Besides the swimming pool and Spa, it also has barbecue, oven (wood-fired oven), garden etc.
-- It is available all year round.
--
-- Note that the charge of the main villa for months July and August is 50 euro extra.
-- Also, the charge of guest house is 50 euro.'
--   , 4);
--
-- insert into localized_property (id, version, locale, title, address, description, property_id)
--   values (404, 0, 'ru', 'Villa Chrysa', 'Rhodes town, Rhodes, Dodecanese', '', 4);
--
-- insert into property_images (property_id, images_id) values (4, 49);
-- insert into property_images (property_id, images_id) values (4, 50);
-- insert into property_images (property_id, images_id) values (4, 51);
-- insert into property_images (property_id, images_id) values (4, 52);
-- insert into property_images (property_id, images_id) values (4, 53);
-- insert into property_images (property_id, images_id) values (4, 54);
-- insert into property_images (property_id, images_id) values (4, 55);
-- insert into property_images (property_id, images_id) values (4, 56);
-- insert into property_images (property_id, images_id) values (4, 57);


--insert into localized_property_specification (id, version, specification, localized_property_id) values (40, 0, 'Bedroom (2 Guests - 1 double bed)', 4);
--insert into localized_property_specification (id, version, specification, localized_property_id) values (41, 0, 'Bedroom (2 Guests - 1 double bed)', 4);
--insert into localized_property_specification (id, version, specification, localized_property_id) values (42, 0, 'Bedroom (1 Guest - 1 single bed)', 4);
--insert into localized_property_specification (id, version, specification, localized_property_id) values (43, 0, 'Bedroom (3 Guests - 1 double bed, 1 single bed)', 4);
--insert into localized_property_specification (id, version, specification, localized_property_id) values (45, 0, 'Hair Dryer, Shower, Soap and Shampoo, Towels', 4);
-- insert into localized_property_specification (id, version, specification, localized_property_id) values (44, 0, 'Children friendly, Pets, Smoking', 4);
-- insert into localized_property_specification (id, version, specification, localized_property_id) values (46, 0, 'Air conditioning', 4);
-- insert into localized_property_specification (id, version, specification, localized_property_id) values (47, 0, 'Balcony', 4);
-- insert into localized_property_specification (id, version, specification, localized_property_id) values (48, 0, 'Barbecue', 4);
-- insert into localized_property_specification (id, version, specification, localized_property_id) values (49, 0, 'Garden', 4);
-- insert into localized_property_specification (id, version, specification, localized_property_id) values (50, 0, 'Parking / Garage', 4);
-- insert into localized_property_specification (id, version, specification, localized_property_id) values (51, 0, 'Swimming Pool', 4);

-- 5 [Sale] LAR 801/1

insert into asset (id, version, key, asset_type) values (58, 0, 'property/5/DSC_3562.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (59, 0, 'property/5/DSC_3761.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (60, 0, 'property/5/DSC_3775.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (61, 0, 'property/5/DSC_3778.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (62, 0, 'property/5/DSC_3781.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (63, 0, 'property/5/DSC_3785.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (64, 0, 'property/5/DSC_3789.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (65, 0, 'property/5/DSC_3791.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (66, 0, 'property/5/DSC_3796.jpg', 'IMAGE');


insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, property_status, price, covered_area, plot_size, distance_to_sea, bedrooms, baths, representative_image_id, owner_id)
  values (5, 0, 'LAR 801/1', 'Oroklini, Larnaca', 'Oroklini, Larnaca', 'LARNACA', '34.972661', '33.645909', 'APARTMENT', 'SALE', 338000, 105, 177, 1500, 3, 1, 66, 1);

insert into localized_property (id, version, locale, description, property_id)
  values (5, 0, 'en', '', 5);

insert into localized_property (id, version, locale, description, property_id)
  values (105, 0, 'ru', '', 5);


insert into property_images (property_id, images_id) values (5, 58);
insert into property_images (property_id, images_id) values (5, 59);
insert into property_images (property_id, images_id) values (5, 60);
insert into property_images (property_id, images_id) values (5, 61);
insert into property_images (property_id, images_id) values (5, 62);
insert into property_images (property_id, images_id) values (5, 63);
insert into property_images (property_id, images_id) values (5, 64);
insert into property_images (property_id, images_id) values (5, 65);
insert into property_images (property_id, images_id) values (5, 66);


insert into localized_property_specification (id, version, specification, localized_property_id) values (52, 0, 'Communal swimming pool', 5);
insert into localized_property_specification (id, version, specification, localized_property_id) values (53, 0, 'Showers, toilets, wardrobe', 5);
insert into localized_property_specification (id, version, specification, localized_property_id) values (54, 0, 'Kitchen fully electrical appliances', 5);
insert into localized_property_specification (id, version, specification, localized_property_id) values (55, 0, 'Landscaped garden (designer)', 5);
insert into localized_property_specification (id, version, specification, localized_property_id) values (56, 0, 'Automatic entrance and exit', 5);
insert into localized_property_specification (id, version, specification, localized_property_id) values (57, 0, 'Fully air-conditioning', 5);
insert into localized_property_specification (id, version, specification, localized_property_id) values (58, 0, 'Elevators (Lift)', 5);
insert into localized_property_specification (id, version, specification, localized_property_id) values (59, 0, 'Gypsum board ceilings, lighting', 5);


-- 6 [Sale] LAR 802

insert into asset (id, version, key, asset_type) values (67, 0, 'property/6/DSC_3529.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (68, 0, 'property/6/DSC_3534.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (69, 0, 'property/6/DSC_3539.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (70, 0, 'property/6/DSC_3545.jpg', 'IMAGE');


insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, property_status, price, covered_area, plot_size, distance_to_sea, bedrooms, baths, ready_to_move_in, representative_image_id, owner_id)
  values (6, 0, 'LAR 802', 'Athenon street, Larnaca', 'Athenon street, Larnaca', 'LARNACA', '34.914313', '33.637733', 'APARTMENT', 'SALE', 572000, 78, 82, 50, 2, 1, true, 70, 1);

insert into localized_property (id, version, locale, description, property_id)
  values (6, 0, 'en', '', 6);

insert into localized_property (id, version, locale, description, property_id)
  values (106, 0, 'ru', '', 6);



insert into property_images (property_id, images_id) values (6, 67);
insert into property_images (property_id, images_id) values (6, 68);
insert into property_images (property_id, images_id) values (6, 69);
insert into property_images (property_id, images_id) values (6, 70);


insert into localized_property_specification (id, version, specification, localized_property_id) values (60, 0, 'Fully furnishing, including TV', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (61, 0, 'Kitchen fully electrical appliances', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (62, 0, 'Automatic entrance and exit', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (63, 0, 'Fully air-conditioning', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (64, 0, 'Elevators (Lift)', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (65, 0, 'Gypsum board  ceilings, lighting', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (66, 0, 'Concierge service', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (67, 0, 'Store room', 6);


-- 7 [Long term] http://www.greece-property.net/property/4003/

insert into asset (id, version, key, asset_type) values (71, 0, 'property/7/1.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (72, 0, 'property/7/2.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (73, 0, 'property/7/3.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (74, 0, 'property/7/4.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (75, 0, 'property/7/5.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (76, 0, 'property/7/6.jpg', 'IMAGE');


insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, property_status, price, distance_to_sea, bedrooms, heating_system, furnishing, representative_image_id, owner_id)
  values (7, 0, 'Crete Apartment for rent', 'Greece, Crete, Chania', 'Greece, Crete, Chania', 'GREECE', '35.463105', '24.133071', 'APARTMENT', 'LONG_TERM', 350, 150, 2, true, 'NONE', 71, 1);


insert into localized_property (id, version, locale, description, property_id)
  values (7, 0, 'en', 'This rental offers space and comfort, both inside and out, benefiting from a balcony that wraps around the apartment.

Entering this wonderful apartment there is a hallway leading to the living-dining room as well as to the two bedrooms of 11m² each.

Both bedrooms feature fitted wardrobes.

This marvelous apartment also comprises a kitchen of 10m² and a bathroom with bathtub.

A great advantage of this rental is its proximity to all amenities such as a big super market (100m), and a bus station (50m) with frequent schedules to Chania city as well as many other destinations.

This is an ideal base for a memorable living experience...', 7);

insert into localized_property (id, version, locale, description, property_id)
  values (107, 0, 'ru', '', 7);


insert into property_images (property_id, images_id) values (7, 71);
insert into property_images (property_id, images_id) values (7, 72);
insert into property_images (property_id, images_id) values (7, 73);
insert into property_images (property_id, images_id) values (7, 74);
insert into property_images (property_id, images_id) values (7, 75);
insert into property_images (property_id, images_id) values (7, 76);


insert into localized_property_specification (id, version, specification, localized_property_id) values (68, 0, 'Autonomous heating', 7);
insert into localized_property_specification (id, version, specification, localized_property_id) values (69, 0, 'Internet connection possible', 7);
insert into localized_property_specification (id, version, specification, localized_property_id) values (70, 0, 'Pet friendly', 7);

insert into localized_property_specification (id, version, specification, localized_property_id) values (1068, 0, 'Автономное отопление', 107);
insert into localized_property_specification (id, version, specification, localized_property_id) values (1069, 0, 'Доступ в интернет', 107);
insert into localized_property_specification (id, version, specification, localized_property_id) values (1070, 0, 'Отдых с животными разрешён', 107);

-- 8 [Long term] http://www.greece-property.net/property/3964/

insert into asset (id, version, key, asset_type) values (77, 0, 'property/8/1.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (78, 0, 'property/8/2.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (79, 0, 'property/8/3.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (80, 0, 'property/8/4.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (81, 0, 'property/8/5.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (82, 0, 'property/8/6.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (83, 0, 'property/8/7.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (84, 0, 'property/8/8.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (85, 0, 'property/8/9.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (86, 0, 'property/8/10.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (87, 0, 'property/8/11.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (88, 0, 'property/8/12.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (89, 0, 'property/8/13.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (90, 0, 'property/8/14.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (91, 0, 'property/8/15.jpg', 'IMAGE');


insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, property_status, price, distance_to_sea, bedrooms, heating_system, furnishing, representative_image_id, owner_id)
  values (8, 0, 'Traditional house for rent', 'Greece, Crete, Rethymno', 'Greece, Crete, Rethymno', 'GREECE', '35.368804', '24.703283', 'HOUSE', 'LONG_TERM', 450, 12000, 2, true, 'FULL', 77, 1);


insert into localized_property (id, version, locale, description, property_id)
  values (8, 0, 'en', '', 8);

insert into localized_property (id, version, locale, description, property_id)
  values (108, 0, 'ru', '', 8);


insert into property_images (property_id, images_id) values (8, 77);
insert into property_images (property_id, images_id) values (8, 78);
insert into property_images (property_id, images_id) values (8, 79);
insert into property_images (property_id, images_id) values (8, 80);
insert into property_images (property_id, images_id) values (8, 81);
insert into property_images (property_id, images_id) values (8, 82);
insert into property_images (property_id, images_id) values (8, 83);
insert into property_images (property_id, images_id) values (8, 84);
insert into property_images (property_id, images_id) values (8, 85);
insert into property_images (property_id, images_id) values (8, 86);
insert into property_images (property_id, images_id) values (8, 87);
insert into property_images (property_id, images_id) values (8, 88);
insert into property_images (property_id, images_id) values (8, 89);
insert into property_images (property_id, images_id) values (8, 90);
insert into property_images (property_id, images_id) values (8, 91);


insert into localized_property_specification (id, version, specification, localized_property_id) values (71, 0, 'Garden', 8);
insert into localized_property_specification (id, version, specification, localized_property_id) values (72, 0, 'Fire place', 8);
insert into localized_property_specification (id, version, specification, localized_property_id) values (73, 0, 'Internet Connection', 8);


-- 9 [House] Determinis

insert into asset (id, version, key, asset_type) values (92, 0, 'property/9/IMG_0019.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (93, 0, 'property/9/IMG_0020.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (94, 0, 'property/9/IMG_0021.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (95, 0, 'property/9/IMG_0022.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (96, 0, 'property/9/IMG_0023.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (97, 0, 'property/9/IMG_0024.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (98, 0, 'property/9/IMG_0025.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (99, 0, 'property/9/IMG_0026.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (100, 0, 'property/9/IMG_0027.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (101, 0, 'property/9/IMG_0028.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (102, 0, 'property/9/IMG_0029.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (103, 0, 'property/9/IMG_0030.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (104, 0, 'property/9/IMG_0031.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (105, 0, 'property/9/IMG_0032.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (106, 0, 'property/9/IMG_0033.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (107, 0, 'property/9/IMG_0034.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (108, 0, 'property/9/IMG_0035.jpg', 'IMAGE');

insert into asset (id, version, key, asset_type) values (109, 0, 'property/9/pano.xml', 'PANO_XML');


insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, property_status, price, bedrooms, heating_system, furnishing, representative_image_id, pano_xml_id, owner_id)
  values (9, 0, 'Demetris Residence', '18 Agiou Amvrosiou street Krasas area 7100 Aradippou Larnaca Cyprus', '18 Agiou Amvrosiou street, Larnaca', 'LARNACA', '34.904490', '33.587985', 'HOUSE', 'LONG_TERM', 1000, 4, true, 'FULL', 93, 109, 2);


insert into localized_property (id, version, locale, description, property_id)
  values (9, 0, 'en', '', 9);

insert into localized_property (id, version, locale, description, property_id)
  values (109, 0, 'ru', '', 9);


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

insert into localized_property_specification (id, version, specification, localized_property_id) values (74, 0, 'A wood fireplace', 9);
insert into localized_property_specification (id, version, specification, localized_property_id) values (75, 0, 'A/C in all bed rooms and kitchen', 9);
insert into localized_property_specification (id, version, specification, localized_property_id) values (76, 0, 'Ceiling fans with remote control', 9);
insert into localized_property_specification (id, version, specification, localized_property_id) values (77, 0, 'Alarm system', 9);
insert into localized_property_specification (id, version, specification, localized_property_id) values (78, 0, 'Garage gate with remote control', 9);
insert into localized_property_specification (id, version, specification, localized_property_id) values (79, 0, 'Three olive trees', 9);
insert into localized_property_specification (id, version, specification, localized_property_id) values (80, 0, 'One lemon tree', 9);
insert into localized_property_specification (id, version, specification, localized_property_id) values (81, 0, 'One mandarin tree', 9);
insert into localized_property_specification (id, version, specification, localized_property_id) values (82, 0, 'One walnut tree', 9);
insert into localized_property_specification (id, version, specification, localized_property_id) values (83, 0, 'Other plants', 9);

insert into localized_property_specification (id, version, specification, localized_property_id) values (1074, 0, 'Дровяной камин', 109);
insert into localized_property_specification (id, version, specification, localized_property_id) values (1075, 0, 'Кондиционеры во всех комнатах', 109);
insert into localized_property_specification (id, version, specification, localized_property_id) values (1076, 0, 'Потолочные вентиляторы', 109);
insert into localized_property_specification (id, version, specification, localized_property_id) values (1077, 0, 'Охранная система', 109);
insert into localized_property_specification (id, version, specification, localized_property_id) values (1078, 0, 'Гараж с дистанц. управлением', 109);
insert into localized_property_specification (id, version, specification, localized_property_id) values (1079, 0, 'Три оливковых дерева', 109);
insert into localized_property_specification (id, version, specification, localized_property_id) values (1080, 0, 'Лимонное дерево', 109);
insert into localized_property_specification (id, version, specification, localized_property_id) values (1081, 0, 'Мандариновое дерево', 109);
insert into localized_property_specification (id, version, specification, localized_property_id) values (1082, 0, 'Ореховое дерево', 109);
insert into localized_property_specification (id, version, specification, localized_property_id) values (1083, 0, 'Другие растения', 109);


-- 10 (Short term) - a short term offer for property #9

insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, property_status, price, bedrooms, guests, air_conditioner, distance_to_sea, representative_image_id, pano_xml_id, owner_id)
  values (10, 0, 'Demetris Residence', '18 Agiou Amvrosiou street Krasas area 7100 Aradippou Larnaca Cyprus', '18 Agiou Amvrosiou street, Larnaca', 'LARNACA', '34.904490', '33.587985', 'HOUSE', 'SHORT_TERM', 100, 4, 8, true, 4500, 93, 109, 2);


insert into localized_property (id, version, locale, description, property_id)
  values (10, 0, 'en', '', 10);

insert into localized_property (id, version, locale, description, property_id)
  values (110, 0, 'ru', '', 10);

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

insert into localized_property_specification (id, version, specification, localized_property_id) values (84, 0, 'A wood fireplace', 10);
insert into localized_property_specification (id, version, specification, localized_property_id) values (85, 0, 'A/C in all bed rooms and kitchen', 10);
insert into localized_property_specification (id, version, specification, localized_property_id) values (86, 0, 'Ceiling fans with remote control', 10);
insert into localized_property_specification (id, version, specification, localized_property_id) values (87, 0, 'Alarm system', 10);
insert into localized_property_specification (id, version, specification, localized_property_id) values (88, 0, 'Garage gate with remote control', 10);
insert into localized_property_specification (id, version, specification, localized_property_id) values (89, 0, 'Three olive trees', 10);
insert into localized_property_specification (id, version, specification, localized_property_id) values (90, 0, 'One lemon tree', 10);
insert into localized_property_specification (id, version, specification, localized_property_id) values (91, 0, 'One mandarin tree', 10);
insert into localized_property_specification (id, version, specification, localized_property_id) values (92, 0, 'One walnut tree', 10);
insert into localized_property_specification (id, version, specification, localized_property_id) values (93, 0, 'Other plants', 10);

insert into localized_property_specification (id, version, specification, localized_property_id) values (10084, 0, 'Дровяной камин', 110);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10085, 0, 'Кондиционеры во всех комнатах', 110);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10086, 0, 'Потолочные вентиляторы', 110);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10087, 0, 'Охранная система', 110);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10088, 0, 'Гараж с дистанц. управлением', 110);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10089, 0, 'Три оливковых дерева', 110);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10090, 0, 'Лимонное дерево', 110);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10091, 0, 'Мандариновое дерево', 110);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10092, 0, 'Ореховое дерево', 110);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10093, 0, 'Другие растения', 110);

-- 11 Trisveis / Villa at dekhelia road(near Galu with common pool)

insert into asset (id, version, key, asset_type) values (110, 0, 'property/11/DSC_0287.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (111, 0, 'property/11/DSC_0291.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (112, 0, 'property/11/DSC_0283.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (113, 0, 'property/11/19.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (114, 0, 'property/11/H3DR0167-S-22.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (115, 0, 'property/11/H3DR0167-S-23.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (116, 0, 'property/11/DSC_0294.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (117, 0, 'property/11/DSC_0293.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (118, 0, 'property/11/DSC_0279.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (119, 0, 'property/11/DSC_0278.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (120, 0, 'property/11/DSC_0282.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (121, 0, 'property/11/DSC_0288.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (122, 0, 'property/11/DSC_0277.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (123, 0, 'property/11/DSC_0284.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (124, 0, 'property/11/20.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (125, 0, 'property/11/DSC_0295.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (126, 0, 'property/11/DSC_0280.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (127, 0, 'property/11/DSC_0286.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (128, 0, 'property/11/DSC_0290.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (129, 0, 'property/11/H3DR0167-S.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (130, 0, 'property/11/DSC_0289.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (131, 0, 'property/11/1.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (132, 0, 'property/11/DSC_0276.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (133, 0, 'property/11/DSC_0292.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (134, 0, 'property/11/DSC_0281.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (135, 0, 'property/11/DSC_0285.jpg', 'IMAGE');


insert into property (id, version, title, address, short_address, location, latitude, longitude, property_type, property_status, price, bedrooms, guests, air_conditioner, distance_to_sea, representative_image_id, pano_xml_id, owner_id)
  values (11, 0, 'East Villa #4', '8 Anatolis street, house #4, 7041, Larnaca, Cyprus', '8 Anatolis street, Larnaca', 'LARNACA', '34.960201', '33.654542', 'HOUSE', 'SHORT_TERM', 150, 3, 6, true, 200, 115, null, 3);


insert into localized_property (id, version, locale, description, property_id)
  values (11, 0, 'en', 'This lovely modern property has quality furnishings throughout. There is a ground floor wc and hand basin. The villa has a spacious open plan living/dining area with large white sofa, quality coffee table, feature hall unit and desk. The dining table seats 6 persons.
The kitchen has high quality units, with breakfast bar and comes fully equipped. The dining area has patio doors that lead to a small secure outside terrace, access to the underground parking. The terrace has a gas BBQ and an outside dining table with 6 chairs.The small wooden and iron spiral staircase leads to a twin bedroom with fitted wardrobes and twin single beds.
The second bedroom has two desks with a bed fitment/upper bunk bed over, this is a high bed suitable for a child, and the mattress is of a good quality and single size.  The master bedroom has an ensuite shower room with wc and wash basin and has a wooden/iron framed king size water bed, that can be heated in the winter.
The family bathroom has a bath, wc and hand basin. At the front of the villa is a communal pool with a paved terrace around.There is also underground parking with access to the rear of the villa.Home size 170 sq.m
Guests will be required to pay a security deposit of 100 euros upon arrival. This security deposit is to cover any costs due to any damage(s) by yourself or any member of your party. If no deductions are required, a full refund will be made upon departure from the property. Should the security deposit not be sufficient to cover any damage(s) or service charges incurred by yourself and/or your party, you will be responsible for the extra payment immediately and upon request by the owners representative.'
  , 11);

insert into localized_property (id, version, locale, description, property_id)
  values (111, 0, 'ru', 'Красивый современный дом экипирован качественной мебелью. Кухня оборудована по последнему слову техники, где предусмотрен даже барный столик. Обеденный стол, рассчитан на 6 человек. Из кухни Вы можете переместиться на террасу для времяпровождения на открытом воздухе. Для Вашего удобства на террасе размещен стол на шесть персон и барбекю зона. Просторная гостиная меблирована комфортабельными белыми диванами. Гостиная комната  органично переходит в просторную столовую. Между кухней и столовой располагается гостевой туалет.
На второй этаж ведет винтовая лестница с удобными деревянными ступеньками. В родительской спальне установлена большая водяная кровать с обогревом, так же есть ванная комната с душевой кабиной и остальными удобствами. В другой спальне установлены двухместная кровать и встроенный шкаф. В детской комнате установлены две двухъярусные кровати с удобными матрацами. В общей ванной комнате можно принять ванную.
Перед домом находится бассейн на два дома. Бассейн огорожен перегородкой от любопытных взоров. Имеется подземная парковка с черным входом в дом. Размер дома 170 кв.м.
Гости при въезде должны внести 100 евро на депозит на случай непредвиденных инцидентов, связанных с порчей имущества в апартаментах. Депозит возвращается в полном объеме в день выезда (сдачи апартаментов владельцу). Если Вы или Ваши гости случайно разбили или повредили оборудование, то из внесенного депозита вычитается необходимая сумма для покрытия убытков владельца. Если ущерб превышает размер депозита, то Вам необходимо будет покрыть разницу.'
  , 11);


insert into property_images (property_id, images_id) values (11, 110);
insert into property_images (property_id, images_id) values (11, 111);
insert into property_images (property_id, images_id) values (11, 112);
insert into property_images (property_id, images_id) values (11, 113);
insert into property_images (property_id, images_id) values (11, 114);
insert into property_images (property_id, images_id) values (11, 115);
insert into property_images (property_id, images_id) values (11, 116);
insert into property_images (property_id, images_id) values (11, 117);
insert into property_images (property_id, images_id) values (11, 118);
insert into property_images (property_id, images_id) values (11, 119);
insert into property_images (property_id, images_id) values (11, 120);
insert into property_images (property_id, images_id) values (11, 121);
insert into property_images (property_id, images_id) values (11, 122);
insert into property_images (property_id, images_id) values (11, 123);
insert into property_images (property_id, images_id) values (11, 124);
insert into property_images (property_id, images_id) values (11, 125);
insert into property_images (property_id, images_id) values (11, 126);
insert into property_images (property_id, images_id) values (11, 127);
insert into property_images (property_id, images_id) values (11, 128);
insert into property_images (property_id, images_id) values (11, 129);
insert into property_images (property_id, images_id) values (11, 130);
insert into property_images (property_id, images_id) values (11, 131);
insert into property_images (property_id, images_id) values (11, 132);
insert into property_images (property_id, images_id) values (11, 133);
insert into property_images (property_id, images_id) values (11, 134);
insert into property_images (property_id, images_id) values (11, 135);


insert into localized_property_specification (id, version, specification, localized_property_id) values (94, 0, 'Cooker', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (95, 0, 'Fridge', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (96, 0, 'Freezer', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (97, 0, 'Microwave', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (98, 0, 'Toaster', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (99, 0, 'Kettle', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (100, 0, 'Dishwasher', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (101, 0, 'Washing machine', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (102, 0, 'Clothes dryer', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (103, 0, 'Iron', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (104, 0, 'High chair', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (105, 0, 'Satellite TV', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (106, 0, 'Wi-Fi available', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (107, 0, 'Ironing board', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (108, 0, 'Ceiling Fans', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (109, 0, 'Air conditioning', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (110, 0, 'Linen provided', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (111, 0, 'Towels provided', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (112, 0, 'Shared outdoor pool', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (113, 0, 'Shared garden', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (114, 0, 'Balcony or terrace', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (115, 0, 'Parking', 11);
insert into localized_property_specification (id, version, specification, localized_property_id) values (116, 0, 'Garage', 11);

insert into localized_property_specification (id, version, specification, localized_property_id) values (10094, 0, 'Плита', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10095, 0, 'Холодильник', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10096, 0, 'Микроволновка', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10097, 0, 'Тостер', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10098, 0, 'Чайник', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10099, 0, 'Посудомоечная машина', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10100, 0, 'Стиральная машина', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10101, 0, 'Сушилка для белья', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10102, 0, 'Барный стул', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10103, 0, 'Спутниковое ТВ', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10104, 0, 'Wi-Fi', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10105, 0, 'Гладильная доска', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10106, 0, 'Потолочные вентиляторы', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10107, 0, 'Кондиционер', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10108, 0, 'Постельное белье', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10109, 0, 'Полотенца', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10110, 0, 'Бассейн на два дома', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10111, 0, 'Общий сад', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10112, 0, 'Терраса', 111);
insert into localized_property_specification (id, version, specification, localized_property_id) values (10113, 0, 'Стоянка', 111);
