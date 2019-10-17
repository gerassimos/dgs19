#!/bin/bash

# start a web server that is used to generate(serve) the html pages  
docker run -d --name dgst19 -p 8069:80 -v $(pwd):/usr/local/apache2/htdocs/docs httpd:2.4

rm -rfv pdf
mkdir pdf
chmod 777 pdf

for md_file in $(ls D_*.md); do

  html_file_name=$(echo web_"${md_file}" | sed 's/.md$/.html/')
  pdf_file_name=$(echo "${md_file}" | sed 's/.md$/.pdf/')
  docker run --rm -it --privileged --net=host -v `pwd`:/slides astefanutti/decktape:2.9.2  --size 1024x769 remark \
  http://localhost:8069/docs/${html_file_name} pdf/${pdf_file_name}
done






