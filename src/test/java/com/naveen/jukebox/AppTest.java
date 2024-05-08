
package com.naveen.jukebox;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AppTest")
class AppTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * We are replacing the spliterator. Because output stream separator may be different from spring line seperator.
     * The output stream sometimes varies from compiler to compiler.
     * So Remove the spliterator replacement in case of test case failure.
     *  <pre>
     * {@code actual = actual.replaceAll("\\r\\n", "\n"); }
     * </pre>
     */
    @Test
    @DisplayName("Integration Test #1")
    void runTest1(){

        //Arrange
        List<String> arguments= new ArrayList<>(List.of("INPUT_FILE=jukebox-input.txt"));

        String expectedOutput = """
                Songs Loaded successfully
                1 Kiran
                Playlist ID - 1
                Playlist ID - 2
                Delete Successful
                Current Song Playing
                Song - South of the Border
                Album - No.6 Collaborations Project
                Artists - Ed Sheeran,Cardi.B,Camilla Cabello
                Playlist ID - 1
                Playlist Name - MY_PLAYLIST_1
                Song IDs - 1 4 5 6 7
                Playlist ID - 1
                Playlist Name - MY_PLAYLIST_1
                Song IDs - 1 4 5 6
                Current Song Playing
                Song - Cross Me
                Album - No.6 Collaborations Project
                Artists - Ed Sheeran,Chance The Rapper,PnB Rock
                Current Song Playing
                Song - Give Life Back To Music
                Album - Random Access Memories
                Artists - Daft Punk,Nile Rodgers
                Current Song Playing
                Song - South of the Border
                Album - No.6 Collaborations Project
                Artists - Ed Sheeran,Cardi.B,Camilla Cabello
                Current Song Playing
                Song - Give Life Back To Music
                Album - Random Access Memories
                Artists - Daft Punk,Nile Rodgers
                Current Song Playing
                Song - Cross Me
                Album - No.6 Collaborations Project
                Artists - Ed Sheeran,Chance The Rapper,PnB Rock
                Given song id is not a part of the active playlist""";
        //Act
        App.run(arguments);
        String actual = outputStreamCaptor.toString().trim();
        actual = actual.replaceAll("\\r\\n", "\n");
        //Assert
        Assertions.assertEquals(expectedOutput,actual);

    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

}
