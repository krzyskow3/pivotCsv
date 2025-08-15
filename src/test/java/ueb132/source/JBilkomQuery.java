package ueb132.source;

public interface JBilkomQuery {

    String SELECT_SALE_TEMPORARY = "SELECT " +
            "id, " +
            "'ST' AS rec_type, " +
            "(CASE WHEN data_zwrotu IS NULL THEN 'SALE' ELSE 'RET' END) AS op_type, " +
            "(CASE WHEN data_zwrotu IS NULL THEN data_sp ELSE data_zwrotu END)::date AS op_day, " +
            "seria AS tck_series, " +
            "nr_bil AS tck_number, " +
            "oferta AS offer_code, " +
            "cena_jedn AS base_price, " +
            "abs(nalezn) AS price, " +
            "abs(nalezn)-abs(ptu_kwota) AS vat, " +
            "odstepne_kwota AS compens, " +
            "tar_100 AS tar100, " +
            "tar_50 AS tar50, " +
            "red_code, " +
            "abs(red_value) AS red_value, " +
            "red_perc " +
            "FROM sale_temporary";

}
