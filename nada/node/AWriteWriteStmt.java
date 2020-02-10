/* This file was generated by SableCC (http://www.sablecc.org/). */

package nada.node;

import nada.analysis.*;

@SuppressWarnings("nls")
public final class AWriteWriteStmt extends PWriteStmt
{
    private TWrite _write_;
    private TLParen _lParen_;
    private PWriteExpr _writeExpr_;
    private TRParen _rParen_;
    private TSemi _semi_;

    public AWriteWriteStmt()
    {
        // Constructor
    }

    public AWriteWriteStmt(
        @SuppressWarnings("hiding") TWrite _write_,
        @SuppressWarnings("hiding") TLParen _lParen_,
        @SuppressWarnings("hiding") PWriteExpr _writeExpr_,
        @SuppressWarnings("hiding") TRParen _rParen_,
        @SuppressWarnings("hiding") TSemi _semi_)
    {
        // Constructor
        setWrite(_write_);

        setLParen(_lParen_);

        setWriteExpr(_writeExpr_);

        setRParen(_rParen_);

        setSemi(_semi_);

    }

    @Override
    public Object clone()
    {
        return new AWriteWriteStmt(
            cloneNode(this._write_),
            cloneNode(this._lParen_),
            cloneNode(this._writeExpr_),
            cloneNode(this._rParen_),
            cloneNode(this._semi_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAWriteWriteStmt(this);
    }

    public TWrite getWrite()
    {
        return this._write_;
    }

    public void setWrite(TWrite node)
    {
        if(this._write_ != null)
        {
            this._write_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._write_ = node;
    }

    public TLParen getLParen()
    {
        return this._lParen_;
    }

    public void setLParen(TLParen node)
    {
        if(this._lParen_ != null)
        {
            this._lParen_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lParen_ = node;
    }

    public PWriteExpr getWriteExpr()
    {
        return this._writeExpr_;
    }

    public void setWriteExpr(PWriteExpr node)
    {
        if(this._writeExpr_ != null)
        {
            this._writeExpr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._writeExpr_ = node;
    }

    public TRParen getRParen()
    {
        return this._rParen_;
    }

    public void setRParen(TRParen node)
    {
        if(this._rParen_ != null)
        {
            this._rParen_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rParen_ = node;
    }

    public TSemi getSemi()
    {
        return this._semi_;
    }

    public void setSemi(TSemi node)
    {
        if(this._semi_ != null)
        {
            this._semi_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semi_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._write_)
            + toString(this._lParen_)
            + toString(this._writeExpr_)
            + toString(this._rParen_)
            + toString(this._semi_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._write_ == child)
        {
            this._write_ = null;
            return;
        }

        if(this._lParen_ == child)
        {
            this._lParen_ = null;
            return;
        }

        if(this._writeExpr_ == child)
        {
            this._writeExpr_ = null;
            return;
        }

        if(this._rParen_ == child)
        {
            this._rParen_ = null;
            return;
        }

        if(this._semi_ == child)
        {
            this._semi_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._write_ == oldChild)
        {
            setWrite((TWrite) newChild);
            return;
        }

        if(this._lParen_ == oldChild)
        {
            setLParen((TLParen) newChild);
            return;
        }

        if(this._writeExpr_ == oldChild)
        {
            setWriteExpr((PWriteExpr) newChild);
            return;
        }

        if(this._rParen_ == oldChild)
        {
            setRParen((TRParen) newChild);
            return;
        }

        if(this._semi_ == oldChild)
        {
            setSemi((TSemi) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
