package routine;

import java.util.*;

public class WorkoutManager {
    private static final Map<String, List<WorkoutEntry>> userData = new HashMap<>();

    public static List<WorkoutEntry> getEntries(String username) {
        return userData.computeIfAbsent(username, k -> new ArrayList<>());
    }

    public static void addEntry(String username, WorkoutEntry entry) {
        getEntries(username).add(entry);
    }
}
