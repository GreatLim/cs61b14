/* Set.java */

import list.*;

/**
 *  A Set is a collection of Comparable elements stored in sorted order.
 *  Duplicate elements are not permitted in a Set.
 **/
public class Set {
  public List set;
  /**
   * Set ADT invariants:
   *  1)  The Set's elements must be precisely the elements of the List.
   *  2)  The List must always contain Comparable elements, and those elements 
   *      must always be sorted in ascending order.
   *  3)  No two elements in the List may be equal according to compareTo().
   **/

  /**
   *  Constructs an empty Set. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public Set() {
    set = new DList();
  }

  /**
   *  cardinality() returns the number of elements in this Set.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int cardinality() {
    return set.length();
  }

  /**
   *  insert() inserts a Comparable element into this Set.
   *
   *  Sets are maintained in sorted order.  The ordering is specified by the
   *  compareTo() method of the java.lang.Comparable interface.
   *
   *  Performance:  runs in O(this.cardinality()) time.
   **/
  /*
  public void insert(Comparable c) {
    int num = cardinality();
    if(num == 0){
      set.insertFront(c);
    } else {
      ListNode n = set.front();
      for(int i = 0; i < num; i++){
        try {
          if(((Comparable) n.item()).compareTo(c) > 0){
            n.insertBefore(c);
            break;
          }
          if (((Comparable) n.item()).compareTo(c) < 0 && i == num - 1) {
            n.insertAfter(c);
            break;
          }
          if (((Comparable) n.item()).compareTo(c) == 0){
            break;
          }
          n = n.next();
        } catch (InvalidNodeException e){
          System.out.println(e);
        }
      }
    }
  }
  */
  public void insert(Comparable c) {
    int sign;
    ListNode n = set.front();
    while (n.isValidNode()) {
      try {
        sign = ((Comparable) n.item()).compareTo(c);
        if(sign > 0){
          n.insertBefore(c);
          break;
        } else if (sign == 0){
          break;
        }
        n = n.next();
      } catch (InvalidNodeException e){
        System.out.println(e);
      }
    }
    if(!n.isValidNode()){
      set.insertBack(c);
    }
  }
  /**
   *  union() modifies this Set so that it contains all the elements it
   *  started with, plus all the elements of s.  The Set s is NOT modified.
   *  Make sure that duplicate elements are not created.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Your implementation should NOT copy elements of s or "this", though it
   *  will copy _references_ to the elements of s.  Your implementation will
   *  create new _nodes_ for the elements of s that are added to "this", but
   *  you should reuse the nodes that are already part of "this".
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
   **/
  /*
  public void union(Set s) {
    ListNode n1 = set.front();
    ListNode n2 = s.set.front();
    int num1 = cardinality();
    int num2 = s.set.length();
    int i = 0;
    int j = 0;
    if(s.cardinality() == 0){
      return;
    }
    while(i < num1 && j < num2) {
      try{
        if(((Comparable) n1.item()).compareTo(n2.item()) > 0) {
          n1.insertBefore(n2.item());
          n2 = n2.next();
          j++;
        } else if(((Comparable) n1.item()).compareTo(n2.item()) == 0) {
          n1 = n1.next();
          n2 = n2.next();
          i++;
          j++;
        } else {
            n1 = n1.next();
            i++;
        }
      } catch (InvalidNodeException e){
        System.out.println(e);
      }
    }
    while(j < num2) {
      try{
        if(set.back().isValidNode()){
          n1 = set.back();
          n1.insertAfter(n2.item());
          n1 = n1.next();
          n2 = n2.next();
          j++;
        } else {
          if(j == 0){
            set.insertFront(n2.item());
            n2 = n2.next();
            j++;
          }
          n1 = set.back();
          n1.insertAfter(n2.item());
          n1 = n1.next();
          n2 = n2.next();
          j++;
        }
      } catch (InvalidNodeException e){
        System.out.println(e);
      }
    }
  }
  */
  public void union(Set s) {
    ListNode n1 = set.front();
    ListNode n2 = s.set.front();
    if(s.cardinality() == 0){
      return;
    }
    while(n1.isValidNode() && n2.isValidNode()) {
      try{
        int sign = ((Comparable) n1.item()).compareTo(n2.item());
        if(sign > 0) {
          n1.insertBefore(n2.item());
          n2 = n2.next();
        } else if(sign == 0) {
          n1 = n1.next();
          n2 = n2.next();
        } else {
          n1 = n1.next();
        }
      } catch (InvalidNodeException e){
        System.out.println(e);
      }
    }
    while(n2.isValidNode()) {
      try{
        set.insertBack(n2.item());
        n2 = n2.next();
      } catch (InvalidNodeException e){
        System.out.println(e);
      }
    }
  }

