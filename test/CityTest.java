import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/* Test cases
   1. find() black-box
   1a. find() returns a City that exists when it is the only City
   1b. find() returns a City that exists when it is the second City created
   1c. find() returns a new City when the City does not exist and there were no previous cities
   1d. find() returns a new City when the City does not exist and there were previous cities
   2. addLink() black-box
   2a. addLink adds a link when links is empty
   2b. addLink adds a link when links is not empty
   3. compareTo() black-box
   3a. compareTo returns negative if this.name is alphanumerically less
   3b. compareTo returns 0 if this.name.equals(p.name)
   3c. compareTo returns positive if this.name is alphanumerically greater
   4. toString () white-box returns the name of a city
   5. compare() black-box
   5a. compare returns negative if 0 <= c1.distance <= c2.distance
   5b. compare returns 0 if 0 <= c1.distance == c2.distance
   5c. compare returns positive if 0 <= c2.distance <= c1.distance
   6. getLinkTo black-box
   6a. getLinkTo returns true when a path from this to dest exists, and
         the followed links have been added to routeLinks
   6b. getLinkTo returns false when a path from this to dest does not exist.
*/

class CityTest {

  public static final String city1Name = "City1";
  public static final String city2Name = "City2";
  public static final String city3Name = "City3";
  public static final int cityDistanceShort = 1;
  public static final int cityDistanceLong = 2;

  /* clear the cities HashMap after every test */
  @AfterEach
  public void clearCities() {
    City.cities.clear();
  }

  /* 1. find() black-box */
  /* 1a. find() returns a City that exists when it is the only City */
  @Test
  void find_existsOne() {
    City city1 = new City(city1Name);
    City result = City.find(city1Name);
    assertSame(city1, result, "find did not return the same City");
  }

  /* 1b. find() returns a City that exists when it is the second City created */
  @Test
  void find_existsTwo() {
    City city1 = new City(city1Name);
    City city2 = new City(city2Name);
    City result = City.find(city2Name);
    assertSame(city2, result, "find did not return the same City");
  }

  /* 1c. find() returns a new City when the City does not exist and there were no previous cities */
  @Test
  void find_notExistsOne() {
    int numCities = City.cities.size();
    City result = City.find(city1Name);
    assertTrue(city1Name.equals(result.name), "find returned a City with the wrong name");
    assertEquals(numCities+1, City.cities.size(), "City.cities did not increase in size");
  }

  /* 1d. find() returns a new City when the City does not exist and there were previous cities */
  @Test
  void find_notExistsTwo() {
    City city1 = new City(city1Name);
    int numCities = City.cities.size();
    City result = City.find(city2Name);
    assertTrue(city2Name.equals(result.name), "find returned a City with the wrong name");
    assertNotSame(city1, result, "find did not return a new City");
    assertEquals(numCities+1, City.cities.size(), "City.cities did not increase in size");
  }

  /* 2. addLink() black-box */
  /* 2a. addLink adds a link when links is empty */
  @Test
  void addLink_empty() {
    City city1 = new City(city1Name);
    int numLinks = city1.links.size();
    City city2 = new City(city2Name);

    /* note: creating the link calls addLink() */
    Link link = new Link(city1, city2, cityDistanceShort);
    assertEquals(numLinks+1, city1.links.size(), "addLink did not increase length of links");
    assertTrue(city1.links.contains(link), "addLink did not add the link to links");
  }

  /* 2b. addLink adds a link when links is not empty */
  @Test
  void addLink_notEmpty() {
    City city1 = new City(city1Name);
    City city2 = new City(city2Name);
    City city3 = new City(city3Name);
    Link link = new Link(city1, city2, cityDistanceShort);
    int numLinks = city1.links.size();

    /* note: creating the link calls addLink() */
    Link link2 = new Link(city1, city3, cityDistanceLong);

    assertEquals(numLinks+1, city1.links.size(), "addLink did not increase length of links");
    assertTrue(city1.links.contains(link2), "addLink did not add the link to links");
  }

  /* 3. compareTo() black-box */
  /* 3a. compareTo returns negative if this.name is alphanumerically less */
  @Test
  void compareTo_xSmaller() {
    City city1 = new City(city1Name);
    City city2 = new City(city2Name);
    assertTrue(city1.compareTo(city2) < 0, "compareTo was not negative");
    City.cities.clear();
  }

