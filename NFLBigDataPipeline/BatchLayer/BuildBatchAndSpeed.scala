import org.apache.spark.{SparkContext, SparkConf}

object BuildBatchAndSpeed{
  def main(args: Array[String]) {
    import sparkSession.sql

    /*
    Part 1: Aggregate Tracking Data
     */
    val eighteen = spark.table("wjsjr_tracking2018_raw")
    val nineteen = spark.table("wjsjr_tracking2019_raw")
    val twenty = spark.table("wjsjr_tracking2020_raw")

    val eightAndNine = eighteen.union(nineteen)
    val tracking = eightAndNine.union(twenty)

    /*
    PART 2: BREAK UP DATA FROM DATA LAKE INTO BATCH AND SPEED SETS
    */
    val distinctPlays = tracking.select(tracking("playId")).distinct
    val playSplit = distinctPlays.randomSplit(Array(0.9, 0.1))

    val batchPlays = playSplit(0).filter(col("playId").isNotNull).select("playId").map(f=>f.getInt(0)).collect.toSeq
    val speedPlays = playSplit(1).filter(col("playId").isNotNull).select("playId").map(f=>f.getInt(0)).collect.toSeq
    val batchTracking = tracking.filter(col("playId").isin(batchPlays:_*))
    val speedTracking = tracking.filter(col("playId").isin(speedPlays:_*))

    /*
    PART 3: Build batch view for serving layer
     */
    import org.apache.spark.sql.expressions.Window
    val games = spark.table("wjsjr_games_raw")
    val topSpeeds = batchTracking.groupBy("gameid").agg(max("s").alias("s"))
    val homeClean = games.withColumn("home", (split(col("hometeamabbr"), "\"").getItem(1)))
    val bothClean = homeClean.withColumn("away", (split(col("visitorteamabbr"), "\"").getItem(1))).drop("hometeamabbr", "visitorteamabbr")
    val games = bothClean.filter(col("season").isNotNull)
    val gamesAndSpeed = games.join(topSpeeds, topSpeeds("gameid") === games("gameid"), "inner").drop("gameid", "gametimeeastern", "gamedate")
    val HomeKey = gamesAndSpeed.withColumn("key", concat(col("home"), lit("$"), col("season"), lit("$"),  col("week"))).drop("season", "week", "home", "away")
    val AwayKey = gamesAndSpeed.withColumn("key", concat(col("away"), lit("$"), col("season"), lit("$"),  col("week"))).drop("season", "week", "home", "away")
    val BatchView = HomeKey.union(AwayKey)

    /*
    Part 4: Write batch view, and speed data set to Hive table / csv (not sure which I need yet)
     */


    import org.apache.spark.sql.SaveMode
    BatchView.write.mode(SaveMode.Overwrite).saveAsTable("wjsjr_trackingBatchView")
    speedTracking.write.mode(SaveMode.Overwrite).saveAsTable("wjsjr_trackingSpeed")

    val gamesAndSpeed = games.join(speedTracking, speedTracking("gameid") === games("gameid"), "inner").drop("gameid", "gametimeeastern", "gamedate")
    gamesAndSpeed.write.csv("hdfs:///wjsjr/NFLData/SpeedLayer/speedLayer.csv")
  }
}