import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Recursive doubly-linked list for CS1331 2018 Summer HW9.
 * @author 1331 TAs and sralph3
 * @version 1
 * @param <E> Generic type parameter.
 * Refrences:
 * https://www.ics.uci.edu/~pattis/ICS-21/lectures/llrecursion/lecture.html
 */
public class RecursiveLinkedList<E> implements List<E> {

    /**
     * Do not delete this field! You must use this as your LinkedList head.
     */
    private LinkedListNode<E> head;
    private LinkedListNode<E> temp;
    private int size;

    /**
     * No-args constructor. You may add code to this, add other constructors,
     * or just leave this empty, but you may not delete this constructor
     * entirely.
     */
    public RecursiveLinkedList() {
    }

    /**
     * Add an element to the list.
     * @return True if the element is added
     * @param e the element to add
     */
    @Override
    public boolean add(E e) {
        if (head == null) {
            head = new LinkedListNode(e, null, null);
        } else {
            addHelp(e, head);
        }
        size++;
        return true;
    }

    private void addHelp(E e, LinkedListNode point) {
        if (point == null) {
            point = new LinkedListNode(e, temp, null);
            temp.setNext(point);
        } else {
            temp = point;
            addHelp(e, point.getNext());
        }
    }

    /**
     * Clear the list.
     */
    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * See if an object is stored in the list
     * @return True if the element is in the list
     * @param o the object to check
     */
    @Override
    public boolean contains(Object o) {
        if (head == null)  {
            return false;
        } else {
            temp = head;
            while (temp != null) {
                if (temp.getData().equals(o)) {
                    return true;
                }
                temp = temp.getNext();
            }
            return false;
        }
    }

    /**
     * See if an object is equal to the list
     * @return True if the object is equal to the list
     * @param o the object to check
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof List) {
            List w = (List<E>) o;
            if (w.size() == this.size()) {
                Iterator<E> itr = this.iterator();
                for (int i = 0; i < this.size; i++) {
                    if (this.get(i) != w.get(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * TOSTRING
     * @return The toString
     */
    @Override
    public String toString() {
        String str = "[";
        if (head == null)  {
            return (str + "]");
        } else {
            temp = head;
            str = str + temp;
            temp = temp.getNext();

            while (temp != null) {
                str = str + ", " + temp.getData();
                temp = temp.getNext();
            }
            str = str + "]";
            return str;
        }
    }

    /**
     * Create the list's hashcode & return it
     * @return The hashcode for the RLL
     */
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (E e : this) {
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        }
        return hashCode;
    }

    /**
     * get the first index of an object in the list
     * @return the index
     * @param o the object
     */
    @Override
    public int indexOf(Object o) {
        if (!this.contains(o)) {
            return -1;
        }

        temp = head;
        int ind = 0;
        while (temp != null) {
            if (temp.getData().equals(o)) {
                return ind;
            }
            temp = temp.getNext();
            ind++;
        }
        return -1;
    }

    /**
     * List iterator
     * @return an iterator over the list
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private LinkedListNode<E> itTemp = null;

            @Override
            public boolean hasNext() {
                if (itTemp == null && head != null) {
                    return true;
                } else if (itTemp != null) {
                    return (itTemp.getNext() != null);
                } else {
                    return false;
                }
            }

            @Override
            public E next() {
                if (itTemp == null) {
                    itTemp = head;
                } else {
                    itTemp = itTemp.getNext();
                }
                if (itTemp == null) {
                    throw new NoSuchElementException();
                }
                return itTemp.getData();
            }
        };
    }

    /**
     * get the object at an index
     * @return the object
     * @param index The index to retreive
     */
    @Override
    public E get(int index) {
        temp = head;
        if (temp == null) {
            throw new IndexOutOfBoundsException("No list");
        }
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return temp.getData();
            }
            if (temp.getNext() == null) {
                throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
            } else {
                temp = temp.getNext();
            }
        }
        return null;
    }

    /**
     * get the size (Number of objects) of the list
     * @return the size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes an object from the list
     * @return true if the object was removed
     * @param o the object
     */
    @Override
    public boolean remove(Object o) {
        if (!(this.contains(o))) {
            return false;
        } else {
            removeHelp(o, head);
            size--;
            return true;
        }
    }

    private void removeHelp(Object o, LinkedListNode point) {
        if (point.getData().equals(o)) {
            //If there is only one element in the list
            if (point.getPrev() == null && point.getNext() == null) {
                head = null;
            } else if (point.getPrev() == null) {
                //if the element is the head
                point.getNext().setPrev(null);
                head = point.getNext();
            } else if (point.getNext() == null) {
                //if the element is the tail
                point.getPrev().setNext(null);
            } else {
                point.getPrev().setNext(point.getNext());
                point.getNext().setPrev(point.getPrev());
            }
        } else {
            removeHelp(o, point.getNext());
        }
    }


    /**
     * toArray
     * @return an array of objects representing the list
     */
    @Override
    public Object[] toArray() {
        Object[] ary = new Object[this.size()];
        for (int i = 0; i < this.size(); i++) {
            ary[i] = this.get(i);
        }
        return ary;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    /**
     * get the last index of an object in the list
     * @return the index
     * @param o the object
     */
    @Override
    public int lastIndexOf(Object o) {
        if (!(this.contains(o))) {
            return -1;
        } else {
            return liHelp(o, head, 0, 0);

        }
    }

    private int liHelp(Object o, LinkedListNode point, int i, int ind) {
        if (point.getData().equals(o)) {
            if (point.getNext() == null) {
                return size - 1;
            } else {
                ind = i;
                i++;
                return liHelp(o, point.getNext(), i, ind);
            }
        } else if (point.getNext() == null) {
            return ind;
        } else {
            i++;
            return liHelp(o, point.getNext(), i, ind);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }



    @Override
    public E set(int index, E element) {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

}
