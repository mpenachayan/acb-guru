package gal.mpch.acbguru.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class PlayByPlayHandler extends DefaultHandler {

    private static final String MIN = "min";

    private static final String TITLE = "title";

    private static final String ITEM = "item";

    private static final String PERIOD = "category";

    private static final String TEAM = "team";

    private static final String SCORE = "score";

    private PlayByPlayItem playByPlay;

    private StringBuilder stringBuilder;

    private List<PlayByPlayItem> plays;

    private boolean itemElement;

    public List<PlayByPlayItem> getPlays() {
        return plays;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        plays = new ArrayList<PlayByPlayItem>();
        stringBuilder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equalsIgnoreCase(ITEM)) {
            this.playByPlay = new PlayByPlayItem();
            itemElement = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        stringBuilder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (playByPlay != null) {
            if (qName.equalsIgnoreCase(TITLE) && itemElement) {
                playByPlay.setDescription(stringBuilder.toString());
            } else if (qName.equalsIgnoreCase(SCORE)) {
                playByPlay.setScore(stringBuilder.toString());
            } else if (qName.equalsIgnoreCase(TEAM)) {
                playByPlay.setTeam(stringBuilder.toString());
            } else if (qName.equalsIgnoreCase(PERIOD)) {
                playByPlay.setPeriod(stringBuilder.toString());
            } else if (qName.equalsIgnoreCase(MIN)) {
                playByPlay.setMinute(stringBuilder.toString());
            } else if (qName.equalsIgnoreCase(ITEM)) {
                plays.add(playByPlay);
                itemElement = false;
            }
            stringBuilder.setLength(0);
        }
    }
}
