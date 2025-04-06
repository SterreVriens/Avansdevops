package infrastructure.adapters.scm;

import  domain.common.models.User;
import  domain.scm.interfaces.ISCMAdapter;
import  domain.scm.models.Branch;
import  domain.scm.models.Commit;
import  domain.scm.models.Repository;
import  infrastructure.libs.scm.GitLibrary;

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
