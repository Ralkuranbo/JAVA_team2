package routine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ProfileStore {
    private static final Map<String,Profile> profiles = new HashMap<>();
    private static final String FILE_PATH = "profile_data.csv";

    static { load(); }

    public static void save(Profile p) {
        profiles.put(p.getUsername(), p);
        saveToFile();
    }

    public static Profile get(String username) {
        return profiles.get(username);
    }

    private static void saveToFile() {
        try (BufferedWriter w = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(FILE_PATH), StandardCharsets.UTF_8))) {
            for (Profile p : profiles.values()) {
                // username,gender,age,height,weight
                w.write(p.getUsername() + ","
                      + p.getGenderStr() + ","
                      + p.getAge() + ","
                      + p.getHeight() + ","
                      + p.getWeight());
                w.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void load() {
        File f = new File(FILE_PATH);
        if (!f.exists()) return;
        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String user   = parts[0];
                    boolean gender= parts[1].equals("남자");
                    int age       = Integer.parseInt(parts[2]);
                    float height  = Float.parseFloat(parts[3]);
                    float weight  = Float.parseFloat(parts[4]);
                    profiles.put(user, new Profile(user, gender, age, height, weight));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
