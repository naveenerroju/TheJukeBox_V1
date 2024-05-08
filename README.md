# The Juke Box

The Jukebox V1 is a simpler version of my Jukebox project. It is an in memory application. we can add users, songs and create playlists of users for users.

## Operations

1. Load Data
2. Create User
3. Create Playlist
4. Delete Playlist
5. Play playlist.
6. Modify Playlist
7. Play Song

## Load data:

Provide a valid CSV file path. With song all fields. Program will read all the valid columns as song fields and store in in-memory data. Refer the existing csv file for the format.

**Elements**: Id, song title, genre, Album, Song artist, Collaborators.

Collaborators should be separated with ‘#’ symbol. Example: Ed Sheeran#Cardi.B#Camilla Cabello

**Command**: LOAD-DATA

**Input**: file path

**Output**: no output.

## Create User:

We need users to create playlists or play songs from playlists.

**Command**: CREATE-USER

**Input**: username (no spaces)

**Output**: userId, Username

## Create Playlist:

Create a playlist for a user

**Command**: CREATE-PLAYLIST

**Input**: userId, playlist name, song ids (you can add multiple song ids)

**Output**: playlist id

## Delete Playlist:

Deletes a playlist for a user

**Command**: DELETE-PLAYLIST

**Input**: userId, playlist id

**Output**: result String

## Play Playlist:

Plays a playlist for the user.  Playlist starts from the first song of the playlist. There are two sub operations in this single operation.

1. NEXT
2. BACK

**Command**: PLAY-PLAYLIST

**Input**: userId, playlist id/sub operation command

**Output**: result String, song details

## Modify Playlist:

Modifies a playlist. There are two sub operations in this single operation.

1. ADD-SONG
2. DELETE-SONG

**Command**: PLAY-PLAYLIST

**Input**: sub operation command, userId, playlist id, song id

**Output**: result String, song details
