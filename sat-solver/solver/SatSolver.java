package solver;

import expressions.Expression;
import expressions.Interpretation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class representing a sat solver.
 * @version 0.1
 */
public class SatSolver {

    /**
     * Checks if an expression is a tautology, i.e.: it is true under all interpretations
     * @param expression the expression to check
     * @return {@code true} iff {@code no interpretation i : not expression.evaluate(i)}
     * Preconditions: the expression cannot be null
     * Postconditions: returns true if the expression is a tautology, is false otherwise
     */
    public static boolean isTautology(Expression expression) {
        if ( expression == null ){
            throw new IllegalArgumentException("The 'expression' cannot be null");
        }
        
        Set<Interpretation> interpretations = getAllInterpretations(expression);               //We get all the possible interpretations

        if ( interpretations.isEmpty() ){
            return true;                                                                       //if the interpretations is just empty, it is always true
        }
        Interpretation[] interpretationArray = new Interpretation[interpretations.size()];
        int index = 0;
        for ( Interpretation interpretation : interpretations ){                               //We create a array to store the interpretations
            interpretationArray[index] = interpretation;
            index++;
        }
        for ( int i = 0 ; i < interpretationArray.length ; i++ ){                              //We travel all the interpretations to check the value
            if ( !expression.evaluate(interpretationArray[i]) ){
                return false;
            }
        }
        return true;
    }                                                                                          //The idea is the same in the methods below, I will not write the explanatory note

    /**
     * Checks if an expression is a contradiction, i.e.: it is false under all interpretations
     * @param expression the expression to check
     * @return {@code true} iff {@code all interpretation i : not expression.evaluate(i)}
     * Preconditions: the expression cannot be null
     * Postconditions: return true if the expression is a contradiction, is false otherwise
     */
    public static boolean isContradiction(Expression expression) {
        if ( expression == null ){
            throw new IllegalArgumentException("The 'expression' cannot be null");
        }
        Set<Interpretation> interpretations = getAllInterpretations(expression);
        Interpretation[] interpretationArray = new Interpretation[interpretations.size()];
        int index = 0;
        for ( Interpretation interpretation : interpretations ){
            interpretationArray[index] = interpretation;
            index++;
        }
        for ( int i = 0 ; i < interpretationArray.length ; i++ ){
            if ( expression.evaluate(interpretationArray[i]) ){
                return false;
            }
        }
        return true;
    }   

    /**
     * Checks if an expression is satisfiable, i.e.: it is true under at least one interpretation
     * @param expression the expression to check
     * @return {@code true} iff {@code exists interpretation i : expression.evaluate(i)}
     * Preconditions: the expression cannot be null
     * Postconditions: return ture if the expression is satisfiable, is false otherwise
     */
    public static boolean isSatisfiable(Expression expression) {
        if ( expression == null ){
            throw new IllegalArgumentException("The 'expression' cannot be null");
        }
        Set<Interpretation> interpretations = getAllInterpretations(expression);
        Interpretation[] interpretationArray = new Interpretation[interpretations.size()];
        int index = 0;
        for ( Interpretation interpretation : interpretations ){
            interpretationArray[index] = interpretation;
            index++;
        }
        for ( int i = 0 ; i < interpretationArray.length ; i++ ){
            if  ( expression.evaluate(interpretationArray[i]) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all possible interpretations under which an expression is satisfiable
     * @param expression the expression to check
     * @return {@code l : all i in l : expression.evaluate(i)}
     * Preconditions: the expression cannot be null
     * Postconditions: returns a list of all the interpretations that satisfy the expression
     */
    public static List<Interpretation> allSatisfiableInterpretations(Expression expression) {
        if ( expression == null ){
            throw new IllegalArgumentException("The 'expression' cannot be null");
        }
        Set<Interpretation> interpretations = getAllInterpretations(expression);
        Interpretation[] interpretationArray = new Interpretation[interpretations.size()];
        int index = 0;
        for ( Interpretation interpretation : interpretations){
            interpretationArray[index] = interpretation;
            index++;
        }
        List<Interpretation> satisfiableInterpretations = new ArrayList<>();                     //We create a list to store the satisfied interpretations  
        
        for ( int i = 0 ; i < interpretationArray.length ; i++ ){
            if ( expression.evaluate(interpretationArray[i]) ){
                satisfiableInterpretations.add(interpretationArray[i]);
            }
        }
        return satisfiableInterpretations;
    }

    /**
     * Returns all possible interpretations under which an expression is unsatisfiable
     * @param expression the expression to check
     * @return {@code l : all i in l : not expression.evaluate(i)}
     * Preconditions: the expression cannot be null
     * Postconditions: return a list of all the interpretations that do not satisfy the expression
     */
    public static List<Interpretation> allUnsatisfiableInterpretations(Expression expression) {
        if ( expression == null ){
            throw new IllegalArgumentException("The 'expression' cannot be null");
        }
        Set<Interpretation> interpretations = getAllInterpretations(expression);
        Interpretation[] interpretationArray = new Interpretation[interpretations.size()];
        int index = 0;
        for ( Interpretation interpretation : interpretations ){
            interpretationArray[index] = interpretation;
            index++;
        }
        List<Interpretation> unSatisfiableInterpretations = new ArrayList<>();
        for ( int i = 0 ; i < interpretationArray.length ; i++ ){
            if ( !expression.evaluate(interpretationArray[i]) ){
                unSatisfiableInterpretations.add(interpretationArray[i]);
            }
        }
        return unSatisfiableInterpretations;
    }

    /**
     * @param expression the expression to check
     * @return all variable names in an expression
     * Preconditions: the expression cannot be null
     * Post conditions: returns a set of all the variable names in the expression
     */
    public static Set<String> getAllVariables(Expression expression) {
        if ( expression == null ){
            throw new IllegalArgumentException("The 'expression' cannot be null");
        }
        return expression.variables();
    }

    /**
     * Returns all possible interpretations for a particular expression
     * @param expression the expression to use
     * @return all possible interpretations for {@code expression}
     * Preconditions: the expression cannot be null
     * Postconditions: return a set of all the possible interpretations for the expression
     */
    public static Set<Interpretation> getAllInterpretations(Expression expression) {
        if ( expression == null ){
            throw new IllegalArgumentException("The 'expression' cannot be null");
        }
        Set<String> variables = getAllVariables(expression);
        Set<Interpretation> interpretations = new HashSet<>();

        //First, I want to get all the variables

        int variableNumber = getAllVariables(expression).size();
        String[] variableArray = new String[variableNumber];
        int index = 0;
        for ( String var : variables ){
            variableArray[index] = var;
            index++;
        }

        //Then, I want to get all the possibilities(TFvalues) for the variables

        int possibilitiesNumber = 1;
        for ( int i = 0 ; i < variableNumber ; i++ ){
            possibilitiesNumber = possibilitiesNumber*2;                         // *2 is because we have TRUE and FALSE  two situations
        }

        /*
         * Now, I want to use the binary system to stand for the possibilities
         * True is 1, False is 0
         * every variable will take one bit
         * form the bitset
         */

        for ( int i = 0 ; i < possibilitiesNumber ; i++ ){
            BitSet bitSet = new BitSet(variableNumber);
            for ( int j = 0 ; j < variableNumber ; j++ ){
                if ( (i & ( 1<< j)) != 0){
                    bitSet.set(j);
                }
            }
        
        Interpretation interpretation = new Interpretation(Arrays.asList(variableArray), bitSet);
        interpretations.add(interpretation);
        }
        return interpretations;
    }
    
}
