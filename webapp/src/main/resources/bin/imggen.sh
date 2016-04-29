#!/bin/sh

# Choosing property ID

while true; do
	read -e -p "Property ID: " -i "${ID:-}" ID
	read -e -p "Will generate images for the Property '${ID}'. Are you sure? (yes/no): " -i "yes" ok

	if [ "$ok" = "yes" ]; then break; fi
done

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
echo ""Resizing original images to the reasonable size..."
find . -maxdepth 1 -name "*.jpg" -exec sh -c 'convert -quality 80 -resize 3840x2160^ "${1}" "${1}"' _ {} \;
echo
echo "OK"

echo
echo "Uploading images to S3..."
aws --region eu-central-1 s3 cp . s3://assets.gotocy.com/property/$ID/ --recursive --exclude "*" --include "*.jpg"
echo
echo "OK"

echo
echo "We're done. Here is the list of paths for you:"
for f in *.jpg; do echo "property/$ID/$f"; done
echo