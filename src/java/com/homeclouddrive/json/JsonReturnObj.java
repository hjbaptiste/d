
package com.homeclouddrive.json;

/**
 *
 * @author admin
 */
public class JsonReturnObj {
    private boolean isError = false;
    private String errorMessage = "";
    private String successMessage = "";

    public boolean isIsError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
    
    
}
