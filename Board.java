package test;

import java.util.ArrayList;

public class Board {
    private final int dl;
    private final int  dw;
    private final int tl;
    private final int tw;
    private boolean isEmpty;
    private final int[][] bonus;
    private Tile[][] tiles;
    public static Board b=null;
    private Board(){
        this.dl=2;	// double letter
        this.tl=3;	// triple letter
        this.dw=20;	// double word
        this.tw=30;	// triple word
        this.isEmpty=true;
        this.tiles= new Tile[15][15];
        this.bonus= new int[][] {
                {tw,0,0,dl,0,0,0,tw,0,0,0,dl,0,0,tw},
                {0,dw,0,0,0,tl,0,0,0,tl,0,0,0,dw,0},
                {0,0,dw,0,0,0,dl,0,dl,0,0,0,dw,0,0},
                {dl,0,0,dw,0,0,0,dl,0,0,0,dw,0,0,dl},
                {0,0,0,0,dw,0,0,0,0,0,dw,0,0,0,0},
                {0,tl,0,0,0,tl,0,0,0,tl,0,0,0,tl,0},
                {0,0,dl,0,0,0,dl,0,dl,0,0,0,dl,0,0},
                {tw,0,0,dl,0,0,0,dw,0,0,0,dl,0,0,tw},
                {0,0,dl,0,0,0,dl,0,dl,0,0,0,dl,0,0},
                {0,tl,0,0,0,tl,0,0,0,tl,0,0,0,tl,0},
                {0,0,0,0,dw,0,0,0,0,0,dw,0,0,0,0},
                {dl,0,0,dw,0,0,0,dl,0,0,0,dw,0,0,dl},
                {0,0,dw,0,0,0,dl,0,dl,0,0,0,dw,0,0},
                {0,dw,0,0,0,tl,0,0,0,tl,0,0,0,dw,0},
                {tw,0,0,dl,0,0,0,tw,0,0,0,dl,0,0,tw}};

    }

    public Tile[][] getTiles() {
        return tiles.clone();
    }
    public static Board getBoard(){
        if (b==null){
            b = new Board();
        }
        return b;
    }

    private boolean inBoard(int row, int col) {
        return (row>=0 && row<tiles.length && col>=0 && col<tiles[0].length);
    }

    private boolean onStar(Word w) {
        int i=w.getRow();
        int j=w.getCol();
        //for(int k = 0; k <w.getTiles().length; k++) {
        for(@SuppressWarnings("unused") Tile t: w.getTiles()) {
            if(i==7 && j==7)
                return true;
            if(w.isVertical())
                i++;
            else
                j++;
        }
        return false;
    }

    private boolean crossTile(Word w) {
        int i=w.getRow(),j=w.getCol();
        for(@SuppressWarnings("unused") Tile t: w.getTiles()){

            if(tiles[i][j]!=null ||
                    (inBoard(i+1, j) 	&& tiles[i+1][j]!=null)   ||
                    // (inBoard(i+1, j+1) 	&& tiles[i+1][j+1]!=null) ||
                    (inBoard(i, j+1) 	&& tiles[i][j+1]!=null)   ||
                    // (inBoard(i-1, j+1) 	&& tiles[i-1][j+1]!=null) ||
                    (inBoard(i-1, j) 	&& tiles[i-1][j]!=null)   ||
                    // (inBoard(i-1, j-1) 	&& tiles[i-1][j-1]!=null) ||
                    (inBoard(i, j-1) 	&& tiles[i][j-1]!=null))   //||
                //(inBoard(i+1, j-1) 	&& tiles[i+1][j-1]!=null))
                return true;

            if(w.isVertical()) i++; else j++;
        }
        return false;
    }

    private boolean isChangable(Word w) {
        int i=w.getRow(),j=w.getCol();
        for(Tile t : w.getTiles()) {
            if(tiles[i][j]!=null && tiles[i][j]!=t)
                return  true;
            if(w.isVertical()) i++; else j++;
        }
        return false;
    }

