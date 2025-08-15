package ueb132.scripts;

import pl.pkpik.bilkom.pivotcsv.csv.CsvSaver;
import pl.pkpik.bilkom.pivotcsv.csv.printer.BaseScriptMaker;

import java.io.File;

public class Script1 extends BaseScriptMaker implements CsvSaver {

    public Script1(File outFolder) {
        super(outFolder);
    }

    @Override
    protected String sql() {
        return "DELETE FROM sale_temporary WHERE id = :id;";
    }
}
