package pl.pkpik.bilkom.pivotcsv.functions;

import pl.pkpik.bilkom.pivotcsv.functions.impl.FnCase;
import pl.pkpik.bilkom.pivotcsv.functions.impl.FnConst;
import pl.pkpik.bilkom.pivotcsv.functions.impl.FnField;

public class FunctionBuilder {

    public static BaseFunction field(String field) {
        return new FnField(field);
    }

    public static BaseFunction fnCase(BaseFunction when, BaseFunction then, BaseFunction orElse) {
        return new FnCase(when, then, orElse);
    }

    public static BaseFunction fnCase(BaseFunction when, int then, int orElse) {
        return new FnCase(when, new FnConst(Integer.toString(then)), new FnConst(Integer.toString(orElse)));
    }

}
