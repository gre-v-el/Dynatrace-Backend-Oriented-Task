package server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.zip.DataFormatException;

/**
 * Given a currency code and the number of last quotations N (N <= 255),
 * provide the major difference between the buy and ask rate.
 */
public class MajorDifferenceContextHandler extends ContextHandler {
    private static final String API_URL = "http://api.nbp.pl/api/exchangerates/rates/c/";

    /**
     * Provides maximum difference between ask and buy rates over given number of last quotations.
     * @param args Should have exactly two items. args[0]: Currency code. args[1]: Number of quotations (max 255).
     * @return Average exchange rate.
     */
    @Override
    protected Response act(String[] args) {
        try {
            if(args.length != 2) throw new DataFormatException();

            Response response = Util.apiGet(API_URL + args[0] + "/last/" + args[1] + "/?format=json");

            if(response.code == HttpURLConnection.HTTP_OK) {

                // get max difference by searching through the rates array
                BigDecimal max = BigDecimal.ZERO;

                JSONObject json = new JSONObject(response.body);
                JSONArray rates = json.getJSONArray("rates");
                for(Object object : rates) {
                    JSONObject rate = (JSONObject) object;
                    BigDecimal bid = rate.getBigDecimal("bid");
                    BigDecimal ask = rate.getBigDecimal("ask");

                    max = max.max(ask.subtract(bid));
                }

                JSONObject outcome = new JSONObject();
                outcome.accumulate("maxDifference", max);

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
