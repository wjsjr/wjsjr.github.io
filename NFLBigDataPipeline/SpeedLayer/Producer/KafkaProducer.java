package org.wjsjr;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FSDataInputStream;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer {
	static int index = 0;
	static String dirPath = "hdfs:///wjsjr/SpeedLayerMerged/";
	static BufferedReader br;

	private static void readFileFromHDFS() {
		//getIndexFromHDFS();
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(conf);
			Path path = new Path(dirPath.concat("speed.csv"));
			br = new BufferedReader(new InputStreamReader(fs.open(path)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	static class Task extends TimerTask {
		Task() {

		}

		@Override
		public void run() {
			try {
				// Adapted from http://hortonworks.com/hadoop-tutorial/simulating-transporting-realtime-events-stream-apache-kafka/
				Properties props = new Properties();
				props.put("metadata.broker.list", "b-1.mpcs53014-kafka.198nfg.c7.kafka.us-east-2.amazonaws.com:9092");
				//props.put("zk.connect", "z-3.mpcs53014-kafka.198nfg.c7.kafka.us-east-2.amazonaws.com:2181,z-2.mpcs53014-kafka.198nfg.c7.kafka.us-east-2.amazonaws.com:2181,z-1.mpcs53014-kafka.198nfg.c7.kafka.us-east-2.amazonaws.com:2181");
				props.put("serializer.class", "kafka.serializer.StringEncoder");
				props.put("request.required.acks", "1");

				String TOPIC = "wjsjr_speed_layer";
				ProducerConfig config = new ProducerConfig(props);

				Producer<String, String> producer = new Producer<String, String>(config);
				String nextRow;


				if ((nextRow = br.readLine()) != null) {
					KeyedMessage<String, String> data = new KeyedMessage<String, String>(TOPIC, nextRow);
					producer.send(data);
					System.out.print("Sent Row: ");
					System.out.println(data);
				}
				else{
					System.out.println("END OF SPEED LAYER!");
					br.close();
					System.exit(1);
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	public static void main(String[] args) {
		readFileFromHDFS();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Task(), 0, 15 * 1000);

	}
}