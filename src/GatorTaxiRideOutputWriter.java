import java.io.FileWriter;

class GatorTaxiRideOutputWriter {

//    Output writer writes the output in the output_file.txt;
    private static final String OUTPUT_FILE_NAME = "output_file.txt";

    private FileWriter writer;

    public GatorTaxiRideOutputWriter() {
        try {
            initializeFile();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void initializeFile() throws Exception {
        writer = new FileWriter(OUTPUT_FILE_NAME);
    }

    public void writeOutputToFile(String txt) throws Exception {
        this.writer.write(String.format("%s\n",txt));
    }

    public void closeWriter() throws Exception {
        writer.close();
    }
}