import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductReader
{
    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec= "";
        ArrayList <Product> productArrayList = new ArrayList<>();

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
                    rec = reader.readLine();
                    String[] lineInArray = rec.split(", ");
                    Product item = new Product(lineInArray[0], lineInArray[1], lineInArray[2], Double.parseDouble(lineInArray[3]));
                    productArrayList.add(item);
                }
                reader.close();
                System.out.printf("%-30s%-30s%-30s%-30s\n", "ID#", "Name", "Description", "Cost");
                System.out.println("=".repeat(100));
                for(Product listedProduct : productArrayList)
                {
                    System.out.printf("%-30s%-30s%-30s%-30s\n\n", listedProduct.getID(), listedProduct.getName(), listedProduct.getDescription(), listedProduct.getCost());
                }
                System.out.println("\n\nData file read!");
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
