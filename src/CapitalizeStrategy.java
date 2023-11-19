class CapitalizeStrategy implements CaseStrategy {
    @Override
    public String applyCase(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
