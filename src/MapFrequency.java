import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
import javax.swing.JFileChooser;

import java.util.*;


public class MapFrequency {

    private HashMap<String, Integer> wordFrequency;

    public MapFrequency() {
        wordFrequency = new HashMap<>();
    }

    public Map<String, Integer> getWordCount()
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String hold = "";
        String key = "";

        try
        {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while(reader.ready())
                {
                    hold = onlyStr(reader.readLine());
                    String[] split = hold.split("}");

                    for(int i=0; i < split.length; i++)
                    {
                        key = split[i];

                        if(!Objects.equals(key, ""))
                        {
                            Integer store = wordFrequency.putIfAbsent(key, 1);

                            if(store != null) {
                                wordFrequency.replace(key, store + 1);
                            }
                        }
                    }
                }
                reader.close();
                System.out.println(wordFrequency);
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return wordFrequency;
    }

    private String
    onlyStr (String str)
    {
        // replace the given string
        // with empty string
        // except the pattern "[^a-z]"
        str = str.toLowerCase(Locale.ROOT);
        //str = str.replace("'", "");
        str = str.replaceAll("[^a-z]+", " ");

        // return string
        return str;
    }
}
