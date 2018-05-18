package Util;

import org.junit.Test;

import static org.junit.Assert.*;

public class PinyinSplitTest {

    @Test
    public void split() {
    }

    @Test
    public void splitSpell() {
        //System.out.println(PinyinSplit.splitSpell("beijing").equals("bei jing "));
        assertEquals(PinyinSplit.splitSpell("beijing"), "bei jing ");
    }
}