package tuan8;

// Java program for estimation of Pi using Monte
//Carlo Simulation
import java.util.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class GFG implements Runnable
{
    // Defines precision for x and y values. More the
    // interval, more the number of significant digits
    static int INTERVAL = 10000;

    // Driver code
    public static void main(String[] args)throws IOException
    {
        double rand_x, rand_y, origin_dist, pi=0;
        int circle_points = 0, square_points = 0;

        // Total Random numbers generated = possible x
        // values * possible y values
        for (int i = 0; i < (INTERVAL * INTERVAL); i++) {

            // Randomly generated x and y values in the range [-1,1]
            rand_x = Math.random()*2-1;
            rand_y = Math.random()*2-1;

            // Distance between (x, y) from the origin
            origin_dist = rand_x * rand_x + rand_y * rand_y;

            // Checking if (x, y) lies inside the define
            // circle with R=1
            if (origin_dist <= 1)
                circle_points++;

            // Total number of points generated
            square_points++;

            // estimated pi after this iteration
            pi = ((4.0 * circle_points) / square_points);

            // For visual understanding (Optional)
            //System.out.println(rand_x+" "+rand_y+" "+circle_points+" "+square_points+" - "+pi);
        }

        // Final Estimated Value
        System.out.println("Final Estimation of Pi = " + pi);
    }

    @Override
    public void run() {
        try {
            double rand_x, rand_y, origin_dist, pi=0;
            int circle_points = 0, square_points = 0;

            // Total Random numbers generated = possible x
            // values * possible y values
            for (int i = 0; i < (INTERVAL * INTERVAL); i++) {

                // Randomly generated x and y values in the range [-1,1]
                rand_x = Math.random()*2-1;
                rand_y = Math.random()*2-1;

                // Distance between (x, y) from the origin
                origin_dist = rand_x * rand_x + rand_y * rand_y;

                // Checking if (x, y) lies inside the define
                // circle with R=1
                if (origin_dist <= 1)
                    circle_points++;

                // Total number of points generated
                square_points++;

                // estimated pi after this iteration
                pi = ((4.0 * circle_points) / square_points);

                // For visual understanding (Optional)
                //System.out.println(rand_x+" "+rand_y+" "+circle_points+" "+square_points+" - "+pi);
            }

            // Final Estimated Value
            System.out.println(Thread.currentThread().getName()+" Final Estimation of Pi = " + pi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// This code is contributed by shruti456rawal

