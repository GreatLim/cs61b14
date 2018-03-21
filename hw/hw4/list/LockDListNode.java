package list;

public class LockDListNode extends DListNode {
    protected boolean isLocked;
    public LockDListNode(Object i, DListNode p, DListNode n){
        super(i, p, n);
        this.isLocked = false;
    }
}
