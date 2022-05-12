package edu.volstate.flickrliker.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.volstate.flickrliker.R;
import edu.volstate.flickrliker.adapters.FavoriteAdapter;
import edu.volstate.flickrliker.controllers.FileController;
import edu.volstate.flickrliker.models.Favorites;
import edu.volstate.flickrliker.models.FlickrPicture;
import edu.volstate.flickrliker.models.SearchTerms;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {

    View rootView;
    public ArrayList<FlickrPicture> flickrPictureArrayList = new ArrayList<>();
    private FavoriteAdapter favoriteAdapter;
    private RecyclerView recyclerView;
    public ArrayList<Favorites> favoritesArrayList = new ArrayList<>();


    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        String folderName = rootView.getContext().getApplicationContext().getFilesDir().toString();
        recyclerView = rootView.findViewById(R.id.rv_Favorites);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        File favoritesFileName = new File(folderName, "favorites.json");
        try {
            if(FileController.fileExists(favoritesFileName)) {
                favoritesArrayList = FileController.getFavoritesFromFile(favoritesFileName);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        // Need to get a way to get the list of flickr pictures so we can remove
        // set the favorite boolean
        favoriteAdapter = new FavoriteAdapter(rootView.getContext(), flickrPictureArrayList, favoritesArrayList, recyclerView);
        recyclerView.setAdapter(favoriteAdapter);
        return rootView;
    }
}