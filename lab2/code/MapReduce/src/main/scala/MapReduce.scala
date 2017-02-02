

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object MapReduce {

  def main(args: Array[String]) {
//    added .set("spark.hadoop.validateOutputSpecs","false") so that error is not thrown when ouptut file already exists
    val sparkConf = new SparkConf().setAppName("MapReduce").setMaster("local[*]").set("spark.hadoop.validateOutputSpecs", "false")

    val sc=new SparkContext(sparkConf)

    val input=sc.textFile("input")

    val wc=input.flatMap(line=>{line.split(" ")}).map(word=>(word,1)).cache()//map-->Transformation

    val output=wc.reduceByKey(_+_)//reduceByKey-->Transformation

//output contains word and count
    output.coalesce(1).saveAsTextFile("results/output")
    val sortedOutput: RDD[(String, Int)] =output.distinct().sortBy(_._2,false)//sortBy-->action
// sortedOutput contains output sorted by count in decreasing order
      sortedOutput.coalesce(1).saveAsTextFile("results/sortedOutput")//saveAsTextFile-->action
    val topTen=sortedOutput.collect().take(10)//collect-->action,take-->action
    val topTenrdd=sc.parallelize(topTen,1)
// topTen contains the top ten words and their count
    topTenrdd.saveAsTextFile("results/topTen")
    topTenrdd.foreach(word=>{println(word)})
  }

}
