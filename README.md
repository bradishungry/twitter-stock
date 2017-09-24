ssh bkmeeder@proj-309-yt-1.cs.iastate.edu
Database host: mysql.cs.iastate.edu 
Schema: db309yt1 
User: dbu309yt1 
Pass:ZWI4NjRhMjdl

#Docker Instructions
##Install
Download here: https://www.docker.com/products/docker-toolbox
Open terminal and type
Setup: 
pip install tweepy 
pip install pandas 
pip install pymysql 
pip install ujson 
docker cp parse.py 22301f9d55ca:/twitterStockApp 
docker cp twitterStream.py 22301f9d55ca:/twitterStockApp
docker run -v /etc/localtime:/etc/localtime:ro -i -t -d 0e1a1df6ed54 
docker exec -i -t cdbd9f293853 /bin/bash 
docker exec -i -t cdbd9f293853 python twitterStockApp/twitterStream.py
docker commit 0c784e32c516 bmeeder22/python:latest

##Create new image
Create new repo on dockerhub
docker pull bmeeder22/
docker tag : docker.io/bmeeder22/
docker commit 22301f9d55ca bmeeder22/python

##Running python docker container
docker run -v /etc/localtime:/etc/localtime:ro -i -t -d 0e1a1df6ed54
tmux new -s python
docker exec -i -t 976f216927e7 python twitterStockApp/twitterStream.py > log.txt
tmux attach -t python

##View Logs
docker exec -i -t cdbd9f293853 nano twitterStockApp/log.txt

##Running apache docker container
docker run -p 80:80 -p 443:443 -d 4ed4277718b5
tmux new -s apache
docker exec -i -t cdbd9f293853
