package edu.volstate.flickrliker.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.chip.ChipGroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.volstate.flickrliker.R;
import edu.volstate.flickrliker.controllers.FileController;
import edu.volstate.flickrliker.controllers.SettingsController;
import edu.volstate.flickrliker.models.Favorites;
import edu.volstate.flickrliker.models.SearchTerms;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private ArrayList<SearchTerms> searchTermsArrayList = new ArrayList<>();
    EditText etAddSearchTerm;
    ImageButton btnAddSearchTerm;
    ChipGroup cgSearchTerms;
    View rootView;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SettingsFragment:onCreate", "I was created!");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings_, container, false);
        btnAddSearchTerm = rootView.findViewById(R.id.btn_AddSearchTerm);
        btnAddSearchTerm.setOnClickListener(addSearchTerm);
        etAddSearchTerm = rootView.findViewById(R.id.et_AddSearchTerm);
        cgSearchTerms = rootView.findViewById(R.id.cg_searchterms);
        String folderName = rootView.getContext().getApplicationContext().getFilesDir().toString();
        Log.i("SettingsFragment:onCreateView", folderName);
        File fileName = new File(folderName, "searchTerms.json");
        try {
            searchTermsArrayList = SettingsController.populateUI(fileName, cgSearchTerms, getActivity());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return rootView;
        //return inflater.inflate(R.layout.fragment_settings_, container, false);
    }

    private final View.OnClickListener addSearchTerm = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if(etAddSearchTerm.getText().toString() == null || etAddSearchTerm.getText().toString().isEmpty()) {
                Toast.makeText(rootView.getContext(), "Please enter a search term", Toast.LENGTH_SHORT).show();
            }
            if (cgSearchTerms.getChildCount() < 3)
            {
                SettingsController.addSearchTerm(getActivity(), cgSearchTerms, etAddSearchTerm, searchTermsArrayList);
            } else {
                Toast.makeText(getContext(), "You can only have 3 search terms", Toast.LENGTH_SHORT).show();
            }

        }
    };
}