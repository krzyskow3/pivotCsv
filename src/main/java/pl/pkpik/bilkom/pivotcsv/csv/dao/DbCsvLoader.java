package pl.pkpik.bilkom.pivotcsv.csv.dao;

import lombok.AllArgsConstructor;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.csv.CsvLoader;
import pl.pkpik.bilkom.pivotcsv.projection.Projection;

@AllArgsConstructor
public class DbCsvLoader implements CsvLoader {

    private final DbConnection connection;
    private final String query;
    private final Projection projection;

    @Override
    public Csv load() {
        Csv csv = connection.executeQuery(query);
        return (projection == null) ? csv : csv.projection(projection);
    }
}
