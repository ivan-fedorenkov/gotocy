#!/bin/bash

# Usefull commands:
#  find . -name "*.jpg" -exec sh -c 'mv ${1} ${1%.*}.jpg' _ {} \;
#  find . -name "*.jpg" -exec sh -c 'convert -resize 848x474^ -gravity center -crop 848x600+0+0 +repage ${1} MEDIUM_${1##*/}' _ {} \;
#  convert -resize 100x75^ -gravity center -crop 100x75+0+0 +repage 1.jpg THUMBNAIL_1.jpg
#  convert -resize 262x197^ -gravity center -crop 262x197+0+0 +repage 1.jpg SMALL_1.jpg


dir=$1 # directory to look for images
property_id=$2 # initial property id
image_id=$3 # initial image id
spec_id=$4 # initial specification id

find ${dir} -name "*.jpg" -printf "%T@ %Tc %p\n" | sort -n | while read f; do
  echo "insert into asset (id, version, key, asset_type) values (${image_id}, 0, 'property/${property_id}/${f##*/}', 'IMAGE');"
  ((image_id++))
done

image_id=$3
find ${dir} -name "*.jpg" -printf "%T@ %Tc %p\n" | sort -n | while read f; do
  echo "insert into property_images (property_id, images_id) values (${property_id}, ${image_id});"
  ((image_id++))
done

while read line; do
	echo "insert into localized_property_specification (id, version, specification, localized_property_id) values (${spec_id}, 0, '${line}', ${property_id});"
	((spec_id++))
done < "${dir}/specs.txt"