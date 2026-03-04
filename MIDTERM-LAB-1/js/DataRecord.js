class DataRecord {
    constructor(title, console, genre, publisher, developer, totalSales, releaseDate) {
        this.title = title;
        this.console = console;
        this.genre = genre;
        this.publisher = publisher;
        this.developer = developer;
        this.totalSales = totalSales;
        this.releaseDate = releaseDate;
    }

    // Parse one CSV row into a DataRecord
    static fromCSV(line) {
        // Split by comma but respect quoted fields
        const fields = line.match(/(".*?"|[^",\s]+)(?=\s*,|\s*$)/g) || line.split(',');

        if (!fields || fields.length < 14) return null;

        const title       = fields[1].replace(/"/g, '').trim();
        const console     = fields[2].replace(/"/g, '').trim();
        const genre       = fields[3].replace(/"/g, '').trim();
        const publisher   = fields[4].replace(/"/g, '').trim();
        const developer   = fields[5].replace(/"/g, '').trim();
        const releaseDate = fields[13].replace(/"/g, '').trim();

        // Parse total_sales safely
        let totalSales = 0.0;
        try {
            const salesStr = fields[7].replace(/"/g, '').trim();
            if (salesStr) totalSales = parseFloat(salesStr) || 0.0;
        } catch (e) {
            totalSales = 0.0;
        }

        return new DataRecord(title, console, genre, publisher, developer, totalSales, releaseDate);
    }

    // Extract YYYY-MM from release date
    getYearMonth() {
        if (!this.releaseDate || this.releaseDate.length < 7) return 'Unknown';
        return this.releaseDate.substring(0, 7);
    }
}

module.exports = DataRecord;