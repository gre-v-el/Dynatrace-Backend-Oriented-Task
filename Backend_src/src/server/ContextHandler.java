package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Common functionalities of each operation of the server
 */
public abstract class ContextHandler implements HttpHandler {

    public void handle (HttpExchange exchange) throws IOException {
        System.out.println();
        Main.log("Received: " + exchange.getRequestURI());
        // allow every request origin
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        // Get arguments from request URI. Path begins with "/example[...]",
        // so first two items of split ("" and "example") are skipped.
        String path = exchange.getRequestURI().getPath();
        String[] split = path.split("/");
        String[] args = Arrays.copyOfRange(split, 2, split.length);

        Response response = act(args);
        byte[] body = response.body.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(response.code, body.length);
        OutputStream os = exchange.getResponseBody();
        os.write(body);
        os.close();

        Main.log("Sent: " + response);
    }

    /**
     * Performs the operations specific to this context.
     * @param args Arguments from the request URI. URI "http://localhost:8888/example/arg1/arg2/arg3" gives ["arg1", "arg2", "arg3"]
     * @return The response.
     */
    protected abstract Response act(String[] args);
}
