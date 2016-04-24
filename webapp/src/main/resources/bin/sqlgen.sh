#!/bin/bash

# Usefull commands:
# find . -name "*.JPG" -exec sh -c 'mv ${1} ${1%.*}.jpg' _ {} \;
# find . -maxdepth 1 -name "*.jpg" -exec sh -c 'convert -auto-orient "${1}" "${1}"' _ {} \;
# find . -maxdepth 1 -name "*.jpg" -exec sh -c 'convert -quality 50 -resize 524x394^ -gravity center -crop 524x394+0+0 +repage "${1}" "MEDIUM/${1##*/}"' _ {} \;
# find . -maxdepth 1 -name "*.jpg" -exec sh -c 'convert -quality 70 -resize 1920x1080^ -gravity center "${1}" "BIG/${1##*/}"' _ {} \;
# aws s3 cp MEDIUM/ s3://assets.gotocy.com/property/$ID/MEDIUM/ --recursive --storage-class REDUCED_REDUNDANCY
# aws s3 cp BIG/ s3://assets.gotocy.com/property/$ID/BIG/ --recursive --storage-class REDUCED_REDUNDANCY
# aws s3 cp . s3://assets.gotocy.com/property/$ID/ --recursive --exclude "*" --include "*.jpg" --exclude "MEDIUM/*" --exclude "BIG/*" --dryrun

dir=${1:-.} # working directory

if [ "$dir" != "." ]; then 
  echo "Changing dir to $dir..."
  cd $dir
fi

[ -f log.txt ] && rm log.txt

# http://stackoverflow.com/questions/3173131/redirect-copy-of-stdout-to-log-file-from-within-bash-script-itself
# Redirect stdout ( > ) into a named pipe ( >() ) running "tee"
exec > >(tee log.txt)
# Without this, only stdout would be captured - i.e. your
# log file would not contain any error messages.
# SEE answer by Adam Spiers, which keeps STDERR a seperate stream -
# I did not want to steal from him by simply adding his answer to mine.
exec 2>&1

echo
echo "Cleaning up previous results..."
rm MEDIUM*.jpg
rm SMALL*.jpg
echo

echo
echo "1. Enter property details"
echo

while true; do
  read -e -p "Property title: " -i "${property_title:-}" property_title
  read -e -p "Location (FAMAGUSTA, LIMASSOL, LARNACA, NICOSIA, PAPHOS): " -i ${property_location:-LARNACA} property_location
  read -e -p "Latitude: " -i "${property_lat:-}" property_lat
  read -e -p "Longitude: " -i "${property_long:-}" property_long
  read -e -p "Full Address: " -i "${property_full_address:-}" property_full_address
  read -e -p "Short Address: " -i "${property_short_address:-$property_full_address}" property_short_address
  read -e -p "Property Type (HOUSE, APARTMENT, FLAT): " -i "${property_type:-}"  property_type
  read -e -p "Property Status (LONG_TERM, SHORT_TERM, SALE): " -i "${property_status:-}" property_status
  read -e -p "Price: " -i "${property_price:-}" property_price
  read -e -p "Covered Area: " -i "${property_covered_area:-}" property_covered_area
  read -e -p "Plot Size: " -i "${property_plot_size:-}" property_plot_size
  read -e -p "Number of Bedrooms: " -i "${property_bedrooms:-}" property_bedrooms
  read -e -p "Number of Guests: " -i "${property_guests:-}" property_guests
  read -e -p "Distance to Sea: " -i "${property_dist_to_sea:-}" property_dist_to_sea
  read -e -p "Air Conditioner (TRUE, FALSE): " -i "${property_ac:-TRUE}" property_ac
  read -e -p "Ready to Move in (TRUE, FALSE): " -i "${property_ready_to_move_in:-}" property_ready_to_move_in
  read -e -p "Heating System (TRUE, FALSE): " -i "${property_heating_system:-}" property_heating_system
  read -e -p "Furnishing (FULL, SEMI, NONE): " -i "${property_furnishing:-}" property_furnishing

  echo 
  echo "Carefully review the provided details!"
  echo
  echo "-----------------------------------------"
  echo "Property title: ${property_title}"
  echo "Location: ${property_location}"
  echo "Latitude: ${property_lat}"
  echo "Longitude: ${property_long}"
  echo "Full Address: ${property_full_address}"
  echo "Short Address: ${property_short_address}"
  echo "Property Type: ${property_type}"
  echo "Property Status: ${property_status}"
  echo "Price: ${property_price}"
  echo "Covered Area: ${property_covered_area}"
  echo "Plot Size: ${property_plot_size}"
  echo "Number of Bedrooms: ${property_bedrooms}"
  echo "Number of Guests: ${property_guests}"
  echo "Distance to Sea: ${property_dist_to_sea}"
  echo "Air Conditioner: ${property_ac}"
  echo "Ready to Move in: ${property_ready_to_move_in}"
  echo "Heating System: ${property_heating_system}"
  echo "Furnishing: ${property_furnishing}"
  echo "-----------------------------------------"
  echo
  read -e -p "Is everything ok? (yes/no): " -i "yes" ok

  if [ "$ok" = "yes" ]; then break; fi
