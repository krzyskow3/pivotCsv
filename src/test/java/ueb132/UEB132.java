package ueb132;

import lombok.SneakyThrows;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.pivottable.PivotTable;
import pl.pkpik.bilkom.pivotcsv.pivottable.PivotTableBuilder;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Count;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Max;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Sum;
import pl.pkpik.bilkom.pivotcsv.projection.Projection;

import static pl.pkpik.bilkom.pivotcsv.filters.FilterBuilder.field;

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
        cmpKdStItems(selected);
        cmpKdSt1617(selected);
        script1_1617(selected);
        script2_1617(selected);
        script3_1617(selected);
    }

    private void script1_1617(Csv selected) {
        new PivotTableBuilder(selected)
                .withFilter(field("rec_type").in("KD", "ST"))
                .withFilter(field("offer_code").in("16", "17"))
                .withRowFields("tck_series", "tck_number", "op_type")
                .withColumnFields("rec_type", "offer_code")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .build()
                .having(field("count_id_KD_16").eq("1"),
                        field("count_id_KD_17").eq("1"),
                        field("count_id_ST_16").eq("3"),
                        field("count_id_ST_17").empty())
                .toCsv()
                .save(ctx.csvSaver, "script1_1617")
                .projection(new Projection()
                        .mapField("id", "max_id_ST_16"))
                .save(ctx.script1(), "script1_1617");
    }

    private void script2_1617(Csv selected) {
        new PivotTableBuilder(selected)
                .withFilter(field("rec_type").in("KD", "ST"))
                .withFilter(field("offer_code").in("16", "17"))
                .withRowFields("tck_series", "tck_number", "op_type")
                .withColumnFields("rec_type", "offer_code")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .build()
                .having(field("count_id_KD_16").eq("1"),
                        field("count_id_KD_17").eq("1"),
                        field("count_id_ST_16").eq("2"),
                        field("count_id_ST_17").empty())
                .toCsv()
                .save(ctx.csvSaver, "script2_1617")
                .projection(new Projection()
                        .mapField("id", "max_id_ST_16")
                        .addField("offer", "17"))
                .save(ctx.script2(), "script2_1617");
    }

    private void script3_1617(Csv selected) {
        new PivotTableBuilder(selected)
                .withFilter(field("rec_type").in("KD", "ST"))
                .withFilter(field("offer_code").in("16", "17"))
                .withRowFields("tck_series", "tck_number", "op_type")
                .withColumnFields("rec_type", "offer_code")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .build()
                .having(field("count_id_KD_16").eq("1"),
                        field("count_id_KD_17").eq("1"),
                        field("count_id_ST_16").eq("1"),
                        field("count_id_ST_17").empty())
                .toCsv()
                .save(ctx.csvSaver, "script3_1617")
                .projection(new Projection()
                        .mapField("id", "max_id_ST_16")
                        .addField("offer", "17"))
                .save(ctx.script3(), "script3_1617");
    }





    private void cmpKdSt1617(Csv selected) {
        new PivotTableBuilder(selected)
                .withFilter(field("rec_type").in("KD", "ST"))
                .withFilter(field("offer_code").in("16", "17"))
                .withRowFields("tck_series", "tck_number", "op_type", "op_day")
                .withColumnFields("rec_type", "offer_code")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .withRowSummary()
                .build()
                .toCsv().save(ctx.csvSaver, "cmp_kd_st_1617");
    }

    private void cmpKdStItems(Csv selected) {
        new PivotTableBuilder(selected)
                .withFilter(field("rec_type").in("KD", "ST"))
                .withRowFields("tck_series", "tck_number", "op_type", "op_day", "offer_code", "red_code")
                .withColumnFields("rec_type")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .withRowSummary()
                .build()
                .toCsv().save(ctx.csvSaver, "cmp_kd_st_items");
    }

    private Csv selectKdStTickets(Csv all) {
        PivotTable pivotTable = new PivotTableBuilder(all)
                .withFilter(field("rec_type").in("KD", "ST"))
                .withRowFields("tck_number", "op_type")
                .withColumnFields("rec_type")
                .withDataFields(Sum.of("price"))
                .withRowSummary()
                .build()
                .having(field("sum_price_KD").notEmpty(), field("sum_price_ST").notEmpty());
        pivotTable.toCsv().save(ctx.csvSaver, "pivot_kd_st_tickets");
        return pivotTable.toDetailsCsv().save(ctx.csvSaver, "selected");
    }

}
