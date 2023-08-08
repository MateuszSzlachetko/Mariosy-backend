package com.deloitte.ads.mariosy.entity;

import java.util.Set;

public class MariosyDTO {

    private Set<Marios> mariosy;
    private int count;

    public MariosyDTO(Set<Marios> mariosy) {
        this.mariosy = mariosy;
        this.count = mariosy.size();
    }

    public Set<Marios> getMariosy() {
        return mariosy;
    }

    public void setMariosy(Set<Marios> mariosy) {
        this.mariosy = mariosy;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
