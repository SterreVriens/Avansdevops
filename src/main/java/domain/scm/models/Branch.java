package  domain.scm.models;

import java.util.ArrayList;

import  domain.scm.interfaces.ISCMAdapter;

public class Branch {
    private String name;
    private ISCMAdapter scmAdapter;
    private Repository repo;
    private ArrayList<Commit> commits = new ArrayList<>();

    public Branch(String name, Repository repo, ISCMAdapter scmAdapter) {
        this.scmAdapter = scmAdapter;
        this.name = name;
        this.repo = repo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Commit> getCommits() {
        return commits;
    }
    public void printCommits() {
        for (Commit commit : commits) {
            System.out.println(commit.getMessage());
        }
    }
    public void addCommit(Commit commit) {
        scmAdapter.createCommit(commit, this, this.repo);
        this.commits.add(commit);
    }
    public void removeCommit(Commit commit) {
        this.commits.remove(commit);
    }
}
