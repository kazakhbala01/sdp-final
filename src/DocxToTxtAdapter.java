class DocxToTxtAdapter implements TextAdapter {
    @Override
    public String adapt(String text) {
        // Simulate adapting DOCX to TXT
        return "Adapted from DOCX to TXT:\n" + text;
    }
}
