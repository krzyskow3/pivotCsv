package pl.pkpik.bilkom.pivotcsv.functions;

import pl.pkpik.bilkom.pivotcsv.functions.impl.FnField;

public class FunctionBuilder {

    public static BaseFunction field(String field) {
        return new FnField(field);
    }

}
