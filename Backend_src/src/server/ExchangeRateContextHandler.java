package server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.zip.DataFormatException;

/*
 * Given a date (formatted YYYY-MM-DD) and a currency code,
 * provide its average exchange rate.
 */
public class ExchangeRateContextHandler extends ContextHandler{
    private static final String API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";

    /**
     * Provides average exchange rate of a given currency in a given day.
     * @param args Should have exactly two items. args[0]: Currency code. args[1]: date formatted YYYY-MM-DD.
     * @return Average exchange rate.
     */
    @Override
    protected Response act(String[] args) {
        try {
            if(args.length != 2) throw new DataFormatException();

            Response response = Util.apiGet(API_URL + args[0] + "/" + args[1] + "/?format=json");

            if(response.code == HttpURLConnection.HTTP_OK) {

                // traverse the JSON
                JSONObject json = new JSONObject(response.body);
                JSONArray rates = json.getJSONArray("rates");
                JSONObject rate = rates.getJSONObject(0);
                BigDecimal mid = rate.getBigDecimal("mid");

                JSONObject outcome = new JSONObject();
                outcome.accumulate("rate", mid);

                return new Response(outcome.toString(), 200);
            }
            else {
                return new Response("Bad API call.", HttpURLConnection.HTTP_BAD_REQUEST);
            }
        }
        catch (DataFormatException e) {
            return new Response("Bad request.", HttpURLConnection.HTTP_BAD_REQUEST);
        }
        catch (IOException e) {
            return new Response("Could not connect to API.", HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
        catch (JSONException e) {
            return new Response("Unexpected JSON format.", HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
        catch (Exception e) {
            return new Response("Internal error.", HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }
}
