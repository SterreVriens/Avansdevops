package project.infrastructure.libs.scm;

public class GitLibrary {
    //make commits, branches and repos
    public void commit(String message, String authorName, String branch, String repoName) {
        System.out.println("\n---------------Git-----------------");
        System.out.println("Creating commit in repository: " + repoName);
        System.out.println("Branch: " + branch);
        System.out.println("Committing changes with message: " + message);
        System.out.println("Committed by: " + authorName);
        System.out.println("-------------------------------------\n");
    }
    public void createBranch(String branchName, String repoName) {
        System.out.println("\n---------------Git-----------------");
        System.out.println("Creating branch: " + branchName);
        System.out.println("In repository: " + repoName);
        System.out.println("-------------------------------------\n");
    }
    public void createRepo(String repoName) {
        System.out.println("\n---------------Git-----------------");
        System.out.println("Creating repository: " + repoName);
        System.out.println("-------------------------------------\n");
    }
}
