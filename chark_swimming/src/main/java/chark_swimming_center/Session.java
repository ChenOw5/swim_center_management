package chark_swimming_center;

import java.sql.Date;

public class Session {
    private final int sessionId;
    private final String user_email;
    private final Date sessionDate;
    private final String startTime;
    private final String endTime;
    private final int sessionPax;
    private final double amount;
    private final String paymentMethod;
    private final int is_deleted;

    public Session(int sessionId, String user_email, Date sessionDate, String startTime, String endTime, int sessionPax, double amount, String paymentMethod, int is_deleted) {
        this.sessionId = sessionId;
        this.user_email = user_email;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sessionPax = sessionPax;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.is_deleted = is_deleted;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getUser_email() {
        return user_email;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getSessionPax() {
        return sessionPax;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public int getIs_deleted() {
        return is_deleted;
    }
}