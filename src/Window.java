import javax.swing.*;

public class Window extends JFrame {

    private JComboBox<String> jComboBox1;
    private JComboBox<String> jComboBox2;
    private JTextField jTextField;
    private JLabel jLabel;
    private JButton jButton;
    private Converter converter = new Converter(this);

    public Window() {
        run();
    }

    public void run() {
        jComboBox1 = new JComboBox<>();
        jComboBox1.setSize(285, 30);
        jComboBox1.setLocation(0,0);

        jComboBox2 = new JComboBox<>();
        jComboBox2.setSize(285, 30);
        jComboBox2.setLocation(0,30);

        jTextField = new JTextField("0");
        jTextField.setSize(140, 30);
        jTextField.setLocation(0,60);

        jLabel = new JLabel("Value: ");
        jLabel.setSize(140, 30);
        jLabel.setLocation(150, 60);

        jButton = new JButton("Convert");
        jButton.setSize(285, 30);
        jButton.setLocation(0,90);
        jButton.addActionListener(converter);

        getContentPane().add(jComboBox1);
        getContentPane().add(jComboBox2);
        getContentPane().add(jTextField);
        getContentPane().add(jLabel);
        getContentPane().add(jButton);
        setComboBoxesItem();

        setSize(300,160);
        setLayout(null);
        setVisible(true);
    }

    public void setComboBoxesItem() {
        for(String string : converter.getAllCurrencies()){
            string = string.replace("\"", "");
            jComboBox1.addItem(string);
            jComboBox2.addItem(string);
        }
    }

    public Object getjComboBox1() {
        return jComboBox1.getSelectedItem();
    }

    public Object getjComboBox2() {
        return jComboBox2.getSelectedItem();
    }

    public String getjTextField() {
        return jTextField.getText();
    }

    public String getjLabel() {
        return jLabel.getText();
    }

    public void setjLabel(String string) {
        this.jLabel.setText(string);
    }
}
