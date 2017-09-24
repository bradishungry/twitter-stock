from tweepy.streaming import StreamListener
from parse import parseTwitterStream
from multiprocessing import Pool
from tweepy import OAuthHandler
from shutil import copyfile
from tweepy import Stream
import datetime as dt

access_token = "2257213718-M50SSW9hBP59kT5PYo6l0iYjVwJs9TA2DywAWqr"
access_token_secret = "5ACxQkTVHt2OpcQlZnkYeLQXdoawQTrIeQhZwSXNVcKXp"
consumer_key = "7iOGDdqfHsJjyFkb6IktFY9Ph"
consumer_secret = "s59KhvMLvRlufYPpqkgHC953JpUo7GafBJjYLNtXPEwmcEWXl7"

MinuteToParse = 0
currentHour = 0


class StdOutListener(StreamListener):

    def on_data(self, data):
        global MinuteToParse, currentHour
        if dt.datetime.now().minute == MinuteToParse and dt.datetime.now().hour != currentHour:
            self.reset()

        if 'hashtags":[{"text' in data:
            f.write(data)
        return True

    def on_error(self, status):
        print status

    def reset(self):
        copyfile("stream.txt", "toParse.txt")
        f.seek(0)
        f.truncate()
        self.asyncParse()

    def asyncParse(self):
        global currentHour
        currentHour = dt.datetime.now().hour
        pool = Pool(processes=1)
        pool.apply_async(parseTwitterStream)

listener = StdOutListener()
auth = OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
stream = Stream(auth, listener)
f = open('stream.txt', 'w')

stream.filter(locations=[-123.15,32.51,-68.4,47.31])
