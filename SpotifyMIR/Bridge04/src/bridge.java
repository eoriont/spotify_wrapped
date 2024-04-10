import java.io.*;
public class bridge {
    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("python", "C:/Users/colby/Desktop/O&D/spotify_wrapped/Bridge04/test02.py");
        Process process = builder.start();

        String[] songs = {"Blinding Lights", "Baby"};

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        for (String s: songs) {
            writer.write(s + "\n");
        }
        writer.flush();

        // Read result from Python
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();
        System.out.println( line);

        process.waitFor();
    }
}
