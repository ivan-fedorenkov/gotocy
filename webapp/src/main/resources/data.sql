-- 1 (Short term)

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

insert into property (id, version, location, latitude, longitude, property_type, property_status, price, area, bedrooms, baths, air_conditioner, distance_to_sea, representative_image_id)
  values (1, 0, 'GREECE', '35.318198', '24.318744', 'APARTMENT', 'SHORT_TERM', 95, 80, 1, 1, 'Y', 1200, 1);

insert into localized_property (id, version, locale, title, address, description, property_id)
  values (1, 0, 'en', 'Sofia', 'Chania, Crete, Nomós Chaníon', 'Kastellos village is situated in the picturesque village of Kastellos in Georgioupolis, Chania. The complex is ideal for those wishing to escape everyday routine and relax enjoying the Cretan countryside; it’s for those who want to contact the nature, the wild life and of course to meet the people and their habits.
"Sofia Apartment" independent and fully equipped. It is built according to the traditional architecture. On the outside, beautiful stone work frames the dark green doorways and windows, further accentuated by the pastel strawberry and lemon walls. Add to this lovely tiled roofs and backdrop of the blue sky and mountains, and then you begin to understand why we were so smitten.
Inside the restoration is if anything, even more remarkable, with beautifully tiled floors, ceilings supported by huge beams interlaced with traditional bamboo linings and pale ochre coloured walls set off by elegant concealed lighting.
"Sofia Apartment" has two floors and a private veranda with panoramic view of the mountain and the sea. Is has a fully equipped kitchen (with kitchen appliances and utensils), bathroom with bathtub, WC with shower and one or two bedrooms with king size beds. The house combines harmoniously the traditional style with modern decorative elements, creating a warm and cosy atmosphere. For your convenience, you can find A/C, fireplace and plasma TV and can accommodate 2 to 6 persons.

KASTELLOS VILLAGE is built in a privileged location. During your stay here you can make a tour around the area and enjoy nature.
It is worth visiting the famous Kourna lake, which is only 5 km from the village. You can also visit the area of Argiroupolis, known for the running and spring waters. You will have a meal under the plane trees and visit the small church of Saint Prokopios, which is built in a cave.
The popular beach of Georgioupolis (5 km from the complex) is a pole of attraction for the visitors of the area.
There are many restaurants and taverns in the village, where you can enjoy the famous recipes of the traditional Cretan cuisine.
Semi-mountainous village built on the feet of the White Mountains on an altitude of 252m. Its named derived from the site it was built (like a castle). Originally the village was situated lower on the valley in the present area of the settlement Agathes from where the inhabitants were forced to leave in 1800 due to the Ottomans. Close to the village a there was in 1835 a severe battle amongst Cretans and Ottomans that left 22 dead Ottomans and just 1 Cretan. It was the reason that the ottomans stopped raiding the area. The locals had an important contribution to the resistance against the Germans fascists during the 2sn World War.'
  , 1);

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


