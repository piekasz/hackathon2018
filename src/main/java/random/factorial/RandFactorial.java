package random.factorial;

import java.math.BigInteger;
import java.util.Random;

public class RandFactorial {
    Random random = new Random();


//Silnia

    public static BigInteger factorial(BigInteger num) {
        BigInteger fact = BigInteger.valueOf(1);
        for (int i = 1; i <= num.intValue(); i++)
            fact = fact.multiply(BigInteger.valueOf(i));
        return fact;
    }

//    public static int factorial(long number) throws ArithmeticException{
//        int result = 1;
//
//        for (long factor = 2; factor <= number; factor++) {
//
//                result *= factor;
//                if (result==0)
//                    throw new ArithmeticException("Nie dziel przez 0");
//                }
//                return result;
//    }


//    Zwraca liczbę losowań  All! / [(n_All-n_Sel)!n_Sel!]

    public static BigInteger iterNumber(BigInteger n_All,BigInteger n_Sel) {
        BigInteger diff = n_All.subtract(n_Sel);
    BigInteger result =  factorial(n_All).divide(factorial(diff).multiply(factorial(n_Sel)));

   return result;


   }


    public static void main(String[] args) {

        System.out.println(
                factorial(BigInteger.valueOf(50))
        );
        System.out.println(iterNumber(BigInteger.valueOf(50),BigInteger.valueOf(30)));


    }


}