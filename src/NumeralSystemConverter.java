import java.io.IOException;
import java.util.Scanner;
public class NumeralSystemConverter {

    Scanner scan = new Scanner(System.in);
    int srcRadix;
    int dstRadix;
    int decimalNumber = 0;
    String srcNumber;
    String integerResult = "";
    String fractionalResult = "";
    StringBuilder builderResult = new StringBuilder();

    public static void main(String[] args) {
        NumeralSystemConverter main = new NumeralSystemConverter();
        String finalResult;

        main.takeInput();
        main.integerResult = main.calculateIntegerPart();
        if (main.checkForFractional()) {
            main.fractionalResult = main.calculateFractionalPart();
            finalResult = main.integerResult+"."+main.fractionalResult;
        } else {
            finalResult = main.integerResult;
        }

        System.out.println(finalResult);


    }

    /*
    Method takes user input (source base, source number, destination base).
     */
    void takeInput() {
        boolean accepted = false;
        while (!accepted) {
            try {
                System.out.print("Give a source radix: ");
                this.srcRadix = scan.nextInt();
                if (this.srcRadix > 36 || this.srcRadix <= 0) {
                    throw new IndexOutOfBoundsException();
                }
                accepted = true;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("error: out of bounds: src radix");
                System.exit(0);
            } catch (Exception e) {
                System.out.println("error: exception: src radix");
                System.exit(0);
            }
        }

        accepted = false;
        while (!accepted) {
            try {
                System.out.print("Give a source number: ");
                this.srcNumber = scan.next();
                for (int i = 0; i < srcNumber.length(); i++) {
                    if (srcRadix == 1) {
                        if (!srcNumber.matches("[1]+")) {
                            throw new IOException();
                        }
                    } else if (Character.getNumericValue(srcNumber.charAt(i)) >= srcRadix) {
                        throw new IOException();
                    }
                }
                accepted = true;
            } catch (IOException e) {
                System.out.println("error: input exception: src number");
                System.exit(0);
            } catch (Exception e) {
                System.out.println("error: exception: src number");
                System.exit(0);
            }
        }

        accepted = false;
        while (!accepted) {
            try {
                System.out.print("Give a destination radix: ");
                this.dstRadix = scan.nextInt();
                if (this.dstRadix > 36 || this.dstRadix <= 0) {
                    throw new IndexOutOfBoundsException();
                }
                accepted = true;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("error: out of bounds: dst radix");
                System.exit(0);
            } catch (Exception e) {
                System.out.println("error: exception: dst radix");
                scan.next();
                System.exit(0);
            }
        }
    }

    /*
    Method checks if there is a "." in source number (after there is a fractional part).
     */
    boolean checkForFractional() {
        return srcNumber.contains(".");
    }

    /*
    Check if both bases are not bigger than 36.
    Convert to decimal number and then to destination base.
     */
    String calculateIntegerPart() {
        if (srcRadix == 1) {
            try {
                decimalNumber = srcNumber.substring(0, srcNumber.indexOf(".")).length();
            } catch (StringIndexOutOfBoundsException e) {
                decimalNumber = srcNumber.length();
            }
        } else {
            try {
                decimalNumber = Integer.parseInt(srcNumber.substring(0, srcNumber.indexOf(".")), srcRadix);
            } catch (StringIndexOutOfBoundsException e) {
                decimalNumber = Integer.parseInt(srcNumber, srcRadix);
            }
        }

        if (dstRadix == 1) {
            while (decimalNumber > 0) {
                builderResult.append("1");
                decimalNumber--;
            }
            return builderResult.toString();
        } else {
            return Integer.toString(decimalNumber, dstRadix);
        }
    }


    String calculateFractionalPart() {

        StringBuilder fractionalPart = new StringBuilder();
        double decimalValue = 0;
        StringBuilder dstBaseValue = new StringBuilder();
        fractionalPart.append(srcNumber.substring(srcNumber.indexOf(".") + 1));

        for (int i = 0; i < fractionalPart.length(); i++) {
            decimalValue += (Character.getNumericValue(fractionalPart.charAt(i))) / Math.pow(srcRadix, i + 1);
        }

        for (int i = 0; i < 5; i++) {
            dstBaseValue.append(Character.forDigit((int) (decimalValue * dstRadix), dstRadix));
            decimalValue = (decimalValue * dstRadix) - ((int) (decimalValue * dstRadix));
        }

        return dstBaseValue.toString();
    }
}