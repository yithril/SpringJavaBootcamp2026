package org.example;

public class LeaseContract extends Contract {

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
    }

    public double getExpectedEndingValue() {
        return getVehicleSold().getPrice() * 0.50;
    }

    public double getLeaseFee() {
        return getVehicleSold().getPrice() * 0.07;
    }

    @Override
    public double getTotalPrice() {
        return getExpectedEndingValue() + getLeaseFee();
    }

    @Override
    public double getMonthlyPayment() {
        return calculateMonthlyPayment(getTotalPrice(), 0.04, 36);
    }

    private double calculateMonthlyPayment(double principal, double annualRate, int months) {
        double monthlyRate = annualRate / 12;
        return principal * (monthlyRate * Math.pow(1 + monthlyRate, months))
                / (Math.pow(1 + monthlyRate, months) - 1);
    }
}
