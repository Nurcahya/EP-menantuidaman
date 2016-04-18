/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep;

import static ep.EvolutionaryProgramming.rd;
import static ep.EvolutionaryProgramming.ROH;
import static ep.EvolutionaryProgramming.TAM;
import static ep.EvolutionaryProgramming.PEK;
import static ep.EvolutionaryProgramming.KET;
import static ep.EvolutionaryProgramming.FSET_START;
import static ep.EvolutionaryProgramming.FSET_END;
import static ep.EvolutionaryProgramming.varnumber;
import static ep.EvolutionaryProgramming.randomnumber;
import ep.EvolutionaryProgramming.*;
/**
 *
 * @author Nurcahya
 */
public class Mutasi {
 char [] mutasi( char [] parent, double pmut ) {
    int len = EvolutionaryProgramming.traverse( parent, 0 ), i;
    int mutsite;
    char [] parentcopy = new char [len];
    
    System.arraycopy( parent, 0, parentcopy, 0, len );
    for (i = 0; i < len; i ++ ) {  
      if ( rd.nextDouble() < pmut ) {
      mutsite =  i;
      if ( parentcopy[mutsite] < FSET_START )
        parentcopy[mutsite] = (char) rd.nextInt(varnumber+randomnumber);
      else
        switch(parentcopy[mutsite]) {
      	case ROH: 
      	case TAM: 
      	case PEK: 
      	case KET:
           parentcopy[mutsite] = 
              (char) (rd.nextInt(FSET_END - FSET_START + 1) 
                     + FSET_START);
        }
      }
    }
    return( parentcopy );
  }
}
