public class MazeTile {
    
    private int x,y;
    private char type;
    private boolean hasAgent;

    //3131313131123

    private boolean isTraversable(){
        return false;
    }

    public String toString(){
        if(hasAgent){
            return "A";

        
        }
        return String.valueOf(type);


    }
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public  char getType(){
        return type;
    }
    public boolean hasAgentAgent(){
        return hasAgent;
    }

    public void setAgent(boolean hasAgent){
        this.hasAgent = hasAgent;
    }
    
}

