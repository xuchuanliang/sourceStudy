import entity.ListNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Solution {
    public static void main(String[] args) {
        ListNode l1 = new ListNode();
        l1.val = 2;
    }

    /**
     * 1. 两数之和
     * https://leetcode-cn.com/problems/two-sum/
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>(nums.length);
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],i);
        }
        for(int i=0;i<nums.length;i++){
            Integer index = map.get((target-nums[i]));
            if(Objects.nonNull(index) && index!=i){
                return new int[]{i,index};
            }
        }
        return null;
    }

    public int[] twoSumV2(int[] nums,int target){
        Map<Integer,Integer> map = new HashMap<>(nums.length);
        for(int i=0;i<nums.length;i++){
            int val = target-nums[i];
            if(map.get(val)!=null){
                return new int[]{i,map.get(val)};
            }else{
                map.put(nums[i],i);
            }
        }
        return null;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode firstNode = new ListNode();
        ListNode tempNode = firstNode;
        boolean carryFlg = false;
        while (l1!=null || l2!=null || carryFlg){
            int val1 = 0;
            int val2 = 0;
            if(null!=l1){
                val1 = l1.val;
            }
            if(null!=l2){
                val2 = l2.val;
            }
            int sum = val1+val2;
            if(carryFlg){
                sum = sum+1;
            }
            ListNode l = new ListNode();
            l.val = sum % 10;
            tempNode.next = l;
            tempNode = l;
            carryFlg = sum>=10;
        }
        return firstNode.next;
    }
}
