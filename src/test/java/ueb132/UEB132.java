package ueb132;

import lombok.SneakyThrows;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.pivottable.PivotTable;
import pl.pkpik.bilkom.pivotcsv.pivottable.PivotTableBuilder;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Count;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Max;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Sum;
import pl.pkpik.bilkom.pivotcsv.projection.Projection;

import static pl.pkpik.bilkom.pivotcsv.filters.FilterBuilder.ftField;
import static pl.pkpik.bilkom.pivotcsv.functions.BaseFunction.field;
import static pl.pkpik.bilkom.pivotcsv.functions.FunctionBuilder.fnCase;

public class UEB132 implements Runnable {

    private final Context ctx = Context.create();

    @SneakyThrows
    public static void main(String[] args) {
        new UEB132().run();
    }

    @Override
    public void run() {
        Csv all = ctx.loadData();
        Csv selected = selectKdStTickets(all);
        script1(selected, "16", "17");
        script1(selected, "161", "171");
        script2(selected, "16", "17");
        script2(selected, "161", "171");
        script3(selected, "16", "17");
        script3(selected, "161", "171");

        cmpKdSt1617(selected);
        cmpKdSt161171(selected);
        Csv cmpOfferItems = cmpKdStOfferItems(selected);


    }

    private void script1(Csv selected, String offer1, String offer2) {
        new PivotTableBuilder(selected)
                .withFilter(ftField("rec_type").in("KD", "ST"))
                .withFilter(ftField("offer_code").in(offer1, offer2))
                .withRowFields("tck_series", "tck_number", "op_type")
                .withColumnFields("rec_type", "offer_code")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .build()
                .having(ftField("count_id_KD_" + offer1).eq("1"),
                        ftField("count_id_KD_" + offer2).eq("1"),
                        ftField("count_id_ST_" + offer1).eq("3"),
                        ftField("count_id_ST_" + offer2).empty())
                .toCsv()
                .save(ctx.csvSaver, "script1_" + offer1 + offer2)
                .projection(new Projection()
                        .mapField("id", "max_id_ST_" + offer1))
                .save(ctx.script1(), "script1_" + offer1 + offer2);
    }

    private void script2(Csv selected, String offer1, String offer2) {
        new PivotTableBuilder(selected)
                .withFilter(ftField("rec_type").in("KD", "ST"))
                .withFilter(ftField("offer_code").in(offer1, offer2))
                .withRowFields("tck_series", "tck_number", "op_type")
                .withColumnFields("rec_type", "offer_code")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .build()
                .having(ftField("count_id_KD_" + offer1).eq("1"),
                        ftField("count_id_KD_" + offer2).eq("1"),
                        ftField("count_id_ST_" + offer1).eq("2"),
                        ftField("count_id_ST_" + offer2).empty())
                .toCsv()
                .save(ctx.csvSaver, "script2_" + offer1 + offer2)
                .projection(new Projection()
                        .mapField("id", "max_id_ST_" + offer1)
                        .addField("offer", offer2))
                .save(ctx.script2(), "script2_" + offer1 + offer2);
    }

    private void script3(Csv selected, String offer1, String offer2) {
        new PivotTableBuilder(selected)
                .withFilter(ftField("rec_type").in("KD", "ST"))
                .withFilter(ftField("offer_code").in(offer1, offer2))
                .withRowFields("tck_series", "tck_number", "op_type")
                .withColumnFields("rec_type", "offer_code")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .build()
                .having(ftField("count_id_KD_" + offer1).eq("1"),
                        ftField("count_id_KD_" + offer2).eq("1"),
                        ftField("count_id_ST_" + offer1).eq("1"),
                        ftField("count_id_ST_" + offer2).empty())
                .toCsv()
                .save(ctx.csvSaver, "script3_" + offer1 + offer2)
                .projection(new Projection()
                        .mapField("id", "max_id_ST_" + offer1)
                        .mapField("tck_series")
                        .mapField("tck_number")
                        .addField("offer", offer2))
                .save(ctx.script3(), "script3_" + offer1 + offer2);
    }

