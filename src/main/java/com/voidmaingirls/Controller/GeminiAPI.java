
package com.voidmaingirls.Controller;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
public class GeminiAPI {

    private static final String API_KEY = "AIzaSyDQpesj3QbnhjVoM1hkA8Rlfuql96j3FRU"; // Replace with your real key
    private static final String API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;

    public static String getGeminiResponse(String prompt) {
        // Set longer timeout
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        // Create payload
        JSONObject textPart = new JSONObject();
            textPart.put("text", prompt);

            JSONArray partsArray = new JSONArray();
            partsArray.put(textPart);

            JSONObject content = new JSONObject();
            content.put("role", "user"); // ADD THIS LINE
            content.put("parts", partsArray);

            JSONArray contentsArray = new JSONArray();
            contentsArray.put(content);

            JSONObject payload = new JSONObject();
            payload.put("contents", contentsArray);


        RequestBody body = RequestBody.create(payload.toString(), MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println("RAW RESPONSE:\n" + responseBody);

            JSONObject json = new JSONObject(responseBody);
            if (json.has("candidates")) {
                JSONArray candidates = json.getJSONArray("candidates");
                if (candidates.length() > 0) {
                    JSONObject candidate = candidates.getJSONObject(0);
                    JSONObject candidateContent = candidate.getJSONObject("content");
                    JSONArray parts = candidateContent.getJSONArray("parts");
                    if (parts.length() > 0) {
                        return parts.getJSONObject(0).getString("text");
                    }
                }
            }
            return "No response from Gemini.";
        } catch (IOException e) {
            return "Timeout or network error: " + e.getMessage();
        } catch (Exception e) {
            return "Unexpected error: " + e.getMessage();
        }

   

    }

    public static String getNutriBotResponse(String prompt) {
        String systemPrompt =
                "You are NutriBot, a helpful assistant built into the NutriNXT application. " +
                "NutriNXT is an AI-powered app offering personalized diet plans (WeekBite), recipe suggestions (RecipeHub), " +
                "calorie tracking (Calorie Vault), water intake monitoring (DrinkUp), local farmer integration, and delivery tracking. " +
                "Always respond in under 100 words. Only answer questions related to NutriNXT. " +
                "Be friendly, simple, and clear.\n\n" +
                "User: " + prompt + "\n\n" +
                "NutriBot:";

        return getGeminiResponse(systemPrompt);
    }

}
