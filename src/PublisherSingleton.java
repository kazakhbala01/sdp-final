class PublisherSingleton {
    private static Publisher instance;

    private PublisherSingleton() {
        // Private constructor to enforce Singleton pattern
    }

    public static Publisher getInstance() {
        if (instance == null) {
            instance = new Publisher();
        }
        return instance;
    }
}