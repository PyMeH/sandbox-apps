package pack;

import java.util.function.Consumer;

public class LambdaScope {

    public int x = 0;

    class NestedClass {

        public int x = 1;

        void methodInFirstLevel(int x) {
            
            // The following statement causes the compiler to generate
            // the error "local variables referenced from a lambda expression
            // must be final or effectively final" in statement A:
            //
            // x = 99;
            
            Consumer<Integer> myConsumer = (y) -> 
            {
                System.out.println("x = " + x); // Statement A
                System.out.println("y = " + y);
                System.out.println("this.x = " + this.x);
                System.out.println("LambdaScope.this.x = " +
                    LambdaScope.this.x);
            };

            myConsumer.accept(x);

        }
    }

    public static void main(String... args) {
        LambdaScope st = new LambdaScope();
        LambdaScope.NestedClass fl = st.new NestedClass();
        fl.methodInFirstLevel(23);
    }
}