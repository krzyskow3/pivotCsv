public class A {

    public static void main(String[] args) {
        compare("Ala", "Ola");
        compare(" aa", "aaa");
        compare("AAA", "ABC");
        compare("abc", "ABC");
        compare("123", "ABC");
        compare("123", "abc");
        compare("ab\tcd", "abc\td");
        compare("AB\tCD", "ABC\tD");
        compare("12\t34", "123\t4");
        compare("  \t34", "   \t4");
    }

    private static void compare(String first, String second) {
        System.out.println(String.format("%s, %s, %d", first, second, first.compareTo(second)));
    }

}
