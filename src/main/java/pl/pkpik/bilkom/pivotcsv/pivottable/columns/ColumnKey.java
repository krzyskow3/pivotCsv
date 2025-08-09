package pl.pkpik.bilkom.pivotcsv.pivottable.columns;

import org.jetbrains.annotations.NotNull;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.pivottable.base.BaseFieldsKey;

import java.util.List;

public class ColumnKey extends BaseFieldsKey {

    public ColumnKey(@NotNull Record record, @NotNull List<String> fields) {
        super(record, fields);
    }

}
