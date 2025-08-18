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
            "nalezn AS price, " +
            "ptu_kwota AS vat, " +
            "odstepne_kwota AS compens, " +
            "tar_100 AS tar100, " +
            "tar_50 AS tar50, " +
            "red_code, " +
            "red_value AS red_value, " +
            "red_perc " +
            "FROM sale_temporary " +
            "WHERE coalesce(data_zwrotu, data_sp)::date BETWEEN '?1' AND '?2' " +
            "AND seria='GS'";

    String SELECT_SALE_RECORDS = "SELECT " +
            "sr.origin_order_id||'_'||sr.record_idx AS id, " +
            "'SR' AS rec_type, " +
            "(CASE WHEN sr.operation_type = 'SALE' THEN 'SALE' ELSE 'RET' END) AS op_type, " +
            "sr.op_day, " +
            "substr(sr.ticket_number, 1, 2) AS tck_series," +
            "substr(sr.ticket_number, 4, 8)::integer AS tck_number, " +
            "sr.offer_code, " +
            "sr.base_price, " +
            "(CASE WHEN sr.operation_type = 'SALE' THEN sr.sale_brutto " +
            "      ELSE sr.return_brutto END) AS price, " +
            "(CASE WHEN sr.operation_type = 'SALE' THEN sr.sale_brutto - sr.sale_netto " +
            "      ELSE sr.return_brutto - sr.return_netto END) AS vat, " +
            "sr.compensation AS compens, " +
            "(CASE WHEN sr.discount_code = 1 THEN 1 ELSE 0 END) AS tar100, " +
            "(CASE WHEN sr.discount_code > 1 THEN 1 ELSE 0 END) AS tar50, " +
            "sr.discount_code AS red_code, " +
            "sr.discount_percentage AS red_perc, " +
            "sr.base_price - sr.return_brutto AS red_value " +
            "FROM settlement.sale_records sr " +
            "LEFT JOIN public.payment_refund pr ON pr.id = sr.refund_id " +
            "WHERE sr.op_day BETWEEN '?1' AND '?2' " +
            "AND carrier_code = 14 " +
            "AND ticket_number LIKE 'GS%' " +
            "AND coalesce(pr.return_channel,'') <> 'ADMINISTRATION' ";

}
