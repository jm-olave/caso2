// Java program to print all
// possible strings of length k

import java.io.*;

class EscrituraArchivo {


  private  PrintWriter  writerObj1;
    private  static char[] set1 = "abcdefghijklmnñopqrstuvwxyz".toCharArray();
    private int k = 0;







    public EscrituraArchivo(String path)
    {

        try {
            writerObj1 = new PrintWriter(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public  void writeAllKLength(char[] set, int k)
    {
        int n = set.length;


        for(int i = 1; i<7;i++)
        {
            writeAllKLengthRec(set, "", n, i);

        }

        writerObj1.flush();
        writerObj1.close();

    }


    public void writeAllKLengthRec(char[] set,
                                   String prefix,
                                   int n, int k)
    {

        // Base case: k is 0,
        // print prefix
        if (k == 0)
        {
            writerObj1.write(prefix + '\n');

            return;
        }


        for (int i = 0; i < n; ++i)
        {


            String newPrefix = prefix + set[i];

            writeAllKLengthRec(set, newPrefix,
                    n, k - 1);
        }


    }






    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Empieza a escribir el archivo ");

        String path = "C:\\Users\\Joshua\\IdeaProjects\\Caso2Infracomp\\case2Scrypt"+"all"+".txt";
        EscrituraArchivo writer = new EscrituraArchivo(path);
       writer.writeAllKLength(set1,1);


//    int k = 7;
//        long time1 = System.currentTimeMillis();
//        writeAllKLength(set1,k);
//        long time2 = System.currentTimeMillis();
//        System.out.println(time2-time1);

    }
}

