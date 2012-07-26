/**
 * A simple check
 *
 * @author Justin Stockton
 */
class Check {

    String value

    /**
     * Convert a check value to words.
     * e.g. assert '123.45' == 'One hundred twenty-three and 45/100 dollars'
     *
     * @return amount as a string
     */
    public String toString() {
        def result = ""

        def val = this.value
        def dollars = getDollars(val)
        def cents = getCents(val)
        if (dollars < 0) {
            result += "negative "
        }

        if (dollars) {
            result += convertToWords(dollars.abs())
            result += " and "
        }
        result += convertCents(cents)
        result += " dollars"
        return result.capitalize()
    }

    /**
     * Get the dollar amount from a string
     *
     * @param String value
     * @return Integer value
     */
    private Integer getDollars(String val) {
        def amnt = val.tokenize('.')
        if (val.contains('.') && amnt.size() == 1) {
            return 0
        } else {
            return amnt[0].toInteger()
        }
    }

    /**
     * Get the cents from a string
     *
     * @param String value
     * @return Integer value
     */
    private Integer getCents(String val) {
        if (val.contains('.')) {
            def cents = val[-2..-1]
            return cents.toInteger()
        } else {
            return 0
        }
    }

    /**
     * Convert an integer into words
     *
     * @param number
     * @return string
     */
    private String convertToWords(Integer number) {
        def places = ['thousand', 'million', 'billion']
        def biggest = 1000000000 // MAX_INTEGER is 2 billion so biggest places is 1 billion
        def i = 3 // number of comma separated places in the biggest number above
        def subset = number
        def result = ""
        while (number != 0) {
            subset = number.intdiv(biggest)
            number = number % biggest
            biggest = biggest.intdiv(1000)
            i--
            if (subset != 0) {
                result += convertHundreds(subset as int)
                if (i >= 0) {
                    result += " " + places[i]
                }
                if (number != 0) {
                    result += " "
                }
            }
        }
        return result
    }

    /**
     * Convert an integer between 0 and 999 into words
     *
     * @param number
     * @return string
     */
    private String convertHundreds(Integer num) {
        def numbersToWords = [
            1: 'one', 2: 'two', 3: 'three', 4: 'four', 5: 'five', 6: 'six', 7: 'seven', 8: 'eight', 9: 'nine', 10: 'ten',
            11: 'eleven', 12: 'twelve', 13: 'thirteen', 14: 'fourteen', 15: 'fifteen', 16: 'sixteen', 17: 'seventeen', 18: 'eighteen', 19: 'nineteen']
        def tens = ['ten', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety']

        def result = ""

        if (num > 99) {
            result += numbersToWords[(num/100).toInteger()]
            result += " hundred"
            num = num % 100
            if (num != 0) result += " "
        }

        if (num > 19) {
            result += tens[(num/10).toInteger() - 1]
            num = num % 10
            if (num != 0) {
                result += "-"
            }
        }

        if (num > 0) {
            result += numbersToWords[num]
        }
        return result
    }

    /**
     * Convert an integer into cents
     *
     * @param number (defaults to 0)
     * @return string
     */
    private String convertCents(Integer num=00) {
        return "${num?.toString()?.padLeft(2,'0')}/100"
    }
}
