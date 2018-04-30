package com.vitaliimalone.nytimes.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.vitaliimalone.nytimes.model.News;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NewsDeserializer implements JsonDeserializer<List<News>> {
    @Override
    public List<News> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<News> newsList = new ArrayList<>();

        if (json.isJsonObject()) {
            JsonObject jsonBody = json.getAsJsonObject();
            JsonArray jsonArrayResults = jsonBody.getAsJsonArray("results");

            for (JsonElement jsonElement : jsonArrayResults) {
                News news = new News();

                String url = jsonElement.getAsJsonObject().get("url").getAsString();
                String section = jsonElement.getAsJsonObject().get("section").getAsString();
                String title = jsonElement.getAsJsonObject().get("title").getAsString();
                String description = jsonElement.getAsJsonObject().get("abstract").getAsString();
                String date = jsonElement.getAsJsonObject().get("published_date").getAsString();

                JsonArray jsonArrayMedia = jsonElement.getAsJsonObject().getAsJsonArray("media");

                for (JsonElement jsonElementMedia : jsonArrayMedia) {
                    JsonArray jsonArrayMediaMetadata = jsonElementMedia.getAsJsonObject().getAsJsonArray("media-metadata");

                    for (JsonElement jsonElementMediaMetadata : jsonArrayMediaMetadata) {
                        String format = jsonElementMediaMetadata.getAsJsonObject().get("format").getAsString();

                        if (format.equals("mediumThreeByTwo440")) {
                            String imageUrl = jsonElementMediaMetadata.getAsJsonObject().get("url").getAsString();
                            news.setImageUrl(imageUrl);
                        }
                    }
                }
                news.setUrl(url);
                news.setSection(section);
                news.setTitle(title);
                news.setDescription(description);
                news.setDate(date);
                news.setFavorite(false);

                newsList.add(news);
            }
        }
        return newsList;
    }
}
