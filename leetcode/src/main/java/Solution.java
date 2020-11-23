import entity.ListNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Solution {
    public static void main(String[] args) {
//        ListNode l1 = new ListNode();
//        l1.val = 2;
//        ListNode l11 = new ListNode();
//        l11.val=4;
//        ListNode l111 = new ListNode();
//        l111.val= 3;
//        l1.next = l11;
//        l11.next = l111;
//
//        ListNode l2 = new ListNode();
//        l2.val = 5;
//        ListNode l22 = new ListNode();
//        l22.val=6;
//        ListNode l222 = new ListNode();
//        l222.val= 4;
//        l2.next = l22;
//        l22.next = l222;
//
//        ListNode result = addTwoNumbers(l1,l2);
//        System.out.println(result);

//        System.out.println(lengthOfLongestSubstring("dvdf"));
//        System.out.println(lengthOfLongestSubstring("pwwkew"));
//        System.out.println(lengthOfLongestSubstring("tmmzuxt"));
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

    /**
     * 2.两数相加
     * https://leetcode-cn.com/problems/add-two-numbers/
     * 思路：两个链表从左向右依次相加，并且将相加后的结果对10取余后放在第三个链表中，且记录是否进位，直到两个链表都到头且不需要进位为止，则第三个链表存储的数据就是和
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //返回结果的持有者
        ListNode pre = new ListNode();
        ListNode currentPre = pre;
        ListNode current1 = l1;
        ListNode current2 = l2;
        boolean carry = false;//是否需要进位
        //只要当前位置还有值或者进位标识为true，即继续增加
        while (current1!=null || current2!=null || carry){
            //获取两个节点的值
            int val1 = current1!=null ? current1.val : 0;
            int val2 = current2!=null ? current2.val : 0;
            //计算总计
            int sum = carry ? 1 : 0;
            sum = sum + val1 + val2;
            //还原进位
            carry = false;
            //创建当前新的节点，并追加到pre后面
            ListNode tempPre = new ListNode();
            tempPre.val = sum % 10;
            currentPre.next = tempPre;
            currentPre = tempPre;
            //更新是否进位
            carry = sum >= 10;
            //更新current为下一个节点
            current1 = current1!=null ? current1.next : null;
            current2 = current2!=null ? current2.next : null;
        }
        return pre.next;
    }

    /**
     * 3.无重复字符的最长子串
     * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     * 思路：滑动窗口，从左向右遍历字符串的每一个字符，未遇到重复则将最大长度加一；三个元素：左边的索引，右边的索引，记录最大长度
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        int left = 0;
        Map<Character,Integer> map = new HashMap<>();
        for(int i=0;i<s.length();i++){
            if(map.containsKey(s.charAt(i)) && map.get(s.charAt(i)) >= left){
                //窗口移动到重复字符的前一个的位置后面
                left = map.get(s.charAt(i))+1;
            }
            maxLength = Math.max(maxLength,i-left+1);
            map.put(s.charAt(i),i);
        }
        return maxLength;
    }

    /**
     * 4.最长回文子串
     *https://leetcode-cn.com/problems/longest-palindromic-substring/
     * 思路：
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        return null;
    }
}
