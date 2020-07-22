import javax.xml.transform.Result;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DataCruncher {

    private static Object Comparators;

    // do not modify this method - just use it to get all the Transactions that are in scope for the exercise
    public List<Transaction> readAllTransactions() throws Exception {
        return Files.readAllLines(Paths.get("src/main/resources/payments.csv"), StandardCharsets.UTF_8)
                .stream()
                .skip(1)
                .map(line -> {
                    var commaSeparatedLine = List.of(line
                            .replaceAll("'", "")
                            .split(",")
                    );
                    var ageString = commaSeparatedLine.get(2);
                    var ageInt = "U".equals(ageString) ? 0 : Integer.parseInt(ageString);
                    return new Transaction(commaSeparatedLine.get(1),
                            ageInt,
                            commaSeparatedLine.get(3),
                            commaSeparatedLine.get(4),
                            commaSeparatedLine.get(5),
                            commaSeparatedLine.get(6),
                            commaSeparatedLine.get(7),
                            Double.parseDouble(commaSeparatedLine.get(8)),
                            "1".equals(commaSeparatedLine.get(9)));
                })
                .collect(Collectors.toList());
    }

    // example
    public List<Transaction> readAllTransactionsAge0() throws Exception {
        return readAllTransactions().stream()
                .filter(transaction -> transaction.getAge() == 0)
                .collect(Collectors.toList());
    }

    // task 1
    public Set<String> getUniqueMerchantIds() throws Exception {
        List<Transaction> users = this.readAllTransactions();
        return users.stream()
                    .map(Transaction::getMerchantId)
                    .collect(Collectors.toSet());
    }

    // task 2
    public long getTotalNumberOfFraudulentTransactions() throws Exception {
        return readAllTransactions().stream().filter(Transaction::isFraud).count();
    }

    // task 3
    public long getTotalNumberOfTransactions(boolean isFraud) throws Exception {
        return readAllTransactions().stream().filter(bol -> bol.isFraud() == isFraud).count();

    }

    // task 4
    public Set<Transaction> getFraudulentTransactionsForMerchantId(String merchantId) throws Exception {
        return readAllTransactions().stream()
                .filter(transaction -> transaction.getMerchantId().equals(merchantId) && transaction.isFraud())
                .collect(Collectors.toSet());
    }

    // task 5
    public Set<Transaction> getTransactionsForMerchantId(String merchantId, boolean isFraud) throws Exception {
        return readAllTransactions().stream()
                .filter(transaction -> transaction.getMerchantId().equals(merchantId) && transaction.isFraud()==isFraud)
                .collect(Collectors.toSet());
    }

    // task 6
    public List<Transaction> getAllTransactionsSortedByAmount() throws Exception {
        return readAllTransactions().stream().sorted(Comparator.comparing(Transaction::getAmount).reversed()).collect(Collectors.toList());
    }

    // task 7
    public double getFraudPercentageForMen() throws Exception {
        double fraud_men = readAllTransactions()
                .stream()
                .filter(transaction -> transaction.getGender().equals("M") & transaction.isFraud())
                .count();
        return fraud_men/getTotalNumberOfFraudulentTransactions();
    }

    // task 8
    public Set<String> getCustomerIdsWithNumberOfFraudulentTransactions(int numberOfFraudulentTransactions) throws Exception {
        return readAllTransactions().stream().filter(Transaction::isFraud)
                .collect(Collectors.groupingBy(Transaction::getCustomerId, Collectors.counting()))
                .entrySet().stream().filter(m -> m.getValue()>=numberOfFraudulentTransactions)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).keySet();
    }

    // task 9
    public Map<String, Integer> getCustomerIdToNumberOfTransactions() throws Exception {
        return readAllTransactions().stream().filter(Transaction::isFraud)
                .collect(Collectors.groupingBy(Transaction::getCustomerId, Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().intValue()));
    }

    // task 10
    public Map<String, Double> getMerchantIdToTotalAmountOfFraudulentTransactions() throws Exception {
        return readAllTransactions().stream().filter(Transaction::isFraud)
                .collect(Collectors.groupingBy(Transaction::getMerchantId, Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().doubleValue()));
    }

    // bonus
    public double getRiskOfFraudFigure(Transaction transaction) throws Exception {
        return 1.0;
    }

//    public static void main(String[] args) throws Exception {
//        DataCruncher my_obj = new DataCruncher();
//        "M1823072687"
//        System.out.println(my_obj.getCustomerIdsWithNumberOfFraudulentTransactions(100));
//        System.out.println(
//
//        my_obj.getAllTransactionsSortedByAmount().stream().filter(a -> a.getAmount()).sorted()
//        );
//    }
}
