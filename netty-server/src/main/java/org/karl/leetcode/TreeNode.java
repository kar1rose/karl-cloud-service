package org.karl.leetcode;

/**
 * @author KARL ROSE
 * @date 2020/6/2 18:05
 **/
public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

/*class Test {
    public static void main(String[] args) {

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode p = l1;
        ListNode q = l2;
        ListNode curr = head;
        int carry = 0;
        while (q != null || p != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = x + y + carry;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (q != null) {
                q = q.next;
            }
            if (p != null) {
                p = p.next;
            }
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return null;
    }
}*/
