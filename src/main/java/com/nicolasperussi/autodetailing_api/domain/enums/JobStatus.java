package com.nicolasperussi.autodetailing_api.domain.enums;

public enum JobStatus {
    CANCELED(0),
    SCHEDULED(1),
    ONGOING(2),
    FINISHED(3);

    private int code;

    JobStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static JobStatus valueOf(int code) {
        for (JobStatus value : JobStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid JobStatus code");
    }
}
