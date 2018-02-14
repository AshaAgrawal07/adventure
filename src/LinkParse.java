import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import com.mashape.unirest.http.HttpResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LinkParse {
    public static Adventure adventure;
    //private static final int WILL_WORK = 200;

    public static Adventure parse(final String file) {
                Gson gson = new Gson();
                String str = Data.getFileContentsAsString(file);
                Adventure adventure = gson.fromJson(str, Adventure.class);
                return adventure;
            }
            //return filesIntoObjects;
        //}
        /*// Make an HTTP request to the above URL
        try {
            makeApiRequest(url);
        } catch (com.mashape.unirest.http.exceptions.UnirestException e) {
//            e.printStackTrace();
            System.out.println("Network not responding");
        } catch (MalformedURLException e) {
            System.out.println("Bad URL: " + url);
        }
    }*/

    /*static void makeApiRequest(String url) throws com.mashape.unirest.http.exceptions.UnirestException, MalformedURLException {
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
    }*/

    private static class UnirestException extends Exception {
    }
}
