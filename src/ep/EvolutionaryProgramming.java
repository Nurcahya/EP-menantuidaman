/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep;

import java.util.*;
import java.io.*; 
import java.text.DecimalFormat; 

public class EvolutionaryProgramming {
  double [] fitness;
  char [][] pop;
  static Random rd = new Random();
  static final int 
    ROH = 110, 
    TAM = 111, 
    PEK = 112, 
    KET = 113,
    FSET_START = ROH, 
    FSET_END = KET;
  static double [] x = new double[FSET_START];
  static double minrandom, maxrandom;
  static char [] program;
  static int PC;
  static int varnumber, fitnesscases, randomnumber;
  static double fbestpop = 0.0, favgpop = 0.0;
  static long seed;
  static double avg_len; 
  public static int  
    MAX_LEN = 10000;
   static final int   
    POPSIZE = 100000,
    DEPTH   = 5,
    GENERATIONS = 100,
    TSIZE = 2;
  public static final double  
    PMUT_PER_NODE  = 0.05,
    CROSSOVER_PROB = 0.9;
  static double [][] targets;

  double run() {
    char primitive = program[PC++];
    if ( primitive < FSET_START )
      return(x[primitive]);
    switch ( primitive ) {
      case ROH : {
                double edge1 = run(), edge2 = run();
                if (edge1 <= edge2) return( edge1 );
                else return (edge2);
            }
      case TAM : {
                double edge1 = run(), edge2 = run();
                if (edge1 <= edge2) return( edge1 );
                else return (edge2);
            }
      case PEK : {
                double edge1 = run(), edge2 = run();
                if (edge1 <= edge2) return( edge1 );
                else return (edge2);
            }
      case KET : {
                double edge1 = run(), edge2 = run();
                if (edge1 <= edge2) return( edge1 );
                else return (edge2);
            }
      }
    return( 0.0 ); 
  }
          
  static int traverse( char [] buffer, int buffercount ) {
    if ( buffer[buffercount] < FSET_START )
      return( ++buffercount );
    
    switch(buffer[buffercount]) {
      case ROH: 
      case TAM: 
      case PEK: 
      case KET: 
      return( traverse( buffer, traverse( buffer, ++buffercount ) ) );
      }
    return( 0 ); 
  }

  void inisialisasi(String fname) {
    try {
      int i,j;
      String line;
      
      BufferedReader in = 
      new BufferedReader(
      		    new
      		    FileReader(fname));
      line = in.readLine();
      StringTokenizer tokens = new StringTokenizer(line);
      varnumber = Integer.parseInt(tokens.nextToken().trim());
      randomnumber = Integer.parseInt(tokens.nextToken().trim());
      minrandom =  Double.parseDouble(tokens.nextToken().trim());
      maxrandom =  Double.parseDouble(tokens.nextToken().trim());
      fitnesscases = Integer.parseInt(tokens.nextToken().trim());
      targets = new double[fitnesscases][varnumber+1];
      if (varnumber + randomnumber >= FSET_START ) 
        System.out.println("Terlalu banyak variabel");
      
      for (i = 0; i < fitnesscases; i ++ ) {
        line = in.readLine();
        tokens = new StringTokenizer(line);
        for (j = 0; j <= varnumber; j++) {
          targets[i][j] = Double.parseDouble(tokens.nextToken().trim());
      	}
      }
      in.close();
    }
   catch(FileNotFoundException e) {
      System.out.println("ERROR: Data tidak tersedia");
      System.exit(0);
    }
    catch(Exception e ) {
      System.out.println("ERROR: Format data salah");
      System.exit(0);
    }
  }

  double fitness_function( char [] Prog ) {
    int i = 0, len;
    double result, fit = 0.0;
    
    len = traverse( Prog, 0 );
    for (i = 0; i < fitnesscases; i ++ ) {
      for (int j = 0; j < varnumber; j ++ )
          x[j] = targets[i][j];
      program = Prog;
      PC = 0;
      result = run();
      fit += Math.abs( result - targets[i][varnumber]);
      }
    return(-fit );
  }

  int grow( char [] buffer, int pos, int max, int depth ) {
    char prim = (char) rd.nextInt(2);
    int one_child;

    if ( pos >= max ) 
      return( -1 );
    
    if ( pos == 0 )
      prim = 1;
    
    if ( prim == 0 || depth == 0 ) {
      prim = (char) rd.nextInt(varnumber + randomnumber);
      buffer[pos] = prim;
      return(pos+1);
      }
    else  {
      prim = (char) (rd.nextInt(FSET_END - FSET_START + 1) + FSET_START);
      switch(prim) {
      case ROH: 
      case TAM: 
      case PEK: 
      case KET:
        buffer[pos] = prim;
	one_child = grow( buffer, pos+1, max,depth-1);
	if ( one_child < 0 ) 
		return( -1 );
        return( grow( buffer, one_child, max,depth-1 ) );
      }
    }
    return( 0 );
  }
  
  int print_indiv( char []buffer, int buffercounter ) {
    int a1=0, a2;
    if ( buffer[buffercounter] < FSET_START ) {
     // if ( buffer[buffercounter] < varnumber )
       // System.out.print( "X"+ (buffer[buffercounter] + 1 )+ " ");
      //else
        System.out.print( x[buffer[buffercounter]]);
      return( ++buffercounter );
      }
    else if ( buffer[buffercounter] == FSET_START ) {
        System.out.print(" diterima ");
    }
    switch(buffer[buffercounter]) {
      case ROH: System.out.print( "(");
        a1=print_indiv( buffer, ++buffercounter ); 
        System.out.print( " Kerohanian "); 
        break;
      case TAM: System.out.print( "(");
        a1=print_indiv( buffer, ++buffercounter ); 
        System.out.print( " Ketampanan "); 
        break;
      case PEK: System.out.print( "(");
        a1=print_indiv( buffer, ++buffercounter ); 
        System.out.print( " Pekerjaan "); 
        break;
      case KET: System.out.print( "(");
        a1=print_indiv( buffer, ++buffercounter ); 
        System.out.print( " Keturunan "); 
        break;
      }
    a2=print_indiv( buffer, a1 ); 
    System.out.print( ")"); 
    return( a2);
  }
  

