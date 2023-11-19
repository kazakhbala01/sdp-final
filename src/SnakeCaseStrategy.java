class SnakeCaseStrategy implements CaseStrategy {
    @Override
    public String applyCase(String text) {
        return text.replaceAll("\\s", "_").toLowerCase();
    }
}
