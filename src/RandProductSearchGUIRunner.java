import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE;

public class RandProductSearchGUIRunner extends JFrame
{
    JPanel mainPnl;
    JPanel subMain;

    JPanel displayOGPnl;
    JPanel displayFilteredPnl;
    JPanel controlPnl;

    //displaying
    JTextArea displayOGTextTA;
    JScrollPane OGTextScroller;

    JTextArea displayFilteredTextTA;
    JScrollPane filteredScroller;

    JButton loadFileBtn;
    JButton searchFileBtn;
    JButton quitBtn;

    java.util.List<String> list = new LinkedList<>(Arrays.asList());
    String check = "";

    public RandProductSearchGUIRunner()
    {
        mainPnl = new JPanel();
        mainPnl.setLayout(new GridLayout(2,1));

        createDisplayPanel();
        subMain = new JPanel();
        subMain.setLayout(new GridLayout(1, 2));
        subMain.setBorder(new TitledBorder(new EtchedBorder(), "Text"));

        subMain.add(displayOGPnl, BorderLayout.WEST);
        subMain.add(displayFilteredPnl, BorderLayout.EAST);

        mainPnl.add(subMain);

        createControlPanel();
        mainPnl.add(controlPnl);

        add(mainPnl);
        setSize(900,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createDisplayPanel()
    {
        displayOGPnl = new JPanel();
        displayOGTextTA = new JTextArea(17,60);
        displayOGTextTA.setFont(new Font("Monospaced", Font.PLAIN, 12));
        OGTextScroller = new JScrollPane(displayOGTextTA);
        displayOGPnl.add(OGTextScroller);

        displayFilteredPnl = new JPanel();
        displayFilteredTextTA = new JTextArea(17,60);
        displayFilteredTextTA.setFont(new Font("Monospaced", Font.PLAIN, 12));
        filteredScroller = new JScrollPane(displayFilteredTextTA);
        displayFilteredPnl.add(filteredScroller);
    }

    private void createControlPanel()
    {
        controlPnl = new JPanel();
        controlPnl.setBorder(new TitledBorder(new EtchedBorder(), "Controls"));

        loadFileBtn = new JButton("Load File");
        controlPnl.add(loadFileBtn);
        loadFileBtn.addActionListener((ActiveEvent_ae) ->
        {
            loadFile();
        });


        searchFileBtn = new JButton("Search File");
        controlPnl.add(searchFileBtn);
        searchFileBtn.addActionListener((ActiveEvent_ae) ->
        {
            searchFile();
        });


        quitBtn = new JButton("Quit");
        controlPnl.add(quitBtn);
        quitBtn.addActionListener((ActiveEvent_ae) ->
        {
            int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?","Select an Option", JOptionPane.YES_NO_CANCEL_OPTION);
            if(input == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        });
    }

    private void loadFile()
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec= "";

        displayOGTextTA.setText("");
        displayFilteredTextTA.setText("");

        try
        {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();

                Stream lines = Files.lines(Paths.get(selectedFile.toURI()));
                list = lines.toList();
                list.forEach(item -> displayOGTextTA.append(item.concat("\n")));
            }
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "File not found");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void searchFile()
    {
        displayFilteredTextTA.setText("");

        if(displayOGTextTA.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "You have not yet entered any text. \nPlease enter text before filtering");
        }
        else {
            String hold = JOptionPane.showInputDialog("Please enter a filter")
                    .toLowerCase();

            List<String> filteredList = list.stream()
                    .filter(word -> word.toLowerCase().contains(hold))
                    .collect(Collectors.toList());

            String display = "";
            for (int i = 0; i < filteredList.stream().count(); i++) {
                display += filteredList.get(i) + "\n";
            }

            displayFilteredTextTA.append(display);
        }
    }
}
