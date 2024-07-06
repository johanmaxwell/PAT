package org.example;

import com.google.gson.*;
import org.example.absensi.LogAbsensi;
import org.example.login.LoginRequest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    private static final String BASE_URL = "http://localhost:8000";
    private static String sessionCookie = null;

    public static String signupPetugas(Petugas petugas) throws Exception {
        String apiUrl = BASE_URL + "/signup_petugas";
        String jsonPayload = new Gson().toJson(petugas);
        return sendPOSTRequest(apiUrl, jsonPayload);
    }

    public static String assignLocation(String nip, String latitude, String longitude, String assignmentDate) throws Exception {
        String apiUrl = BASE_URL + "/assign_location";
        JsonObject payload = new JsonObject();
        payload.addProperty("nip_pegawai", nip);
        payload.addProperty("latitude", latitude);
        payload.addProperty("longitude", longitude);
        payload.addProperty("assignment_date", assignmentDate);
        String jsonPayload = new Gson().toJson(payload);
        return sendPOSTRequest(apiUrl, jsonPayload);
    }

    public static String getNipPegawai() throws Exception {
        String apiUrl = BASE_URL + "/get_nip_pegawai_khusus";
        return sendGETRequest(apiUrl);
    }

    public static String login(LoginRequest req) throws Exception {
        String apiUrl = BASE_URL + "/login";
        String jsonPayload = new Gson().toJson(req);

        // Create connection object
        HttpURLConnection connection = null;
        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Write request payload
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            // Read the response
            int responseCode = connection.getResponseCode();
            InputStream inputStream = (responseCode >= 200 && responseCode < 300) ? connection.getInputStream() : connection.getErrorStream();
            String response = readResponse(inputStream);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                handleSessionCookie(connection);
                return response;
            } else {
                throw new Exception(parseErrorResponse(response));
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static String logout() throws Exception {
        String apiUrl = BASE_URL + "/logout";
        HttpURLConnection connection = null;
        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            if (sessionCookie != null) {
                connection.setRequestProperty("Cookie", sessionCookie); // Include session cookie in request
            }
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            InputStream inputStream = (responseCode >= 200 && responseCode < 300) ? connection.getInputStream() : connection.getErrorStream();
            String response = readResponse(inputStream);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                handleSessionCookie(connection); // Update session cookie (might be cleared by server)
                return response;
            } else {
                throw new Exception(parseErrorResponse(response));
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static List<LogAbsensi> getListAbsensi() throws Exception {
        String apiUrl = BASE_URL + "/list_absensi";
        String jsonResponse = sendGETRequest(apiUrl);
        return parseListAbsensi(jsonResponse);
    }

    public static String updateAbsensiStatus(String action, String nipPegawai) throws Exception {
        String apiUrl = BASE_URL + "/manage_absensi/" + action + "/" + nipPegawai;
        return sendPUTRequest(apiUrl);
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

    private static String sendPUTRequest(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        if (sessionCookie != null) {
            connection.setRequestProperty("Cookie", sessionCookie); // Include session cookie in request
        }
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        InputStream inputStream = (responseCode >= 200 && responseCode < 300) ? connection.getInputStream() : connection.getErrorStream();
        String response = readResponse(inputStream);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            return response;
        } else {
            throw new Exception(parseErrorResponse(response));
        }
    }

    private static List<LogAbsensi> parseListAbsensi(String json) {
        List<LogAbsensi> listAbsensi = new ArrayList<>();
        JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();

        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            String nipPegawai = jsonObject.get("nip_pegawai").getAsString();
            String waktuMasuk = jsonObject.get("waktu_masuk").getAsString();
            String waktuPulang = jsonObject.has("waktu_pulang") && !jsonObject.get("waktu_pulang").isJsonNull() ? jsonObject.get("waktu_pulang").getAsString() : null;
            String status = jsonObject.get("status").getAsString();
            String fotoBuktiMasuk = jsonObject.has("foto_bukti_masuk") && !jsonObject.get("foto_bukti_masuk").isJsonNull() ? jsonObject.get("foto_bukti_masuk").getAsString() : null;
            String fotoBuktiPulang = jsonObject.has("foto_bukti_pulang") && !jsonObject.get("foto_bukti_pulang").isJsonNull() ? jsonObject.get("foto_bukti_pulang").getAsString() : null;

            // Create LogAbsensi object and add to list
            LogAbsensi logAbsensi = new LogAbsensi(nipPegawai, waktuMasuk, waktuPulang, status, fotoBuktiMasuk, fotoBuktiPulang);
            listAbsensi.add(logAbsensi);
        }

        return listAbsensi;
    }

    private static String sendPOSTRequest(String apiUrl, String jsonPayload) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        if (sessionCookie != null) {
            connection.setRequestProperty("Cookie", sessionCookie); // Include session cookie in request
        }
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        InputStream inputStream = (responseCode >= 200 && responseCode < 300) ? connection.getInputStream() : connection.getErrorStream();
        String response = readResponse(inputStream);

        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            handleSessionCookie(connection); // Update session cookie (might be changed by server)
            return response;
        } else {
            throw new Exception(parseErrorResponse(response));
        }
    }

    private static String sendGETRequest(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        if (sessionCookie != null) {
            connection.setRequestProperty("Cookie", sessionCookie); // Include session cookie in request
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

    private static String readResponse(InputStream inputStream) throws Exception {
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
