package net.portalblock.portalbot;

/**
 * Created by portalBlock on 1/31/2015.
 */
public interface Ignorable {

    public void ignore(String hostmask);

    public void unignore(String hostmask);

    public boolean isIgnoring(String hostmask);

}
