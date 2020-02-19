package nada.visitors;
import nada.node.*;
import nada.analysis.*;

/**
 * a helper visitor to check whether a procedure is returnable,
 * i.e. whether it has a parameter labeled mode out.
 * warning! it should be only applied to a ASubprogramBody node!
 */
public class ReturnableChecker extends DepthFirstAdapter{
    boolean returnable = false;

    public boolean isReturnable(){
        return this.returnable;
    }

    @Override
    public void inAParamSpec(AParamSpec node) {
        if(node.getOut() != null){
            // it is labeled out
            this.returnable = true;
        }
    }
}