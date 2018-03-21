
public class DList {
  protected DListNode head;
  protected long size;

  public DList() {
    head = new DListNode();
    head.item = null;
    head.next = head;
    head.prev = head;
    size = 0;
  }

  public DList(Run a) {
    head = new DListNode();
    head.item = null;
    head.next = new DListNode();
    head.next.item = a;
    head.prev = head.next;
    head.next.prev = head;
    head.prev.next = head;
    size = 1;
  }

  public void insertFront(Run i) {
    DListNode n1 = new DListNode(i);
    n1.next = head.next;
    head.next.prev = n1;
    head.next = n1;
    n1.prev = head;
    size ++;
  }

  public void insertEnd(Run i) {
    DListNode n1 = new DListNode(i);
    n1.next = head;
    head.prev.next = n1;
    n1.prev = head.prev;
    head.prev = n1;
    size ++;
  }

  public void removeFront() {
    if(size != 0){
      DListNode n1 = head.next;
      head.next = n1.next;
      n1.next.prev = head;
      n1.next = null;
      n1.prev = null;
      size --;
    }
  }

  public void remove(DListNode node){
    if(size != 0){
      node.prev.next = node.next;
      node.next.prev = node. prev;
      node.next = null;
      node.prev = null;
      size --;
    }
  }

  public void link(DListNode node1, DListNode node2, DListNode node3){
    node1.next = node2;
    node2.prev = node1;
    node2.next = node3;
    node3.prev = node2;
    size ++;
  }
}


