class UppercaseStrategy implements CaseStrategy {
    @Override
    public String applyCase(String text) {
        return text.toUpperCase();
    }
}
