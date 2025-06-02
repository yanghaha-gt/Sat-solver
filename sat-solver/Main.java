
import expressions.Expression;
import expressions.Interpretation;
import solver.SatSolver;

/**
 * Class to test SatSolver and related expressions.
 */
public class Main {
    private static final Expression placeholder = Expression.createConstant(true);
    
    public static void main(String[] args) {
        Main main = new Main();
        main.print(main.expression1());
        main.print(main.expression2());
        main.print(main.expression3());
        main.print(main.expression4());
        main.print(main.expression5());
        main.print(main.expression6());
        main.print(main.expression7());

    }

    public void  print( Expression expr ){
        System.out.println( "Expression: " + expr +"\n" );
        System.out.println( "The variables: " + expr.variables() + "\n" );
        System.out.println( "All possible interpretations: \n");
        for ( Interpretation interpretation : SatSolver.getAllInterpretations(expr) ){
            System.out.println(interpretation + "\n" );
        }
        System.out.println( "The expression is " + (SatSolver.isSatisfiable(expr) ? "" : "not") + " satisfiable");
        System.out.println( "The expression is " + (SatSolver.isTautology(expr) ? "" : "not") + " a tautology");
        System.out.println( "The expression is " + (SatSolver.isContradiction(expr) ? "" : "not") + " a contradiction");
        System.out.println( "All interpretations that satisfies the expression: \n");
        for ( Interpretation interpretation : SatSolver.allSatisfiableInterpretations(expr) ){
            System.out.println(interpretation + "\n");
        }
        System.out.println( "All interpretations that do not satisfy the expression: \n");
        for ( Interpretation interpretation : SatSolver.allUnsatisfiableInterpretations(expr) ){
            System.out.println(interpretation + "\n");
        }
    }

    public Expression expression1(){
        Expression p = Expression.createVariableExpression("p");
        return placeholder.or( p ,placeholder.not(p));
    }

    public Expression expression2(){
        Expression p = Expression.createVariableExpression("p");
        Expression q = Expression.createVariableExpression("q");
        return placeholder.and( p , q );
    }

    public Expression expression3(){
        Expression q = Expression.createVariableExpression("q");
        return placeholder.implies( Expression.createConstant(false) , q );
    }

    public Expression expression4(){
        Expression p = Expression.createVariableExpression("p");
        return placeholder.implies( p , placeholder.not(p) );
    }

    public Expression expression5(){
        Expression p = Expression.createVariableExpression("p");
        return placeholder.and( p , placeholder.not(p) );
    }

    public Expression expression6(){
        Expression p = Expression.createVariableExpression("p");
        Expression q = Expression.createVariableExpression("q");
        Expression r = Expression.createVariableExpression("r");
        return placeholder.xor( p , placeholder.implies( q , r));
    }

    public Expression expression7(){
        Expression p = Expression.createVariableExpression("p");
        Expression q = Expression.createVariableExpression("q");
        return placeholder.iff ( placeholder.not( placeholder.or(p,q)) , (placeholder.and(placeholder.not(p),placeholder.not(q))) );
    }

}
