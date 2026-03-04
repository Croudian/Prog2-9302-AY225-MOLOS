import java.util.*;

public class Analyzer {

    private List<DataRecord> records;

    // ── Constructor ───────────────────────────────────────────────────────────
    public Analyzer(List<DataRecord> records) {
        this.records = records;
    }

    // ── Main method: groups by month, computes totals, finds best month ───────
    public void displayMonthlySummary() {

        // Step 1: Group total sales by month (YYYY-MM)
        Map<String, Double> monthlySales = new TreeMap<>(); // TreeMap auto-sorts by key

        for (DataRecord record : records) {
            String month = record.getYearMonth();
            double sales = record.getTotalSales();

            if (month.equals("Unknown")) continue; // skip records with no date

            // Add sales to existing month, or start fresh
            monthlySales.put(month, monthlySales.getOrDefault(month, 0.0) + sales);
        }

        if (monthlySales.isEmpty()) {
            System.out.println("❌ No valid monthly data found.");
            return;
        }

        // Step 2: Find the best performing month
        String bestMonth = null;
        double bestSales = -1;

        for (Map.Entry<String, Double> entry : monthlySales.entrySet()) {
            if (entry.getValue() > bestSales) {
                bestSales = entry.getValue();
                bestMonth = entry.getKey();
            }
        }

        // Step 3: Display formatted results
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║       📊 MONTHLY SALES PERFORMANCE REPORT      ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf("║  %-15s  %-25s  ║%n", "Month", "Total Sales (Millions)");
        System.out.println("╠══════════════════════════════════════════════╣");

        for (Map.Entry<String, Double> entry : monthlySales.entrySet()) {
            String marker = entry.getKey().equals(bestMonth) ? " ⭐" : "";
            System.out.printf("║  %-15s  %-23.2f  ║%n",
                entry.getKey() + marker, entry.getValue());
        }

        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf("║  🏆 Best Month : %-28s║%n", bestMonth);
        System.out.printf("║  💰 Total Sales: %-26.2f  ║%n", bestSales);
        System.out.println("╚══════════════════════════════════════════════╝");
    }
}