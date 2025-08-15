package pl.pkpik.bilkom.pivotcsv.csv.dao;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.csv.CsvSaver;

import java.io.File;

@AllArgsConstructor
public class JsonCsvSaver implements CsvSaver, GsonProvider {

    private final File outFolder;

    @SneakyThrows
    @Override
    public void save(Csv csv, String name) {
        CsvData csvData = new CsvData();
        csv.getRecords().forEach(rec -> csvData.addRecord(rec.getMap()));
        String json = GSON.toJson(csvData);

        outFolder.mkdirs();
        File file = new File(outFolder, name + ".json");
        FileUtils.write(file, json, "UTF-8");
        System.out.println("Saved " + file.getName() + ": " + csv.size());
    }

}
