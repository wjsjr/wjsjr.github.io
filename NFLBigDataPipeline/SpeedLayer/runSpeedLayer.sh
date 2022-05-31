#!/bin/bash
while true
do
        spark-submit --master local[2] --class HbaseWriter /home/hadoop/wjsjr/src/target/uber-HbaseWriter-1.0-SNAPSHOT.jar 
sleep 60
done
