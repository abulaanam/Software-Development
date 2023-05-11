import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    private static final TrinaryHeap heap = new TrinaryHeap();

    //Test case where the heap is empty and the item is not in the heap
    @Test
    public void testContainsEmptyHeapItemNotInHeap() {
        assertFalse(heap.contains(0));
    }

    //Test case where the heap contains one node and the item is in the heap.
    @Test
    public void testContainsOneNodeItemInHeap() {
        heap.add(1, 5);
        assertTrue(heap.contains(5));
    }

    //Test case where the heap contains one node and the item is not in the heap.
    @Test
    public void testContainsOneNodeItemNotInHeap() {
        heap.add(1, 5);
        assertFalse(heap.contains(10));
    }


    //Test case #4 where the heap contains multiple nodes and the item is not in the heap.
    @Test
    public void testContainsMultipleNodesItemNotInHeap() {
        heap.add(1, 5);
        heap.add(2, 7);
        heap.add(3, 2);
        heap.add(4, 9);
        heap.add(5, 1);
        assertFalse(heap.contains(10));
    }

    //Test case #5 where the heap contains multiple nodes and the item is in the heap.
    @Test
    public void testContainsMultipleNodesItemInHeap() {
        heap.add(1, 5);
        heap.add(2, 7);
        heap.add(3, 2);
        heap.add(4, 9);
        heap.add(5, 1);
        assertTrue(heap.contains(7));
    }

    //Test case to throw exception when heap is empty
    @Test
    public void testEmptyHeap() {
        try {
            heap.contains(65);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }


    //Test case to throw null exception when sub tree is null
    @Test
    public void testSubTree() {
        try {
            heap.subTrinaryHeapHelper(3, null);
        } catch (NullPointerException e) {
            // Handle the exception here
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }



    //Test case to throw exception when index is out of range
    @Test
    public void testTrickleUpIndexOutOfBounds() {
        try {
            heap.add(5, 1);
            heap.trickleUp(3);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught an index out of bounds exception: " + e.getMessage());
            // Handle the exception here, if desired
        }
    }



}
