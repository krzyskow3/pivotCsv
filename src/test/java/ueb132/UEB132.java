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
        script1(selected, "16", "17");
        script1(selected, "161", "171");
        script2(selected, "16", "17");
        script2(selected, "161", "171");
        script3(selected,"16", "17");
        script3(selected,"161", "171");

        cmpKdStItems(selected);
        cmpKdSt1617(selected);
        cmpKdSt161171(selected);

    }

    private void script1(Csv selected, String offer1, String offer2) {
        new PivotTableBuilder(selected)
                .withFilter(field("rec_type").in("KD", "ST"))
                .withFilter(field("offer_code").in(offer1, offer2))
                .withRowFields("tck_series", "tck_number", "op_type")
                .withColumnFields("rec_type", "offer_code")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .build()
                .having(field("count_id_KD_" + offer1).eq("1"),
                        field("count_id_KD_" + offer2).eq("1"),
                        field("count_id_ST_" + offer1).eq("3"),
                        field("count_id_ST_" + offer2).empty())
                .toCsv()
                .save(ctx.csvSaver, "script1_" + offer1 + offer2)
                .projection(new Projection()
                        .mapField("id", "max_id_ST_" + offer1))
                .save(ctx.script1(), "script1_" + offer1 + offer2);
    }

    private void script2(Csv selected, String offer1, String offer2) {
        new PivotTableBuilder(selected)
                .withFilter(field("rec_type").in("KD", "ST"))
                .withFilter(field("offer_code").in(offer1, offer2))
                .withRowFields("tck_series", "tck_number", "op_type")
                .withColumnFields("rec_type", "offer_code")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .build()
                .having(field("count_id_KD_" + offer1).eq("1"),
                        field("count_id_KD_" + offer2).eq("1"),
                        field("count_id_ST_" + offer1).eq("2"),
                        field("count_id_ST_" + offer2).empty())
                .toCsv()
                .save(ctx.csvSaver, "script2_" + offer1 + offer2)
                .projection(new Projection()
                        .mapField("id", "max_id_ST_" + offer1)
                        .addField("offer", offer2))
                .save(ctx.script2(), "script2_" + offer1 + offer2);
    }

    private void script3(Csv selected, String offer1, String offer2) {
        new PivotTableBuilder(selected)
                .withFilter(field("rec_type").in("KD", "ST"))
                .withFilter(field("offer_code").in(offer1, offer2))
                .withRowFields("tck_series", "tck_number", "op_type")
                .withColumnFields("rec_type", "offer_code")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .build()
                .having(field("count_id_KD_" + offer1).eq("1"),
                        field("count_id_KD_" + offer2).eq("1"),
                        field("count_id_ST_" + offer1).eq("1"),
                        field("count_id_ST_" + offer2).empty())
                .toCsv()
                .save(ctx.csvSaver, "script3_" + offer1 + offer2)
                .projection(new Projection()
                        .mapField("id", "max_id_ST_" + offer1)
                        .mapField("tck_series")
                        .mapField("tck_number")
                        .addField("offer", offer2))
                .save(ctx.script3(), "script3_" + offer1 + offer2);
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

    private void cmpKdSt161171(Csv selected) {
        new PivotTableBuilder(selected)
                .withFilter(field("rec_type").in("KD", "ST"))
                .withFilter(field("offer_code").in("161", "171"))
                .withRowFields("tck_series", "tck_number", "op_type", "op_day")
                .withColumnFields("rec_type", "offer_code")
                .withDataFields(Sum.of("price"), Count.of("id"), Max.of("id"))
                .withRowSummary()
                .build()
                .toCsv().save(ctx.csvSaver, "cmp_kd_st_161171");
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
