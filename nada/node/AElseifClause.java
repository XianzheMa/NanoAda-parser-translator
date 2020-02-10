/* This file was generated by SableCC (http://www.sablecc.org/). */

package nada.node;

import nada.analysis.*;

@SuppressWarnings("nls")
public final class AElseifClause extends PElseifClause
{
    private TElseif _elseif_;
    private PRelation _relation_;
    private TThen _then_;
    private PStmtSeq _stmtSeq_;

    public AElseifClause()
    {
        // Constructor
    }

    public AElseifClause(
        @SuppressWarnings("hiding") TElseif _elseif_,
        @SuppressWarnings("hiding") PRelation _relation_,
        @SuppressWarnings("hiding") TThen _then_,
        @SuppressWarnings("hiding") PStmtSeq _stmtSeq_)
    {
        // Constructor
        setElseif(_elseif_);

        setRelation(_relation_);

        setThen(_then_);

        setStmtSeq(_stmtSeq_);

    }

    @Override
    public Object clone()
    {
        return new AElseifClause(
            cloneNode(this._elseif_),
            cloneNode(this._relation_),
            cloneNode(this._then_),
            cloneNode(this._stmtSeq_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAElseifClause(this);
    }

    public TElseif getElseif()
    {
        return this._elseif_;
    }

    public void setElseif(TElseif node)
    {
        if(this._elseif_ != null)
        {
            this._elseif_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._elseif_ = node;
    }

    public PRelation getRelation()
    {
        return this._relation_;
    }

    public void setRelation(PRelation node)
    {
        if(this._relation_ != null)
        {
            this._relation_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._relation_ = node;
    }

    public TThen getThen()
    {
        return this._then_;
    }

    public void setThen(TThen node)
    {
        if(this._then_ != null)
        {
            this._then_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._then_ = node;
    }

    public PStmtSeq getStmtSeq()
    {
        return this._stmtSeq_;
    }

    public void setStmtSeq(PStmtSeq node)
    {
        if(this._stmtSeq_ != null)
        {
            this._stmtSeq_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._stmtSeq_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._elseif_)
            + toString(this._relation_)
            + toString(this._then_)
            + toString(this._stmtSeq_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._elseif_ == child)
        {
            this._elseif_ = null;
            return;
        }

        if(this._relation_ == child)
        {
            this._relation_ = null;
            return;
        }

        if(this._then_ == child)
        {
            this._then_ = null;
            return;
        }

        if(this._stmtSeq_ == child)
        {
            this._stmtSeq_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._elseif_ == oldChild)
        {
            setElseif((TElseif) newChild);
            return;
        }

        if(this._relation_ == oldChild)
        {
            setRelation((PRelation) newChild);
            return;
        }

        if(this._then_ == oldChild)
        {
            setThen((TThen) newChild);
            return;
        }

        if(this._stmtSeq_ == oldChild)
        {
            setStmtSeq((PStmtSeq) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}