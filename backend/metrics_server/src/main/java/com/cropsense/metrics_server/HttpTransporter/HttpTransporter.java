package com.cropsense.metrics_server.HttpTransporter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.cropsense.metrics_server.Logger.AppLogger;

public class HttpTransporter {

    private final AppLogger logger = new AppLogger(getClass().toString());
    

    /**
     * 
     * @param url
     * @return
     * @throws URISyntaxException
     */
    public HttpRequest buildRequest(String url) throws URISyntaxException {

        logger.logInfoMsg("Building URI for request");
        
        // build URI
        URI uri = new URI(url);
       
        // build request object
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        logger.logInfoMsg("Prepared request: " + request.toString() + " to send");

        return request;

    } 


    /**
     * 
     * @param request
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {

        logger.logInfoMsg("Sending the prepared request");
        
        // build client 
        HttpClient client = HttpClient.newHttpClient();
        // build response
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        
        logger.logInfoMsg("Received response: \n" + response.body());
        
        return response;

    }

}
