package com.example.technology_app.models;

public class NotiResponse {
    private long multicast_id;

    public long getMulticast_id() {
        return multicast_id;
    }

    public NotiResponse(long multicast_id, int success, int failure, int canonical_ids) {
        this.multicast_id = multicast_id;
        this.success = success;
        this.failure = failure;
        this.canonical_ids = canonical_ids;
    }

    public void setMulticast_id(long multicast_id) {
        this.multicast_id = multicast_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(int canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    private int success, failure, canonical_ids;
}
