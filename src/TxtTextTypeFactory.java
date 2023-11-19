class TxtTextTypeFactory implements TextTypeFactory {
    @Override
    public TextType createTextType(String type) {
        return new TxtText();
    }
}
