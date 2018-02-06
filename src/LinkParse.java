import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.net.URL;


public class LinkParse {
    public static Adventure adventure;
    private static final int WILL_WORK = 200;
    public static void parse(final String url) {

        // Make an HTTP request to the above URL
        try {
            makeApiRequest(url);
        } catch (UnirestException e) {
//            e.printStackTrace();
            System.out.println("Network not responding");
        } catch (MalformedURLException e) {
            System.out.println("Bad URL: " + url);
        }
    }

    static void makeApiRequest(String url) throws UnirestException, MalformedURLException {
        final HttpResponse<String> stringHttpResponse;

        // This will throw MalformedURLException if the url is malformed.
        new URL(url);

        stringHttpResponse = Unirest.get(url).asString();
        // Check to see if the request was successful; if so, convert the payload JSON into Java objects
        if (stringHttpResponse.getStatus() == WILL_WORK) {
            String json = stringHttpResponse.getBody();
            Gson gson = new Gson();
             adventure = gson.fromJson(json, Adventure.class);
        }
    }

    private static class UnirestException extends Exception {
    }
}
