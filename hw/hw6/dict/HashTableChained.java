/* HashTableChained.java */

package dict;

import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
  list.DList[] table;

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    int size = sizeEstimate;
    while(!isPrime(size)){
      size ++;
    }
    table = new DList[size];
  }

  private boolean isPrime(int n){
    if(n <= 1){
      return false;
    } else {
      for(int i = 2; i * i <= n; i++) {
        if(n % i == 0){
          return false;
        }
      }
    }
    return true;
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    this(100);
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    int bucket = Math.abs(code) % size();
    return bucket;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    return table.length;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    for(int i = 0; i < size(); i ++) {
      if(table[i] != null) {
        return false;
      }
    }
    return true;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    int bucket = compFunction(key.hashCode());
    if(table[bucket] == null) {
      table[bucket] = new DList();
    }
    Entry e = new Entry(key, value);
    table[bucket].insertBack(e);
    return e;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    int bucket = compFunction(key.hashCode());
    ListNode n = table[bucket].front();
    while(n.isValidNode()) {
      try{
        if(((Entry) n.item()).key.equals(key)){
          return (Entry) n.item();
        }
        n = n.next();
      } catch (InvalidNodeException e){
        System.out.println(e);
      }
    }
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    DList list = table[compFunction(key.hashCode())];
    ListNode n = list.front();
    while(n.isValidNode()) {
      try{
        if(((Entry) n.item()).key.equals(key)){
          Entry temp = (Entry) n.item();
          n.remove();
          return temp;
        }
        n = n.next();
      } catch (InvalidNodeException e){
        System.out.println(e);
      }
    }
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
    for(int i = 0; i < size(); i ++) {
      if(table[i] != null) {
        try{
          ListNode n = table[i].front();
          while(n.isValidNode()){
            n.remove();
            n = table[i].front();
          }
        } catch (InvalidNodeException e){
          System.out.println(e);
        }
      }
    }
  }

  public int numCollision(){
    int num = 0;
    for (int i = 0; i < size(); i++) {
      if(table[i] != null && table[i].length() > 1) {
        num += table[i].length() - 1;
      }
    }
    return num;
  }



  public String toString() {
    String str = "";
    for (int i = 0; i < size(); i++) {
      int num;
      if(table[i] != null) {
        num = table[i].length();
      } else {
        num = 0;
      }
      str += "[" + num + "] ";
      if((i + 1) % 10 == 0) {
        str += "\n";
      }
    }
    return str;
  }
}
