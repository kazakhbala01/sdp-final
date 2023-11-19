class CaseContext {
    private CaseStrategy strategy;

    public void setStrategy(CaseStrategy strategy) {
        this.strategy = strategy;
    }

    public String executeStrategy(String text) {
        return strategy.applyCase(text);
    }
}
