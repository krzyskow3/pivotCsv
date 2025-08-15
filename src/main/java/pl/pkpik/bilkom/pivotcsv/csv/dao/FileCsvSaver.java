package pl.pkpik.bilkom.pivotcsv.csv.dao;

import lombok.AllArgsConstructor;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.csv.CsvSaver;

import java.io.File;

@AllArgsConstructor
public class FileCsvSaver implements CsvSaver {

    private final File outFolder;

    @Override
    public void save(Csv csv, String name) {
        outFolder.mkdirs();
        csv.save(new File(outFolder, name + ".csv"));
        System.out.println("Saved " + name + ": " + csv.size());
    }
}
