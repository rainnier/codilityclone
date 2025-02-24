public class TesterClass {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java TesterClass <ClassName> <MethodName> <MethodArgs>");
            return;
        }

        String className = args[0]; // The name of the class to instantiate
        String methodName = args[1]; // The name of the method to call

        try {
            // Load the class dynamically
            Class<?> clazz = Class.forName(className);
            
            // Print all methods of the class
            /*System.out.println("Available methods in class " + className + ":");
            for (java.lang.reflect.Method m : clazz.getDeclaredMethods()) {
                System.out.println("Method: " + m.getName() + 
                                 " Return type: " + m.getReturnType().getName() +
                                 " Parameter types: " + java.util.Arrays.toString(m.getParameterTypes()));
            }*/

            // Create an instance of the class
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // Prepare method arguments (if any)
            Object[] methodArgs = new Object[args.length - 2];
            for (int i = 2; i < args.length; i++) {
                methodArgs[i - 2] = Integer.parseInt(args[i]); // Assuming method takes int arguments
            }

            // Find the method with the specified name and parameter types
            Class<?>[] paramTypes = new Class[methodArgs.length];
            for (int i = 0; i < methodArgs.length; i++) {
                paramTypes[i] = methodArgs[i].getClass();
            }

            // Get the method to invoke
            java.lang.reflect.Method method = clazz.getMethod(methodName, paramTypes);

            // Invoke the method and print the result
            Object result = method.invoke(instance, methodArgs);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}