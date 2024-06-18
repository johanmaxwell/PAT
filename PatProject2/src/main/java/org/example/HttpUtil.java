package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.login.LoginRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    private static final String BASE_URL = "http://localhost:8000";
    private static String sessionCookie = null;

    public static String signupManager(Manager manager) throws IOException {
        String apiUrl = BASE_URL + "/signup_manager";
        String jsonPayload = new Gson().toJson(manager);
        return sendPOSTRequest(apiUrl, jsonPayload);
    }

    public static String assignTasks(String jsonPayload) throws IOException {
        String apiUrl = BASE_URL + "/assign_task";
        return sendPOSTRequest(apiUrl, jsonPayload);
    }

    public static String getNipPegawai() throws IOException {
        String apiUrl = BASE_URL + "/get_nip_pegawai";
        return sendGETRequest(apiUrl);
    }

    public static String getNipPetugas() throws IOException {
        String apiUrl = BASE_URL + "/get_nip_petugas";
        return sendGETRequest(apiUrl);
    }

    public static String changePetugas(String jsonPayload) throws IOException {
        String apiUrl = BASE_URL + "/change_petugas";
        return sendPUTRequest(apiUrl, jsonPayload);
    }

    public static String deleteTask(String jsonPayload) throws Exception {
        String apiUrl = BASE_URL + "/delete_task";
        return sendDELETERequest(apiUrl, jsonPayload);
    }

    public static String login(LoginRequest req) throws IOException {
        String apiUrl = BASE_URL + "/login";
        String jsonPayload = new Gson().toJson(req);
        return sendPOSTRequest(apiUrl, jsonPayload);
    }


    public static String logout() throws IOException {
        String apiUrl = BASE_URL + "/logout";
        return sendPOSTRequest(apiUrl, null); // Assuming no payload needed for logout
    }

    private static String sendPUTRequest(String apiUrl, String jsonPayload) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            setupConnection(connection, "PUT", jsonPayload);
            return handleResponse(connection);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String sendPOSTRequest(String apiUrl, String jsonPayload) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            setupConnection(connection, "POST", jsonPayload);
            return handleResponse(connection);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String sendGETRequest(String apiUrl) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            setupConnection(connection, "GET", null);
            return handleResponse(connection);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String sendDELETERequest(String apiUrl, String jsonPayload) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Content-Type", "application/json");
        if (sessionCookie != null) {
            connection.setRequestProperty("Cookie", sessionCookie);
        }
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        InputStream inputStream = (responseCode >= 200 && responseCode < 300) ? connection.getInputStream() : connection.getErrorStream();
        String response = readResponse(inputStream);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            return response;
        } else {
            throw new Exception(parseErrorResponse(response));
        }
    }

    private static void setupConnection(HttpURLConnection connection, String method, String jsonPayload) throws IOException {
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        if (sessionCookie != null) {
            connection.setRequestProperty("Cookie", sessionCookie);
        }
        connection.setDoOutput(true);

        if (jsonPayload != null) {
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }
        }
    }

    private static String handleResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        InputStream inputStream = (responseCode >= 200 && responseCode < 300) ?
                connection.getInputStream() : connection.getErrorStream();
        String response = readResponse(inputStream);

        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            handleSessionCookie(connection);
            return response;
        } else {
            throw new IOException(parseErrorResponse(response));
        }
    }

    private static void handleSessionCookie(HttpURLConnection connection) {
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        List<String> cookiesHeader = headerFields.get("Set-Cookie");
        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                sessionCookie = cookie.split(";\\s*")[0];
            }
        }
    }

    private static String readResponse(InputStream inputStream) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }

    private static String parseErrorResponse(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String message = jsonObject.has("message") ? jsonObject.get("message").getAsString() : "Unknown error occurred";
        return message;
    }
}
