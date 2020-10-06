import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Demo extends JFrame {

    private static ObjectOutputStream objectOutputStream;
    private static Store store;
    private static MySQL database;
    static Scanner scanInput = new Scanner(System.in);
    static Scanner inputEnglish = new Scanner(System.in);
    static Scanner inputViet = new Scanner(System.in);

    private JPanel contentPaneLayout;
    private JTextField inputWordEnglish;
    private JTextField txtWordVietNam;
    private JTextField txtTypeWord;
    private JTextField txtExampleEnglish;
    private JTextField txtExampleViet;
    private boolean isCheckSearchEnglish = true;

    public static void main(String[] args) throws IOException, SQLException {


        //server
        try {
            database = new MySQL();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Demo frame = new Demo();
        frame.setUpView();
        frame.setVisible(true);
        frame.setSize(400, 400);


    }

    public void setUpView() throws SQLException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 300);
        contentPaneLayout = new JPanel();
        contentPaneLayout.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPaneLayout);
        contentPaneLayout.setLayout(null);

        JLabel lblNewLabel = new JLabel("Vietnamese");
        lblNewLabel.setBounds(320, 10, 92, 14);
        contentPaneLayout.add(lblNewLabel);

        JLabel lblNewLabel_5 = new JLabel("DICTIONARY");
        lblNewLabel_5.setBackground(UIManager.getColor("Button.darkShadow"));
        lblNewLabel_5.setBounds(230, 0, 80, 14);
        contentPaneLayout.add(lblNewLabel_5);

        JComboBox comboBox = new JComboBox();
        comboBox.addItem("English");
        comboBox.addItem("Vietnamese");
        comboBox.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox.getSelectedIndex() == 0){
                    lblNewLabel.setText("Vietnamese");
                    isCheckSearchEnglish = true;
                }else {
                    lblNewLabel.setText("English");
                    isCheckSearchEnglish = false;
                }
            }
        });
        comboBox.setBounds(10, 10, 150, 20);
        contentPaneLayout.add(comboBox);

//        JLabel lblNewLabel_2 = new JLabel("English");
//        lblNewLabel_2.setBounds(10, 20, 60, 15);
//        contentPaneLayout.add(lblNewLabel_2);

        // input text word english
        inputWordEnglish = new JTextField();
        inputWordEnglish.setBounds(10, 40, 200, 60);
        contentPaneLayout.add(inputWordEnglish);
        inputWordEnglish.setColumns(10);



        //input orr output Viet nam
        txtWordVietNam = new JTextField();
        txtWordVietNam.setBounds(320, 40, 200, 60);
        contentPaneLayout.add(txtWordVietNam);
        txtWordVietNam.setColumns(10);

        // out type word
        txtTypeWord = new JTextField();
        txtTypeWord.setBounds(10, 123, 200, 20);
        contentPaneLayout.add(txtTypeWord);
        txtTypeWord.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setBounds(70, 1, 46, 14);
        contentPaneLayout.add(lblNewLabel_1);





        JLabel lblNewLabel_4 = new JLabel("Example english");
        lblNewLabel_4.setBounds(10, 150, 200, 14);
        contentPaneLayout.add(lblNewLabel_4);

        JLabel lblNewLabel_3 = new JLabel("Type");
        lblNewLabel_3.setBounds(10, 105, 86, 14);
        contentPaneLayout.add(lblNewLabel_3);

        //output example English
        txtExampleEnglish = new JTextField();
        txtExampleEnglish.setBounds(10, 180, 200, 83);
        contentPaneLayout.add(txtExampleEnglish);
        txtExampleEnglish.setColumns(10);

        JLabel lblNewLabel_6 = new JLabel("Example Viet nam");
        lblNewLabel_6.setBounds(320, 150, 200, 14);
        contentPaneLayout.add(lblNewLabel_6);

        //output example Viet
        txtExampleViet = new JTextField();
        txtExampleViet.setBounds(320, 180, 200, 83);
        contentPaneLayout.add(txtExampleViet);
        txtExampleViet.setColumns(10);


        // btn convert
        JButton btnConvertWord = new JButton("<=>");
        btnConvertWord.addActionListener(e -> {
            //todo handle btn convert
            try {
                if (isCheckSearchEnglish){
                    store = new Store(database.getWordsFlowEnglish(inputWordEnglish.getText().trim()));
                    store.getWord();
                    Words words = store.getWord();
                    if (words.getType() != null){
                        System.out.println(words.toString());
                        System.out.println("test");

                        txtWordVietNam.setText(words.getMean());
                        txtTypeWord.setText(words.getType());
                        txtExampleEnglish.setText(words.getExemple());
                        txtExampleViet.setText(words.getMean_exemple());
                    }else {
                        String message = "Từ bạn tìm không tồn tại! \n" +
                                "Vui lòng kiểm tra lại";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Warning",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }else if (!isCheckSearchEnglish){
                    store = new Store(database.getWordsFlowVietNam(inputWordEnglish.getText().trim()));
                    store.getWord();
                    Words words = store.getWord();
                    if (words.getType() != null){
                        System.out.println(words.toString());
                        System.out.println("test");

                        txtWordVietNam.setText(words.getWord());
                        txtTypeWord.setText(words.getType());
                        txtExampleEnglish.setText(words.getExemple());
                        txtExampleViet.setText(words.getMean_exemple());
                    }else {
                        String message = "Từ bạn tìm không tồn tại! \n" +
                                "Vui lòng kiểm tra lại";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Warning",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }



            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
        btnConvertWord.setBounds(235, 50, 60, 25);
        contentPaneLayout.add(btnConvertWord);

        //btn Delete
        JButton btnRemove = new JButton("Remove");
        btnRemove.addActionListener(e -> {
            inputWordEnglish.setText("");
            txtWordVietNam.setText("");
            txtTypeWord.setText("");
            txtExampleEnglish.setText("");
            txtExampleViet.setText("");
        });
        btnRemove.setBounds(230, 270, 80, 25);
        contentPaneLayout.add(btnRemove);
    }



}

               