CREATE EXTERNAL TABLE IF NOT EXISTS wjsjr_tracking2018_raw(
    time STRING,
    x DECIMAL,
    y DECIMAL,
    s DECIMAL,
    a DECIMAL,
    dis DECIMAL,
    o DECIMAL,
    dir DECIMAL,
    event STRING,
    nflid INT,
    displayName STRING,
    jerseyNumber TINYINT,
    position STRING,
    team STRING,
    frameId INT,
    gameId INT,
    playId INT,
    playDirection STRING

 )
 ROW FORMAT DELIMITED
 FIELDS TERMINATED BY ','
 STORED AS TEXTFILE
 LOCATION '/wjsjr/NFLData/tracking2018'
 TBLPROPERTIES ("skip.header.line.count"="1");

CREATE EXTERNAL TABLE IF NOT EXISTS wjsjr_tracking2019_raw(
    time STRING,
    x DECIMAL,
    y DECIMAL,
    s DECIMAL,
    a DECIMAL,
    dis DECIMAL,
    o DECIMAL,
    dir DECIMAL,
    event STRING,
    nflid INT,
    displayName STRING,
    jerseyNumber TINYINT,
    position STRING,
    team STRING,
    frameId INT,
    gameId INT,
    playId INT,
    playDirection STRING

 )
 ROW FORMAT DELIMITED
 FIELDS TERMINATED BY ','
 STORED AS TEXTFILE
 LOCATION '/wjsjr/NFLData/tracking2019'
 TBLPROPERTIES ("skip.header.line.count"="1");


 CREATE EXTERNAL TABLE IF NOT EXISTS wjsjr_tracking2020_raw(
     time STRING,
     x DECIMAL,
     y DECIMAL,
     s DECIMAL,
     a DECIMAL,
     dis DECIMAL,
     o DECIMAL,
     dir DECIMAL,
     event STRING,
     nflid INT,
     displayName STRING,
     jerseyNumber TINYINT,
     position STRING,
     team STRING,
     frameId INT,
     gameId INT,
     playId INT,
     playDirection STRING

  )
  ROW FORMAT DELIMITED
  FIELDS TERMINATED BY ','
  STORED AS TEXTFILE
  LOCATION '/wjsjr/NFLData/tracking2020'
  TBLPROPERTIES ("skip.header.line.count"="1");


