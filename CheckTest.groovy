class CheckTest extends GroovyTestCase {

    void test_convert_check_value_to_written_numbers() {
        def check = new Check(value: '2523.04')
        assert check.toString() == "Two thousand five hundred twenty-three and 04/100 dollars"
    }

    void test_convert_max_check_value_to_written_numbers() {
        def check = new Check(value: "${Integer.MAX_VALUE}.00") // MAX_VALUE is 2147483647
        assert check.toString() == "Two billion one hundred forty-seven million four hundred eighty-three thousand six hundred forty-seven and 00/100 dollars"
    }

    void test_convert_negative_check_value_to_written_numbers() {
        def check = new Check(value: '-111.11')
        assert check.toString() == "Negative one hundred eleven and 11/100 dollars"
    }

    void test_convert_check_for_pennies_to_written_numbers() {
        def check = new Check(value: '.04')
        assert check.toString() == "04/100 dollars"
    }

    void test_get_dollars() {
        def check = new Check()
        assert check.getDollars('123.45') == 123
        assert check.getDollars('123') == 123
        assert check.getDollars('.12') == 0
    }

    void test_get_cents() {
        def check = new Check()
        assert check.getCents('123.45') == 45
        assert check.getCents('123') == 0
        assert check.getCents('.12') == 12
    }

    void test_convert_to_cents() {
        def check = new Check()
        assert check.convertCents() == "00/100"
        assert check.convertCents(0) == "00/100"
        assert check.convertCents(00) == "00/100"
        assert check.convertCents(01) == "01/100"
        assert check.convertCents(99) == "99/100"
    }

    void test_convert_hundreds_to_words() {
        def check = new Check()
        assert check.convertHundreds(1) == "one"
        assert check.convertHundreds(2) == "two"
        assert check.convertHundreds(10) == "ten"
        assert check.convertHundreds(11) == "eleven"
        assert check.convertHundreds(20) == "twenty"
        assert check.convertHundreds(21) == "twenty-one"
        assert check.convertHundreds(100) == "one hundred"
        assert check.convertHundreds(111) == "one hundred eleven"
        assert check.convertHundreds(121) == "one hundred twenty-one"
        assert check.convertHundreds(999) == "nine hundred ninety-nine"
    }
}
