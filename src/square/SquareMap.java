package src.square;

import java.util.LinkedList;

// E fhad lclasse hya Move
public class SquareMap<E> {
    private LinkedList<E>[] mapE;
    private LinkedList<Square>[] mapS;
    private int size;

    public SquareMap(int size) {
        if(size == 0)
            size++;
        this.size = size;

        // kandiro LinkedList dial Moves fiha size (l3adad) dial LinkedLists ldakhl
        mapE = new LinkedList[size];
        for (int i = 0; i < mapE.length; i++) {
            mapE[i] = new LinkedList<>();
        }

        // kandiro LinkedList dial Squares fiha size (l3adad) dial LinkedLists ldakhl
        mapS = new LinkedList[size];
        for (int i = 0; i < mapS.length; i++) {
            mapS[i] = new LinkedList<>();
        }
    }

    public void add(Square square, E e) {
        int pos = calculate(square) % size;
        mapE[pos].add(e);
        mapS[pos].add(square);
    }

    public E remove(Square square) {
        int pos = calculate(square) % size;
        int index = mapS[pos].indexOf(square);
        if (index != -1) {
            mapS[pos].remove(index);
            return mapE[pos].remove(index);
        }
        return null;
    }

    // kat3ti lkola square wahd return value distincte 3la squares lkhrin
    private int calculate(Square square) {
        int k1 = square.getRow();
        int k2 = square.getCol();
        return (k1 + k2) * (k1 + k2 + 1) / 2 + k2;
    }
}