insert into localized_property_specification (id, version, specification, localized_property_id) values (1, 0, 'Bedroom (2 Guests - 1 double bed)', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (2, 0, 'Living Room (1 Guest - 1 sofa bed / futon)', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (3, 0, 'Air conditioning', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (4, 0, 'Children friendly, Smoking', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (5, 0, 'Board games, Internet (Wi-Fi), Radio, TV', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (6, 0, 'Balcony', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (7, 0, 'Barbecue', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (8, 0, 'Garden', 1);
insert into localized_property_specification (id, version, specification, localized_property_id) values (9, 0, 'Parking / Garage', 1);

-- 2 (Short term)

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


insert into property (id, version, location, latitude, longitude, property_type, property_status, price, area, bedrooms, baths, air_conditioner, distance_to_sea, representative_image_id)
  values (2, 0, 'GREECE', '35.31242', '24.310504', 'HOUSE', 'SHORT_TERM', 118, 130, 2, 2, 'Y', 120, 19);

insert into localized_property (id, version, locale, title, address, description, property_id)
  values (2, 0, 'en', 'Anezina', 'Chania, Crete, Nomós Chaníon', 'Kastellos village is situated in the picturesque village of Kastellos in Georgioupolis, Chania. The complex is ideal for those wishing to escape everyday routine and relax enjoying the Cretan countryside; it’s for those who want to contact the nature, the wild life and of course to meet the people and their habits.
"Anezina House" independent and fully equipped. It is built according to the traditional architecture. On the outside, beautiful stone work frames the dark green doorways and windows, further accentuated by the pastel strawberry and lemon walls. Add to this lovely tiled roofs and backdrop of the blue sky and mountains, and then you begin to understand why we were so smitten.
Inside the restoration is if anything, even more remarkable, with beautifully tiled floors, ceilings supported by huge beams interlaced with traditional bamboo linings and pale ochre coloured walls set off by elegant concealed lighting.
"Anezina House" has two floors and a private veranda with panoramic view of the mountain and the sea. Is has a fully equipped kitchen (with kitchen appliances and utensils), bathroom with bathtub, WC with shower and one or two bedrooms with king size beds. The house combines harmoniously the traditional style with modern decorative elements, creating a warm and cosy atmosphere. For your convenience, you can find A/C, fireplace and plasma TV and can accommodate 2 to 6 persons.

KASTELLOS VILLAGE is built in a privileged location. During your stay here you can make a tour around the area and enjoy nature.
It is worth visiting the famous Kourna lake, which is only 5 km from the village. You can also visit the area of Argiroupolis, known for the running and spring waters. You will have a meal under the plane trees and visit the small church of Saint Prokopios, which is built in a cave.
The popular beach of Georgioupolis (5 km from the complex) is a pole of attraction for the visitors of the area.
There are many restaurants and taverns in the village, where you can enjoy the famous recipes of the traditional Cretan cuisine.
Semi-mountainous village built on the feet of the White Mountains on an altitude of 252m. Its named derived from the site it was built (like a castle). Originally the village was situated lower on the valley in the present area of the settlement Agathes from where the inhabitants were forced to leave in 1800 due to the Ottomans. Close to the village a there was in 1835 a severe battle amongst Cretans and Ottomans that left 22 dead Ottomans and just 1 Cretan. It was the reason that the ottomans stopped raiding the area. The locals had an important contribution to the resistance against the Germans fascists during the 2sn World War.'
  , 2);

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


insert into localized_property_specification (id, version, specification, localized_property_id) values (10, 0, 'Bedroom (2 Guests - 1 double bed)', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (11, 0, 'Bedroom (2 Guests - 1 double bed)', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (12, 0, 'Living Room (2 Guests - 2 sofa beds / futons)', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (13, 0, 'Air conditioning', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (14, 0, 'Board games, Internet (Wi-Fi), Radio, TV, Telephone', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (15, 0, 'Children friendly, Smoking', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (16, 0, 'Balcony', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (17, 0, 'Barbecue', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (18, 0, 'Garden', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (19, 0, 'Parking / Garage', 2);
insert into localized_property_specification (id, version, specification, localized_property_id) values (20, 0, 'Swimming Pool', 2);

-- 3 (Short term)

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


insert into property (id, version, location, latitude, longitude, property_type, property_status, price, area, bedrooms, baths, air_conditioner, distance_to_sea, representative_image_id)
  values (3, 0, 'GREECE', '35.345041', '24.689299', 'HOUSE', 'SHORT_TERM', 141, 160, 3, 2, 'N', 150, 45);

insert into localized_property (id, version, locale, title, address, description, property_id)
  values (3, 0, 'en', 'Green Paradise Luxury Villa', 'Rethymno, Crete, Nomós Rethýmnis','Built in a quiet hamlet of the village Margarites, the Tzannakiana villa ‘Green Paradise’ is a country retreat, stone-built with a distinctive blend of modern and traditional. In an area of natural beauty, surrounded by a garden of flowers, herbs and trees. This is a luxurious villa to rent for your perfect holiday.

The villa is on the outskirts of the hamlet and so has stunning views of the Mediterranean Sea, the surrounding lush countryside and mountains. Quiet and private but not isolated, just 0.6km from the centre of the picturesque village of Margarites where there are traditional tavernas, cafes and a super-market. The village of Margarites also has a renowned pottery where visitors have the opportunity to watch the ceramic pottery being made.

Green Paradise Villa is only 20 km from Rethymno, 72km from Herkalion airport and 95km from Chania airport. The villa is also in the vicinity of several places of historical interest such as Arkadi (16km), ancient Eleftherna (5km) and a little bit further afield the famous archaeological site of Knossos (72km). And conveniently, the villa is just 13km (approx: 10-15 minute drive) from the beautiful, sandy beaches of Panormo. In the large village of Panormo you will find more amenities.

This delightful two-storey villa is 160 sq.m, with beautifully tiled floors and high, wooden beam ceilings. It features a lovely well-kept garden and terrace with a 20 sq.m pool with sunbeds and umbrellas, barbecue facilities and parking area. It can accommodate 6 people in three elegantly furnished bedrooms, of which two have double beds and the third has two single beds. Furthermore, it is possible for more persons to be accommodated. The very comfortable living room sofa easily converts into a bed and a cot/crib for a baby/infant is available that can be used in any of the rooms. In addition all rooms feature air conditioning, satellite T.V. and internet access.

On the ground floor there is a patio of 35 sq.m with steel table and chairs, ideal for dining in the garden. The comfortable sitting room is fully furnished and has satellite T.V., CD player and internet access. The modern fully equipped kitchen has all essential appliances such as electric cooker/oven, fridge/freezer, dish-washer, microwave, kettle, coffee maker, toaster, juicer etc. and dining table and chairs. There is a large, beautifully designed 17 sq.m bathroom with jacuzzi/bathtub, double wash-hand basins and toilet. Off the bathroom, there is a separate storage room for laundry with a washing machine. Also on the ground floor is one of the double bedrooms.

On the first floor there is a 35 sq.m balcony with a comfortable, wicker suite and coffee table where you can sit back, relax and overlook the breath-taking views of the Mediterranean Sea and Cretan countryside. There is a shower-room with wash-hand basin and toilet. The other double bedroom is also on this floor along with the bedroom with the two single beds.

Throughout, the interior has been furnished with stylish, modern wooden furniture and elegant fabrics, with some modern and traditional touches, all in perfect harmony. Plus, there is ample storage space in all of the rooms.

Due to the location of the village and the topography of the area, there are many beautiful hiking trails, such as E4 starting from the village of Margarites and going up to Eleftherna. Green Paradise Villa offers visitors (on request) organised excursions in the countryside (at weekends) enabling them to explore the archaeological site of ancient Eleftherna, the caves of Melidoni and Zoniana all located within walking distance.

Your stay, here at Green Paradise Luxury Villa, is sure to be heavenly!

Green Paradise Villa is approved by Greek Tourism Organisation with license number: 1041K91002955601'
  , 3);


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


insert into localized_property_specification (id, version, specification, localized_property_id) values (21, 0, 'Bedroom (2 Guests - 1 double bed)', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (22, 0, 'Bedroom (2 Guests - 1 double bed)', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (23, 0, 'Bedroom (2 Guests - 2 single beds)', 3);
insert into localized_property_specification (id, version, specification, localized_property_id) values (24, 0, 'Living area (1 Guest - 1 sofa bed / futon)', 3);
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

-- 4 (Short term)

insert into asset (id, version, key, asset_type) values (49, 0, 'property/4/1.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (50, 0, 'property/4/2.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (51, 0, 'property/4/3.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (52, 0, 'property/4/4.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (53, 0, 'property/4/5.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (54, 0, 'property/4/6.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (55, 0, 'property/4/7.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (56, 0, 'property/4/8.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (57, 0, 'property/4/9.jpg', 'IMAGE');


insert into property (id, version, location, latitude, longitude, property_type, property_status, price, area, bedrooms, baths, air_conditioner, distance_to_sea, representative_image_id)
  values (4, 0, 'GREECE', '36.295279', '28.155108', 'HOUSE', 'SHORT_TERM', 182, 150, 4, 4, 'Y', 250, 49);

insert into localized_property (id, version, locale, title, address, description, property_id)
  values (4, 0, 'en', 'Villa Chrysa', 'Rhodes town, Rhodes, Dodecanese', 'Its located in St. Luke Afandou on the eastern side of the island . It is about 20 km away from the city of Rhodes and from Rhodes Airport , 3 km from Afandou beach -with panoramic view towards this beach- it is also 2 km away from Afandou Golf Course , 6 km from the tourist resort Faliraki of Rhodes with the known beach , 6 km from the tourist resort of Kolymbia Rhodes and the famous Tsampika beach .
Also , its location is such that it is easily accessible to other tourist resorts/sightseeings of the island such as Lindos which is 30km away , Seven Springs - 7km, Butterflies - 15k , -and Filerimos is 20km away, the above are just a portion of the accessible options close and relatively close to its location, there are many more. It was built in 2010 and it will be the first year that will be used by visitors .The town in which is located, Afandou, has population of approximately 7000 inhabitants, it is easily accessible , and it takes just 15 minutes to get to the city because of the existence of the highway. Except from golf and the known beach with length over 7 thousand km , in the area you can find Super Market, Banks , Police Station, tavernas , cafes , bars and hotel complexes .
The house is located on a hill above the city Afandou with boundless view. It has a private driveway along with 4 other houses in the area , it was built on a plot of 13,000 sq.m. and it has ground floor. The total area of ​the 1st floor is ​150sq.m. On the ground floor there are kitchen , living room , dining room, storage room and WC, and on the first floor there are three bedrooms and two bathrooms . It also features a guest house made of stone, in the courtyard area. Its dimensions are 40sqm, and includes kitchen, bathroom, air-condition and other amenities. Overall the house, along with the guest house, has the possibility of hosting at least 8 people. Besides the swimming pool and Spa, it also has barbecue, oven (wood-fired oven), garden etc.
It is available all year round.

Note that the charge of the main villa for months July and August is 50 euro extra.
Also, the charge of guest house is 50 euro.'
  , 4);


insert into property_images (property_id, images_id) values (4, 49);
insert into property_images (property_id, images_id) values (4, 50);
insert into property_images (property_id, images_id) values (4, 51);
insert into property_images (property_id, images_id) values (4, 52);
insert into property_images (property_id, images_id) values (4, 53);
insert into property_images (property_id, images_id) values (4, 54);
insert into property_images (property_id, images_id) values (4, 55);
insert into property_images (property_id, images_id) values (4, 56);
insert into property_images (property_id, images_id) values (4, 57);


insert into localized_property_specification (id, version, specification, localized_property_id) values (40, 0, 'Bedroom (2 Guests - 1 double bed)', 4);
insert into localized_property_specification (id, version, specification, localized_property_id) values (41, 0, 'Bedroom (2 Guests - 1 double bed)', 4);
insert into localized_property_specification (id, version, specification, localized_property_id) values (42, 0, 'Bedroom (1 Guest - 1 single bed)', 4);
insert into localized_property_specification (id, version, specification, localized_property_id) values (43, 0, 'Bedroom (3 Guests - 1 double bed, 1 single bed)', 4);
insert into localized_property_specification (id, version, specification, localized_property_id) values (44, 0, 'Children friendly, Pets, Smoking', 4);
insert into localized_property_specification (id, version, specification, localized_property_id) values (45, 0, 'Hair Dryer, Shower, Soap and Shampoo, Towels', 4);
insert into localized_property_specification (id, version, specification, localized_property_id) values (46, 0, 'Air conditioning', 4);
insert into localized_property_specification (id, version, specification, localized_property_id) values (47, 0, 'Balcony', 4);
insert into localized_property_specification (id, version, specification, localized_property_id) values (48, 0, 'Barbecue', 4);
insert into localized_property_specification (id, version, specification, localized_property_id) values (49, 0, 'Garden', 4);
insert into localized_property_specification (id, version, specification, localized_property_id) values (50, 0, 'Parking / Garage', 4);
insert into localized_property_specification (id, version, specification, localized_property_id) values (51, 0, 'Swimming Pool', 4);

-- 5 (Sale)

insert into asset (id, version, key, asset_type) values (58, 0, 'property/5/DSC_3562.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (59, 0, 'property/5/DSC_3761.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (60, 0, 'property/5/DSC_3775.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (61, 0, 'property/5/DSC_3778.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (62, 0, 'property/5/DSC_3781.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (63, 0, 'property/5/DSC_3785.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (64, 0, 'property/5/DSC_3789.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (65, 0, 'property/5/DSC_3791.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (66, 0, 'property/5/DSC_3796.jpg', 'IMAGE');


insert into property (id, version, location, latitude, longitude, property_type, property_status, price, area, bedrooms, baths, representative_image_id)
  values (5, 0, 'LARNACA', '34.972661', '33.645909', 'APARTMENT', 'SALE', 338000, 105, 3, 1, 66);

insert into localized_property (id, version, locale, title, address, description, property_id)
  values (5, 0, 'en', 'LAR 801/1', 'Oroklini', '', 5);

insert into localized_property (id, version, locale, title, address, description, property_id)
  values (105, 0, 'ru', 'LAR 801/1', 'Oroklini', '', 5);


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


-- 6 (Sale)

insert into asset (id, version, key, asset_type) values (67, 0, 'property/6/DSC_3529.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (68, 0, 'property/6/DSC_3534.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (69, 0, 'property/6/DSC_3539.jpg', 'IMAGE');
insert into asset (id, version, key, asset_type) values (70, 0, 'property/6/DSC_3545.jpg', 'IMAGE');


insert into property (id, version, location, latitude, longitude, property_type, property_status, price, area, bedrooms, baths, representative_image_id)
  values (6, 0, 'LARNACA', '34.914313', '33.637733', 'APARTMENT', 'SALE', 572000, 78, 2, 1, 70);

insert into localized_property (id, version, locale, title, address, description, property_id)
  values (6, 0, 'en', 'LAR 802', 'Athenon street', '', 6);

insert into localized_property (id, version, locale, title, address, description, property_id)
  values (106, 0, 'ru', 'LAR 802', 'Athenon street', '', 6);



insert into property_images (property_id, images_id) values (6, 67);
insert into property_images (property_id, images_id) values (6, 68);
insert into property_images (property_id, images_id) values (6, 69);
insert into property_images (property_id, images_id) values (6, 70);


insert into localized_property_specification (id, version, specification, localized_property_id) values (60, 0, 'Fully furnished, including TV', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (61, 0, 'Kitchen fully electrical appliances', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (62, 0, 'Automatic entrance and exit', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (63, 0, 'Fully air-conditioning', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (64, 0, 'Elevators (Lift)', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (65, 0, 'Gypsum board  ceilings, lighting', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (66, 0, 'Concierge service', 6);
insert into localized_property_specification (id, version, specification, localized_property_id) values (67, 0, 'Store room', 6);
