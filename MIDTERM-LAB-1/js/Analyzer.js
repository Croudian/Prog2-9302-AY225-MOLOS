function Analyzer(records) {
    this.records = records;
}

Analyzer.prototype.displayMonthlySummary = function() {
    const monthlySales = {};

    for (let i = 0; i < this.records.length; i++) {
        const record = this.records[i];
        const month = record.getYearMonth();
        const sales = record.totalSales;

        if (month === 'Unknown') continue;
        monthlySales[month] = (monthlySales[month] || 0) + sales;
    }

    if (Object.keys(monthlySales).length === 0) {
        console.log('No valid monthly data found.');
        return;
    }

    const sortedMonths = Object.keys(monthlySales).sort();

    let bestMonth = null;
    let bestSales = -1;

    for (let i = 0; i < sortedMonths.length; i++) {
        const month = sortedMonths[i];
        if (monthlySales[month] > bestSales) {
            bestSales = monthlySales[month];
            bestMonth = month;
        }
    }

    console.log('================================================');
    console.log('     MONTHLY SALES PERFORMANCE REPORT');
    console.log('================================================');
    console.log('  Month            Total Sales (Millions)');
    console.log('------------------------------------------------');

    for (let i = 0; i < sortedMonths.length; i++) {
        const month = sortedMonths[i];
        const marker = month === bestMonth ? ' <-- BEST' : '';
        console.log('  ' + month.padEnd(15) + '  ' + monthlySales[month].toFixed(2) + marker);
    }

    console.log('================================================');
    console.log('  Best Month  : ' + bestMonth);
    console.log('  Total Sales : ' + bestSales.toFixed(2));
    console.log('================================================');
};

module.exports = Analyzer;