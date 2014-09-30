import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;


public class Topic05Demo {
   //Instantiating a List object in which "Generic Type Invocation" is performed.
   private static ArrayList<String> names = new ArrayList<String>();  
   final static String randomName[] = {"Amy","Kiky","Dao","Duen"};
   final static int level[] = {100,40,20,80};
   public static void main(String[] args){
       for(int i = 0; i < randomName.length; i++){
           names.add(randomName[i]);
       }
       System.out.println("***Unsorted***");
       for(int i = 0; i < randomName.length; i++){
           //Invoking some "positional access" methods.
           System.out.print(names.get(i)+"\t");    
       }
       Collections.sort(names);
       System.out.println("\n\n***Sorted***");
       for(int i = 0; i < randomName.length; i++){
           System.out.print(names.get(i)+"\t");
       }
       
       ListIterator li = names.listIterator();
       System.out.println("\n\n***Iterator Forward Reading***");
       while(li.hasNext()){
         System.out.print(li.next()+"\t");
       }
       System.out.println("\n\n***Iterator Backward Reading***");
       //Using ListIterator to traverse the list.
       while(li.hasPrevious()){
           System.out.print(li.previous()+"\t");
       }
       //Using some polymorphic method from the Collections class to manipulate the list.
       Collections.rotate(names, 1);
       System.out.println("\n\n***Polymorphic Manipulation***");
       for(int i = 0; i < randomName.length; i++){
           System.out.print(names.get(i)+"\t");
       }    
       Collections.sort(names);
       //Instantiating a Map object in which "Generic Type Invocation" is performed.
       Map<String, Integer> m1 = new HashMap<String, Integer>();
       for(int i = 0; i < level.length; i++){
           m1.put(names.get(i),level[i]);
       }
       System.out.println("\n\n***Basic Operations of the Map***");
       //Showing some basic operations of the map.
       for(int i = 0; i < m1.size(); i++){
           System.out.print(i+1+". "+names.get(i)+"\t\t");
           System.out.println(m1.get(names.get(i)));
       }
              
   }
}