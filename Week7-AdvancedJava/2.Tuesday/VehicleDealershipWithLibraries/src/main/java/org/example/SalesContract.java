package org.example;


public class SalesContract extends Contract {
    private boolean financed;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean financed) {
        super(date, customerName, customerEmail, vehicleSold);
        this.financed = financed;
    }

    public double getSalesTaxAmount() {
        return getVehicleSold().getPrice() * 0.05;
    }

    public double getRecordingFee() {
        return 100.00;
    }

    public double getProcessingFee() {
        return getVehicleSold().getPrice() < 10000 ? 295.00 : 495.00;
    }

    public boolean isFinanced() {
        return financed;
    }

    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice()
                + getSalesTaxAmount()
                + getRecordingFee()
                + getProcessingFee();
    }

    @Override
    public double getMonthlyPayment() {
        if (!financed) {
            return 0.00;
        }

        if (getVehicleSold().getPrice() >= 10000) {
            return calculateMonthlyPayment(getTotalPrice(), 0.0425, 48);
        }

        return calculateMonthlyPayment(getTotalPrice(), 0.0525, 24);
    }

    private double calculateMonthlyPayment(double principal, double annualRate, int months) {
        double monthlyRate = annualRate / 12;
        return principal * (monthlyRate * Math.pow(1 + monthlyRate, months))
                / (Math.pow(1 + monthlyRate, months) - 1);
    }
}
