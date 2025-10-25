import java.util.List;
import java.util.Map;

public class DataVisualizer {
    
    public static void printTrendAnalysis(Map<String, Double> growthRates) {
        System.out.println("\n=== MONTHLY GROWTH RATES ===");
        System.out.println("=".repeat(50));
        
        for (Map.Entry<String, Double> entry : growthRates.entrySet()) {
            String month = entry.getKey();
            double growth = entry.getValue();
            String trend = growth >= 0 ? "📈" : "📉";
            String bar = growth >= 0 ? "🟩" : "🟥";
            int barLength = (int) Math.min(Math.abs(growth) / 5, 20);
            
            System.out.printf("%s: %s%.2f%% %s%n", 
                month, trend, growth, bar.repeat(barLength));
        }
    }
    
    public static void printCorrelationMatrix(Map<String, Map<String, Double>> correlations) {
        System.out.println("\n=== CATEGORY CORRELATION MATRIX ===");
        System.out.print("        ");
        
        for (String category : correlations.keySet()) {
            System.out.printf("%-12s", category.substring(0, Math.min(10, category.length())));
        }
        System.out.println();
        
        for (String cat1 : correlations.keySet()) {
            System.out.printf("%-8s", cat1.substring(0, Math.min(7, cat1.length())));
            for (String cat2 : correlations.keySet()) {
                if (cat1.equals(cat2)) {
                    System.out.printf("%-12s", "1.00");
                } else {
                    double corr = correlations.get(cat1).getOrDefault(cat2, 0.0);
                    System.out.printf("%-12.2f", corr);
                }
            }
            System.out.println();
        }
    }
    
    public static void printSeasonalAnalysis(Map<String, Map<String, Double>> seasonalData) {
        System.out.println("\n=== SEASONAL PATTERNS BY CATEGORY ===");
        
        for (Map.Entry<String, Map<String, Double>> entry : seasonalData.entrySet()) {
            String category = entry.getKey();
            Map<String, Double> monthlyData = entry.getValue();
            
            System.out.println("\n" + category + ":");
            double maxValue = monthlyData.values().stream().mapToDouble(Double::doubleValue).max().orElse(1.0);
            
            for (int month = 1; month <= 12; month++) {
                String monthKey = String.format("%02d", month);
                double value = monthlyData.getOrDefault(monthKey, 0.0);
                int barLength = (int) ((value / maxValue) * 30);
                String monthName = getMonthName(month);
                
                System.out.printf("  %-9s: %s %.2f%n", 
                    monthName, "█".repeat(barLength), value);
            }
        }
    }
    
    public static void printOutliers(List<DataPoint> outliers) {
        System.out.println("\n=== DETECTED OUTLIERS ===");
        System.out.println("Count: " + outliers.size());
        
        for (DataPoint outlier : outliers) {
            System.out.printf("🚨 %s - Value: %.2f (Category: %s, Region: %s)%n",
                outlier.getDate(), outlier.getValue(), outlier.getCategory(), outlier.getRegion());
        }
    }
    
    public static void printMovingAverage(Map<String, Double> movingAverages) {
        System.out.println("\n=== MOVING AVERAGE ===");
        
        for (Map.Entry<String, Double> entry : movingAverages.entrySet()) {
            System.out.printf("%s: %.2f%n", entry.getKey(), entry.getValue());
        }
    }
    
    public static void printPeriodComparison(Map<String, Map<String, Double>> comparison) {
        System.out.println("\n=== PERIOD COMPARISON ===");
        System.out.printf("%-15s %-12s %-12s %-10s%n", 
            "Category", "Period 1", "Period 2", "Change %");
        System.out.println("-".repeat(50));
        
        for (Map.Entry<String, Map<String, Double>> entry : comparison.entrySet()) {
            String category = entry.getKey();
            Map<String, Double> values = entry.getValue();
            double period1 = values.get("period1");
            double period2 = values.get("period2");
            double change = values.get("change_percent");
            String arrow = change >= 0 ? "↑" : "↓";
            
            System.out.printf("%-15s %-12.2f %-12.2f %s%-9.2f%%%n",
                category, period1, period2, arrow, Math.abs(change));
        }
    }
    
    public static void printCumulativeAnalysis(Map<String, Double> cumulativeSums) {
        System.out.println("\n=== CUMULATIVE SUM ANALYSIS ===");
        
        double finalTotal = cumulativeSums.values().stream()
                .mapToDouble(Double::doubleValue)
                .max().orElse(0);
        
        for (Map.Entry<String, Double> entry : cumulativeSums.entrySet()) {
            double percentage = (entry.getValue() / finalTotal) * 100;
            int progress = (int) (percentage / 2);
            
            System.out.printf("%s: %6.2f %s %5.1f%%%n",
                entry.getKey(), entry.getValue(), 
                "▰".repeat(progress) + "▱".repeat(50 - progress),
                percentage);
        }
    }
    
