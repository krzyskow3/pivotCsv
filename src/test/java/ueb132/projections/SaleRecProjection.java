package ueb132.projections;

import pl.pkpik.bilkom.pivotcsv.projection.Projection;

import java.time.LocalDate;

import static pl.pkpik.bilkom.pivotcsv.filters.FilterBuilder.field;

public class SaleRecProjection {

    public static Projection create(LocalDate fromDay, LocalDate toDay) {
        return new Projection()
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
                .addFilter(field("op_day").between(fromDay, toDay));
    }

}
