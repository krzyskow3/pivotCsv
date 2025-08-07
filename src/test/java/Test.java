import kk.pivotcsv.csv.Csv;
import kk.pivotcsv.pivottable.Filter;
import kk.pivotcsv.pivottable.PivotTable;
import kk.pivotcsv.pivottable.aggregators.Sum;
import lombok.SneakyThrows;

import java.io.File;

public class Test {

    private static final File DATA_FOLDER = new File("src/test/resources");

    @SneakyThrows
    public static void main(String[] args) {
        File file = new File(DATA_FOLDER, "test.csv");
        Csv csv = new Csv().load(file);
        PivotTable pivotCsv = new PivotTable(csv)
                .withFilter(new Filter("rec_type", "ST", "OSDM"))
                .withRowFields("tck_series", "tck_number", "op_day", "offer_code", "red_code", "base_price")
                .withColumnFields("op_type", "rec_type")
                .withDataFields(Sum.of("price"), Sum.of("vat"))
                .calculate();






        System.out.println("");
    }

}
