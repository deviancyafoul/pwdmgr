package com.foobar.pwdmgr;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;


/**
 * Created by alisaarnold on 7/24/17.
 */
public class ClipboardUtils {



    public static void addToClipboard(String username, String password) throws InterruptedException {
        StringSelection selection = new StringSelection(password);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        System.out.println(username);
        System.out.println(password);
        Thread.sleep(4500);
        String password2 = "";
        StringSelection selection2 = new StringSelection(password2);
        clipboard.setContents(selection2, selection2);

    }
}
