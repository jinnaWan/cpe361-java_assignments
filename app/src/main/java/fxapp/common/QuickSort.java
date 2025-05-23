package fxapp.common;

import java.util.Comparator;
import java.util.List;

public class QuickSort<T> {
    
    public void sort(List<T> list, Comparator<? super T> comparator, boolean ascending) {
        if (list == null || list.size() <= 1) {
            return;
        }
        
        Comparator<? super T> effectiveComparator = ascending ? comparator : comparator.reversed();
        quickSort(list, 0, list.size() - 1, effectiveComparator);
    }
    
    private void quickSort(List<T> list, int low, int high, Comparator<? super T> comparator) {
        if (low < high) {
            int partitionIndex = partition(list, low, high, comparator);
            
            quickSort(list, low, partitionIndex - 1, comparator);
            quickSort(list, partitionIndex + 1, high, comparator);
        }
    }
    
    private int partition(List<T> list, int low, int high, Comparator<? super T> comparator) {
        T pivot = list.get(high);
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                
                // Swap list[i] and list[j]
                T temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        
        // Swap list[i+1] and list[high] (pivot)
        T temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        
        return i + 1;
    }
}
