import java.util.*;

// Author: Abul Aanam
// This program reads input from the user and creates a rail network.
// It then calculates the cost of a given route by adding up the lengths of the links in the route.
// The program checks for invalid inputs and prints error messages when necessary.
// The program uses the City and Link classes to represent cities and links between them.
// It uses a TreeSet to store the links in the network, which is sorted automatically by the Link class.


public class RouteCost {

  // The main method is responsible for reading user inputs and
  // creating links between cities, then finding the route cost.
  public static void main(String [] args) {

    Scanner inp = new Scanner(System.in);
    TreeSet<Link> links = new TreeSet<Link>();
    boolean invalid = false;

    // Loop through the input lines to create links between cities
    for (String name = inp.nextLine(); !name.equals("done"); name = inp.nextLine()) {
      invalid = createLink(name,invalid);
      if(invalid){
        return;
      }
    }

    // Find the route cost for each route entered by the user

    for (String name = inp.nextLine(); !name.equals("done"); name = inp.nextLine()){
        invalid = calculateRouteCost(name,links);

        if(invalid){
          return;
        }
      }

    // Print out the links and the total cost of the routes
    print(links);

  }

  // Check if the direction input is valid
  private static boolean isValidDirection(Direction direction) {
    return direction == Direction.one || direction == Direction.two ;
  }

  // Print out the links and the total cost of the routes
  private static void print( TreeSet<Link> links){
    int total = 0;
    System.out.println("The rail network consists of:");
    for (Link l : links) {
      System.out.println("  " + l);
      total += l.getLength();
    }
    System.out.println("The total cost is: " + total);
  }

  private static void invalid(String name){
    System.out.println("Invalid line: "+ name);
  }

  private static  boolean  createLink(String name, boolean invalid){
    String[] words = name.split("\\s+");

    // Check if the input line is valid
    if(words.length<3 || words.length>4){
      invalid(name);
      invalid = true;
      return invalid;
    }

    String inp_1 = words[0];

    int inp_2 = 0;
    try {
      inp_2 = Integer.parseInt(words[1]);  // attempt to parse the string into an integer
      // the input is an integer
    } catch (NumberFormatException e) {
      // the input is not an integer
      invalid(name);
      invalid = true;
      return invalid;
    }

    String inp_3 = words[2];
    Direction inp_4 = null;

    if(words.length==4){
      inp_4 = Direction.valueOf(words[3]);
    }


    City c1 = City.find(inp_1);
    int length = inp_2;
    City c2 = City.find(inp_3);
    Direction direction = inp_4;

    // Create a new link between the two cities if valid
    if(isValidDirection(direction)){
      Link l = new Link(c1, c2, length, direction);
    }
    else if( direction == null){
      Link l = new Link(c1, c2, length);
    }
    else{
      invalid(name);
      invalid =  true;
    }

    return invalid;
  }

  private static boolean calculateRouteCost(String name, TreeSet<Link>  links){
      boolean invalid = false;
      String[] words = name.split("\\s+");

      if(words.length != 2){
        invalid(name);
        invalid = true;
        return invalid;
      }

      String inp_1 = words[0];
      String inp_2 = words[1];


      City c = City.find(inp_1);
      c.makeTree();

      // Get the links between the two cities and calculate the total cost
      if (!c.getLinksTo(City.find(inp_2), links)) {
        System.out.println("Error: Route not found! " + c);
        invalid = true;
        return invalid;
      }
      return invalid;

  }
}
