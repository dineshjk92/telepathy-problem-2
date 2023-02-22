package org.telepathy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;


public class FindCheapPlan {

    public static Map<String, Double> sortPlanByPrice(Map<String, Double> planPrice) {
        Set<Entry<String, Double>> entries = planPrice.entrySet();

        Comparator<Entry<String, Double>> valueComparator = new Comparator<Entry<String,Double>>() {
            @Override
            public int compare(Entry<String, Double> e1, Entry<String, Double> e2) {
                Double v1 = e1.getValue();
                Double v2 = e2.getValue();
                return v1.compareTo(v2);
            }
        };

        List<Entry<String, Double>> listOfEntries = new ArrayList<Entry<String, Double>>(entries);

        Collections.sort(listOfEntries, valueComparator);
        LinkedHashMap<String, Double> sortedByValue = new LinkedHashMap<String, Double>(listOfEntries.size());

        for(Entry<String, Double> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }


        return sortedByValue;
    }

    private static void subsetsOf(List<String> plans, int k, int index, Set<String> tempSet, List<Set<String>> finalSet) {
        if (tempSet.size() == k) {
            finalSet.add(new HashSet<>(tempSet));
            return;
        }

        if (index == plans.size())
            return;

        String str = plans.get(index);

        tempSet.add(str);
        subsetsOf(plans, k, index+1, tempSet, finalSet);

        tempSet.remove(str);
        subsetsOf(plans, k, index+1, tempSet, finalSet);
    }

    public static List<Set<String>> combination(List<String> plans, int k) {
        List<Set<String>> planSubsets = new ArrayList<>();
        subsetsOf(plans, k, 0, new HashSet<String>(), planSubsets);
        return planSubsets;
    }

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        List<String> plans = new ArrayList<>();
        Map<String, Double> planPrice = new HashMap<>();
        Map<String, List> planFeature = new HashMap<>();
        Set<String> finalPlans = new HashSet<>();
        String bestPlans="";
        double bestPrice = 0;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;

        while ((st = br.readLine()) != null) {
            String planDetails[] = st.split(",", 3);
            List<String> features = Arrays.asList(planDetails[2].split(","));
            plans.add(planDetails[0]);
            planPrice.put(planDetails[0], Double.parseDouble(planDetails[1]));
            planFeature.put(planDetails[0], features);
        }

        planPrice = sortPlanByPrice(planPrice);

        for(int k=1; k<=plans.size() && finalPlans.size()==0; k++) {
            List<Set<String>> planSubset = combination(new ArrayList<String>(planPrice.keySet()), k);
            for(Set<String> subset : planSubset) {
                List<String> reqFeatures = Arrays.asList(args[1].split(","));
                for(String plan : subset) {
                    List<String> features = planFeature.get(plan);
                    List<String> finalReqFeatures = reqFeatures;
                    reqFeatures = finalReqFeatures.stream()
                            .filter(o -> !features.contains(o))
                            .collect(Collectors.toList());
                }
                if(reqFeatures.size() == 0) {
                    finalPlans = subset;
                    break;
                }
            }
        }

        for(String plan:finalPlans) {
            bestPrice += planPrice.get(plan);
            bestPlans += ","+plan;
        }
        System.out.println(bestPrice+bestPlans);

    }
}
