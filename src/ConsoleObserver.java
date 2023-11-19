class ConsoleObserver implements Observer {
    private boolean showInConsole = true;

    public void setShowInConsole(boolean showInConsole) {
        this.showInConsole = showInConsole;
    }

    @Override
    public void update(String content) {
        if (showInConsole) {
            System.out.println("ConsoleObserver received update: " + content);
        }
    }
}