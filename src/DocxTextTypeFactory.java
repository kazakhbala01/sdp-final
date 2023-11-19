class DocxTextTypeFactory implements TextTypeFactory {
    @Override
    public TextType createTextType(String type) {
        return new DocxText();
    }
}
