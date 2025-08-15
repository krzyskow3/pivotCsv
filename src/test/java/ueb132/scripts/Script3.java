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
        return "UPDATE sale_temporary SET oferta = :offer WHERE id = :id;";
    }
}
