#!/bin/sh

ID=$1

# Cleanup
echo
echo "Cleaning up..."
rm -rf $ID
mkdir $ID

echo
echo "cd to '${ID}'"
cd $ID

echo
echo "Fetching the images from S3..."
aws s3 cp s3://assets.gotocy.eu/property/$ID/ . --recursive --exclude "*" --include "*.jpg"
echo
echo "OK"

echo
echo "Cleaning up..."
rm MEDIUM*.jpg SMALL*.jpg THUMB*.jpg
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
echo
echo "OK"

echo
echo "Uploading images to S3..."
aws s3 cp MEDIUM/ s3://assets.gotocy.eu/property/$ID/MEDIUM/ --recursive --storage-class REDUCED_REDUNDANCY
aws s3 cp BIG/ s3://assets.gotocy.eu/property/$ID/BIG/ --recursive --storage-class REDUCED_REDUNDANCY
echo
echo "OK"