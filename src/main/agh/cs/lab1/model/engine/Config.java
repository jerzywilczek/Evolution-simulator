package agh.cs.lab1.model.engine;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Config {
    public final int width, height, startEnergy, moveEnergy, plantEnergy, startAnimalAmount;
    public final double jungleRatio;


    private Config() {
        width = -1;
        height = -1;
        startEnergy = -1;
        moveEnergy = -1;
        plantEnergy = -1;
        jungleRatio = -1;
        startAnimalAmount = -1;
    }

    /**
     * Loads a config from a .json file
     * @param filename file to read from
     * @return config with values loaded from the JSON file
     * @throws IOException when <code>java.nio.file.Files.readString()</code> throws the exception or when values loaded from the file are wrong
     */
    public static Config loadFromJSON(String filename) throws IOException, JsonSyntaxException, NumberFormatException {
        Path file = Paths.get("./" + filename);
        Config config = new Gson().fromJson(Files.readString(file), Config.class);
        if (
                config.width <= 0 ||
                config.height <= 0 ||
                config.startEnergy <= 0 ||
                config.moveEnergy <= 0 ||
                config.plantEnergy <= 0 ||
                config.jungleRatio < 0 ||
                config.jungleRatio > 1 ||
                config.startAnimalAmount < 0
        ) {
            throw new IOException("parameters.json file has wrong values or format");
        }
        return config;
    }

    public static void generateParametersFile() throws IOException {
        Path file = Paths.get("./parameters.json");
        Files.writeString(file, new GsonBuilder().setPrettyPrinting().create().toJson(new Config()), StandardOpenOption.CREATE_NEW, StandardOpenOption.APPEND);
    }
}
