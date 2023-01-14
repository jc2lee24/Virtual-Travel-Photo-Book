public class MyHashTable<K,V> {
    
    private DLList<V>[] values;
    private DLList<K> keys;

    @SuppressWarnings("unchecked")
    public MyHashTable(){
        values = new DLList[1000];
        keys = new DLList<K>();
    }

    public void put(K key, V value){
        if(values[key.hashCode()] == null){
            values[key.hashCode()] = new DLList<V>();
        }
        values[key.hashCode()].add(value);
        if(!keys.contains(key)){
            keys.add(key);
        }
    }
    
    public DLList<V> get(K key){
        return this.values[key.hashCode()];
    }
    
    @Override
    public String toString(){
        String printString = "";
        for(int i = 0; i < keys.size(); i++){
            printString += keys.get(i);
        }
        return printString;
    }
    
    public DLList<K> getKeys(){
        return this.keys;
    }

    public void remove(K key, V value){
        if(values[key.hashCode()] != null){
            values[key.hashCode()].remove(value);
        }
    }
    
    public void remove(K key){
        values[key.hashCode()] = null;
        keys.remove(key);
    }

    public String getSave(){
        String returnString = "";
        for(int i = 0; i < keys.size(); i++){
            if(this.values[keys.get(i).hashCode()] != null){
                for(int j = 0; j < values[keys.get(i).hashCode()].size(); j++){
                    returnString += keys.get(i).toString() + "," + values[keys.get(i).hashCode()].get(j).toString() + "\n";
                }
            }
        }

        return returnString;
    }

}