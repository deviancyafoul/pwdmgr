package com.foobar.pwdmgr;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Created by alisaarnold on 7/24/17.
 */
public class ClipboardUtils {

    public static void addToClipboard(String username, String password) {
        StringSelection selection = new StringSelection(password);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        System.out.println(username);
    }
}
