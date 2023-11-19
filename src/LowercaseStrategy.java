class LowercaseStrategy implements CaseStrategy {
    @Override
    public String applyCase(String text) {
        return text.toLowerCase();
    }
}
