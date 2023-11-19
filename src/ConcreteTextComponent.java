class ConcreteTextComponent implements TextComponent {
    private String content;

    public ConcreteTextComponent(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }
}
