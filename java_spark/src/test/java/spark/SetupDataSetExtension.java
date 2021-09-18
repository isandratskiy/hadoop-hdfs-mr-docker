package spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.extension.*;

import static org.apache.spark.sql.SparkSession.builder;
import static org.apache.spark.sql.types.DataTypes.IntegerType;
import static org.apache.spark.sql.types.DataTypes.StringType;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class SetupDataSetExtension implements BeforeAllCallback, ParameterResolver, AfterEachCallback {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == Dataset.class;
    }

    @Override
    public Dataset resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(GLOBAL).get("DATA_SET", Dataset.class);
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        var spark = builder().master("local[*]").appName("Spark_DataSet").getOrCreate();

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

        var ds = spark.read().format("csv")
                .option("header", "true")
                .option("delimiter", ",")
                .schema(schema)
                .load("src/test/resources/annual-enterprise-survey-2020-financial-year-provisional-csv.csv");

        context.getStore(GLOBAL).put("DATA_SET", ds);
        context.getStore(GLOBAL).put("SPARK_SESSION", spark);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        context.getStore(GLOBAL).get("SPARK_SESSION", SparkSession.class).stop();
    }
}
