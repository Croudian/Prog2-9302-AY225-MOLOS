import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File file = null;

        // ── Step 1: Ask for file path and validate in a loop ──────────────────
        while (true) {
            System.out.print("Enter dataset file path: ");
            String path = scanner.nextLine().trim();

            file = new File(path);

            if (!file.exists()) {
                System.out.println("❌ Error: File does not exist. Please try again.\n");

            } else if (!file.isFile()) {
                System.out.println("❌ Error: Path is not a file. Please try again.\n");

            } else if (!path.toLowerCase().endsWith(".csv")) {
                System.out.println("❌ Error: File is not a CSV. Please try again.\n");

            } else if (!file.canRead()) {
                System.out.println("❌ Error: File cannot be read. Please try again.\n");

            } else {
                System.out.println("✅ File found! Loading data...\n");
                break; // valid file — exit loop
            }
        }

        // ── Step 2: Load the CSV into a list of DataRecords ───────────────────
        List<DataRecord> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String header = br.readLine(); // skip the header row

            if (header == null) {
                System.out.println("❌ Error: CSV file is empty.");
                return;
            }

            String line;
            int lineNumber = 1;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                try {
                    DataRecord record = DataRecord.fromCSV(line);
                    if (record != null) {
                        records.add(record);
                    }
                } catch (Exception e) {
                    // skip malformed rows but notify
                    System.out.println("⚠️  Skipping malformed row at line " + lineNumber + ": " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
            return;
        }

      // ── Step 3: Pass data to Analyzer and display results ─────────────────
        Analyzer analyzer = new Analyzer(records);
        analyzer.displayMonthlySummary();

        scanner.close(); // ← add this line
    }
}