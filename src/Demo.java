import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;

public class Demo extends JFrame {
    public Demo() {
    }

    private static ObjectOutputStream objectOutputStream;
    private static Store store;
    private static MySQL database;


    private JPanel contentPaneLayout;
    private JTextArea inputWordEnglish;
    private JTextArea txtWordVietNamese;
    private JLabel txtTypeWord;
    private TextArea txtExampleEnglish;
    private TextArea txtExampleViet;
    private boolean isCheckSearchEnglish = true;

    public static void main(String[] args) throws IOException, SQLException {


        //server
        try {
            database = new MySQL();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "The driver has not received any packets from the server."
                    , "Warning",
                    JOptionPane.ERROR_MESSAGE);
        }

        Demo frame = new Demo();
        frame.setUpView();
        frame.setTitle("DICTIONARY");
        frame.setVisible(true);
        frame.setSize(850, 650);
        //   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //   frame.pack(); // give a suitable size to window automatically


    }

    public void setUpView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 705, 619);
        contentPaneLayout = new JPanel();
        //contentPaneLayout.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPaneLayout.setBorder(BorderFactory.createLineBorder(Color.GRAY, 10));
        setContentPane(contentPaneLayout);
        contentPaneLayout.setLayout(null);


        JLabel lblNewLabel = new JLabel("Việt Nam");
        lblNewLabel.setBounds(490, 244, 92, 14);
        contentPaneLayout.add(lblNewLabel);

//        JLabel lblNewLabel_5 = new JLabel("DICTIONARY");
//        lblNewLabel_5.setBackground(UIManager.getColor("Button.darkShadow"));
//        lblNewLabel_5.setBounds(230, 0, 80, 14);
//        contentPaneLayout.add(lblNewLabel_5);


        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Tiếng Anh");
        comboBox.addItem("Tiếng Việt");
        comboBox.addActionListener(e -> {
            if (comboBox.getSelectedIndex() == 0) {
                lblNewLabel.setText("Tiếng Việt");
                isCheckSearchEnglish = true;
                removalItem();

            } else {
                lblNewLabel.setText("Tiếng Anh");
                isCheckSearchEnglish = false;
                removalItem();

            }
        });
        comboBox.setBounds(21, 241, 150, 20);
        //    comboBox.setLocation(10,11);
        contentPaneLayout.add(comboBox);


