package edu.volstate.flickrliker.controllers;

import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.volstate.flickrliker.R;
import edu.volstate.flickrliker.models.Favorites;

public class FavoritesController {

    public static void removeFavorite(ImageButton ibFavorite, ArrayList<Favorites> favoritesArrayList, Favorites favorites, View view, RecyclerView recyclerView) {
        String folderName = view.getContext().getApplicationContext().getFilesDir().toString();
        File favoritesFileName = new File(folderName, "favorites.json");
        // if they un-favorite it remove from the list and notify the recyclerview that the arraylist was updated
        favoritesArrayList.remove(favorites);
        // Write the list to favorites.json
        try {
            FileController.writeFavoritesToFile(favoritesFileName, favoritesArrayList);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.getAdapter().notifyDataSetChanged();
        // We also need to remove the favorite from the list we got from Flickr...

    }
}
