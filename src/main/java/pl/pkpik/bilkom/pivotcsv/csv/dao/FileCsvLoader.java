package pl.pkpik.bilkom.pivotcsv.csv.dao;

import lombok.AllArgsConstructor;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.csv.CsvLoader;
import pl.pkpik.bilkom.pivotcsv.projection.Projection;

import java.io.File;

@AllArgsConstructor
public class FileCsvLoader implements CsvLoader {

    private final File dataFolder;
    private final String name;
    private final Projection projection;

    @Override
    public Csv load() {
        Csv csv = Csv.load(new File(dataFolder, name + ".csv"));
        return (projection == null) ? csv : csv.projection(projection);
    }
}
