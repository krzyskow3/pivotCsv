package kk.pivotcsv;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Csv {

    private final List<String> headers = new ArrayList<>();
    private final List<Record> records = new ArrayList<>();




}
