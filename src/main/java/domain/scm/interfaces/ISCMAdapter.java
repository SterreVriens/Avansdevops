package  domain.scm.interfaces;

import  domain.scm.models.Branch;
import  domain.scm.models.Commit;
import  domain.scm.models.Repository;

public interface ISCMAdapter {
    public void createCommit(Commit commit, Branch branchName, Repository repoName);
    public void createBranch(Branch branchName, Repository repoName);
    public void createRepo(String repoName);
}
