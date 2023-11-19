class TextAdapterContext {
    private TextAdapter adapter;

    public void setAdapter(TextAdapter adapter) {
        this.adapter = adapter;
    }

    public String adaptText(String text) {
        return adapter.adapt(text);
    }
}
