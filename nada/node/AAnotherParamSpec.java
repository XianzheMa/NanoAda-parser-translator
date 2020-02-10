/* This file was generated by SableCC (http://www.sablecc.org/). */

package nada.node;

import nada.analysis.*;

@SuppressWarnings("nls")
public final class AAnotherParamSpec extends PAnotherParamSpec
{
    private TComma _comma_;
    private PParamSpec _paramSpec_;

    public AAnotherParamSpec()
    {
        // Constructor
    }

    public AAnotherParamSpec(
        @SuppressWarnings("hiding") TComma _comma_,
        @SuppressWarnings("hiding") PParamSpec _paramSpec_)
    {
        // Constructor
        setComma(_comma_);

        setParamSpec(_paramSpec_);

    }

    @Override
    public Object clone()
    {
        return new AAnotherParamSpec(
            cloneNode(this._comma_),
            cloneNode(this._paramSpec_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAnotherParamSpec(this);
    }

    public TComma getComma()
    {
        return this._comma_;
    }

    public void setComma(TComma node)
    {
        if(this._comma_ != null)
        {
            this._comma_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._comma_ = node;
    }

    public PParamSpec getParamSpec()
    {
        return this._paramSpec_;
    }

    public void setParamSpec(PParamSpec node)
    {
        if(this._paramSpec_ != null)
        {
            this._paramSpec_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._paramSpec_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._comma_)
            + toString(this._paramSpec_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._comma_ == child)
        {
            this._comma_ = null;
            return;
        }

        if(this._paramSpec_ == child)
        {
            this._paramSpec_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._comma_ == oldChild)
        {
            setComma((TComma) newChild);
            return;
        }

        if(this._paramSpec_ == oldChild)
        {
            setParamSpec((PParamSpec) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
