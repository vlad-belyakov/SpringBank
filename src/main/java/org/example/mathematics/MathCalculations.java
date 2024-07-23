package org.example.mathematics;

import java.math.BigDecimal;


public class MathCalculations {

    public BigDecimal summation(double first, double second){
        BigDecimal bigFirst = new BigDecimal(first);
        return (bigFirst.add(BigDecimal.valueOf(second)));
    }

    public BigDecimal subtraction(double first, double second){
        BigDecimal bigFirst = new BigDecimal(first);
        return (bigFirst.subtract(BigDecimal.valueOf(second)));
    }

    public BigDecimal multiply(double first, double second){
        BigDecimal bigFirst = new BigDecimal(first);
        return (bigFirst.multiply(BigDecimal.valueOf(second)));
    }

    public BigDecimal divide(double first, double second){
        BigDecimal bigFirst = new BigDecimal(first);
        return (bigFirst.divide(BigDecimal.valueOf(second)));
    }

    public double credit(double amount, double months, double procents){
        BigDecimal bigAmount = new BigDecimal(amount);
        BigDecimal bigMonths = new BigDecimal(months);
        BigDecimal bigProcents = new BigDecimal(procents);
        BigDecimal monthly = bigAmount.multiply(bigProcents).divide(bigMonths);
        BigDecimal payment;
        BigDecimal overAllSumm = new BigDecimal(0);
        while (decimalToDouble(bigAmount) > 0){
            if(decimalToDouble(bigAmount.subtract(monthly)) > 0) {
                payment = bigAmount.subtract(monthly);
                overAllSumm = overAllSumm.add(payment);
                bigAmount = bigAmount.subtract(payment).multiply(BigDecimal.valueOf(procents));
            } else {
                payment = bigAmount;
                overAllSumm = overAllSumm.add(payment);
                bigAmount = BigDecimal.valueOf(0);
            }
        }
        return decimalToDouble(overAllSumm);
    }

    public double decimalToDouble(BigDecimal big){
        return Double.parseDouble(String.valueOf(big));
    }
}
