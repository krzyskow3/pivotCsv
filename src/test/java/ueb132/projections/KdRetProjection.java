package ueb132.projections;

import pl.pkpik.bilkom.pivotcsv.projection.Projection;

import java.time.LocalDate;

import static pl.pkpik.bilkom.pivotcsv.filters.FilterBuilder.ftField;

public class KdRetProjection {

    public static Projection create(LocalDate fromDay, LocalDate toDay) {
        return new Projection()
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
                .addFilter(ftField("op_day").between(fromDay, toDay));
    }

}
