package test;

public class Tile {
    public final  char letter;
    public final  int score;
    private Tile(char letter, int score){
        this.letter=letter;
        this.score=score;
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass()!= object.getClass()) return false;
        Tile other = (Tile)object;
        if (other.score != this.score||other.letter != this.letter ) return false;
        return true;

    }
    public static class Bag{
        private final int[] max_quantity;
        private int[] quantity;
        private int size;
        private final Tile[] tiles;
        private static Bag b=null;
        private Bag(){
            this.max_quantity=  new int[] { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1 };
            this.quantity= max_quantity.clone();
            this.size=0;
            for (int i=0; i<quantity.length; i++){
                size += quantity[i];
            }
            this.tiles = new Tile[] {new Tile('A', 1), new Tile('B', 3), new Tile('C', 3),
                    new Tile('D', 2), new Tile('E', 1), new Tile('F', 4),
                    new Tile('G', 2), new Tile('H', 4), new Tile('I', 1),
                    new Tile('J', 8), new Tile('K', 5), new Tile('L', 1),
                    new Tile('M', 3), new Tile('N', 1), new Tile('O', 1),
                    new Tile('P', 3), new Tile('Q', 10), new Tile('R', 1),
                    new Tile('S', 1), new Tile('T', 1), new Tile('U', 1),
                    new Tile('V', 4), new Tile('W', 4), new Tile('X', 8),
                    new Tile('Y', 4), new Tile('Z', 10) };

        }
        public static Bag getBag(){
            if (b==null){
                b= new Bag();
            }
            return b;
        }
        public Tile getRand(){
            if (size == 0) {
                return null;
            }
            int num = ((int) ((Math.random()*(26-1+1))+1))-1;
            if (this.quantity[num] > 0) {
                this.quantity[num]--;
                this.size--;
                return this.tiles[num];
            }
            return getRand();
        }
        public Tile getTile(char c){
            for (int i=0; i<tiles.length; i++){
                if (tiles[i].letter == c){
                    if (this.quantity[i] > 0) {
                        this.quantity[i]--;
                        this.size--;
                        return this.tiles[i];
                    }
                }
            }
            return null;
        }
        public void put(Tile tile){
            int index = tile.letter-'A';
            if(this.quantity[index]<this.max_quantity[index]){
                this.quantity[index]++;
                this.size++;
                return;
            }
        }
        public int size(){
            return this.size;
        }
        public int[] getQuantities(){
            return this.quantity.clone();
        }
    }

}

