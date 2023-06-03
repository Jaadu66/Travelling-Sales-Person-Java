import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Enter the file path for the test file in the following string
		String testFile = ""; // Enter filepath

        // Solve the text file by best first search algorithm
        solveTextFile(testFile);

    }

	public static void solveTextFile(String fileName) {
		long startTime = System.nanoTime();
		File citiesFile = new File(fileName);
		int[][] cities = readFile(citiesFile);

		int[] bestPath;

			// Initializing best first search algorithm
			BestFirstSearch bfs = new BestFirstSearch(cities);
			bfs.resolve();
			bestPath = bfs.getBestPath();

		System.out.println("Test file: "+citiesFile.getName());
		System.out.println("Path: "+Arrays.toString(bestPath));
		System.out.println("Time to solve: "+(System.nanoTime() - startTime) + " Nano Seconds");
	}

    //Reads the input File
    public static int[][] readFile(File dataFile) {
        ArrayList<int[]> content = new ArrayList<>();
        Scanner input = null;
        try {
            input = new Scanner(dataFile);
        }
        //Check to see if the file is found
        catch (FileNotFoundException ex) {
            System.out.println("Problem with reading a file.");
            System.exit(0);
        }

        // reading the file
        while (input.hasNextLine() && input.hasNextInt()) {
            int[] contentLine = new int[3];
            Scanner line = new Scanner(input.nextLine());
            try {
                contentLine[2] = line.nextInt();
                contentLine[0] = line.nextInt();
                contentLine[1] = line.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: The file is not in the proper format.");
                System.exit(1);
            }
            content.add(contentLine);
            line.close();
        }
        input.close(); // close the file

        // Convert the list into array
        int[][] resultArray = new int[content.size()][3];
        for(int contentLine = 0; contentLine < content.size(); contentLine++) {
            resultArray[contentLine] = content.get(contentLine);
        }
        return resultArray;
    }}