const fs = require('fs');
const readline = require('readline');
const DataRecord = require('./DataRecord');
const Analyzer = require('./Analyzer');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

function askFilePath() {
    rl.question('Enter dataset file path: ', function(path) {
        path = path.trim();

        if (!fs.existsSync(path)) {
            console.log('Error: File does not exist. Please try again.\n');
            askFilePath();

        } else if (!fs.statSync(path).isFile()) {
            console.log('Error: Path is not a file. Please try again.\n');
            askFilePath();

        } else if (!path.toLowerCase().endsWith('.csv')) {
            console.log('Error: File is not a CSV. Please try again.\n');
            askFilePath();

        } else {
            console.log('File found! Loading data...\n');
            rl.close();
            loadAndAnalyze(path);
        }
    });
}

function loadAndAnalyze(filePath) {
    try {
        const content = fs.readFileSync(filePath, 'utf8');
        const lines = content.split(/\r?\n/); // handles both Windows and Mac line endings
        const records = [];

        console.log(`Total lines found: ${lines.length}`);

        // Skip header row
        for (let i = 1; i < lines.length; i++) {
            const line = lines[i].trim();
            if (!line) continue;

            try {
                const record = DataRecord.fromCSV(line);
                if (record) records.push(record);
            } catch (e) {
                // silently skip bad rows
            }
        }

        console.log(`Successfully loaded ${records.length} records.\n`);

        const analyzer = new Analyzer(records);
        analyzer.displayMonthlySummary();

    } catch (e) {
        console.log('Error reading file: ' + e.message);
    }
}

askFilePath();