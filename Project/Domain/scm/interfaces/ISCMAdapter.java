package project.domain.scm.interfaces;

import project.domain.scm.models.Branch;
import project.domain.scm.models.Commit;
import project.domain.scm.models.Repository;

public interface ISCMAdapter {
    public void createCommit(Commit commit, Branch branchName, Repository repoName);
    public void createBranch(Branch branchName, Repository repoName);
    public void createRepo(String repoName);
}
