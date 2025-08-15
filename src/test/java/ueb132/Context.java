package ueb132;

import lombok.AllArgsConstructor;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.csv.CsvLoader;
import pl.pkpik.bilkom.pivotcsv.csv.CsvSaver;
import pl.pkpik.bilkom.pivotcsv.csv.dao.*;
import pl.pkpik.bilkom.pivotcsv.projection.Projection;
import ueb132.projections.KdRetProjection;
import ueb132.projections.KdSaleProjection;
import ueb132.projections.SaleRecProjection;
import ueb132.projections.SaleTmpProjection;
import ueb132.source.JBilkomDbConnection;
import ueb132.source.JBilkomQuery;

import java.io.File;
import java.time.LocalDate;

@AllArgsConstructor
class Context implements JBilkomQuery {

    private static final File DATA_FOLDER = new File("src/test/resources");
    private static final File OUT_FOLDER = new File("target/out");
    private static final LocalDate fromDay = LocalDate.of(2025, 8, 1);
    private static final LocalDate toDay = LocalDate.of(2025, 8, 13);

    public final CsvSaver csvSaver;
    public final CsvLoader kdSaleFile;
    public final CsvLoader kdRetFile;
    public final CsvLoader saleTmpDb;
    public final CsvLoader saleTmpJson;
    public final CsvLoader saleRecJson;

    static Context create() {
        Projection kdSalePr = KdSaleProjection.create(fromDay, toDay);
        Projection kdRetPr = KdRetProjection.create(fromDay, toDay);
        Projection saleTmpPr = SaleTmpProjection.create(fromDay, toDay);
        Projection saleRecPr = SaleRecProjection.create(fromDay, toDay);
        DbConnection jBilkom = JBilkomDbConnection.create();
        CsvSaver csvSaver = new FileCsvSaver(OUT_FOLDER);
        CsvLoader kdSaleFile = new FileCsvLoader(DATA_FOLDER, "kd_sale", kdSalePr);
        CsvLoader kdRetFile = new FileCsvLoader(DATA_FOLDER, "kd_ret", kdRetPr);
        CsvLoader saleTmpDb = new DbCsvLoader(jBilkom, SELECT_SALE_TEMPORARY, saleTmpPr);
        CsvLoader saleTmpJson = new JsonCsvLoader(DATA_FOLDER, "sale_temporary", saleTmpPr);
        CsvLoader saleRecJson = new JsonCsvLoader(DATA_FOLDER, "sale_records", saleRecPr);
        return new Context(csvSaver, kdSaleFile, kdRetFile, saleTmpDb, saleTmpJson, saleRecJson);
    }

    public Csv loadData() {
        Csv kdSaleCsv = kdSaleFile.load().save(csvSaver, "load_kd_sale");
        Csv kdRetCsv = kdRetFile.load().save(csvSaver, "load_kd_ret");
        Csv saleTmpCsv = saleTmpDb.load().save(csvSaver, "load_sale_tmp_db");
//        Csv saleTmpCsv = saleTmpJson.load().save(csvSaver, "load_sale_tmp_json");
        Csv saleRecCsv = saleRecJson.load().save(csvSaver, "load_sale_rec");
        return kdSaleCsv
                .merge(kdRetCsv)
                .merge(saleTmpCsv)
                .merge(saleRecCsv)
                .save(csvSaver, "load_all");
    }
}