  static char [] buffer = new char[MAX_LEN];
  char [] create_random_indiv( int depth ) {
    char [] ind;
    int len;

    len = grow( buffer, 0, MAX_LEN, depth );

    while (len < 0 )
      len = grow( buffer, 0, MAX_LEN, depth );

    ind = new char[len];

    System.arraycopy(buffer, 0, ind, 0, len ); 
    return( ind );
  }

  char [][] create_random_pop(int n, int depth, double [] fitness ) {
    char [][]pop = new char[n][];
    int i;
    
    for ( i = 0; i < n; i ++ ) {
      pop[i] = create_random_indiv( depth );
      fitness[i] = fitness_function( pop[i] );
      }
    return( pop );
  }


  void stats( double [] fitness, char [][] pop, int gen ) {
    int i, best = rd.nextInt(POPSIZE);
    int node_count = 0;
    fbestpop = fitness[best];
    favgpop = 0.0;

    for ( i = 0; i < POPSIZE; i ++ ) {
      node_count +=  traverse( pop[i], 0 );
      favgpop += fitness[i];
      if ( fitness[i] > fbestpop ) {
      best = i;
      fbestpop = fitness[i];
      }
    }
    avg_len = (double) node_count / POPSIZE;
    favgpop /= POPSIZE;
    System.out.print("--------------------------------------------- \nGenerasi="+gen+"\nRata-rata Fitness="+(-favgpop)+
    		 "\nBest Fitness="+(-fbestpop)+" \nRata-rata ukuran="+avg_len+
    		 "\nBest Individu: ");
    print_indiv( pop[best], 0 );
    System.out.print( "\n");
    System.out.flush();
  }

  int tournament( double [] fitness, int tsize ) {
    int best = rd.nextInt(POPSIZE), i, competitor;
    double  fbest = -1.0e34;
    
    for ( i = 0; i < tsize; i ++ ) {
      competitor = rd.nextInt(POPSIZE);
      if ( fitness[competitor] > fbest ) {
        fbest = fitness[competitor];
        best = competitor;
      }
    }
    return( best );
  }
  
  int negative_tournament( double [] fitness, int tsize ) {
    int worst = rd.nextInt(POPSIZE), i, competitor;
    double fworst = 1e34;
    
    for ( i = 0; i < tsize; i ++ ) {
      competitor = rd.nextInt(POPSIZE);
      if ( fitness[competitor] < fworst ) {
    	fworst = fitness[competitor];
    	worst = competitor;
        }
    }
    return( worst );
  }
  
 
  void print_parms() {
   System.out.print("Parameter : \n");
   System.out.print("SEED="+seed+"\nMAX_LEN="+MAX_LEN+
   	    "\nPOPSIZE="+POPSIZE+"\nDEPTH="+DEPTH+
     	    "\nPMUT_PER_NODE="+PMUT_PER_NODE+
     	    "\nMIN_RANDOM="+minrandom+
     	    "\nMAX_RANDOM="+maxrandom+
     	    "\nGENERATIONS="+GENERATIONS+
     	    "\nTSIZE="+TSIZE+
     	    "\n----------------------------------\n");
  }

  public EvolutionaryProgramming( String fname, double mut, int pops, int dep) {
    fitness =  new double[POPSIZE];
    seed = 1;
    if ( seed >= 0 )
        rd.setSeed(seed);
    inisialisasi(fname);
    for ( int i = 0; i < FSET_START; i ++ )
      x[i]= (maxrandom-minrandom)*rd.nextDouble()+minrandom;    
    pop = create_random_pop(POPSIZE, DEPTH, fitness );
  }

  void evaluasi(int genr) {
    int gen = 0, indivs, offspring, parent1, parent2, parent;
    double newfit;
    char []newind;
    print_parms();
    stats( fitness, pop, 0 );
    for ( gen = 1; gen < genr; gen ++ ) {
      if (  fbestpop > -1e-5 ) {
      //System.out.print("PROBLEM SOLVED\n");
      System.exit( 0 );
      }
      for ( indivs = 0; indivs < POPSIZE; indivs ++ ) {
      parent = tournament( fitness, TSIZE );
      Mutasi mut = new Mutasi();
      newind = mut.mutasi(pop[parent], PMUT_PER_NODE );
      newfit = fitness_function( newind );
      offspring = negative_tournament( fitness, TSIZE );
      pop[offspring] = newind;
      fitness[offspring] = newfit;
      }
      stats( fitness, pop, gen );
    }
   // System.out.print("PROBLEM *NOT* SOLVED\n");
    //System.exit( 1 );
  }

//  public static void main(String[] args) {
//    String fname = "/data/problem.dat";
//    String folder = System.getProperty("user.dir");
//    folder = folder.replace("src", "/");
//    String lokasiFile = folder + fname;   
//    long s = -1;
//    
//    if ( args.length == 2 ) {
//      s = Integer.valueOf(args[0]).intValue();
//      lokasiFile = args[1];
//    }
//    if ( args.length == 1 ) {
//      lokasiFile = args[0];
//    }
//    
//    EvolutionaryProgramming gp = new EvolutionaryProgramming(lokasiFile, s);
//    gp.evolve();
//  }
};
