package edu.volstate.flickrliker.controllers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import edu.volstate.flickrliker.MainActivity;
import edu.volstate.flickrliker.R;
import edu.volstate.flickrliker.models.SearchTerms;

public class SettingsController {

    public static void addSearchTerm(Activity activity, ChipGroup cgSearchTerms, EditText etAddSearchTerm, ArrayList<SearchTerms> searchTermsArrayList) {
        Chip newChip;
        String folderName = activity.getApplicationContext().getFilesDir().toString();
        File fileName = new File(folderName, "searchTerms.json");
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(activity.getApplicationContext());
            newChip = (Chip) layoutInflater.inflate(R.layout.chip_layout, cgSearchTerms, false);
            newChip.setText(etAddSearchTerm.getText().toString());
            SearchTerms searchTerms = new SearchTerms(etAddSearchTerm.getText().toString());
            searchTermsArrayList.add(searchTerms);
            newChip.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Log.i("SettingsController:addSearchTerm", "Clicked");
                }
            });
            newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("SettingsController:addSearchTerm", "Removing term");
                    cgSearchTerms.removeView(newChip);
                    searchTermsArrayList.remove(searchTerms);
                    try {
                        FileController.writeSearchTermstoFile(fileName, searchTermsArrayList);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            cgSearchTerms.addView(newChip);
            // This is where I need to flush everything to the searchTerms.json file
            // UPDATES NEEDED
            FileController.writeSearchTermstoFile(fileName, searchTermsArrayList);

            Toast.makeText(activity.getApplicationContext(),
                    String.valueOf(cgSearchTerms.getChildCount()), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(), "An error occurred, please try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static ArrayList<SearchTerms> populateUI(File fileName, ChipGroup cgSearchTerms, Activity activity) throws IOException {
        ArrayList<SearchTerms> searchTermsArrayList;
        if (FileController.fileExists(fileName)) {
            String jsonSearchTerms = FileController.readSearchTermsfromFile(fileName);
            searchTermsArrayList = new ArrayList<>();
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<SearchTerms>>() {}.getType();
            searchTermsArrayList = gson.fromJson(jsonSearchTerms, type);
            for(SearchTerms st : searchTermsArrayList) {
                // add a new chip
                Chip newChip;
                try {
                    LayoutInflater layoutInflater = LayoutInflater.from(activity.getApplicationContext());
                    newChip = (Chip) layoutInflater.inflate(R.layout.chip_layout, cgSearchTerms, false);
                    newChip.setText(st.getTerm());
                    ArrayList<SearchTerms> finalSearchTermsArrayList = searchTermsArrayList;
                    newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("SettingsController:addSearchTerm", "Removing term");
                            cgSearchTerms.removeView(newChip);
                            finalSearchTermsArrayList.remove(st);
                            try {
                                FileController.writeSearchTermstoFile(fileName, finalSearchTermsArrayList);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Need to call function that will update the arrayList and flush to json
                        }
                    });
                    cgSearchTerms.addView(newChip);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return searchTermsArrayList;
        } else {
            searchTermsArrayList = new ArrayList<>();
            return searchTermsArrayList;
        }
    }
}
