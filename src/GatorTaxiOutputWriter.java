import java.io.FileWriter;

class GatorTaxiOutputWriter {

    /**
     * Output writer writes the output in the output_file.txt;
     */
    private static final String OUTPUT_FILE_NAME = "output_file.txt";

    private FileWriter outputFileWriter;

    public GatorTaxiOutputWriter() {
        try {
            initOutputFile();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Initialize output file
     * @throws Exception
     */
    private void initOutputFile() throws Exception {
        outputFileWriter = new FileWriter(OUTPUT_FILE_NAME);
    }

    /**
     * Write output string to output file
     * @param outputStr - output string
     * @throws Exception
     */
    public void writeOutputToFile(String outputStr) throws Exception {
        this.outputFileWriter.write(String.format("%s\n",outputStr));
    }

    /**
     * Close output writer
     * @throws Exception
     */
    public void closeOutputWriter() throws Exception {
        outputFileWriter.close();
    }
}