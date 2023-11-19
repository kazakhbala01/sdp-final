class CamelCaseStrategy implements CaseStrategy {
    @Override
    public String applyCase(String text) {
        // Implement camel case conversion logic
        // For simplicity, just capitalize the first letter of each word
        String[] words = text.split("\\s");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
            }
            result.append(" ");
        }
        return result.toString().trim();
    }
}
