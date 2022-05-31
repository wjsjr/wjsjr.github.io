import org.apache.spark.{SparkConf, SparkContext}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{ConnectionFactory, Get, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.dsl.expressions.{DslExpression, StringToAttributeConversionHelper}
import org.apache.spark.sql.functions._
import scala.sys.process._
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


import scala.reflect.internal.util.TableDef.Column

object HbaseWriter {
  val hbaseConf: Configuration = HBaseConfiguration.create()
  hbaseConf.set("hbase.zookeeper.property.clientPort", "2181")
  hbaseConf.set("hbase.zookeeper.quorum", "localhost")
  val hbaseConnection = ConnectionFactory.createConnection(hbaseConf)
  val table = hbaseConnection.getTable(TableName.valueOf("wjsjr_speedServing"))
  def hbaseHelper(homePut: Put, awayPut: Put, speed: String) ={
    homePut.addColumn(Bytes.toBytes("speed"), Bytes.toBytes("s"), Bytes.toBytes(speed))
    awayPut.addColumn(Bytes.toBytes("speed"), Bytes.toBytes("s"), Bytes.toBytes(speed))
    table.put(homePut)
    table.put(awayPut)

  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("HiveWriter").setMaster("local[2]");
    // Create a spark session
    val spark = SparkSession
      .builder()
      .appName("Build SD Table")
      .config("spark.hadoop.metastore.catalog.default", "hive")
      .enableHiveSupport()
      .getOrCreate()
    val df = spark.read.textFile("hdfs:///wjsjr/SpeedLayerOutput/*")
    val dfSplit = df.select(split(col("value"), ",").as("valueArray")).drop("value")
    val parsed = dfSplit.select(expr("valueArray[0]").alias("season"), expr("valueArray[1]").alias("week"), expr("valueArray[2]").alias("home"), expr("valueArray[3]").alias("away"), expr("valueArray[7]").alias("speed"))
    val HomeKey = parsed.withColumn("homeKey", concat(col("home"), lit("$"), col("season"), lit("$"),  col("week")))
    val withKeys = HomeKey.withColumn("awaykey", concat(col("away"), lit("$"), col("season"), lit("$"),  col("week"))).drop("season", "week", "home", "away")
    withKeys.foreach(row => {
      val speed = row.mkString(",").split(",")(0)
      val homeKey = row.mkString(",").split(",")(1)
      val awayKey = row.mkString(",").split(",")(2)
      val homePut = new Put(Bytes.toBytes(homeKey))
      val awayPut = new Put(Bytes.toBytes(awayKey))
      val result = table.get(new Get(Bytes.toBytes(awayKey)))
      if (result.isEmpty){
        hbaseHelper(homePut, awayPut, speed)
      }

      else {
        val byteResult = result.getValue(Bytes.toBytes("speed"), Bytes.toBytes("s"))
        val length = Bytes.len(byteResult)
        if (length == 4 && Bytes.toInt(byteResult) < speed.toDouble){
          hbaseHelper(homePut, awayPut, speed)
        }
        else if (length == 8 && Bytes.toDouble(byteResult) < speed.toDouble){
          hbaseHelper(homePut, awayPut, speed)
        }

      }
    })
    s"hdfs dfs -rmr hdfs:///wjsjr/SpeedLayerOutput/*" !

  }
}