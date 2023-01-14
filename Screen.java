import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;


public class Screen extends JPanel implements ActionListener{

    FileReader reader;
    FileWriter writer;

    File countryFile = new File("./Countries.txt");
    File save = new File("./Data.txt");

    DLList<MyImage> images;

    public URL link;
    public boolean hasImage = false;
    public String abr;
    public String country;
    public int currentImage = 0;
    public String landmark;
    public String fileString = "";

    MyHashTable<Country, MyImage> myTable;
    CustomHashTable<Country> countries;

    private JButton previousImage;
    private JButton nextImage;
    private JButton deleteImage;
    private JTextField enterAbr;
    private JTextField enterLocation;
    private JTextField enterURL;
    private JButton addImage;
    private JButton searchCountry;
    private JTextArea countriesList;


    public Screen(){
        this.setLayout(null);
        countries = new CustomHashTable<Country>();
        myTable = new MyHashTable<Country, MyImage>();

        //add list of countries
        try{
            reader = new FileReader(countryFile);
            Scanner text = new Scanner(new FileReader("Countries.txt"));
            while(text.hasNext()){
                String[] data = text.nextLine().split(",");
                countries.add(new Country(data[0], data[1]));
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
        
        Save s = new Save();
        if(s.hasSave()){
            myTable = s.getTable();
            hasImage = true;
            abr = s.getAbr();
            country = s.getCountry();
            currentImage = 0;
            landmark = s.getLocation();
        }
        if(s.hasSave())
        images = myTable.get(new Country(abr, country));


        previousImage = new JButton();
        previousImage.setFont(new Font("Arial", Font.PLAIN, 20));
        previousImage.setHorizontalAlignment(SwingConstants.CENTER);
        previousImage.setBounds(295, 405, 50, 30);
        previousImage.setText("<=");
        this.add(previousImage);
        previousImage.addActionListener(this);

        nextImage = new JButton();
        nextImage.setFont(new Font("Arial", Font.PLAIN, 20));
        nextImage.setHorizontalAlignment(SwingConstants.CENTER);
        nextImage.setBounds(565, 405, 50, 30);
        nextImage.setText("=>");
        this.add(nextImage);
        nextImage.addActionListener(this);

        deleteImage = new JButton();
        deleteImage.setFont(new Font("Arial", Font.BOLD, 20));
        deleteImage.setHorizontalAlignment(SwingConstants.CENTER);
        deleteImage.setBounds(355, 405, 200, 30);
        deleteImage.setText("Delete Image");
        this.add(deleteImage);
        deleteImage.addActionListener(this);

        enterAbr = new JTextField();
        enterAbr.setFont(new Font("Arial", Font.PLAIN, 20));
        enterAbr.setHorizontalAlignment(SwingConstants.CENTER);
        enterAbr.setBounds(55, 455, 200, 30);
        enterAbr.setText("Abreviation");
        this.add(enterAbr);

        enterLocation = new JTextField();
        enterLocation.setFont(new Font("Arial", Font.PLAIN, 20));
        enterLocation.setHorizontalAlignment(SwingConstants.CENTER);
        enterLocation.setBounds(305, 455, 200, 30);
        enterLocation.setText("Location");
        this.add(enterLocation);

        enterURL = new JTextField();
        enterURL.setFont(new Font("Arial", Font.PLAIN, 20));
        enterURL.setHorizontalAlignment(SwingConstants.CENTER);
        enterURL.setBounds(555, 455, 200, 30);
        enterURL.setText("URL");
        this.add(enterURL);

        addImage = new JButton();
        addImage.setFont(new Font("Arial", Font.BOLD, 20));
        addImage.setHorizontalAlignment(SwingConstants.CENTER);
        addImage.setBounds(180, 504, 200, 30);
        addImage.setText("Add");
        this.add(addImage);
        addImage.addActionListener(this);

        searchCountry = new JButton();
        searchCountry.setFont(new Font("Arial", Font.BOLD, 20));
        searchCountry.setHorizontalAlignment(SwingConstants.CENTER);
        searchCountry.setBounds(455, 505, 200, 30);
        searchCountry.setText("Search");
        this.add(searchCountry);
        searchCountry.addActionListener(this);

        countriesList = new JTextArea();
        countriesList.setFont(new Font("Arial", Font.PLAIN, 10));
        countriesList.setBounds(50,50,700,400);
        countriesList.setText(myTable.toString());
        countriesList.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(countriesList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(50,50,200,350);
        this.add(scrollPane);
    }


    public Dimension getPreferredSize(){
        return new Dimension(800, 600);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        countriesList.setText(myTable.toString());

        if(hasImage){
            myTable.get(new Country(abr, country)).get(currentImage).drawMe(g, 400, 100);
            g.drawString(landmark, 510, 370);
        }
    }

    public void writeToFile(){
        try{
            writer = new FileWriter(save);
            //add items to data
            String save = myTable.getSave();
            writer.write(save);

            writer.flush();
            writer.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource() == addImage){
            String urlString = enterURL.getText();
            try {
                link = new URL(urlString);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
            abr = enterAbr.getText().toLowerCase();
            DLList<Country> temp = countries.getList(abr);
            for(int i = 0; i < temp.size(); i++){
                if(temp.get(i).getAbr().equals(abr)){
                    country = temp.get(i).getName();
                }
            }
            
            landmark = enterLocation.getText();
            myTable.put(new Country(abr, country), new MyImage(link, landmark));
            writeToFile();
            images = myTable.get(new Country(abr, country));

            landmark = images.get(currentImage).getLandmark();

            if(!hasImage)
            hasImage = true;

        }

        else if(e.getSource() == searchCountry){
            if(images.size() != 0){
                hasImage = true;
            }
            currentImage = 0;
            abr = enterAbr.getText();
            images = myTable.get(new Country(abr, country));
            landmark = images.get(currentImage).getLandmark();
        }

        else if(e.getSource() == nextImage){
            DLList<Country> temp = countries.getList(abr);
            if(temp.size() - 1 > currentImage){
                currentImage++;
                images = myTable.get(new Country(abr, country));
                landmark = images.get(currentImage).getLandmark();
            }

        }

        else if(e.getSource() == previousImage){
            if(currentImage > 0){
                currentImage --;
                images = myTable.get(new Country(abr, country));
                landmark = images.get(currentImage).getLandmark();
            }
        }

        else if(e.getSource() == deleteImage){
            DLList<Country> temp = countries.getList(abr);
            for(int i = 0; i < temp.size(); i++){
                if(temp.get(i).getAbr().equals(abr)){
                    country = temp.get(i).getName();
                }
            }
            images = myTable.get(new Country(abr, country));
            if(images.size() == 1){
                hasImage = false;
            }   
            int tempCurrentImage = currentImage;
            currentImage = 0;
            images.remove(tempCurrentImage);
            if(images.size() == 0){
                myTable.remove(new Country(abr, country));
                hasImage = false;
            }
            if(hasImage)
            landmark = images.get(currentImage).getLandmark();

            writeToFile();
        }

        repaint();
    }


}
