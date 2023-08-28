package com.nrc.excelreader.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nrc.excelreader.logger.SimpleLogger;
import com.nrc.excelreader.util.CustomConfig;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;

/**
 * API sevrice to make calls to target API
 * Date : 27.08.2023
 * Version : 1.0 (Initial Version)
 * @author Ujwala Vanve
 */

public class ApiService {

    private static final SimpleLogger logger = new SimpleLogger(ApiService.class);

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void sendingDataToApi(String postData) {

        String nrcApiKey = CustomConfig.properties.getProperty("api.NRC-API-KEY");
        String apiURI    = CustomConfig.properties.getProperty("api.URI");

        try {
            if (apiURI==null || nrcApiKey== null || postData==null) {
                throw new NullPointerException();
            }
        }catch (NullPointerException e) {
                logger.info("Either api.URI or NRC Key or xlsx data is empty (check properties file)");
                e.printStackTrace();
                return;
            }
        HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(apiURI))
                        .header("Content-type", "application/json")
                        .header("charset", "UTF-8")
                        .header("Content-Encoding", "UTF-8")
                        .header("Accept", "application/json")
                        .header("NRC-API-KEY", nrcApiKey)
                        .POST(HttpRequest.BodyPublishers.ofString(postData))
                        .build();
                // Used to show organized JSON data
                logger.info(" ------------> Logger Output <-----------");
                logger.info(" ----> Organized data from XLS sheet is printed below <------ ");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonElement jsonElement = JsonParser.parseString(postData);
                String prettyJson = gson.toJson(jsonElement);
                logger.info(prettyJson);
        try {
                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenAccept(System.out::println)
                        .join();
                //logger.info("File is requested for upload to : - " +request.headers());
        } catch (Exception e) {
            logger.error("HTTP Connection Issue, Kindly Check URl : "+e);
            e.printStackTrace();
        }

    }

}
