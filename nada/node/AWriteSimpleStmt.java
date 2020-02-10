/* This file was generated by SableCC (http://www.sablecc.org/). */

package nada.node;

import nada.analysis.*;

@SuppressWarnings("nls")
public final class AWriteSimpleStmt extends PSimpleStmt
{
    private PWriteStmt _writeStmt_;

    public AWriteSimpleStmt()
    {
        // Constructor
    }

    public AWriteSimpleStmt(
        @SuppressWarnings("hiding") PWriteStmt _writeStmt_)
    {
        // Constructor
        setWriteStmt(_writeStmt_);

    }

    @Override
    public Object clone()
    {
        return new AWriteSimpleStmt(
            cloneNode(this._writeStmt_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAWriteSimpleStmt(this);
    }

    public PWriteStmt getWriteStmt()
    {
        return this._writeStmt_;
    }

    public void setWriteStmt(PWriteStmt node)
    {
        if(this._writeStmt_ != null)
        {
            this._writeStmt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._writeStmt_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._writeStmt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._writeStmt_ == child)
        {
            this._writeStmt_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._writeStmt_ == oldChild)
        {
            setWriteStmt((PWriteStmt) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}