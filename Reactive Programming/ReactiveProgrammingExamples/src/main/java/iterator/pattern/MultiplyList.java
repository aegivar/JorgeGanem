package iterator.pattern;

import java.util.Iterator;
import java.util.function.Consumer;

public class MultiplyList implements Iterable<Integer> {

    private int position;
    private int size;
    private int number;

    public MultiplyList(int size, int number) {
        this.size = size;
        this.number = number;
    }

    @Override
    public Iterator<Integer> iterator() {
        int size = this.size;

        Iterator<Integer> iterator = new Iterator<Integer>(){

            @Override
            public boolean hasNext() {
                if(position<=size){
                    return true;
                }else{
                    return false;
                }
            }

            @Override
            public Integer next() {
                return number*position++;
            }
        };
        return iterator;
    }

}
