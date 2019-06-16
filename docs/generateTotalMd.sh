#!/bin/bash

rm -fv total.md
touch total.md

for md_file in $(ls D_*.md); do
  cat ${md_file} >> total.md
  # Add empty page
  echo '  ' >> total.md
  echo '---' >> total.md
done


