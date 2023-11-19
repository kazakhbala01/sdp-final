# 💻 Text editing application by Almas Amanbay, Akzhan Omir, Adilkhan Kenzhaliev; group SE-2215
## 🎨 Change text type and case using GUI!
### We used 6 patterns such as Factory, Strategy, Singleton, Observer, Decorator, Adapter.
Simply type something and click buttons below to change between different cases or change type.
IMAGE -->
</br>
<img width="203" alt="image" src="https://github.com/kazakhbala01/sdp-final/assets/125587417/3b046236-32a5-466a-ad16-138de18b3e98">


DIAGRAM
</br>
![photo_2023-11-19_23-26-18](https://github.com/kazakhbala01/sdp-final/assets/125587417/503376ce-e0ac-42c7-b650-8ce217efe966)

## Code
### GUI
#### Using Jframe create window with textarea and buttons
```java
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
        Publisher.getInstance().addObserver(consoleObserver);
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
                adapterContext.setAdapter(new TxtToDocxAdapter());
                System.out.println(adapterContext.adaptText(textPane.getText()));
                notifyObservers();
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
        Publisher.getInstance().notifyObservers(textPane.getText());
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

```
### Factory pattern
#### Choose 2 different types such as txt or docx
```java
class DocxTextTypeFactory implements TextTypeFactory {
    @Override
    public TextType createTextType(String type) {
        return new DocxText();
    }
}
interface TextTypeFactory {
    TextType createTextType(String type);
}
class TxtTextTypeFactory implements TextTypeFactory {
    @Override
    public TextType createTextType(String type) {
        return new TxtText();
    }
}

```
### Singleton
#### Used singleton pattern to return publisher class
```java
class PublisherSingleton {
    private static Publisher instance;

    private PublisherSingleton() {
        // Private constructor to enforce Singleton pattern
    }

    public static Publisher getInstance() {
        if (instance == null) {
            instance = new Publisher();
        }
        return instance;
    }
}
```
### Adapter pattern
#### Adapt between previously chosen docx type to txt and otherwise
```java
// Adapter Pattern
interface TextAdapter {
    String adapt(String text);
}

class DocxToTxtAdapter implements TextAdapter {
    @Override
    public String adapt(String text) {
        // Simulate adapting DOCX to TXT
        return "Adapted from DOCX to TXT:\n" + text;
    }
}

class TxtToDocxAdapter implements TextAdapter {
    @Override
    public String adapt(String text) {
        // Simulate adapting TXT to DOCX
        return "Adapted from TXT to DOCX:\n" + text;
    }
}
```
### Strategy pattern
#### Change Text cases such as Snake_case, Capitalize, CamelCase, UPPERCASE or lowercase 
```java
class CaseContext {
    private CaseStrategy strategy;

    public void setStrategy(CaseStrategy strategy) {
        this.strategy = strategy;
    }

    public String executeStrategy(String text) {
        return strategy.applyCase(text);
    }
}

// Strategy Pattern
interface CaseStrategy {
    String applyCase(String text);
}

class CapitalizeStrategy implements CaseStrategy {
    @Override
    public String applyCase(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
class CamelCaseStrategy implements CaseStrategy {
    @Override
    public String applyCase(String text) {
       
        String[] words = text.split("\\s");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
            }
            result.append(" ");
        }
        return result.toString().trim();
    }
}

class LowercaseStrategy implements CaseStrategy {
    @Override
    public String applyCase(String text) {
        return text.toLowerCase();
    }
}

class UppercaseStrategy implements CaseStrategy {
    @Override
    public String applyCase(String text) {
        return text.toUpperCase();
    }
}
class SnakeCaseStrategy implements CaseStrategy {
    @Override
    public String applyCase(String text) {
        return text.replaceAll("\\s", "_").toLowerCase();
    }
}

```
### Decorator pattern
#### Add additional decorators to change text font to bold or italic
```java
class TxtText implements TextType {
    @Override
    public String getType() {
        return "Txt";
    }

    @Override
    public TextComponent createTextComponent(String content) {
        return new ConcreteTextComponent(content);
    }
}
// Decorator Pattern
interface TextComponent {
    String getContent();
}

abstract class TextDecorator implements TextComponent {
    protected TextComponent textComponent;

    public TextDecorator(TextComponent textComponent) {
        this.textComponent = textComponent;
    }

    @Override
    public String getContent() {
        return textComponent.getContent();
    }
}

class BoldDecorator extends TextDecorator {
    public BoldDecorator(TextComponent textComponent) {
        super(textComponent);
    }

    @Override
    public String getContent() {
        return "<b>" + super.getContent() + "</b>";
    }
}

class ItalicDecorator extends TextDecorator {
    public ItalicDecorator(TextComponent textComponent) {
        super(textComponent);
    }

    @Override
    public String getContent() {
        return "<i>" + super.getContent() + "</i>";
    }
}

```
### Observer pattern
#### Notificate user in console on events that happening that can be turned off
```java
// Observer Pattern
interface Observer {
    void update(String content);
}
import java.util.ArrayList;
import java.util.List;

class Publisher {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String content) {
        for (Observer observer : observers) {
            observer.update(content);
        }
    }
}
class ConsoleObserver implements Observer {
    private boolean showInConsole = true;

    public void setShowInConsole(boolean showInConsole) {
        this.showInConsole = showInConsole;
    }

    @Override
    public void update(String content) {
        if (showInConsole) {
            System.out.println("ConsoleObserver received update: " + content);
        }
    }
}
```
