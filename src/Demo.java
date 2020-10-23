import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.sql.*;

public class Demo extends JFrame {
	public Demo() {
	}

    private static ObjectOutputStream objectOutputStream;
    private static Store store;
    private static MySQL database;


    private JPanel contentPaneLayout;
    private TextArea inputWordEnglish;
    private TextArea txtWordVietNamese;
    private JTextField txtTypeWord;
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
      //  frame.setSize(700, 400);
     //   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     //   frame.pack(); // give a suitable size to window automatically


    }

    public void setUpView() throws SQLException {
    	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBounds(100, 100, 705, 619);
	        contentPaneLayout = new JPanel();
	        contentPaneLayout.setBorder(new EmptyBorder(5, 5, 5, 5));
	        setContentPane(contentPaneLayout);
	        contentPaneLayout.setLayout(null);


	        JLabel lblNewLabel = new JLabel("Vietnamese");
	        lblNewLabel.setBounds(429, 244, 92, 14);
	        contentPaneLayout.add(lblNewLabel);

//        JLabel lblNewLabel_5 = new JLabel("DICTIONARY");
//        lblNewLabel_5.setBackground(UIManager.getColor("Button.darkShadow"));
//        lblNewLabel_5.setBounds(230, 0, 80, 14);
//        contentPaneLayout.add(lblNewLabel_5);


        JComboBox comboBox = new JComboBox();
        comboBox.addItem("English");
        comboBox.addItem("Vietnamese");
        comboBox.addActionListener(e -> {
            if (comboBox.getSelectedIndex() == 0){
                lblNewLabel.setText("Vietnamese");
                isCheckSearchEnglish = true;
                removalItem();

            }else {
                lblNewLabel.setText("English");
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
	    inputWordEnglish = new TextArea(5,1);
        inputWordEnglish.setBounds(21, 275, 250, 110);
        contentPaneLayout.add(inputWordEnglish);
        inputWordEnglish.setColumns(10);



        //input orr output Viet nam
        txtWordVietNamese = new TextArea(5,1);
        txtWordVietNamese.setBounds(429, 275, 250, 110);
        contentPaneLayout.add(txtWordVietNamese);
        txtWordVietNamese.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Type");
        lblNewLabel_3.setBounds(10, 160, 86, 14);
        contentPaneLayout.add(lblNewLabel_3);



        // out type word
        txtTypeWord = new JTextField();
        txtTypeWord.setBounds(21, 409, 200, 20);
        contentPaneLayout.add(txtTypeWord);
        txtTypeWord.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setBounds(70, 1, 46, 14);
        contentPaneLayout.add(lblNewLabel_1);






        JLabel lblNewLabel_4 = new JLabel("Example ");
        lblNewLabel_4.setBounds(21, 440, 200, 14);
        contentPaneLayout.add(lblNewLabel_4);


        //output example English
        txtExampleEnglish = new TextArea(5,1);
        txtExampleEnglish.setBounds(21, 460, 250, 110);
        contentPaneLayout.add(txtExampleEnglish);
        txtExampleEnglish.setColumns(10);

        JLabel lblNewLabel_6 = new JLabel("Ví dụ ");
        lblNewLabel_6.setBounds(429, 440, 200, 14);
        contentPaneLayout.add(lblNewLabel_6);

        //output example Viet
        txtExampleViet = new TextArea(5,1);
        txtExampleViet.setBounds(429, 460, 250, 110);
        txtExampleViet.requestFocusInWindow();
        txtExampleViet.setPreferredSize(new Dimension(20, 50));
        contentPaneLayout.add(txtExampleViet);


        // btn convert
        JButton btnConvertWord = new JButton("Translation");
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

                        txtWordVietNamese.setText(words.getMean());
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

                        txtWordVietNamese.setText(words.getWord());
                        txtTypeWord.setText(words.getType());
                        txtExampleEnglish.setText(words.getExemple());
                        String txtViduVn = words.getMean_exemple();

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
        btnConvertWord.setBounds(297, 311, 100, 25);
        contentPaneLayout.add(btnConvertWord);

        //btn Delete
        JButton btnRemove = new JButton("Remove");
        btnRemove.addActionListener(e -> {
            removalItem();
        });
        btnRemove.setBounds(303, 500, 80, 25);
        contentPaneLayout.add(btnRemove);
        
        JLabel lblNewLabel_2 = new JLabel("New label");
        lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\DELL\\Desktop\\background.jpg"));
        lblNewLabel_2.setBounds(0, 1, 689, 228);
        contentPaneLayout.add(lblNewLabel_2);
    }

    private void removalItem(){
        inputWordEnglish.setText("");
        txtWordVietNamese.setText("");
        txtTypeWord.setText("");
        txtExampleEnglish.setText("");
        txtExampleViet.setText("");
    }



}

               