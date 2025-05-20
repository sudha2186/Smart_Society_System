package model;
public class Maintenance extends FlatOwner {
    private String flatNo;
    private double dueAmount;
    private boolean isPaid;

    public Maintenance(String flatNo, double dueAmount) {
        this.flatNo = flatNo;
        this.dueAmount = dueAmount;
        this.isPaid = false;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public void updatePaymentStatus(boolean status) {
        this.isPaid = status;
    }
}
