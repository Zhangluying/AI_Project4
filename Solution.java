package p4;


public class Solution
{
    //there are 3 dices(D1 D2 D3)
    //each dice is a three-sided dice with labels 1, 2, and 3
    static int[] dices = new int[]{1,2,3};
    static int[] labels = new int[]{1,2,3};

    //Specify the alphabet, the states, the transition,
    //probabilities and the emission probabilities
    //using InputFile1.txt
    static int[] states = new int[]{1,2,3};
    static int[] observations = new int[]{1, 2, 2, 2, 1, 3, 3, 2, 1, 3,
    					  3, 3, 2, 1, 1, 3, 3, 1, 1, 2, 
    					  3, 1, 3, 1, 3, 1, 3, 1, 2, 3, 
    					  1, 3, 1, 1, 1, 3, 2, 1, 3, 1, 
    					  1, 3, 2, 2, 1, 2, 1, 1, 1, 1, 
    					  1, 1, 1, 3, 2, 2, 3, 3, 1, 1, 
    					  1, 1, 2, 1, 3, 3, 2, 2, 1, 1, 
    					  3, 3, 1, 1, 1, 1, 2, 3, 2, 1, 
    					  1, 1, 2, 1, 2, 1, 2, 2, 1, 3, 
    					  3, 3, 2, 2, 1, 2, 2, 2, 1, 2};
    static double[] start_probability = new double[]{0.33333333, 0.33333333, 0.33333333};
    
    
    static double[][] transititon_probability = new double[][]{
            {0.53904235, 0.230478825, 0.230478825},
            {0.230478825, 0.53904235, 0.230478825},
            {0.230478825, 0.230478825, 0.53904235},
    };
    static double[][] emission_probability = new double[][]{
            {0.5080058, 0.38622028, 0.105773926},
            {0.121888414, 0.45579398, 0.42231762},
            {0.57735425,0.13901345,0.28363228},
    };
    
    
   //use viterbi algorithm to compute 
   //a sequence of states which best explains the sequence of rolls
    public static int[] compute(int[] obs, int[] states, double[] start_p, double[][] trans_p, double[][] emit_p)
    {
        double[][] V = new double[obs.length][states.length];
        int[][] path = new int[states.length][obs.length];
 
        for (int y : states)
        {
            V[0][y-1] = start_p[y-1] * emit_p[y-1][obs[0] - 1];
            path[y-1][0] = y-1;
        }
 
        for (int t = 1; t < obs.length; ++t)
        {
            int[][] newpath = new int[states.length][obs.length];
 
            for (int y : states)
            {
                double prob = -1;
                int state;
                for (int y0 : states)
                {
                    double nprob = V[t - 1][y0 - 1] *trans_p[y0 - 1][y - 1] * emit_p[y - 1][obs[t] - 1];
                    if (nprob > prob)
                    {
                        prob = nprob;
                        state = y0-1;
                        // record the biggest probability
                        V[t][y-1] = prob;
                        // record the path
                        System.arraycopy(path[state], 0, newpath[y-1], 0, t);
                        newpath[y-1][t] = y-1;
                    }
                }
            }
 
            path = newpath;
        }
 
        double prob = -1;
        int state = 0;
        for (int y : states)
        {
            if (V[obs.length - 1][y-1] > prob)
            {
                prob = V[obs.length - 1][y-1];
                state = y-1;
            }
        }
        System.out.println("Probability:"+prob);
        return path[state];
    }
 
    public static void main(String[] args)
    {
        int[] result = compute(observations, states, start_probability, transititon_probability, emission_probability);
        for (int r : result)
        {
            System.out.print(dices[r] + " ");
        }
        System.out.println();
    }
}

