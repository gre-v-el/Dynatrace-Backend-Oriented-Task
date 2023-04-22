package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {



    static Response apiGet(String url) throws IOException {
        URL resource = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) resource.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK) {
            String body = Util.inputStreamToString(connection.getInputStream());
            return new Response(body, responseCode);
        }
        else {
            return new Response("", responseCode);
        }
    }


    static String inputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        StringBuilder content = new StringBuilder();

        while(line != null) {
            content.append(line);
            line = reader.readLine();
        }
        reader.close();

        return content.toString();
    }
}

