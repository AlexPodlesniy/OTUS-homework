package homework;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> customersData;

    public CustomerService() {
        this.customersData = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        return exportImmutableEntry(customersData.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return exportImmutableEntry(customersData.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customersData.put(customer, data);
    }

    private Map.Entry<Customer, String> exportImmutableEntry(Map.Entry<Customer, String> source) {
        return (source == null) ? null :
                new AbstractMap.SimpleImmutableEntry<>(
                        new Customer(source.getKey().getId(), source.getKey().getName(), source.getKey().getScores()),
                        source.getValue());
    }
}