  /* 3b. compareTo returns 0 if this.name.equals(p.name) */
  @Test
  void compareTo_xEqual() {
    City city1 = new City(city1Name);
    City city2 = new City(city1Name);
    assertTrue(city1.compareTo(city2) == 0, "compareTo was not zero");
  }

  /* 3c. compareTo returns positive if this.name is alphanumerically greater */
  @Test
  void compareTo_xLarger() {
    City city1 = new City(city2Name);
    City city2 = new City(city1Name);
    assertTrue(city1.compareTo(city2) > 0, "compareTo was not positive");
  }

  /* 4. toString () white-box returns the name of a city */
  @Test
  void testToString() {
    City city1 = new City(city1Name);
    assertTrue(city1Name.equals(city1.toString()), "toString did not return City name");
  }

  /* 5. compare() black-box */
  /* 5a. compare returns negative if 0 <= c1.distance <= c2.distance */
  @Test
  void compare_xSmaller() {
    City city1 = new City(city1Name);
    City city2 = new City(city2Name);
    city1.distance = cityDistanceShort;
    city2.distance = cityDistanceLong;
    assertTrue(city1.compare(city1, city2) < 0, "compare was not negative");
  }

  /* 5b. compare returns 0 if 0 <= c1.distance == c2.distance */
  @Test
  void compare_xEqual() {
    City city1 = new City(city1Name);
    City city2 = new City(city2Name);
    city1.distance = cityDistanceShort;
    city2.distance = cityDistanceShort;
    assertTrue(city1.compare(city1, city2) == 0, "compare was not zero");
  }

  /* 5c. compare returns positive if 0 <= c2.distance <= c1.distance */
  @Test
  void compare_xLarger() {
    City city1 = new City(city1Name);
    City city2 = new City(city2Name);
    city1.distance = cityDistanceLong;
    city2.distance = cityDistanceShort;
    assertTrue(city1.compare(city1, city2) > 0, "compare was not positive");
  }

  /* 6. getLinkTo black-box */
  /* 6a. getLinkTo returns true when a path from this to dest exists, and
             the followed links have been added to routeLinks */
  @Test
  void getLinksTo_exists() {
    City city1 = new City(city1Name);
    City city2 = new City(city2Name);
    Link link = new Link(city1, city2, cityDistanceShort);
    link.setUsed(true);
    Set<Link> routeLinks = new HashSet<Link>();
    assertTrue(city1.getLinksTo(city2, routeLinks), "getLinkTo returned false when there is a path");
    assertTrue(routeLinks.contains(link), "getLinkTo did not add link to routeLinks");
  }

  /* 6b. getLinkTo returns false when a path from this to dest does not exist. */
  @Test
  void getLinksTo_notExists() {
    City city1 = new City(city1Name);
    City city2 = new City(city2Name);
    Set<Link> routeLinks = new HashSet<Link>();
    assertFalse(city1.getLinksTo(city2, routeLinks), "getLinkTo returned true when there is no path");
  }

  /*
  testInitializeCities() tests if the cities are being initialised correctly.
   */
  @Test
  public void testInitializeCities() {
    City cityA = new City("A");
    City cityB = new City("B");
    Link linkAB = new Link(cityA, cityB, 10);
    cityA.addLink(linkAB);
    cityB.addLink(linkAB);

    cityA.initializeCities();

    assertEquals(Integer.MAX_VALUE, cityB.distance);
    assertEquals(0, cityA.distance);
    assertNull(cityA.parent);
    assertNull(cityB.parent);
    assertFalse(linkAB.isUsed());
  }

  /*
  testPopulatePriorityQueue() checks if the priority queue is being populated correctly
   */
  @Test
  public void testPopulatePriorityQueue() {
    // Create some cities
    City city1 = new City("A");
    City city2 = new City("B");
    City city3 = new City("C");
    City city4 = new City("D");

    // Populate the priority queue
    PriorityQueue<City> pq = city1.populatePriorityQueue();

    // Check that the priority queue contains all the cities
    Set<City> expected = new HashSet<>();
    expected.add(city1);
    expected.add(city2);
    expected.add(city3);
    expected.add(city4);
    assert expected.equals(new HashSet<>(pq));
  }

