package com.naveen.jukebox.utility;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.naveen.jukebox.model.Playlist;
import com.naveen.jukebox.model.Songs;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

/**
 * This is a Utility class that has multiple static methods. Initialisation of this class is not possible.
 */
public class DataUtility {

    /**
     * Constructor is private intentionally. To hide the public constructor so initialisation is not possible.
     */
    private DataUtility() {
    }

    /**
     * Reads all lines from a file into a list of strings.
     * <p>
     * This method attempts to read all lines from the specified file located by the {@code fileName} path.
     * It uses UTF-8 character encoding to read the file. If an IOException occurs during reading,
     * the exception is caught and printed to the standard error stream, and an empty list is returned.
     * </p>
     *
     * @param fileName The path to the file as a string.
     * @return A list of strings where each string represents a line in the file. If the file cannot be
     * read, or if an IOException is caught, an empty list is returned.
     * @implNote The method utilizes {@link java.nio.file.Files#readAllLines} which can throw
     * {@link java.io.IOException} if an I/O error occurs reading from the file or a malformed or
     * unmappable byte sequence is read. In such cases, the method handles the exception by
     * catching it and printing the stack trace, then continues to return an empty list.
     */
    public static List<String> readFileInList(String fileName) {

        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(
                    Paths.get(fileName),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * Reads a CSV (Comma-Separated Values) file and returns its contents as a list of string arrays.
     * <p>
     * This method reads the contents of the specified CSV file located at the given {@code filePath}.
     * It utilizes the OpenCSV library to parse the CSV file. The method returns a list of string arrays,
     * where each string array represents a row in the CSV file. Each element in the string array
     * corresponds to a cell value in the respective row of the CSV file.
     * </p>
     * <p>
     * If any errors occur during file reading, such as the file not being found or being inaccessible,
     * an IOException is thrown, and the stack trace is printed to the standard error stream. In such cases,
     * an empty list is returned to indicate the failure to read the file.
     * </p>
     *
     * @param filePath The path to the CSV file to be read.
     * @return A list of string arrays representing the contents of the CSV file. If the file cannot be
     * read or if an IOException occurs, an empty list is returned.
     * @throws CsvException If an error occurs during parsing of the CSV file.
     * @see <a href="https://opencsv.sourceforge.io/">OpenCSV</a>
     */
    public static List<String[]> readCsvFile(String filePath) throws CsvException {
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            return csvReader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    /**
     * Converts a list of string arrays into a list of {@link Songs} objects. Each string array represents
     * song data where the fields are expected to be in a specific order: ID, Title, Genre, Album, Owner,
     * and Collaborations. Collaborations are expected to be a single string delimited by '#'.
     * <p>
     * The method iterates over each string array, constructing a {@link Songs} object for each, setting
     * its properties according to the array index:
     * <ul>
     *     <li>Index 0: Song ID (expects an integer value)</li>
     *     <li>Index 1: Song Title (expects a string)</li>
     *     <li>Index 2: Genre (expects a string)</li>
     *     <li>Index 3: Album (expects a string)</li>
     *     <li>Index 4: Owner (expects a string)</li>
     *     <li>Index 5: Collaborations (expects a string, split by '#')</li>
     * </ul>
     * Note that if the array length or the data types do not match these expectations, the behavior
     * of the method may not be defined, and errors such as {@link NumberFormatException} could occur.
     *
     * @param data A list of string arrays, where each array contains the data for a song in the order
     *             specified above.
     * @return A list of {@link Songs} objects, each populated with data from the input arrays. If
     * a data entry has fewer fields or fields that cannot be correctly parsed (e.g., non-numeric
     * ID), the method may produce unexpected results or runtime exceptions.
     */
    public static List<Songs> convertDataSongs(List<String[]> data) {
        List<Songs> songs = new ArrayList<>();

        for (String[] s : data) {
            Songs song = new Songs();
            for (int i = 0; i < s.length; i++) {
                switch (i) {
                    case 0 -> song.setId(Integer.parseInt(s[i]));
                    case 1 -> song.setTitle(s[i]);
                    case 2 -> song.setGenre(s[i]);
                    case 3 -> song.setAlbum(s[i]);
                    case 4 -> song.setOwner(s[i]);
                    case 5 -> {
                        List<String> collabs = List.of(s[i].split("#"));
                        song.setCollaboration(collabs);
                    }
                    default -> {
                        break;
                    }
                }
            }
            songs.add(song);
        }
        return songs;
    }


    /**
     * Prints a list of songs to the standard output.
     * <p>
     * This method iterates over a list of {@link Songs} objects and prints each song's details to the standard output
     * using the song's {@code toString()} method. Each song is printed on a new line.
     * </p>
     *
     * @param songs A list of {@link Songs} objects to be printed. If the list is empty, nothing will be printed.
     */
    public static void printSongs(List<Songs> songs) {
        for (Songs song : songs) {
            DataUtility.printOutput(song.toString());
        }
    }


    /**
     * Converts a subset of a string array into an integer array.
     * <p>
     * This method starts from the fourth element of the provided string array and attempts to convert each
     * string element to an integer, storing the results in a new integer array. The first three elements
     * of the input array are ignored. If any string in the array cannot be converted to an integer, a
     * {@link NumberFormatException} will be thrown.
     * </p>
     *
     * @param sarr The input string array containing strings which need to be converted to integers,
     *             starting from the fourth element of the array.
     * @return An integer array containing the converted values from the fourth element onwards of the
     * input string array.
     * @throws NumberFormatException if any string from the fourth element onward cannot be parsed
     *                               into an integer.
     */
    public static int[] stringArrayToIntArray(String[] sarr) {
        int[] result = new int[sarr.length - 3];
        for (int i = 3; i < sarr.length; i++) {
            result[i - 3] = Integer.parseInt(sarr[i]);
        }
        return result;
    }


    /**
     * Constructs a formatted string detailing the currently playing song.
     *
     * @param song The {@link Songs} object containing details of the current song.
     * @return A formatted string that includes the title, album, and artist(s) of the song.
     */
    public static String constructSongResponse(Songs song) {
        return "Current Song Playing\n" +
                "Song - " + song.getTitle() + "\n" +
                "Album - " + song.getAlbum() + "\n" +
                "Artists - " + getCollaboratorsString(song);
    }

    /**
     * Generates a comma-separated string of collaborators from a song.
     *
     * @param song The {@link Songs} object containing the collaborators.
     * @return A string listing all collaborators separated by commas.
     */
    private static String getCollaboratorsString(Songs song) {
        List<String> collabs = song.getCollaboration();
        return String.join(",", collabs);
    }

    /**
     * Constructs a formatted string detailing the playlist.
     *
     * @param playlist The {@link Playlist} object to be detailed.
     * @return A formatted string that includes the playlist ID, name, and song IDs.
     */
    public static String constructPlaylistResponse(Playlist playlist) {
        return "Playlist ID - " + playlist.getId() + "\n" +
                "Playlist Name - " + playlist.getTitle() + "\n" +
                "Song IDs - " + songsOfPlaylist(playlist.getSongs());
    }

    /**
     * Creates a space-separated string of song IDs from a list of songs.
     *
     * @param songs A list of {@link Songs} representing the songs in a playlist.
     * @return A string containing all song IDs from the list, separated by spaces.
     */
    private static String songsOfPlaylist(List<Songs> songs) {
        StringBuilder sb = new StringBuilder();
        for (Songs song : songs) {
            sb.append(song.getId()).append(" ");
        }
        return sb.substring(0, sb.length() - 1); // Remove the trailing space
    }

    /**
     * Prints the string in console
     * @param output string to print
     */
    public static void printOutput(String output){
        System.out.println(output);
    }

}
