package ipleiria.estg.dei.ei.utils;

import java.util.Comparator;

public class FitnessComparator implements Comparator<Integer>{
        // Custom comparator to compare chromosome fitness. Configured to sort in descending order by default instead of ascending.
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1 < o2){
                return 1;
            }

            if (o1 > o2){
                return -1;
            }
            return 0;
        }
    }

