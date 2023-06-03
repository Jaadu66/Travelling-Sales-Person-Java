import java.util.Comparator;
import java.util.LinkedList;

public class BestFirstSearch {
    private LinkedList<double[]> graph = new LinkedList<>();          //double[]{city1, city2, path_Length}
    private LinkedList<Double> path = new LinkedList<>();
    private int[][] cities;

    private double bestLength = Double.MAX_VALUE;
    private  int[] bestPath;

    public BestFirstSearch(int[][] cities){
        this.cities = cities;
        initiateGraph();
    }

    //Returns the best path
    public int[] getBestPath(){
        //convert the internal indexes of the cities to the indexes from the source file
        int[] realIndexes = new int[bestPath.length];
        for (int citiesIndex = 0; citiesIndex<bestPath.length; citiesIndex++){
            realIndexes[citiesIndex] = cities[bestPath[citiesIndex]][2];
        }
        return realIndexes;
    }

    //Gets tje best length
    public double getBestLength(){
        return bestLength;
    }

    //Solve the Travelling salesman problem
    public void resolve(){
        //Add the first pair with the shortest distance between them to the result path.
        path.add(graph.getFirst()[0]);
        path.add(graph.getFirst()[1]);
        graph.removeFirst();          //Take the first pair out of the source list

        //Search for the next city in a loop until all cities are in the result path
        while(true){
            //find a city that is close to the first one
            double nearestFirst = findNearest(path.getFirst());
            if(nearestFirst == -1){
                //There are no cities left
                break;
            } else{
                path.addFirst(nearestFirst);            //Include the nearest city in the result path
            }

            //Find the closest city to the last one
            double nearestLast = findNearest(path.getLast());
            if (nearestLast == -1){
                //There are no cities left
                break;       //terminate loop
            } else{
                path.addLast(nearestLast);             //Include the nearest city in the result path
            }
        }

        //Add last city to create a loop
        path.addLast(path.getFirst());

        //Convert the list into Array
        int[] pathArray = new int[path.size()];
        for(int i = 0; i < pathArray.length; i++){
            pathArray[i] = path.get(i).intValue();
        }

        bestPath = pathArray;
        bestLength = calculateTotalPathLength();
    }

    //Calculates the distances between all pairs of cities and sort the list

    private void initiateGraph(){
        //Fill in the graph
        for(int firstcityIndex = 0; firstcityIndex < cities.length - 1; firstcityIndex++){
            for (int secondCityIndex = firstcityIndex + 1; secondCityIndex < cities.length; secondCityIndex++){
                double[] path = new double[3];
                path[0] = firstcityIndex;
                path[1] = secondCityIndex;
                path[2] = distance(cities[firstcityIndex], cities[secondCityIndex]);

                graph.add(path);
            }
        }

        //Sort the List
        graph.sort(new Comparator<double[]>() {
            @Override
            public int compare(double[] o1, double[] o2) {
                return (int)(o1[2]*1000000) - (int)(o2[2]*1000000);
            }
        });
    }

    //Calculate distance between the two points
    private double distance(int[] point1, int[] point2){
        return Math.pow(Math.pow(point2[0] - point1[0], 2) + Math.pow(point2[1] - point1[1], 2), 1.0/2);
    }

    //Find a city that is closest to the particular city and is not yet on the result path
    private double findNearest(double city){
        for(int i = 0; i < graph.size(); i++){
            if (graph.get(i)[0] == city || graph.get(i)[1] == city) {
                //If one of the pair cities is the same as the given one

                double nextCity = (graph.get(i)[0] == city)? graph.get(i)[1]:graph.get(i)[0];
                if(!path.contains(nextCity)){
                    //If the new city is not in the result path yet
                    return nextCity;
                }

            }
        }
        return -1; //No city was found
    }

    //Calculate the total length of the path
    private double calculateTotalPathLength(){
        double total = 0;

        for(int i = 0; i < path.size() - 1; i++){
            total += distance(cities[path.get(i).intValue()], cities[path.get(i+1).intValue()]);
        }
        return total;
    }














}






















