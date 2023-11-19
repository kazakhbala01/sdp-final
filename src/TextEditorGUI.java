import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// GUI
public class TextEditorGUI extends JFrame {
    private JTextPane textPane;
    private CaseContext caseContext;
    private TextAdapterContext adapterContext;
    private TextTypeFactory textTypeFactory;
    private ConsoleObserver consoleObserver;

    public TextEditorGUI() {
        initUI();
        caseContext = new CaseContext();
        adapterContext = new TextAdapterContext();
        consoleObserver = new ConsoleObserver();
        PublisherSingleton.getInstance().addObserver(consoleObserver);
    }

    private void initUI() {
        setTitle("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        String[] textTypes = {"Docx", "Txt"};
        JComboBox<String> typeComboBox = new JComboBox<>(textTypes);
        add(typeComboBox);

        JButton chooseTypeBtn = new JButton("Choose Text Type");
        chooseTypeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) typeComboBox.getSelectedItem();
                if ("Docx".equalsIgnoreCase(selectedType)) {
                    textTypeFactory = new DocxTextTypeFactory();
                } else if ("Txt".equalsIgnoreCase(selectedType)) {
                    textTypeFactory = new TxtTextTypeFactory();
                }
            }
        });
        add(chooseTypeBtn);


        textPane = new JTextPane();
        add(new JScrollPane(textPane));

        JButton uppercaseBtn = new JButton("Uppercase");
        uppercaseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caseContext.setStrategy(new UppercaseStrategy());
                textPane.setText(caseContext.executeStrategy(textPane.getText()));
                notifyObservers();
            }
        });
        add(uppercaseBtn);

        JButton lowercaseBtn = new JButton("Lowercase");
        lowercaseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caseContext.setStrategy(new LowercaseStrategy());
                textPane.setText(caseContext.executeStrategy(textPane.getText()));
                notifyObservers();
            }
        });
        add(lowercaseBtn);

        JButton camelCaseBtn = new JButton("Camel Case");
        camelCaseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caseContext.setStrategy(new CamelCaseStrategy());
                textPane.setText(caseContext.executeStrategy(textPane.getText()));
                notifyObservers();
            }
        });
        add(camelCaseBtn);

        JButton snakeCaseBtn = new JButton("Snake Case");
        snakeCaseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caseContext.setStrategy(new SnakeCaseStrategy());
                textPane.setText(caseContext.executeStrategy(textPane.getText()));
                notifyObservers();
            }
        });
        add(snakeCaseBtn);

        JButton capitalizeBtn = new JButton("Capitalize");
        capitalizeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caseContext.setStrategy(new CapitalizeStrategy());
                textPane.setText(caseContext.executeStrategy(textPane.getText()));
                notifyObservers();
            }
        });
        add(capitalizeBtn);

        JButton boldBtn = new JButton("Bold");
        boldBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyBoldItalicStyle(textPane, true, false);
                notifyObservers();
            }
        });
        add(boldBtn);

        JButton italicBtn = new JButton("Italic");
        italicBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyBoldItalicStyle(textPane, false, true);
                notifyObservers();
            }
        });
        add(italicBtn);

        JButton switchTxtDocxBtn = new JButton("Switch Text Type (TXT to DOCX)");
        switchTxtDocxBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textTypeFactory != null) {
                    TextType textType = textTypeFactory.createTextType("DummyType"); // Replace "DummyType" with the actual type
                    textPane.setText(textType.createTextComponent(textPane.getText()).getContent());
                    String typeInfo = "Type: " + textType.getType();  // Use getType() method
                    System.out.println(typeInfo);  // For example, print the type information
                    notifyObservers();
                }
            }
        });
        add(switchTxtDocxBtn);

        JButton switchDocxTxtBtn = new JButton("Switch Text Type (DOCX to TXT)");
        switchDocxTxtBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adapterContext.setAdapter(new DocxToTxtAdapter());
                System.out.println(adapterContext.adaptText(textPane.getText()));
                notifyObservers();
            }
        });
        add(switchDocxTxtBtn);

        JCheckBox consoleCheckBox = new JCheckBox("Show Updates in Console");
        consoleCheckBox.setSelected(true);
        consoleCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consoleObserver.setShowInConsole(consoleCheckBox.isSelected());
            }
        });
        add(consoleCheckBox);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void notifyObservers() {
        PublisherSingleton.getInstance().notifyObservers(textPane.getText());
    }

    private void applyBoldItalicStyle(JTextPane textPane, boolean bold, boolean italic) {
        StyledDocument document = textPane.getStyledDocument();
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setBold(attributes, bold);
        StyleConstants.setItalic(attributes, italic);

        document.setCharacterAttributes(0, document.getLength(), attributes, false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TextEditorGUI();
            }
        });
    }
}
