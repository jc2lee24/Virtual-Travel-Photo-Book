public class CustomHashTable<E> {

    private DLList<E>[] table;

    @SuppressWarnings("unchecked")
    public CustomHashTable(){
       table = new DLList[21];
    }
  

    public void add(E obj){
        if(table[obj.hashCode()%21] == null){
            table[obj.hashCode()%21] = new DLList<E>();
        } 
        table[obj.hashCode()%21].add(obj);

        //make it sort when added
    }

    public void createList(int index){
        table[index] = new DLList<E>();
    }

    public void remove(E obj){
        table[obj.hashCode()].remove(obj);
    }

    public DLList<E> getList(int index){
        return table[index];
    }

    public DLList<E> getList(String abr){
        int code;
        char char1 = abr.charAt(0);
        char char2 = abr.charAt(1);

        int value1 = (int)(char1) - 96;
        int value2 = (int)(char2) - 96;

        code = (value1 * 26) + value2;
    
        return table[code%21];
    }

    public boolean contains(E obj){
        if(table[obj.hashCode()].contains(obj)){
            return true;
        }
        return false;
    }
    
    @Override
    public String toString(){
        String returnString = "";
        for(int i = 0; i < 21; i++){
            if(table[i] != null){
                returnString += table[i].toString() + "\n";
            }
        }
        return returnString;
    }
    
}
