import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DataCruncherTest {
    private final DataCruncher dataCruncher = new DataCruncher();
    private Integer Ordering;

    // ignore
    @Test
    public void readAllTransactions() throws Exception {
        var transactions = dataCruncher.readAllTransactions();
        assertEquals(594643, transactions.size());
    }

    // example
    @Test
    public void readAllTransactionsAge0() throws Exception {
        var transactions = dataCruncher.readAllTransactionsAge0();
        assertEquals(3630, transactions.size());
    }

    // task1
    @Test
    public void getUniqueMerchantIds() throws Exception {
        var transactions = dataCruncher.getUniqueMerchantIds();
        assertEquals(50, transactions.size());
    }

    // task2
    @Test
    public void getTotalNumberOfFraudulentTransactions() throws Exception {
        var totalNumberOfFraudulentTransactions = dataCruncher.getTotalNumberOfFraudulentTransactions();
        assertEquals(297508, totalNumberOfFraudulentTransactions);
    }

    // task3
    @Test
    public void getTotalNumberOfTransactions() throws Exception {
        assertEquals(297508, dataCruncher.getTotalNumberOfTransactions(true));
        assertEquals(297135, dataCruncher.getTotalNumberOfTransactions(false));
    }

    // task4
    @Test
    public void getFraudulentTransactionsForMerchantId() throws Exception {
        Set<Transaction> fraudulentTransactionsForMerchantId = dataCruncher.getFraudulentTransactionsForMerchantId("M1823072687");
        assertEquals(149001, fraudulentTransactionsForMerchantId.size());
    }

    // task5
    @Test
    public void getTransactionForMerchantId() throws Exception {
        assertEquals(102588, dataCruncher.getTransactionsForMerchantId("M348934600", true).size());
        assertEquals(102140, dataCruncher.getTransactionsForMerchantId("M348934600", false).size());
    }


    private Boolean isSorted(List<Transaction> myList){
        int i=1;
        boolean sorted = true;
        while(i<myList.size() & sorted){
            if (myList.get(i).getAmount() <= myList.get(i-1).getAmount()){
                i++;
            }else{
                sorted=false;
            }
        }
        return sorted;
    }

    // task6
    @Test
    public void getAllTransactionSortedByAmount() throws Exception {
        List<Transaction> allTransactionsSortedByAmount = dataCruncher.getAllTransactionsSortedByAmount();
        assertEquals(true,isSorted(allTransactionsSortedByAmount));
    }

    // task7
    @Test
    public void getFraudPercentageForMen() throws Exception {
        double fraudPercentageForMen = dataCruncher.getFraudPercentageForMen();
        assertEquals(0.45, fraudPercentageForMen, 0.01);
    }

    // task8
    @Test
    public void getCustomerIdsWithNumberOfFraudulentTransactions() throws Exception {
        assertEquals(76, dataCruncher.getCustomerIdsWithNumberOfFraudulentTransactions(100).size());
        assertEquals(2119, dataCruncher.getCustomerIdsWithNumberOfFraudulentTransactions(80).size());
    }

    // task9
    @Test
    public void getCustomerIdToNumberOfTransactions() throws Exception {
        assertEquals((long)141,(long) dataCruncher.getCustomerIdToNumberOfTransactions().get("C1275518867"));
        assertEquals((long)79,(long) dataCruncher.getCustomerIdToNumberOfTransactions().get("C572671067"));

    }

    // task10
    @Test
    public void getMerchantIdToTotalAmountOfFraudulentTransactions() throws Exception {
        assertEquals( (double)3383, dataCruncher.getMerchantIdToTotalAmountOfFraudulentTransactions().get("M1053599405"), 0.01);
        assertEquals( (double)105, dataCruncher.getMerchantIdToTotalAmountOfFraudulentTransactions().get("M1416436880"), 0.01);
    }
}