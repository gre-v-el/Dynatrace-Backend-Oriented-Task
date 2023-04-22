package server;

import org.junit.Assert;
import org.junit.Test;

import java.net.HttpURLConnection;

public class TestExchangeRateContextHandler {
    @Test
    public void testValid() {
        ExchangeRateContextHandler handler = new ExchangeRateContextHandler();
        Response expected = new Response("{\"rate\":4.2042}", 200);
        Assert.assertEquals(expected, handler.act(new String[]{"usd", "2023-04-14"}));
    }

    @Test
    public void testBadRequest() {
        ExchangeRateContextHandler handler = new ExchangeRateContextHandler();
        Response expected = new Response("Bad request.", HttpURLConnection.HTTP_BAD_REQUEST);
        Assert.assertEquals(expected, handler.act(new String[]{"usd", "2023-04-14", "extra parameter"}));
    }

    @Test
    public void testBadCall() {
        ExchangeRateContextHandler handler = new ExchangeRateContextHandler();
        Response expected = new Response("Bad API call.", HttpURLConnection.HTTP_BAD_REQUEST);
        Assert.assertEquals(expected, handler.act(new String[]{"usd", "wrong parameter"}));
    }
}