done

echo
echo "2. Making sure all pictures have .jpg extention"
echo

find . -name "*.JPG" -exec sh -c 'mv "${1}" "${1%.*}.jpg"' _ {} \;
echo "OK"

echo
echo "3. Generating script"
echo

echo "-----------------------------------------"


find ${dir} -name "*.jpg" -printf "%T@ %Tc %p\n" | sort -n | while read f; do
  echo "insert into asset (id, version, key, asset_type) values (<IMAGE_ID>, 0, 'property/<PROPERTY_ID>/${f##*/}', 'IMAGE');"
done

echo 

echo "insert into property (id, version, title, location, latitude, longitude, address, short_address, property_type, property_status, price, covered_area, plot_size, bedrooms, guests, distance_to_sea, air_conditioner, ready_to_move_in, heating_system, furnishing, representative_image_id, pano_xml_id, primary_contact_id) values (<PROPERTY_ID>, 0, '${property_title}', '${property_location}', '${property_lat:-0}', '${property_long:-0}', '${property_full_address:-}', '${property_short_address:-}', '${property_type:-!!!PROPERTY TYPE!!!}', '${property_status:-!!!PROPERTY STATUS!!!}', ${property_price:-0}, ${property_covered_area:-NULL}, ${property_plot_size:-NULL}, ${property_bedrooms:-0}, ${property_guests:-NULL}, ${property_dist_to_sea:-NULL}, ${property_ac:-FALSE}, ${property_ready_to_move_in:-FALSE}, ${property_heating_system:-FALSE}, '${property_furnishing:-NONE}', <REPRESENTATIVE_IMAGE_ID>, <PANO_XML_ID>, <OWNER_ID>);"

echo

echo "insert into localized_property (id, version, locale, description, property_id) values (<LP_EN_ID>, 0, 'en', '', <PROPERTY_ID>);"
echo "insert into localized_property (id, version, locale, description, property_id) values (<LP_RU_ID>, 0, 'ru', '', <PROPERTY_ID>);"

echo 

find ${dir} -name "*.jpg" -printf "%T@ %Tc %p\n" | sort -n | while read f; do
  echo "insert into property_images (property_id, images_id) values (<PROPERTY_ID>, <IMAGE_ID>);"
done

echo

while read line; do
	echo "insert into localized_property_specification (specification, localized_property_id) values ('${line}', <LP_EN_ID>);"
done < "${dir}/en_specs.txt"

echo

while read line; do
	echo "insert into localized_property_specification (specification, localized_property_id) values ('${line}', <LP_RU_ID>);"
done < "${dir}/ru_specs.txt"

echo "-----------------------------------------"

echo
read -e -p "Image conversion? (yes/no): " -i "yes" convert_images
if [ $convert_images = "yes" ]; then
  echo
  echo "4. Image conversion"
  echo

  echo
  echo "Auto-orient pictures..."
  find . -maxdepth 1 -name "*.jpg" -exec sh -c 'convert -auto-orient "${1}" "${1}"' _ {} \;
  echo "OK"

  ls -lah

  echo
  echo "Converting images to MEDIUM size..."
  find . -maxdepth 1 -name "*.jpg" -exec sh -c 'convert -quality 50 -resize 524x394^ -gravity center -crop 524x394+0+0 +repage "${1}" "MEDIUM/${1##*/}"' _ {} \;
  echo "OK"



  echo
  echo "Converting representative image to BIG size..."
  find . -maxdepth 1 -name "*.jpg" -exec sh -c 'convert -quality 50 -resize 1920x1080^ "${1}" "BIG/${1##*/}"' _ {} \;
  echo "OK"

  read -p "Please enter the representative image name: " representative_image
  echo
  echo "The representative image would be $representative_image"

fi

echo
echo "Looks like we're done!"
echo
