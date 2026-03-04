public class DataRecord {

    // ── Fields matching the CSV columns ───────────────────────────────────────
    private String title;
    private String console;
    private String genre;
    private String publisher;
    private String developer;
    private double totalSales;
    private String releaseDate;  // format: YYYY-MM-DD

    // ── Constructor ───────────────────────────────────────────────────────────
    public DataRecord(String title, String console, String genre,
                      String publisher, String developer,
                      double totalSales, String releaseDate) {
        this.title = title;
        this.console = console;
        this.genre = genre;
        this.publisher = publisher;
        this.developer = developer;
        this.totalSales = totalSales;
        this.releaseDate = releaseDate;
    }

    // ── Parse one CSV row into a DataRecord ───────────────────────────────────
    public static DataRecord fromCSV(String line) {
        // Split by comma but respect quoted fields
        String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        if (fields.length < 14) return null; // skip incomplete rows

        String title       = fields[1].trim().replace("\"", "");
        String console     = fields[2].trim().replace("\"", "");
        String genre       = fields[3].trim().replace("\"", "");
        String publisher   = fields[4].trim().replace("\"", "");
        String developer   = fields[5].trim().replace("\"", "");
        String releaseDate = fields[13].trim().replace("\"", "");

        // Parse total_sales safely
        double totalSales = 0.0;
        try {
            String salesStr = fields[7].trim().replace("\"", "");
            if (!salesStr.isEmpty()) {
                totalSales = Double.parseDouble(salesStr);
            }
        } catch (NumberFormatException e) {
            totalSales = 0.0; // default if missing or invalid
        }

        return new DataRecord(title, console, genre, publisher, developer, totalSales, releaseDate);
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String getTitle()       { return title; }
    public String getConsole()     { return console; }
    public String getGenre()       { return genre; }
    public String getPublisher()   { return publisher; }
    public String getDeveloper()   { return developer; }
    public double getTotalSales()  { return totalSales; }
    public String getReleaseDate() { return releaseDate; }

    // ── Extract just the month (e.g. "2023-05-12" → "2023-05") ───────────────
    public String getYearMonth() {
        if (releaseDate == null || releaseDate.length() < 7) return "Unknown";
        return releaseDate.substring(0, 7); // returns "YYYY-MM"
    }
}