    public boolean boardLegal(Word w){
        int row=w.getRow();
        int col=w.getCol();

        int vCol, vRow;
        if(!inBoard(row, col))
            return false;
        if(w.isVertical()) {
            vCol=col;
            vRow=row+w.getTiles().length-1;
        }else {
            vRow=row;
            vCol=col+w.getTiles().length-1;
        }
        if(!inBoard(vRow, vCol))
            return false;
        if(isEmpty && !onStar(w))
            return false;

        if(!isEmpty && !crossTile(w))
            return false;

        if(isChangable(w))
            return false;

        return true;
    }


    public boolean dictionaryLegal(Word w){
        return true;
    }

    public int getScore(Word w){
        int isdw=0;
        int istw=0;
        int score=0;
        int r=w.getRow();
        int c=w.getCol();
        for (int i=0;i<w.getTiles().length;i++){
            score+=w.getTiles()[i].score;
            if(this.bonus[r][c]==dl){
                score+=w.getTiles()[i].score;
            }
            if(this.bonus[r][c]==tl){
                score+=2*(w.getTiles()[i].score);
            }
            if(this.bonus[r][c]==dw){
                isdw++;
            }
            if(this.bonus[r][c]==tw){
                istw++;
            }

            if(w.isVertical())r++;
            if(!w.isVertical())c++;

        }
        while(isdw>0){
            score= 2*score;
            isdw--;
        }
        while(istw>0){
            score= 3*score;
            istw--;
        }
        return score;
    }
    private ArrayList<Word> getAllWords(Tile[][] tiles){
        ArrayList<Word> words = new ArrayList<Word>();
        //horizontal words
        for (int i=0;i<tiles[0].length;i++){
            int j=0;
            while(j<tiles[i].length){
                ArrayList<Tile> tls= new ArrayList<>();
                int row=i;
                int col= j;
                while(j<tiles[i].length&& tiles[i][j]!= null){
                    tls.add(tiles[i][j]);
                    j++;
                }
                if(tls.size()>1){
                    Tile[] newtiles= new Tile[tls.size()];
                    words.add(new Word(tls.toArray(newtiles),row,col,false));//horizontal
                }
                j++;
            }
        }
        for (int j=0;j<tiles[0].length;j++){
            int i=0;
            while(i<tiles[j].length){
                ArrayList<Tile> tls= new ArrayList<>();
                int row=i;
                int col= j;
                while(i<tiles[i].length&& tiles[i][j]!= null){
                    tls.add(tiles[i][j]);
                    i++;
                }
                if(tls.size()>1){
                    Tile[] newtiles= new Tile[tls.size()];
                    words.add(new Word(tls.toArray(newtiles),row,col,true));//vertical
                }
                i++;
            }
        }
        return words;

    }
    public ArrayList<Word> getWords(Word w){
        Tile[][] tiles= getTiles();
        ArrayList<Word> before= getAllWords(tiles);
        int row=w.getRow();
        int col=w.getCol();
        for(Tile t: w.getTiles()){
            tiles[row][col]=t;
            if(w.isVertical()){
                row++;
            }
            else{
                col++;
            }
        }
        Tile[][] aftertiles= getTiles();
        ArrayList<Word> after= getAllWords(aftertiles);
        after.removeAll(before);
        return after;
    }
    public int tryPlaceWord(Word w) {

        Tile[] ts = w.getTiles();
        int row=w.getRow();
        int col=w.getCol();
        for(int i=0;i<ts.length;i++) {
            if(ts[i]==null)
                ts[i]=tiles[row][col];
            if(w.isVertical()){ row++;} else {col++;}
        }

        Word test=new Word(ts, w.getRow(), w.getCol(), w.isVertical());

        int sum=0;
        if(boardLegal(test) ) {
            ArrayList<Word> newWords=getWords(test);
            for(Word nw : newWords) {
                if(dictionaryLegal(nw))
                    sum+=getScore(nw);
                else
                    return 0;
            }
        }

        // the placement
        row=w.getRow();
        col=w.getCol();
        for(Tile t : w.getTiles()) {
            tiles[row][col]=t;
            if(w.isVertical()) row++; else col++;
        }

        if(isEmpty) {
            isEmpty=false;
            bonus[7][7]=0;
        }
        return sum;
    }
}

