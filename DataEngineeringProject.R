#This is a project I worked in my job at a risk advisory consulting firm. I created a script which queries a list of security 
#tickers from a SQL database, cleans up these tickers and maps them to price data provided by Quandl. The script creates a table
#containing daily price data from the last two years for each target security.


# install.packages(c("Quandl", "dplyr", "RODBC", "tidyverse", "reshape2", "parallel", "doParallel", "snow", "lubridate")) #USE THIS FIRST TIME 

library("Quandl")
library("RODBC")
library("tidyverse")
library("dplyr")
library("reshape2")
library("parallel")
library("doParallel")
library("snow")
library("lubridate")

APIKEY <<- ""
Quandl.api_key(APIKEY)
dbName <<- ""
userID <<- ""
password <<- ""
  
ImportQuery <- ""

channel<- odbcConnect (dbName, userID, password)
quandlFetch <- sqlQuery(channel, ImportQuery, errors = TRUE)

quandlFetch$ID_BB_SEC_NUM_DES <- as.character(quandlFetch$ID_BB_SEC_NUM_DES)
dirtyTickers <- quandlFetch$ID_BB_SEC_NUM_DES

quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, " US", "")

quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "1709583D", "VXX1")
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "1719109D", "HYAC") 
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "1719848D", "EDR")
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "9876345D", "NA") 
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "9876456D", "ET")
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "9966699D", "NA") 
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "9977553D", "CCO")
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "9992222D", "NA") 
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "9999945D", "NA") 
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "DWDP", 'NA') 


quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "/U", ".WS" )
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "/", ".")

quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "-R-W", ".R")
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "-", ".")

quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "ACDVF", "ACNAQ") 
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "FSNNQ", "FSNN") 
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "SHLDQ", "SHLD")
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "APHB", "ARMP")
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "QWST", "PQUEQ")
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "TBRG", "THBR")

quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "PDVW", 'ATEX')
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "WTW", 'WW')
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "UBNT", 'UI')
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "TMK", 'GL')
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "IPIC", 'IPICQ')
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "BABY", 'NTUS')
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "LNGG", 'LINE')
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "KLXIV", 'KLXI') 
quandlFetch$ID_BB_SEC_NUM_DES<- append(quandlFetch$ID_BB_SEC_NUM_DES, "HKRSQ", after = length(quandlFetch$ID_BB_SEC_NUM_DES))
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "ABX", "GOLD") #Barrick Gold
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "AHS", "AMN") #AMN Healthcare
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "AMGP", "AM") #Antero Midstrem
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "BBEP", "BBEPQ") #Breitburn Energy PArtners
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "BWS", "CAL") #Brown Shoe Company rebranded to Caleres 
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "CEDC", "CEDCQ") #Central European Distribution Corp
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "CJES", "CJ") #C&J Energy Services
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "COH", "TPR") #Coach Rebranded to Tapestry Inc
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "COSI", "COSIQ") #Cosi Inc
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "ERTS", "EA") #Electronic Arts 
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "FRNT", "FRNTQ") #Frontier Airlines
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "FTE", "FTNW") #FTE Newtorks
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "ETE", "ET") #Energy Transfer Equity
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "KORS", "CPRI") #Michael Kors Rebranded as Capri Holdings
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "LNCO", "LNCOQQ") #LinnCo 1
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "LNCOQQ", "LNCOQ") #LinnCo 2
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "MOT", "MOTS") #Motus GI Holdings
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "MPEL", "MLCO") #Melco Resorts
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "NAB", "NABZY") #National Australia Bank

quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "NYB", "NYCB") #New York Community Bancorp
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "ONNN", "ON") #ON Semiconductor Corp
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "PCLN", "BKNG") #Booking Holdings, Parent of Priceline
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "PCU", "SCCO") #Southern Copper Corp
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "QSII", "NXGN") #NextGen Healthcare
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "RIMM", "PRIM") #Primoris Services
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "SYRG", "SRC") #SRC Energy
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "TCB", "TCF") #TCF Financial
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "TEGP", "TGE") #Tallgrass Energy

quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "THQI", "THQIQ") #THQ Inc
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "VNR", "VNRRQ") #Vanguard Natural Resources
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "VRX", "VRX1") #Valeant Pharmaceuticals
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "WAG", "TGE") #Walgreens Boots Alliance
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "WFMI", "WFM") #Whole Foods
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "WGP", "WES") #Western Midstream Partners 
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "WYN", "WYND") #Wyndham Worldwide
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "ZQK", "ZQKSQ") #Quiksilver
quandlFetch$ID_BB_SEC_NUM_DES <- str_replace_all(quandlFetch$ID_BB_SEC_NUM_DES, "MW", "TLRD") #Tailored Brands; Parent Company of Mens Warehouse
quandlFetch$ID_BB_SEC_NUM_DES <- quandlFetch$ID_BB_SEC_NUM_DES[quandlFetch$ID_BB_SEC_NUM_DES != "MW"]
quandlFetch$ID_BB_SEC_NUM_DES <- append(quandlFetch$ID_BB_SEC_NUM_DES, "TLRD", after = length(quandlFetch$ID_BB_SEC_NUM_DES))

