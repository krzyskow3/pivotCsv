import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.csv.CsvData;
import pl.pkpik.bilkom.pivotcsv.pivottable.PivotTableBuilder;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Sum;
import pl.pkpik.bilkom.pivotcsv.projection.Projection;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static pl.pkpik.bilkom.pivotcsv.csv.Csv.GSON;
import static pl.pkpik.bilkom.pivotcsv.filters.FilterBuilder.field;

public class Test {

    private static final File DATA_FOLDER = new File("src/test/resources");
    private static final File OUT_FOLDER = new File("target/out");

    private final LocalDate fromDay = LocalDate.of(2025, 8, 1);
    private final LocalDate toDay = LocalDate.of(2025, 8, 12);

    @SneakyThrows
    public static void main(String[] args) {
        Test test = new Test();
        test.prettyPrintTest();

        System.out.println();

        test.loadData().prettyPrint();
//        test.cmpStOsdm();
        test.cmpSrSt();
    }

    private void prettyPrintTest() throws IOException {
        Csv csv = loadData();
        List<String> print = csv.prettyPrint();
        FileUtils.writeLines(new File(OUT_FOLDER, "all_pretty_print.txt"), print);
    }

    private void cmpStOsdm() throws IOException {
        new PivotTableBuilder(loadData())
                .withFilter(field("rec_type").in("OSDM", "ST"))
                .withRowFields("tck_series","tck_number","op_type","op_day","offer_code", "red_code","base_price")
                .withColumnFields("rec_type")
                .withDataFields(Sum.of("price"), Sum.of("vat"), Sum.of("compens"))
                .withRowSummary()
                .build()
                .asCsv().save(new File(OUT_FOLDER, "out_cmp_st_osdm.csv"));
    }

    private void cmpSrSt() throws IOException {
        new PivotTableBuilder(loadData())
                .withFilter(field("rec_type").in("KD", "ST"))
                .withRowFields("tck_series","tck_number", "op_day")
                .withColumnFields("op_type", "rec_type")
                .withDataFields(Sum.of("price"), Sum.of("compens"))
                .withRowSummary()
                .build()
                .asCsv().save(new File(OUT_FOLDER, "out_cmp_sr_st.csv"));
    }


    private Csv loadData() throws IOException {
        File kdSaleFile = new File(DATA_FOLDER, "kd_sale.csv");
        File kdRetFile = new File(DATA_FOLDER, "kd_ret.csv");
        File saleRecordsFile = new File(DATA_FOLDER, "sale_records.json");
        File saleTemporaryFile = new File(DATA_FOLDER, "sale_temporary.json");
        OUT_FOLDER.mkdirs();
        return loadKdSale(fromDay, toDay, kdSaleFile)
                .merge(loadKdRet(fromDay, toDay, kdRetFile))
                .merge(loadSaleRecords(fromDay, toDay, saleRecordsFile))
                .merge(loadSaleTemporary(fromDay, toDay, saleTemporaryFile))
                .save(new File(OUT_FOLDER, "all.csv"));
    }

    private Csv loadKdSale(LocalDate fromDay, LocalDate toDay, File file) throws IOException {
        Csv csv = Csv.load(file)
                .projection(new Projection()
                        .addField("id", null)
                        .addField("rec_type", "KD")
                        .addField("op_type", "SALE")
                        .mapField("op_day", "data_sp")
                        .mapField("tck_series", "seria")
                        .mapField("tck_number", "nr_bil")
                        .mapField("offer_code", "oferta")
                        .mapField("base_price", "cena_jedn")
                        .mapField("price", "nalezn")
                        .mapField("vat", "ptu_kwota")
                        .addField("compens", "odstepne_kwota")
                        .mapField("tar_100", "tar_100")
                        .mapField("tar_50", "tar_50")
                        .mapField("red_code", "red_code")
                        .mapField("red_value", "red_value")
                        .mapField("red_perc", "red_perc")
                        .addFilter(field("op_day").between(fromDay, toDay)))
                .save(new File(OUT_FOLDER, "out_kd_sale.csv"));
        System.out.println("Load kdSale: " + csv.size());
        return csv;
    }

    private Csv loadKdRet(LocalDate fromDay, LocalDate toDay, File file) throws IOException {
        Csv csv = Csv.load(file)
                .projection(new Projection()
                        .addField("id", null)
                        .addField("rec_type", "KD")
                        .addField("op_type", "RET")
                        .mapField("op_day", "data_zwrotu")
                        .mapField("tck_series", "seria")
                        .mapField("tck_number", "nr_bil")
                        .mapField("offer_code", "oferta")
                        .mapField("base_price", "cena_jedn")
                        .mapField("price", "nalezn")
                        .mapField("vat", "ptu_kwota")
                        .mapField("compens", "odstepne_kwota")
                        .mapField("tar_100", "tar_100")
                        .mapField("tar_50", "tar_50")
                        .mapField("red_code", "red_code")
                        .mapField("red_value", "red_value")
                        .mapField("red_perc", "red_perc")
                        .addFilter(field("op_day").between(fromDay, toDay)))
                .save(new File(OUT_FOLDER, "out_kd_ret.csv"));
        System.out.println("Load kdRet: " + csv.size());
        return csv;
    }

    private Csv loadSaleRecords(LocalDate fromDay, LocalDate toDay, File file) throws IOException {
        String json = FileUtils.readFileToString(file, "UTF-8");
        CsvData data = GSON.fromJson(json, CsvData.class);
        Csv csv = Csv.create(data.toList()).projection(new Projection()
                        .mapField("id")
                        .mapField("rec_type")
                        .mapField("op_type")
                        .mapField("op_day")
                        .mapField("tck_series")
                        .mapField("tck_number")
                        .mapField("offer_code")
                        .mapField("base_price")
                        .mapField("price")
                        .mapField("vat")
                        .mapField("compens")
                        .mapField("tar_100", "tar100")
                        .mapField("tar_50", "tar50")
                        .mapField("red_code")
                        .mapField("red_value")
                        .mapField("red_perc")
                        .addFilter(field("op_day").between(fromDay, toDay)))
                .save(new File(OUT_FOLDER, "out_sale_records.scv"));
        System.out.println("Load saleRecords: " + csv.size());
        return csv;
    }

    private Csv loadSaleTemporary(LocalDate fromDay, LocalDate toDay, File file) throws IOException {
        String json = FileUtils.readFileToString(file, "UTF-8");
        CsvData data = GSON.fromJson(json, CsvData.class);
        Csv csv = Csv.create(data.toList()).projection(new Projection()
                        .mapField("id")
                        .mapField("rec_type")
                        .mapField("op_type")
                        .mapField("op_day")
                        .mapField("tck_series")
                        .mapField("tck_number")
                        .mapField("offer_code")
                        .mapField("base_price")
                        .mapField("price")
                        .mapField("vat")
                        .mapField("compens")
                        .mapField("tar_100", "tar100")
                        .mapField("tar_50", "tar50")
                        .mapField("red_code")
                        .mapField("red_value")
                        .mapField("red_perc")
                        .addFilter(field("op_day").between(fromDay, toDay)))
                .save(new File(OUT_FOLDER, "out_sale_temporary.scv"));
        System.out.println("Load saleTemporary: " + csv.size());
        return csv;
    }
}
