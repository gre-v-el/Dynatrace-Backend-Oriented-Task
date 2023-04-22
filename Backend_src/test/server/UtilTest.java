package server;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UtilTest {
    @Test
    public void testApiGetOk() throws IOException {
        String url = "http://api.nbp.pl/api/exchangerates/rates/a/usd/2023-04-21?format=json";
        Response expected = new Response(
                "{\"table\":\"A\",\"currency\":\"dolar amerykański\",\"code\":\"USD\",\"rates\":[{\"no\":\"078/A/NBP/2023\",\"effectiveDate\":\"2023-04-21\",\"mid\":4.2006}]}",
                200
        );
        Response actual = Util.apiGet(url);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IOException.class)
    public void testApiGetWrongURL() throws IOException {
        Util.apiGet("This is not a URL");
    }

    @Test
    public void testInputStreamToString() throws IOException {
        String source = "{\n" +
                "\"table\":\"A\",\n" +
                "\"currency\":\"dolar amerykański\",\n" +
                "\"code\":\"USD\",\n" +
                "\"rates\":[\n" +
                "{\n" +
                "\"no\":\"078/A/NBP/2023\",\n" +
                "\"effectiveDate\":\"2023-04-21\",\n" +
                "\"mid\":4.2006\n" +
                "}\n" +
                "]\n" +
                "}";
        String expected = "{\"table\":\"A\",\"currency\":\"dolar amerykański\",\"code\":\"USD\",\"rates\":[{\"no\":\"078/A/NBP/2023\",\"effectiveDate\":\"2023-04-21\",\"mid\":4.2006}]}";

        InputStream inputStream = new ByteArrayInputStream(source.getBytes());

        Assert.assertEquals(expected, Util.inputStreamToString(inputStream));
    }
}
