package gal.mpch.acbguru.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import gal.mpch.acbguru.Config;

public class Utility {


    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    public static InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }


    public static Date parseWsDate(String wsStringDate) throws ParseException {
        return new SimpleDateFormat(Config.WS_DATE_STRING_FORMAT, Locale.ENGLISH).parse(wsStringDate);
    }

    public static String formatDateWithText(Date date, Locale locale) {
        return new SimpleDateFormat(Config.TEXT_DATE_FORMAT, locale).format(date);
    }

    public static String formatPlayingTime(String playingTime){
        if(playingTime!=null && playingTime.length()==8){
            return playingTime.substring(3);
        }
        return playingTime;
    }
}
