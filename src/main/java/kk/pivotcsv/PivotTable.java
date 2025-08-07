package kk.pivotcsv;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PivotTable {

    private final Csv source;
    private final List<PageFilter> pageFilters = new ArrayList<>();

    public PivotTable withPageFilter(PageFilter filter) {
        pageFilters.add(filter);
        return this;
    }

}
