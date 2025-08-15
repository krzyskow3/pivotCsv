package ueb132.scripts;

import pl.pkpik.bilkom.pivotcsv.csv.CsvSaver;
import pl.pkpik.bilkom.pivotcsv.csv.printer.BaseScriptMaker;

import java.io.File;

public class Script3 extends BaseScriptMaker implements CsvSaver {

    public Script3(File outFolder) {
        super(outFolder);
    }

    @Override
    protected String sql() {
        return "INSERT INTO sale_temporary (id, bus, cena_jedn, czas_sp, data_sp, data_zwrotu, home_co,\n" +
                "       ident, jedn_sl, kasa, kat_poc, klasa, miesiac_r, nalezn, nr_bil, nr_bil_pierw, nr_bil_wym, obow_do, obow_od,\n" +
                "       odl_pier_odc, odleg, odstepne_kwota, oferta, other_co, ptu_kwota, ptu_stawka, rec_id, rec_typ, red_code,\n" +
                "       red_perc, red_value, rodz_sprzed, rok, seria, seria_bil_pierw, seria_wym, sp_prac, stac_do, stac_od,\n" +
                "       stac_zm_tar, stacja_1, stacja_2, status, carrier_code, tar_100, tar_50, uzyt, wsk_druk, wzor_bil,\n" +
                "       zmiana, stac_p1, stac_p2, stac_p3, stac_p4, nr_poc1, nr_poc2, nr_poc3, nr_poc4) \n" +
                "SELECT nextval ('sale_temporary_pk_sq'::regclass), bus, cena_jedn, czas_sp, data_sp, data_zwrotu, home_co,\n" +
                "       ident, jedn_sl, kasa, kat_poc, klasa, miesiac_r, 0, nr_bil, nr_bil_pierw, nr_bil_wym, obow_do, obow_od,\n" +
                "       odl_pier_odc, odleg, 0, :offer, other_co, 0, ptu_stawka, rec_id, rec_typ, red_code,\n" +
                "       red_perc, 0, rodz_sprzed, rok, seria, seria_bil_pierw, seria_wym, sp_prac, stac_do, stac_od,\n" +
                "       stac_zm_tar, stacja_1, stacja_2, status, carrier_code, tar_100, tar_50, uzyt, wsk_druk, wzor_bil,\n" +
                "       zmiana, stac_p1, stac_p2, stac_p3, stac_p4, nr_poc1, nr_poc2, nr_poc3, nr_poc4 \n" +
                "FROM sale_temporary WHERE id = :id AND seria = ':tck_series' AND nr_bil = :tck_number;\n";
    }

}
