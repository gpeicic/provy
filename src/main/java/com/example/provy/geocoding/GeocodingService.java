package com.example.provy.geocoding;

import com.example.provy.common.LatLng;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class GeocodingService {
    private final String apiKey = "YOUR_API_KEY";

    private final RestTemplate restTemplate = new RestTemplate();

    public LatLng geocode(String address) {
        try {
            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                    URLEncoder.encode(address, StandardCharsets.UTF_8) +
                    "&key=" + apiKey;

            Map response = restTemplate.getForObject(url, Map.class);
            List results = (List) response.get("results");

            if (results.isEmpty()) throw new RuntimeException("Address not found");

            Map location = (Map) ((Map) ((Map) results.get(0)).get("geometry")).get("location");
            double lat = (double) location.get("lat");
            double lng = (double) location.get("lng");

            return new LatLng(lat, lng);

        } catch (Exception e) {
            throw new RuntimeException("Failed to geocode address", e);
        }
    }


}
