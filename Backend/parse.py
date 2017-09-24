from collections import Counter
import pymysql.cursors
import datetime as dt
import ujson as json
import pandas as pd
import time


tweets_data_path = 'toParse.txt'
hashtags = []

def loadJSON():
    tweets_data = []
    tweetsDataframe = pd.DataFrame()
    tweets_file = open(tweets_data_path, "r")
    for line in tweets_file:
        try:
            tweet = json.loads(line)
            tweets_data.append(tweet)
        except:
            continue
    tweets_file.close()
    tweetsDataframe['hashtags'] = map(lambda tweet: tweet['entities']['hashtags'], tweets_data)
    return tweetsDataframe

def getHashTagsFromJSON(tweetsDataframe):
    global hashtags
    for tweet in tweetsDataframe['hashtags']:
        for hashlist in tweet:
            tag = hashlist['text']
            tag = tag.lower()
            if '\u0' not in tag and '\ub' not in tag:
                hashtags.append(tag)

    hashtags = dict(Counter(hashtags))

def importToMysql():
    connection = pymysql.connect(host='mysql.cs.iastate.edu',
                                 user='dbu309yt1',
                                 password='ZWI4NjRhMjdl',
                                 db='db309yt1',
                                 charset='utf8mb4',
                                 cursorclass=pymysql.cursors.DictCursor)

    hour = dt.datetime.now().hour
    day = dt.date.today().day
    month = dt.date.today().month
    year = dt.date.today().year
    date = "%s-%s-%s" % (year, month, day)
    for text,count in hashtags.iteritems():
        if not is_ascii(text):
            continue
        with connection.cursor() as cursor:
            sql = "INSERT INTO `hashtags` (`text`, `count`, `hour`, `date`) VALUES (%s, %s, %s, %s)"
            cursor.execute(sql, (text, count, hour, date))
        connection.commit()

def parseTwitterStream():
    start = int(round(time.time() * 1000))
    print "Loading JSON..."
    tweetsDataFrame = loadJSON()
    getHashTagsFromJSON(tweetsDataFrame)
    print "Adding data to DB..."
    importToMysql()
    print "Parse completed for hour %s" % dt.datetime.now().hour
    end = int(round(time.time() * 1000))
    print "Completed in %s milliseconds" % (end-start)

def sqlTest():
    connection = pymysql.connect(host='mysql.cs.iastate.edu',
                                 user='dbu309yt1',
                                 password='ZWI4NjRhMjdl',
                                 db='db309yt1',
                                 charset='utf8mb4',
                                 cursorclass=pymysql.cursors.DictCursor)

    with connection.cursor() as cursor:
        sql = "INSERT INTO `hashtags_test` (`text`, `count`, `hour`, `date`) VALUES (\"test\", 1, 12, \"2017-1-08\")"
        cursor.execute(sql)
    connection.commit()

def is_ascii(s):
    return all(ord(c) < 128 for c in s)