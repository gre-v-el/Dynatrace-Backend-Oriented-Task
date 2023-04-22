package server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws IOException {
        Main.log("Starting server...");
        HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);

        server.createContext("/rate", new ExchangeRateContextHandler());
        server.createContext("/maxmin", new MinMaxExchangeRateContextHandler());
        server.createContext("/majordiff", new MajorDifferenceContextHandler());

        server.start();
        log("Server started");
    }

    public static void log(String text) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("[" + dtf.format(now) + "] " + text);
    }
}

