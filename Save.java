import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.net.*;


public class Save {

    private MyHashTable<Country, MyImage> table;
    private boolean hasData = false;
    private boolean firstLine = true;
    private String abr;
    private String location;
    private String country;
    private URL linkSave;
    private String saveString;

    public Save(){
        table = new MyHashTable<Country, MyImage>();
		try {
			Scanner scan = new Scanner(new FileReader("Data.txt"));
			while (scan.hasNextLine()){
                hasData = true;
                saveString = scan.nextLine();
                String[] data = saveString.split(",");
                URL link = new URL(data[2]);
                table.put(new Country(data[0], data[1]), new MyImage(link, data[3]));
                if(firstLine){
                    abr = data[0];
                    country = data[1];
                    linkSave = link;
                    location = data[3];

                }
            }
		}
		catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasSave(){
        return hasData;
    }

    public MyHashTable<Country, MyImage> getTable(){
        return table;
    }

    public String getAbr(){
        return abr;
    }
    
    public String getLocation(){
        return location;
    }

    public String getCountry(){
        return country;
    }

    public URL getURL(){
        return linkSave;
    }

    public String getSave(){
        return saveString;
    }
}
