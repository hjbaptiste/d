
package com.homeclouddrive.exception;

/**
 *
 * @author getjigy
 */
public class BaseException extends Exception {
    private String reason;

    public BaseException(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
}