  /**
   *  intersect() modifies this Set so that it contains the intersection of
   *  its own elements and the elements of s.  The Set s is NOT modified.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Do not construct any new ListNodes during the execution of intersect.
   *  Reuse the nodes of "this" that will be in the intersection.
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT CONSTRUCT ANY NEW NODES.
   *  DO NOT ATTEMPT TO COPY ELEMENTS.
   **/

  public void intersect(Set s) {
    ListNode n1 = set.front();
    ListNode n2 = s.set.front();
    int num1 = cardinality();
    int num2 = s.set.length();
    int i = 0;
    int j = 0;
    if(cardinality() == 0){
      return;
    }
    while(j < num2 && i < num1) {
      try{
        if(((Comparable) n1.item()).compareTo(n2.item()) > 0) {
          n2 = n2.next();
          j++;
        } else if(((Comparable) n1.item()).compareTo(n2.item()) == 0) {
          n1 = n1.next();
          n2 = n2.next();
          i++;
          j++;
        } else {
          ListNode temp = n1;
          n1 = n1.next();
          temp.remove();
          i++;
        }
      } catch (InvalidNodeException e){
        System.out.println(e);
      }
    }
    while(i < num1) {
      try{
        ListNode temp = n1;
        n1 = n1.next();
        temp.remove();
        i++;
      } catch (InvalidNodeException e){
        System.out.println(e);
      }
    }
  }

  /*
  public void intersect(Set s) {
    ListNode n1 = set.front();
    ListNode n2 = s.set.front();
    if(cardinality() == 0){
      return;
    }
    while(n1.isValidNode() && n2.isValidNode()) {
      try{
        int sign = ((Comparable) n1.item()).compareTo(n2.item());
        if(sign > 0) {
          n2 = n2.next();
        } else if(sign == 0) {
          n1 = n1.next();
          n2 = n2.next();
        } else {
          ListNode temp = n1;
          n1 = n1.next();
          temp.remove();
        }
      } catch (InvalidNodeException e){
        System.out.println(e);
      }
    }
    while(n1.isValidNode()) {
      try{
        set.back().remove();
      } catch (InvalidNodeException e){
        System.out.println(e);
      }
    }
  }
  */

  /**
   *  toString() returns a String representation of this Set.  The String must
   *  have the following format:
   *    {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
   *            between them.
   *    {  1  2  3  } for a Set of three Integer elements.  No spaces before
   *            "{" or after "}"; two spaces before and after each element.
   *            Elements are printed with their own toString method, whatever
   *            that may be.  The elements must appear in sorted order, from
   *            lowest to highest according to the compareTo() method.
   *
   *  WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
   *            FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
   *            DEVIATIONS WILL LOSE POINTS.
   **/
  public String toString() {
    // Replace the following line with your solution.
    String result = "{  ";
    ListNode current = set.front();
    while (current.isValidNode()) {
      try{
        result = result + current.item() + "  ";
        current = current.next();
      } catch (InvalidNodeException e) {
        System.out.println(e);
      }
    }
    return result + "}";
  }

