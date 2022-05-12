package edu.volstate.flickrliker.controllers;


import static edu.volstate.flickrliker.MainActivity.API_KEY;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.volstate.flickrliker.models.FlickrPicture;
import edu.volstate.flickrliker.singletons.VolleySingleton;

public class PictureDataController {
    private Context context;

    public PictureDataController(Context context) { this.context = context; }

    // example url
    // https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=GET_YOUR_OWN_KEY&text=nashville&format=json&nojsoncallback=1

    // This is the interface for the callback for getPictures
    public interface VolleyGetPictures {
        void onError(String message);
        void onResponse(ArrayList<FlickrPicture> flickrPictureArrayList);
    }

    public void getPictures(ArrayList<FlickrPicture> flickrPictureArrayList, String searchTerms, VolleyGetPictures volleyGetPictures) {
        Log.i("PictureDataController:getPictures", searchTerms);
        String url = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=" + API_KEY + "&text="+searchTerms+"&format=json&nojsoncallback=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonPhotos = response.getJSONObject("photos");
                            JSONArray jsonPhotoArray = jsonPhotos.getJSONArray("photo");
                            for (int i = 0; i < jsonPhotoArray.length(); i++) {
                                JSONObject jsonPhoto = jsonPhotoArray.getJSONObject(i);
                                flickrPictureArrayList.add(new FlickrPicture(
                                        jsonPhoto.getString("id"),
                                        jsonPhoto.getString("secret"),
                                        jsonPhoto.getString("server"),
                                        jsonPhoto.getString("title"),
                                        false
                                ));
                                //volleyGetPictures.onResponse(flickrPictureArrayList);
                            }
                            volleyGetPictures.onResponse(flickrPictureArrayList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyGetPictures.onError(error.toString());
                    }
            });
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }



}
