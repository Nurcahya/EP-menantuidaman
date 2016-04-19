/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep;

import static ep.EvolutionaryProgramming.program;
import static ep.EvolutionaryProgramming.rd;
import static ep.EvolutionaryProgramming.ROH;
import static ep.EvolutionaryProgramming.TAM;
import static ep.EvolutionaryProgramming.PEK;
import static ep.EvolutionaryProgramming.KET;
import static ep.EvolutionaryProgramming.FSET_START;
import static ep.EvolutionaryProgramming.FSET_END;
import static ep.EvolutionaryProgramming.varnumber;
import static ep.EvolutionaryProgramming.randomnumber;
import static ep.EvolutionaryProgramming.PC;
import static ep.EvolutionaryProgramming.x;
/**
 *
 * @author Nurcahya
 */
public class PilihEdge {
    
    
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
}