  public static void main(String[] argv) {
    /*
    Set s = new Set();
    s.insert(new Integer(3));
    s.insert(new Integer(4));
    s.insert(new Integer(3));
    System.out.println("Set s = " + s);
    System.out.println("s.cardinality() = " + s.cardinality());

    Set s2 = new Set();
    s2.insert(new Integer(4));
    s2.insert(new Integer(5));
    s2.insert(new Integer(5));
    System.out.println("Set s2 = " + s2);
    System.out.println("s2.cardinality() = " + s2.cardinality());


    Set s3 = new Set();
    s3.insert(new Integer(5));
    s3.insert(new Integer(3));
    s3.insert(new Integer(8));
    System.out.println("Set s3 = " + s3);
    System.out.println("s3.cardinality() = " + s3.cardinality());

    Set s4 = new Set();
    s4.insert(new Integer(1));
    s4.insert(new Integer(2));
    s4.insert(new Integer(4));

    System.out.println("Set s4 = " + s4);
    System.out.println("s4.cardinality() = " + s4.cardinality());

    Set s5 = new Set();
    s5.insert(new Integer(-1));
    s5.insert(new Integer(0));
    s5.insert(new Integer(1));
    s5.insert(new Integer(3));
    s5.insert(new Integer(4));
    s5.insert(new Integer(5));
    s5.insert(new Integer(6));
    s5.insert(new Integer(7));

    System.out.println("Set s5 = " + s5);
    System.out.println("s5.cardinality() = " + s5.cardinality());


    Set s6 = new Set();

    System.out.println("Set s6 = " + s6);
    System.out.println("s6.cardinality() = " + s6.cardinality());


    Set s7 = new Set();
    s7.insert(new Integer(1));
    s7.insert(new Integer(2));
    s7.insert(new Integer(4));

    System.out.println("Set s7 = " + s7);

    Set s8 = new Set();
    s8.insert(new Integer(-1));
    s8.insert(new Integer(0));
    s8.insert(new Integer(1));
    s8.insert(new Integer(3));
    s8.insert(new Integer(4));
    s8.insert(new Integer(5));
    s8.insert(new Integer(6));
    s8.insert(new Integer(7));

    System.out.println("Set s8 = " + s8);

    Set s9 = new Set();

    System.out.println("Set s9 = " + s9);

    Set s10 = new Set();
    s10.insert(new Integer(-1));
    s10.insert(new Integer(0));

    System.out.println("Set s9 = " + s10);

    s.union(s2);
    System.out.println("After s.union(s2), s = " + s);

    s4.union(s5);
    System.out.println("After s4.union(s5), s = " + s4);

    s4.union(s6);
    System.out.println("After s4.union(s6), s = " + s4);

    s6.union(s4);
    System.out.println("After s6.union(s4), s = " + s6);

    s.intersect(s3);
    System.out.println("After s.intersect(s3), s = " + s);


    s8.intersect(s7);
    System.out.println("After s8.intersect(s7), s8 = " + s8);

    s8.intersect(s9);
    System.out.println("After s8.intersect(s9), s8 = " + s8);

    s9.intersect(s10);
    System.out.println("After s9.intersect(s10), s19 = " + s9);
    */
    System.out.println("Testing insert()");
    Set s = new Set();
    s.insert(new Integer(3));
    s.insert(new Integer(4));
    s.insert(new Integer(3));
    System.out.println("Set s should be { 3 4 }: " + s);

    Set s2 = new Set();
    s2.insert(new Integer(4));
    s2.insert(new Integer(5));
    s2.insert(new Integer(5));
    System.out.println("Set s2 should be { 4 5 }: " + s2);

    Set s3 = new Set();
    s3.insert(new Integer(5));
    s3.insert(new Integer(3));
    s3.insert(new Integer(8));
    System.out.println("Set s3 should be { 3 5 8 }: " + s3);

    System.out.println();
    System.out.println("Tesing union()");
    s.union(s2);
    System.out.println("After s.union(s2), s should be { 3 4 5 }: " + s);
    s2.union(s3);
    System.out.println("After s2.union(s3), s2 should be { 3 4 5 8 }: " + s2);
    Set s4 = new Set();
    System.out.println("Empty set s4 = " + s4);
    s.union(s4);
    System.out.println("After s.union(s4), s should be { 3 4 5 }: " + s);
    s4.union(s);
    System.out.println("After s4.union(s), s4 should be { 3 4 5 }: " + s4);

    System.out.println();
    System.out.println("Tesing intersect()");
    Set s5 = new Set();
    Set s6 = new Set();
    s6.insert(new Integer(1));
    s5.intersect(s6);
    System.out.println("{}.intersect({1}) should be { }: " + s5);
    s6.intersect(s5);
    System.out.println("{1}.intersect({}) should be { }: " + s6);
    s6.insert(new Integer(1));
    Set s7 = new Set();
    s7.insert(new Integer(1));
    s7.insert(new Integer(2));
    s6.intersect(s7);
    System.out.println("{1}.intersect({1 2}) should be { 1 }: " + s6);
    s6.insert(new Integer(2));
    s6.insert(new Integer(3));
    s6.intersect(s7);
    System.out.println("{1 2 3}.intersect({1 2}) should be { 1 2 }: " + s6);
    s6.insert(new Integer(3));
    s6.insert(new Integer(5));
    s7.insert(new Integer(4));
    s7.insert(new Integer(7));
    s7.intersect(s6);
    System.out.println("{1 2 4 7}.intersect({1 2 3 5}) should be { 1 2 }: " + s7);

    System.out.println();
    System.out.println("Tesing cardinality()");
    System.out.println("s.cardinality() should be 3: " + s.cardinality());
    System.out.println("s4.cardinality() should be 3: " + s4.cardinality());
    System.out.println("s5.cardinality() should be 0: " + s5.cardinality());
    System.out.println("s6.cardinality() should be 4: " + s6.cardinality());
    System.out.println("s7.cardinality() should be 2: " + s7.cardinality());
    // You may want to add more (ungraded) test code here.
  }
}
