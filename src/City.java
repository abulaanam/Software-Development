import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Set;
import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

/* Class representing a City
 */
public class City implements Comparable<City> {
  /* lookup table of all cities by name */
  public static HashMap<String, City> cities = new HashMap<String, City>();

  public String name;
  /* adjacent Links */
  public final HashSet<Link> links = new HashSet<Link>();
  /* shortest path distance */
  protected int distance;
  /* shortest path parent */
  protected Link parent;

  /* construct a City with name nm
   * add to the HashMap of cities
   */
  public City(String nm) {
    name = nm;
    cities.put(name, this);
  }

  /* find a city with name nm in cities
   * return the city if it exists
   * otherwise return a new city with that name
   */
  public static City find(String nm) {
    City p = cities.get(nm);
    if (p == null) {
      p = new City(nm);
      return p;
    }
    return p;
  }

  /* add a link to links
   */
  public void addLink(Link lnk) {
    links.add(lnk);
  }

  /* compare cities by their names
   * return: negative if c1 is alphanumerically less,
   *  0 if names are the same,
   *  positive if c2 is alphanumerically less
   */
  public int compareTo(City p) {
    return name.compareTo(p.name);
  }

  /* return the name of the city
   */
  public String toString() {
    return name;
  }

  /* compare cities by their distance from the start of the rail network
   * return: negative if c1 is closer to 0, 0 if equal distance, positive if c2 is closer to 0
   */
  public int compare(City c1, City c2) {
    return c1.distance - c2.distance;
  }

  /* find a path from this to dest of used links
   * return true if a path of used links is found and false otherwise
   * add the followed Links to routeLinks
   */
  public boolean getLinksTo(City dest, Set<Link> routeLinks) {
    for (Link l : links) {
      if (l.isUsed() && (l != parent)) {
        City child = l.getAdj(this);
        if ((dest == child) || child.getLinksTo(dest, routeLinks)) {
          routeLinks.add(l);
          return true;
        }
      }
    }
    return false;
  }

  /* create a shortest path tree starting from this City
   * uses Dijkstra's shortest paths algorithm
   * set the distance of this City to 0 and others to infinity
   * consider each found City closest to the start
   *   update the best known distance to all adjacent cities
   * postcondition: every City.distance is the shortest distance from this to that City
   * postcondition: every City.parent is the Link before that City in the set of shortest paths
   */

  /**
   * This method can populate the priority queue with all the cities.
   * @return pq
   */
  public PriorityQueue<City> populatePriorityQueue() {
    Comparator<City> comparator = new CityComparator();
    PriorityQueue<City> pq = new PriorityQueue<>(comparator);
    for (City c : cities.values()) {
      pq.add(c);
    }
    return pq;
  }

  /**
   * This method can initialize the cities by setting the distance of all cities except the
   * current city to Integer.MAX_VALUE and setting the distance of the current city to 0.
   * It can also initialize the parent of each city as null and set the 'used' flag of each link as false.
   */
  public void initializeCities() {
    for (City c : cities.values()) {
      if (c != this) {
        c.distance = Integer.MAX_VALUE;
      } else {
        c.distance = 0;
      }
      c.parent = null;
      for (Link l : c.links) {
        l.setUsed(false);
      }
    }
  }

  /**
   * This method can build the Minimum Spanning Tree by repeatedly
   * visiting the next closest city and updating the distances of its adjacent cities
   * until all cities have been visited.
   * @return tree
   */
  public HashSet<City> makeTree() {
    initializeCities();
    PriorityQueue<City> pq = populatePriorityQueue();
    HashSet<City> tree = new HashSet<>();
    while (!pq.isEmpty()) {
      City city = findNextClosestCity(pq);
      if (city.parent != null) {
        city.parent.setUsed(true);
      }
      tree.add(city);
      updateDistances(city, tree, pq);
    }
    return tree;
  }

  /**
   * This method can find the next closest city by polling the priority queue.
   */
  public City findNextClosestCity(PriorityQueue<City> pq) {
    return pq.poll();
  }

  /**
   * This method can update the distances of adjacent cities in the priority queue.
   */
  public void updateDistances(City city, HashSet<City> tree, PriorityQueue<City> pq) {
    for (Link l : city.links) {
      City child = l.getAdj(city);
      if (!tree.contains(child)) {
        int length = l.getLength();
        if (child.distance > city.distance + length) {
          pq.remove(child);
          child.distance = city.distance + length;
          child.parent = l;
          pq.add(child);
        }
      }
    }
  }
}
