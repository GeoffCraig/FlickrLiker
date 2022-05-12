package edu.volstate.flickrliker.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import edu.volstate.flickrliker.models.Favorites;
import edu.volstate.flickrliker.models.SearchTerms;

public class FileController {

    public static void writeSearchTermstoFile(File filename, ArrayList<SearchTerms> searchTermsArrayList) throws IOException, JSONException {
        Gson gson = new Gson();
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(gson.toJson(searchTermsArrayList));
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean fileExists(File filename) throws IOException {
        return filename.exists();
    }
    // Thanks to https://medium.com/@nayantala259/android-how-to-read-and-write-parse-data-from-json-file-226f821e957a
    public static String readSearchTermsfromFile(File fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    public static ArrayList<SearchTerms> getSearchTermsFromFile(File fileName) throws IOException {
        ArrayList<SearchTerms> searchTermsArrayList;
        String jsonSearchTerms = FileController.readSearchTermsfromFile(fileName);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<SearchTerms>>() {}.getType();
        searchTermsArrayList = gson.fromJson(jsonSearchTerms, type);
        return searchTermsArrayList;
    }

    // This is duplicate code that I may refactor to only be one method
    public static void writeFavoritesToFile(File filename, ArrayList<Favorites> searchTermsArrayList) throws IOException, JSONException {
        Gson gson = new Gson();
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(gson.toJson(searchTermsArrayList));
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFavoritesfromFile(File fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    public static ArrayList<Favorites> getFavoritesFromFile(File fileName) throws IOException {
        ArrayList<Favorites> favoritesArrayList;
        String jsonFavorites = FileController.readFavoritesfromFile(fileName);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Favorites>>() {}.getType();
        favoritesArrayList = gson.fromJson(jsonFavorites, type);
        return favoritesArrayList;
    }
}
