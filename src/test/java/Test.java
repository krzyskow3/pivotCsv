import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.filters.DayValueBetween;
import pl.pkpik.bilkom.pivotcsv.filters.ValueIn;
import pl.pkpik.bilkom.pivotcsv.pivottable.PivotTableBuilder;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Sum;
import pl.pkpik.bilkom.pivotcsv.projection.Projection;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.pkpik.bilkom.pivotcsv.csv.Csv.GSON;

public class Test {

    private static final File DATA_FOLDER = new File("src/test/resources");
    private static final File OUT_FOLDER = new File("target/out");

    @SneakyThrows
    public static void main(String[] args) {
        Test test = new Test();
        Csv csv = new PivotTableBuilder(test.loadData())
                .withFilter(new ValueIn("rec_type", "SR", "ST"))
                .withRowFields("tck_series","tck_number","op_type","op_day","offer_code","red_code","base_price")
                .withColumnFields("rec_type")
                .withDataFields(Sum.of("price"), Sum.of("vat"), Sum.of("compens"))
                .build()
                .asCsv().save(new File(OUT_FOLDER, "pivot.csv"));
        System.out.println(csv.size());
    }


    private Csv loadData() throws IOException {
        LocalDate fromDay = LocalDate.of(2025, 7, 8);
        LocalDate toDay = LocalDate.of(2025, 7, 31);
        File saleOsdmFile = new File(DATA_FOLDER, "sale_osdm.csv");
        File retOsdmFile = new File(DATA_FOLDER, "return_osdm.csv");
        File saleRecordsFile = new File(DATA_FOLDER, "sale_records.json");
        File saleTemporaryFile = new File(DATA_FOLDER, "sale_temporary.json");
        OUT_FOLDER.mkdirs();
        return loadSaleOsdm(fromDay, toDay, saleOsdmFile)
                .merge(loadReturnOsdm(fromDay, toDay, retOsdmFile))
                .merge(loadSaleRecords(fromDay, toDay, saleRecordsFile))
                .merge(loadSaleTemporary(fromDay, toDay, saleTemporaryFile))
                .save(new File(OUT_FOLDER, "all.csv"));
    }

    private Csv loadSaleOsdm(LocalDate fromDay, LocalDate toDay, File file) throws IOException {
        Csv csv = Csv.load(file)
                .projection(new Projection()
                        .addField("id", null)
                        .addField("rec_type", "OSDM")
                        .addField("op_type", "SALE")
                        .mapField("op_day", "data_sp")
                        .mapField("tck_series", "seria")
                        .mapField("tck_number", "nr_bil")
                        .mapField("offer_code", "oferta")
                        .mapField("base_price", "cena_jedn")
                        .mapField("price", "nalezn")
                        .mapField("vat", "ptu_kwota")
                        .addField("compens", "0")
                        .mapField("tar_100")
                        .mapField("tar_50")
                        .mapField("red_code")
                        .mapField("red_value")
                        .mapField("red_perc")
                        .addFilter(new DayValueBetween("op_day", fromDay, toDay)))
                .save(new File(OUT_FOLDER, "out_sale_osdm.csv"));
        System.out.println("Load saleOsdm: " + csv.size());
        return csv;
    }

    private Csv loadReturnOsdm(LocalDate fromDay, LocalDate toDay, File file) throws IOException {
        Csv csv = Csv.load(file)
                .projection(new Projection()
                        .addField("id", null)
                        .addField("rec_type", "OSDM")
                        .addField("op_type", "RET")
                        .mapField("op_day", "data_zw")
                        .mapField("tck_series", "seria")
                        .mapField("tck_number", "nr_bil")
                        .mapField("offer_code", "oferta")
                        .mapField("base_price", "cena_jedn")
                        .mapField("price", "kwota_z")
                        .mapField("vat", "ptu_kwota")
                        .mapField("compens", "p_ag_value")
                        .mapField("tar_100")
                        .mapField("tar_50")
                        .mapField("red_code")
                        .mapField("red_value")
                        .mapField("red_perc")
                        .addFilter(new DayValueBetween("op_day", fromDay, toDay)))
                .save(new File(OUT_FOLDER, "out_return_osdm.csv"));
        System.out.println("Load returnOsdm: " + csv.size());
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
                        .addFilter(new DayValueBetween("op_day", fromDay, toDay)))
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
                        .addFilter(new DayValueBetween("op_day", fromDay, toDay)))
                .save(new File(OUT_FOLDER, "out_sale_temporary.scv"));
        System.out.println("Load saleTemporary: " + csv.size());
        return csv;
    }


    public static class CsvData extends ArrayList<HashMap<String, String>> {

        public List<Map<String, Object>> toList() {
            return super.stream().map(this::toMap).collect(Collectors.toList());
        }

        private Map<String, Object> toMap(HashMap<String, String> element) {
            return new HashMap<>(element);
        }
    }

}
