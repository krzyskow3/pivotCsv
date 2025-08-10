package pl.pkpik.bilkom.pivotcsv.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkpik.bilkom.pivotcsv.csv.Record;

import static pl.pkpik.bilkom.pivotcsv.functions.BaseFunction.field;

class FunctionTest {

    @Test
    void testArithmeticFormula() {
        Record rec = new Record()
                .setValue("a", "2")
                .setValue("b", "3")
                .setValue("c", "4")
                .setValue("d", "5");
        Function f = field("a").multiply(field("b")).add( field("c").multiply(field("d")) );
        String result = f.getValue(rec);
        Assertions.assertEquals("26.0000", result);
    }
}