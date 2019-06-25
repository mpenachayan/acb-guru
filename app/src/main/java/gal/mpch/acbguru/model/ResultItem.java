package gal.mpch.acbguru.model;

import java.util.Date;

public class ResultItem {

    private int id;

    private Date matchDate;

    private String liveClock;

    private String localTeam;

    private String localTeamId;

    private int localPoints;

    private String visitorTeam;

    private String visitorTeamId;

    private int visitorPoints;

    private String status;

    private String localImageUrl;

    private String visitorImageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getLiveClock() {
        return liveClock;
    }

    public void setLiveClock(String liveClock) {
        this.liveClock = liveClock;
    }

    public String getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(String localTeam) {
        this.localTeam = localTeam;
    }

    public int getLocalPoints() {
        return localPoints;
    }

    public void setLocalPoints(int localPoints) {
        this.localPoints = localPoints;
    }

    public String getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(String visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public int getVisitorPoints() {
        return visitorPoints;
    }

    public void setVisitorPoints(int visitorPoints) {
        this.visitorPoints = visitorPoints;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocalImageUrl() {
        return localImageUrl;
    }

    public void setLocalImageUrl(String localImageUrl) {
        this.localImageUrl = localImageUrl;
    }

    public String getVisitorImageUrl() {
        return visitorImageUrl;
    }

    public void setVisitorImageUrl(String visitorImageUrl) {
        this.visitorImageUrl = visitorImageUrl;
    }

    public String getLocalTeamId() {
        return localTeamId;
    }

    public void setLocalTeamId(String localTeamId) {
        this.localTeamId = localTeamId;
    }

    public String getVisitorTeamId() {
        return visitorTeamId;
    }

    public void setVisitorTeamId(String visitorTeamId) {
        this.visitorTeamId = visitorTeamId;
    }
}


