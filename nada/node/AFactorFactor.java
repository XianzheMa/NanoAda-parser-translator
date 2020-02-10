/* This file was generated by SableCC (http://www.sablecc.org/). */

package nada.node;

import nada.analysis.*;

@SuppressWarnings("nls")
public final class AFactorFactor extends PFactor
{
    private PPrimary _primary_;

    public AFactorFactor()
    {
        // Constructor
    }

    public AFactorFactor(
        @SuppressWarnings("hiding") PPrimary _primary_)
    {
        // Constructor
        setPrimary(_primary_);

    }

    @Override
    public Object clone()
    {
        return new AFactorFactor(
            cloneNode(this._primary_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFactorFactor(this);
    }

    public PPrimary getPrimary()
    {
        return this._primary_;
    }

    public void setPrimary(PPrimary node)
    {
        if(this._primary_ != null)
        {
            this._primary_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._primary_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._primary_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._primary_ == child)
        {
            this._primary_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._primary_ == oldChild)
        {
            setPrimary((PPrimary) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}