package com.example.technology_app.models.GetLive;

import java.util.List;

public class Metadata {
    List<Meeting> meetings;

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public Metadata(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
