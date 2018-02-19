package ru.sberbank.homework.dergun.hw2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class UrlConnect {
    private URL url;

    public void start() {
        while (true) {
            String url = urlRead();
            if (readPage(url)) {
                break;
            }
        }
    }

    private boolean readPage(String url) {
        if (!urlValidator(url)) {
            return false;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream()))){
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                System.out.println(inputLine);
            }
            System.out.println("Done");

        } catch (Exception ignored) {
            System.err.println("Error");
        }
        return true;
    }

    private String urlRead() {
        System.out.println("Enter the URL:");
        Scanner in = new Scanner(System.in);
        return in.next();
    }

    private boolean urlValidator(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (URISyntaxException | MalformedURLException e) {
            System.out.println("Can't install connection. Reply.");
            return false;
        }
    }
}
