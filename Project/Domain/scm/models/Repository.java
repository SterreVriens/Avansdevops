package project.domain.scm.models;

import java.util.ArrayList;

import project.domain.scm.interfaces.ISCMAdapter;

public class Repository {
    private String name;
    private ISCMAdapter scmAdapter;
    private ArrayList<Branch> branches = new ArrayList<>();

    public Repository(String name, ISCMAdapter scmAdapter) {
        this.scmAdapter = scmAdapter;
        this.name = name;
        scmAdapter.createRepo(name);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Branch> getBranches() {
        return branches;
    }
    public void addBranch(Branch branch) {
        scmAdapter.createBranch(branch, this);
        this.branches.add(branch);
    }
    public void removeBranch(Branch branch) {
        this.branches.remove(branch);
    }
    public Branch getBranchByName(String name) {
        for (Branch branch : branches) {
            if (branch.getName().equals(name)) {
                return branch;
            }
        }
        return null;
    }
}
