package com.g7.wanbao.local;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeSet;

import android.app.Activity;

public class Data {
    /** Called when the activity is first created. */

	private static Data instance = null;
    private Activity rootActivity;
    public static final int MAX_ITEM_ID = 353;
    public static final int MIN_ITEM_ID = 1;

    public static Data getInstance(Activity _rootActivity) {
    	if(instance == null) {
    		instance = new Data(_rootActivity);
    		instance.readEverythingFromAssets();
    		instance.readCompIntroFromAssets();
    	}
    	return instance;
    }
    
    public Data(Activity _rootActivity) {
        rootActivity = _rootActivity;

        for (int i = 0; i < 512; i++) {
            itemList.add(new Item());
        }

        for (int i = 0; i < 128; i++) {
            compList.add(new Comp());
        }

        for (int i = 0; i < 8; i++) {
            categoryList.add(new Category());
        }
    }


    class Item {
        int itemID;
        int compID;
        ArrayList<String> intro;
        String ps;
        String imgUrl;
        String itemName;
        int category;

        Item() {
            intro = new ArrayList<String>();
        }
    }

    class Comp {
        int compID;
        ArrayList<String> intro;
        String compName;
        TreeSet<Integer> itemIDList;
        Comp() {
            intro = new ArrayList<String>();
            itemIDList = new TreeSet<Integer>();
        }
    }

    class Category {
        int category;
        TreeSet<Integer> itemIDList;

        Category() {
            itemIDList = new TreeSet<Integer>();
        }
    }


    public static ArrayList<Item> itemList = new ArrayList<Item>(512);
    public static ArrayList<Comp> compList = new ArrayList<Comp>(128);
    public static ArrayList<Category> categoryList = new ArrayList<Category>(8);

    public int getCompIDfromItemID(int itemID) {
        return itemList.get(itemID).compID;
    }

    public String getItemIntrofromItemID(int itemID) {
        String fs = "";
        for (String s : itemList.get(itemID).intro) {
            fs += (s + "\n");
        }
        return fs;
    }

    public String getPsfromItemID(int itemID) {
        return itemList.get(itemID).ps;
    }

    public String getImgUrlfromItemID(int itemID) {
        return itemList.get(itemID).imgUrl;
    }

    public String getItemNamefromItemID(int itemID) {
        return itemList.get(itemID).itemName;
    }

    public int getCategoryfromItemID(int itemID) {
        return itemList.get(itemID).category;
    }

    public TreeSet<Integer> getItemIDListfromCompID(int compID) {
        return compList.get(compID).itemIDList;
    }

    public String getCompIntrofromCompID(int compID) {
        String fs = "";
        for (String s : compList.get(compID).intro) {
            fs += s + "\n";
        }
        return fs;
    }

    public String getCompNamefromCompID(int compID) {
        return compList.get(compID).compName;
    }

    public TreeSet<Integer> getItemIDListfromCategory(int category) {
        return categoryList.get(category).itemIDList;
    }

    BufferedReader reader = null;

    public void readEverythingFromAssets(){
        try {
            reader = new BufferedReader(
                    new InputStreamReader(rootActivity.getAssets().open("Awked.txt")));

            // do reading, usually loop until end of file reading
            String line;
            while ((line = reader.readLine()) != null) {
                //process line

                String[] tokens = line.split("#");

                int itemID = Integer.parseInt(tokens[0]);
                int compID = Integer.parseInt(tokens[1]);
                int category = Integer.parseInt(tokens[2]);
                String imgUrl = tokens[3];
                String itemName = tokens[4];
                String compName = tokens[5];
                String[] itemIntro = tokens[6].split("\t");
                String ps = tokens[7];

                itemList.get(itemID).itemID = itemID;
                itemList.get(itemID).compID = compID;
                for (String s : itemIntro) {
                    itemList.get(itemID).intro.add(s);
                }
                itemList.get(itemID).ps = ps;
                itemList.get(itemID).imgUrl = imgUrl;
                itemList.get(itemID).itemName = itemName;
                itemList.get(itemID).category = category;

                compList.get(compID).compID = compID;
                compList.get(compID).compName = compName;
                compList.get(compID).itemIDList.add(itemID);

                categoryList.get(category).category = category;
                categoryList.get(category).itemIDList.add(itemID);

            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

    public void readItemlistFromAssets(){
        try {
            reader = new BufferedReader(
                    new InputStreamReader(rootActivity.getAssets().open("Itemlist.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line


                String[] tokens = mLine.split(" ");

                int compID = Integer.parseInt(tokens[0]);
                String compName = tokens[1];
                int itemID = Integer.parseInt(tokens[2]);
                String itemName = tokens[3];
                int category = Integer.parseInt(tokens[4]);
                String ps = tokens[5];

                itemList.get(itemID).itemID = itemID;
                itemList.get(itemID).compID = compID;
                itemList.get(itemID).ps = ps;
                itemList.get(itemID).itemName = itemName;
                itemList.get(itemID).category = category;

                compList.get(compID).compID = compID;
                compList.get(compID).compName = compName;
                compList.get(compID).itemIDList.add(itemID);

                categoryList.get(category).category = category;
                categoryList.get(category).itemIDList.add(itemID);

            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

    public void readCompIntroFromAssets(){
        try {
            reader = new BufferedReader(
                    new InputStreamReader(rootActivity.getAssets().open("CompIntro.txt")));

            // do reading, usually loop until end of file reading
            String line;
//            int id = 0;
            while ((line = reader.readLine()) != null) {
                //process line

                String[] tokens = line.split("@");
                int compID = Integer.parseInt(tokens[0]);
                String[] intros = tokens[1].split("#");
                for (String s : intros) {
                    if ( ! compList.get(compID).intro.contains(s) ) {
                        compList.get(compID).intro.add(s);
                    }
                }

//                if (line.equals("")) {
//                    continue;
//                }
//
//                if (line.contains("___")) {
//                    // keepReading = false;
//                    continue;
//                }
//
//                boolean isDigit = true;
//                for (int i = 0; i < line.length(); i++) {
//                    if (line.charAt(i) < '0' || line.charAt(i) > '9') {
//                        isDigit = false;
//                        break;
//                    }
//                }
//                if (isDigit) {
//                    // keepReading = true;
//                    id = Integer.parseInt(line);
//                    continue;
//                }
//                compList.get(id).intro.add(line);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

    public void readItemIntroFromAssets(){
        try {
            reader = new BufferedReader(
                    new InputStreamReader(rootActivity.getAssets().open("ItemIntro.txt")));

            // do reading, usually loop until end of file reading
            int id = 0;
            String line = "";
            boolean readUrl = false;
            while ((line = reader.readLine()) != null) {
                //process line
                if (line.equals("")) {
                    continue;
                }
                if (readUrl) {
                    itemList.get(id).imgUrl = line;
                    readUrl = false;
                    continue;
                }
                if (line.contains("___")) {
                    // keepReading = false;
                    continue;
                }

                if (line.contains("__")) {
                    readUrl = true;
                    continue;
                }


                boolean isDigit = true;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) < '0' || line.charAt(i) > '9') {
                        isDigit = false;
                        break;
                    }
                }
                if (isDigit) {
                    // keepReading = true;
                    id = Integer.parseInt(line);
                    continue;
                }

                itemList.get(id).intro.add(line);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

}