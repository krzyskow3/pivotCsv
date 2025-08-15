package pl.pkpik.bilkom.pivotcsv.csv.dao;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.csv.CsvLoader;
import pl.pkpik.bilkom.pivotcsv.projection.Projection;

import java.io.File;

@AllArgsConstructor
public class JsonCsvLoader implements CsvLoader, GsonProvider {

    private final File dataFolder;
    private final String name;
    private final Projection projection;

    @SneakyThrows
    @Override
    public Csv load() {
        File file = new File(dataFolder, name + ".json");
        String json = FileUtils.readFileToString(file, "UTF-8");
        CsvData data = GSON.fromJson(json, CsvData.class);
        Csv csv = Csv.create(data);
        return (projection == null) ? csv : csv.projection(projection);
    }
}
