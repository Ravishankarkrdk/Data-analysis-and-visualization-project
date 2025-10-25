import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "data/sample_data.csv";
        List<DataPoint> dataPoints = CSVReader.readCSV(filePath);
        
        if (dataPoints.isEmpty()) {
            System.out.println("No data loaded. Creating sample data...");
            dataPoints = createSampleData();
        }
        
        DataAnalyzer analyzer = new DataAnalyzer(dataPoints);
        DataVisualizer visualizer = new DataVisualizer();
        
        System.out.println("=== ADVANCED DATA ANALYSIS AND VISUALIZATION PROJECT ===");
        System.out.println("Total data points: " + dataPoints.size());
        
        Map<String, Object> stats = analyzer.getBasicStatistics();
        visualizer.printStatistics(stats);
        
        Map<String, Double> growthRates = analyzer.calculateMonthlyGrowth();
        visualizer.printTrendAnalysis(growthRates);
        
        Map<String, Map<String, Double>> correlations = analyzer.calculateCategoryCorrelations();
        visualizer.printCorrelationMatrix(correlations);
        
        Map<String, Map<String, Double>> seasonalData = analyzer.analyzeSeasonality();
        visualizer.printSeasonalAnalysis(seasonalData);
        
        List<DataPoint> outliers = analyzer.detectOutliers(2.0);
        visualizer.printOutliers(outliers);
        
        Map<String, Double> movingAverages = analyzer.calculateMovingAverage(3);
        visualizer.printMovingAverage(movingAverages);
        
        Map<String, Map<String, Double>> periodComparison = 
            analyzer.comparePeriods("2024-01-15", "2024-01-20", "2024-01-21", "2024-01-24");
        visualizer.printPeriodComparison(periodComparison);
        
        Map<String, Double> cumulativeSums = analyzer.calculateCumulativeSum();
        visualizer.printCumulativeAnalysis(cumulativeSums);
        
        Map<String, Double> percentiles = analyzer.calculatePercentiles();
        visualizer.printPercentiles(percentiles);
        
        Map<String, Map<String, Object>> regionalAnalysis = analyzer.analyzeRegionalPerformance();
        visualizer.printRegionalAnalysis(regionalAnalysis);
        
        Map<String, Object> forecast = analyzer.simpleLinearForecast(3);
        visualizer.printForecast(forecast);
        
        Map<String, Double> categoryAverages = analyzer.getCategoryAverages();
        visualizer.printBarChart("CATEGORY AVERAGES", categoryAverages);
        
        List<DataPoint> top5 = analyzer.getTopN(5);
        visualizer.printDataTable(top5, "TOP 5 VALUES");
        
        visualizer.printHistogram(dataPoints, 10);
    }
    
    private static List<DataPoint> createSampleData() {
        return List.of(
            new DataPoint("Electronics", 150.0, "2024-01-15", "North"),
            new DataPoint("Clothing", 75.5, "2024-01-16", "South"),
            new DataPoint("Electronics", 200.0, "2024-01-17", "North"),
            new DataPoint("Food", 45.0, "2024-01-18", "East"),
            new DataPoint("Clothing", 120.0, "2024-01-19", "West"),
            new DataPoint("Food", 30.0, "2024-01-20", "South"),
            new DataPoint("Electronics", 180.0, "2024-01-21", "East"),
            new DataPoint("Clothing", 90.0, "2024-01-22", "North"),
            new DataPoint("Food", 60.0, "2024-01-23", "West"),
            new DataPoint("Electronics", 220.0, "2024-01-24", "South"),
            new DataPoint("Electronics", 500.0, "2024-01-25", "North"),
            new DataPoint("Clothing", 85.0, "2024-01-26", "East")
        );
    }
}