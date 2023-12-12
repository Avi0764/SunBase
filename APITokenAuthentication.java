package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class APITokenAuthentication {

    public static void main(String[] args) throws IOException {
        
        String authEndpoint = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
        String loginId = "test@sunbasedata.com";
        String password = "Test@123";

       
        String authPayload = "{\n" +
                "\"login_id\" : \"" + loginId + "\",\n" +
                "\"password\" :\"" + password + "\"\n" +
                "}";

        
        String bearerToken = authenticateAndGetToken(authEndpoint, authPayload);
        if (bearerToken != null) {
            // Subsequent API call endpoint
            String apiEndpoint = "https://your-api.com/your/endpoint";
            
            
            String apiResponse = makeAPICall(apiEndpoint, bearerToken);
            System.out.println(apiResponse);
        } else {
            System.out.println("Authentication failed.");
        }
    }

   
    private static String authenticateAndGetToken(String authEndpoint, String authPayload) throws IOException {
        URL url = new URL(authEndpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        
        connection.getOutputStream().write(authPayload.getBytes());

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            return "sample_bearer_token"; // Replace with actual token
        }
        return null;
    }

   
    private static String makeAPICall(String apiEndpoint, String bearerToken) throws IOException {
        URL url = new URL(apiEndpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + bearerToken);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
           
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return "API request failed: " + responseCode;
        }
    }
}