  /*
  testMakeTree() checks if the tree is being made correctly
   */
  @Test
  public void testMakeTree() {
    City a = new City("A");
    City b = new City("B");
    City c = new City("C");
    City d = new City("D");
    City e = new City("E");

    // Create links between the cities
    Link ab = new Link(a, b, 2);
    Link ac = new Link(a, c, 3);
    Link bd = new Link(b, d, 1);
    Link cd = new Link(c, d, 1);
    Link ce = new Link(c, e, 4);
    Link de = new Link(d, e, 3);

    // Add the links to the cities
    a.addLink(ab);
    a.addLink(ac);
    b.addLink(ab);
    b.addLink(bd);
    c.addLink(ac);
    c.addLink(cd);
    c.addLink(ce);
    d.addLink(bd);
    d.addLink(cd);
    d.addLink(de);
    e.addLink(ce);
    e.addLink(de);

    // Call the makeTree method
    HashSet<City> tree = a.makeTree();

    // Check that the tree has the correct size
    assertEquals(5, tree.size());

    // Check that the links in the tree connect the correct cities
    for (City city : tree) {
      if (city == a) {
        assertNull(city.parent);
      } else if (city == b) {
        assertEquals(a, city.parent.getAdj(b));
      } else if (city == c) {
        assertEquals(a, city.parent.getAdj(c));
      } else if (city == d) {
        assertEquals(b, city.parent.getAdj(d));
      } else if (city == e) {
        assertEquals(d, city.parent.getAdj(e));
      }
    }

  }

  /**
   * Test to check if polling is being done correctly
   */

  @Test
  public void testFindNextClosestCity() {

    City city1 = new City("city1");

    city1.distance = 5;

    PriorityQueue<City> pq = new PriorityQueue<>();
    pq.add(city1);

    City nextCity = city1.findNextClosestCity(pq);

    assertEquals(city1, nextCity);
  }

  /*
  testUpdateDistances() checks  if the distances are being updated correctly according to the input
   */

  @Test
  public void testUpdateDistances() {
    City city1 = new City("City 1");
    City city2 = new City("City 2");
    City city3 = new City("City 3");
    City city4 = new City("City 4");

    Link link1 = new Link(city1, city2, 10);
    Link link2 = new Link(city1, city3, 5);
    Link link3 = new Link(city2, city4, 15);
    Link link4 = new Link(city3, city4, 20);

    city1.addLink(link1);
    city1.addLink(link2);
    city2.addLink(link3);
    city3.addLink(link4);

    PriorityQueue<City> pq = new PriorityQueue<City>(new CityComparator());
    HashSet<City> tree = new HashSet<City>();

    // Initialize distances
    city1.distance = 0;
    city2.distance = Integer.MAX_VALUE;
    city3.distance = Integer.MAX_VALUE;
    city4.distance = Integer.MAX_VALUE;

    pq.add(city1);
    pq.add(city2);
    pq.add(city3);
    pq.add(city4);

    // Test updating distances
    City nextClosestCity = city1;
    nextClosestCity.updateDistances(nextClosestCity, tree, pq);
    assertEquals(0, city1.distance);
    assertEquals(10, city2.distance);
    assertEquals(5, city3.distance);
    assertEquals(Integer.MAX_VALUE, city4.distance);

    nextClosestCity = city2;
    nextClosestCity.updateDistances(nextClosestCity, tree, pq);
    assertEquals(0, city1.distance);
    assertEquals(10, city2.distance);
    assertEquals(5, city3.distance);
    assertEquals(25, city4.distance);

    nextClosestCity = city3;
    nextClosestCity.updateDistances(nextClosestCity, tree, pq);
    assertEquals(0, city1.distance);
    assertEquals(10, city2.distance);
    assertEquals(5, city3.distance);
    assertEquals(25, city4.distance);

    nextClosestCity = city4;
    nextClosestCity.updateDistances(nextClosestCity, tree, pq);
    assertEquals(0, city1.distance);
    assertEquals(10, city2.distance);
    assertEquals(5, city3.distance);
    assertEquals(25, city4.distance);
  }
}
