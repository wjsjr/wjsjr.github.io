create external table wjsjr_batchServing (
  key String,
  topSpeed SMALLINT)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ('hbase.columns.mapping' = ':key, speed:s')
TBLPROPERTIES ('hbase.table.name' = 'wjsjr_batchServing');


insert overwrite table wjsjr_batchServing
select wjsjr_trackingbatchview.key,
   wjsjr_trackingbatchview.s
   from wjsjr_trackingBatchView;
cd