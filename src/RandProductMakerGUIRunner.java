import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMakerGUIRunner extends JFrame
{
    static final String FILEPATH = "C:\\Users\\rmp12\\IdeaProjects\\FileStreams\\src\\RandProductText.txt";
    public static final int ID_PADDED_LENGTH = 6;
    public static final int NAME_PADDED_LENGTH = 35;
    public static final int DESCRIPTION_PADDED_LENGTH = 75;
    public static final int COST_PADDED_LENGTH = 8;

    JPanel mainPnl;

    JPanel formPnl;
    JPanel namePnl, descriptionPnl, idPnl, costPnl;
    JTextField nameTF, descriptionTF, idTF, costTF;
    JLabel nameLbl, descriptionLbl, idLbl, costLbl;

    JPanel countPnl;
    JTextField countTF;
    JLabel countLbl;

    JPanel controlPnl;
    JButton addBtn, quitBtn;

    String name, description, id;
    double cost;

    int recordCount = 0;
    boolean hasValidProperties;

    public RandProductMakerGUIRunner()
    {
        mainPnl = new JPanel();
        mainPnl.setLayout(new GridLayout(2,1));

        createFormPnl();
        mainPnl.add(formPnl);

        createControlPnl();
        mainPnl.add(controlPnl);

        add(mainPnl);
        setSize(600,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createFormPnl()
    {
        formPnl = new JPanel();
        formPnl.setLayout(new GridLayout(5, 1));
        namePnl = new JPanel();
        descriptionPnl = new JPanel();
        idPnl = new JPanel();
        costPnl = new JPanel();
        countPnl = new JPanel();

        nameLbl = new JLabel("Name");
        nameTF = new JTextField(15);
        namePnl.add(nameLbl);
        namePnl.add(nameTF);

        descriptionLbl = new JLabel("Description");
        descriptionTF = new JTextField(19);
        descriptionPnl.add(descriptionLbl);
        descriptionPnl.add(descriptionTF);

        idLbl = new JLabel("ID");
        idTF = new JTextField(14);
        idPnl.add(idLbl);
        idPnl.add(idTF);

        costLbl = new JLabel("Cost");
        costTF = new JTextField(15);
        costPnl.add(costLbl);
        costPnl.add(costTF);

        countLbl = new JLabel("Count");
        countTF = new JTextField(15);
        countTF.setText("" + recordCount);
        countPnl.add(countLbl);
        countPnl.add(countTF);

        formPnl.add(namePnl);
        formPnl.add(descriptionPnl);
        formPnl.add(idPnl);
        formPnl.add(costPnl);
        formPnl.add(countPnl);
    }

    private void createControlPnl()
    {
        controlPnl = new JPanel();

        addBtn = new JButton("Add");
        controlPnl.add(addBtn);
        addBtn.addActionListener((ActionEvent_ae) ->
        {
            getProductFields();

            if (hasValidProperties) {
                Product product = new Product(id, name, description, cost);

                try {
                    //if I get rid of the position parameter in writeToFile method I can remove 0
                    writeProductToFile(FILEPATH, product);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                recordCount++;
                countTF.setText("" + recordCount);
                clearFields();
            }
        });

        quitBtn = new JButton("Quit");
        controlPnl.add(quitBtn);
        quitBtn.addActionListener((ActionEvent_ae) ->
        {
            int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?","Select an Option", JOptionPane.YES_NO_CANCEL_OPTION);
            if(input == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        });
    }

    //would have to remove position parameter to do file.seek(file.length()) which would mean it writes to the last spot
    private void writeProductToFile(String filePath, Product product)
            throws IOException {

        RandomAccessFile file = new RandomAccessFile(filePath, "rw");

        String paddedID = padProductFields(product.getID(), ID_PADDED_LENGTH, "0");
        String paddedName = padProductFields(product.getName(), NAME_PADDED_LENGTH, " ");
        String paddedDescription = padProductFields(product.getDescription(), DESCRIPTION_PADDED_LENGTH, " ");
        String paddedCost = padProductFields(String.valueOf(product.getCost()), COST_PADDED_LENGTH, " ");

        file.seek(file.length());
        file.write(paddedID.getBytes());
        file.write(paddedName.getBytes());
        file.write(paddedDescription.getBytes());
        file.write(paddedCost.getBytes());
        file.close();
    }

    private String padProductFields(String field, int padLength, String padding)
    {
        int repeated = padLength - field.length();
        return padding.repeat(repeated) + field;
    }

    private void clearFields()
    {
        nameTF.setText("");
        descriptionTF.setText("");
        idTF.setText("");
        costTF.setText("");
    }

    private void getProductFields()
    {
        name = nameTF.getText();
        boolean nameCheck = SafeInput.checkNonZeroLenStr(name);

        description = descriptionTF.getText();
        boolean descriptionCheck = SafeInput.checkNonZeroLenStr(description);

        id = idTF.getText();
        boolean idCheck = SafeInput.checkNonZeroLenStr(id);

        boolean costCheck = SafeInput.checkDouble(costTF.getText());
        cost = Double.parseDouble(costTF.getText());

        hasValidProperties = nameCheck && descriptionCheck && idCheck && costCheck;
    }
}
