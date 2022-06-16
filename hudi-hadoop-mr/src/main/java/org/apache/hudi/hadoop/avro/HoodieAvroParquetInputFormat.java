/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hudi.hadoop.avro;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.parquet.hadoop.ParquetInputFormat;
import org.apache.parquet.hadoop.util.ContextUtil;

import java.io.IOException;

/**
 * Replace default HoodieParquetInputFormat to HoodieAvroParquetInputFormat so that
 * we can use HoodieAvroParquetReader. It is only used when `hoodie.datasource.hive_sync.support_timestamp` is true
 * or hive table contains timestamp/int column.
 */
public class HoodieAvroParquetInputFormat extends ParquetInputFormat<ArrayWritable> {
  
  @Override
  public RecordReader<Void, ArrayWritable> createRecordReader(
      InputSplit inputSplit,
      TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
    Configuration conf = ContextUtil.getConfiguration(taskAttemptContext);
    return new HoodieAvroParquetReader(inputSplit, conf);
  }
}