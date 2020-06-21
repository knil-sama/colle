package colle

import com.amazonaws.services.glue.log.GlueLogger
import com.amazonaws.services.glue.GlueContext
import com.amazonaws.services.glue.util.GlueArgParser
import com.amazonaws.services.glue.util.Job
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import scala.collection.JavaConverters._
import org.apache.spark.sql.SaveMode
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3ClientBuilder
object GlueApp {
  def run(sysArgs: Array[String]){
    val s3Endpoint = "http://localhost:4572"
    val endpoint: AwsClientBuilder.EndpointConfiguration = new AwsClientBuilder.EndpointConfiguration(s3Endpoint, "us-east-1");
    val client = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(endpoint)
            .build();
    val logger = new GlueLogger
    val spark: SparkContext = new SparkContext()
    val glueContext: GlueContext = new GlueContext(spark)
    val sparkSession: SparkSession = glueContext.getSparkSession
    import sparkSession.implicits._
    // @params: [JOB_NAME]
    val args = GlueArgParser.getResolvedOptions(sysArgs, Seq("JOB_NAME").toArray)
    Job.init(args("JOB_NAME"), glueContext, args.asJava)

    val staticData = sparkSession.read          // read() returns type DataFrameReader
      .format("parquet")
      .load("./src/test/ressources/colle/userdata1.parquet"); 
      //.load(GlueApp.class.getResource("userdata1.parquet");) 
    staticData.write.mode(SaveMode.Overwrite).format("parquet").option("quote", " ").save("s3://fake-bucket/file.parquet");
    Job.commit()
  }

  def main(sysArgs: Array[String]) {
    run(sysArgs)
  }
}
