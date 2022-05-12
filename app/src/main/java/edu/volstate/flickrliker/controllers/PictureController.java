package edu.volstate.flickrliker.controllers;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.volstate.flickrliker.R;
import edu.volstate.flickrliker.models.Favorites;
import edu.volstate.flickrliker.models.FlickrPicture;

public class PictureController {

    public static void setFavorite(ImageButton ibFavorite, ArrayList<Favorites> favoritesArrayList, FlickrPicture flickrPicture, View view, RecyclerView recyclerView) {
        String folderName = view.getContext().getApplicationContext().getFilesDir().toString();
        File favoritesFileName = new File(folderName, "favorites.json");
        if((Integer) ibFavorite.getTag() == R.drawable.favorite_border) {
            ibFavorite.setImageResource(R.drawable.favorites);
            ibFavorite.setTag(R.drawable.favorites);
            // Need to add this to the favoritesArrayList
            favoritesArrayList.add(new Favorites(
                    flickrPicture.getId(),
                    flickrPicture.getSecret(),
                    flickrPicture.getServer(),
                    flickrPicture.getTitle()
            ));
            // Write the list to favorites.json
            try {
                FileController.writeFavoritesToFile(favoritesFileName, favoritesArrayList);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ibFavorite.setImageResource(R.drawable.favorite_border);
            ibFavorite.setTag(R.drawable.favorite_border);
            // Need to remove this from the favoriteArrayList
            favoritesArrayList.removeIf(f -> f.getId().equals(flickrPicture.getId()));
            flickrPicture.setFavorite(false);
            recyclerView.getAdapter().notifyDataSetChanged();
            // Write the list to favorites.json
            try {
                FileController.writeFavoritesToFile(favoritesFileName, favoritesArrayList);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
