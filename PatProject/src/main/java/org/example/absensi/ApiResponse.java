package org.example.absensi;
import java.util.List;

public class ApiResponse<T> {
    private String message;
    private int status;
    private String error;
    private List<T> response;

    public ApiResponse(String message, int status, String error, List<T> response) {
        this.message = message;
        this.status = status;
        this.error = error;
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<T> getResponse() {
        return response;
    }

    public void setResponse(List<T> response) {
        this.response = response;
    }
}
