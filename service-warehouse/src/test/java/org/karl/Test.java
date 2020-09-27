package org.karl;

/**
 * @author KARL ROSE
 * @date 2020/9/23 15:38
 **/
public class Test {

    public int minOperationsMaxProfit(int[] customers, int boardingCost, int runningCost) {
        /*HashMap<Integer, Integer> map = new HashMap<>();
        int maxProfit = 0;
        int profit;
        int uploadedCustomer = 0;
        int runNum = 0;

        for (int customer : customers) {
            int upCustomer = Math.min(customer, 4);
            uploadedCustomer += upCustomer;
            while (upCustomer <= customer) {
                runNum++;
                profit = uploadedCustomer * boardingCost - runNum * runningCost;
                maxProfit = Math.max(maxProfit, profit);
                map.put(maxProfit, runNum);
                if (upCustomer == customer) {
                    break;
                }
                if (customer - upCustomer < 4) {
                    uploadedCustomer += (customer - upCustomer);
                    upCustomer = customer;
                } else {
                    upCustomer += 4;
                    uploadedCustomer += 4;
                }
            }
        }
        return maxProfit > 0 ? map.get(maxProfit) : -1;*/
        int profit = 0, res = -1, rem = 0;
        int round = 0, sum = 0, n = customers.length;
        int i = 0;
        while(i < n || rem > 0) {
            rem += i < n ? customers[i++]: 0;
            int cur = Math.min(rem, 4);
            rem -= cur;
            round += 1;
            sum += cur;
            int cost = sum * boardingCost - runningCost * round;
            if(cost > profit) {
                profit = cost;
                res = round;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new Test().minOperationsMaxProfit(new int[]{10, 10, 6, 4, 7}, 3, 8));
    }
}
