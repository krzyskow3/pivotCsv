package pl.pkpik.bilkom.pivotcsv.csv.dao;

import lombok.AllArgsConstructor;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.csv.CsvLoader;
import pl.pkpik.bilkom.pivotcsv.projection.Projection;

public class DbCsvLoader implements CsvLoader {

    private final DbConnection connection;
    private final Projection projection;
    private final String query;

    public DbCsvLoader(DbConnection connection, Projection projection, String query) {
        this.connection = connection;
        this.projection = projection;
        this.query = query;
    }

    @Override
    public Csv load() {
        Csv csv = connection.executeQuery(query);
        return (projection == null) ? csv : csv.projection(projection);
    }
}
