package spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.apache.spark.sql.SparkSession.builder;
import static org.apache.spark.sql.types.DataTypes.IntegerType;
import static org.apache.spark.sql.types.DataTypes.StringType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Spark DataSet")
public class SparkDataSet {
    SparkSession spark;
    Dataset<Row> ds;

    @BeforeEach
    void before() {
        this.spark = builder().master("local[*]").appName("Spark_DataSet").getOrCreate();

        var schema = new StructType()
                .add("Year", IntegerType, true)
                .add("Industry_aggregation_NZSIOC", StringType, true)
                .add("Industry_code_NZSIOC", StringType, true)
                .add("Industry_name_NZSIOC", StringType, true)
                .add("Units", StringType, true)
                .add("Variable_code", StringType, true)
                .add("Variable_name", StringType, true)
                .add("Variable_category", StringType, true)
                .add("Value", StringType, true)
                .add("Industry_code_ANZSIC06", StringType, true);

        this.ds = spark.read().format("csv")
                .option("header", "true")
                .option("delimiter", ",")
                .schema(schema)
                .load("src/test/resources/annual-enterprise-survey-2020-financial-year-provisional-csv.csv");
    }

    @AfterEach
    void close() {
        spark.stop();
    }

    @DisplayName("should count all values")
    @Test
    void shouldCount() {
        this.ds = ds.filter("Variable_name = 'Indirect taxes'");
        this.ds.show(30);
        assertEquals(1112, this.ds.count());
    }
}
