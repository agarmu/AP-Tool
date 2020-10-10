import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Interact {
    File dir;
    Interact(){
        this.dir = new File(System.getProperty("user.dir"));
    }
    public void create(Identifier identifier) {
        System.err.println("");
        System.err.println("Creating video " + identifier.format());
        File unitFolder = new File(this.dir.toString() + "/U" + identifier.unit);
        System.err.print("Checking for existence of Unit ...");
        try {
            Interact.createNotPresent(unitFolder);
        } catch (IOException e) {
            System.err.println("\rUnit: " + e.getMessage());
        }
        System.err.print("Checking for existence of Video ...\r");
        File finalFolder = new File(unitFolder.toString() + "/" + identifier.format());
        try {
            Interact.createNotPresent_butNotEmpty(finalFolder);
        } catch (IOException e) {
            System.err.println("\r" + e.getMessage());
        }
        String name = Input.input("Please enter a one-word (no spaces/camel case) name: ");
        File javaFile = new File(finalFolder.toString() + "/" + name + ".java");
        try {
            if (javaFile.createNewFile()) {
                System.err.println("Successfully created main source file.");
            } else {
                System.err.println("Error creating source java file. Aborting...");
                System.exit(2);
            };
            
            FileWriter srcJava = new FileWriter(javaFile);
            srcJava.append("public class " + name + "{\n");
            srcJava.append("\tpublic static void main(String[] args){\n");
            srcJava.append("\t\tSystem.out.println(\"Hello World!\");\n");
            srcJava.append("\t}\n");
            srcJava.append("}\n");
            srcJava.flush();
            srcJava.close();
        } catch (IOException i) {
            System.err.println("Error occurred writing to the file.");
            System.exit(2);
        }
        
    }
    public void run(Identifier id) {
        File srcDir = new File(this.dir.toString() + "/U" + id.unit + "/" + id.format());
        File buildDir = new File(this.dir.toString() + "/build");
        File unitBuildDir = new File(buildDir.toString() + "/U" + id.unit);
        File destDir = new File(unitBuildDir.toString()  + "/" + id.format());
        try {
            Runner runner = new Runner(srcDir, destDir);
            runner.run();
        } catch(Exception e) {
            System.err.println("Run Error: " + e.getMessage());
        }
    }
    public static void createNotPresent_butNotEmpty(File f) throws IOException {
        String err = "";
        if (f.exists()) {
            if (f.isDirectory()) {
                if (f.listFiles().length != 0) {
                    err += "Fatal Error: " + f.toString() + " is not empty.";
                    err += "\nMake sure you did not accidentally input a previously populated directory.";
                    err += "\nAborting...";
                    throw new IOException(err);
                } else {
                    System.err.println("WARNING: The video directory "+ f.toString() +" already exists, but it is empty. Proceeding...");
                }
            } else {
               err += "Fatal Error: " + f.toString() + " is present, but is a file, not a directory.";
               throw new IOException(err);
            }
        } else {
            if (f.mkdir()) {
               System.out.println("Video directory " + f.toString() + " created.");
            } else {
                err += "Unable to create directory. Aborting...";
                throw new IOException(err);
            }
        }
    }
    public static boolean checkIfPresent(File f) throws IOException {
        return f.exists() && f.isDirectory(); 
    }
    public static void createNotPresent(File f) throws IOException {
        String err = "";
        if (f.exists()) {
            if(f.isDirectory()) {
            } else {
                err += "Fatal Error: " + f.toString() + "is present, but is a file, not a directory.";
                err += "Aborting...";
                throw new IOException(err);
            }
        } else {            
            if (f.mkdir()) {
                System.err.println("Directory successfully created.");
            } else {
                err += "Directory not created. Aborting...";
                throw new IOException(err);
            }
        }
    }
    
}