tickers <- unique(quandlFetch$ID_BB_SEC_NUM_DES)

StartDate <- format((Sys.Date() -1) %m-% months(24), "%Y/%m/%d") #Sets start date of query to 2 years and 1 day before current date. Uses Lubridate approach which accounts for Leap Years
EndDate <- format(Sys.Date() - 1, "%Y/%m/%d") #Sets end date of query to 1 day before current date. 

#Parallel 

startTimeParallel <- Sys.time() #Starts Timing Parallel Process
cl <- makeCluster(detectCores(logical = FALSE) - 1) #Creates "cluster" of each available physical core. Saves 1 core for safety (This is best practice according to internet)

registerDoParallel(cl)#Tells R to use the cluster of available cores

#Breaks tickers up into chunks which different cores can process simultaneously 
StartDate <- as.Date.character(StartDate)
EndDate <- as.Date.character(EndDate) 

nTickers <- length(tickers) #Number of Tickers
dateDiff <- 728
maxChunk <- 1000000 / (nTickers * dateDiff)#Quandl.datatable function can only output 1 million rows from each query, throws error if this is violated. Number of rows equals number of tickers * days in date range, so this calculates the number of tickers which can be used in each fetch without output exceeding 1 million rows
optimalChunkSize <- floor((length(tickers))/(detectCores(logical = FALSE) - 1))#this is most efficient way to chunk tickers.
if (optimalChunkSize > maxChunk) { #Caps chunk size to keep quandl.datatable from throwing error
  chunkSize <- maxChunk
} else {
  chunkSize <-optimalChunkSize
}
tickerChunks <- split(tickers, ceiling(seq_along(tickers)/chunkSize))#breaks Tickers up into chunks which cores will process

parallelOutput <- list()#Accumulator 
parallelOutput <- foreach(i = 1:length(tickerChunks)) %dopar% {
  library("Quandl")
  APIKEY <<- "shc295zGxn7d9nUUSfg3" 
  Quandl.api_key(APIKEY)
  Quandl.datatable('SHARADAR/SEP', date.gte = StartDate, date.lte = EndDate, ticker = factor(tickerChunks[[i]]), paginate = TRUE)#Iterates through each chunk, plugs chunk into quandl.datatable function and stores output in parallelOutput list. This is where parallel cores save time. Instead of looping sequentially, each core can run simultaneously. So if you have 3 cores, index 1, 2 and 3 will run at same time. Then index 4, 5, 6 ...
}

stopCluster(cl) #turns R back into sequential mode
parallelOutput <- bind_rows(parallelOutput) #Creates pivot table from data
parallelOutput <- select(parallelOutput, "ticker", "date", "close")
endTimeParallel <- Sys.time() 
endTimeParallel - startTimeParallel #Parallel Test

droppedTickersv = setdiff(tickers, parallelOutput$ticker)
droppedTickers = data.frame(droppedTickersv)

noPull <- inner_join(droppedTickers, quandlFetch, by = c("droppedTickersv" = "ID_BB_SEC_NUM_DES"))
SQLOutput <- inner_join(parallelOutput, quandlFetch, by = c("ticker" = "ID_BB_SEC_NUM_DES"))

noPull <- noPull[-c(1)]
noPull$ID = NA
noPull$SysDate = NA

SQLdbFileName = ""
noPullFileName = ""

write.table(
  SQLOutput,
  file = paste0(dbFileName, "_DBInsert.txt"),
  quote = FALSE,
  sep = ",",
  row.names = FALSE,
  col.names = FALSE,
  append = FALSE
)

write.table(
  noPull,
  file = paste0(noPullFileName, "_DBInsert.txt"),
  quote = FALSE,
  sep = ",",
  row.names = FALSE,
  col.names = FALSE,
  append = FALSE
)

channel<- odbcConnect (dbName, userID, password)
sqlQuery(
  channel,
  paste(
    "BULK
          INSERT ",
    db,
    ".dbo.tempQuandlres",
    dbFileName,
    "_DBInsert.txt'
          WITH ( FIELDTERMINATOR = ',',ROWTERMINATOR = '\\n')",
    sep = ""
  )
)
sqlQuery(
  channel,
  paste(
    "BULK
          INSERT ",
    db,
    ".dbo.tempQuandlres",
    dbFileName,
    "_DBInsert.txt'
          WITH ( FIELDTERMINATOR = ',',ROWTERMINATOR = '\\n')",
    sep = ""
  )
)
Sys.sleep(3)

sqlQuery(
  channel,
  paste(
    "BULK
          INSERT ",
    db,
    ".dbo.QuandlNoPull",
    noPullFileName,
    "_DBInsert.txt'
          WITH ( FIELDTERMINATOR = ',',ROWTERMINATOR = '\\n')",
    sep = ""
  )
)



