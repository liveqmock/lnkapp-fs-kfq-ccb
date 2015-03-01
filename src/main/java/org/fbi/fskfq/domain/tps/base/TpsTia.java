package org.fbi.fskfq.domain.tps.base;

import java.io.Serializable;

public abstract class TpsTia implements Serializable {
    public abstract TpsTiaHeader getHeader();
    public abstract TpsTiaBody getBody();
    public abstract TpsTiaSigns getSigns();
}
