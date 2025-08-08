import lombok.SneakyThrows;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.filters.DayValueBetween;
import pl.pkpik.bilkom.pivotcsv.projection.Projection;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class Test {

    private static final File DATA_FOLDER = new File("src/test/resources");
    private static final File OUT_FOLDER = new File("target/out");

    @SneakyThrows
    public static void main(String[] args) {
        Test test = new Test();
        Csv csv = test.loadData();
        System.out.println(csv.size());
    }


    private Csv loadData() throws IOException {
        LocalDate fromDay = LocalDate.of(2025, 7, 8);
        LocalDate toDay = LocalDate.of(2025, 7, 31);
        File saleOsdmFile = new File(DATA_FOLDER, "sale_osdm.csv");
        File retOsdmFile = new File(DATA_FOLDER, "return_osdm.csv");
        OUT_FOLDER.mkdirs();
        Csv csv = loadOsdmSale(fromDay, toDay, saleOsdmFile)
                .merge(loadOsdmReturn(fromDay, toDay, retOsdmFile))
                .save(new File(OUT_FOLDER, "all.csv"));

        return csv;
    }

    private Csv loadOsdmSale(LocalDate fromDay, LocalDate toDay, File file) throws IOException {
        Csv csv = Csv.load(file)
                .projection(new Projection()
                        .addField("id", null)
                        .addField("rec_type", "OSDM")
                        .addField("op_type", "SALE")
                        .mapField("data_sp", "op_day")
                        .mapField("seria", "tck_series")
                        .mapField("nr_bil", "tck_number")
                        .mapField("oferta", "offer_code")
                        .mapField("cena_jedn", "base_price")
                        .mapField("nalezn", "price")
                        .mapField("ptu_kwota", "vat")
                        .addField("compens", "0")
                        .mapField("tar_100")
                        .mapField("tar_50")
                        .mapField("red_code")
                        .mapField("red_value")
                        .mapField("red_perc")
                        .addFilter(new DayValueBetween("op_day", fromDay, toDay)))
                .save(new File(OUT_FOLDER, "out_" + file.getName()));
        System.out.println("=> osdmSale: " + csv.size());
        return csv;
    }

    private Csv loadOsdmReturn(LocalDate fromDay, LocalDate toDay, File file) throws IOException {
        Csv csv = Csv.load(file)
                .projection(new Projection()
                        .addField("id", null)
                        .addField("rec_type", "OSDM")
                        .addField("op_type", "RET")
                        .mapField("data_zw", "op_day")
                        .mapField("seria", "tck_series")
                        .mapField("nr_bil", "tck_number")
                        .mapField("oferta", "offer_code")
                        .mapField("cena_jedn", "base_price")
                        .mapField("kwota_z", "price")
                        .mapField("ptu_kwota", "vat")
                        .mapField("p_ag_value", "compens")
                        .mapField("tar_100")
                        .mapField("tar_50")
                        .mapField("red_code")
                        .mapField("red_value")
                        .mapField("red_perc")
                        .addFilter(new DayValueBetween("op_day", fromDay, toDay)))
                .save(new File(OUT_FOLDER, "out_" + file.getName()));
        System.out.println("=> osdmReturn: " + csv.size());
        return csv;
    }


}
