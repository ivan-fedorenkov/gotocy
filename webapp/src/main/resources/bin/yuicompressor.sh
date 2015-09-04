#!/bin/bash

# CSS compression commands
# cd /home/killer/Projects/gotocy-dev/webapp/src/main/resources/public/assets
## application wide
# yui.sh -o css/application.min.css fonts/font-awesome.min.css bootstrap/css/bootstrap.min.css css/bootstrap-select.min.css css/magnific-popup.css css/owl.carousel.css css/jquery.slider.min.css css/style.css

# JS compression commands
# cd /home/killer/Projects/gotocy-dev/webapp/src/main/resources/public/assets
## app-wide
# yui.sh -f -o js/application.min.js js/jquery-2.1.0.min.js js/jquery-migrate-1.2.1.min.js bootstrap/js/bootstrap.min.js js/smoothscroll.js js/owl.carousel.min.js js/bootstrap-select.min.js js/jquery.validate.min.js js/jquery.placeholder.js js/icheck.min.js js/retina-1.1.0.min.js js/jquery.raty.min.js js/jquery.magnific-popup.min.js js/jshashtable-2.1_src.js js/jquery.numberformatter-1.2.3.js js/tmpl.js js/jquery.dependClass-0.1.js js/draggable-0.1.js js/jquery.slider.min.js js/jquery.fitvids.js js/imagesloaded.pkgd.min.js js/masonry.pkgd.min.js js/scrollReveal.min.js js/custom.js
## maps
# yui.sh -f -o js/application-maps.min.js js/markerwithlabel_packed.js js/markerclusterer_packed.js js/infobox.js js/custom-map.js
## pano
# yui.sh -f -o js/application-pano.min.js pano2vr/pano2vr_player.js pano2vr/skin.js

# the path to your java program to use
JAVA=java

# the location where YUI Compressor is installed
YUI=/path/to/compressor/yuicompressor-*.jar

# the maximum number of columns in the output files
COLS=4000

###############################################################################

outfile=
minimize=0
silent=

# read the options
while [[ "$1" == -* ]]; do
  if [ "$1" = -f ]; then
    minimize=1
    shift
  elif [ "$1" = -s ]; then
    silent=1
    shift
  elif [ "$1" = -o ]; then
    shift
    outfile=$1
    shift
  else
    echo Unknown option: $1
    exit 1
  fi
done

if [ $# -lt 1 ]
then
  echo '
Usage: yuicompress [-o out] [-f] [-s] file1 file2 ...

Uses YUI Compressor to minimize multiple JavaScript or CSS files and
concatenate them into a single file. The files are concatenated in the
order they are received as parameters. The type of the input file is
determined by its extension: .js for JavaScript and .css for CSS.
You cannot mix .js and .css files in the same command.

The output file is specified using -o followed by its name. If no
output file is specified, the minimized content is printed to the
standard output.

You can force the processing using -f. Without -f, the minimization
is performed only if at least one input file is newer than the output
file. If no output file is specified with -o, then -f is implicitly
assumed.

Silent mode can be specified with the -s option. In this case all
progress output will be suppressed.

'
  exit 1
fi

if [ -z "$outfile" -o ! -e "$outfile" ]; then
  minimize=1
fi

# if not forced, minimize only if there is any newer input file
if [ $minimize != 1 ]; then
  for file in "$@"; do
    if [ "$file" -nt "$outfile" ]; then
      minimize=1
      break
    fi
  done
fi

# process one file at a time
if [ $minimize = 1 ]; then
  if [ -n "$outfile" ]; then
    rm -f "$outfile"
    test -z $silent && echo Output: $outfile
    for file in "$@"; do
      test -z $silent && echo Minimize "$file"... >&2
      "$JAVA" -jar $YUI "$file" --line-break $COLS >>"$outfile"
    done
  else
    test -z $silent && echo Output: standard output
    for file in "$@"; do
      test -z $silent && echo Minimize "$file"... >&2
      "$JAVA" -jar $YUI "$file" --line-break $COLS
    done
  fi
else
  test -z $silent && echo No newer file. Skipping... >&2
fi