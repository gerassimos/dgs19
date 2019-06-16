#!/bin/bash

rm -v web_*.html
echo 'Table of Contents' > index.html
echo '<br>' >> index.html

for md_file in $(ls D_*.md); do

  html_file_name=$(echo web_"${md_file}" | sed 's/.md$/.html/')
  echo ${html_file_name}
  cp template.html ${html_file_name}
  sed -i "s/MD_FILE/${md_file}/" ${html_file_name}

  echo "<a href=\"${html_file_name}\">$(echo "${md_file}" | sed 's/.md$//')</a>" >> index.html
  echo '<br>' >> index.html

done