    public static void printPercentiles(Map<String, Double> percentiles) {
        System.out.println("\n=== VALUE DISTRIBUTION PERCENTILES ===");
        
        for (Map.Entry<String, Double> entry : percentiles.entrySet()) {
            System.out.printf("%s: %.2f%n", entry.getKey(), entry.getValue());
        }
    }
    
    public static void printRegionalAnalysis(Map<String, Map<String, Object>> regionalAnalysis) {
        System.out.println("\n=== REGIONAL PERFORMANCE ANALYSIS ===");
        
        for (Map.Entry<String, Map<String, Object>> entry : regionalAnalysis.entrySet()) {
            String region = entry.getKey();
            Map<String, Object> analysis = entry.getValue();
            Map<String, Object> stats = (Map<String, Object>) analysis.get("basic_stats");
            Map<String, Double> shares = (Map<String, Double>) analysis.get("category_shares");
            
            System.out.println("\n" + region.toUpperCase() + ":");
            System.out.printf("  Data Points: %d, Total: %.2f, Average: %.2f%n",
                analysis.get("data_points"), stats.get("sum"), stats.get("average"));
            
            System.out.println("  Category Shares:");
            for (Map.Entry<String, Double> share : shares.entrySet()) {
                System.out.printf("    %-12s: %.1f%%%n", share.getKey(), share.getValue());
            }
        }
    }
    
    public static void printForecast(Map<String, Object> forecast) {
        System.out.println("\n=== SIMPLE LINEAR FORECAST ===");
        System.out.printf("Slope: %.2f, Intercept: %.2f, R²: %.3f%n",
            forecast.get("slope"), forecast.get("intercept"), forecast.get("r_squared"));
        
        System.out.println("Future Projections:");
        Map<Integer, Double> futureValues = (Map<Integer, Double>) forecast.get("forecast");
        for (Map.Entry<Integer, Double> entry : futureValues.entrySet()) {
            System.out.printf("  Period +%d: %.2f%n", entry.getKey(), entry.getValue());
        }
    }
    
    public static void printBarChart(String title, Map<String, Double> data) {
        System.out.println("\n" + title);
        System.out.println("=".repeat(50));
        
        double maxValue = data.values().stream().mapToDouble(Double::doubleValue).max().orElse(1.0);
        int maxBarLength = 40;
        
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            String key = entry.getKey();
            double value = entry.getValue();
            int barLength = (int) ((value / maxValue) * maxBarLength);
            
            String bar = "█".repeat(barLength);
            System.out.printf("%-15s: %s %.2f%n", key, bar, value);
        }
    }
    
    public static void printStatistics(Map<String, Object> stats) {
        System.out.println("\n=== BASIC STATISTICS ===");
        System.out.printf("Count: %d%n", stats.get("count"));
        System.out.printf("Sum: %.2f%n", stats.get("sum"));
        System.out.printf("Average: %.2f%n", stats.get("average"));
        System.out.printf("Median: %.2f%n", stats.get("median"));
        System.out.printf("Max: %.2f%n", stats.get("max"));
        System.out.printf("Min: %.2f%n", stats.get("min"));
        System.out.printf("Standard Deviation: %.2f%n", stats.get("stdDev"));
        System.out.printf("Variance: %.2f%n", stats.get("variance"));
    }
    
    public static void printDataTable(List<DataPoint> data, String title) {
        System.out.println("\n" + title);
        System.out.println("=".repeat(80));
        System.out.printf("%-15s %-10s %-15s %-10s%n", 
            "Category", "Value", "Date", "Region");
        System.out.println("-".repeat(80));
        
        for (DataPoint dp : data) {
            System.out.printf("%-15s %-10.2f %-15s %-10s%n",
                dp.getCategory(), dp.getValue(), dp.getDate(), dp.getRegion());
        }
    }
    
    public static void printHistogram(List<DataPoint> data, int bins) {
        if (data.isEmpty()) return;
        
        double min = data.stream().mapToDouble(DataPoint::getValue).min().getAsDouble();
        double max = data.stream().mapToDouble(DataPoint::getValue).max().getAsDouble();
        double range = max - min;
        double binWidth = range / bins;
        
        int[] histogram = new int[bins];
        
        for (DataPoint dp : data) {
            int binIndex = (int) ((dp.getValue() - min) / binWidth);
            if (binIndex == bins) binIndex--;
            histogram[binIndex]++;
        }
        
        System.out.println("\n=== VALUE DISTRIBUTION HISTOGRAM ===");
        for (int i = 0; i < bins; i++) {
            double binStart = min + i * binWidth;
            double binEnd = min + (i + 1) * binWidth;
            String bar = "█".repeat(histogram[i] / 2);
            System.out.printf("%.2f - %.2f: %s (%d)%n", 
                binStart, binEnd, bar, histogram[i]);
        }
    }
    
    private static String getMonthName(int month) {
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                              "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month - 1];
    }
}