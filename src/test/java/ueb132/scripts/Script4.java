package ueb132.scripts;

import org.apache.commons.lang3.StringUtils;
import pl.pkpik.bilkom.pivotcsv.csv.CsvSaver;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.csv.printer.BaseScriptMaker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Script4 extends BaseScriptMaker implements CsvSaver {

    public Script4(File outFolder) {
        super(outFolder);
    }

    @Override
    protected String sql() {
        return "";
    }

    @Override
    protected List<String> prepareSql(Record record) {
        List<String> updates = new ArrayList<>();
        if ("true".equals(record.getValue("diff_base_price"))) {
            updates.add("cena_jedn = " + record.getValue("base_price"));
        }
        if ("true".equals(record.getValue("diff_price"))) {
            updates.add("nalezn = " + record.getValue("price"));
        }
        if ("true".equals(record.getValue("diff_vat"))) {
            updates.add("ptu_kwota = " + record.getValue("vat"));
        }
        if ("true".equals(record.getValue("diff_compens"))) {
            updates.add("odstepne_kwota = " + record.getValue("compens"));
        }
        if ("true".equals(record.getValue("diff_tar_100"))) {
            updates.add("tar_100 = " + record.getValue("tar_100"));
        }
        if ("true".equals(record.getValue("diff_tar_50"))) {
            updates.add("tar_50 = " + record.getValue("tar_50"));
        }
        if ("true".equals(record.getValue("diff_red_code"))) {
            updates.add("red_code = " + record.getValue("red_code"));
        }
        if ("true".equals(record.getValue("diff_red_value"))) {
            updates.add("red_value = " + record.getValue("red_value"));
        }
        if ("true".equals(record.getValue("diff_red_perc"))) {
            updates.add("red_perc = " + record.getValue("red_perc"));
        }
        List<String> script = new ArrayList<>();
        script.add("UPDATE sale_temporary ");
        script.add("SET " + StringUtils.join(updates, ", ") + " ");
        script.add("WHERE id = ?1 AND seria = '?2' AND nr_bil = ?3;"
                .replace("?1", record.getValue("id"))
                .replace("?2", record.getValue("tck_series"))
                .replace("?3", record.getValue("tck_number")));
        script.add("");
        return script;
    }
}
