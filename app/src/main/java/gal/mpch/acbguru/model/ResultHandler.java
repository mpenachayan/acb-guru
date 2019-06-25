package gal.mpch.acbguru.model;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import gal.mpch.acbguru.util.Utility;

public class ResultHandler extends DefaultHandler {

    private static final String POINTS = "points";

    private static final String NAME = "name";

    private static final String PUB_DATE = "pubDate";

    private static final String ITEM = "item";

    private static final String LOCAL_TEAM = "localTeam";

    private static final String VISITOR_TEAM = "visitorTeam";

    private static final String STATUS = "status";

    private static final String TIME = "time";

    private static final String ROUND = "round";

    private static final String IMAGE = "image";

    private static final String ID = "ID";

    private final String LOG_TAG = ResultHandler.class.getSimpleName();

    private ResultItem resultItem;

    private List<ResultItem> results;

    private StringBuilder builder;

    private boolean localTeam;

    private boolean visitorTeam;

    private String matchDay;

    public List<ResultItem> getResults() {
        return results;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equalsIgnoreCase(ITEM)) {
            this.resultItem = new ResultItem();
        }
        if (qName.equalsIgnoreCase(LOCAL_TEAM)) {
            localTeam = true;
        }
        if (qName.equalsIgnoreCase(VISITOR_TEAM)) {
            visitorTeam = true;
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        results = new ArrayList<>();
        builder = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);

        if (this.resultItem != null) {
            if (name.equalsIgnoreCase(NAME)) {
                if (localTeam) {
                    resultItem.setLocalTeam(builder.toString().trim());
                } else if (visitorTeam) {
                    resultItem.setVisitorTeam(builder.toString().trim());
                }
            } else if (name.equalsIgnoreCase(ID)) {
                if (localTeam) {
                    resultItem.setLocalTeamId(builder.toString().trim());
                } else if (visitorTeam) {
                    resultItem.setVisitorTeamId(builder.toString().trim());
                } else {
                    resultItem.setId(Integer.valueOf(builder.toString().trim()));
                }
            } else if (name.equalsIgnoreCase(IMAGE) && localTeam) {
                resultItem.setLocalImageUrl(builder.toString().trim());
            } else if (name.equalsIgnoreCase(IMAGE) && visitorTeam) {
                resultItem.setVisitorImageUrl(builder.toString().trim());
            } else if (name.equalsIgnoreCase(ROUND)) {
                matchDay = builder.toString().trim();
            } else if (name.equalsIgnoreCase(LOCAL_TEAM)) {
                localTeam = false;
            } else if (name.equalsIgnoreCase(VISITOR_TEAM)) {
                visitorTeam = false;
            } else if (name.equalsIgnoreCase(POINTS) && localTeam) {
                resultItem.setLocalPoints(Integer.parseInt(builder.toString().trim()));
            } else if (name.equalsIgnoreCase(POINTS) && visitorTeam) {
                resultItem.setVisitorPoints(Integer.parseInt(builder.toString().trim()));
            } else if (name.equalsIgnoreCase(STATUS)) {
                resultItem.setStatus(builder.toString().trim());
            } else if (name.equalsIgnoreCase(TIME)) {
                resultItem.setLiveClock(builder.toString().trim());
            } else if (name.equalsIgnoreCase(PUB_DATE)) {
                try {
                    resultItem.setMatchDate(Utility.parseWsDate(builder.toString().trim()));
                } catch (ParseException e) {
                    Log.e(LOG_TAG, e.getLocalizedMessage());
                }

            } else if (name.equalsIgnoreCase(ITEM)) {
                results.add(resultItem);
            }
            builder.setLength(0);
        }
    }


    public String getMatchDay() {
        return matchDay;
    }
}