//        JLabel lblNewLabel_2 = new JLabel("English");
//        lblNewLabel_2.setBounds(10, 20, 60, 15);
//        contentPaneLayout.add(lblNewLabel_2);

        // input text word english
        inputWordEnglish = new JTextArea(5, 1);
        inputWordEnglish.setBounds(21, 271, 310, 120);
        inputWordEnglish.setBorder(new LineBorder(Color.BLACK,1));
        inputWordEnglish.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        //  inputWordEnglish.set
        contentPaneLayout.add(inputWordEnglish);
        inputWordEnglish.setColumns(10);


        //input orr output Viet nam
        txtWordVietNamese = new JTextArea( 5,1);
        txtWordVietNamese.setBounds(490, 271, 310, 120);
        txtWordVietNamese.setBorder(new LineBorder(Color.BLACK,1));
        txtWordVietNamese.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        contentPaneLayout.add(txtWordVietNamese);
        txtWordVietNamese.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Loại từ");
        lblNewLabel_3.setBounds(21, 394, 100, 14);
        contentPaneLayout.add(lblNewLabel_3);


        // out type word
        txtTypeWord = new JLabel();
        txtTypeWord.setBounds(21, 413, 200, 20);
        txtTypeWord.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        txtTypeWord.setBorder(new LineBorder(Color.BLACK,1));
        contentPaneLayout.add(txtTypeWord);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setBounds(70, 1, 46, 14);
        contentPaneLayout.add(lblNewLabel_1);


        JLabel lblNewLabel_4 = new JLabel("Ví dụ tiếng Anh ");
        lblNewLabel_4.setBounds(21, 440, 230, 14);
        contentPaneLayout.add(lblNewLabel_4);


        //output example English
        txtExampleEnglish = new TextArea(5, 1);
        txtExampleEnglish.setBounds(21, 460, 310, 120);
        txtExampleEnglish.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        contentPaneLayout.add(txtExampleEnglish);
        txtExampleEnglish.setColumns(10);

        JLabel lblNewLabel_6 = new JLabel("Ví dụ tiếng Việt ");
        lblNewLabel_6.setBounds(490, 440, 310, 14);
        contentPaneLayout.add(lblNewLabel_6);

        //output example Viet
        txtExampleViet = new TextArea(5, 1);
        txtExampleViet.setBounds(490, 460, 310, 120);
        txtExampleViet.setFont(new Font("Serif", Font.PLAIN, 14));
        txtExampleViet.requestFocusInWindow();
        txtExampleViet.setPreferredSize(new Dimension(20, 50));
        contentPaneLayout.add(txtExampleViet);


        // btn convert
        //JButton btnConvertWord = new JButton("Translation");
        JButton btnConvertWord = new JButton("Dịch");
        btnConvertWord.addActionListener(e -> {
            //todo handle btn convert
            try {
                if (inputWordEnglish.getText().equals("")){
                    String message = "Từ bạn tìm không tồn tại! \n" +
                            "Vui lòng kiểm tra lại";
                    JOptionPane.showMessageDialog(new JFrame(), message, "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }else {
                    if (isCheckSearchEnglish) {
                        store = new Store(database.getWordsFlowEnglish(inputWordEnglish.getText().trim()));
                        store.getWord();
                        Words words = store.getWord();
                        if (words.getType() != null) {
                            System.out.println(words.toString());
                            System.out.println("test");

                            txtWordVietNamese.setText(words.getMean());
                            txtTypeWord.setText(words.getType());
                            txtExampleEnglish.setText(words.getExemple());

                            String wordViet = words.getMean_exemple();
                            txtExampleViet.setText(wordViet);
//                        String strOutWordViet = "";
//                        int size = wordViet.length();
//                        if (wordViet.length() > 50 ) {
//                            strOutWordViet = wordViet.substring(0, 50) + "\n" + wordViet.substring(50, size);
//                            txtExampleViet.setText(strOutWordViet);
//                        } else {
//                            txtExampleViet.setText(wordViet);
//                        }

                        } else {
                            String message = "Từ bạn tìm không tồn tại! \n" +
                                    "Vui lòng kiểm tra lại";
                            JOptionPane.showMessageDialog(new JFrame(), message, "Warning",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else if (!isCheckSearchEnglish) {
                        store = new Store(database.getWordsFlowVietNam(inputWordEnglish.getText().trim()));
                        store.getWord();
                        Words words = store.getWord();
                        if (words.getType() != null) {
                            System.out.println(words.toString());
                            System.out.println("test");

                            txtWordVietNamese.setText(words.getWord());
                            txtTypeWord.setText(words.getType());
                            txtExampleEnglish.setText(words.getExemple());
                            String txtViduVn = words.getMean_exemple();

                            txtExampleViet.setText(words.getMean_exemple());
                        } else {
                            String message = "Từ bạn tìm không tồn tại! \n" +
                                    "Vui lòng kiểm tra lại";
                            JOptionPane.showMessageDialog(new JFrame(), message, "Warning",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }



            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
        btnConvertWord.setBounds(357, 311, 100, 25);
        contentPaneLayout.add(btnConvertWord);

        //btn Delete
        JButton btnRemove = new JButton("Xóa");
        btnRemove.addActionListener(e ->

        {
            removalItem();
        });
        btnRemove.setBounds(363, 500, 80, 25);
        contentPaneLayout.add(btnRemove);

        JLabel lblNewLabel_2 = new JLabel();
//        lblNewLabel_2.setIcon(new ImageIcon("bg.jpg"));
//        lblNewLabel_2.getScaledInstance(842, 581, image.SCALE_SMOOTH)
//        lblNewLabel_2.setBounds(110, 5, 1000, 230);


        //Nền
        lblNewLabel_2 = new

                JLabel();

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("bg.jpg"));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        lblNewLabel_2.setIcon(new

                ImageIcon(image.getScaledInstance(810, 220, image.SCALE_SMOOTH)));
        lblNewLabel_2.setBounds(11, 11, 810, 220);
        contentPaneLayout.add(lblNewLabel_2);
    }


    private void removalItem() {
        inputWordEnglish.setText("");
        txtWordVietNamese.setText("");
        txtTypeWord.setText("");
        txtExampleEnglish.setText("");
        txtExampleViet.setText("");
    }


}

