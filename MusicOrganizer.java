import java.util.ArrayList;
import java.util.Random;

/**
 * A class to hold details of audio tracks.
 * Individual tracks may be played.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class MusicOrganizer
{
    // An ArrayList for storing music tracks.
    private ArrayList<Track> tracks;
    // A player for the music tracks.
    private MusicPlayer player;
    // A reader that can read music files and load them as tracks.
    private TrackReader reader;
    // A random object that can generate random numbers for shuffling tracks.
    private Random random;
    /**
     * Create a MusicOrganizer
     */
    public MusicOrganizer()
    {
        tracks = new ArrayList<>();
        player = new MusicPlayer();
        reader = new TrackReader();
        random = new Random();
        readLibrary("../audio");
        System.out.println("Music library loaded. " + getNumberOfTracks() + " tracks.");
        System.out.println();
    }

    /**
     * Add a track file to the collection.
     * @param filename The file name of the track to be added.
     */
    public void addFile(String filename)
    {
        tracks.add(new Track(filename));
    }

    /**
     * Add a track to the collection.
     * @param track The track to be added.
     */
    public void addTrack(Track track)
    {
        tracks.add(track);
    }

    /**
     * Play a track in the collection.
     * @param index The index of the track to be played.
     */
    public void playTrack(int index)
    {
        if(indexValid(index)) {
            Track track = tracks.get(index);
            player.playSample(track.getFilename());
            System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
        }
    }

    /**
     * Return the number of tracks in the collection.
     * @return The number of tracks in the collection.
     */
    public int getNumberOfTracks()
    {
        return tracks.size();
    }

    /**
     * List a track from the collection.
     * @param index The index of the track to be listed.
     */
    public void listTrack(int index)
    {
        System.out.print("Track " + index + ": ");
        Track track = tracks.get(index);
        System.out.println(track.getDetails());
    }

    /**
     * Show a list of all the tracks in the collection.
     */
    public void listAllTracks()
    {
        System.out.println("Track listing: ");

        for(Track track : tracks) {
            System.out.println(track.getDetails());
        }
        System.out.println();
    }

    /**
     * List all tracks by the given artist.
     * @param artist The artist's name.
     */
    public void listByArtist(String artist)
    {
        for(Track track : tracks) {
            if(track.getArtist().contains(artist)) {
                System.out.println(track.getDetails());
            }
        }
    }

    /**
     * Remove a track from the collection.
     * @param index The index of the track to be removed.
     */
    public void removeTrack(int index)
    {
        if(indexValid(index)) {
            tracks.remove(index);
        }
    }

    /**
     * Play the first track in the collection, if there is one.
     */
    public void playFirst()
    {
        if(tracks.size() > 0) {
            player.startPlaying(tracks.get(0).getFilename());
        }
    }

    /**
     * Stop the player.
     */
    public void stopPlaying()
    {
        player.stop();
    }

    /**
     * Determine whether the given index is valid for the collection.
     * Print an error message if it is not.
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index)
    {
        // The return value.
        // Set according to whether the index is valid or not.
        boolean valid;

        if(index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        }
        else if(index >= tracks.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }

    private void readLibrary(String folderName)
    {
        ArrayList<Track> tempTracks = reader.readTracks(folderName, ".mp3");

        // Put all thetracks into the organizer.
        for(Track track : tempTracks) {
            addTrack(track);
        }
    }

    /**
     * Play a single random track from the collection.
     * This method selects a random track from the list and plays it.
     * If the collection is empty, a message is printed.
     */
    public void playRandomTrack() {
        if (!tracks.isEmpty()) {
            // Use the existing random object to generate a random index
            int randomIndex = random.nextInt(tracks.size());

            // Retrieve the track at the random index
            Track track = tracks.get(randomIndex);

            // Play the selected track
            player.startPlaying(track.getFilename());
            System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
        } else {
            // Print a message if there are no tracks to play
            System.out.println("No tracks to play.");
        }
    }

    /**
 * Play all tracks in the collection in a random order.
 */
public void shuffle() {
    // Create a new ArrayList to store the tracks in a random order
    ArrayList<Track> shuffledTracks = new ArrayList<Track>();

    // Copy the tracks from the original collection to the new collection
    shuffledTracks.addAll(tracks);

    // Clear the original collection
    tracks.clear();

    // Play all tracks in the shuffled collection
    while (!shuffledTracks.isEmpty()) {
        // Use the existing random object to generate a random index
        int randomIndex = random.nextInt(shuffledTracks.size());

        // Retrieve the track at the random index
        Track track = shuffledTracks.get(randomIndex);

        // Play the selected track
        player.startPlaying(track.getFilename());
        System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());

        // Sleep for 10 seconds
        try {
            Thread.sleep(10000); // Sleep for 10,000 milliseconds = 10 seconds
        } catch (InterruptedException e) {
            // Handle interruption
            Thread.currentThread().interrupt();
            System.out.println("Playback interrupted");
        }

        // Stop the playing track after 10 seconds
        player.stop();
        System.out.println("Stopped playing: " + track.getArtist() + " - " + track.getTitle());

        // Remove the track from the shuffled collection
        shuffledTracks.remove(randomIndex);
    }
}


}
