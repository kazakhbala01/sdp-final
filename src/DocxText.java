class DocxText implements TextType {
    @Override
    public String getType() {
        return "Docx";
    }

    @Override
    public TextComponent createTextComponent(String content) {
        return new ConcreteTextComponent(content);
    }
}