package com.ethan5.controller;

import com.ethan5.dto.LLMRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/LLM")
public class LLMController {
    @GetMapping
    public String LLMGPT(@RequestBody LLMRequest req) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";

        try {
            URL getOpenAI = new URL(url);
            HttpURLConnection getConnection = (HttpURLConnection) getOpenAI.openConnection();
            getConnection.setRequestMethod("POST");
            getConnection.setRequestProperty("Authorization", "Bearer " + apiKey);
            getConnection.setRequestProperty("Content-Type", "application/json");
            List<String> list = req.genres();
            String prompt = "Assume I am really interested into " + list.get(0) + ", " + list.get(1) + ", and " + list.get(2) +
                    ". Describe how someone like me tends to act/think/dress. Limit to 40 words.";
            String getRequestBody = "{\"model\": \"" + "gpt-3.5-turbo" + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            getConnection.setDoOutput(true);
            try (OutputStreamWriter writer = new OutputStreamWriter(getConnection.getOutputStream())) {
                writer.write(getRequestBody);
                writer.flush();
            }

            int responseCode = getConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(getConnection.getInputStream()));
                String line;

                StringBuffer OpenAIResponse = new StringBuffer();

                while ((line = br.readLine()) != null) {
                    OpenAIResponse.append(line);
                }
                br.close();
                return OpenAIResponse.toString().substring(OpenAIResponse.toString().indexOf("content") + 11,
                        OpenAIResponse.toString().indexOf("\"",
                                OpenAIResponse.toString().indexOf("content") + 11));
            } else {
                return "Error: Server responded with code " + responseCode;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}






