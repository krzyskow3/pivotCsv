package pl.pkpik.bilkom.pivotcsv.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.params.Params;

import java.time.LocalDate;

import static pl.pkpik.bilkom.pivotcsv.functions.FunctionBuilder.field;

class FunctionTest {

    @Test
    void testArithmeticFormula() {
        Record rec = new Record()
                .setValue("a", "2")
                .setValue("b", "3")
                .setValue("c", "4")
                .setValue("d", "5");
        Function f = field("a").multiply(field("b")).add( field("c").multiply(field("d")) );
        String result = f.getValue(rec, Params.DECIMALS_0);
        Assertions.assertEquals("26", result);
    }

    @Test
    void testFieldIn() {
        Record rec1 = new Record().setValue("rec_type", "abc");
        Record rec2 = new Record().setValue("rec_type", "def");
        Function f = field("rec_type").in("abc", "xyz");
        Assertions.assertEquals("true", f.getValue(rec1));
        Assertions.assertEquals("false", f.getValue(rec2));
    }

    @Test
    void testFieldDayBetween() {
        Record rec1 = new Record().setValue("op_day", "2025-06-06");
        Record rec2 = new Record().setValue("op_day", "2025-08-08");
        Record rec3 = new Record().setValue("op_day", "2025-08-28");
        LocalDate fromDay = LocalDate.of(2025, 8, 1);
        LocalDate toDay = LocalDate.of(2025, 8, 15);
        Function f = field("op_day").between(fromDay, toDay);
        Assertions.assertEquals("false", f.getValue(rec1));
        Assertions.assertEquals("true", f.getValue(rec2));
        Assertions.assertEquals("false", f.getValue(rec3));
    }

}