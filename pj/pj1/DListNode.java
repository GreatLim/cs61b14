
public class DListNode {

  public Run item;
  public DListNode prev;
  public DListNode next;

  /**
   *  DListNode() constructor.
   */
  DListNode() {
    item = null;
    prev = null;
    next = null;
  }

  DListNode(Run i) {
    item = i;
    prev = null;
    next = null;
  }

}