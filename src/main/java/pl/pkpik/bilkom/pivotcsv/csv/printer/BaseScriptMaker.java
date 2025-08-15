package pl.pkpik.bilkom.pivotcsv.csv.printer;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.csv.CsvSaver;
import pl.pkpik.bilkom.pivotcsv.csv.Record;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseScriptMaker implements CsvSaver {

    private final File outFolder;

    public BaseScriptMaker(File outFolder) {
        this.outFolder = outFolder;
    }

    @SneakyThrows
    @Override
    public void save(Csv csv, String name) {
        List<String> script = new ArrayList<>();
        for (Record record :  csv.getRecords()) {
            HashMap<String, String> map = record.getMap();
            String sql = sql();
            for (String key : map.keySet()) {
                sql = StringUtils.replace(sql, ":" + key, map.get(key));
            }
            script.addAll(Arrays.stream(StringUtils.split(sql, '\n'))
                    .collect(Collectors.toList()));
        }
        outFolder.mkdirs();
        File file = new File(outFolder, name + ".sql");
        FileUtils.writeLines(file, script);
        System.out.println("Saved " + file.getName() + ": " + csv.size());
    }

    protected abstract String sql();

}
