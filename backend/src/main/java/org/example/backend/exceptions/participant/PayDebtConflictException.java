package org.example.backend.exceptions.participant;

public class PayDebtConflictException extends RuntimeException {
    public PayDebtConflictException(double amount) {
        super("PayDebt with amount " + amount + " can't be paid!");
    }
}
