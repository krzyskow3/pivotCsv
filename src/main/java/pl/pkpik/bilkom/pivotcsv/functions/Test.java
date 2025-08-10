package pl.pkpik.bilkom.pivotcsv.functions;

import pl.pkpik.bilkom.pivotcsv.csv.Record;

import static pl.pkpik.bilkom.pivotcsv.functions.BaseFunction.field;


public class Test {

    public static void main(String[] args) {
        Record rec = new Record()
                .setValue("a", "2")
                .setValue("b", "3")
                .setValue("c", "4")
                .setValue("d", "5");
        Function f = field("a").multiply(field("b")).add( field("c").multiply(field("d")) );
        System.out.println(f.getValue(rec));

//        Record rec1 = new Record().setValue("rec_type", "OSDM");
//        Record rec2 = new Record().setValue("rec_type", "ST");
//        Record rec3 = new Record().setValue("rec_type", "SR");

//        Function function = Field.of("rec_type").in("OSDM", "ST");
//        System.out.println("rec1 => " + function.getValue(rec1));
//        System.out.println("rec2 => " + function.getValue(rec2));
//        System.out.println("rec3 => " + function.getValue(rec3));

    }


}
