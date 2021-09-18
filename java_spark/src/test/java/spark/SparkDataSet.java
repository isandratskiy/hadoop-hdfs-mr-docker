package spark;

import org.apache.spark.sql.Dataset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.regexp_replace;
import static org.apache.spark.sql.types.DataTypes.DoubleType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Spark DataSet")
@ExtendWith(SetupDataSetExtension.class)
public class SparkDataSet {

    @Test
    @DisplayName("should sum all 'taxes' values")
    void shouldSumValues(Dataset<String> ds) {
        var groupedDs = ds.filter(col("Variable_name").contains("taxes"))
                .withColumn("Value", regexp_replace(col("Value"), ",", "."))
                .withColumn("Value", col("Value").cast(DoubleType))
                .groupBy("Variable_name").sum("Value");
        var sum = groupedDs.first().getDouble(1);
        groupedDs.show();
        assertEquals(73819.69, sum);
    }

    @Test
    @DisplayName("should sum all 'taxes' values")
    void shouldFilterValues(Dataset<String> ds) {
        var filteredDs = ds.filter(col("Variable_name").contains("taxes"))
                .filter(col("Value").gt(800));
        filteredDs.show();
        assertEquals(1, filteredDs.count());
    }
}
