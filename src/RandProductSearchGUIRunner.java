import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RandProductSearchGUIRunner extends JFrame
{
    static final String FILEPATH = "C:\\Users\\rmp12\\IdeaProjects\\FileStreams\\src\\RandProductText.txt";
    private final int TOTAL_PADDED_LENGTH = 124;
    private static final int ID_PADDED_LENGTH = 6;
    private static final int NAME_PADDED_LENGTH = 35;
    private static final int DESCRIPTION_PADDED_LENGTH = 75;
    private static final int COST_PADDED_LENGTH = 8;

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

    JButton searchBtn;
    JButton quitBtn;

    List<String> list = new LinkedList<>(Arrays.asList());

    public RandProductSearchGUIRunner() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new GridLayout(2,1));

        createDisplayPanel();
        subMain = new JPanel();
        subMain.setLayout(new GridLayout(1, 1));
        subMain.setBorder(new TitledBorder(new EtchedBorder(), "Text"));

        subMain.add(displayFilteredPnl);

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
        displayFilteredPnl = new JPanel();
        displayFilteredTextTA = new JTextArea(17,100);
        displayFilteredTextTA.setFont(new Font("Monospaced", Font.PLAIN, 12));
        filteredScroller = new JScrollPane(displayFilteredTextTA);
        displayFilteredPnl.add(filteredScroller);
    }

    private void createControlPanel()
    {
        controlPnl = new JPanel();
        controlPnl.setBorder(new TitledBorder(new EtchedBorder(), "Controls"));

        searchBtn = new JButton("Search");
        controlPnl.add(searchBtn);
        searchBtn.addActionListener((ActiveEvent_ae) ->
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

    private void searchFile() {
        displayFilteredTextTA.setText("");

            String filterWord = JOptionPane.showInputDialog("Please enter a filter")
                    .toLowerCase();

            String display = "";

        try {
            RandomAccessFile file = new RandomAccessFile(FILEPATH, "r");

            Product product = null;

            for(int i = 0; i < file.length(); i += TOTAL_PADDED_LENGTH)
            {
                product = readProductFromFile(file, i);
                if(product.getName().toLowerCase().contains(filterWord))
                {
                    display += product.toString() + "\n";
                }
            }
            file.close();

            displayFilteredTextTA.append(display);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Product readProductFromFile(RandomAccessFile file, int position)
            throws IOException {

        Product product = new Product();
        byte[] bytes = null;

        file.seek(position);
        bytes = new byte[ID_PADDED_LENGTH];
        file.read(bytes);
        product.setID(new String(bytes, StandardCharsets.UTF_8).trim());

        bytes = new byte[NAME_PADDED_LENGTH];
        file.read(bytes);
        product.setName(new String(bytes, StandardCharsets.UTF_8).trim());

        bytes = new byte[DESCRIPTION_PADDED_LENGTH];
        file.read(bytes);
        product.setDescription(new String(bytes, StandardCharsets.UTF_8).trim());

        bytes = new byte[COST_PADDED_LENGTH];
        file.read(bytes);
        String cost = new String(bytes, StandardCharsets.UTF_8).trim();
        product.setCost(Double.parseDouble(cost));

        return product;

    }
}
