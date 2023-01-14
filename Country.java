public class Country {
    String name;
    String abr;

    public Country(String abr, String name){
        this.name = name;
        this.abr = abr;
    }

    public String getAbr(){
        return abr;
    }

    public String getName(){
        return name;
    }

    @Override
    public int hashCode(){
        int code;
        String newAbr = abr.toLowerCase();
        char char1 = newAbr.charAt(0);
        char char2 = newAbr.charAt(1);

        int value1 = (int)(char1) - 96;
        int value2 = (int)(char2) - 96;

        code = (value1 * 26) + value2; 
        System.out.println(abr);
        System.out.println(code);
        return code;
    }

    @Override
    public boolean equals(Object o){
        Country temp = (Country)(o);
        if(temp.getAbr().equals(this.abr)){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return abr.toUpperCase() + "," + name;
    }

    public String printString(){
        return abr.toUpperCase() + " - " + name;
    }
}
