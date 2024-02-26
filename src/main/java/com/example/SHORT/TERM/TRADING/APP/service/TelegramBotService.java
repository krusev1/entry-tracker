package com.example.SHORT.TERM.TRADING.APP.service;

import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;

@Service
public class TelegramBotService {

    private final String TELEGRAM_BOT_API = System.getenv("TELEGRAM_BOT_API");
    private final String TELEGRAM_CHAT_ID = System.getenv("TELEGRAM_CHAT_ID");

    private final String URL_STRING = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";


    public void sendMessageToTelegram(String message) throws UnsupportedEncodingException {

        String encodedMessage = URLEncoder.encode(message, "UTF-8");

        String urlString = String.format(URL_STRING, TELEGRAM_BOT_API, TELEGRAM_CHAT_ID, encodedMessage);

        try {
            URI uri = new URI(urlString);
            URLConnection conn = uri.toURL().openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}