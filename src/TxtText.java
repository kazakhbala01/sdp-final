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
