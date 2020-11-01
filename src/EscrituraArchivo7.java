import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class EscrituraArchivo7 extends Thread{




        private PrintWriter writerObj1;
        private char[] set1 = "abcdefghijklmnñopqrstuvwxyz".toCharArray();
        private int k = 0;
        private int archivoActual = 0;

        public EscrituraArchivo7(int k, String path, int archivoActual)
        {
            this.k = k;
            this.archivoActual = archivoActual;
            try {
                writerObj1 = new PrintWriter(new File(path));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // The method that prints all
// possible strings of length k.
// It is mainly a wrapper over
// recursive function printAllKLengthRec()
        public void writeAllKLength(char[] set, int k)
        {
            int n = set.length;
            writeAllKLengthRec(set,""+set[archivoActual], n, k-1);
            writerObj1.flush();
            writerObj1.close();
        }

        // The main recursive method
        // to print all possible
        // strings of length k
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

            // One by one add all characters
            // from set and recursively
            // call for k equals to k-1


            for (int i = 0; i < n; ++i)
            {

                // Next character of input added
                String newPrefix = prefix + set[i];
                // writerObj1.write(newPrefix + '\n');
                // k is decreased, because
                // we have added a new character
                writeAllKLengthRec(set, newPrefix,
                        n, k - 1);
            }


        }



        public void run() {

            writeAllKLength(set1, k);
        }

        // Driver Code
        public  static void main(String[] args) throws FileNotFoundException {
            System.out.println("Empieza a escribir el archivo ");

            EscrituraArchivo7[] aray = new EscrituraArchivo7[27];
            for(int i = 1; i<27+1;i++)
            {
                String path = "C:\\Users\\Joshua\\IdeaProjects\\Caso2Infracomp\\case2Scrypt"+ "7" +"----"+ i + ".txt";

                EscrituraArchivo7 thread = new EscrituraArchivo7(7,path,i-1);
                aray[i-1] = thread;

            }
            for(int k = 0;k<27;k++) {
                aray[k].start();
            }

//    int k = 7;
//        long time1 = System.currentTimeMillis();
//        writeAllKLength(set1,k);
//        long time2 = System.currentTimeMillis();
//        System.out.println(time2-time1);

        }
    }



