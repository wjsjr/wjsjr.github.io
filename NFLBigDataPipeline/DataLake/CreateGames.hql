CREATE EXTERNAL TABLE IF NOT EXISTS wjsjr_games_raw (
 gameId INT,
 season SMALLINT,
 week TINYINT,
 gameDate STRING,
 gameTimeEastern STRING,
 homeTeamAbbr STRING,
 visitorTeamAbbr STRING
 )
 ROW FORMAT DELIMITED
 FIELDS TERMINATED BY ','
 STORED AS TEXTFILE
 LOCATION '/wjsjr/NFLData/games'
 TBLPROPERTIES ("skip.header.line.count"="1");