    private Csv selectKdStTickets(Csv all) {
        PivotTable pivotTable = new PivotTableBuilder(all)
                .withFilter(ftField("rec_type").in("KD", "ST"))
                .withRowFields("tck_number", "op_type")
                .withColumnFields("rec_type")
                .withDataFields(Sum.of("price"))
                .build()
                .having(ftField("sum_price_KD").notEmpty(),
                        ftField("sum_price_ST").notEmpty());
        pivotTable.toCsv().save(ctx.csvSaver, "pivot_kd_st_tickets");
        return pivotTable.toDetailsCsv().save(ctx.csvSaver, "selected");
    }

    private void cmpKdSt1617(Csv selected) {
        new PivotTableBuilder(selected)
                .withFilter(ftField("rec_type").in("KD", "ST"))
                .withFilter(ftField("offer_code").in("16", "17"))
                .withRowFields("tck_series", "tck_number", "op_type", "op_day")
                .withColumnFields("rec_type", "offer_code")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .build()
                .toCsv().save(ctx.csvSaver, "cmp_kd_st_1617");
    }

    private void cmpKdSt161171(Csv selected) {
        new PivotTableBuilder(selected)
                .withFilter(ftField("rec_type").in("KD", "ST"))
                .withFilter(ftField("offer_code").in("161", "171"))
                .withRowFields("tck_series", "tck_number", "op_type", "op_day")
                .withColumnFields("rec_type", "offer_code")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .build()
                .toCsv().save(ctx.csvSaver, "cmp_kd_st_161171");
    }

    private Csv cmpKdStOfferItems(Csv selected) {
        return new PivotTableBuilder(selected)
                .withFilter(ftField("rec_type").in("KD", "ST"))
                .withRowFields("tck_series", "tck_number", "op_type", "op_day", "offer_code")
                .withColumnFields("rec_type")
                .withDataFields(
                        Max.of("base_price"),
                        Sum.of("price"),
                        Sum.of("vat"),
                        Sum.of("compens"),
                        Sum.of("tar_100"),
                        Sum.of("tar_50"),
                        Max.of("red_code"),
                        Sum.of("red_value"),
                        Max.of("red_perc"),
                        Count.of("id"),
                        Max.of("id"))
                .build()
                .toCsv()
                .calcField("signum", fnCase(field("op_type").eq("SALE"), 1, -1))
                .calcField("price", field("sum_price_KD").multiply(field("signum")))
                .calcField("vat", field("sum_vat_KD").multiply(field("signum")))
                .calcField("red_value", field("sum_red_value_KD").multiply(field("signum")))
                .calcField("same_base_price", field("max_base_price_KD").eq(field("max_base_price_ST")))
                .calcField("same_price", field("price").eq(field("sum_price_ST")))
                .calcField("same_vat", field("vat").eq(field("sum_vat_ST")))
                .calcField("same_compens", field("sum_compens_KD").eq(field("sum_compens_ST")))
                .calcField("same_tar_100", field("sum_tar_100_KD").eq(field("sum_tar_100_ST")))
                .calcField("same_tar_50", field("sum_tar_50_KD").eq(field("sum_tar_50_ST")))
                .calcField("same_red_code", field("max_red_code_KD").eq(field("max_red_code_ST")))
                .calcField("same_red_value", field("red_value").eq(field("sum_red_value_ST")))
                .calcField("same_red_perc", field("max_red_perc_KD").eq(field("max_red_perc_ST")))
                .calcField("same_all", field("same_base_price")
                        .and(field("same_price"))
                        .and(field("same_vat"))
                        .and(field("same_compens"))
                        .and(field("same_tar_100"))
                        .and(field("same_tar_50"))
                        .and(field("same_red_code"))
                        .and(field("same_red_value"))
                        .and(field("same_red_perc"))
                )
                .save(ctx.csvSaver, "cmp_kd_st_offer_items");
    }

}
