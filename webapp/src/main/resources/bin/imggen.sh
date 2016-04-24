#!/bin/sh

# Choosing property ID

while true; do
	read -e -p "Property ID: " -i "${ID:-}" ID
	read -e -p "Will generate images for the Property '${ID}'. Are you sure? (yes/no): " -i "yes" ok

	if [ "$ok" = "yes" ]; then break; fi
done

echo
echo "Cleaning up..."
rm -rf MEDIUM*.jpg SMALL*.jpg THUMB*.jpg
rm -rf MEDIUM BIG
echo
echo "OK"


echo
echo "JPG -> jpg ..."
find . -name "*.JPG" -exec sh -c 'mv "${1}" "${1%.*}".jpg' _ {} \;
echo
echo "OK"

echo
echo "Auto orienting pictures..."
find . -maxdepth 1 -name "*.jpg" -exec sh -c 'convert -auto-orient "${1}" "${1}"' _ {} \;
echo
echo "OK"

echo
echo "Creating images of appropriate size..."
mkdir MEDIUM BIG
find . -maxdepth 1 -name "*.jpg" -exec sh -c 'convert -quality 50 -resize 524x394^ -gravity center -crop 524x394+0+0 +repage "${1}" "MEDIUM/${1##*/}"' _ {} \;
find . -maxdepth 1 -name "*.jpg" -exec sh -c 'convert -quality 70 -resize 1920x1080^ -gravity center "${1}" "BIG/${1##*/}"' _ {} \;
find . -maxdepth 1 -name "*.jpg" -exec sh -c 'convert -quality 80 -resize 3840x2160^ "${1}" "${1}"' _ {} \;
echo
echo "OK"

echo
echo "Uploading images to S3..."
aws s3 cp . s3://assets.gotocy.com/property/$ID/ --recursive --exclude "*" --include "*.jpg" --exclude "MEDIUM/*" --exclude "BIG/*"
aws s3 cp MEDIUM/ s3://assets.gotocy.com/property/$ID/MEDIUM/ --recursive --storage-class REDUCED_REDUNDANCY
aws s3 cp BIG/ s3://assets.gotocy.com/property/$ID/BIG/ --recursive --storage-class REDUCED_REDUNDANCY
echo
echo "OK"

echo
echo "We're done. Here is the list of paths for you:"
for f in *.jpg; do echo "property/$ID/$f"; done
echo