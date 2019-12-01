"""
Built a custom web-scraper for an NYC-based brand consulting firm. Scraper searches 10 leading news sites for a list of terms relating to Giving Tuesday. 

""""



from bs4 import BeautifulSoup
import requests 
import re
import newspaper
import pandas as pd
import numpy as np
import datetime
import random
import time

newYorkPost = "https://nypost.com/" 
dailyMail = "https://www.dailymail.co.uk/ushome"
abc = "https://abcnews.go.com/"
chron = "https://www.chron.com/" 
usaToday = "https://www.usatoday.com/"  
cnn = "https://www.cnn.com/" 
classy = "https://www.classy.org/blog/"
nbc = "https://www.nbcnews.com/"
fox = 'https://www.foxnews.com/'

term1 = "GivingTuesday"
term2 = "Giving Tuesday"
term3 = "#GivingTuesday"
term4 = "GivingTuesday Match"
term5 = "Giving Tuesday Match"
term6  = "#GivingTuesday Match"
term7 = "GivingTuesday Campaign"
term8 = "Giving Tuesday Campaign"
term9 = "#GivingTuesday Campaign"
term10 = "Match Donation"
term11 = "Match Challenge"
term12 = "Matching Challenge"
term13 = "Matching Gifts"
term14 = "Fundraising Match"
term15 = "challenge grant"
term15 = "Challenge Grant"  

urls = [newYorkPost, chron, abc, usaToday, cnn, classy, fox, dailyMail, nbc]
terms = {term1:[0], term2:[0], term3:[0], term4:[0], term5:[0], term6:[0], term7:[0], term8:[0], term9:[0], term10:[0],term11:[0], term12:[0], term13:[0], term14:[0]}
random.seed(1)
scrapeCount = 0

for url in urls:
    r = requests.get(url)
    waitTime = 0
    soup = BeautifulSoup(r.content,'html.parser')
    if (url == newYorkPost):
        nypURLs = []
        nypSearched = 0
        nypArticles =  newspaper.build(url)
        for a in soup.findAll('a', attrs = {'class': 'story-thumbnail-wrapper'}, href = True):
            if a.text:
                nypURLs.append(a['href'])
        nypClean = []
        [nypClean.append(x) for x in nypURLs if x not in nypClean]
        for nypURL in nypClean:
            waitTime = random.randint(1,5)
            print("Article Searched:" + nypURL)
            print("total: " + str (scrapeCount + 1))
            scrapeCount += 1
            time.sleep(waitTime)
            article = newspaper.Article(nypURL, language = 'en')
            try:
                article.download()
                article.parse()
                for term in terms:
                    if (term in article.text or term in article.title):
                        terms[term][0] += 1
                        terms[term].append(article.url)
            except:
                print("error" + str (article.url))
                if(term in article.url):
                    terms[term][0] += 1
                    terms[term].append(article.url)
        print ("Total " + url + ": " + str(nypSearched))
    if (url == usaToday):
        usaURLs = []
        searched = 0
        for link in soup.findAll( 'a', attrs = {'href': re.compile("/story/")}):
            if link.text:
                usaURLs.append(link['href'])     
        usaClean = []
        [usaClean.append(x) for x in usaURLs if x not in usaClean]          
        for usaURL in usaClean:
            waitTime = random.randint(1,5)
            print("Article Searched:" + article.url)
            print("total: " + str (scrapeCount + 1))
            scrapeCount += 1
            searched += 0
            time.sleep(waitTime)
            article = newspaper.Article(usaURL, language = 'en')
            try:
                article.download()
                article.parse()
                for term in terms:
                    if (term in article.text or term in article.title):
                        terms[term][0] += 1
                        terms[term].append(article.url)
            except:
                print("error" + str (article.url))
                if(term in article.url):
                    terms[term][0] += 1
                    terms[term].append(article.url)
        print ("Total " + url + ": " + str(searched))
    if (url == chron):
        chronURLs = []  
        searched = 0
        for link in soup.findAll( 'a', attrs = {'href': re.compile("/news/")}):
            if link.text:
                chronURLs.append(link['href'])
        chronClean = [] 
        [chronClean.append(x) for x in chronURLs if x not in chronClean]          
        for chronURL in chronClean:
            waitTime = random.randint(1,5)
            scrapeCount += 1
            time.sleep(waitTime)
            searched += 0
            print("Article Searched:" + article.url)
            print("total: "  + str (scrapeCount + 1))
            try:
                scrapeCount += 1
                article.download()
                article.parse()
                for term in terms:
                    if (term in article.text or term in article.title):
                        terms[term][0] += 1
                        terms[term].append(article.url)
            except:
                print("error" + str (article.url))
                if(term in article.url):
                    terms[term][0] += 1
                    terms[term].append(article.url)
        print ("Total " + url + ": " + str(searched))
    if (url == classy):
        classURLs = []
        searched = 0 
        for link in soup.findAll( 'a', attrs = {'href': re.compile("/blog/")}):
                classURLs.append(link['href'])
        classClean = []
        [classClean.append(x) for x in classURLs if x not in classClean]          
        for classURL in classClean:
            waitTime = random.randint(1,5)
            print("Article Searched:" + article.url)
            print("total: " + str (scrapeCount + 1))
            searched += 0
            scrapeCount += 1
            time.sleep(waitTime)
            article = newspaper.Article(classURL, language = 'en')
            try:
                article.download()
                article.parse()
                for term in terms:
                    if (term in article.text or term in article.title):
                        terms[term][0] += 1
                        terms[term].append(article.url)
            except:
                print("error")
                if(term in article.url):
                    terms[term][0] += 1
                    terms[term].append(article.url)
        print ("Total " + url + ": " + str(searched))
    else:
        paper = newspaper.build(url)
        counter = 0
        searched = 0
        print(url)
        if (counter <100): 
            for article in paper.articles:
                counter += 1
                searched += 1
                print("Article Searched:" + article.url)
                print("total: " + str (scrapeCount + 1))
                waitTime = random.randint(1,5)
                scrapeCount += 1
                time.sleep(waitTime)
                for term in terms:
                    try:
                        article.download()
                        article.parse()
                        if (term in article.text or term in article.title):
                            terms[term][0] += 1
                            terms[term].append(article.url)
                    except: 
                        print("error" + str (article.url))
                        if(term in article.url):
                            terms[term][0] += 1
                            terms[term].append(article.url)
        print ("Total " + url + ": " + str(searched))
outputdf = pd.DataFrame({ key:pd.Series(value) for key, value in terms.items() })
for term in terms:
    outputdf.drop_duplicates(subset = term)
outputdf = outputdf.replace(np.nan, '', regex = True)
print(outputdf)
print("Scrape Finished. Articles Searched:" + str(scrapeCount))

todayDate = datetime.date.today()
todayString = todayDate.strftime("%m-%d-%Y")
filepath = #Insert filepath name here
file = filepath + "/scrapeOutput-" + todayString + ".xlsx"
outputdf.to_excel(file, sheet_name = 'WebScrape')

