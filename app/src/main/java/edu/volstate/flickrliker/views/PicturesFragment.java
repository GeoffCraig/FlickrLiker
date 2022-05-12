package edu.volstate.flickrliker.views;

import android.graphics.Picture;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.volstate.flickrliker.R;
import edu.volstate.flickrliker.adapters.PictureAdapter;
import edu.volstate.flickrliker.controllers.FileController;
import edu.volstate.flickrliker.controllers.PictureDataController;
import edu.volstate.flickrliker.models.Favorites;
import edu.volstate.flickrliker.models.FlickrPicture;
import edu.volstate.flickrliker.models.SearchTerms;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PicturesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PicturesFragment extends Fragment {

    View rootView;
    public ArrayList<FlickrPicture> flickrPictureArrayList = new ArrayList<>();
    private PictureAdapter pictureAdapter;
    private RecyclerView recyclerView;
    public ArrayList<SearchTerms> searchTermsArrayList = new ArrayList<>();
    public String searchTerms = "";
    public ArrayList<Favorites> favoritesArrayList = new ArrayList<>();

    public PicturesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PicturesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PicturesFragment newInstance(String param1, String param2) {
        PicturesFragment fragment = new PicturesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pictures, container, false);
        String folderName = rootView.getContext().getApplicationContext().getFilesDir().toString();
        recyclerView = rootView.findViewById(R.id.rv_Pictures);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        // Need to pull favorites list
        File favoritesFileName = new File(folderName, "favorites.json");
        try {
            if(FileController.fileExists(favoritesFileName)) {
                favoritesArrayList = FileController.getFavoritesFromFile(favoritesFileName);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        pictureAdapter = new PictureAdapter(rootView.getContext(), flickrPictureArrayList, favoritesArrayList, recyclerView);
        recyclerView.setAdapter(pictureAdapter);
        // Need to pull search terms from the searchTerms.jsom
        File fileName = new File(folderName, "searchTerms.json");
        try {
            if(FileController.fileExists(fileName)) {
                searchTermsArrayList = FileController.getSearchTermsFromFile(fileName);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        // If it doesn't exist or is empty default to Nashville dogs
        if(searchTermsArrayList.size() == 0) {
            searchTerms = "Nashville%20dogs";
        } else {
            searchTermsArrayList.forEach((term) -> {
                searchTerms = searchTerms + term.getTerm() + "%20";
            });
        }

        PictureDataController pictureDataController = new PictureDataController(getContext());
        pictureDataController.getPictures(flickrPictureArrayList, searchTerms, new PictureDataController.VolleyGetPictures() {
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<FlickrPicture> flickrPictureArrayList) {
                //recyclerView.getAdapter().notifyDataSetChanged();
                Log.i("PicturesFragment:onResponse", "Size "+favoritesArrayList.size());
                if(favoritesArrayList != null || favoritesArrayList.size() > 0) {
                    for (Favorites f : favoritesArrayList) {
                        String id = f.getId();
                        Log.i("PicturesFragment:onResponse:Favorite", id);
                        for (FlickrPicture fp: flickrPictureArrayList) {
                            Log.i("PicturesFragment:onResponse:Flicker", fp.getId());
                            if(fp.getId().equals(id)) {
                                Log.i("PicturesFragment:onResponse:Match", id + " " + fp.getId());
                                fp.setFavorite(true);
                            }
                        }
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        return rootView;
    }
}