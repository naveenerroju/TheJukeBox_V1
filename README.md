# The Jukebox

The Jukebox V1 is a Java-based application utilizing the command design pattern to simulate a jukebox system. This application functions as an in-memory database for managing songs, users, and playlists. The system reads commands from input files to perform various operations like loading songs from a CSV file, creating users, and managing playlists.

We need a file that has the commands. Each file has set of commands. Make a list of file paths and send the list as an argument to the main method. The output will be printed in the console. Songs can be loaded using a csv file. This CSV file is needed to be in a particular format for parsing to model class. Refer the existing sample Songs csv file in the root folder of the project.

A sample Input file is already existing in the project, jukebox-input.txt. Refer this file for more information on the input. A integration testcase is already written in AppTest.java. Refer how the input is passing and what is the expected output for the specified input command.

## Features

- **Load Songs**: Load song details from a CSV file into the application's memory.
- **Create Users**: Add new users to the system.
- **Create Playlists**: Users can create playlists which can include any of the loaded songs.
- **Command Pattern**: Utilizes the command pattern to handle various operations dynamically.
- **In-Memory Data**: This project uses InMemoryData. You do not need any external database.

## Project Structure
The Jukebox V1 is organized into several directories under `src/main/java` and `src/test/java` for the application's source code and tests, respectively.

- **.gradle/**
- **gradle/**
- **src/**
    - **main/**
        - **java/**
            - **com/**
                - **naveen/**
                    - **jukebox/**
                        - **command/**
                            - IJukeCommands.java
                            - JukeboxCommands.java
                        - **model/**
                            - Constants.java
                            - CurrentlyPlaying.java
                            - Playlist.java
                            - Songs.java
                            - User.java
                        - **repository/**
                            - SongsRepository.java
                            - UserRepository.java
                        - **service/**
                            - SongsService.java
                            - UserService.java
                        - **utility/**
                            - DataUtility.java
                        - App.java
    - **test/**
        - **java/**
            - **com/**
                - **naveen/**
                    - **jukebox/**
                        - AppTest.java
- .gitignore
- build.gradle
- gradlew
- jukebox-input.txt
- notes.txt
- README.md
- songs.csv

This structure outlines the main components and files within the project, providing a clear map of where functionalities are located and how the project is organized.

## Operations

1. Load Data
2. Create User
3. Create Playlist
4. Delete Playlist
5. Play playlist.
6. Modify Playlist
7. Play Song

### Load data:

Provide a valid CSV file path. With song all fields. Program will read all the valid columns as song fields and store in in-memory data. Refer the existing csv file for the format.

**Elements**: Id, song title, genre, Album, Song artist, Collaborators.

Collaborators should be separated with ‘#’ symbol. Example: Ed Sheeran#Cardi.B#Camilla Cabello

**Command**: LOAD-DATA

**Input**: file path

**Output**: no output.

### Create User:

We need users to create playlists or play songs from playlists.

**Command**: CREATE-USER

**Input**: username (no spaces)

**Output**: userId, Username

### Create Playlist:

Create a playlist for a user

**Command**: CREATE-PLAYLIST

**Input**: userId, playlist name, song ids (you can add multiple song ids)

**Output**: playlist id

### Delete Playlist:

Deletes a playlist for a user

**Command**: DELETE-PLAYLIST

**Input**: userId, playlist id

**Output**: result String

### Play Playlist:

Plays a playlist for the user.  Playlist starts from the first song of the playlist. There are two sub operations in this single operation.

1. NEXT
2. BACK

**Command**: PLAY-PLAYLIST

**Input**: userId, playlist id/sub operation command

**Output**: result String, song details

### Modify Playlist:

Modifies a playlist. There are two sub operations in this single operation.

1. ADD-SONG
2. DELETE-SONG

**Command**: PLAY-PLAYLIST

**Input**: sub operation command, userId, playlist id, song id

**Output**: result String, song details

# Testing
Integration tests are provided in the AppTest.java file which includes tests based on the example input and output provided in the project. This test alone will cover 80% of the code. Ensure to run these tests to verify the application's functionality after any changes.

# Files Included
Sample CSV File: songs.csv
Sample Input File: instructions.txt
Integration Test File: AppTest.java


## Input Files

Input files should contain one command per line. Here’s the format and example of commands you might include in an input file:

- `LOAD_DATA songs.csv` - Command to load songs from a specified CSV file.
- `CREATE_USER username` - Command to create a new user with the given username.
- `CREATE_PLAYLIST user_id playlist_name song_ids` - Command to create a playlist for a specified user. Song IDs should be provided in a space-separated list.

## Improved version
This is a version 1 of the Jukebox project. I enhanced it even further by making it a Rest API. Using Spring Boot and maven, I developed an interactive API. I used a real database and performed operations on database using rest endpoints.

Please refer my code repository at https://github.com/naveenerroju/TheJukeBox_API