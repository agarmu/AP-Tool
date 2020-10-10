import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.lang.Runtime;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Runner {
    File sourceDir;
    File destDir;
    Runtime runtime;
    Runner(File src, File dest){
        this.sourceDir = src;
        this.destDir = dest;
        this.runtime = Runtime.getRuntime();
    }
    void run() throws Exception{
        String name = this.getEntryPoint();
        try {
            System.out.println("Building...");
            int status = build(name);
            if (status != 0) {
                throw new Exception("Failed to build. (Exited with status " + status + ")");
            }
            status = execute(name);
            if (status != 0) {
                throw new Exception("Failed to run. (Exited with status " + status + ")");
            }
        } catch(IOException e) {
            throw new Exception(e.getMessage());
        }
       
    }
    int execute(String id) throws IOException , InterruptedException {
        Process process = this.runtime.exec("java " + id, new String[0], this.sourceDir);
        printResults(process);;
        return process.waitFor();
    }
    public static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
    int build(String id) throws IOException, InterruptedException {
        Process process = this.runtime.exec("javac " + id + ".java", new String[0], this.sourceDir);
        
        return  process.waitFor();
    }
    public String[] getFiles() throws IOException {
        if (Interact.checkIfPresent(this.sourceDir)) {
            return this.sourceDir.list();
        } else {
            throw new IOException("Directory does not exist.");
        }
    }
    public String getEntryPoint() throws IOException {
        String[] files = this.getFiles();
        int mainCount = 0;
        String main = "";
        Pattern suffixMatcher = Pattern.compile("(.*)\\.java$");
        for (String file : files) {
            Matcher match = suffixMatcher.matcher(file);
            if(match.find()) {
                File javaFile = new File(this.sourceDir.toString() + "/" + file);
                if (findMain(javaFile)) {
                    if (mainCount == 0) {
                        main = match.group(1);
                        mainCount++;
                    } else {
                        throw new IOException("Too many files with main function");
                    }
                }
            }
        }
        if (mainCount == 0) {
            throw new IOException("Unable to find main function in source directory: " + this.sourceDir.toString());
        }
        return main;
    }
    static boolean findMain(File javaFile) throws FileNotFoundException {
        Scanner reader = new Scanner(javaFile);
        String regex = "\\s*public static void main";
        Pattern MainFunctionMatcher = Pattern.compile(regex);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            Matcher match = MainFunctionMatcher.matcher(line);

            if( match.find() ) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }
}
