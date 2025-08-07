import kk.pivotcsv.Csv;
import kk.pivotcsv.PageFilter;
import kk.pivotcsv.PivotTable;
import lombok.SneakyThrows;

import java.io.File;

public class Test {

    private static final File DATA_FOLDER = new File("src/test/resources");

    @SneakyThrows
    public static void main(String[] args) {
        File file = new File(DATA_FOLDER, "test.csv");
        Csv csv = new Csv().load(file);
        PivotTable pivotCsv = new PivotTable(csv)
                .withPageFilter(new PageFilter("rec_type", "ST", "OSDM"));


        System.out.println("");
    }

}
