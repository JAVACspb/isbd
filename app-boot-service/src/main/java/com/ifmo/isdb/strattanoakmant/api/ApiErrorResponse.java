package com.ifmo.isdb.strattanoakmant.api;

import java.time.Instant;
import java.util.List;

public class ApiErrorResponse {
    private final Instant timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final List<FieldViolation> violations;

    public ApiErrorResponse(
            Instant timestamp,
            int status,
            String error,
            String message,
            String path,
            List<FieldViolation> violations
    ) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.violations = violations;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public List<FieldViolation> getViolations() {
        return violations;
    }

    public static class FieldViolation {
        private final String field;
        private final String message;

        public FieldViolation(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}


