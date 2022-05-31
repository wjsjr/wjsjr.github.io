This was final capstone for Big Data Application Architecture course I took with Professor Michael Spertus (https://cs.uchicago.edu/people/michael-spertus/)
while at UChicago. I built a Big data pipeline serving a simple Javascript client which allowed users to query on-field player speeds from a massive NFL tracking dataset.

This pipeline was built using a "Lambda Architecture" (https://databricks.com/glossary/lambda-architecture), with Apache Spark + Hive (Batch Layer), 
Kafka (Streaming Layer), Hbase (Serving Layer), serving a Javascript client sitting on top of an AWS Cloud Native backend (Cloud deploy + EC2).

We deployed our data pipelines on an EMR Cluster, I've done my best to include all the scripts and configurations used for deployment

Abstract
I built an application that allows users to analyze NFL data. My data comes from the "NFL Big Data Bowl 2022" (https://www.kaggle.com/c/nfl-big-data-bowl-2022), and includes detailed, frame-by-frame tracking data for special teams players on every play of the 2018, 2019 and 2020 seasons. Users can enter in a team abbreviation (ex. CHI or DEN), Year and Week # and view the top speed (in Yards/Second) any player ran during the selected game. To accomplish this, I implemented Nathan Marz' Lambda Architecture as described in class. I will describe my architecture and implementation details below


Datalake / Dataset

To build my datalake, I first downloaded the dataset from Kaggle to my local machine using the Kaggle API and uploaded it to our EMR cluster using scp. This dataset is 5GB large so this was a slow process. A more optimal solution would clearly be install the necessary dependencies to invoke the Kaggle API directly from our cluster. I then manually added these files into HDFS (hdfs dfs -put /wjsjr/NFLData/...) The data is provided in CSV format, and I left it in its original format inside of the data lake to maximize rawness. 

To allow other components of my application to acces the datalake, I created external hive tables sitting on top of the lake, which provide easy read only access without risk of corruption (again maximizing rawness.) The hql code to build these tables can be found in the "Data Lake" sub directory of my submission. I only created tables to access the data needed for my application which was the tracking data, and "games data" (which includes metadata about each game)

Hive Tables: wjsjr_tracking2018_raw, wjsjr_tracking2019_raw, wjsjr_tracking2020_raw, wjsjr_games_raw


Batch Layer

I wrote a scala spark job that split my dataset into two subsets, one of which formed my batch layer and the other of which I set aside for my speed layer. This code can be found in the "Batch Layer" directory of my submission (called BuildBatchAndSpeed). This spark script first concatenates all tracking data into a single table. It then counts each distinct playID number from the track data, and randomly splits plays into two buckets (90% of the plays go to batch, remaining 10% goto speed). I then split up the tracking data table based on these buckets: any frame corresponding to a play in the speed bucket is set aside, while the remaining plays are kept in the "batch table"

At this point, I calculated the max speed of any player during each game in the "batch table". I then took this calculated data, and joined it with the game metadata from the games dataset in the data lake. I used this metadata to build two seperate keys for each game, one key storing the home team and the other storing the away team that played in the game. I then created a new table with two rows for each game, one of which is keyed by the home team, the other being keyed by the away team. 

The keys were built as follows: teamAbbreviation$year$week

My spark script then saves my calculated batch views to hive, and saves my raw speed layer data to HDFS (hdfs:///wjsjr/NFLData/SpeedLayer/speedLayer.csv)

Hive Tables: wjsjr_trackingBatchView

Serving Layer

To serve these batch views to my applications front end, I used hbase as illustrated in class. The code I used to create my serving table, and write from hive to the serving table, can be found in the Serving Layer sub director.

Each row in my table contains a key (as described above) and the top speed that occured during the game that key maps to. 

Hbase table: wjsjr_batchServing


Speed Layer

As described above, I held back 10% of my tracking data to simulate a real time data feed. This speed layer uses Kafka Topics and Spark Streaming, to stream data into the application. All code described below can be found in the Speed Layer directory of my submission

The next step of building my speed layer was creating a Kafka Topic. I created a topic called "wjsjr_speed_layer".


To make the process of reading in from HDFS a little easier, I concatenated all of the speed layer CSV files that my BuildBatchAndSpeed Spark job created into one single file (stored in hdfs:///wjsjr/SpeedLayerMerged/). 

To protect our batch layer from potential corruption, I created a seperate hbase table (wjsjr_speedServing) to house the data generated by the streaming layer.

i) Kafka Producer

The first component of my Speed Layer is a Kafka Produdcer (code found in the Kafka Producer sub directory). My Kafka producer was built from the "Kafka Weather" Archetype. It reads in data from the speedLayer.csv file and pushes it to the Kafka Topic. My produer is configured to send a frame of data every 15 seconds.

Building this producer was an interesting challenge, because my speed layer dataset is too large to be stored in memory at a given time. I was able to configure each scheduled "Task" to read in only the next line from the file by having all tasks access a static BufferedReader linked to the file in HDFS. 

ii) Kafka Consumer / Hbase writer.

The second component of my speed layer is a Kafka consumer (see Kafka consumer directory). This component monitors a Kafka topic, and then transmits new data from that topic into the speed layer.

I had a ton of problems deserializing data from the Kafka topic and eventually opted to 
follow a novel approach which wasn't covered in class. I believe my approach is still scalable, and in some ways its arguably even more scalable then our approach from class. 

I configured my kafka consumer to read from kafka, and then write data directly into HDFS (can be found in following path when speed layer is running: hdfs:///wjsjr/SpeedLayerOutput)

I then have a third component of my system which I call an "hbase writer". This component reads the streamed data from HDFS, transforms it and writes it to Hbase. I first query hbase to check whether a row exists for the key. If the key doesnt exist, or if the row contains a speed lower than the speed in current frame, I write the new speed to hbase.

My hbase writer job then deletes all of the files it read from in HDFS which prevents hdfs from eventually running out of space. I wrote a bash script that runs this job every minute (runSpeedLayer.sh)

I think my speed layer architecture is quite scalable. By having seperate spark jobs monitoring the kafka topic and writing to hbase, I believe my system can handle a higher velocity stream of data than the system provided in class. It could also be very easily modified to save the HDFS files in a "speed lake", which could periodically be added to the data lake.

I also think that having seperate hbase tables for batch and speed provides a higher degree of safety and ensures that we can easily recover from failures due to speed layer corruption.


The biggest flaw in my speed layer is clearly the fact that I never merged it into the data lake. Although the speed serving layer is clearly scalable on its own, this prevents the data lake from being a single source of ground truth.

If I had more time, I was planning on modifiying my system to have the hbase writer rename files that it had read (so that they wouldnt be read again) instead of deleting them. I would then have a cron job which moved the used speed files into the data lake and recomputed batch views. I would then clear out my speed hbase table.

Kafka Topic: "wjsjr_speed_layer"
To run Kafka Producer (from EMR cluster terminal): yarn jar /home/hadoop/wjsjr/src/target/uber-Kafka-Producer-0.0.1-SNAPSHOT.jar org.wjsjr.KafkaProducer

To run Kafka Consumer:
spark-submit --master local[2] --class StreamPlays /home/hadoop/wjsjr/src/target/uber-KafkaConsumer-1.0-SNAPSHOT.jar 

To run hbase writer (needs consumer and producer running to work properly):
./home/hadoop/wjsjr/runSpeedLayer.sh)



Frontend / Deployment

I built a node / express app, which takes user input from an HTML form and queries the serving layer. I make seperate calls to the batch and speed tables in the serving layer and return the highest speed found in either table. It then uses mustache to render the query results (i.e. top speed for given game) and displays them to the user. I included some simple error handling (dealing with missing data in hbase due to NFL bye weeks, and handling invalid form data).

I deployed the data scalably using code deploy and a load balancer. I've included all of my deployment configs, and front end src code (appspec.yaml, shell scripts...) in the submission under the Front End / Deployment sub directory.



How to run speed layer (I recommend using 3 seperate terminals to monitor logs)

yarn jar /home/hadoop/wjsjr/src/target/uber-Kafka-Producer-0.0.1-SNAPSHOT.jar org.wjsjr.KafkaProducer

spark-submit --master local[2] --class StreamPlays /home/hadoop/wjsjr/src/target/uber-KafkaConsumer-1.0-SNAPSHOT.jar 

./home/hadoop/wjsjr/runSpeedLayer.sh



