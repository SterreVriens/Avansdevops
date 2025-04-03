package project.infrastructure.adapters.scm;

import project.domain.common.models.User;
import project.domain.scm.interfaces.ISCMAdapter;
import project.domain.scm.models.Branch;
import project.domain.scm.models.Commit;
import project.domain.scm.models.Repository;
import project.infrastructure.libs.scm.GitLibrary;

public class GitAdapter implements ISCMAdapter{
    private GitLibrary gitLibrary;

    public GitAdapter(){
        this.gitLibrary = new GitLibrary();
    }

    @Override
    public void createCommit(Commit commit, Branch branchName, Repository repoName) {
        gitLibrary.commit(commit.getMessage(), commit.getAuthor().getUsername(), branchName.getName(), repoName.getName());
    }

    @Override
    public void createBranch(Branch branchName, Repository repoName) {
        gitLibrary.createBranch(branchName.getName(), repoName.getName());
    }

    @Override
    public void createRepo(String repoName) {
        gitLibrary.createRepo(repoName);
    }
    
}
