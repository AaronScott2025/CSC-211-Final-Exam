package pkgloveheart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Questions {

	public String[] loadQuestions() throws IOException {
		String fileName = "q.txt"; // Questions file
		
		ArrayList<String> data = new ArrayList<>(); //Store data in a non-fixed sized array.

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Make into a static size
        String[] Questions = data.toArray(new String[0]);
        return Questions;
	}
}
