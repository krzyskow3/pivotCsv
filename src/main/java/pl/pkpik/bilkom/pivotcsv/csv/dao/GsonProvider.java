package pl.pkpik.bilkom.pivotcsv.csv.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface GsonProvider {

    Gson GSON = new GsonBuilder().setPrettyPrinting().create();

}
